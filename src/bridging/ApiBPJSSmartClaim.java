package bridging;

import fungsi.koneksiDB;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * BPJS Smart Claim API Integration Class Handles compression, encryption, and
 * API communication for Smart Claim submissions
 *
 * @author IT Team - BMMC
 * @version 2.0
 */
public class ApiBPJSSmartClaim {

    private String Key, Consid, KodeFaskes;
    private String salt;
    private String generateHmacSHA256Signature;
    private byte[] hmacData;
    private Mac mac;
    private long millis;
    private SSLContext sslContext;
    private SSLSocketFactory sslFactory;
    private SecretKeySpec secretKey;
    private Scheme scheme;
    private HttpComponentsClientHttpRequestFactory factory;
    private ApiBPJSAesKeySpec mykey;

    /**
     * Constructor - Initializes BPJS API credentials from database
     */
    public ApiBPJSSmartClaim() {
        try {
            Key = koneksiDB.SECRETKEYAPIBPJS();
            Consid = koneksiDB.CONSIDAPIBPJS();
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
        }
    }

    /**
     * Generate HMAC SHA-256 signature for API authentication
     *
     * @param utc UTC timestamp
     * @return HMAC signature string
     */
    public String getHmac(String utc) {
        salt = Consid + "&" + utc;
        generateHmacSHA256Signature = null;
        try {
            generateHmacSHA256Signature = generateHmacSHA256Signature(salt, Key);
        } catch (GeneralSecurityException e) {
            System.out.println("Error Signature : " + e);
            e.printStackTrace();
        }
        return generateHmacSHA256Signature;
    }

    /**
     * Generate HMAC SHA-256 signature
     *
     * @param data Data to sign
     * @param key Secret key
     * @return Base64 encoded signature
     * @throws GeneralSecurityException
     */
    public String generateHmacSHA256Signature(String data, String key) throws GeneralSecurityException {
        hmacData = null;
        try {
            secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);
            hmacData = mac.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hmacData);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error Generate HMac: " + e);
            throw new GeneralSecurityException(e);
        }
    }

    /**
     * Get current UTC timestamp in seconds
     *
     * @return UTC timestamp
     */
    public long GetUTCdatetimeAsString() {
        millis = System.currentTimeMillis();
        return millis / 1000;
    }

    /**
     * Decrypt BPJS response data
     *
     * @param data Encrypted data
     * @param utc UTC timestamp
     * @return Decrypted string
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public String Decrypt(String data, String utc) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        System.out.println("Decrypting data: " + data);
        mykey = ApiBPJSEnc.generateKey(Consid + Key + utc);
        data = ApiBPJSEnc.decrypt(data, mykey.getKey(), mykey.getIv());
        data = ApiBPJSLZString.decompressFromEncodedURIComponent(data);
        return data;
    }

    /**
     * Compresses and Base64 encodes the data (Step 1-2 of Smart Claim flow)
     * Flow: String → GZIP Compress → Base64 Encode
     *
     * @param str JSON string to compress
     * @return Base64 encoded compressed data
     * @throws IOException
     */
    public String compressSmartClaim(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
        }

        // Return Base64 encoded compressed data (matching PHP's base64_encode)
        return Base64.getEncoder().encodeToString(out.toByteArray());
    }

    /**
     * Encrypts compressed data using AES-256-CBC (PHP equivalent) Flow: Base64
     * Compressed Data → AES-256-CBC Encrypt → Base64 Encode
     *
     * Complete encryption flow: 1. Generate SHA-256 hash of encryption key 2.
     * Convert hash to binary (hex2bin equivalent) 3. Use first 16 bytes as IV
     * 4. Encrypt compressed data with AES-256-CBC 5. Return Base64 encoded
     * encrypted data
     *
     * @param kodefaskes Facility code (kode faskes)
     * @param compressedBase64Data Already compressed and Base64 encoded data
     * from compressSmartClaim()
     * @return Base64 encoded encrypted data, or null if error occurs
     */
    public String EncryptCompressedData(String kodefaskes, String compressedBase64Data) {
        String encryptMethod = "AES/CBC/PKCS5Padding";
        String encryptKey = Consid + Key + kodefaskes;

        System.out.println("=== Smart Claim Encryption Debug ===");
        System.out.println("Cons ID      : " + Consid);
        System.out.println("Secret key   : " + Key);
        System.out.println("Kode Faskes  : " + kodefaskes);
        System.out.println("Encrypt Key  : " + encryptKey);

        try {
            // Step 1: Generate key hash using SHA-256 (matching PHP's hash('sha256', $key))
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] keyHashHex = digest.digest(encryptKey.getBytes(StandardCharsets.UTF_8));

            // Step 2: Convert hex to binary (matching PHP's hex2bin())
            // PHP hash() returns hex string, then hex2bin() converts it to binary
            String keyHashHexString = bytesToHex(keyHashHex);
            byte[] keyHash = hexStringToByteArray(keyHashHexString);

            // Step 3: Generate IV from key hash (first 16 bytes, matching PHP)
            byte[] iv = new byte[16];
            System.arraycopy(keyHash, 0, iv, 0, 16);

            System.out.println("Key Hash Hex : " + keyHashHexString.substring(0, 32) + "...");
            System.out.println("IV Hex       : " + bytesToHex(iv));

            // Step 4: Initialize cipher
            Cipher cipher = Cipher.getInstance(encryptMethod);
            SecretKeySpec secretKey = new SecretKeySpec(keyHash, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            // Step 5: Encrypt the compressed Base64 data directly (NO extra Base64 encoding)
            // PHP encrypts the string directly, not Base64 encoded again
            byte[] encryptedBytes = cipher.doFinal(compressedBase64Data.getBytes(StandardCharsets.UTF_8));

            // Step 6: Base64 encode the encrypted result
            String finalEncrypted = Base64.getEncoder().encodeToString(encryptedBytes);

            System.out.println("Encryption successful");
            System.out.println("Encrypted length: " + finalEncrypted.length());
            System.out.println("====================================");

            return finalEncrypted;

        } catch (Exception e) {
            System.err.println("Error in EncryptCompressedData: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Convert byte array to hexadecimal string Used for debugging and matching
     * PHP's hash output format
     *
     * @param bytes Byte array to convert
     * @return Hexadecimal string representation
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Convert hexadecimal string to byte array (PHP's hex2bin equivalent) This
     * is critical for matching PHP's encryption behavior
     *
     * @param hexString Hexadecimal string
     * @return Byte array
     */
    private byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Base64 encode string (legacy method - kept for backward compatibility)
     *
     * @param data String to encode
     * @return Base64 encoded string
     * @deprecated Use Base64.getEncoder().encodeToString() directly
     */
    @Deprecated
    public String encodeBase64(String data) {
        try {
            return Base64.getEncoder().encodeToString(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error encoding Base64: " + e);
            return null;
        }
    }

    /**
     * Base64 encode from bytes (legacy method - kept for backward
     * compatibility)
     *
     * @param data Byte array to encode
     * @return Base64 encoded string
     * @deprecated Use Base64.getEncoder().encodeToString() directly
     */
    @Deprecated
    public String encodeBase64FromBytes(byte[] data) {
        try {
            return Base64.getEncoder().encodeToString(data);
        } catch (Exception e) {
            System.out.println("Error encoding Base64 from bytes: " + e);
            return null;
        }
    }

    /**
     * Create RestTemplate with improved SSL handling and connection pooling
     *
     * @return Configured RestTemplate
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public RestTemplate getRest() throws NoSuchAlgorithmException, KeyManagementException {
        sslContext = SSLContext.getInstance("TLSv1.2"); // Use TLS 1.2 instead of SSL

        TrustManager[] trustManagers = {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
            }
        };

        sslContext.init(null, trustManagers, new SecureRandom());
        sslFactory = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        scheme = new Scheme("https", 443, sslFactory);

        factory = new HttpComponentsClientHttpRequestFactory();

        // Set timeouts to handle slow connections
        //factory.set(30000); // 30 seconds
        factory.setReadTimeout(60000);    // 60 seconds

        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);

        return new RestTemplate(factory);
    }

    /**
     * Complete Smart Claim encryption flow Combines compression and encryption
     * in one method
     *
     * @param jsonData JSON string data to encrypt
     * @param kodefaskes Facility code
     * @return Final encrypted and encoded string ready for API transmission
     * @throws IOException
     */
    public String encryptSmartClaimData(String jsonData, String kodefaskes) throws IOException {
        // Step 1 & 2: Compress and Base64 encode
        String compressed = compressSmartClaim(jsonData);

        // Step 3 & 4: Encrypt and Base64 encode
        String encrypted = EncryptCompressedData(kodefaskes, compressed);

        return encrypted;
    }

    /**
     * Compresses data using GZIP (Step 1) Returns RAW compressed bytes, NOT
     * base64
     *
     * @param str JSON string to compress
     * @return Raw GZIP compressed bytes
     * @throws IOException
     */
    public byte[] compressSmartClaimRaw(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return new byte[0];
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
        }

        return out.toByteArray(); // Return raw bytes, not base64
    }

    /**
     * Encrypts compressed data using AES-256-CBC (matching PHP with
     * OPENSSL_RAW_DATA)
     *
     * Flow matching PHP: 1. json_encode($data) 2. gzencode($data) → raw bytes
     * 3. base64_encode($compressed) → string 4. Generate key:
     * hex2bin(hash('sha256', $keyRaw)) 5. Generate IV: substr($key, 0, 16) 6.
     * openssl_encrypt($compressed_base64_string, 'AES-256-CBC', $key, 0, $iv) →
     * returns base64 7. Return encrypted base64
     *
     * @param kodefaskes Facility code
     * @param compressedBytes Raw compressed bytes from compressSmartClaimRaw()
     * @return Base64 encoded encrypted data
     */
    public String EncryptCompressedDataV2(String kodefaskes, byte[] compressedBytes) {
        String encryptMethod = "AES/CBC/PKCS5Padding";
        String encryptKey = Consid + Key + kodefaskes;

        //  System.out.println("=== Smart Claim Encryption V2 (PHP Compatible) ===");
        //  System.out.println("Cons ID      : " + Consid);
        //  System.out.println("Secret key   : " + Key);
        // System.out.println("Kode Faskes  : " + kodefaskes);
        // System.out.println("Encrypt Key  : " + encryptKey);
        try {
            // Step 1: Base64 encode the compressed bytes (matching PHP's base64_encode($compressed))
            String compressedBase64 = Base64.getEncoder().encodeToString(compressedBytes);
            //    System.out.println("Compressed Base64 length: " + compressedBase64.length());

            // Step 2: Generate key hash using SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] keyHashBytes = digest.digest(encryptKey.getBytes(StandardCharsets.UTF_8));

            // Step 3: Convert to hex string (matching PHP's hash('sha256', $key))
            String keyHashHex = bytesToHex(keyHashBytes);

            // Step 4: Convert hex to binary (matching PHP's hex2bin())
            byte[] keyHash = hexStringToByteArray(keyHashHex);

            // Step 5: Generate IV from key hash (first 16 bytes)
            byte[] iv = new byte[16];
            System.arraycopy(keyHash, 0, iv, 0, 16);

            //System.out.println("Key Hash Hex : " + keyHashHex.substring(0, 32) + "...");
            // System.out.println("IV Hex       : " + bytesToHex(iv));
            // Step 6: Initialize cipher
            Cipher cipher = Cipher.getInstance(encryptMethod);
            SecretKeySpec secretKey = new SecretKeySpec(keyHash, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            // Step 7: Encrypt the base64 compressed string (matching PHP)
            // PHP encrypts the base64 string, not the raw bytes
            byte[] encryptedBytes = cipher.doFinal(compressedBase64.getBytes(StandardCharsets.UTF_8));

            // Step 8: Base64 encode the encrypted result
            String finalEncrypted = Base64.getEncoder().encodeToString(encryptedBytes);

            //System.out.println("Encryption successful");
            //System.out.println("Final encrypted length: " + finalEncrypted.length());
            // System.out.println("================================================");
            return finalEncrypted;

        } catch (Exception e) {
            System.err.println("Error in EncryptCompressedDataV2: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Complete encryption flow matching PHP exactly
     *
     * @param jsonData JSON string
     * @param kodefaskes Facility code
     * @return Encrypted base64 string
     * @throws IOException
     */
    public String encryptSmartClaimDataV2(String jsonData, String kodefaskes) throws IOException {
        // Step 1: Compress to raw bytes
        byte[] compressedBytes = compressSmartClaimRaw(jsonData);

        // Step 2: Encrypt (internally converts to base64, encrypts, then returns base64)
        String encrypted = EncryptCompressedDataV2(kodefaskes, compressedBytes);

        return encrypted;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fungsi.koneksiDB;
import fungsi.sekuel;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author khanzasoft
 */
public class SatuSehatCekNIK {
    
    // ========== KONSTANTA STATUS ==========
    public static final String IHS_NIK_TIDAK_VALID  = "NIK_TIDAK_VALID";
    public static final String IHS_DITEMUKAN        = "DITEMUKAN";
    public static final String IHS_CACHE_COCOK      = "CACHE_COCOK";
    public static final String IHS_CACHE_DIPERBAIKI = "CACHE_DIPERBAIKI";
    public static final String IHS_TIDAK_DITEMUKAN  = "TIDAK_DITEMUKAN";
    public static final String IHS_GAGAL            = "GAGAL";
    public static final String IHS_DIBUAT           = "DIBUAT";
    public static final String IHS_DATA_BELUM_LENGKAP = "DATA_BELUM_LENGKAP";
    public static final String IHS_GAGAL_BUAT       = "GAGAL_BUAT";

    // ========== INNER CLASS HasilIHS ==========
    public static class HasilIHS {
        /** IHS number bila ketemu, "" bila tidak. */
        public String ihs = "";
        /** Salah satu konstanta IHS_*. */
        public String status = "";
        public String pesan = "";
        /** true bila mapping NIK->IHS tersimpan di satu_sehat_ihs_patient setelah pengecekan ini. */
        public boolean tersimpanLokal = false;
        /**
         * true bila SATUSEHAT punya resource Patient untuk NIK ini tapi tidak ada satu pun yang
         * ber-identifier ihs-number (Patient provisional). Bukan "tidak terdaftar": NIK-nya sudah
         * dipakai di server, jadi pembuatan Patient baru harus dibatalkan supaya tidak jadi ganda.
         */
        public boolean adaTanpaIhs = false;

        // ---- data dari resource Patient (kosong bila tidak ada di respons) ----
        public String nama = "";
        public String gender = "";
        public String tglLahir = "";
        /** Nilai identifier NIK persis seperti dibalikkan server — biasanya bertopeng "################". */
        public String nikServer = "";
        /** "Aktif"/"Tidak aktif"/"" bila field active tidak dikirim. */
        public String aktif = "";

        public boolean ada() {
            return ihs != null && !ihs.trim().equals("");
        }
    }
    // ==========================================

    // ========== VARIABEL ==========
    private String link = "", json = "";
    public String birthDate = "", province = "", provincename = "", city = "", cityname = "",
                  district = "", districtname = "", village = "", villagename = "", rt = "",
                  rw = "", line = "", postalCode = "", gender = "", noktp = "", idpasien = "",
                  maritalStatus = "", name = "", phone = "", email = "";
    private ApiSatuSehat api = new ApiSatuSehat();
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader dataPropinsi, dataKabupaten, dataKecamatan, dataKelurahan;
    private sekuel Sequel = new sekuel();
    private final Connection koneksi = koneksiDB.condb();

    // ========== KONSTRUKTOR ==========
    public SatuSehatCekNIK() {
        super();
        try {
            link = koneksiDB.URLFHIRSATUSEHAT();
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }

        try {
            dataPropinsi = new FileReader("./cache/propinsi.iyem");
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }

        try {
            dataKabupaten = new FileReader("./cache/kabupaten.iyem");
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }

        try {
            dataKecamatan = new FileReader("./cache/kecamatan.iyem");
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }

        try {
            dataKelurahan = new FileReader("./cache/kelurahan.iyem");
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    // ========== METHOD YANG SUDAH ADA (DARI KODE ANDA) ==========
    public void tampil(String cari) {
        try {
            birthDate = "";
            province = "";
            provincename = "";
            city = "";
            cityname = "";
            district = "";
            districtname = "";
            village = "";
            villagename = "";
            rt = "";
            rw = "";
            line = "";
            postalCode = "";
            gender = "";
            noktp = "";
            idpasien = "";
            maritalStatus = "";
            name = "";
            phone = "";
            email = "";
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : " + link + "/Patient?identifier=https://fhir.kemkes.go.id/id/nik|" + cari);
            json = api.getRest().exchange(link + "/Patient?identifier=https://fhir.kemkes.go.id/id/nik|" + cari, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("JSON : " + json);
            root = mapper.readTree(json);
            for (JsonNode list : root.path("entry")) {
                idpasien = list.path("resource").path("id").asText();
                noktp = cari;
                try {
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
                    requestEntity = new HttpEntity(headers);
                    System.out.println("URL : " + link + "/Patient/" + idpasien);
                    json = api.getRest().exchange(link + "/Patient/" + idpasien, HttpMethod.GET, requestEntity, String.class).getBody();
                    System.out.println("JSON : " + json);
                    root = mapper.readTree(json);
                    gender = root.path("gender").asText().toLowerCase().equals("male") ? "Laki-laki" : "Perempuan";
                    birthDate = root.path("birthDate").asText();
                    maritalStatus = root.path("maritalStatus").path("text").asText().toLowerCase().equals("married") ? "Menikah" : "Belum Menikah";
                    for (JsonNode listname : root.path("name")) {
                        name = listname.path("text").asText();
                    }
                    for (JsonNode listtelecom : root.path("telecom")) {
                        if (listtelecom.path("system").asText().equals("phone")) {
                            phone = listtelecom.path("value").asText();
                        } else if (listtelecom.path("system").asText().equals("email")) {
                            email = listtelecom.path("value").asText();
                        }
                    }
                    for (JsonNode listaddress : root.path("address")) {
                        line = listaddress.path("line").get(0).asText();
                        postalCode = listaddress.path("postalCode").asText();
                        for (JsonNode listextension : listaddress.path("extension")) {
                            for (JsonNode listextensionextension : listextension.path("extension")) {
                                if (listextensionextension.path("url").asText().equals("province")) {
                                    province = listextensionextension.path("valueCode").asText();
                                } else if (listextensionextension.path("url").asText().equals("city")) {
                                    city = listextensionextension.path("valueCode").asText();
                                } else if (listextensionextension.path("url").asText().equals("district")) {
                                    district = listextensionextension.path("valueCode").asText();
                                } else if (listextensionextension.path("url").asText().equals("village")) {
                                    village = listextensionextension.path("valueCode").asText();
                                } else if (listextensionextension.path("url").asText().equals("rt")) {
                                    rt = listextensionextension.path("valueCode").asText();
                                } else if (listextensionextension.path("url").asText().equals("rw")) {
                                    rw = listextensionextension.path("valueCode").asText();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                }
                response = mapper.readTree(dataKelurahan).path("kelurahan");
                for (JsonNode listkelurahan : response) {
                    if (listkelurahan.path("id").asText().toLowerCase().equals(village) && listkelurahan.path("id_kecamatan").asText().equals(district)) {
                        villagename = listkelurahan.path("nama").asText();
                    }
                }
                response = mapper.readTree(dataKecamatan).path("kecamatan");
                for (JsonNode listkcamatan : response) {
                    if (listkcamatan.path("id").asText().toLowerCase().equals(district) && listkcamatan.path("id_kabupaten").asText().equals(city)) {
                        districtname = listkcamatan.path("nama").asText();
                    }
                }
                response = mapper.readTree(dataKabupaten).path("kabupaten");
                for (JsonNode listkabupaten : response) {
                    if (listkabupaten.path("id").asText().toLowerCase().equals(city) && listkabupaten.path("id_propinsi").asText().equals(province)) {
                        cityname = listkabupaten.path("nama").asText();
                    }
                }
                response = mapper.readTree(dataPropinsi).path("propinsi");
                for (JsonNode listpropinsi : response) {
                    if (listpropinsi.path("id").asText().toLowerCase().equals(province)) {
                        provincename = listpropinsi.path("nama").asText();
                    }
                }
            }

            if (name.equals("")) {
                try {
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
                    requestEntity = new HttpEntity(headers);
                    System.out.println("URL : " + link + "/Patient/" + cari);
                    json = api.getRest().exchange(link + "/Patient/" + cari, HttpMethod.GET, requestEntity, String.class).getBody();
                    System.out.println("JSON : " + json);
                    root = mapper.readTree(json);
                    idpasien = cari;
                    gender = root.path("gender").asText().toLowerCase().equals("male") ? "Laki-laki" : "Perempuan";
                    birthDate = root.path("birthDate").asText();
                    maritalStatus = root.path("maritalStatus").path("text").asText().toLowerCase().equals("married") ? "Menikah" : "Belum Menikah";
                    for (JsonNode listname : root.path("name")) {
                        name = listname.path("text").asText();
                    }
                    for (JsonNode listtelecom : root.path("telecom")) {
                        if (listtelecom.path("system").asText().equals("phone")) {
                            phone = listtelecom.path("value").asText();
                        }
                        if (listtelecom.path("system").asText().equals("email")) {
                            email = listtelecom.path("value").asText();
                        }
                    }
                    for (JsonNode listnoktp : root.path("identifier")) {
                        if (listnoktp.path("system").asText().equals("https://fhir.kemkes.go.id/id/nik")) {
                            noktp = listnoktp.path("value").asText();
                        }
                    }
                    for (JsonNode listaddress : root.path("address")) {
                        line = listaddress.path("line").get(0).asText();
                        postalCode = listaddress.path("postalCode").asText();
                        for (JsonNode listextension : listaddress.path("extension")) {
                            for (JsonNode listextensionextension : listextension.path("extension")) {
                                if (listextensionextension.path("url").asText().equals("province")) {
                                    province = listextensionextension.path("valueCode").asText();
                                }
                                if (listextensionextension.path("url").asText().equals("city")) {
                                    city = listextensionextension.path("valueCode").asText();
                                }
                                if (listextensionextension.path("url").asText().equals("district")) {
                                    district = listextensionextension.path("valueCode").asText();
                                }
                                if (listextensionextension.path("url").asText().equals("village")) {
                                    village = listextensionextension.path("valueCode").asText();
                                }
                                if (listextensionextension.path("url").asText().equals("rt")) {
                                    rt = listextensionextension.path("valueCode").asText();
                                }
                                if (listextensionextension.path("url").asText().equals("rw")) {
                                    rw = listextensionextension.path("valueCode").asText();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                }
                response = mapper.readTree(dataKelurahan).path("kelurahan");
                for (JsonNode listkelurahan : response) {
                    if (listkelurahan.path("id").asText().toLowerCase().equals(village) && listkelurahan.path("id_kecamatan").asText().equals(district)) {
                        villagename = listkelurahan.path("nama").asText();
                    }
                }
                response = mapper.readTree(dataKecamatan).path("kecamatan");
                for (JsonNode listkcamatan : response) {
                    if (listkcamatan.path("id").asText().toLowerCase().equals(district) && listkcamatan.path("id_kabupaten").asText().equals(city)) {
                        districtname = listkcamatan.path("nama").asText();
                    }
                }
                response = mapper.readTree(dataKabupaten).path("kabupaten");
                for (JsonNode listkabupaten : response) {
                    if (listkabupaten.path("id").asText().toLowerCase().equals(city) && listkabupaten.path("id_propinsi").asText().equals(province)) {
                        cityname = listkabupaten.path("nama").asText();
                    }
                }
                response = mapper.readTree(dataPropinsi).path("propinsi");
                for (JsonNode listpropinsi : response) {
                    if (listpropinsi.path("id").asText().toLowerCase().equals(province)) {
                        provincename = listpropinsi.path("nama").asText();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

        if (name.equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Belum Ada data di Server Satu Sehat");
        }
    }

    public String tampilIDPasien(String cari) {
        idpasien = "";
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : " + link + "/Patient?identifier=https://fhir.kemkes.go.id/id/nik|" + cari);
            json = api.getRest().exchange(link + "/Patient?identifier=https://fhir.kemkes.go.id/id/nik|" + cari, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("JSON : " + json);
            root = mapper.readTree(json);
            for (JsonNode list : root.path("entry")) {
                idpasien = list.path("resource").path("id").asText();
            }
        } catch (Exception e) {
            idpasien = "";
            System.out.println("Notifikasi : " + e);
        }
        return idpasien;
    }

    public String tampilIDParktisi(String cari) {
        idpasien = "";
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
            requestEntity = new HttpEntity(headers);
            System.out.println("URL : " + link + "/Practitioner?identifier=https://fhir.kemkes.go.id/id/nik|" + cari);
            json = api.getRest().exchange(link + "/Practitioner?identifier=https://fhir.kemkes.go.id/id/nik|" + cari, HttpMethod.GET, requestEntity, String.class).getBody();
            System.out.println("JSON : " + json);
            root = mapper.readTree(json);
            response = root.path("entry");
            for (JsonNode list : response) {
                idpasien = list.path("resource").path("id").asText();
            }
        } catch (Exception e) {
            idpasien = "";
            System.out.println("Notifikasi : " + e);
        }
        return idpasien;
    }

    // ========== METHOD CEK IHS (DARI KODE TEMAN) ==========

    /**
     * Cari IHS number pasien berdasarkan NIK, sekaligus rawat cache lokal satu_sehat_ihs_patient.
     */
    public HasilIHS cekIHS(String nik) {
        HasilIHS hasil = new HasilIHS();
        nik = (nik == null) ? "" : nik.trim();

        // NIK wajib 16 digit numerik
        if (!nik.matches("\\d{16}")) {
            hasil.status = IHS_NIK_TIDAK_VALID;
            hasil.pesan = "NIK harus 16 digit angka.";
            System.out.println("Notifikasi cekIHS : NIK tidak valid = '" + nik + "'");
            return hasil;
        }

        JsonNode bundle;
        try {
            bundle = bundlePatientByNik(nik);
        } catch (Exception e) {
            hasil.status = IHS_GAGAL;
            hasil.pesan = "Gagal menghubungi SATUSEHAT. Mapping lokal dibiarkan apa adanya."
                    + "\n\nSebabnya:\n" + ringkasError(e);
            System.out.println("Notifikasi cekIHS (NIK=" + nik + ") : " + e);
            return hasil;
        }

        JsonNode patient = pilihPatientBerIhs(bundle);
        if (patient == null && adaEntryPatient(bundle)) {
            hasil.adaTanpaIhs = true;
            System.out.println("Notifikasi cekIHS : NIK=" + nik + " punya entry Patient tapi tidak ada yang "
                    + "ber-identifier ihs-number. Sengaja tidak dipakai supaya id Patient provisional "
                    + "tidak ter-cache, dan pembuatan Patient baru dibatalkan.");
        }

        String idServer = (patient == null) ? "" : patient.path("id").asText();
        String idCache = ambilCacheIhs(nik);

        if (idServer.equals("")) {
            if (!idCache.equals("")) {
                Sequel.queryu2("delete from satu_sehat_ihs_patient where nikpasien=?", 1, new String[]{nik});
                System.out.println("Notifikasi cekIHS : NIK=" + nik + " tidak ada di SATUSEHAT, "
                        + "mapping lokal usang (" + idCache + ") dihapus.");
                hasil.pesan = "Pasien tidak ditemukan di SATUSEHAT. Mapping lokal yang usang sudah dihapus.";
            } else {
                hasil.pesan = "Pasien tidak ditemukan di SATUSEHAT.";
            }
            hasil.status = IHS_TIDAK_DITEMUKAN;
            return hasil;
        }

        hasil.ihs = idServer;
        idpasien = idServer;
        isiDataPatient(hasil, patient);

        if (idCache.equals(idServer)) {
            hasil.status = IHS_CACHE_COCOK;
            hasil.tersimpanLokal = true;
            hasil.pesan = "IHS ditemukan dan cocok dengan data lokal.";
            return hasil;
        }

        hasil.status = idCache.equals("") ? IHS_DITEMUKAN : IHS_CACHE_DIPERBAIKI;
        hasil.tersimpanLokal = simpanCacheIhs(nik, idServer);
        if (!idCache.equals("")) {
            System.out.println("Notifikasi cekIHS : mapping diperbaiki. NIK=" + nik
                    + ", ID lama=" + idCache + ", ID benar=" + idServer);
        }
        hasil.pesan = hasil.tersimpanLokal
                ? (idCache.equals("") ? "IHS ditemukan dan disimpan ke data lokal."
                : "IHS ditemukan. Mapping lokal yang salah sudah diperbaiki.")
                : "IHS ditemukan, tetapi belum bisa disimpan lokal karena NIK ini belum ada "
                + "di data pasien. Simpan dulu data pasiennya, lalu cek ulang.";
        return hasil;
    }

    /**
     * Cari IHS; bila pasien memang belum terdaftar, daftarkan sekalian ke SATUSEHAT.
     */
    public HasilIHS cekIHSAtauBuat(String nik) {
        HasilIHS hasil = cekIHS(nik);
        if (!hasil.status.equals(IHS_TIDAK_DITEMUKAN)) {
            return hasil;
        }
        if (hasil.adaTanpaIhs) {
            hasil.pesan = "NIK ini sudah dipakai Patient provisional di SATUSEHAT (belum ber-IHS). "
                    + "Tidak dibuatkan Patient baru supaya tidak jadi data ganda.";
            return hasil;
        }
        return buatPatient((nik == null) ? "" : nik.trim(), hasil);
    }

    /**
     * Daftarkan pasien ke SATUSEHAT berdasarkan NIK.
     */
    public HasilIHS daftarkanPasien(String nik) {
        return buatPatient((nik == null) ? "" : nik.trim(), new HasilIHS());
    }

    // ========== METHOD PEMBANTU ==========

    private JsonNode bundlePatientByNik(String nik) throws Exception {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
        requestEntity = new HttpEntity(headers);
        String url = link + "/Patient?identifier=https://fhir.kemkes.go.id/id/nik|" + nik;
        System.out.println("URL : " + url);
        String json = api.getRest().exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
        System.out.println("JSON : " + json);
        return mapper.readTree(json);
    }

    private JsonNode pilihPatientBerIhs(JsonNode bundle) {
        if (bundle == null || !bundle.has("entry")) {
            return null;
        }
        for (JsonNode entry : bundle.path("entry")) {
            JsonNode resource = entry.path("resource");
            if (!resource.path("id").asText().equals("")) {
                // Cek apakah resource ini memiliki identifier ihs-number
                for (JsonNode ident : resource.path("identifier")) {
                    String system = ident.path("system").asText();
                    if (system != null && system.contains("/ihs-number")) {
                        return resource;
                    }
                }
            }
        }
        return null;
    }

    private boolean adaEntryPatient(JsonNode bundle) {
        if (bundle == null || !bundle.has("entry")) {
            return false;
        }
        for (JsonNode entry : bundle.path("entry")) {
            if (!entry.path("resource").path("id").asText().equals("")) {
                return true;
            }
        }
        return false;
    }

    static void isiDataPatient(HasilIHS hasil, JsonNode patient) {
        if (patient == null) {
            return;
        }
        for (JsonNode nama : patient.path("name")) {
            String teks = nama.path("text").asText();
            if (!teks.equals("")) {
                hasil.nama = teks;
                break;
            }
        }
        String gender = patient.path("gender").asText();
        if (gender.equalsIgnoreCase("male")) {
            hasil.gender = "Laki-laki";
        } else if (gender.equalsIgnoreCase("female")) {
            hasil.gender = "Perempuan";
        } else {
            hasil.gender = gender;
        }
        hasil.tglLahir = patient.path("birthDate").asText();
        if (patient.has("active")) {
            hasil.aktif = patient.path("active").asBoolean() ? "Aktif" : "Tidak aktif";
        }
        for (JsonNode ident : patient.path("identifier")) {
            if (ident.path("system").asText().endsWith("/nik")) {
                hasil.nikServer = ident.path("value").asText();
                break;
            }
        }
    }

    private String ambilCacheIhs(String nik) {
        String id = Sequel.cariIsi("select ihspasien from satu_sehat_ihs_patient where nikpasien=?", nik);
        return (id == null) ? "" : id.trim();
    }

    private boolean simpanCacheIhs(String nik, String ihs) {
        if (Sequel.cariInteger("select count(no_rkm_medis) from pasien where no_ktp=?", nik) <= 0) {
            System.out.println("Notifikasi cekIHS : NIK=" + nik + " belum ada di tabel pasien, "
                    + "mapping IHS tidak disimpan.");
            return false;
        }
        Sequel.queryu2("replace into satu_sehat_ihs_patient (nikpasien,ihspasien) values (?,?)",
                2, new String[]{nik, ihs});
        System.out.println("Notifikasi cekIHS : mapping disimpan. NIK=" + nik + ", IHS=" + ihs);
        return true;
    }

    // ========== METHOD BUAT PATIENT ==========

    private HasilIHS buatPatient(String nik, HasilIHS hasil) {
        if (!nik.matches("\\d{16}")) {
            hasil.status = IHS_NIK_TIDAK_VALID;
            hasil.pesan = "NIK harus 16 digit angka.";
            return hasil;
        }

        DataPasien d = ambilPasienLokal(nik);
        if (d == null) {
            hasil.status = IHS_DATA_BELUM_LENGKAP;
            hasil.pesan = "NIK ini belum ada di data pasien SIMRS, jadi tidak ada yang bisa didaftarkan.";
            System.out.println("Notifikasi buatPatient : NIK=" + nik + " tidak ada di tabel pasien.");
            return hasil;
        }

        String kurang = d.yangKurang();
        if (!kurang.equals("")) {
            hasil.status = IHS_DATA_BELUM_LENGKAP;
            hasil.pesan = "Data pasien belum layak dikirim: " + kurang + ". Lengkapi dulu di menu pasien.";
            System.out.println("Notifikasi buatPatient : NIK=" + nik + " ditolak, " + kurang);
            return hasil;
        }

        KodeWilayah w = petakanWilayah(d);
        if (!w.lengkap()) {
            hasil.status = IHS_DATA_BELUM_LENGKAP;
            hasil.pesan = "Alamat pasien tidak bisa dipetakan ke kode wilayah Kemendagri (" + w.gagalDi
                    + "). Betulkan dulu alamatnya, jangan dikira-kira.";
            System.out.println("Notifikasi buatPatient : NIK=" + nik + " gagal petakan wilayah - " + w.gagalDi);
            return hasil;
        }

        try {
            String id = postPatient(bodyPatient(mapper, nik, d, w));
            if (id.equals("")) {
                hasil.status = IHS_GAGAL_BUAT;
                hasil.pesan = "SATUSEHAT menerima kiriman tapi tidak mengembalikan IHS number.";
                return hasil;
            }
            hasil.ihs = id;
            idpasien = id;
            hasil.status = IHS_DIBUAT;
            hasil.nama = d.nama;
            hasil.gender = d.jk.equals("P") ? "Perempuan" : "Laki-laki";
            hasil.tglLahir = d.tglLahir;
            hasil.tersimpanLokal = simpanCacheIhs(nik, id);
            hasil.pesan = "Pasien berhasil didaftarkan ke SATUSEHAT, IHS " + id + ".";
            System.out.println("Notifikasi buatPatient : NIK=" + nik + " -> IHS=" + id);
            return hasil;
        } catch (org.springframework.web.client.HttpStatusCodeException e) {
            String badan = e.getResponseBodyAsString();
            hasil.status = IHS_GAGAL_BUAT;
            hasil.pesan = pesanPenolakan(badan, e.getStatusCode().value());
            System.out.println("Notifikasi buatPatient (NIK=" + nik + ") : " + e.getStatusCode()
                    + " - " + badan);
            return hasil;
        } catch (Exception e) {
            hasil.status = IHS_GAGAL_BUAT;
            hasil.pesan = "Gagal mendaftarkan pasien ke SATUSEHAT.\n\nSebabnya:\n" + ringkasError(e);
            System.out.println("Notifikasi buatPatient (NIK=" + nik + ") : " + e);
            return hasil;
        }
    }

    /**
     * Terjemahkan penolakan server jadi pesan yang bisa ditindaklanjuti petugas.
     */
    static String pesanPenolakan(String badan, int kode) {
        String b = (badan == null) ? "" : badan;
        if (b.contains("Found duplicate") && b.contains("Patient")) {
            return "NIK ini sudah punya Patient di SATUSEHAT, tetapi tidak muncul saat dicari by NIK. "
                    + "Cari manual memakai nama + tanggal lahir + jenis kelamin, jangan didaftarkan ulang.";
        }
        if (b.contains("Patient data exists")) {
            return "NIK terdaftar di Dukcapil tapi belum bisa dicari lewat NIK. Cari manual memakai "
                    + "nama + tanggal lahir + jenis kelamin, jangan didaftarkan ulang.";
        }
        return "SATUSEHAT menolak pendaftaran (HTTP " + kode + ").\n\nKata servernya:\n"
                + rincianPenolakan(b);
    }

    static String rincianPenolakan(String badan) {
        String b = (badan == null) ? "" : badan.trim();
        if (b.equals("")) {
            return "(server tidak mengirim keterangan apa pun)";
        }
        StringBuilder s = new StringBuilder();
        try {
            JsonNode akar = new ObjectMapper().readTree(b);
            for (JsonNode issue : akar.path("issue")) {
                String teks = issue.path("details").path("text").asText();
                if (teks.equals("")) {
                    teks = issue.path("diagnostics").asText();
                }
                if (teks.equals("")) {
                    teks = issue.path("code").asText();
                }
                if (!teks.equals("")) {
                    if (s.length() > 0) {
                        s.append("\n");
                    }
                    s.append("- ").append(teks);
                }
            }
        } catch (Exception e) {
            // Balasan bukan OperationOutcome
        }
        String hasil = (s.length() > 0) ? s.toString() : b;
        return hasil.length() > 600 ? hasil.substring(0, 600) + " ..." : hasil;
    }

    public static String ringkasError(Throwable e) {
        String pesan = (e == null || e.getMessage() == null) ? "" : e.getMessage().trim();
        String jenis = (e == null) ? "Error" : e.getClass().getSimpleName();
        if (pesan.length() > 300) {
            pesan = pesan.substring(0, 300) + " ...";
        }
        return pesan.equals("") ? jenis : (jenis + ": " + pesan);
    }

    // ========== DATA LOKAL ==========

    static class DataPasien {
        String nama = "", jk = "", tglLahir = "", alamat = "", telepon = "", email = "", sttsNikah = "";
        String namaProp = "", namaKab = "", namaKec = "", namaKel = "";
        String namaAsli = "";

        String yangKurang() {
            StringBuilder s = new StringBuilder();
            if (nama.equals("")) {
                s.append("nama pasien kosong");
            }
            if (!jk.equals("L") && !jk.equals("P")) {
                if (s.length() > 0) { s.append(", "); }
                s.append("jenis kelamin belum diisi");
            }
            if (!tglLahir.matches("\\d{4}-\\d{2}-\\d{2}") || tglLahir.startsWith("0000")) {
                if (s.length() > 0) { s.append(", "); }
                s.append("tanggal lahir tidak sah");
            }
            if (alamat.equals("")) {
                if (s.length() > 0) { s.append(", "); }
                s.append("alamat kosong");
            }
            return s.toString();
        }
    }

    private DataPasien ambilPasienLokal(String nik) {
        DataPasien d = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            p = koneksi.prepareStatement(
                    "select ifnull(p.nm_pasien,'') nm_pasien, ifnull(p.jk,'') jk, "
                    + "ifnull(date_format(p.tgl_lahir,'%Y-%m-%d'),'') tgl_lahir, "
                    + "ifnull(p.alamat,'') alamat, ifnull(p.no_tlp,'') no_tlp, ifnull(p.email,'') email, "
                    + "ifnull(p.stts_nikah,'') stts_nikah, ifnull(pr.nm_prop,'') nm_prop, "
                    + "ifnull(kb.nm_kab,'') nm_kab, ifnull(kc.nm_kec,'') nm_kec, ifnull(kl.nm_kel,'') nm_kel "
                    + "from pasien p "
                    + "left join propinsi pr on pr.kd_prop=p.kd_prop "
                    + "left join kabupaten kb on kb.kd_kab=p.kd_kab "
                    + "left join kecamatan kc on kc.kd_kec=p.kd_kec "
                    + "left join kelurahan kl on kl.kd_kel=p.kd_kel "
                    + "where p.no_ktp=? limit 1");
            p.setString(1, nik);
            r = p.executeQuery();
            if (r.next()) {
                d = new DataPasien();
                d.namaAsli = r.getString("nm_pasien");
                d.nama = namaUntukKirim(d.namaAsli);
                d.jk = r.getString("jk").trim().toUpperCase();
                d.tglLahir = r.getString("tgl_lahir").trim();
                d.alamat = r.getString("alamat").trim();
                d.telepon = r.getString("no_tlp").trim();
                d.email = r.getString("email").trim();
                d.sttsNikah = r.getString("stts_nikah").trim().toUpperCase();
                d.namaProp = r.getString("nm_prop");
                d.namaKab = r.getString("nm_kab");
                d.namaKec = r.getString("nm_kec");
                d.namaKel = r.getString("nm_kel");
            }
        } catch (Exception e) {
            System.out.println("Notifikasi ambilPasienLokal (NIK=" + nik + ") : " + e);
            d = null;
        } finally {
            tutup(r, p);
        }
        return d;
    }

    private static final java.util.Set<String> SAPAAN = new java.util.HashSet<String>(
            java.util.Arrays.asList("NY", "TN", "AN", "NN", "SDR", "SDRI", "BY"));

    static String namaUntukKirim(String namaAsli) {
        String asli = (namaAsli == null) ? "" : namaAsli.trim();
        if (!namaMenunjukIbu(asli)) {
            return bersihkanGelar(asli);
        }
        String rapi = rapikanTandaBaca(buangPenandaKurung(asli));
        return rapi.equals("") ? asli : rapi;
    }

    private static String buangPenandaKurung(String nama) {
        String s = (nama == null) ? "" : nama.trim();
        s = s.replaceAll("\\s*\\([^)]*\\)", " ");
        int buka = s.indexOf('(');
        if (buka >= 0) {
            s = s.substring(0, buka);
        }
        return s.replaceAll("\\s+", " ").trim();
    }

    private static String rapikanTandaBaca(String nama) {
        return nama.replaceAll("^[\\s.,]+", "").replaceAll("[\\s.,]+$", "").trim();
    }

    static String bersihkanGelar(String nama) {
        String asli = (nama == null) ? "" : nama.trim();
        String hasil = rapikanTandaBaca(buangPenandaKurung(asli));
        for (int i = 0; i < 3; i++) {
            String sesudah = rapikanTandaBaca(kupasSapaan(hasil));
            if (sesudah.equals("") || sesudah.equals(hasil)) {
                break;
            }
            hasil = sesudah;
        }
        hasil = rapikanTandaBaca(hasil);
        return hasil.equals("") ? asli : hasil;
    }

    static boolean namaMenunjukIbu(String namaAsli) {
        return normalNama(namaAsli).matches("^BY[\\s.,]+NY[\\s.,]+.*");
    }

    private static String kupasSapaan(String nama) {
        int koma = nama.lastIndexOf(',');
        if (koma > 0 && sapaanMurni(nama.substring(koma + 1))) {
            return nama.substring(0, koma).trim();
        }
        int spasi = nama.lastIndexOf(' ');
        if (spasi > 0 && sapaanMurni(nama.substring(spasi + 1))) {
            return nama.substring(0, spasi).trim();
        }
        return nama.replaceFirst("(?i)^(NY|TN|NN|AN|SDR|SDRI|BY)\\.?[\\s,]+", "").trim();
    }

    private static boolean sapaanMurni(String bagian) {
        String s = normalNama(bagian).replace(".", "");
        s = s.replaceAll("\\s*\\([^)]*\\)\\s*", " ").trim();
        return SAPAAN.contains(s);
    }

    private static String normalNama(String s) {
        return (s == null) ? "" : s.trim().toUpperCase();
    }

    private void tutup(ResultSet r, PreparedStatement p) {
        try { if (r != null) { r.close(); } } catch (Exception e) { System.out.println("Notif : " + e); }
        try { if (p != null) { p.close(); } } catch (Exception e) { System.out.println("Notif : " + e); }
    }

    // ========== KODE WILAYAH KEMENDAGRI ==========

    static class KodeWilayah {
        String provinsi = "", kabupaten = "", kecamatan = "", kelurahan = "", gagalDi = "";

        boolean lengkap() {
            return !kelurahan.equals("");
        }
    }

    // Indeks wilayah statis
    private static JsonNode idxPropinsi = null;
    private static JsonNode idxKabupaten = null;
    private static JsonNode idxKecamatan = null;
    private static JsonNode idxKelurahan = null;

    private static boolean siapkanIndeksWilayah() {
        if (idxPropinsi != null && idxKabupaten != null && idxKecamatan != null && idxKelurahan != null) {
            return true;
        }
        try {
            if (idxPropinsi == null) {
                idxPropinsi = new ObjectMapper().readTree(new FileReader("./cache/propinsi.iyem"));
            }
            if (idxKabupaten == null) {
                idxKabupaten = new ObjectMapper().readTree(new FileReader("./cache/kabupaten.iyem"));
            }
            if (idxKecamatan == null) {
                idxKecamatan = new ObjectMapper().readTree(new FileReader("./cache/kecamatan.iyem"));
            }
            if (idxKelurahan == null) {
                idxKelurahan = new ObjectMapper().readTree(new FileReader("./cache/kelurahan.iyem"));
            }
            return true;
        } catch (Exception e) {
            System.out.println("Notifikasi siapkanIndeksWilayah : " + e);
            return false;
        }
    }

    static KodeWilayah petakanWilayah(DataPasien d) {
        KodeWilayah w = new KodeWilayah();
        if (!siapkanIndeksWilayah()) {
            w.gagalDi = "berkas ./cache/*.iyem tidak terbaca";
            return w;
        }
        w.provinsi = kodeWilayah(idxPropinsi, "", new String[]{aliasPropinsi(d.namaProp)});
        if (w.provinsi.equals("")) {
            w.gagalDi = "propinsi '" + d.namaProp.trim() + "' tidak dikenali";
            return w;
        }
        for (String calon : kandidatKabupaten(d.namaKab)) {
            String kodeKab = kodeWilayah(idxKabupaten, w.provinsi, new String[]{calon});
            if (kodeKab.equals("")) {
                continue;
            }
            if (w.kabupaten.equals("")) {
                w.kabupaten = kodeKab;
            }
            String kodeKec = kodeWilayah(idxKecamatan, kodeKab, new String[]{d.namaKec});
            if (!kodeKec.equals("")) {
                w.kabupaten = kodeKab;
                w.kecamatan = kodeKec;
                break;
            }
        }
        if (w.kabupaten.equals("")) {
            w.gagalDi = "kabupaten/kota '" + d.namaKab.trim() + "' tidak dikenali";
            return w;
        }
        if (w.kecamatan.equals("")) {
            w.gagalDi = "kecamatan '" + d.namaKec.trim() + "' tidak ada di bawah "
                    + d.namaKab.trim();
            return w;
        }
        w.kelurahan = kodeWilayah(idxKelurahan, w.kecamatan, new String[]{d.namaKel});
        if (w.kelurahan.equals("")) {
            w.gagalDi = "kelurahan/desa '" + d.namaKel.trim() + "' tidak ada di bawah kecamatan "
                    + d.namaKec.trim();
        }
        return w;
    }

    private static final String WILAYAH_KEMBAR = "KEMBAR";

    private static String kodeWilayah(JsonNode indeks, String kodeInduk, String[] namaCari) {
        if (indeks == null || namaCari == null || namaCari.length == 0) {
            return "";
        }
        String kodeTemu = "";
        for (String nama : namaCari) {
            String s = (nama == null) ? "" : normalNama(nama);
            if (s.equals("")) {
                continue;
            }
            for (JsonNode node : indeks.path(indeks.path("root").asText())) {
                String namaNode = normalNama(node.path("nama").asText());
                if (!s.equals(namaNode)) {
                    continue;
                }
                String kodeIndukNode = node.path("id_" + indeks.path("parent").asText()).asText();
                if (!kodeInduk.equals("")) {
                    if (!kodeIndukNode.equals(kodeInduk)) {
                        continue;
                    }
                }
                String kode = node.path("id").asText();
                if (kodeTemu.equals("")) {
                    kodeTemu = kode;
                } else if (!kodeTemu.equals(kode)) {
                    return WILAYAH_KEMBAR;
                }
            }
            if (!kodeTemu.equals("")) {
                return kodeTemu;
            }
        }
        return kodeTemu;
    }

    private static String[] kandidatKabupaten(String nama) {
        String s = normalNama(nama);
        if (s.equals("")) {
            return new String[]{""};
        }
        if (s.startsWith("KABUPATEN ")) {
            return new String[]{s.substring(9).trim(), s};
        }
        if (s.startsWith("KOTA ")) {
            return new String[]{s.substring(4).trim(), s};
        }
        // "KABUPATEN/KOTA SEMARANG" -> coba "SEMARANG" dulu, baru yang panjang
        String[] potong = s.split("[/ ]+");
        if (potong.length > 1 && (potong[0].equals("KABUPATEN") || potong[0].equals("KOTA"))) {
            return new String[]{potong[1], s};
        }
        return new String[]{s};
    }

    private static String aliasPropinsi(String nama) {
        String s = normalNama(nama);
        if (s.equals("")) {
            return "";
        }
        // Beberapa nama propinsi di SIMRS tidak sesuai format Kemendagri
        if (s.equals("JAWA TENGAH")) {
            return "JAWA TENGAH";
        }
        if (s.equals("JAWA TIMUR")) {
            return "JAWA TIMUR";
        }
        if (s.equals("JAWA BARAT")) {
            return "JAWA BARAT";
        }
        if (s.equals("DKI JAKARTA")) {
            return "DKI JAKARTA";
        }
        if (s.equals("BANTEN")) {
            return "BANTEN";
        }
        if (s.equals("SUMATERA UTARA")) {
            return "SUMATERA UTARA";
        }
        if (s.equals("SUMATERA SELATAN")) {
            return "SUMATERA SELATAN";
        }
        if (s.equals("SUMATERA BARAT")) {
            return "SUMATERA BARAT";
        }
        if (s.equals("LAMPUNG")) {
            return "LAMPUNG";
        }
        if (s.equals("KALIMANTAN TIMUR")) {
            return "KALIMANTAN TIMUR";
        }
        if (s.equals("KALIMANTAN SELATAN")) {
            return "KALIMANTAN SELATAN";
        }
        if (s.equals("KALIMANTAN BARAT")) {
            return "KALIMANTAN BARAT";
        }
        if (s.equals("KALIMANTAN TENGAH")) {
            return "KALIMANTAN TENGAH";
        }
        if (s.equals("SULAWESI SELATAN")) {
            return "SULAWESI SELATAN";
        }
        if (s.equals("SULAWESI UTARA")) {
            return "SULAWESI UTARA";
        }
        if (s.equals("SULAWESI TENGAH")) {
            return "SULAWESI TENGAH";
        }
        if (s.equals("SULAWESI TENGGARA")) {
            return "SULAWESI TENGGARA";
        }
        if (s.equals("BALI")) {
            return "BALI";
        }
        if (s.equals("NUSA TENGGARA BARAT")) {
            return "NUSA TENGGARA BARAT";
        }
        if (s.equals("NUSA TENGGARA TIMUR")) {
            return "NUSA TENGGARA TIMUR";
        }
        if (s.equals("MALUKU")) {
            return "MALUKU";
        }
        if (s.equals("MALUKU UTARA")) {
            return "MALUKU UTARA";
        }
        if (s.equals("PAPUA")) {
            return "PAPUA";
        }
        if (s.equals("PAPUA BARAT")) {
            return "PAPUA BARAT";
        }
        return s;
    }

    // ========== POST PATIENT KE SATUSEHAT ==========

    private String postPatient(ObjectNode body) throws Exception {
    headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", "Bearer " + api.TokenSatuSehat());
    requestEntity = new HttpEntity(body, headers);
    String url = link + "/Patient";
    System.out.println("URL POST : " + url);
    System.out.println("BODY : " + body.toString());  // <-- PERBAIKAN
    ResponseEntity<String> responseEntity = api.getRest().exchange(url, HttpMethod.POST, requestEntity, String.class);
    String responseBody = responseEntity.getBody();
    System.out.println("RESPONSE : " + responseBody);
    JsonNode responseNode = mapper.readTree(responseBody);
    String id = responseNode.path("id").asText();
    return id != null ? id : "";
}

    private ObjectNode bodyPatient(ObjectMapper mapper, String nik, DataPasien d, KodeWilayah w) {
        ObjectNode patient = mapper.createObjectNode();
        patient.put("resourceType", "Patient");

        // Identifier NIK
        ObjectNode identifier = mapper.createObjectNode();
        identifier.put("use", "official");
        identifier.put("system", "https://fhir.kemkes.go.id/id/nik");
        identifier.put("value", nik);
        patient.set("identifier", mapper.createArrayNode().add(identifier));

        // Nama
        ObjectNode name = mapper.createObjectNode();
        name.put("use", "official");
        name.put("text", d.nama);
        patient.set("name", mapper.createArrayNode().add(name));

        // Gender
        patient.put("gender", d.jk.equals("P") ? "female" : "male");

        // BirthDate
        patient.put("birthDate", d.tglLahir);

        // MaritalStatus
        if (!d.sttsNikah.equals("")) {
            ObjectNode marital = mapper.createObjectNode();
            ObjectNode coding = mapper.createObjectNode();
            String statusCode = d.sttsNikah.equals("MENIKAH") ? "M" : "U";
            coding.put("system", "http://terminology.hl7.org/CodeSystem/v3-MaritalStatus");
            coding.put("code", statusCode);
            coding.put("display", d.sttsNikah);
            marital.set("coding", mapper.createArrayNode().add(coding));
            marital.put("text", d.sttsNikah);
            patient.set("maritalStatus", marital);
        }

        // Telecom
        if (!d.telepon.equals("") || !d.email.equals("")) {
            ObjectNode telecom = mapper.createObjectNode();
            if (!d.telepon.equals("")) {
                telecom.put("system", "phone");
                telecom.put("value", d.telepon);
                telecom.put("use", "home");
                patient.set("telecom", mapper.createArrayNode().add(telecom));
            }
            if (!d.email.equals("")) {
                ObjectNode emailNode = mapper.createObjectNode();
                emailNode.put("system", "email");
                emailNode.put("value", d.email);
                emailNode.put("use", "home");
                patient.withArray("telecom").add(emailNode);
            }
        }

        // Address
        ObjectNode address = mapper.createObjectNode();
        address.put("use", "home");
        address.set("line", mapper.createArrayNode().add(d.alamat));
        if (!w.kelurahan.equals("")) {
            address.put("city", w.kabupaten);
            address.put("district", w.kecamatan);
            address.put("postalCode", "");
        }

        // Extension untuk kode wilayah
        ObjectNode extension = mapper.createObjectNode();
        extension.put("url", "http://fhir.kemkes.go.id/StructureDefinition/address-extension");

        ObjectNode provinceExt = mapper.createObjectNode();
        provinceExt.put("url", "province");
        provinceExt.put("valueCode", w.provinsi);
        extension.withArray("extension").add(provinceExt);

        ObjectNode cityExt = mapper.createObjectNode();
        cityExt.put("url", "city");
        cityExt.put("valueCode", w.kabupaten);
        extension.withArray("extension").add(cityExt);

        ObjectNode districtExt = mapper.createObjectNode();
        districtExt.put("url", "district");
        districtExt.put("valueCode", w.kecamatan);
        extension.withArray("extension").add(districtExt);

        ObjectNode villageExt = mapper.createObjectNode();
        villageExt.put("url", "village");
        villageExt.put("valueCode", w.kelurahan);
        extension.withArray("extension").add(villageExt);

        address.withArray("extension").add(extension);
        patient.set("address", mapper.createArrayNode().add(address));

        // Active
        patient.put("active", true);

        return patient;
    }
}
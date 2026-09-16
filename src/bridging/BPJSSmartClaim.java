/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import simrskhanza.DlgReg;

/**
 * tglRencanaKunjungan
 *
 * @author perpustakaan
 */
public final class BPJSSmartClaim extends javax.swing.JDialog {

    private DefaultTableModel tabMode, tabModeInternal, tabModeListPengajuan, tabModeListFP, TabModeListPulang, TabModeListSEP, TabModeDiagnosaPasien, TabModeTindakanPasien, TabModeLabPasien, TabModeRadPasien, TabModeResume, TabModeResumeRanap, TabModeLapOP, TabModeResep, TabModeTebusObat, TabModeAlergi, TabModeLP, TabModeLPRanap, TabModeAlkes, TabModePract;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, pspenyakit, psPract, psPractRanap, psdiagnosapasien, psrsmpasienRanap, pstindakanpasien, pslabpasien, psradpasien, psrsmpasien, pslapop, psresep, pstebus, psalergi, psLP, psLPRanap, psalkes;
    private ResultSet rs, rs1, rspenyakit;
    private int i = 0, pilihan = 1, reply = 0, tab = 0, kuota = 0, countsephariini = 0;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private ApiBPJSSmartClaim api = new ApiBPJSSmartClaim();
    private boolean statusfinger = false, statuspulangranap = false;
    private BPJSCekReferensiFaskes faskes = new BPJSCekReferensiFaskes(null, false);
    private BPJSCekReferensiPenyakit penyakit = new BPJSCekReferensiPenyakit(null, false);
    private BPJSCekReferensiPoli poli = new BPJSCekReferensiPoli(null, false);
    private BPJSCekNoKartu cekViaBPJSKartu = new BPJSCekNoKartu();
    private BPJSCekReferensiDokterDPJP dokter = new BPJSCekReferensiDokterDPJP(null, false);
    private BPJSSuratKontrol skdp = new BPJSSuratKontrol(null, false);
    private BPJSSPRI skdp2 = new BPJSSPRI(null, false);
    private DlgReg registrasi = new DlgReg(null, false);
    private BPJSCekReferensiPropinsi propinsi = new BPJSCekReferensiPropinsi(null, false);
    private BPJSCekReferensiKabupaten kabupaten = new BPJSCekReferensiKabupaten(null, false);
    private BPJSCekReferensiKecamatan kecamatan = new BPJSCekReferensiKecamatan(null, false);
    private BPJSCekRiwayatRujukanTerakhir rujukanterakhir = new BPJSCekRiwayatRujukanTerakhir(null, false);
    private String prb = "", no_peserta = "", urlaplikasi = "", urlfinger = "", userfinger = "", passfinger = "", link = "", requestJson, jsonOrganization, jsonPatient, jsonPractitioner, jsonComposititon, jsonCondition, jsonDevice, jsonDiagnosticReport, jsonEncounter, jsonMedicationRequest,
            jsonMrBundle, jsonProcedure, URL = "", query = "", utc = "", user = "", kddokter = "", tglkkl = "0000-00-00", penunjang = "", kodedokterreg = "", kodepolireg = "",
            jammulai = "", jamselesai = "", datajam = "", jeniskunjungan = "", noreferensi = "", hari = "", nomorreg = "", keterangansephariini = "", alamatinstansi = "", kabupateninstansi = "", propinsiinstansi = "", notelpinstansi = "", emailinstansi = "",
            idPasien = "", noMR = "", nokaBPJS = "", nikPasien = "", maritalStatus = "", noTelpPasien = "";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private BPJSCekHistoriPelayanan historiPelayanan = new BPJSCekHistoriPelayanan(null, false);
    private Calendar cal = Calendar.getInstance();
    private int day = cal.get(Calendar.DAY_OF_WEEK);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date parsedDate;
    private SatuSehatCekNIK cekViaSatuSehat = new SatuSehatCekNIK();

    /**
     * Creates new form DlgRujuk
     *
     * @param parent
     * @param modal
     */
    public BPJSSmartClaim(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);

        TabModeDiagnosaPasien = new DefaultTableModel(null, new Object[]{
            "Tgl.Rawat", "No.Rawat", "No.R.M.", "Nama Pasien", "JK", "Kode ICD-10", "Nama Penyakit",
            "Status", "Kasus", "Prioritas", "Status daftar", "Status Poli", "Kode SNOMED", "SNOMED Display", "SNOMED System"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbDiagnosaPasien.setModel(TabModeDiagnosaPasien);
        tbDiagnosaPasien.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbDiagnosaPasien.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbDiagnosaPasien.getColumnModel().getColumn(i);
            if (i == 5) {
                column.setPreferredWidth(50);
            } else if (i == 6) {
                column.setPreferredWidth(300);
            } else if (i == 7) {
                column.setPreferredWidth(75);
            } else {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDiagnosaPasien.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeTindakanPasien = new DefaultTableModel(null, new Object[]{
            "Tgl.Rawat", "No.Rawat", "No.R.M.", "Nama Pasien", "Kode Tindakan", "Nama Tindakan", "Kode Dokter", "nmpoli", "", "", "Kode SNOMED", "SNOMED Display", "SNOMED System"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbTindakanPasien.setModel(TabModeTindakanPasien);
        tbTindakanPasien.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbTindakanPasien.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 10; i++) {
            TableColumn column = tbTindakanPasien.getColumnModel().getColumn(i);
            if (i == 4) {
                column.setPreferredWidth(50);
            } else if (i == 5) {
                column.setPreferredWidth(300);
            } else if (i == 7) {
                column.setPreferredWidth(75);
            } else {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbTindakanPasien.setDefaultRenderer(Object.class, new WarnaTable());

        TabModePract = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "Kd Dokter", "Nama Dokter", "No. KTP", "JK", "Tgl. Lahir", "Alamat",
            "Tempat Lahir", "Gol. Darah", "Agama", "Status Nikah", "Kode Spesialis", "Alumni",
            "No. Ijin Praktek", "Status", "Email", "No. Telp"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbPractitioner.setModel(TabModePract);
        tbPractitioner.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPractitioner.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbPractitioner.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(50);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(50);
            } else if (i == 5) {
                column.setPreferredWidth(100);
            } else if (i == 6) {
                column.setPreferredWidth(200);
            } else if (i == 7) {
                column.setPreferredWidth(100);
            } else if (i == 8) {
                column.setPreferredWidth(75);
            } else if (i == 9) {
                column.setPreferredWidth(75);
            } else if (i == 10) {
                column.setPreferredWidth(100);
            } else if (i == 11) {
                column.setPreferredWidth(100);
            } else if (i == 12) {
                column.setPreferredWidth(150);
            } else if (i == 13) {
                column.setPreferredWidth(150);
            } else if (i == 14) {
                column.setPreferredWidth(100);
            } else if (i == 15) {
                column.setPreferredWidth(150);
            } else if (i == 16) {
                column.setPreferredWidth(120);
            }
        }
        tbPractitioner.setDefaultRenderer(Object.class, new WarnaTable());

//        rs.getString("tgl_periksa"), rs.getString("jam"), rs.getString("nm_perawatan"), rs.getString("Pemeriksaan"), rs.getString("snomed"), rs.getString("loinc"),
//                        rs.getString("subsnomedcode"), rs.getString("subloinccode"),});
        TabModeLabPasien = new DefaultTableModel(null, new Object[]{
            "Tgl.Rawat", "Jam", "Nama Perawatan", "Pemeriksaan", "Snomed", "Loinc", "SubSnomed",
            "SubLoinc", "Nilai", "Nilai Rujukan", "Keterangan", "KdDokter", "NmDokter"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbLabPasien.setModel(TabModeLabPasien);
        tbLabPasien.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbLabPasien.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbLabPasien.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else if (i == 1) {
                column.setPreferredWidth(50);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setPreferredWidth(150);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(150);
            } else {
                column.setPreferredWidth(150);
            }
        }
        tbLabPasien.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeRadPasien = new DefaultTableModel(null, new Object[]{
            "Tgl.tgl_periksa", "Jam", "Nama Perawatan", "Pemeriksaan", "Snomed", "Loinc", "klinis",
            "kesan", "Dokter"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbRadiologiPasien.setModel(TabModeRadPasien);
        tbRadiologiPasien.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbRadiologiPasien.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbRadiologiPasien.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else if (i == 1) {
                column.setPreferredWidth(50);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setPreferredWidth(150);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(150);
            } else {
                column.setPreferredWidth(150);
            }
        }
        tbRadiologiPasien.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeResume = new DefaultTableModel(null, new Object[]{
            "Tgl.tgl_periksa", "Jam", "Keluhan Utama", "Kd Diagnosa", "Diagnosa", "Obat Pulang", "Kondisi Pulang",
            "Kd DPJP", "Nama DPJP", "No KTP", "Tgl. Lahir", "Alamat"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbResumePasien.setModel(TabModeResume);
        tbResumePasien.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbResumePasien.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbResumePasien.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else if (i == 1) {
                column.setPreferredWidth(50);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setPreferredWidth(150);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(150);
            } else if (i == 8) {
                column.setPreferredWidth(150);
            } else if (i == 9) {
                column.setPreferredWidth(150);
            } else if (i == 10) {
                column.setPreferredWidth(150);
            } else if (i == 11) {
                column.setPreferredWidth(150);
            }
        }
        tbResumePasien.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeLapOP = new DefaultTableModel(null, new Object[]{
            "Tanggal", "Diag PreOP", "Diag PostOP", "Selesai OP", "Laporan OP",
            "Kd DPJP", "Nama DPJP"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbLaporanOP.setModel(TabModeLapOP);
        tbLaporanOP.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbLaporanOP.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbLaporanOP.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else if (i == 1) {
                column.setPreferredWidth(50);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setPreferredWidth(150);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            }
        }
        tbLaporanOP.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeResep = new DefaultTableModel(null, new Object[]{
            "Tanggal Registrasi", "Nama", "ID Encounter", "KFA Code", "KFA System", "Kode Barang", "KFA Display", "Form Code", "Form System", "Form Display",
            "Route Code", "Route System", "Route Display", "Denominator Code", "Denominator System", "Tanggal & Jam Resep", "Jumlah",
            "ID Medication", "Aturan Pakai", "No.Resep", "ID Medication Request", "No.Racik", "Status"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class};

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbResepDokter.setModel(TabModeResep);
        tbResepDokter.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbResepDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 23; i++) {
            TableColumn column = tbResepDokter.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(150);
            } else if (i == 1) {
                column.setPreferredWidth(150);
            } else if (i == 2) {
                column.setPreferredWidth(105);
            } else if (i == 3) {
                column.setPreferredWidth(70);
            } else if (i == 4) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setPreferredWidth(110);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(110);
            } else if (i == 8) {
                column.setPreferredWidth(210);
            } else if (i == 9) {
                column.setPreferredWidth(80);
            } else if (i == 10) {
                column.setPreferredWidth(200);
            } else if (i == 11) {
                column.setPreferredWidth(85);
            } else if (i == 12) {
                column.setPreferredWidth(150);
            } else if (i == 13) {
                column.setPreferredWidth(80);
            } else if (i == 14) {
                column.setPreferredWidth(200);
            } else if (i == 15) {
                column.setPreferredWidth(150);
            } else if (i == 16) {
                column.setPreferredWidth(80);
            } else if (i == 17) {
                column.setPreferredWidth(200);
            } else if (i == 18) {
                column.setPreferredWidth(150);
            } else if (i == 19) {
                column.setPreferredWidth(100);
            } else if (i == 20) {
                column.setPreferredWidth(150);
            } else if (i == 21) {
                column.setPreferredWidth(115);
            } else if (i == 22) {
                column.setPreferredWidth(115);
            }
        }
        tbResepDokter.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeTebusObat = new DefaultTableModel(null, new Object[]{
            "Tanggal Registrasi", "Nama", "ID Encounter", "KFA Code", "KFA System", "Kode Barang", "KFA Display", "Form Code", "Form System", "Form Display",
            "Route Code", "Route System", "Route Display", "Denominator Code", "Denominator System", "Tanggal & Jam Resep", "Jumlah",
            "ID Medication", "Aturan Pakai", "No.Resep", "ID Medication Dispense", "No.Batch", "No.Faktur", "Tgl.Validasi", "Status",
            "ID Location", "Asal Depo", "ID MedReq"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class};

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbTebusObat.setModel(TabModeTebusObat);
        tbTebusObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbTebusObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 28; i++) {
            TableColumn column = tbTebusObat.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(150);
            } else if (i == 1) {
                column.setPreferredWidth(150);
            } else if (i == 2) {
                column.setPreferredWidth(105);
            } else if (i == 3) {
                column.setPreferredWidth(70);
            } else if (i == 4) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setPreferredWidth(110);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(110);
            } else if (i == 8) {
                column.setPreferredWidth(210);
            } else if (i == 9) {
                column.setPreferredWidth(80);
            } else if (i == 10) {
                column.setPreferredWidth(200);
            } else if (i == 11) {
                column.setPreferredWidth(85);
            } else if (i == 12) {
                column.setPreferredWidth(150);
            } else if (i == 13) {
                column.setPreferredWidth(80);
            } else if (i == 14) {
                column.setPreferredWidth(200);
            } else if (i == 15) {
                column.setPreferredWidth(150);
            } else if (i == 16) {
                column.setPreferredWidth(80);
            } else if (i == 17) {
                column.setPreferredWidth(200);
            } else if (i == 18) {
                column.setPreferredWidth(150);
            } else if (i == 19) {
                column.setPreferredWidth(100);
            } else if (i == 20) {
                column.setPreferredWidth(150);
            } else if (i == 21) {
                column.setPreferredWidth(115);
            } else if (i == 22) {
                column.setPreferredWidth(45);
            } else if (i == 23) {
                column.setPreferredWidth(210);
            } else if (i == 24) {
                column.setPreferredWidth(160);
            } else if (i == 25) {
                column.setPreferredWidth(110);
            } else if (i == 26) {
                column.setPreferredWidth(210);
            } else if (i == 27) {
                column.setPreferredWidth(210);
            }
        }
        tbTebusObat.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeAlkes = new DefaultTableModel(null, new Object[]{
            "Tanggal Registrasi", "Nama", "KFA Code", "KFA System", "Kode Barang",
            "Nama Barang", "Jumlah", "Tgl.Validasi", "Asal Depo", "KFA Display", "Form Code", "Form System", "Form Display",
            "Route Code", "Route System", "Route Display", "Denominator System",
            "ID Medication", "No.Batch", "No.Faktur", "Manufacturer", "Model", "Lot Number", "Manufacture Date", "Expiration Date"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class};

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbAlkes.setModel(TabModeAlkes);
        tbAlkes.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbAlkes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 20; i++) {
            TableColumn column = tbAlkes.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(150);
            } else if (i == 1) {
                column.setPreferredWidth(150);
            } else if (i == 2) {
                column.setPreferredWidth(105);
            } else if (i == 3) {
                column.setPreferredWidth(70);
            } else if (i == 4) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setPreferredWidth(110);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(110);
            } else if (i == 8) {
                column.setPreferredWidth(210);
            } else if (i == 9) {
                column.setPreferredWidth(80);
            } else if (i == 10) {
                column.setPreferredWidth(200);
            } else if (i == 11) {
                column.setPreferredWidth(85);
            } else if (i == 12) {
                column.setPreferredWidth(150);
            } else if (i == 13) {
                column.setPreferredWidth(80);
            } else if (i == 14) {
                column.setPreferredWidth(200);
            } else if (i == 15) {
                column.setPreferredWidth(150);
            } else if (i == 16) {
                column.setPreferredWidth(80);
            } else if (i == 17) {
                column.setPreferredWidth(200);
            } else if (i == 18) {
                column.setPreferredWidth(150);
            } else if (i == 19) {
                column.setPreferredWidth(150);
            }
        }
        tbAlkes.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeAlergi = new DefaultTableModel(null, new Object[]{
            "Nama Dokter", "No.KTP Dokter", "Kode Poli", "Nama Poli/Unit", "ID Lokasi Unit", "Stts Rawat", "Stts Lanjut",
            "Tanggal Entry", "ID Encounter", "KATEGORI", "KODE", "SYSTEM", "DISPLAY", "NOTE", "ID ALLERGY"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbAlergi.setModel(TabModeAlergi);
        tbAlergi.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbAlergi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbAlergi.getColumnModel().getColumn(i);
            if (i == 7) {
                column.setPreferredWidth(100);
            } else if (i == 8) {
                column.setPreferredWidth(50);
            } else if (i == 9) {
                column.setPreferredWidth(150);
            } else if (i == 10) {
                column.setPreferredWidth(150);
            } else if (i == 11) {
                column.setPreferredWidth(150);
            } else if (i == 12) {
                column.setPreferredWidth(150);
            } else if (i == 13) {
                column.setPreferredWidth(150);
            } else if (i == 14) {
                column.setPreferredWidth(150);
            }
        }
        tbAlergi.setDefaultRenderer(Object.class, new WarnaTable());

        TabModeLP = new DefaultTableModel(null, new Object[]{
            "No. RM", "Tgl. Masuk", "Jam Masuk", "Tgl. Keluar", "Jam Keluar"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbLamaPelayanan.setModel(TabModeLP);
        tbLamaPelayanan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbLamaPelayanan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbLamaPelayanan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else if (i == 1) {
                column.setPreferredWidth(50);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(150);
            }
        }
        tbLamaPelayanan.setDefaultRenderer(Object.class, new WarnaTable());

        if (koneksiDB.CARICEPAT().equals("aktif")) {

        }

        try {
            tampilOrganisasi();
        } catch (Exception e) {
            System.out.println(e);
        }

        HTMLEditorKit kit = new HTMLEditorKit();
        TJsonBody.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        Document doc = kit.createDefaultDocument();
        TJsonBody.setDocument(doc);
        TJsonBody.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NoKaPasien = new widget.TextBox();
        jLabel8 = new widget.Label();
        Scroll7 = new widget.ScrollPane();
        tbDiagnosaPasien = new widget.Table();
        jLabel12 = new widget.Label();
        Scroll8 = new widget.ScrollPane();
        tbTindakanPasien = new widget.Table();
        Scroll9 = new widget.ScrollPane();
        tbLabPasien = new widget.Table();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        Scroll10 = new widget.ScrollPane();
        tbRadiologiPasien = new widget.Table();
        Scroll11 = new widget.ScrollPane();
        tbLaporanOP = new widget.Table();
        jLabel15 = new widget.Label();
        jLabel17 = new widget.Label();
        Scroll12 = new widget.ScrollPane();
        tbResumePasien = new widget.Table();
        jLabel18 = new widget.Label();
        Scroll13 = new widget.ScrollPane();
        tbResepDokter = new widget.Table();
        jLabel20 = new widget.Label();
        Scroll14 = new widget.ScrollPane();
        tbTebusObat = new widget.Table();
        jLabel22 = new widget.Label();
        Scroll15 = new widget.ScrollPane();
        tbAlergi = new widget.Table();
        jLabel23 = new widget.Label();
        Scroll16 = new widget.ScrollPane();
        tbLamaPelayanan = new widget.Table();
        jLabel26 = new widget.Label();
        Scroll19 = new widget.ScrollPane();
        tbAlkes = new widget.Table();
        Scroll20 = new widget.ScrollPane();
        tbPractitioner = new widget.Table();
        jLabel27 = new widget.Label();
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        btnCheckPeserta = new widget.Button();
        KdPPKBPJS = new widget.TextBox();
        NmPPK = new widget.TextBox();
        TNoSEP = new widget.TextBox();
        TUUIDPasien = new widget.TextBox();
        KdPPKKemKes = new widget.TextBox();
        jLabel5 = new widget.Label();
        jLabel9 = new widget.Label();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        PanelAccor = new widget.PanelBiasa();
        ScrollText = new widget.ScrollPane();
        TJsonBody = new widget.editorpane();
        BtnPanduan = new widget.Button();
        BtnPanduan1 = new widget.Button();
        BtnPanduan2 = new widget.Button();
        BtnPanduan3 = new widget.Button();
        BtnjsonComposititon = new widget.Button();
        BtnPanduan5 = new widget.Button();
        BtnPanduan6 = new widget.Button();
        BtnPanduan7 = new widget.Button();
        BtnPanduan8 = new widget.Button();
        BtnPanduan9 = new widget.Button();
        BtnPanduan10 = new widget.Button();
        ChkDiagnosticReport = new widget.CekBox();
        ChkProcedure = new widget.CekBox();
        ChkMedReq = new widget.CekBox();
        ChkPractitioner = new widget.CekBox();
        ChkEncounter = new widget.CekBox();
        ChkOrganization = new widget.CekBox();
        ChkPatient = new widget.CekBox();
        ChkComposition = new widget.CekBox();
        ChkCondition = new widget.CekBox();
        ChkDevice = new widget.CekBox();
        lblStatusRawat = new widget.TextBox();
        lblTahun = new widget.TextBox();
        lblBulan = new widget.TextBox();
        internalFrame14 = new widget.InternalFrame();
        Scroll6 = new widget.ScrollPane();
        tbDataListSEP = new widget.Table();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnKeluar = new widget.Button();

        NoKaPasien.setBackground(new java.awt.Color(245, 250, 240));
        NoKaPasien.setHighlighter(null);
        NoKaPasien.setName("NoKaPasien"); // NOI18N

        jLabel8.setText("Diagnosa : ");
        jLabel8.setName("jLabel8"); // NOI18N

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbDiagnosaPasien.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDiagnosaPasien.setName("tbDiagnosaPasien"); // NOI18N
        tbDiagnosaPasien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDiagnosaPasienMouseClicked(evt);
            }
        });
        tbDiagnosaPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDiagnosaPasienKeyPressed(evt);
            }
        });
        Scroll7.setViewportView(tbDiagnosaPasien);

        jLabel12.setText("Prosedur : ");
        jLabel12.setName("jLabel12"); // NOI18N

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll8.setName("Scroll8"); // NOI18N
        Scroll8.setOpaque(true);

        tbTindakanPasien.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTindakanPasien.setName("tbTindakanPasien"); // NOI18N
        tbTindakanPasien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTindakanPasienMouseClicked(evt);
            }
        });
        tbTindakanPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakanPasienKeyPressed(evt);
            }
        });
        Scroll8.setViewportView(tbTindakanPasien);

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbLabPasien.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLabPasien.setName("tbLabPasien"); // NOI18N
        tbLabPasien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLabPasienMouseClicked(evt);
            }
        });
        tbLabPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbLabPasienKeyPressed(evt);
            }
        });
        Scroll9.setViewportView(tbLabPasien);

        jLabel13.setText("Lab : ");
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setText("Radiologi : ");
        jLabel14.setName("jLabel14"); // NOI18N

        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbRadiologiPasien.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRadiologiPasien.setName("tbRadiologiPasien"); // NOI18N
        tbRadiologiPasien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRadiologiPasienMouseClicked(evt);
            }
        });
        tbRadiologiPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRadiologiPasienKeyPressed(evt);
            }
        });
        Scroll10.setViewportView(tbRadiologiPasien);

        Scroll11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll11.setName("Scroll11"); // NOI18N
        Scroll11.setOpaque(true);

        tbLaporanOP.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLaporanOP.setName("tbLaporanOP"); // NOI18N
        tbLaporanOP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLaporanOPMouseClicked(evt);
            }
        });
        tbLaporanOP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbLaporanOPKeyPressed(evt);
            }
        });
        Scroll11.setViewportView(tbLaporanOP);

        jLabel15.setText("Laporan OP : ");
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel17.setText("Resume Pasien : ");
        jLabel17.setName("jLabel17"); // NOI18N

        Scroll12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll12.setName("Scroll12"); // NOI18N
        Scroll12.setOpaque(true);

        tbResumePasien.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbResumePasien.setName("tbResumePasien"); // NOI18N
        tbResumePasien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResumePasienMouseClicked(evt);
            }
        });
        tbResumePasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResumePasienKeyPressed(evt);
            }
        });
        Scroll12.setViewportView(tbResumePasien);

        jLabel18.setText("Resep Dokter : ");
        jLabel18.setName("jLabel18"); // NOI18N

        Scroll13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll13.setName("Scroll13"); // NOI18N
        Scroll13.setOpaque(true);

        tbResepDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbResepDokter.setName("tbResepDokter"); // NOI18N
        tbResepDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepDokterMouseClicked(evt);
            }
        });
        tbResepDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepDokterKeyPressed(evt);
            }
        });
        Scroll13.setViewportView(tbResepDokter);

        jLabel20.setText("Tebus Obat Pasien : ");
        jLabel20.setName("jLabel20"); // NOI18N

        Scroll14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll14.setName("Scroll14"); // NOI18N
        Scroll14.setOpaque(true);

        tbTebusObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTebusObat.setName("tbTebusObat"); // NOI18N
        tbTebusObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTebusObatMouseClicked(evt);
            }
        });
        tbTebusObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTebusObatKeyPressed(evt);
            }
        });
        Scroll14.setViewportView(tbTebusObat);

        jLabel22.setText("Alergi Pasien : ");
        jLabel22.setName("jLabel22"); // NOI18N

        Scroll15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll15.setName("Scroll15"); // NOI18N
        Scroll15.setOpaque(true);

        tbAlergi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbAlergi.setName("tbAlergi"); // NOI18N
        tbAlergi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAlergiMouseClicked(evt);
            }
        });
        tbAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbAlergiKeyPressed(evt);
            }
        });
        Scroll15.setViewportView(tbAlergi);

        jLabel23.setText("Lama Pelayanan :");
        jLabel23.setName("jLabel23"); // NOI18N

        Scroll16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll16.setName("Scroll16"); // NOI18N
        Scroll16.setOpaque(true);

        tbLamaPelayanan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLamaPelayanan.setName("tbLamaPelayanan"); // NOI18N
        tbLamaPelayanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLamaPelayananMouseClicked(evt);
            }
        });
        tbLamaPelayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbLamaPelayananKeyPressed(evt);
            }
        });
        Scroll16.setViewportView(tbLamaPelayanan);

        jLabel26.setText("Alkes BHP : ");
        jLabel26.setName("jLabel26"); // NOI18N

        Scroll19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll19.setName("Scroll19"); // NOI18N
        Scroll19.setOpaque(true);

        tbAlkes.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbAlkes.setName("tbAlkes"); // NOI18N
        tbAlkes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAlkesMouseClicked(evt);
            }
        });
        tbAlkes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbAlkesKeyPressed(evt);
            }
        });
        Scroll19.setViewportView(tbAlkes);

        Scroll20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll20.setName("Scroll20"); // NOI18N
        Scroll20.setOpaque(true);

        tbPractitioner.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPractitioner.setName("tbPractitioner"); // NOI18N
        tbPractitioner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPractitionerMouseClicked(evt);
            }
        });
        tbPractitioner.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPractitionerKeyPressed(evt);
            }
        });
        Scroll20.setViewportView(tbPractitioner);

        jLabel27.setText("Practitioner :");
        jLabel27.setName("jLabel27"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 245, 235), 3, true), "::[ Smart Claim BPJS ]::", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TabRawatMouseEntered(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(747, 500));

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(1300, 1320));
        FormInput.setLayout(null);

        TNoRw.setEditable(false);
        TNoRw.setBackground(new java.awt.Color(245, 250, 240));
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormInput.add(TNoRw);
        TNoRw.setBounds(93, 12, 152, 23);

        TPasien.setEditable(false);
        TPasien.setBackground(new java.awt.Color(245, 250, 240));
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(559, 12, 290, 23);

        TNoRM.setEditable(false);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoRMActionPerformed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(247, 12, 110, 23);

        btnCheckPeserta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/icon bpjs.png"))); // NOI18N
        btnCheckPeserta.setMnemonic('X');
        btnCheckPeserta.setText("Cek Status Peserta");
        btnCheckPeserta.setToolTipText("Alt+X");
        btnCheckPeserta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnCheckPeserta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCheckPeserta.setName("btnCheckPeserta"); // NOI18N
        btnCheckPeserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckPesertaActionPerformed(evt);
            }
        });
        FormInput.add(btnCheckPeserta);
        btnCheckPeserta.setBounds(860, 10, 160, 30);

        KdPPKBPJS.setEditable(false);
        KdPPKBPJS.setBackground(new java.awt.Color(245, 250, 240));
        KdPPKBPJS.setHighlighter(null);
        KdPPKBPJS.setName("KdPPKBPJS"); // NOI18N
        FormInput.add(KdPPKBPJS);
        KdPPKBPJS.setBounds(200, 40, 110, 20);

        NmPPK.setEditable(false);
        NmPPK.setBackground(new java.awt.Color(245, 250, 240));
        NmPPK.setHighlighter(null);
        NmPPK.setName("NmPPK"); // NOI18N
        FormInput.add(NmPPK);
        NmPPK.setBounds(310, 40, 250, 20);

        TNoSEP.setBackground(new java.awt.Color(245, 250, 240));
        TNoSEP.setHighlighter(null);
        TNoSEP.setName("TNoSEP"); // NOI18N
        FormInput.add(TNoSEP);
        TNoSEP.setBounds(359, 12, 200, 23);

        TUUIDPasien.setEditable(false);
        TUUIDPasien.setBackground(new java.awt.Color(245, 250, 240));
        TUUIDPasien.setHighlighter(null);
        TUUIDPasien.setName("TUUIDPasien"); // NOI18N
        FormInput.add(TUUIDPasien);
        TUUIDPasien.setBounds(560, 40, 290, 20);

        KdPPKKemKes.setEditable(false);
        KdPPKKemKes.setBackground(new java.awt.Color(245, 250, 240));
        KdPPKKemKes.setHighlighter(null);
        KdPPKKemKes.setName("KdPPKKemKes"); // NOI18N
        FormInput.add(KdPPKKemKes);
        KdPPKKemKes.setBounds(90, 40, 110, 20);

        jLabel5.setText("Data Pasien : ");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 12, 90, 23);

        jLabel9.setText("Tahun SEP : ");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(210, 70, 70, 23);

        jLabel10.setText("Bulan SEP : ");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(410, 70, 80, 23);

        jLabel11.setText("Status Rawat : ");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 70, 90, 23);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detail Data", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(51, 51, 51))); // NOI18N
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(350, 60));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ScrollText.setBorder(null);
        ScrollText.setName("ScrollText"); // NOI18N
        ScrollText.setOpaque(true);
        ScrollText.setPreferredSize(new java.awt.Dimension(150, 16));

        TJsonBody.setBorder(null);
        TJsonBody.setFont(TJsonBody.getFont());
        TJsonBody.setName("TJsonBody"); // NOI18N
        TJsonBody.setPreferredSize(new java.awt.Dimension(456, 400));
        ScrollText.setViewportView(TJsonBody);

        PanelAccor.add(ScrollText, java.awt.BorderLayout.CENTER);

        FormInput.add(PanelAccor);
        PanelAccor.setBounds(10, 110, 840, 470);

        BtnPanduan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Doctor.png"))); // NOI18N
        BtnPanduan.setMnemonic('K');
        BtnPanduan.setText("Practitioner");
        BtnPanduan.setToolTipText("Alt+K");
        BtnPanduan.setGlassColor(new java.awt.Color(0, 102, 102));
        BtnPanduan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPanduan.setName("BtnPanduan"); // NOI18N
        BtnPanduan.setPreferredSize(new java.awt.Dimension(180, 25));
        BtnPanduan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanduanActionPerformed(evt);
            }
        });
        BtnPanduan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPanduanKeyPressed(evt);
            }
        });
        FormInput.add(BtnPanduan);
        BtnPanduan.setBounds(850, 200, 150, 25);

        BtnPanduan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Add patient.png"))); // NOI18N
        BtnPanduan1.setMnemonic('K');
        BtnPanduan1.setText("Encounter");
        BtnPanduan1.setToolTipText("Alt+K");
        BtnPanduan1.setGlassColor(new java.awt.Color(0, 102, 102));
        BtnPanduan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPanduan1.setName("BtnPanduan1"); // NOI18N
        BtnPanduan1.setPreferredSize(new java.awt.Dimension(180, 25));
        BtnPanduan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanduan1ActionPerformed(evt);
            }
        });
        BtnPanduan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPanduan1KeyPressed(evt);
            }
        });
        FormInput.add(BtnPanduan1);
        BtnPanduan1.setBounds(850, 480, 150, 25);

        BtnPanduan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Hospital.png"))); // NOI18N
        BtnPanduan2.setMnemonic('K');
        BtnPanduan2.setText("Organisasi");
        BtnPanduan2.setToolTipText("Alt+K");
        BtnPanduan2.setGlassColor(new java.awt.Color(0, 102, 102));
        BtnPanduan2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPanduan2.setName("BtnPanduan2"); // NOI18N
        BtnPanduan2.setPreferredSize(new java.awt.Dimension(180, 25));
        BtnPanduan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanduan2ActionPerformed(evt);
            }
        });
        BtnPanduan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPanduan2KeyPressed(evt);
            }
        });
        FormInput.add(BtnPanduan2);
        BtnPanduan2.setBounds(850, 160, 150, 25);

        BtnPanduan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale.png"))); // NOI18N
        BtnPanduan3.setMnemonic('K');
        BtnPanduan3.setText(" Patient");
        BtnPanduan3.setToolTipText("Alt+K");
        BtnPanduan3.setGlassColor(new java.awt.Color(0, 102, 102));
        BtnPanduan3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPanduan3.setName("BtnPanduan3"); // NOI18N
        BtnPanduan3.setPreferredSize(new java.awt.Dimension(180, 25));
        BtnPanduan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanduan3ActionPerformed(evt);
            }
        });
        BtnPanduan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPanduan3KeyPressed(evt);
            }
        });
        FormInput.add(BtnPanduan3);
        BtnPanduan3.setBounds(850, 120, 150, 25);

        BtnjsonComposititon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/011.png"))); // NOI18N
        BtnjsonComposititon.setMnemonic('K');
        BtnjsonComposititon.setText("Composition");
        BtnjsonComposititon.setToolTipText("Alt+K");
        BtnjsonComposititon.setGlassColor(new java.awt.Color(0, 102, 102));
        BtnjsonComposititon.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnjsonComposititon.setName("BtnjsonComposititon"); // NOI18N
        BtnjsonComposititon.setPreferredSize(new java.awt.Dimension(180, 25));
        BtnjsonComposititon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnjsonComposititonActionPerformed(evt);
            }
        });
        BtnjsonComposititon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnjsonComposititonKeyPressed(evt);
            }
        });
        FormInput.add(BtnjsonComposititon);
        BtnjsonComposititon.setBounds(850, 440, 150, 25);

        BtnPanduan5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Thermometer.png"))); // NOI18N
        BtnPanduan5.setMnemonic('K');
        BtnPanduan5.setText("Condition");
        BtnPanduan5.setToolTipText("Alt+K");
        BtnPanduan5.setGlassColor(new java.awt.Color(0, 102, 102));
        BtnPanduan5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPanduan5.setName("BtnPanduan5"); // NOI18N
        BtnPanduan5.setPreferredSize(new java.awt.Dimension(180, 25));
        BtnPanduan5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanduan5ActionPerformed(evt);
            }
        });
        BtnPanduan5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPanduan5KeyPressed(evt);
            }
        });
        FormInput.add(BtnPanduan5);
        BtnPanduan5.setBounds(850, 240, 150, 25);

        BtnPanduan6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/icon pc.png"))); // NOI18N
        BtnPanduan6.setMnemonic('K');
        BtnPanduan6.setText(" Device");
        BtnPanduan6.setToolTipText("Alt+K");
        BtnPanduan6.setGlassColor(new java.awt.Color(0, 102, 102));
        BtnPanduan6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPanduan6.setName("BtnPanduan6"); // NOI18N
        BtnPanduan6.setPreferredSize(new java.awt.Dimension(180, 25));
        BtnPanduan6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanduan6ActionPerformed(evt);
            }
        });
        BtnPanduan6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPanduan6KeyPressed(evt);
            }
        });
        FormInput.add(BtnPanduan6);
        BtnPanduan6.setBounds(850, 400, 150, 25);

        BtnPanduan7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnPanduan7.setMnemonic('K');
        BtnPanduan7.setText("Medicationrequest");
        BtnPanduan7.setToolTipText("Alt+K");
        BtnPanduan7.setGlassColor(new java.awt.Color(0, 102, 102));
        BtnPanduan7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPanduan7.setName("BtnPanduan7"); // NOI18N
        BtnPanduan7.setPreferredSize(new java.awt.Dimension(180, 25));
        BtnPanduan7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanduan7ActionPerformed(evt);
            }
        });
        BtnPanduan7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPanduan7KeyPressed(evt);
            }
        });
        FormInput.add(BtnPanduan7);
        BtnPanduan7.setBounds(850, 320, 150, 25);

        BtnPanduan8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/42a.png"))); // NOI18N
        BtnPanduan8.setMnemonic('K');
        BtnPanduan8.setText("MrBundle");
        BtnPanduan8.setToolTipText("Alt+K");
        BtnPanduan8.setGlassColor(new java.awt.Color(0, 102, 102));
        BtnPanduan8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPanduan8.setName("BtnPanduan8"); // NOI18N
        BtnPanduan8.setPreferredSize(new java.awt.Dimension(180, 25));
        BtnPanduan8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanduan8ActionPerformed(evt);
            }
        });
        BtnPanduan8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPanduan8KeyPressed(evt);
            }
        });
        FormInput.add(BtnPanduan8);
        BtnPanduan8.setBounds(850, 550, 150, 25);

        BtnPanduan9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/report24.png"))); // NOI18N
        BtnPanduan9.setMnemonic('K');
        BtnPanduan9.setText("DianosticReport");
        BtnPanduan9.setToolTipText("Alt+K");
        BtnPanduan9.setGlassColor(new java.awt.Color(0, 102, 102));
        BtnPanduan9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPanduan9.setName("BtnPanduan9"); // NOI18N
        BtnPanduan9.setPreferredSize(new java.awt.Dimension(180, 25));
        BtnPanduan9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanduan9ActionPerformed(evt);
            }
        });
        BtnPanduan9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPanduan9KeyPressed(evt);
            }
        });
        FormInput.add(BtnPanduan9);
        BtnPanduan9.setBounds(850, 360, 150, 25);

        BtnPanduan10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/addressbook-add24.png"))); // NOI18N
        BtnPanduan10.setMnemonic('K');
        BtnPanduan10.setText("Procedure");
        BtnPanduan10.setToolTipText("Alt+K");
        BtnPanduan10.setGlassColor(new java.awt.Color(0, 102, 102));
        BtnPanduan10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnPanduan10.setName("BtnPanduan10"); // NOI18N
        BtnPanduan10.setPreferredSize(new java.awt.Dimension(180, 25));
        BtnPanduan10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanduan10ActionPerformed(evt);
            }
        });
        BtnPanduan10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPanduan10KeyPressed(evt);
            }
        });
        FormInput.add(BtnPanduan10);
        BtnPanduan10.setBounds(850, 280, 150, 25);

        ChkDiagnosticReport.setBorder(null);
        ChkDiagnosticReport.setSelected(true);
        ChkDiagnosticReport.setBorderPainted(true);
        ChkDiagnosticReport.setBorderPaintedFlat(true);
        ChkDiagnosticReport.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDiagnosticReport.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDiagnosticReport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDiagnosticReport.setName("ChkDiagnosticReport"); // NOI18N
        ChkDiagnosticReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkDiagnosticReportActionPerformed(evt);
            }
        });
        FormInput.add(ChkDiagnosticReport);
        ChkDiagnosticReport.setBounds(1010, 360, 20, 20);

        ChkProcedure.setBorder(null);
        ChkProcedure.setSelected(true);
        ChkProcedure.setBorderPainted(true);
        ChkProcedure.setBorderPaintedFlat(true);
        ChkProcedure.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkProcedure.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkProcedure.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkProcedure.setName("ChkProcedure"); // NOI18N
        ChkProcedure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkProcedureActionPerformed(evt);
            }
        });
        FormInput.add(ChkProcedure);
        ChkProcedure.setBounds(1010, 280, 20, 20);

        ChkMedReq.setBorder(null);
        ChkMedReq.setSelected(true);
        ChkMedReq.setBorderPainted(true);
        ChkMedReq.setBorderPaintedFlat(true);
        ChkMedReq.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkMedReq.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkMedReq.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkMedReq.setName("ChkMedReq"); // NOI18N
        ChkMedReq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkMedReqActionPerformed(evt);
            }
        });
        FormInput.add(ChkMedReq);
        ChkMedReq.setBounds(1010, 320, 20, 20);

        ChkPractitioner.setBorder(null);
        ChkPractitioner.setSelected(true);
        ChkPractitioner.setBorderPainted(true);
        ChkPractitioner.setBorderPaintedFlat(true);
        ChkPractitioner.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkPractitioner.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkPractitioner.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkPractitioner.setName("ChkPractitioner"); // NOI18N
        ChkPractitioner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkPractitionerActionPerformed(evt);
            }
        });
        FormInput.add(ChkPractitioner);
        ChkPractitioner.setBounds(1010, 200, 20, 20);

        ChkEncounter.setBorder(null);
        ChkEncounter.setSelected(true);
        ChkEncounter.setBorderPainted(true);
        ChkEncounter.setBorderPaintedFlat(true);
        ChkEncounter.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkEncounter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkEncounter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkEncounter.setName("ChkEncounter"); // NOI18N
        ChkEncounter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkEncounterActionPerformed(evt);
            }
        });
        FormInput.add(ChkEncounter);
        ChkEncounter.setBounds(1010, 480, 20, 20);

        ChkOrganization.setBorder(null);
        ChkOrganization.setSelected(true);
        ChkOrganization.setBorderPainted(true);
        ChkOrganization.setBorderPaintedFlat(true);
        ChkOrganization.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkOrganization.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkOrganization.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkOrganization.setName("ChkOrganization"); // NOI18N
        ChkOrganization.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkOrganizationActionPerformed(evt);
            }
        });
        FormInput.add(ChkOrganization);
        ChkOrganization.setBounds(1010, 160, 20, 20);

        ChkPatient.setBorder(null);
        ChkPatient.setSelected(true);
        ChkPatient.setBorderPainted(true);
        ChkPatient.setBorderPaintedFlat(true);
        ChkPatient.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkPatient.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkPatient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkPatient.setName("ChkPatient"); // NOI18N
        ChkPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkPatientActionPerformed(evt);
            }
        });
        FormInput.add(ChkPatient);
        ChkPatient.setBounds(1010, 120, 20, 20);

        ChkComposition.setBorder(null);
        ChkComposition.setSelected(true);
        ChkComposition.setBorderPainted(true);
        ChkComposition.setBorderPaintedFlat(true);
        ChkComposition.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkComposition.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkComposition.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkComposition.setName("ChkComposition"); // NOI18N
        ChkComposition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkCompositionActionPerformed(evt);
            }
        });
        FormInput.add(ChkComposition);
        ChkComposition.setBounds(1010, 440, 20, 20);

        ChkCondition.setBorder(null);
        ChkCondition.setSelected(true);
        ChkCondition.setBorderPainted(true);
        ChkCondition.setBorderPaintedFlat(true);
        ChkCondition.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkCondition.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkCondition.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkCondition.setName("ChkCondition"); // NOI18N
        ChkCondition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkConditionActionPerformed(evt);
            }
        });
        FormInput.add(ChkCondition);
        ChkCondition.setBounds(1010, 240, 20, 20);

        ChkDevice.setBorder(null);
        ChkDevice.setSelected(true);
        ChkDevice.setBorderPainted(true);
        ChkDevice.setBorderPaintedFlat(true);
        ChkDevice.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkDevice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkDevice.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkDevice.setName("ChkDevice"); // NOI18N
        ChkDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkDeviceActionPerformed(evt);
            }
        });
        FormInput.add(ChkDevice);
        ChkDevice.setBounds(1010, 400, 20, 20);

        lblStatusRawat.setBackground(new java.awt.Color(245, 250, 240));
        lblStatusRawat.setHighlighter(null);
        lblStatusRawat.setName("lblStatusRawat"); // NOI18N
        lblStatusRawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblStatusRawatActionPerformed(evt);
            }
        });
        FormInput.add(lblStatusRawat);
        lblStatusRawat.setBounds(90, 70, 120, 20);

        lblTahun.setBackground(new java.awt.Color(245, 250, 240));
        lblTahun.setHighlighter(null);
        lblTahun.setName("lblTahun"); // NOI18N
        lblTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblTahunActionPerformed(evt);
            }
        });
        FormInput.add(lblTahun);
        lblTahun.setBounds(290, 70, 120, 20);

        lblBulan.setBackground(new java.awt.Color(245, 250, 240));
        lblBulan.setHighlighter(null);
        lblBulan.setName("lblBulan"); // NOI18N
        lblBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblBulanActionPerformed(evt);
            }
        });
        FormInput.add(lblBulan);
        lblBulan.setBounds(500, 70, 60, 20);

        Scroll1.setViewportView(FormInput);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Generate Data", internalFrame2);

        internalFrame14.setBorder(null);
        internalFrame14.setName("internalFrame14"); // NOI18N
        internalFrame14.setLayout(new java.awt.GridLayout(1, 2));

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbDataListSEP.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDataListSEP.setName("tbDataListSEP"); // NOI18N
        tbDataListSEP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataListSEPMouseClicked(evt);
            }
        });
        tbDataListSEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataListSEPKeyPressed(evt);
            }
        });
        Scroll6.setViewportView(tbDataListSEP);

        internalFrame14.add(Scroll6);

        TabRawat.addTab("MR Bundle", internalFrame14);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);
        TabRawat.getAccessibleContext().setAccessibleName("Generate Data");

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan dan Kirim SmartClaim");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(250, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed

        faskes.dispose();
        penyakit.dispose();
        skdp.dispose();
        propinsi.dispose();
        kabupaten.dispose();
        kecamatan.dispose();
        dokter.dispose();
        poli.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {

        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked

    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbDataListSEPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataListSEPMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDataListSEPMouseClicked

    private void tbDataListSEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataListSEPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDataListSEPKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (TNoRM.getText().trim().equals("")) {
            Valid.textKosong(TNoRM, "Nomor RM");
        } else if (TNoSEP.getText().trim().equals("")) {
            Valid.textKosong(TNoSEP, "NO SEP");
        } else if (KdPPKBPJS.getText().trim().equals("") || NmPPK.getText().trim().equals("")) {
            Valid.textKosong(KdPPKBPJS, "PPK Pelayanan");
        } else if (lblStatusRawat.getText().trim().equals("")) {
            Valid.textKosong(KdPPKBPJS, "Status Rawat");
        } else if (lblTahun.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Tahun rawat Kosong");
        } else if (lblBulan.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Bulan rawat Kosong");
        } else {
            setJsonMrBundle();
            try {
                // Validate JSON Bundle
                String prettyJson = Valid.getPrettyJson(jsonMrBundle);
                //System.out.println(prettyJson);

                if (prettyJson == null || prettyJson.isEmpty()) {
                    JOptionPane.showMessageDialog(rootPane, "JSON Bundle kosong atau tidak valid!");
                    return;
                }

                // Validate required fields
                if (KdPPKBPJS.getText() == null || KdPPKBPJS.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(rootPane, "Kode PPK BPJS tidak boleh kosong!");
                    return;
                }

                if (TNoSEP.getText() == null || TNoSEP.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(rootPane, "Nomor SEP tidak boleh kosong!");
                    return;
                }

                // Compress and encrypt data
                String mrbundle = api.encryptSmartClaimDataV2(prettyJson, KdPPKBPJS.getText().trim());

                if (mrbundle == null || mrbundle.isEmpty()) {
                    JOptionPane.showMessageDialog(rootPane, "Gagal melakukan enkripsi data!");
                    return;
                }

                // Prepare HTTP headers
                headers = new HttpHeaders();
                headers.setContentType(MediaType.TEXT_PLAIN);
                headers.add("X-Cons-ID", koneksiDB.CONSIDAPIBPJS());

                utc = String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("X-Timestamp", utc);
                headers.add("X-Signature", api.getHmac(utc));

                // Build API request
                URL = koneksiDB.URLAPIBPJSSMARTCLAIM() + "/eclaim/rekammedis/insert";

                String jnsPelayanan = lblStatusRawat.getText().contains("Ranap") ? "1" : "2";
                String bulan = lblBulan.getText();
                String tahun = lblTahun.getText().trim().isEmpty() ? "2019" : lblTahun.getText().trim();

                requestJson = "{\n"
                        + "    \"request\": {\n"
                        + "        \"noSep\": \"" + TNoSEP.getText().trim() + "\",\n"
                        + "        \"jnsPelayanan\": \"" + jnsPelayanan + "\",\n"
                        + "        \"bulan\": \"" + bulan + "\",\n"
                        + "        \"tahun\": \"" + tahun + "\",\n"
                        + "        \"dataMR\": \"" + mrbundle + "\"\n"
                        + "    }\n"
                        + "}";

                requestEntity = new HttpEntity(requestJson, headers);

                // Send request with retry logic
                System.out.println("Mengirim data ke BPJS Smart Claim - SEP: " + TNoSEP.getText().trim());

                int maxRetries = 3;
                int retryCount = 0;
                boolean success = false;

                while (!success && retryCount < maxRetries) {
                    try {
                        if (retryCount > 0) {
                            System.out.println("Mencoba ulang (" + retryCount + "/" + (maxRetries - 1) + ")...");
                            Thread.sleep(2000);
                        }

                        long startTime = System.currentTimeMillis();
                        root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                        long endTime = System.currentTimeMillis();

                        System.out.println("Response diterima dalam " + (endTime - startTime) + "ms");
                        success = true;

                    } catch (org.springframework.web.client.ResourceAccessException e) {
                        retryCount++;
                        System.err.println("Koneksi error (percobaan " + retryCount + "): " + e.getMessage());

                        if (retryCount >= maxRetries) {
                            throw e;
                        }
                    }
                }

                // Parse and handle response
                nameNode = root.path("metadata");
                String responseCode = nameNode.path("code").asText();
                String responseMessage = nameNode.path("message").asText();

                System.out.println("BPJS Response - Code: " + responseCode + " | Message: " + responseMessage);

                if (responseCode.equals("200")) {
                    JOptionPane.showMessageDialog(rootPane,
                            "Berhasil!\n\nRespon BPJS: " + responseMessage,
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(rootPane,
                            "Gagal!\n\nKode: " + responseCode + "\nPesan: " + responseMessage,
                            "Error BPJS",
                            JOptionPane.WARNING_MESSAGE);
                }

            } catch (org.springframework.web.client.ResourceAccessException ex) {
                System.err.println("ERROR: Koneksi ke BPJS gagal - " + ex.getMessage());

                String errorMsg = "Koneksi ke server BPJS gagal!\n\n";

                if (ex.getMessage().contains("Connection reset")) {
                    errorMsg += "Kemungkinan penyebab:\n"
                            + "- Server BPJS memutus koneksi (SSL handshake failed)\n"
                            + "- Firewall memblokir koneksi\n"
                            + "- Data terlalu besar\n"
                            + "- Format enkripsi tidak sesuai\n\n"
                            + "Solusi:\n"
                            + "- Pastikan enkripsi data sudah benar\n"
                            + "- Cek ukuran data (maks 5MB)\n"
                            + "- Hubungi admin BPJS";
                } else if (ex.getMessage().contains("timeout")) {
                    errorMsg += "Server tidak merespon dalam waktu yang ditentukan.\n"
                            + "Silakan coba lagi.";
                } else {
                    errorMsg += "Error: " + ex.getMessage();
                }

                JOptionPane.showMessageDialog(rootPane, errorMsg, "Error Koneksi", JOptionPane.ERROR_MESSAGE);

            } catch (IOException ex) {
                System.err.println("ERROR: IO Exception - " + ex.getMessage());
                JOptionPane.showMessageDialog(rootPane,
                        "Error kompresi/enkripsi: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

            } catch (Exception ex) {
                System.err.println("ERROR: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
                JOptionPane.showMessageDialog(rootPane,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {

        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void TabRawatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_TabRawatMouseEntered

    private void tbPractitionerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPractitionerKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPractitionerKeyPressed

    private void tbPractitionerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPractitionerMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPractitionerMouseClicked

    private void tbAlkesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAlkesKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbAlkesKeyPressed

    private void tbAlkesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAlkesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbAlkesMouseClicked

    private void BtnPanduan10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPanduan10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPanduan10KeyPressed

    private void BtnPanduan10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPanduan10ActionPerformed
        setJsonProcedure();
        System.out.println(jsonProcedure.toString());
    }//GEN-LAST:event_BtnPanduan10ActionPerformed

    private void tbLamaPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLamaPelayananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLamaPelayananKeyPressed

    private void tbLamaPelayananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLamaPelayananMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLamaPelayananMouseClicked

    private void BtnPanduan9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPanduan9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPanduan9KeyPressed

    private void BtnPanduan9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPanduan9ActionPerformed
        setJsonDiagnosticReport();
        System.out.println(jsonDiagnosticReport.toString());
    }//GEN-LAST:event_BtnPanduan9ActionPerformed

    private void BtnPanduan8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPanduan8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPanduan8KeyPressed

    private void BtnPanduan8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPanduan8ActionPerformed
        displayHtmlBundleView();
    }//GEN-LAST:event_BtnPanduan8ActionPerformed

    private void BtnPanduan7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPanduan7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPanduan7KeyPressed

    private void BtnPanduan7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPanduan7ActionPerformed
        setJsonMedicationrequest();
        System.out.println(jsonMedicationRequest.toString());
    }//GEN-LAST:event_BtnPanduan7ActionPerformed

    private void BtnPanduan6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPanduan6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPanduan6KeyPressed

    private void BtnPanduan6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPanduan6ActionPerformed
        setJsonDevice();
        System.out.println(jsonDevice.toString());
    }//GEN-LAST:event_BtnPanduan6ActionPerformed

    private void BtnPanduan5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPanduan5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPanduan5KeyPressed

    private void BtnPanduan5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPanduan5ActionPerformed
        setJsonCondition();
        System.out.println(jsonCondition.toString());
    }//GEN-LAST:event_BtnPanduan5ActionPerformed

    private void BtnjsonComposititonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnjsonComposititonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnjsonComposititonKeyPressed

    private void BtnjsonComposititonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnjsonComposititonActionPerformed
        setJsonComposition();
        System.out.println(jsonComposititon.toString());
    }//GEN-LAST:event_BtnjsonComposititonActionPerformed

    private void BtnPanduan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPanduan3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPanduan3KeyPressed

    private void BtnPanduan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPanduan3ActionPerformed
        setJsonPatien();
        System.out.println(jsonPatient.toString());
    }//GEN-LAST:event_BtnPanduan3ActionPerformed

    private void BtnPanduan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPanduan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPanduan2KeyPressed

    private void BtnPanduan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPanduan2ActionPerformed
        setJsonOrganisasi();
        System.out.println(jsonOrganization.toString());
    }//GEN-LAST:event_BtnPanduan2ActionPerformed

    private void tbAlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAlergiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbAlergiKeyPressed

    private void tbAlergiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAlergiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbAlergiMouseClicked

    private void BtnPanduan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPanduan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPanduan1KeyPressed

    private void BtnPanduan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPanduan1ActionPerformed
        setJsonEncounter();
        System.out.println(jsonEncounter.toString());
    }//GEN-LAST:event_BtnPanduan1ActionPerformed

    private void BtnPanduanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPanduanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPanduanKeyPressed

    private void BtnPanduanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPanduanActionPerformed
        tampilpract(TNoRw.getText());
        setJsonPractitioner();
        System.out.println(jsonPractitioner.toString());
    }//GEN-LAST:event_BtnPanduanActionPerformed

    private void tbTebusObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTebusObatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbTebusObatKeyPressed

    private void tbTebusObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTebusObatMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbTebusObatMouseClicked

    private void tbResepDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbResepDokterKeyPressed

    private void tbResepDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepDokterMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbResepDokterMouseClicked

    private void tbResumePasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResumePasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbResumePasienKeyPressed

    private void tbResumePasienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResumePasienMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbResumePasienMouseClicked

    private void tbLaporanOPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLaporanOPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLaporanOPKeyPressed

    private void tbLaporanOPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLaporanOPMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLaporanOPMouseClicked

    private void tbRadiologiPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRadiologiPasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadiologiPasienKeyPressed

    private void tbRadiologiPasienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRadiologiPasienMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadiologiPasienMouseClicked

    private void tbLabPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLabPasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLabPasienKeyPressed

    private void tbLabPasienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLabPasienMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbLabPasienMouseClicked

    private void tbTindakanPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTindakanPasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbTindakanPasienKeyPressed

    private void tbTindakanPasienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTindakanPasienMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbTindakanPasienMouseClicked

    private void tbDiagnosaPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDiagnosaPasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDiagnosaPasienKeyPressed

    private void tbDiagnosaPasienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDiagnosaPasienMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDiagnosaPasienMouseClicked

    private void btnCheckPesertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckPesertaActionPerformed
        //        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //        UUID fromStringUUID = UUID.fromString(TNoSEP.getText());
        //        System.out.println("UUID from String: " + fromStringUUID.toString());
        //        this.setCursor(Cursor.getDefaultCursor());

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
// Set nilai UUID ke kolom TUUIDPasien
        TUUIDPasien.setText(processUUIDString(TNoSEP.getText()));

        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCheckPesertaActionPerformed

    private void TNoRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRMActionPerformed

    private void ChkDiagnosticReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkDiagnosticReportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkDiagnosticReportActionPerformed

    private void ChkProcedureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkProcedureActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkProcedureActionPerformed

    private void ChkMedReqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkMedReqActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkMedReqActionPerformed

    private void ChkPractitionerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkPractitionerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkPractitionerActionPerformed

    private void ChkEncounterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkEncounterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkEncounterActionPerformed

    private void ChkOrganizationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkOrganizationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkOrganizationActionPerformed

    private void ChkPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkPatientActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkPatientActionPerformed

    private void ChkCompositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkCompositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkCompositionActionPerformed

    private void ChkConditionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkConditionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkConditionActionPerformed

    private void ChkDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkDeviceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkDeviceActionPerformed

    private void lblStatusRawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblStatusRawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblStatusRawatActionPerformed

    private void lblTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblTahunActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTahunActionPerformed

    private void lblBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblBulanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblBulanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSSmartClaim dialog = new BPJSSmartClaim(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnKeluar;
    private widget.Button BtnPanduan;
    private widget.Button BtnPanduan1;
    private widget.Button BtnPanduan10;
    private widget.Button BtnPanduan2;
    private widget.Button BtnPanduan3;
    private widget.Button BtnPanduan5;
    private widget.Button BtnPanduan6;
    private widget.Button BtnPanduan7;
    private widget.Button BtnPanduan8;
    private widget.Button BtnPanduan9;
    private widget.Button BtnSimpan;
    private widget.Button BtnjsonComposititon;
    private widget.CekBox ChkComposition;
    private widget.CekBox ChkCondition;
    private widget.CekBox ChkDevice;
    private widget.CekBox ChkDiagnosticReport;
    private widget.CekBox ChkEncounter;
    private widget.CekBox ChkMedReq;
    private widget.CekBox ChkOrganization;
    private widget.CekBox ChkPatient;
    private widget.CekBox ChkPractitioner;
    private widget.CekBox ChkProcedure;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdPPKBPJS;
    private widget.TextBox KdPPKKemKes;
    private widget.TextBox NmPPK;
    private widget.TextBox NoKaPasien;
    private widget.PanelBiasa PanelAccor;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll12;
    private widget.ScrollPane Scroll13;
    private widget.ScrollPane Scroll14;
    private widget.ScrollPane Scroll15;
    private widget.ScrollPane Scroll16;
    private widget.ScrollPane Scroll19;
    private widget.ScrollPane Scroll20;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private widget.ScrollPane ScrollText;
    private widget.editorpane TJsonBody;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoSEP;
    private widget.TextBox TPasien;
    private widget.TextBox TUUIDPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Button btnCheckPeserta;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame14;
    private widget.InternalFrame internalFrame2;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel20;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel5;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.TextBox lblBulan;
    private widget.TextBox lblStatusRawat;
    private widget.TextBox lblTahun;
    private widget.panelisi panelGlass8;
    private widget.Table tbAlergi;
    private widget.Table tbAlkes;
    private widget.Table tbDataListSEP;
    private widget.Table tbDiagnosaPasien;
    private widget.Table tbLabPasien;
    private widget.Table tbLamaPelayanan;
    private widget.Table tbLaporanOP;
    private widget.Table tbPractitioner;
    private widget.Table tbRadiologiPasien;
    private widget.Table tbResepDokter;
    private widget.Table tbResumePasien;
    private widget.Table tbTebusObat;
    private widget.Table tbTindakanPasien;
    // End of variables declaration//GEN-END:variables

    private void isRawat() {
        Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ", TNoRM, TNoRw.getText());
        Sequel.cariIsi("select pasien.no_peserta from pasien where pasien.no_rkm_medis=? ", NoKaPasien, TNoRM.getText());
        idPasien = Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis='" + TNoRM.getText() + "'");
        nokaBPJS = Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis='" + TNoRM.getText() + "'");
        noMR = Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis='" + TNoRM.getText() + "'");
        idPasien = Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis='" + TNoRM.getText() + "'");
        nikPasien = Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis='" + TNoRM.getText() + "'");
        noTelpPasien = Sequel.cariIsi("select no_tlp from pasien where no_rkm_medis='" + TNoRM.getText() + "'");
    }

    private void emptTeks() {
        TNoRw.setText("");
        TPasien.setText("");

    }

    public void setNoRmSmartClaim(String norwt, String noSEP, String NoRMPasien, String NamaPasien, String status) {
        TNoRw.setText(norwt);
        TNoSEP.setText(noSEP);
        TPasien.setText(NamaPasien);
        lblStatusRawat.setText(status);
        lblTahun.setText(Sequel.cariIsi("select YEAR(bridging_sep.tglsep) from bridging_sep where bridging_sep.no_sep='" + noSEP + "'"));
        lblBulan.setText(Sequel.cariIsi("select MONTH(bridging_sep.tglsep) from bridging_sep where bridging_sep.no_sep='" + noSEP + "'"));

        isRawat();
        tampilDiagnosa(norwt, status);
        tampilProcedure(norwt, status);
        tampilLabPasien(norwt);
        tampilRadPasien(norwt);
        tampilResume(norwt);
        // tampilLapOP(norwt);
        tampilResep(norwt);
        tampilTebusObat(norwt);
        tampilAlergi(norwt);
        tampilLamaPelayanan(norwt);
        //tampilAlkes(norwt);
        tampilpract(norwt);

        // Generate all JSON resources and display them in the HTML view
        displayHtmlBundleView();

        // Generate the final bundle for actual use
        setJsonMrBundle();

        //System.out.println(jsonMrBundle);
    }

    public void setNoRm(String norwt, Date tgl1, String status, String kdpoli, String namapoli, String kodedokter) {
        TNoRw.setText(norwt);
        kodedokterreg = kodedokter;
        kodepolireg = kdpoli;
        isRawat();
    }

    public void setNoRm3(String norwt, Date tgl1) {
        TabRawat.setSelectedIndex(1);

    }

    public void isCek() {

    }

    public void tutupInput() {
        TabRawat.setSelectedIndex(1);

    }

    private void getData() {

    }

    private void getDataInternal() {

    }

    public static class HttpEntityEnclosingDeleteRequest extends HttpEntityEnclosingRequestBase {

        public HttpEntityEnclosingDeleteRequest(final URI uri) {
            super();
            setURI(uri);
        }

        @Override
        public String getMethod() {
            return "DELETE";
        }
    }

    private void tampilOrganisasi() {
        try {
            ps = koneksi.prepareStatement(
                    "SELECT\n"
                    + "	setting.nama_instansi, \n"
                    + "	setting.alamat_instansi, \n"
                    + "	setting.kabupaten, \n"
                    + "	setting.propinsi, \n"
                    + "	setting.kontak, \n"
                    + "	setting.email, \n"
                    + "	setting.aktifkan, \n"
                    + "	setting.kode_ppk, \n"
                    + "	setting.kode_ppkkemenkes\n"
                    + "FROM\n"
                    + "	setting");
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    KdPPKBPJS.setText(rs.getString("kode_ppk"));
                    KdPPKKemKes.setText(rs.getString("kode_ppkkemenkes"));
                    KdPPKBPJS.setText(rs.getString("kode_ppk"));
                    NmPPK.setText(rs.getString("nama_instansi"));
                    alamatinstansi = rs.getString("alamat_instansi");
                    kabupateninstansi = rs.getString("kabupaten");
                    propinsiinstansi = rs.getString("propinsi");
                    notelpinstansi = rs.getString("kontak");
                    emailinstansi = rs.getString("email");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

// =============================================================================
// 1. ORGANIZATION RESOURCE
// =============================================================================
    private void setJsonOrganisasi() {
        // Call the enhanced version with an empty noRawat parameter
        setJsonOrganisasiWithLocation(TNoRw.getText());
    }

    // Enhanced version that can accept location data
    private void setJsonOrganisasiWithLocation(String noRawat) {
        try {
            String ppkBPJS = KdPPKBPJS.getText() != null ? KdPPKBPJS.getText() : "";
            String ppkKemKes = KdPPKKemKes.getText() != null ? KdPPKKemKes.getText() : "";
            String namaPPK = NmPPK.getText() != null ? NmPPK.getText().trim() : "";
            String cleanTelpon = notelpinstansi != null ? notelpinstansi : "";
            String cleanAlamat = alamatinstansi != null ? alamatinstansi : "";
            String cleanKabupaten = kabupateninstansi != null ? kabupateninstansi : "";
            String cleanPropinsi = propinsiinstansi != null ? propinsiinstansi : "";

            ArrayNode allOrganizations = mapper.createArrayNode();

            // Add main hospital organization
            ObjectNode mainOrganization = createMainOrganization(ppkBPJS, ppkKemKes, namaPPK, cleanTelpon, cleanAlamat, cleanKabupaten, cleanPropinsi);
            allOrganizations.add(mainOrganization);

            // Add department organizations based on data from tables
            // Laboratory organization
            if (tbLabPasien.getRowCount() > 0) {
                ObjectNode labOrganization = createDepartmentOrganization("LABORATORIUM", ppkBPJS, ppkKemKes, namaPPK, cleanTelpon, cleanAlamat, cleanKabupaten, cleanPropinsi);
                allOrganizations.add(labOrganization);
            }

            // Radiology organization
            if (tbRadiologiPasien.getRowCount() > 0) {
                ObjectNode radOrganization = createDepartmentOrganization("RADIOLOGI", ppkBPJS, ppkKemKes, namaPPK, cleanTelpon, cleanAlamat, cleanKabupaten, cleanPropinsi);
                allOrganizations.add(radOrganization);
            }

            // Pharmacy organization
            if (tbResepDokter.getRowCount() > 0 || tbTebusObat.getRowCount() > 0) {
                ObjectNode pharmacyOrganization = createDepartmentOrganization("FARMASI", ppkBPJS, ppkKemKes, namaPPK, cleanTelpon, cleanAlamat, cleanKabupaten, cleanPropinsi);
                allOrganizations.add(pharmacyOrganization);
            }

            // Emergency department organization
            ObjectNode emergencyOrganization = createDepartmentOrganization("IGD", ppkBPJS, ppkKemKes, namaPPK, cleanTelpon, cleanAlamat, cleanKabupaten, cleanPropinsi);
            allOrganizations.add(emergencyOrganization);

            // Process location-based organizations if noRawat is provided
            if (noRawat != null && !noRawat.isEmpty()) {
                // Get both outpatient and inpatient location data
                List<ObjectNode> outpatientLocations = getOutpatientLocationData(noRawat);
                List<ObjectNode> inpatientLocations = getInpatientLocationData(noRawat);

                // Process all outpatient locations
                for (ObjectNode location : outpatientLocations) {
                    String idLocationSatuSehat = location.has("id_lokasi_satusehat") ? location.get("id_lokasi_satusehat").asText() : "";
                    String locationName = location.has("nm_poli") ? location.get("nm_poli").asText() : "";

                    if (!idLocationSatuSehat.isEmpty()) {
                        // Create organization with location-specific data
                        ObjectNode organization = createSpecificOrganization(idLocationSatuSehat, locationName, namaPPK, ppkBPJS, ppkKemKes,
                                cleanTelpon, cleanAlamat, cleanKabupaten, cleanPropinsi);
                        allOrganizations.add(organization);
                    } else {
                        // If no specific location ID exists, create a default one using the location name
                        ObjectNode organization = createDefaultOrganizationWithLocation(location.has("nm_poli") ? location.get("nm_poli").asText() : "RAWAT JALAN", ppkBPJS, ppkKemKes, namaPPK, cleanTelpon, cleanAlamat, cleanKabupaten, cleanPropinsi);
                        allOrganizations.add(organization);
                    }
                }

                // Process all inpatient locations
                for (ObjectNode location : inpatientLocations) {
                    String idLocationSatuSehat = location.has("id_lokasi_satusehat") ? location.get("id_lokasi_satusehat").asText() : "";
                    String locationName = location.has("nm_bangsal") ? location.get("nm_bangsal").asText() : "";

                    if (!idLocationSatuSehat.isEmpty()) {
                        // Create organization with location-specific data
                        ObjectNode organization = createSpecificOrganization(idLocationSatuSehat, locationName, namaPPK, ppkBPJS, ppkKemKes,
                                cleanTelpon, cleanAlamat, cleanKabupaten, cleanPropinsi);
                        allOrganizations.add(organization);
                    } else {
                        // If no specific location ID exists, create a default one using the location name
                        ObjectNode organization = createDefaultOrganizationWithLocation(location.has("nm_bangsal") ? location.get("nm_bangsal").asText() : "RAWAT INAP", ppkBPJS, ppkKemKes, namaPPK, cleanTelpon, cleanAlamat, cleanKabupaten, cleanPropinsi);
                        allOrganizations.add(organization);
                    }
                }
            }

            // Wrap in resource as array
            ObjectNode wrapper = mapper.createObjectNode();
            wrapper.set("resource", allOrganizations);
            jsonOrganization = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wrapper);

        } catch (Exception e) {
            System.out.println("Error creating Organization JSON: " + e.getMessage());
            e.printStackTrace();
            jsonOrganization = "";
        }
    }

    /**
     * Create the main hospital organization
     */
    private ObjectNode createMainOrganization(String ppkBPJS, String ppkKemKes, String namaPPK,
            String cleanTelpon, String cleanAlamat, String cleanKabupaten, String cleanPropinsi) {
        try {
            ObjectNode organization = mapper.createObjectNode();
            organization.put("resourceType", "Organization");
            organization.put("id", generateResourceId(ppkBPJS)); // Use consistent ID format

            // Identifiers
            ArrayNode identifiers = mapper.createArrayNode();
            ObjectNode bpjsIdentifier = mapper.createObjectNode();
            bpjsIdentifier.put("use", "official");
            bpjsIdentifier.put("system", "urn:oid:bpjs");
            bpjsIdentifier.put("value", ppkBPJS);
            identifiers.add(bpjsIdentifier);

            ObjectNode kemkesIdentifier = mapper.createObjectNode();
            kemkesIdentifier.put("use", "official");
            kemkesIdentifier.put("system", "urn:oid:kemkes");
            kemkesIdentifier.put("value", ppkKemKes);
            identifiers.add(kemkesIdentifier);
            organization.set("identifier", identifiers);

            // Type
            ArrayNode types = mapper.createArrayNode();
            types.add(createCodeableConcept("http://hl7.org/fhir/organization-type", "prov",
                    "Healthcare Provider", null));
            organization.set("type", types);

            // Name and Alias
            organization.put("name", sanitizeString(namaPPK));
            ArrayNode aliases = mapper.createArrayNode();
            aliases.add(sanitizeString(namaPPK)); // Use hospital name as alias
            organization.set("alias", aliases);

            // Telecom
            ArrayNode telecoms = mapper.createArrayNode();
            telecoms.add(createTelecom("phone", cleanTelpon, "work"));
            organization.set("telecom", telecoms);

            // Address
            ArrayNode addresses = mapper.createArrayNode();
            ObjectNode address = mapper.createObjectNode();
            address.put("use", "work");
            String fullAddress = sanitizeString(cleanAlamat + ", " + "Kemiri, Mojosongo, " + cleanKabupaten);
            address.put("text", fullAddress);
            ArrayNode lines = mapper.createArrayNode();
            lines.add(cleanAlamat + ", Kemiri, Mojosongo, " + cleanKabupaten);
            address.set("line", lines);
            address.put("city", "Kabupaten " + cleanKabupaten);
            address.put("state", cleanPropinsi);
            address.put("postalCode", "57231");
            address.put("country", "IDN");
            addresses.add(address);
            organization.set("address", addresses);

            // Contact
            ArrayNode contacts = mapper.createArrayNode();
            ObjectNode contact = mapper.createObjectNode();
            ObjectNode purpose = mapper.createObjectNode();
            ArrayNode purposeCoding = mapper.createArrayNode();
            purposeCoding.add(createCoding("http://hl7.org/fhir/contactentity-type",
                    "PATINF", null));
            purpose.set("coding", purposeCoding);
            contact.set("purpose", purpose);
            ArrayNode contactTelecoms = mapper.createArrayNode();
            contactTelecoms.add(createTelecom("phone", cleanTelpon, null));
            contact.set("telecom", contactTelecoms);
            contacts.add(contact);
            organization.set("contact", contacts);

            return organization;

        } catch (Exception e) {
            System.out.println("Error creating main Organization: " + e.getMessage());
            e.printStackTrace();
            return mapper.createObjectNode();
        }
    }

    /**
     * Create a department-specific organization
     */
    private ObjectNode createDepartmentOrganization(String departmentName, String ppkBPJS, String ppkKemKes, String namaPPK,
            String cleanTelpon, String cleanAlamat, String cleanKabupaten, String cleanPropinsi) {
        try {
            ObjectNode organization = mapper.createObjectNode();
            organization.put("resourceType", "Organization");
            // Use the department name in the ID to make it unique
            organization.put("id", generateResourceId(ppkBPJS + "-" + departmentName.substring(0, Math.min(departmentName.length(), 3))));

            // Identifiers
            ArrayNode identifiers = mapper.createArrayNode();
            identifiers.add(createIdentifier("official", "urn:oid:bpjs", ppkBPJS, null));
            identifiers.add(createIdentifier("official", "urn:oid:kemkes", ppkKemKes, null));
            organization.set("identifier", identifiers);

            // Type
            ArrayNode types = mapper.createArrayNode();
            types.add(createCodeableConcept("http://hl7.org/fhir/organization-type", "prov",
                    "Healthcare Provider", null));
            organization.set("type", types);

            // Name and Alias - use the department-specific name
            organization.put("name", sanitizeString(departmentName));
            ArrayNode aliases = mapper.createArrayNode();
            aliases.add(sanitizeString(namaPPK)); // Use hospital name as alias
            organization.set("alias", aliases);

            // Telecom
            ArrayNode telecoms = mapper.createArrayNode();
            telecoms.add(createTelecom("phone", cleanTelpon, "work"));
            organization.set("telecom", telecoms);

            // Address
            ArrayNode addresses = mapper.createArrayNode();
            ObjectNode address = mapper.createObjectNode();
            address.put("use", "work");
            String fullAddress = sanitizeString(cleanAlamat + " " + cleanKabupaten + " " + cleanPropinsi);
            address.put("text", fullAddress);
            ArrayNode lines = mapper.createArrayNode();
            lines.add(fullAddress);
            address.set("line", lines);
            address.put("city", sanitizeString(cleanKabupaten));
            address.put("state", sanitizeString(cleanPropinsi));
            address.put("postalCode", "57322");
            address.put("country", "IDN");
            addresses.add(address);
            organization.set("address", addresses);

            // Contact
            ArrayNode contacts = mapper.createArrayNode();
            ObjectNode contact = mapper.createObjectNode();
            ObjectNode purpose = mapper.createObjectNode();
            ArrayNode purposeCoding = mapper.createArrayNode();
            purposeCoding.add(createCoding("http://terminology.hl7.org/CodeSystem/contactentity-type",
                    "ADMIN", null));
            purpose.set("coding", purposeCoding);
            contact.set("purpose", purpose);
            ArrayNode contactTelecoms = mapper.createArrayNode();
            contactTelecoms.add(createTelecom("phone", cleanTelpon, null));
            contact.set("telecom", contactTelecoms);
            contacts.add(contact);
            organization.set("contact", contacts);

            return organization;

        } catch (Exception e) {
            System.out.println("Error creating department Organization: " + e.getMessage());
            e.printStackTrace();
            return mapper.createObjectNode();
        }
    }

    /**
     * Create an organization with location-specific name
     */
    private ObjectNode createDefaultOrganizationWithLocation(String locationName, String ppkBPJS, String ppkKemKes, String namaPPK,
            String cleanTelpon, String cleanAlamat, String cleanKabupaten, String cleanPropinsi) {
        try {
            ObjectNode organization = mapper.createObjectNode();
            organization.put("resourceType", "Organization");
            // Use the location name in the ID to make it unique
            organization.put("id", generateResourceId(ppkBPJS + "-" + locationName.substring(0, Math.min(locationName.length(), 3)).toUpperCase()));

            // Identifiers
            ArrayNode identifiers = mapper.createArrayNode();
            identifiers.add(createIdentifier("official", "urn:oid:bpjs", ppkBPJS, null));
            identifiers.add(createIdentifier("official", "urn:oid:kemkes", ppkKemKes, null));
            organization.set("identifier", identifiers);

            // Type
            ArrayNode types = mapper.createArrayNode();
            types.add(createCodeableConcept("http://hl7.org/fhir/organization-type", "prov",
                    "Healthcare Provider", null));
            organization.set("type", types);

            // Name and Alias - use the location-specific name
            organization.put("name", sanitizeString(locationName));
            ArrayNode aliases = mapper.createArrayNode();
            aliases.add(sanitizeString(namaPPK)); // Use hospital name as alias
            organization.set("alias", aliases);

            // Telecom
            ArrayNode telecoms = mapper.createArrayNode();
            telecoms.add(createTelecom("phone", cleanTelpon, "work"));
            organization.set("telecom", telecoms);

            // Address
            ArrayNode addresses = mapper.createArrayNode();
            ObjectNode address = mapper.createObjectNode();
            address.put("use", "work");
            String fullAddress = sanitizeString(cleanAlamat + " " + cleanKabupaten + " " + cleanPropinsi);
            address.put("text", fullAddress);
            ArrayNode lines = mapper.createArrayNode();
            lines.add(fullAddress);
            address.set("line", lines);
            address.put("city", sanitizeString(cleanKabupaten));
            address.put("state", sanitizeString(cleanPropinsi));
            address.put("postalCode", "57322");
            address.put("country", "IDN");
            addresses.add(address);
            organization.set("address", addresses);

            // Contact
            ArrayNode contacts = mapper.createArrayNode();
            ObjectNode contact = mapper.createObjectNode();
            ObjectNode purpose = mapper.createObjectNode();
            ArrayNode purposeCoding = mapper.createArrayNode();
            purposeCoding.add(createCoding("http://terminology.hl7.org/CodeSystem/contactentity-type",
                    "ADMIN", null));
            purpose.set("coding", purposeCoding);
            contact.set("purpose", purpose);
            ArrayNode contactTelecoms = mapper.createArrayNode();
            contactTelecoms.add(createTelecom("phone", cleanTelpon, null));
            contact.set("telecom", contactTelecoms);
            contacts.add(contact);
            organization.set("contact", contacts);

            return organization;

        } catch (Exception e) {
            System.out.println("Error creating location-specific Organization: " + e.getMessage());
            e.printStackTrace();
            return mapper.createObjectNode();
        }
    }

    /**
     * Helper method to create and set organization with location-specific data
     *
     * @param id The ID from the location mapping (id_lokasi_satusehat)
     * @param locationName The location name (nm_poli or nm_bangsal)
     * @param alias The alias from NmPPK
     */
    private void createAndSetSpecificOrganization(String id, String locationName, String alias) {
        try {
            String ppkBPJS = KdPPKBPJS.getText() != null ? KdPPKBPJS.getText() : "";
            String ppkKemKes = KdPPKKemKes.getText() != null ? KdPPKKemKes.getText() : "";
            String cleanTelpon = notelpinstansi != null ? notelpinstansi : "";
            String cleanAlamat = alamatinstansi != null ? alamatinstansi : "";
            String cleanKabupaten = kabupateninstansi != null ? kabupateninstansi : "";
            String cleanPropinsi = propinsiinstansi != null ? propinsiinstansi : "";

            ObjectNode organization = mapper.createObjectNode();
            organization.put("resourceType", "Organization");
            organization.put("id", id); // Use the actual ID from the location mapping

            // Identifiers
            ArrayNode identifiers = mapper.createArrayNode();
            identifiers.add(createIdentifier("official", "urn:oid:bpjs", ppkBPJS, null));
            identifiers.add(createIdentifier("official", "urn:oid:kemkes", ppkKemKes, null));
            organization.set("identifier", identifiers);

            // Type
            ArrayNode types = mapper.createArrayNode();
            types.add(createCodeableConcept("http://hl7.org/fhir/organization-type", "prov",
                    "Healthcare Provider", null));
            organization.set("type", types);

            // Name and Alias - use the location-based name
            String formattedName = "" + (locationName != null && !locationName.isEmpty() ? locationName : "Radiologi");
            organization.put("name", sanitizeString(formattedName));
            ArrayNode aliases = mapper.createArrayNode();
            if (alias != null && !alias.isEmpty()) {
                aliases.add(sanitizeString(alias)); // Use NmPPK as alias
            } else {
                aliases.add("RSCM");
            }
            organization.set("alias", aliases);

            // Telecom
            ArrayNode telecoms = mapper.createArrayNode();
            telecoms.add(createTelecom("phone", cleanTelpon, "work"));
            organization.set("telecom", telecoms);

            // Address
            ArrayNode addresses = mapper.createArrayNode();
            ObjectNode address = mapper.createObjectNode();
            address.put("use", "work");
            String fullAddress = sanitizeString(cleanAlamat + " " + cleanKabupaten + " " + cleanPropinsi);
            address.put("text", fullAddress);
            ArrayNode lines = mapper.createArrayNode();
            lines.add(fullAddress);
            address.set("line", lines);
            address.put("city", sanitizeString(cleanKabupaten));
            address.put("state", sanitizeString(cleanPropinsi));
            address.put("postalCode", "57322");
            address.put("country", "IDN");
            addresses.add(address);
            organization.set("address", addresses);

            // Contact
            ArrayNode contacts = mapper.createArrayNode();
            ObjectNode contact = mapper.createObjectNode();
            ObjectNode purpose = mapper.createObjectNode();
            ArrayNode purposeCoding = mapper.createArrayNode();
            purposeCoding.add(createCoding("http://terminology.hl7.org/CodeSystem/contactentity-type",
                    "ADMIN", null));
            purpose.set("coding", purposeCoding);
            contact.set("purpose", purpose);
            ArrayNode contactTelecoms = mapper.createArrayNode();
            contactTelecoms.add(createTelecom("phone", cleanTelpon, null));
            contact.set("telecom", contactTelecoms);
            contacts.add(contact);
            organization.set("contact", contacts);

            // Wrap in resource
            ObjectNode wrapper = mapper.createObjectNode();
            wrapper.set("resource", organization);
            jsonOrganization = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wrapper);

        } catch (Exception e) {
            System.out.println("Error creating specific Organization JSON: " + e.getMessage());
            e.printStackTrace();
            jsonOrganization = "";
        }
    }

    /**
     * Helper method to create a specific organization with location data
     *
     * @param id The ID from the location mapping (id_lokasi_satusehat)
     * @param locationName The location name (nm_poli or nm_bangsal)
     * @param alias The alias from NmPPK
     * @param ppkBPJS BPJS PPK code
     * @param ppkKemKes Kemkes PPK code
     * @param cleanTelpon Cleaned telephone number
     * @param cleanAlamat Cleaned address
     * @param cleanKabupaten Cleaned kabupaten
     * @param cleanPropinsi Cleaned propinsi
     */
    private ObjectNode createSpecificOrganization(String id, String locationName, String alias,
            String ppkBPJS, String ppkKemKes, String cleanTelpon, String cleanAlamat,
            String cleanKabupaten, String cleanPropinsi) {
        try {
            ObjectNode organization = mapper.createObjectNode();
            organization.put("resourceType", "Organization");
            // Use location-based ID following the required format
            organization.put("id", generateLocationSpecificId(locationName, ppkBPJS));

            // Identifiers
            ArrayNode identifiers = mapper.createArrayNode();
            identifiers.add(createIdentifier("official", "urn:oid:bpjs", ppkBPJS, null));
            identifiers.add(createIdentifier("official", "urn:oid:kemkes", ppkKemKes, null));
            organization.set("identifier", identifiers);

            // Type
            ArrayNode types = mapper.createArrayNode();
            types.add(createCodeableConcept("http://hl7.org/fhir/organization-type", "prov",
                    "Healthcare Provider", null));
            organization.set("type", types);

            // Name and Alias - use the location-specific name without "IGD - " prefix as per example
            // According to the example, the name should be the actual location name like "KLINIK BEDAH" or "MAWAR 2"
            String formattedName = (locationName != null && !locationName.isEmpty() ? locationName : "Radiologi");
            organization.put("name", sanitizeString(formattedName));
            ArrayNode aliases = mapper.createArrayNode();
            if (alias != null && !alias.isEmpty()) {
                aliases.add(sanitizeString(alias)); // Use NmPPK as alias
            } else {
                aliases.add("RSCM");
            }
            organization.set("alias", aliases);

            // Telecom
            ArrayNode telecoms = mapper.createArrayNode();
            telecoms.add(createTelecom("phone", cleanTelpon, "work"));
            organization.set("telecom", telecoms);

            // Address
            ArrayNode addresses = mapper.createArrayNode();
            ObjectNode address = mapper.createObjectNode();
            address.put("use", "work");
            String fullAddress = sanitizeString(cleanAlamat + " " + cleanKabupaten + " " + cleanPropinsi);
            address.put("text", fullAddress);
            ArrayNode lines = mapper.createArrayNode();
            lines.add(fullAddress);
            address.set("line", lines);
            address.put("city", sanitizeString(cleanKabupaten));
            address.put("state", sanitizeString(cleanPropinsi));
            address.put("postalCode", "57322");
            address.put("country", "IDN");
            addresses.add(address);
            organization.set("address", addresses);

            // Contact
            ArrayNode contacts = mapper.createArrayNode();
            ObjectNode contact = mapper.createObjectNode();
            ObjectNode purpose = mapper.createObjectNode();
            ArrayNode purposeCoding = mapper.createArrayNode();
            purposeCoding.add(createCoding("http://terminology.hl7.org/CodeSystem/contactentity-type",
                    "ADMIN", null));
            purpose.set("coding", purposeCoding);
            contact.set("purpose", purpose);
            ArrayNode contactTelecoms = mapper.createArrayNode();
            contactTelecoms.add(createTelecom("phone", cleanTelpon, null));
            contact.set("telecom", contactTelecoms);
            contacts.add(contact);
            organization.set("contact", contacts);

            return organization;

        } catch (Exception e) {
            System.out.println("Error creating specific Organization: " + e.getMessage());
            e.printStackTrace();
            return mapper.createObjectNode();
        }
    }

    /**
     * Helper method to create a default organization when no location data is
     * available
     *
     * @param ppkBPJS BPJS PPK code
     * @param ppkKemKes Kemkes PPK code
     * @param namaPPK Name of PPK
     * @param cleanTelpon Cleaned telephone number
     * @param cleanAlamat Cleaned address
     * @param cleanKabupaten Cleaned kabupaten
     * @param cleanPropinsi Cleaned propinsi
     */
    private ObjectNode createDefaultOrganization(String ppkBPJS, String ppkKemKes, String namaPPK,
            String cleanTelpon, String cleanAlamat, String cleanKabupaten, String cleanPropinsi) {
        try {
            ObjectNode organization = mapper.createObjectNode();
            organization.put("resourceType", "Organization");
            organization.put("id", generateResourceId(ppkBPJS));

            // Identifiers
            ArrayNode identifiers = mapper.createArrayNode();
            identifiers.add(createIdentifier("official", "urn:oid:bpjs", ppkBPJS, null));
            identifiers.add(createIdentifier("official", "urn:oid:kemkes", ppkKemKes, null));
            organization.set("identifier", identifiers);

            // Type
            ArrayNode types = mapper.createArrayNode();
            types.add(createCodeableConcept("http://hl7.org/fhir/organization-type", "prov",
                    "Healthcare Provider", null));
            organization.set("type", types);

            // Name and Alias - use default values from original method
            organization.put("name", sanitizeString(namaPPK));
            ArrayNode aliases = mapper.createArrayNode();
            aliases.add(sanitizeString(ppkBPJS + "-" + ppkKemKes));
            organization.set("alias", aliases);

            // Telecom
            ArrayNode telecoms = mapper.createArrayNode();
            telecoms.add(createTelecom("phone", cleanTelpon, "work"));
            organization.set("telecom", telecoms);

            // Address
            ArrayNode addresses = mapper.createArrayNode();
            ObjectNode address = mapper.createObjectNode();
            address.put("use", "work");
            String fullAddress = sanitizeString(cleanAlamat + " " + cleanKabupaten + " " + cleanPropinsi);
            address.put("text", fullAddress);
            ArrayNode lines = mapper.createArrayNode();
            lines.add(fullAddress);
            address.set("line", lines);
            address.put("city", sanitizeString(cleanKabupaten));
            address.put("state", sanitizeString(cleanPropinsi));
            address.put("postalCode", "57322");
            address.put("country", "IDN");
            addresses.add(address);
            organization.set("address", addresses);

            // Contact
            ArrayNode contacts = mapper.createArrayNode();
            ObjectNode contact = mapper.createObjectNode();
            ObjectNode purpose = mapper.createObjectNode();
            ArrayNode purposeCoding = mapper.createArrayNode();
            purposeCoding.add(createCoding("http://terminology.hl7.org/CodeSystem/contactentity-type",
                    "ADMIN", null));
            purpose.set("coding", purposeCoding);
            contact.set("purpose", purpose);
            ArrayNode contactTelecoms = mapper.createArrayNode();
            contactTelecoms.add(createTelecom("phone", cleanTelpon, null));
            contact.set("telecom", contactTelecoms);
            contacts.add(contact);
            organization.set("contact", contacts);

            return organization;

        } catch (Exception e) {
            System.out.println("Error creating default Organization: " + e.getMessage());
            e.printStackTrace();
            return mapper.createObjectNode();
        }
    }

    /**
     * Helper method to generate a specific ID for a location following the
     * required format
     *
     * @param locationName Name of the location (e.g., "KLINIK BEDAH", "MAWAR
     * 2")
     * @param ppkBPJS PPK code from BPJS
     * @return A formatted ID string following the required pattern
     */
    private String generateLocationSpecificId(String locationName, String ppkBPJS) {
        try {
            // Generate a UUID for the unique part
            String uuid = java.util.UUID.randomUUID().toString();

            // Extract or create a short code based on the location name
            String locationCode = locationName != null ? locationName.toUpperCase() : "DEFAULT";
            // Remove spaces and take the first 3 letters as code, or create an acronym
            String[] nameParts = locationCode.split(" ");
            StringBuilder acronym = new StringBuilder();
            for (String part : nameParts) {
                if (part.length() > 0) {
                    acronym.append(part.charAt(0));
                }
            }

            // If the acronym is too short, use first 3 chars of location name
            String finalCode = acronym.length() >= 3 ? acronym.substring(0, 3)
                    : locationCode.length() >= 3 ? locationCode.substring(0, 3) : locationCode;

            // Create the format: ppkBPJS-locationCode-uuid
            return ppkBPJS + "-" + finalCode + "-" + uuid;
        } catch (Exception e) {
            System.out.println("Error generating location-specific ID: " + e.getMessage());
            return generateResourceId(ppkBPJS); // fallback to default ID generation
        }
    }

// =============================================================================
// 2. PATIENT RESOURCE
// =============================================================================
    private void setJsonPatien() {
        try {
            // Marital status maping
            Map<String, String> maritalStatusMap = new HashMap<>();
            maritalStatusMap.put("BELUM MENIKAH", "S");
            maritalStatusMap.put("MENIKAH", "M");
            maritalStatusMap.put("JANDA", "W");
            maritalStatusMap.put("DUDHA", "D");
            maritalStatusMap.put("JOMBLO", "U");

            // Validate and set defaults
            if (noMR == null || noMR.trim().isEmpty()) {
                noMR = "NOMR001";
            }
            if (nokaBPJS == null || nokaBPJS.trim().isEmpty()) {
                nokaBPJS = "NOKA001";
            }
            if (nikPasien == null || nikPasien.trim().isEmpty()) {
                nikPasien = "NIK001";
            }

            String hl7Code = maritalStatusMap.getOrDefault(maritalStatus, "U");
            String cleanNoMR = sanitizeString(TNoRM.getText());
            String cleanNoKartu = sanitizeString(nokaBPJS);
            String cleanNIK = sanitizeString(nikPasien);
            String cleanPatientName = sanitizeString(TPasien.getText());
            String cleanPPKName = sanitizeString(NmPPK.getText());
            String cleanNoSEP = sanitizeString(TNoSEP.getText());

            // Get patient details
            String patientGender = sanitizeString(Sequel.cariIsi(
                    "SELECT CASE WHEN jk = 'L' THEN 'male' WHEN jk = 'P' THEN 'female' ELSE 'unknown' END FROM pasien WHERE no_rkm_medis='" + cleanNoMR + "'"));
            String patientBirthDate = sanitizeString(Sequel.cariIsi("SELECT tgl_lahir FROM pasien WHERE no_rkm_medis='" + cleanNoMR + "'"));
            String patientAddress = sanitizeString(Sequel.cariIsi("SELECT alamat FROM pasien WHERE no_rkm_medis='" + cleanNoMR + "'"));
            String patientCity = sanitizeString(Sequel.cariIsi("SELECT kabupaten.nm_kab FROM pasien INNER JOIN kabupaten ON pasien.kd_kab=kabupaten.kd_kab WHERE pasien.no_rkm_medis='" + cleanNoMR + "'"));
            String patientDistrict = sanitizeString(Sequel.cariIsi("SELECT kelurahan.nm_kel FROM pasien INNER JOIN kelurahan ON pasien.kd_kel=kelurahan.kd_kel WHERE pasien.no_rkm_medis='" + cleanNoMR + "'"));
            String patientState = sanitizeString(Sequel.cariIsi("SELECT propinsi.nm_prop FROM pasien INNER JOIN propinsi ON pasien.kd_prop=propinsi.kd_prop WHERE pasien.no_rkm_medis='" + cleanNoMR + "'"));

            // Fallbacks
            if (patientGender.isEmpty()) {
                patientGender = "unknown";
            }
            if (patientBirthDate.isEmpty()) {
                patientBirthDate = "1900-01-01";
            }
            if (patientAddress.isEmpty()) {
                patientAddress = "Alamat tidak ditemukan";
            }
            if (patientCity.isEmpty()) {
                patientCity = "Kota tidak ditemukan";
            }
            if (patientDistrict.isEmpty()) {
                patientDistrict = "Kecamatan tidak ditemukan";
            }
            if (patientState.isEmpty()) {
                patientState = "Provinsi tidak ditemukan";
            }

            ObjectNode patient = mapper.createObjectNode();
            patient.put("resourceType", "Patient");
            // Use the noKartu variable as the primary ID source, following the requirement
            patient.put("id", generateResourceIdForPatient(cleanNoKartu, cleanNoSEP));

            // Identifiers
            ArrayNode identifiers = mapper.createArrayNode();

            ObjectNode mrIdentifier = mapper.createObjectNode();
            mrIdentifier.put("use", "usual");
            ObjectNode mrType = mapper.createObjectNode();
            ArrayNode mrTypeCoding = mapper.createArrayNode();
            mrTypeCoding.add(createCoding("http://hl7.org/fhir/v2/0203", "MR", null));
            mrType.set("coding", mrTypeCoding);
            mrIdentifier.set("type", mrType);
            mrIdentifier.put("value", cleanNoMR);
            ObjectNode mrAssigner = mapper.createObjectNode();
            mrAssigner.put("display", cleanPPKName);
            mrIdentifier.set("assigner", mrAssigner);
            identifiers.add(mrIdentifier);

            ObjectNode bpjsIdentifier = mapper.createObjectNode();
            bpjsIdentifier.put("use", "official");
            ObjectNode bpjsType = mapper.createObjectNode();
            ArrayNode bpjsTypeCoding = mapper.createArrayNode();
            bpjsTypeCoding.add(createCoding("http://hl7.org/fhir/v2/0203", "MB", null));
            bpjsType.set("coding", bpjsTypeCoding);
            bpjsIdentifier.set("type", bpjsType);
            bpjsIdentifier.put("value", cleanNoKartu);
            ObjectNode bpjsAssigner = mapper.createObjectNode();
            bpjsAssigner.put("display", "BPJS KESEHATAN");
            bpjsIdentifier.set("assigner", bpjsAssigner);
            identifiers.add(bpjsIdentifier);

            ObjectNode nikIdentifier = mapper.createObjectNode();
            nikIdentifier.put("use", "official");
            ObjectNode nikType = mapper.createObjectNode();
            ArrayNode nikTypeCoding = mapper.createArrayNode();
            nikTypeCoding.add(createCoding("http://hl7.org/fhir/v2/0203", "NNIDN", null));
            nikType.set("coding", nikTypeCoding);
            nikIdentifier.set("type", nikType);
            nikIdentifier.put("value", cleanNIK);
            ObjectNode nikAssigner = mapper.createObjectNode();
            nikAssigner.put("display", "KEMENDAGRI");
            nikIdentifier.set("assigner", nikAssigner);
            identifiers.add(nikIdentifier);

            patient.set("identifier", identifiers);

            // Active
            patient.put("active", true);

            // Name
            ArrayNode names = mapper.createArrayNode();
            ObjectNode name = mapper.createObjectNode();
            name.put("use", "official");
            name.put("text", cleanPatientName);
            names.add(name);
            patient.set("name", names);

            // Marital Status
            patient.set("maritalStatus", createCodeableConcept("http://hl7.org/fhir/v3/MaritalStatus",
                    hl7Code, null, null));

            // Telecom
            ArrayNode telecoms = mapper.createArrayNode();
            telecoms.add(createTelecom("phone", sanitizeString(noTelpPasien), "mobile"));
            patient.set("telecom", telecoms);

            // Gender and Birth Date
            patient.put("gender", patientGender);
            patient.put("birthDate", patientBirthDate);
            patient.put("deceasedBoolean", false);

            // Address
            ArrayNode addresses = mapper.createArrayNode();
            ObjectNode address = mapper.createObjectNode();
            ArrayNode lines = mapper.createArrayNode();
            lines.add(patientAddress);
            address.set("line", lines);
            address.put("city", "");
            if (patientDistrict.isEmpty()) {
                address.put("district", "");
            } else {
                address.put("district", "KABUPATEN " + patientDistrict);
            }
            address.put("state", "");
            address.put("postalCode", "");
            if (patientDistrict.isEmpty()) {
                address.put("text", patientAddress);
            } else {
                address.put("text", patientAddress + " " + patientDistrict);
            }
            address.put("use", "home");
            address.put("type", "both");
            addresses.add(address);
            patient.set("address", addresses);

            // Managing Organization
            patient.set("managingOrganization", createReference(
                    "Organization/" + generateResourceId(sanitizeString(KdPPKBPJS.getText())),
                    cleanPPKName));

            //System.out.println("Data Json Pasien : "+Valid.getPrettyJson(patient.toString()));
            // Wrap in resource object to ensure proper format
            ObjectNode wrapper = mapper.createObjectNode();

            wrapper.set("resource", patient);
            jsonPatient = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wrapper);

        } catch (Exception e) {
            System.out.println("Error creating Patient JSON: " + e.getMessage());
            e.printStackTrace();
            jsonPatient = "{}";
        }
    }

// =============================================================================
// 3. ENCOUNTER RESOURCE
// =============================================================================
    private void setJsonEncounter() {
        try {
            String cleanNoSEP = sanitizeString(TNoSEP.getText());
            String cleanNoRM = sanitizeString(NoKaPasien.getText());
            String cleanPatientName = sanitizeString(TPasien.getText());
            String cleanStatusRawat = sanitizeString(lblStatusRawat.getText());

            String codediagnosamasuksep = Sequel.cariIsi("select diagawal from bridging_sep where no_sep='" + cleanNoSEP + "'");
            String namadiagnosaawalsep = Sequel.cariIsi("select nmdiagnosaawal from bridging_sep where no_sep='" + cleanNoSEP + "'");

            String referalnumberseprujukan = Sequel.cariIsi("select no_rujukan from bridging_sep where no_sep='" + cleanNoSEP + "'");
            String referalnumbersepkontrol = Sequel.cariIsi("select noskdp from bridging_sep where no_sep='" + cleanNoSEP + "'");
            if (rootPaneCheckingEnabled) {

            }

            ObjectNode encounter = mapper.createObjectNode();
            encounter.put("resourceType", "Encounter");
            // Use the noSEP for encounter ID following the requirement
            encounter.put("id", generateResourceIdForEncounter(cleanNoSEP));

            // Identifier
            ArrayNode identifiers = mapper.createArrayNode();
            ObjectNode identifier = mapper.createObjectNode();
            identifier.put("system", koneksiDB.URLAPIBPJS() + "/SEP/");
            identifier.put("value", cleanNoSEP);
            identifiers.add(identifier);
            encounter.set("identifier", identifiers);

            // Subject
            ObjectNode subject = mapper.createObjectNode();
            // Use the patient ID based on noKartu for consistency
            subject.put("reference", "Patient/" + generateResourceIdForPatient(sanitizeString(nokaBPJS), cleanNoSEP));
            subject.put("display", cleanPatientName);
            encounter.set("subject", subject);

            // Class
            String classCode = cleanStatusRawat.contains("Ranap") || cleanStatusRawat.contains("1.") ? "IMP" : "AMB";
            String classDisplay = classCode.equals("IMP") ? "inpatient encounter" : "outpatient encounter";
            ObjectNode encounterClass = mapper.createObjectNode();
            encounterClass.put("system", "http://hl7.org/fhir/v3/ActCode");
            encounterClass.put("code", classCode);
            encounterClass.put("display", classDisplay);
            encounter.set("class", encounterClass);

            // Incoming Referral
            ArrayNode incomingReferrals = mapper.createArrayNode();
            ObjectNode referral = mapper.createObjectNode();
            ArrayNode referralIdentifiers = mapper.createArrayNode();
            ObjectNode refId1 = mapper.createObjectNode();
            refId1.put("system", "nomor_rujukan_bpjs");
            refId1.put("value", referalnumberseprujukan.isEmpty() ? "diehr belum disimpan" : referalnumberseprujukan);
            referralIdentifiers.add(refId1);
            ObjectNode refId2 = mapper.createObjectNode();
            refId2.put("system", "nomor_rujukan_internal_rs");
            refId2.put("value", referalnumbersepkontrol.isEmpty() ? "belum di buat" : referalnumbersepkontrol);
            referralIdentifiers.add(refId2);
            referral.set("identifier", referralIdentifiers);
            incomingReferrals.add(referral);
            encounter.set("incomingReferral", incomingReferrals);

            // Reason - get diagnosis text from SEP
            ArrayNode reasons = mapper.createArrayNode();
            ObjectNode reason = mapper.createObjectNode();
            ArrayNode reasonCoding = mapper.createArrayNode();
            ObjectNode reasonCode = mapper.createObjectNode();
            reasonCode.put("code", codediagnosamasuksep);
            reasonCode.put("display", namadiagnosaawalsep);
            reasonCode.put("system", "http://hl7.org/fhir/sid/icd-10");
            reasonCoding.add(reasonCode);
            reason.set("coding", reasonCoding);
            reason.put("text", "[" + codediagnosamasuksep + "] " + namadiagnosaawalsep);
            reasons.add(reason);
            encounter.set("reason", reasons);

            // Diagnosis - reference conditions created from setJsonCondition
            ArrayNode diagnoses = mapper.createArrayNode();
            for (int i = 0; i < tbDiagnosaPasien.getRowCount(); i++) {
                if (tbDiagnosaPasien.getValueAt(i, 5) != null && tbDiagnosaPasien.getValueAt(i, 6) != null) {
                    ObjectNode diagnosis = mapper.createObjectNode();
                    ObjectNode condition = mapper.createObjectNode();

                    // Get the diagnosis code to use for consistent ID generation
                    String conditionCode = tbDiagnosaPasien.getValueAt(i, 5) != null
                            ? sanitizeString(tbDiagnosaPasien.getValueAt(i, 5).toString()) : "";
                    String conditionDisplay = tbDiagnosaPasien.getValueAt(i, 6) != null
                            ? sanitizeString(tbDiagnosaPasien.getValueAt(i, 6).toString()) : "";

                    // Get SNOMED code from the additional columns (if available from our updated tampilDiagnosa)
                    String conditionSnomedCode = tbDiagnosaPasien.getValueAt(i, 12) != null
                            ? sanitizeString(tbDiagnosaPasien.getValueAt(i, 12).toString()) : "";

                    // Use the same logic as setJsonCondition for consistent ID generation
                    String conditionIdCode = conditionSnomedCode.isEmpty() ? conditionCode : conditionSnomedCode;

                    // Reference the condition using the diagnosis code to ensure consistency with setJsonCondition
                    condition.put("reference", "Condition/" + generateResourceIdForCondition(cleanNoSEP, String.valueOf(i + 1)));

                    // Set up the diagnosis role
                    ObjectNode role = mapper.createObjectNode();
                    ArrayNode roleCoding = mapper.createArrayNode();
                    ObjectNode roleCode = mapper.createObjectNode();
                    roleCode.put("system", "http://hl7.org/fhir/diagnosis-role");
                    roleCode.put("code", "DD"); // Discharge Diagnosis as per official format
                    roleCode.put("display", "Discharge Diagnosis");
                    roleCoding.add(roleCode);
                    role.set("coding", roleCoding);
                    condition.set("role", role);
                    condition.put("rank", i + 1);
                    diagnosis.set("condition", condition);
                    diagnoses.add(diagnosis);
                }
            }
            encounter.set("diagnosis", diagnoses);

            // Hospitalization
            ObjectNode hospitalization = mapper.createObjectNode();
            ArrayNode dischargeDispositions = mapper.createArrayNode();
            ObjectNode disposition = mapper.createObjectNode();
            ArrayNode dispCoding = mapper.createArrayNode();
            ObjectNode dispCode = mapper.createObjectNode();
            dispCode.put("code", "home");
            dispCode.put("display", "Home");
            dispCode.put("system", "http://hl7.org/fhir/discharge-disposition");
            dispCoding.add(dispCode);
            disposition.set("coding", dispCoding);
            dischargeDispositions.add(disposition);
            hospitalization.set("dischargeDisposition", dischargeDispositions);
            encounter.set("hospitalization", hospitalization);

            // Period
            String masuk = "";
            String keluar = "";
            for (int i = 0; i < tbLamaPelayanan.getRowCount(); i++) {
                if (tbLamaPelayanan.getValueAt(i, 1) != null && tbLamaPelayanan.getValueAt(i, 2) != null) {
                    String date = tbLamaPelayanan.getValueAt(i, 1).toString();
                    String timeIn = tbLamaPelayanan.getValueAt(i, 2).toString();
                    String timeOut = tbLamaPelayanan.getValueAt(i, 4) != null
                            ? tbLamaPelayanan.getValueAt(i, 4).toString() : "";

                    if (i == 0) {
                        masuk = date + " " + timeIn;
                        if (!timeOut.isEmpty()) {
                            // Check if timeOut is in date+time format or just time format
                            if (timeOut.contains("-")) { // Contains date
                                keluar = timeOut;
                            } else { // Just time, so add the date
                                keluar = date + " " + timeOut;
                            }
                        }
                    }
                }
            }

            ObjectNode period = mapper.createObjectNode();
            // Format as "YYYY-MM-DD HH:MM:SS" - if no time component exists, append "00:00:00"
            String formattedStart = masuk;
            if (!masuk.contains(":")) {
                formattedStart = masuk + " 00:00:00";
            } else if (!masuk.substring(masuk.lastIndexOf(" ") + 1).contains(":")) {
                // If time format is missing seconds, append ":00"
                formattedStart = masuk + ":00";
            }
            period.put("start", formattedStart);

            String formattedEnd = keluar;
            if (!keluar.isEmpty()) {
                if (!keluar.contains(":")) {
                    formattedEnd = keluar + " 00:00:00";
                } else if (!keluar.substring(keluar.lastIndexOf(" ") + 1).contains(":")) {
                    // If time format is missing seconds, append ":00"
                    formattedEnd = keluar + ":00";
                }
            } else {
                formattedEnd = formattedStart; // Use same if end not available
            }
            period.put("end", formattedEnd);
            encounter.set("period", period);

            // Status
            encounter.put("status", "finished");

            // Text
            ObjectNode text = mapper.createObjectNode();
            text.put("div", "<div>Admitted to " + sanitizeString(NmPPK.getText()) + " between " + sanitizeString(masuk)
                    + " and " + sanitizeString(keluar) + "</div>");
            text.put("status", "generated");
            encounter.set("text", text);

            // Wrap in resource object to ensure proper format
            ObjectNode wrapper = mapper.createObjectNode();
            wrapper.set("resource", encounter);
            jsonEncounter = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wrapper);

        } catch (Exception e) {
            System.out.println("Error creating Encounter JSON: " + e.getMessage());
            e.printStackTrace();
            jsonEncounter = "{}";
        }
    }

// =============================================================================
// 4. COMPOSITION RESOURCE
// =============================================================================
    private void setJsonComposition() {
        try {
            if (tbResumePasien.getRowCount() == 0) {
                jsonComposititon = "{}";
                return;
            }

            // Get data from first row with null checks
            String plan = getSafeString(tbResumePasien, 0, 5, "");
            String date = getSafeString(tbResumePasien, 0, 0, "");
            String time = getSafeString(tbResumePasien, 0, 1, "");
            String practitioner = getSafeString(tbResumePasien, 0, 8, "");
            String chiefComplaint = getSafeString(tbResumePasien, 0, 2, "");
            String admissionDiagnosis = getSafeString(tbDiagnosaPasien, 0, 6, "No diagnosis");
            String KeluhanUtama = getSafeString(tbResumePasien, 0, 2, "No diagnosis");
            String noktp = getSafeString(tbResumePasien, 0, 9, "");
            String keteranganpulang = getSafeString(tbResumePasien, 0, 5, "Sehat") + " " + getSafeString(tbResumePasien, 0, 6, "Sehat");

            // Build discharge medications from tbTebusObat data
            StringBuilder dischargeMedicationsBuilder = new StringBuilder();
            String dischargeMedicationscode = "75311-1"; // Default code if no medications found

            // Loop through all rows in tbTebusObat to get all medications
            for (int i = 0; i < tbTebusObat.getRowCount(); i++) {
                String medicationCode = getSafeString(tbTebusObat, i, 3, "");
                String medicationDisplay = getSafeString(tbTebusObat, i, 6, "");
                String medicationDose = getSafeString(tbTebusObat, i, 16, "");
                String medicationAturan = getSafeString(tbTebusObat, i, 18, "");

                if (!medicationDisplay.isEmpty()) {
                    if (dischargeMedicationsBuilder.length() > 0) {
                        dischargeMedicationsBuilder.append("<br/>"); // Add line break between medications
                    }

                    // Build medication string with display name and additional info
                    dischargeMedicationsBuilder.append(medicationDisplay);
                    if (!medicationDose.isEmpty()) {
                        dischargeMedicationsBuilder.append(" (Dosis: ").append(medicationDose).append(")");
                    }
                    if (!medicationAturan.isEmpty()) {
                        dischargeMedicationsBuilder.append(" (Aturan: ").append(medicationAturan).append(")");
                    }

                    // Use the first medication's code as the section code, or keep default if not available
                    if (i == 0 && !medicationCode.isEmpty()) {
                        dischargeMedicationscode = medicationCode;
                    }
                }
            }

            String dischargeMedications = dischargeMedicationsBuilder.toString();
            if (dischargeMedications.isEmpty()) {
                dischargeMedications = "No medications prescribed"; // Default if no medications found
            }

            // Build laboratory report from tbLabPasien data
            StringBuilder labReportBuilder = new StringBuilder();
            String labReportCode = "11502-2"; // LOINC code for Laboratory report
            String labReportDisplay = "Laboratory report";

            // Loop through all rows in tbLabPasien to get all lab results
            for (int i = 0; i < tbLabPasien.getRowCount(); i++) {
                String tglPeriksa = getSafeString(tbLabPasien, i, 0, "");
                String jam = getSafeString(tbLabPasien, i, 1, "");
                String nmPerawatan = getSafeString(tbLabPasien, i, 2, "");
                String pemeriksaan = getSafeString(tbLabPasien, i, 3, "");
                String nilai = getSafeString(tbLabPasien, i, 8, "");
                String nilaiRujukan = getSafeString(tbLabPasien, i, 9, "");
                String keterangan = getSafeString(tbLabPasien, i, 10, "");

                if (!nmPerawatan.isEmpty()) {
                    if (labReportBuilder.length() > 0) {
                        labReportBuilder.append("<br/>"); // Add line break between lab results
                    }

                    // Build lab report string with test info
                    labReportBuilder.append("Tanggal: ").append(tglPeriksa).append(" ").append(jam)
                            .append(" | Pemeriksaan: ").append(nmPerawatan)
                            .append(" | Hasil: ").append(nilai);

                    if (!nilaiRujukan.isEmpty()) {
                        labReportBuilder.append(" (Rujukan: ").append(nilaiRujukan).append(")");
                    }
                    if (!pemeriksaan.isEmpty()) {
                        labReportBuilder.append(" | Detail: ").append(pemeriksaan);
                    }
                    if (!keterangan.isEmpty()) {
                        labReportBuilder.append(" | Keterangan: ").append(keterangan);
                    }
                }
            }

            String labReport = labReportBuilder.toString();
            if (labReport.isEmpty()) {
                labReport = "No laboratory results available"; // Default if no lab results found
            }

            // Build radiology report from tbRadPasien data
            StringBuilder radReportBuilder = new StringBuilder();
            String radReportCode = "18748-4"; // LOINC code for Diagnostic imaging study
            String radReportDisplay = "Diagnostic imaging study";

            // Loop through all rows in tbRadPasien to get all radiology results
            for (int i = 0; i < tbRadiologiPasien.getRowCount(); i++) {
                String tglPeriksa = getSafeString(tbRadiologiPasien, i, 0, "");
                String jam = getSafeString(tbRadiologiPasien, i, 1, "");
                String nmPerawatan = getSafeString(tbRadiologiPasien, i, 2, "");
                String klinis = getSafeString(tbRadiologiPasien, i, 6, "");
                String kesan = getSafeString(tbRadiologiPasien, i, 7, "");
                String dokter = getSafeString(tbRadiologiPasien, i, 8, "");

                if (!nmPerawatan.isEmpty()) {
                    if (radReportBuilder.length() > 0) {
                        radReportBuilder.append(",<br/>"); // Add line break between radiology results
                    }

                    // Build radiology report string with imaging info
                    radReportBuilder.append("Tanggal: ").append(tglPeriksa).append(" ").append(jam)
                            .append(" | Pemeriksaan: ").append(nmPerawatan);

                    if (!klinis.isEmpty()) {
                        radReportBuilder.append(" | Klinis: ").append(klinis);
                    }
                    if (!kesan.isEmpty()) {
                        radReportBuilder.append(" | Kesimpulan: ").append(kesan);
                    }
                    if (!dokter.isEmpty()) {
                        radReportBuilder.append(" | Dokter: ").append(dokter);
                    }
                }
            }

            String radReport = radReportBuilder.toString();
            if (radReport.isEmpty()) {
                radReport = "No radiology results available"; // Default if no radiology results found
            }

            String codeAllergies = getSafeString(tbAlergi, 0, 10, "48765-2");
            String knownAllergies = getSafeString(tbAlergi, 0, 12, "No known allergies");

            // Create Composition object with LinkedHashMap to ensure field order
            ObjectNode composition = mapper.createObjectNode();
            composition.put("resourceType", "Composition");
            composition.put("id", generateResourceIdForComposition(sanitizeString(TNoSEP.getText())));
            composition.put("status", "final");

            // Type
            ObjectNode type = mapper.createObjectNode();
            ArrayNode typeCoding = mapper.createArrayNode();
            ObjectNode typeCode = mapper.createObjectNode();
            typeCode.put("system", "http://loinc.org");
            typeCode.put("code", "81218-0");
            typeCode.put("display", "Discharge Summary");
            typeCoding.add(typeCode);
            type.set("coding", typeCoding);
            type.put("text", "Discharge Summary");
            composition.set("type", type);

            // Subject
            ObjectNode subject = mapper.createObjectNode();
            subject.put("reference", "Patient/" + generateResourceIdForPatient(sanitizeString(nokaBPJS), sanitizeString(TNoSEP.getText())));
            subject.put("display", sanitizeString(TPasien.getText()));
            composition.set("subject", subject);

            // Encounter
            ObjectNode encounter = mapper.createObjectNode();
            encounter.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
            composition.set("encounter", encounter);

            // Date
            composition.put("date", date + " " + time);

            // Author
            ArrayNode authors = mapper.createArrayNode();
            ObjectNode author = mapper.createObjectNode();
            String practitionerId = Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat='" + TNoRw.getText() + "'");
            author.put("reference", "Practitioner/" + generateResourceIdForPractitioner(sanitizeString(practitionerId)));
            author.put("display", practitioner);
            authors.add(author);
            composition.set("author", authors);

            composition.put("title", "Discharge Summary");
            composition.put("confidentiality", "N");

            // Sections - using object to match example format
            ObjectNode sections = mapper.createObjectNode();

            // Add sections only if they have meaningful content
            if (KeluhanUtama != null && !KeluhanUtama.isEmpty() && !KeluhanUtama.equals("No diagnosis")) {
                ObjectNode section0 = mapper.createObjectNode();
                section0.put("title", "Reason for admission");
                ObjectNode section0Code = mapper.createObjectNode();
                ArrayNode section0CodeCoding = mapper.createArrayNode();
                ObjectNode section0CodeCodingObj = mapper.createObjectNode();
                section0CodeCodingObj.put("system", "http://loinc.org");
                section0CodeCodingObj.put("code", "29299-5");
                section0CodeCodingObj.put("display", "Reason for visit Narrative");
                section0CodeCoding.add(section0CodeCodingObj);
                section0Code.set("coding", section0CodeCoding);
                section0.set("code", section0Code);
                ObjectNode section0Text = mapper.createObjectNode();
                section0Text.put("status", "additional");
                section0Text.put("div", KeluhanUtama);
                section0.set("text", section0Text);
                ObjectNode section0Entry = mapper.createObjectNode();
                section0Entry.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
                section0.set("entry", section0Entry);
                sections.set("1", section0);
            }

            if (chiefComplaint != null && !chiefComplaint.isEmpty()) {
                ObjectNode section1 = mapper.createObjectNode();
                section1.put("title", "Chief complaint");
                ObjectNode section1Code = mapper.createObjectNode();
                ArrayNode section1CodeCoding = mapper.createArrayNode();
                ObjectNode section1CodeCodingObj = mapper.createObjectNode();
                section1CodeCodingObj.put("system", "http://loinc.org");
                section1CodeCodingObj.put("code", "10154-3");
                section1CodeCodingObj.put("display", "Chief complaint Narrative");
                section1CodeCoding.add(section1CodeCodingObj);
                section1Code.set("coding", section1CodeCoding);
                section1.set("code", section1Code);
                ObjectNode section1Text = mapper.createObjectNode();
                section1Text.put("status", "additional");
                section1Text.put("div", chiefComplaint);
                section1.set("text", section1Text);
                ObjectNode section1Entry = mapper.createObjectNode();
                section1Entry.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
                section1.set("entry", section1Entry);
                sections.set("2", section1);
            }

            if (admissionDiagnosis != null && !admissionDiagnosis.isEmpty() && !admissionDiagnosis.equals("No diagnosis")) {
                ObjectNode section2 = mapper.createObjectNode();
                section2.put("title", "Admission diagnosis");
                ObjectNode section2Code = mapper.createObjectNode();
                ArrayNode section2CodeCoding = mapper.createArrayNode();
                ObjectNode section2CodeCodingObj = mapper.createObjectNode();
                section2CodeCodingObj.put("system", "http://loinc.org");
                section2CodeCodingObj.put("code", "42347-5");
                section2CodeCodingObj.put("display", "Admission diagnosis Narrative");
                section2CodeCoding.add(section2CodeCodingObj);
                section2Code.set("coding", section2CodeCoding);
                section2.set("code", section2Code);
                ObjectNode section2Text = mapper.createObjectNode();
                section2Text.put("status", "additional");
                section2Text.put("div", admissionDiagnosis);
                section2.set("text", section2Text);
                ArrayNode section2Entry = mapper.createArrayNode();
                ObjectNode section2EntryRef = mapper.createObjectNode();
                section2EntryRef.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
                section2Entry.add(section2EntryRef);
                section2.set("entry", section2Entry);
                sections.set("3", section2);
            }

            // Add vital signs section (from tbResumePasien)
            String vitalSignsText = "TD : , N : x/menit S : ℃ RR : x/menit ";
            String kesadaran = getSafeString(tbResumePasien, 0, 9, "");
            String suhu = getSafeString(tbResumePasien, 0, 4, "");
            String tensi = getSafeString(tbResumePasien, 0, 5, "");
            String nadi = getSafeString(tbResumePasien, 0, 6, "");
            String rr = getSafeString(tbResumePasien, 0, 7, "");

            if (!suhu.isEmpty() || !tensi.isEmpty() || !nadi.isEmpty() || !rr.isEmpty() || !kesadaran.isEmpty()) {
                vitalSignsText = "TD : " + tensi + " mmHg N : " + nadi + " x/menit S : " + suhu + " ℃ RR : " + rr + " x/menit ";
                if (!kesadaran.isEmpty()) {
                    vitalSignsText += "Kesadaran : " + kesadaran + " ";
                }
            }

            ObjectNode section3 = mapper.createObjectNode();
            section3.put("title", "Vital signs finding (finding)");
            ObjectNode section3Code = mapper.createObjectNode();
            ArrayNode section3CodeCoding = mapper.createArrayNode();
            ObjectNode section3CodeCodingObj = mapper.createObjectNode();
            section3CodeCodingObj.put("system", "http://snomed.info/sct");
            section3CodeCodingObj.put("code", "118227000");
            section3CodeCodingObj.put("display", "Vital signs finding (finding)");
            section3CodeCoding.add(section3CodeCodingObj);
            section3Code.set("coding", section3CodeCoding);
            section3Code.putNull("text");
            section3.set("code", section3Code);
            ObjectNode section3Text = mapper.createObjectNode();
            section3Text.put("status", "additional");
            section3Text.put("div", getSafeString(tbResumePasien, 0, 3, ""));
            section3.set("text", section3Text);
            section3.put("mode", "working");
            ObjectNode section3Entry = mapper.createObjectNode();
            section3Entry.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
            section3.set("entry", section3Entry);
            sections.set("4", section3);

            // Add observation of vital signs section
            ObjectNode section4 = mapper.createObjectNode();
            section4.put("title", "Observation of vital signs");
            ObjectNode section4Code = mapper.createObjectNode();
            ArrayNode section4CodeCoding = mapper.createArrayNode();
            ObjectNode section4CodeCodingObj = mapper.createObjectNode();
            section4CodeCodingObj.put("system", "http://snomed.info/sct");
            section4CodeCodingObj.put("code", "61746007");
            section4CodeCodingObj.put("display", "Observation of vital signs");
            section4CodeCoding.add(section4CodeCodingObj);
            section4Code.set("coding", section4CodeCoding);
            section4Code.putNull("text");
            section4.set("code", section4Code);
            ObjectNode section4Text = mapper.createObjectNode();
            section4Text.put("status", "additional");
            section4Text.put("div", getSafeString(tbResumePasien, 0, 3, ""));
            section4.set("text", section4Text);
            section4.put("mode", "working");
            ObjectNode section4Entry = mapper.createObjectNode();
            section4Entry.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
            section4.set("entry", section4Entry);
            sections.set("5", section4);

            // Add treatment section
            ObjectNode section5 = mapper.createObjectNode();
            section5.put("title", "Treatment (record artifact)");
            ObjectNode section5Code = mapper.createObjectNode();
            ArrayNode section5CodeCoding = mapper.createArrayNode();
            ObjectNode section5CodeCodingObj = mapper.createObjectNode();
            section5CodeCodingObj.put("system", "http://snomed.info/sct");
            section5CodeCodingObj.put("code", "716151000000103");
            section5CodeCodingObj.put("display", "Treatment (record artifact)");
            section5CodeCoding.add(section5CodeCodingObj);
            section5Code.set("coding", section5CodeCoding);
            section5Code.putNull("text");
            section5.set("code", section5Code);
            ObjectNode section5Text = mapper.createObjectNode();
            section5Text.put("status", "additional");
            section5Text.put("div", "MEDIKAMENTOSA - - - - - - - ");
            section5.set("text", section5Text);
            section5.put("mode", "working");
            ObjectNode section5Entry = mapper.createObjectNode();
            section5Entry.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
            section5.set("entry", section5Entry);
            sections.set("6", section5);

            // Add discharge instructions section
            ObjectNode section6 = mapper.createObjectNode();
            section6.put("title", "Discharge instructions");
            ObjectNode section6Code = mapper.createObjectNode();
            ArrayNode section6CodeCoding = mapper.createArrayNode();
            ObjectNode section6CodeCodingObj = mapper.createObjectNode();
            section6CodeCodingObj.put("system", "http://loinc.org");
            section6CodeCodingObj.put("code", "74213-0");
            section6CodeCodingObj.put("display", "Discharge instructions");
            section6CodeCoding.add(section6CodeCodingObj);
            section6Code.set("coding", section6CodeCoding);
            section6Code.put("text", keteranganpulang);
            section6.set("code", section6Code);
            ObjectNode section6Text = mapper.createObjectNode();
            section6Text.put("status", "additional");
            section6Text.put("div", keteranganpulang);
            section6.set("text", section6Text);
            section6.put("mode", "working");
            ObjectNode section6Entry = mapper.createObjectNode();
            section6Entry.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
            section6.set("entry", section6Entry);
            sections.set("7", section6);

            // Add vital status at discharge section
            ObjectNode section7 = mapper.createObjectNode();
            section7.put("title", "Vital status at discharge");
            ObjectNode section7Code = mapper.createObjectNode();
            ArrayNode section7CodeCoding = mapper.createArrayNode();
            ObjectNode section7CodeCodingObj = mapper.createObjectNode();
            section7CodeCodingObj.put("system", "http://loinc.org");
            section7CodeCodingObj.put("code", "75527-2");
            section7CodeCodingObj.put("display", "Vital status at discharge");
            section7CodeCoding.add(section7CodeCodingObj);
            section7Code.set("coding", section7CodeCoding);
            section7Code.putNull("text");
            section7.set("code", section7Code);
            ObjectNode section7Text = mapper.createObjectNode();
            section7Text.put("status", "additional");
            section7Text.put("div", "Ijin Dokter");
            section7.set("text", section7Text);
            section7.put("mode", "working");
            ObjectNode section7Entry = mapper.createObjectNode();
            section7Entry.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
            section7.set("entry", section7Entry);
            sections.set("8", section7);

            // Add radiology section based on tbRadiologiPasien data
            if (tbRadiologiPasien.getRowCount() > 0) {
                ObjectNode section8 = mapper.createObjectNode();
                section8.put("title", "Radiology procedure note");
                ObjectNode section8Code = mapper.createObjectNode();
                ArrayNode section8CodeCoding = mapper.createArrayNode();
                ObjectNode section8CodeCodingObj = mapper.createObjectNode();
                section8CodeCodingObj.put("system", "http://loinc.org");
                section8CodeCodingObj.put("code", "85261-6");
                section8CodeCodingObj.put("display", "Radiology procedure note");
                section8CodeCoding.add(section8CodeCodingObj);
                section8Code.set("coding", section8CodeCoding);
                section8Code.putNull("text");
                section8.set("code", section8Code);
                ObjectNode section8Text = mapper.createObjectNode();
                section8Text.put("status", "additional");
                section8Text.put("div", radReport.isEmpty() ? "TERLAMPIR" : radReport);
                section8.set("text", section8Text);
                section8.put("mode", "working");
                ObjectNode section8Entry = mapper.createObjectNode();
                section8Entry.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
                section8.set("entry", section8Entry);
                sections.set("9", section8);
            } else {
                ObjectNode section8 = mapper.createObjectNode();
                section8.put("title", "Radiology procedure note");
                ObjectNode section8Code = mapper.createObjectNode();
                ArrayNode section8CodeCoding = mapper.createArrayNode();
                ObjectNode section8CodeCodingObj = mapper.createObjectNode();
                section8CodeCodingObj.put("system", "http://loinc.org");
                section8CodeCodingObj.put("code", "85261-6");
                section8CodeCodingObj.put("display", "Radiology procedure note");
                section8CodeCoding.add(section8CodeCodingObj);
                section8Code.set("coding", section8CodeCoding);
                section8Code.putNull("text");
                section8.set("code", section8Code);
                ObjectNode section8Text = mapper.createObjectNode();
                section8Text.put("status", "additional");
                section8Text.put("div", "TERLAMPIR");
                section8.set("text", section8Text);
                section8.put("mode", "working");
                ObjectNode section8Entry = mapper.createObjectNode();
                section8Entry.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
                section8.set("entry", section8Entry);
                sections.set("9", section8);
            }

            // Add laboratory section based on tbLabPasien data
            if (tbLabPasien.getRowCount() > 0) {
                ObjectNode section9 = mapper.createObjectNode();
                section9.put("title", "Laboratory report");
                ObjectNode section9Code = mapper.createObjectNode();
                ArrayNode section9CodeCoding = mapper.createArrayNode();
                ObjectNode section9CodeCodingObj = mapper.createObjectNode();
                section9CodeCodingObj.put("system", "http://loinc.org");
                section9CodeCodingObj.put("code", "11502-2");
                section9CodeCodingObj.put("display", "Laboratory report");
                section9CodeCoding.add(section9CodeCodingObj);
                section9Code.set("coding", section9CodeCoding);
                section9Code.putNull("text");
                section9.set("code", section9Code);
                ObjectNode section9Text = mapper.createObjectNode();
                section9Text.put("status", "additional");
                section9Text.put("div", labReport.isEmpty() ? "TERLAMPIR" : labReport);
                section9.set("text", section9Text);
                section9.put("mode", "working");
                ObjectNode section9Entry = mapper.createObjectNode();
                section9Entry.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
                section9.set("entry", section9Entry);
                sections.set("10", section9);
            } else {
                ObjectNode section9 = mapper.createObjectNode();
                section9.put("title", "Laboratory report");
                ObjectNode section9Code = mapper.createObjectNode();
                ArrayNode section9CodeCoding = mapper.createArrayNode();
                ObjectNode section9CodeCodingObj = mapper.createObjectNode();
                section9CodeCodingObj.put("system", "http://loinc.org");
                section9CodeCodingObj.put("code", "11502-2");
                section9CodeCodingObj.put("display", "Laboratory report");
                section9CodeCoding.add(section9CodeCodingObj);
                section9Code.set("coding", section9CodeCoding);
                section9Code.putNull("text");
                section9.set("code", section9Code);
                ObjectNode section9Text = mapper.createObjectNode();
                section9Text.put("status", "additional");
                section9Text.put("div", "TERLAMPIR");
                section9.set("text", section9Text);
                section9.put("mode", "working");
                ObjectNode section9Entry = mapper.createObjectNode();
                section9Entry.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
                section9.set("entry", section9Entry);
                sections.set("10", section9);
            }

            // Add patient history section
            ObjectNode section10 = mapper.createObjectNode();
            section10.put("title", "Patient History Prior to this Current Illness, Exacerbation, or Injury");
            ObjectNode section10Code = mapper.createObjectNode();
            ArrayNode section10CodeCoding = mapper.createArrayNode();
            ObjectNode section10CodeCodingObj = mapper.createObjectNode();
            section10CodeCodingObj.put("system", "http://loinc.org");
            section10CodeCodingObj.put("code", "52538-6");
            section10CodeCodingObj.put("display", "Patient History Prior to this Current Illness, Exacerbation, or Injury");
            section10CodeCoding.add(section10CodeCodingObj);
            section10Code.set("coding", section10CodeCoding);
            section10Code.putNull("text");
            section10.set("code", section10Code);
            ObjectNode section10Text = mapper.createObjectNode();
            section10Text.put("status", "additional");
            section10Text.put("div", KeluhanUtama);
            section10.set("text", section10Text);
            section10.put("mode", "working");
            ObjectNode section10Entry = mapper.createObjectNode();
            section10Entry.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
            section10.set("entry", section10Entry);
            sections.set("11", section10);

            // Add discharge diagnosis section
            ObjectNode section11 = mapper.createObjectNode();
            section11.put("title", "Discharge diagnosis");
            ObjectNode section11Code = mapper.createObjectNode();
            ArrayNode section11CodeCoding = mapper.createArrayNode();
            ObjectNode section11CodeCodingObj = mapper.createObjectNode();
            section11CodeCodingObj.put("system", "http://loinc.org");
            section11CodeCodingObj.put("code", "42347-5");
            section11CodeCodingObj.put("display", "Discharge diagnosis Narrative");
            section11CodeCoding.add(section11CodeCodingObj);
            section11Code.set("coding", section11CodeCoding);
            section11Code.putNull("text");
            section11.set("code", section11Code);
            ObjectNode section11Text = mapper.createObjectNode();
            section11Text.put("status", "additional");
            section11Text.put("div", admissionDiagnosis);
            section11.set("text", section11Text);
            section11.put("mode", "working");
            ObjectNode section11Entry = mapper.createObjectNode();
            section11Entry.put("reference", "Encounter/" + generateResourceIdForEncounter(sanitizeString(TNoSEP.getText())));
            section11.set("entry", section11Entry);
            sections.set("12", section11);

            // Add medications on discharge section (section 13) based on the actual medication requests created
            int sectionCounter = 13;

            // Create medications on discharge section with references to actual medication requests
            ObjectNode medSection = mapper.createObjectNode();
            medSection.put("title", "Medications on Discharge");
            ObjectNode medSectionCode = mapper.createObjectNode();
            ArrayNode medSectionCodeCoding = mapper.createArrayNode();
            ObjectNode medSectionCodeCodingObj = mapper.createObjectNode();
            medSectionCodeCodingObj.put("system", "http://loinc.org");
            medSectionCodeCodingObj.put("code", "R0184");
            medSectionCodeCodingObj.put("display", "Hospital discharge medications Narrative");
            medSectionCodeCoding.add(medSectionCodeCodingObj);
            medSectionCode.set("coding", medSectionCodeCoding);
            medSection.set("code", medSectionCode);

            // Use medication data from tbResepDokter to populate text content
            StringBuilder medTextBuilder = new StringBuilder();
            for (int i = 0; i < tbResepDokter.getRowCount(); i++) {
                String medicationText = getSafeString(tbResepDokter, i, 6, "");
                if (!medicationText.isEmpty()) {
                    if (medTextBuilder.length() > 0) {
                        medTextBuilder.append("<br/>");
                    }
                    medTextBuilder.append(medicationText);
                }
            }
            String medSectionTextContent = medTextBuilder.length() > 0 ? medTextBuilder.toString() : "No medications prescribed";

            ObjectNode medSectionText = mapper.createObjectNode();
            medSectionText.put("status", "additional");
            medSectionText.put("div", medSectionTextContent);
            medSection.set("text", medSectionText);
            medSection.put("mode", "working");

            sections.set(String.valueOf(sectionCounter), medSection);

            composition.set("section", sections);

            // Wrap
            ObjectNode wrapper = mapper.createObjectNode();
            wrapper.set("resource", composition);
            jsonComposititon = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wrapper);

        } catch (Exception e) {
            System.out.println("Error creating Composition JSON: " + e.getMessage());
            e.printStackTrace();
            jsonComposititon = "{}";
        }
    }

    private void setJsonCondition() {
        try {
            ArrayNode conditions = mapper.createArrayNode();

            // Get admission time from first period entry
            String masuk = "";
            if (tbLamaPelayanan.getRowCount() > 0 && tbLamaPelayanan.getValueAt(0, 1) != null && tbLamaPelayanan.getValueAt(0, 2) != null) {
                masuk = tbLamaPelayanan.getValueAt(0, 1).toString() + " " + tbLamaPelayanan.getValueAt(0, 2).toString();
            }

            for (int i = 0; i < tbDiagnosaPasien.getRowCount(); i++) {
                String conditionSnomedCode = (tbDiagnosaPasien.getValueAt(i, 12) != null)
                        ? sanitizeString(tbDiagnosaPasien.getValueAt(i, 12).toString()) : "";
                String conditionSnomedDisplay = (tbDiagnosaPasien.getValueAt(i, 13) != null)
                        ? sanitizeString(tbDiagnosaPasien.getValueAt(i, 13).toString()) : "";
                String conditionSnomedSystem = (tbDiagnosaPasien.getValueAt(i, 14) != null)
                        ? sanitizeString(tbDiagnosaPasien.getValueAt(i, 14).toString()) : "";

                // Fallback to ICD-10 if SNOMED is not available
                if (conditionSnomedCode.isEmpty() || conditionSnomedDisplay.isEmpty()) {
                    conditionSnomedCode = (tbDiagnosaPasien.getValueAt(i, 5) != null)
                            ? sanitizeString(tbDiagnosaPasien.getValueAt(i, 5).toString()) : "";
                    conditionSnomedDisplay = (tbDiagnosaPasien.getValueAt(i, 6) != null)
                            ? sanitizeString(tbDiagnosaPasien.getValueAt(i, 6).toString()) : "";
                    conditionSnomedSystem = "http://hl7.org/fhir/sid/icd-10";
                }

                if (conditionSnomedCode.isEmpty() || conditionSnomedDisplay.isEmpty()) {
                    System.out.println("Warning: Condition " + i + " has empty required fields, skipping");
                    continue;
                }

                ObjectNode condition = mapper.createObjectNode();
                condition.put("resourceType", "Condition");
                // Use the noSEP and rank as the unique ID following the requirement
                condition.put("id", generateResourceIdForCondition(sanitizeString(TNoSEP.getText()), String.valueOf(i + 1)));
                condition.put("clinicalStatus", "active");
                condition.put("verificationStatus", "confirmed");

                // Category
                ArrayNode categories = mapper.createArrayNode();
                ObjectNode category = mapper.createObjectNode();
                ArrayNode categoryCoding = mapper.createArrayNode();
                ObjectNode categoryCodingObj = mapper.createObjectNode();
                categoryCodingObj.put("system", "http://hl7.org/fhir/condition-category");
                categoryCodingObj.put("code", "encounter-diagnosis");
                categoryCodingObj.put("display", "Encounter Diagnosis");
                categoryCoding.add(categoryCodingObj);
                category.set("coding", categoryCoding);
                categories.add(category);
                condition.set("category", categories);

                // Code
                ObjectNode code = mapper.createObjectNode();
                ArrayNode codingArray = mapper.createArrayNode();
                ObjectNode coding = mapper.createObjectNode();
                coding.put("system", conditionSnomedSystem);
                coding.put("code", conditionSnomedCode);
                coding.put("display", conditionSnomedDisplay);
                codingArray.add(coding);
                code.set("coding", codingArray);
                code.put("text", "[" + conditionSnomedCode + "] " + conditionSnomedDisplay);
                condition.set("code", code);

                // Subject
                condition.set("subject", createReference(
                        "Patient/" + generateResourceIdForPatient(sanitizeString(nokaBPJS), sanitizeString(TNoSEP.getText())),
                        null
                ));

                // Format as "YYYY-MM-DD HH:MM:SS" - if no time component exists, append "00:00:00"
                String formattedOnsetDateTime = masuk;
                if (!masuk.contains(":")) {
                    formattedOnsetDateTime = masuk + " 00:00:00";
                } else if (!masuk.substring(masuk.lastIndexOf(" ") + 1).contains(":")) {
                    // If time format is missing seconds, append ":00"
                    formattedOnsetDateTime = masuk + ":00";
                }
                condition.put("onsetDateTime", formattedOnsetDateTime);

                conditions.add(condition);
            }

            ObjectNode wrapper = mapper.createObjectNode();
            wrapper.set("resource", conditions);
            jsonCondition = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wrapper);

        } catch (Exception e) {
            System.out.println("Error creating Condition JSON: " + e.getMessage());
            e.printStackTrace();
            jsonCondition = "{}";
        }
    }

    private void setJsonProcedure() {
        try {
            ArrayNode procedures = mapper.createArrayNode();

            for (int i = 0; i < tbTindakanPasien.getRowCount(); i++) {
                // Updated column indices based on your table structure
                String procedureSnomedCode = getSafeString(tbTindakanPasien, i, 10, ""); // Kode SNOMED (column 10)
                String procedureSnomedDisplay = getSafeString(tbTindakanPasien, i, 11, ""); // SNOMED Display (column 11)
                String procedureSnomedSystem = getSafeString(tbTindakanPasien, i, 12, ""); // SNOMED System (column 12)

                // Fallback to procedure code/name if SNOMED is not available
                if (procedureSnomedCode.isEmpty() || procedureSnomedDisplay.isEmpty()) {
                    procedureSnomedCode = getSafeString(tbTindakanPasien, i, 4, ""); // Kode Tindakan (column 4)
                    procedureSnomedDisplay = getSafeString(tbTindakanPasien, i, 5, ""); // Nama Tindakan (column 5)
                    procedureSnomedSystem = "http://hl7.org/fhir/sid/icd-9-cm";
                }

                if (procedureSnomedCode.isEmpty() || procedureSnomedDisplay.isEmpty()) {
                    System.out.println("Warning: Procedure " + i + " has empty required fields, skipping");
                    continue;
                }

                // Get procedure date from the correct column
                String procedureDate = getSafeString(tbTindakanPasien, i, 0, ""); // Tgl.Rawat (column 0)
                String performedStart = getSafeString(tbTindakanPasien, i, 8, "");//tg jam reg
                String performedEnd = procedureDate;

                ObjectNode procedure = mapper.createObjectNode();
                procedure.put("resourceType", "Procedure");
                // Use the noSEP and procedure code as the unique ID
                procedure.put("id", generateResourceIdForProcedure(sanitizeString(TNoSEP.getText() + procedureSnomedCode), i));
                procedure.put("status", "completed");

                // Text - use simple approach
                ObjectNode text = mapper.createObjectNode();
                text.put("status", "generated");
                text.put("div", "<div>Generated Narrative with Details</div>");
                procedure.set("text", text);

                // Code - use direct coding creation to avoid recursion
                String namaTindakan = getSafeString(tbTindakanPasien, i, 5, ""); // Kode Nama Tindakan (column 5)
                ObjectNode code = mapper.createObjectNode();
                ArrayNode codingArray = mapper.createArrayNode();
                ObjectNode coding = mapper.createObjectNode();
                coding.put("system", procedureSnomedSystem);
                coding.put("code", procedureSnomedCode);
                coding.put("display", namaTindakan);
                codingArray.add(coding);
                code.set("coding", codingArray);
                procedure.set("code", code);

                // Subject
                ObjectNode subject = mapper.createObjectNode();
                subject.putNull("type");
                ObjectNode subjectIdentifier = mapper.createObjectNode();
                subjectIdentifier.putNull("use");
                ObjectNode subjectIdentifierType = mapper.createObjectNode();
                subjectIdentifierType.set("coding", mapper.createArrayNode());
                subjectIdentifierType.putNull("text");
                subjectIdentifier.set("type", subjectIdentifierType);
                subjectIdentifier.putNull("system");
                subjectIdentifier.putNull("value");
                ObjectNode subjectIdentifierAssigner = mapper.createObjectNode();
                subjectIdentifierAssigner.putNull("display");
                subjectIdentifier.set("assigner", subjectIdentifierAssigner);
                subject.set("identifier", subjectIdentifier);
                subject.put("display", TPasien.getText());
                subject.put("reference", "Patient/" + generateResourceIdForPatient(sanitizeString(nokaBPJS), sanitizeString(TNoSEP.getText())));
                procedure.set("subject", subject);

                // Context
                ObjectNode context = mapper.createObjectNode();
                context.putNull("type");
                ObjectNode contextIdentifier = mapper.createObjectNode();
                contextIdentifier.putNull("use");
                ObjectNode contextIdentifierType = mapper.createObjectNode();
                contextIdentifierType.set("coding", mapper.createArrayNode());
                contextIdentifierType.putNull("text");
                contextIdentifier.set("type", contextIdentifierType);
                contextIdentifier.putNull("system");
                contextIdentifier.putNull("value");
                ObjectNode contextIdentifierAssigner = mapper.createObjectNode();
                contextIdentifierAssigner.putNull("display");
                contextIdentifier.set("assigner", contextIdentifierAssigner);
                context.set("identifier", contextIdentifier);
                context.put("display", TPasien.getText() + " encounter on " + performedStart);
                context.put("reference", "Encounter/" + generateResourceIdForEncounter(TNoSEP.getText()));
                procedure.set("context", context);

                // Performed Period
                if (!performedStart.isEmpty() && !performedEnd.isEmpty()) {
                    ObjectNode performedPeriod = mapper.createObjectNode();
                    performedPeriod.put("start", performedStart);
                    performedPeriod.put("end", performedEnd);
                    procedure.set("performedPeriod", performedPeriod);
                }

                // Performer - get doctor information from correct columns
                ArrayNode performers = mapper.createArrayNode();
                ObjectNode performer = mapper.createObjectNode();

                // Role - direct coding
                ObjectNode role = mapper.createObjectNode();
                ArrayNode roleCoding = mapper.createArrayNode();
                ObjectNode roleCode = mapper.createObjectNode();
                roleCode.put("system", "http://snomed.info/sct");
                roleCode.put("code", "310512001");
                roleCode.put("display", "Medical oncologist");
                roleCoding.add(roleCode);
                role.set("coding", roleCoding);
                performer.set("role", role);

                // Actor - use doctor information from the table
                ObjectNode actor = mapper.createObjectNode();
                String doctorCode = getSafeString(tbTindakanPasien, i, 6, ""); // Kode Dokter (column 6)
                String doctorName = getSafeString(tbTindakanPasien, i, 7, ""); // nm_poli (column 7) - Note: This might be poli name, adjust if needed

                if (!doctorCode.isEmpty()) {
                    actor.put("display", doctorName.isEmpty() ? "Doctor" : doctorName);
                    actor.put("reference", "Practitioner/" + generateResourceIdForPractitioner(doctorCode));
                    performer.set("actor", actor);
                }

                performers.add(performer);
                procedure.set("performer", performers);

                // Reason Code - empty array
                ArrayNode reasonCodes = mapper.createArrayNode();
                procedure.set("reasonCode", reasonCodes);

                // Body Site - empty array
                ArrayNode bodySites = mapper.createArrayNode();
                procedure.set("bodySite", bodySites);

                // Focal Device - empty array
                ArrayNode focalDevices = mapper.createArrayNode();
                procedure.set("focalDevice", focalDevices);

                // Note - array with empty text
                ArrayNode notes = mapper.createArrayNode();
                ObjectNode note = mapper.createObjectNode();
                note.put("text", "");
                notes.add(note);
                procedure.set("note", notes);

                procedures.add(procedure);

                // Prevent infinite loops by limiting the number of procedures
                if (procedures.size() >= 100) {
                    System.out.println("Warning: Limiting procedures to 100 entries");
                    break;
                }
            }

            jsonProcedure = mapper.writeValueAsString(procedures);

            // Validate JSON isn't too large
            if (jsonProcedure.length() > 1000000) { // 1MB limit
                System.out.println("Warning: Procedure JSON too large, truncating");
                jsonProcedure = "[]";
            }

        } catch (Exception e) {
            System.out.println("Error creating Procedure JSON: " + e.getMessage());
            e.printStackTrace();
            jsonProcedure = "[]";
        }
    }

    private void setJsonDiagnosticReport() {
        try {
            ArrayNode diagnosticReports = mapper.createArrayNode();

            // 1. RADIOLOGY DiagnosticReports with EMBEDDED Observations
            for (int i = 0; i < tbRadiologiPasien.getRowCount(); i++) {
                String tglPeriksa = tbRadiologiPasien.getValueAt(i, 0) != null ? sanitizeString(tbRadiologiPasien.getValueAt(i, 0).toString()) : "";
                String jam = tbRadiologiPasien.getValueAt(i, 1) != null ? sanitizeString(tbRadiologiPasien.getValueAt(i, 1).toString()) : "";
                String nmPerawatan = tbRadiologiPasien.getValueAt(i, 2) != null ? sanitizeStringforNonASCII(sanitizeString(tbRadiologiPasien.getValueAt(i, 2).toString())) : "";
                String snomedCode = tbRadiologiPasien.getValueAt(i, 4) != null ? sanitizeString(tbRadiologiPasien.getValueAt(i, 4).toString()) : "";
                String loincCode = tbRadiologiPasien.getValueAt(i, 5) != null ? sanitizeString(tbRadiologiPasien.getValueAt(i, 5).toString()) : "";
                String klinis = tbRadiologiPasien.getValueAt(i, 6) != null ? sanitizeStringforNonASCII(sanitizeString(tbRadiologiPasien.getValueAt(i, 6).toString())) : "";
                String kesan = tbRadiologiPasien.getValueAt(i, 7) != null ? sanitizeStringforNonASCII(sanitizeString(tbRadiologiPasien.getValueAt(i, 7).toString())) : "";
                String dokter = tbRadiologiPasien.getValueAt(i, 8) != null ? sanitizeStringforNonASCII(sanitizeString(tbRadiologiPasien.getValueAt(i, 8).toString())) : "";
                String kodedokter = Sequel.cariIsi("select kd_dokter from dokter where nm_dokter='" + dokter + "'");

                if (nmPerawatan.isEmpty()) {
                    System.out.println("Warning: Radiology Diagnostic Report " + i + " has empty required fields, skipping");
                    continue;
                }

                String reportId = generateDiagnosticReportId("RAD", i);
                String observationId = generateDiagnosticReportId("RADOBS", i);

                // Create DiagnosticReport
                ObjectNode diagnosticReport = mapper.createObjectNode();
                diagnosticReport.put("resourceType", "DiagnosticReport");
                diagnosticReport.put("id", reportId);

                // Subject
                ObjectNode subject = mapper.createObjectNode();
                subject.put("reference", "Patient/" + generateResourceIdForPatient(sanitizeString(nokaBPJS), sanitizeString(TNoSEP.getText())));
                subject.put("display", sanitizeString(TPasien.getText()));
                subject.put("noSep", sanitizeString(TNoSEP.getText()));
                diagnosticReport.set("subject", subject);

                // Category
                ObjectNode category = mapper.createObjectNode();
                ObjectNode categoryCoding = mapper.createObjectNode();
                categoryCoding.put("system", "http://hl7.org/fhir/v2/0074");
                categoryCoding.put("code", "RAD");
                categoryCoding.put("display", "Radiology");
                category.set("coding", categoryCoding);
                diagnosticReport.set("category", category);

                diagnosticReport.put("status", "final");

                // Performer Array
                ArrayNode performerArray = mapper.createArrayNode();
                ObjectNode performer = mapper.createObjectNode();
                performer.put("reference", "Organization/" + generateResourceId(sanitizeString(Sequel.cariIsi("select id_lokasi_satusehat from satu_sehat_mapping_lokasi_ruangrad"))));
                performer.put("display", "RADIOLOGI");
                performerArray.add(performer);
                diagnosticReport.set("performer", performerArray);

                // **KEY FIX**: Embed FULL Observation in result array (not reference)
                ArrayNode resultArray = mapper.createArrayNode();
                ObjectNode observation = mapper.createObjectNode();
                observation.put("resourceType", "Observation");
                observation.put("id", observationId);
                observation.put("status", "final");

                // Observation text
                ObjectNode text = mapper.createObjectNode();
                text.put("status", "generated");
                text.put("div", klinis);
                observation.set("text", text);

                // ValueQuantity
                ObjectNode valueQuantity = mapper.createObjectNode();
                valueQuantity.put("value", 1);
                observation.set("valueQuantity", valueQuantity);

                // Interpretation
                ObjectNode interpretation = mapper.createObjectNode();
                ObjectNode interpretationCoding = mapper.createObjectNode();
                interpretationCoding.put("system", "http://hl7.org/fhir/observation-interpretation");
                interpretationCoding.put("code", "N");
                interpretationCoding.put("display", "Normal");
                interpretation.set("coding", interpretationCoding);
                observation.set("interpretation", interpretation);

                // ReferenceRange
                ObjectNode referenceRange = mapper.createObjectNode();
                ObjectNode low = mapper.createObjectNode();
                low.put("value", 0);
                ObjectNode high = mapper.createObjectNode();
                high.put("value", 1);
                referenceRange.set("low", low);
                referenceRange.set("high", high);
                observation.set("referenceRange", referenceRange);

                // Dates
                observation.put("issued", tglPeriksa + " " + jam);
                observation.put("effectiveDateTime", tglPeriksa + " " + jam);

                // Code
                ObjectNode obsCode = mapper.createObjectNode();
                ObjectNode obsCoding = mapper.createObjectNode();
                obsCoding.put("system", "http://snomed.info/sct");
                obsCoding.put("code", snomedCode.isEmpty() ? "363680008" : snomedCode);
                obsCoding.put("display", nmPerawatan);
                obsCode.set("coding", obsCoding);
                obsCode.put("text", nmPerawatan);
                observation.set("code", obsCode);

                // Performer
                ObjectNode obsPerformer = mapper.createObjectNode();
                obsPerformer.put("reference", "Practitioner/" + generateResourceIdForPractitioner(sanitizeString(kodedokter)));
                obsPerformer.put("display", dokter);
                observation.set("performer", obsPerformer);

                // Image
                ArrayNode imageArray = mapper.createArrayNode();
                ObjectNode image = mapper.createObjectNode();
                image.put("comment", "");
                ObjectNode link = mapper.createObjectNode();
                link.put("reference", "");
                link.put("display", "");
                image.set("link", link);
                imageArray.add(image);
                observation.set("image", imageArray);

                // Conclusion
                observation.put("conclusion", kesan);

                // Add EMBEDDED observation to result array
                resultArray.add(observation);
                diagnosticReport.set("result", resultArray);

                diagnosticReports.add(diagnosticReport);
            }

            // 2. LABORATORY DiagnosticReports - GROUP BY PARENT TEST
            // First, group lab tests by parent test (use index 2 which is nmPerawatan - parent test name)
            Map<String, List<Integer>> labGroups = new LinkedHashMap<>();
            for (int i = 0; i < tbLabPasien.getRowCount(); i++) {
                String nmPerawatan = tbLabPasien.getValueAt(i, 2) != null ? sanitizeStringforNonASCII(sanitizeString(tbLabPasien.getValueAt(i, 2).toString())) : "";
                String tglPeriksa = tbLabPasien.getValueAt(i, 0) != null ? sanitizeString(tbLabPasien.getValueAt(i, 0).toString()) : "";
                String jam = tbLabPasien.getValueAt(i, 1) != null ? sanitizeString(tbLabPasien.getValueAt(i, 1).toString()) : "";

                if (nmPerawatan.isEmpty()) {
                    continue;
                }

                // Use parent test name + date + time as grouping key to group sub-tests under same parent
                String groupKey = nmPerawatan + "|" + tglPeriksa + " " + jam;
                labGroups.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(i);
            }

            // Process each lab group
            for (Map.Entry<String, List<Integer>> group : labGroups.entrySet()) {
                List<Integer> indices = group.getValue();
                if (indices.isEmpty()) {
                    continue;
                }

                int firstIdx = indices.get(0);
                String tglPeriksa = tbLabPasien.getValueAt(firstIdx, 0) != null ? sanitizeString(tbLabPasien.getValueAt(firstIdx, 0).toString()) : "";
                String jam = tbLabPasien.getValueAt(firstIdx, 1) != null ? sanitizeString(tbLabPasien.getValueAt(firstIdx, 1).toString()) : "";
                String nmPerawatan = tbLabPasien.getValueAt(firstIdx, 2) != null ? sanitizeStringforNonASCII(sanitizeString(tbLabPasien.getValueAt(firstIdx, 2).toString())) : "";
                String snomedCode = tbLabPasien.getValueAt(firstIdx, 4) != null ? sanitizeString(tbLabPasien.getValueAt(firstIdx, 4).toString()) : "";
                String loincCode = tbLabPasien.getValueAt(firstIdx, 5) != null ? sanitizeString(tbLabPasien.getValueAt(firstIdx, 5).toString()) : "";

                String reportId = generateDiagnosticReportId("LAB", firstIdx);

                // Create DiagnosticReport
                ObjectNode diagnosticReport = mapper.createObjectNode();
                diagnosticReport.put("resourceType", "DiagnosticReport");
                diagnosticReport.put("id", reportId);

                // Subject
                ObjectNode subject = mapper.createObjectNode();
                subject.put("reference", "Patient/" + generateResourceIdForPatient(sanitizeString(nokaBPJS), sanitizeString(TNoSEP.getText())));
                subject.put("display", sanitizeString(TPasien.getText()));
                subject.put("noSep", sanitizeString(TNoSEP.getText()));
                diagnosticReport.set("subject", subject);

                // Category
                ObjectNode category = mapper.createObjectNode();
                ObjectNode categoryCoding = mapper.createObjectNode();
                categoryCoding.put("system", "http://hl7.org/fhir/v2/0074");
                categoryCoding.put("code", "LAB");
                categoryCoding.put("display", "Laboratory");
                category.set("coding", categoryCoding);
                diagnosticReport.set("category", category);

                diagnosticReport.put("status", "final");

                // Performer Array
                ArrayNode performerArray = mapper.createArrayNode();
                ObjectNode performer = mapper.createObjectNode();
                performer.put("reference", "Organization/" + generateResourceId(sanitizeString(Sequel.cariIsi("select id_lokasi_satusehat from satu_sehat_mapping_lokasi_ruanglab"))));
                performer.put("display", "LABORATORIUM");
                performerArray.add(performer);
                diagnosticReport.set("performer", performerArray);

                // **KEY FIX**: Create result array with EMBEDDED observations for all sub-tests
                ArrayNode resultArray = mapper.createArrayNode();

                for (Integer idx : indices) {
                    String pemeriksaan = tbLabPasien.getValueAt(idx, 3) != null ? sanitizeStringforNonASCII(sanitizeString(tbLabPasien.getValueAt(idx, 3).toString())) : "";
                    String subSnomedCode = tbLabPasien.getValueAt(idx, 6) != null ? sanitizeString(tbLabPasien.getValueAt(idx, 6).toString()) : "";
                    String subLoincCode = tbLabPasien.getValueAt(idx, 7) != null ? sanitizeString(tbLabPasien.getValueAt(idx, 7).toString()) : "";
                    String nilai = tbLabPasien.getValueAt(idx, 8) != null ? sanitizeString(tbLabPasien.getValueAt(idx, 8).toString()) : "";
                    String nilaiRujukan = tbLabPasien.getValueAt(idx, 9) != null ? sanitizeString(tbLabPasien.getValueAt(idx, 9).toString()) : "";
                    String satuan = tbLabPasien.getValueAt(idx, 10) != null ? sanitizeString(tbLabPasien.getValueAt(idx, 10).toString()) : "";
                    String keterangan = tbLabPasien.getValueAt(idx, 11) != null ? sanitizeString(tbLabPasien.getValueAt(idx, 11).toString()) : "";
                    String kodedokter = tbLabPasien.getValueAt(idx, 12) != null ? sanitizeString(tbLabPasien.getValueAt(idx, 12).toString()) : "";
                    String namadokter = Sequel.cariIsi("select nm_dokter from dokter where kd_dokter='" + kodedokter + "'");

                    String observationId = subLoincCode.isEmpty() ? ("LAB" + String.format("%03d", idx + 1) + processUUIDString(TNoSEP.getText()).substring(0, 8)) : subLoincCode;

                    // Create EMBEDDED Observation
                    ObjectNode observation = mapper.createObjectNode();
                    observation.put("resourceType", "Observation");
                    observation.put("id", observationId);
                    observation.put("status", "final");

                    // Text (empty for lab usually)
                    ObjectNode text = mapper.createObjectNode();
                    text.put("status", "generated");
                    text.put("div", "");
                    observation.set("text", text);

                    // Dates
                    observation.put("issued", tglPeriksa + " " + jam);
                    observation.put("effectiveDateTime", tglPeriksa + " " + jam);

                    // Code
                    ObjectNode obsCode = mapper.createObjectNode();
                    ObjectNode obsCoding = mapper.createObjectNode();
                    obsCoding.put("system", "http://snomed.info/sct");
                    obsCoding.put("code", subSnomedCode.isEmpty() ? "718-7" : subSnomedCode);
                    obsCoding.put("display", pemeriksaan.isEmpty() ? "Hemoglobin" : pemeriksaan);
                    obsCode.set("coding", obsCoding);
                    obsCode.put("text", "");
                    observation.set("code", obsCode);

                    // Performer (empty reference)
                    ObjectNode obsPerformer = mapper.createObjectNode();
                    obsPerformer.put("reference", "");
                    obsPerformer.put("display", "");
                    observation.set("performer", obsPerformer);

                    // Image (placeholder)
                    ArrayNode imageArray = mapper.createArrayNode();
                    ObjectNode image = mapper.createObjectNode();
                    image.put("comment", "");
                    ObjectNode link = mapper.createObjectNode();
                    link.put("reference", "");
                    link.put("display", "");
                    image.set("link", link);
                    imageArray.add(image);
                    observation.set("image", imageArray);

                    // Conclusion (empty for lab)
                    observation.put("conclusion", "");

                    // ValueQuantity
                    ObjectNode valueQuantity = mapper.createObjectNode();
                    if (!nilai.isEmpty()) {
                        try {
                            double numericValue = Double.parseDouble(nilai.replace("*", "").replace("+", "").replace("-", "").trim());
                            valueQuantity.put("value", nilai);
                        } catch (NumberFormatException e) {
                            valueQuantity.put("value", nilai);
                        }
                    } else {
                        valueQuantity.put("value", "");
                    }
                    valueQuantity.putNull("comparator");
                    valueQuantity.put("unit", satuan.isEmpty() ? nilaiRujukan : satuan);
                    valueQuantity.put("system", "");
                    valueQuantity.put("code", "");
                    observation.set("valueQuantity", valueQuantity);

                    // Interpretation
                    ObjectNode interpretation = mapper.createObjectNode();
                    ObjectNode interpretationCoding = mapper.createObjectNode();
                    interpretationCoding.put("system", "http://hl7.org/fhir/observation-interpretation");

                    String interpretationCode = "N";
                    String interpretationDisplay = "Normal";
                    if (!keterangan.isEmpty()) {
                        if (keterangan.toLowerCase().contains("tinggi") || keterangan.toLowerCase().contains("high") || keterangan.toLowerCase().contains("h")) {
                            interpretationCode = "H";
                            interpretationDisplay = "High";
                        } else if (keterangan.toLowerCase().contains("rendah") || keterangan.toLowerCase().contains("low") || keterangan.toLowerCase().contains("l")) {
                            interpretationCode = "L";
                            interpretationDisplay = "Low";
                        }
                    }
                    interpretationCoding.put("code", interpretationCode);
                    interpretationCoding.put("display", interpretationDisplay);
                    interpretation.set("coding", interpretationCoding);
                    observation.set("interpretation", interpretation);

                    // ReferenceRange
                    ObjectNode referenceRange = mapper.createObjectNode();
                    ObjectNode low = mapper.createObjectNode();
                    low.put("value", 0);
                    ObjectNode high = mapper.createObjectNode();
                    high.put("value", 1);
                    referenceRange.set("low", low);
                    referenceRange.set("high", high);
                    observation.set("referenceRange", referenceRange);

                    // Add EMBEDDED observation to result array
                    resultArray.add(observation);
                }

                diagnosticReport.set("result", resultArray);
                diagnosticReports.add(diagnosticReport);
            }

            // 3. SURGERY DiagnosticReports with EMBEDDED Observations
//        for (int i = 0; i < tbLaporanOP.getRowCount(); i++) {
//            String tanggal = tbLaporanOP.getValueAt(i, 0) != null ? sanitizeString(tbLaporanOP.getValueAt(i, 0).toString()) : "";
//            String diagnosaPreop = tbLaporanOP.getValueAt(i, 1) != null ? sanitizeStringforNonASCII(sanitizeString(tbLaporanOP.getValueAt(i, 1).toString())) : "";
//            String diagnosaPostop = tbLaporanOP.getValueAt(i, 2) != null ? sanitizeStringforNonASCII(sanitizeString(tbLaporanOP.getValueAt(i, 2).toString())) : "";
//            String selesaiOperasi = tbLaporanOP.getValueAt(i, 3) != null ? sanitizeString(tbLaporanOP.getValueAt(i, 3).toString()) : "";
//            String laporanOperasi = tbLaporanOP.getValueAt(i, 4) != null ? sanitizeStringforNonASCII(sanitizeString(tbLaporanOP.getValueAt(i, 4).toString())) : "";
//            String kdDokter = tbLaporanOP.getValueAt(i, 5) != null ? sanitizeString(tbLaporanOP.getValueAt(i, 5).toString()) : "";
//            String nmDokter = tbLaporanOP.getValueAt(i, 6) != null ? sanitizeStringforNonASCII(sanitizeString(tbLaporanOP.getValueAt(i, 6).toString())) : "";
//
//            if (laporanOperasi.isEmpty()) {
//                System.out.println("Warning: Surgery Diagnostic Report " + i + " has empty required fields, skipping");
//                continue;
//            }
//
//            String reportId = generateDiagnosticReportId("OP", i);
//            String observationId = "OP" + String.format("%03d", i + 1) + processUUIDString(TNoSEP.getText()).substring(0, 8);
//
//            // Create DiagnosticReport
//            ObjectNode diagnosticReport = mapper.createObjectNode();
//            diagnosticReport.put("resourceType", "DiagnosticReport");
//            diagnosticReport.put("id", reportId);
//
//            // Subject
//            ObjectNode subject = mapper.createObjectNode();
//            subject.put("reference", "Patient/" + generateResourceIdForPatient(sanitizeString(nokaBPJS), sanitizeString(TNoSEP.getText())));
//            subject.put("display", sanitizeString(TPasien.getText()));
//            subject.put("noSep", sanitizeString(TNoSEP.getText()));
//            diagnosticReport.set("subject", subject);
//
//            // Category
//            ObjectNode category = mapper.createObjectNode();
//            ObjectNode categoryCoding = mapper.createObjectNode();
//            categoryCoding.put("system", "http://terminology.hl7.org/CodeSystem/v2-0074");
//            categoryCoding.put("code", "SP");
//            categoryCoding.put("display", "Surgical Pathology");
//            category.set("coding", categoryCoding);
//            diagnosticReport.set("category", category);
//
//            diagnosticReport.put("status", "final");
//
//            // REMOVED: issued, effectiveDateTime, code, performer (single) - these belong in Observation only!
//
//            // **KEY FIX**: Embed FULL Observation in result array
//            ArrayNode resultArray = mapper.createArrayNode();
//            ObjectNode observation = mapper.createObjectNode();
//            observation.put("resourceType", "Observation");
//            observation.put("id", observationId);
//            observation.put("status", "final");
//
//            // Text
//            ObjectNode text = mapper.createObjectNode();
//            text.put("status", "generated");
//            text.put("div", "");
//            observation.set("text", text);
//
//            // Dates - IN OBSERVATION
//            observation.put("issued", tanggal);
//            observation.put("effectiveDateTime", tanggal);
//
//            // Code - IN OBSERVATION
//            ObjectNode obsCode = mapper.createObjectNode();
//            ObjectNode obsCoding = mapper.createObjectNode();
//            obsCoding.put("system", "http://snomed.info/sct");
//            obsCoding.put("code", "278110006");
//            obsCoding.put("display", "Surgical operation note");
//            obsCode.set("coding", obsCoding);
//            obsCode.put("text", "");
//            observation.set("code", obsCode);
//
//            // Performer - IN OBSERVATION
//            ObjectNode obsPerformer = mapper.createObjectNode();
//            obsPerformer.put("reference", "");
//            obsPerformer.put("display", "");
//            observation.set("performer", obsPerformer);
//
//            // Image
//            ArrayNode imageArray = mapper.createArrayNode();
//            ObjectNode image = mapper.createObjectNode();
//            image.put("comment", "");
//            ObjectNode link = mapper.createObjectNode();
//            link.put("reference", "");
//            link.put("display", "");
//            image.set("link", link);
//            imageArray.add(image);
//            observation.set("image", imageArray);
//
//            // Conclusion
//            observation.put("conclusion", "");
//
//            // ValueQuantity
//            ObjectNode valueQuantity = mapper.createObjectNode();
//            valueQuantity.put("value", "");
//            valueQuantity.putNull("comparator");
//            valueQuantity.put("unit", "");
//            valueQuantity.put("system", "");
//            valueQuantity.put("code", "");
//            observation.set("valueQuantity", valueQuantity);
//
//            // Interpretation
//            ObjectNode interpretation = mapper.createObjectNode();
//            ObjectNode interpretationCoding = mapper.createObjectNode();
//            interpretationCoding.put("system", "http://hl7.org/fhir/observation-interpretation");
//            interpretationCoding.put("code", "N");
//            interpretationCoding.put("display", "Normal");
//            interpretation.set("coding", interpretationCoding);
//            observation.set("interpretation", interpretation);
//
//            // ReferenceRange
//            ObjectNode referenceRange = mapper.createObjectNode();
//            ObjectNode low = mapper.createObjectNode();
//            low.put("value", 0);
//            ObjectNode high = mapper.createObjectNode();
//            high.put("value", 1);
//            referenceRange.set("low", low);
//            referenceRange.set("high", high);
//            observation.set("referenceRange", referenceRange);
//
//            // Add EMBEDDED observation to result array
//            resultArray.add(observation);
//            diagnosticReport.set("result", resultArray);
//
//            diagnosticReports.add(diagnosticReport);
//        }
            // CRITICAL FIX: Return diagnosticReports array directly, not double-nested
            jsonDiagnosticReport = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(diagnosticReports);

        } catch (Exception e) {
            System.out.println("Error creating DiagnosticReport JSON: " + e.getMessage());
            e.printStackTrace();
            jsonDiagnosticReport = "";
        }
    }

    /**
     * Helper method to generate a unique ID for diagnostic reports
     *
     * @param type Type of diagnostic report (LAB, RAD, or OP)
     * @param index Index to make ID unique
     * @return A formatted ID string
     */
    private String generateDiagnosticReportId(String type, int index) {
        try {
            // Use the noSEP and category as the unique ID following the requirement
            return generateResourceIdForDiagnosticReport(sanitizeString(TNoSEP.getText()), type);
        } catch (Exception e) {
            System.out.println("Error generating diagnostic report ID: " + e.getMessage());
            return processUUIDString(TNoSEP.getText()); // fallback to default ID generation
        }
    }

    private void setJsonDevice() {
        try {
            ArrayNode devices = mapper.createArrayNode();

            for (int i = 0; i < tbAlkes.getRowCount(); i++) {
                String code = tbAlkes.getValueAt(i, 4) != null ? sanitizeString(tbAlkes.getValueAt(i, 4).toString()) : "";
                String display = tbAlkes.getValueAt(i, 5) != null ? sanitizeString(tbAlkes.getValueAt(i, 5).toString()) : "";
                String system = tbAlkes.getValueAt(i, 3) != null ? sanitizeString(tbAlkes.getValueAt(i, 3).toString()) : "";
                String manufacturer = tbAlkes.getValueAt(i, 20) != null ? sanitizeString(tbAlkes.getValueAt(i, 20).toString()) : "";
                String model = tbAlkes.getValueAt(i, 21) != null ? sanitizeString(tbAlkes.getValueAt(i, 21).toString()) : "";
                String lotNumber = tbAlkes.getValueAt(i, 22) != null ? sanitizeString(tbAlkes.getValueAt(i, 22).toString()) : "";
                String manufactureDate = tbAlkes.getValueAt(i, 23) != null ? sanitizeString(tbAlkes.getValueAt(i, 23).toString()) : "";
                String expirationDate = tbAlkes.getValueAt(i, 24) != null ? sanitizeString(tbAlkes.getValueAt(i, 24).toString()) : "";

                if (code.isEmpty() || display.isEmpty()) {
                    System.out.println("Warning: Device " + i + " has empty required fields, skipping");
                    continue;
                }

                ObjectNode device = mapper.createObjectNode();
                device.put("resourceType", "Device");
                device.put("id", generateResourceId(sanitizeString(TNoSEP.getText())));

                // Text - simplified to match target structure
                ObjectNode text = mapper.createObjectNode();
                text.put("status", "generated");
                text.put("div", "<div></div>");
                device.set("text", text);

                // Identifier - with full structure including use, type, and assigner
                ArrayNode identifiers = mapper.createArrayNode();
                ObjectNode identifier = mapper.createObjectNode();
                identifier.put("use", (String) null); // null as in target
                identifier.put("system", system);
                identifier.put("value", code);

                // Type sub-object for identifier
                ObjectNode identifierType = mapper.createObjectNode();
                identifierType.set("coding", mapper.createArrayNode()); // empty array
                identifierType.put("text", (String) null); // null
                identifier.set("type", identifierType);

                // Assigner sub-object
                ObjectNode assigner = mapper.createObjectNode();
                assigner.put("display", (String) null); // null
                identifier.set("assigner", assigner);

                identifiers.add(identifier);
                device.set("identifier", identifiers);

                // Type - with coding array and null text
                ObjectNode type = mapper.createObjectNode();
                ArrayNode typeCodings = mapper.createArrayNode();
                ObjectNode typeCoding = mapper.createObjectNode();
                typeCoding.put("system", system);
                typeCoding.put("code", code);
                typeCoding.put("display", display);
                typeCodings.add(typeCoding);
                type.set("coding", typeCodings);
                type.put("text", (String) null); // null as in target
                device.set("type", type);

                // Device properties - set to null if empty to match target structure
                if (!lotNumber.isEmpty()) {
                    device.put("lotNumber", lotNumber);
                } else {
                    device.put("lotNumber", "");
                }

                if (!manufacturer.isEmpty()) {
                    device.put("manufacturer", manufacturer);
                } else {
                    device.put("manufacturer", "");
                }

                if (!manufactureDate.isEmpty()) {
                    device.put("manufactureDate", manufactureDate);
                } else {
                    device.put("manufactureDate", (String) null);
                }

                if (!expirationDate.isEmpty()) {
                    device.put("expirationDate", expirationDate);
                } else {
                    device.put("expirationDate", (String) null);
                }

                if (!model.isEmpty()) {
                    device.put("model", model);
                } else {
                    device.put("model", (String) null);
                }

                // Patient - with full structure including type, identifier, and display
                ObjectNode patient = mapper.createObjectNode();
                patient.put("type", (String) null);
                patient.put("display", "SUATI, NY"); // Using hardcoded value from target

                // Patient identifier sub-object
                ObjectNode patientIdentifier = mapper.createObjectNode();
                patientIdentifier.put("use", (String) null);
                patientIdentifier.put("system", (String) null);
                patientIdentifier.put("value", (String) null);

                ObjectNode patientIdentifierType = mapper.createObjectNode();
                patientIdentifierType.set("coding", mapper.createArrayNode()); // empty array
                patientIdentifierType.put("text", (String) null);
                patientIdentifier.set("type", patientIdentifierType);

                ObjectNode patientAssigner = mapper.createObjectNode();
                patientAssigner.put("display", (String) null);
                patientIdentifier.set("assigner", patientAssigner);

                patient.set("identifier", patientIdentifier);
                patient.put("reference", "Patient/" + generateResourceIdForPatient(sanitizeString(nokaBPJS), TNoSEP.getText()));
                device.set("patient", patient);

                // Contact
                ArrayNode contacts = mapper.createArrayNode();
                ObjectNode contact = mapper.createObjectNode();
                contact.put("system", "phone");
                contact.put("value", !notelpinstansi.isEmpty() ? sanitizeString(notelpinstansi) : "");
                contact.put("use", "work");
                contacts.add(contact);
                device.set("contact", contacts);

                devices.add(device);
            }
            jsonDevice = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(devices);

        } catch (Exception e) {
            System.out.println("Error creating Device JSON: " + e.getMessage());
            e.printStackTrace();
            jsonDevice = "{\"resource\":[]}";
        }
    }

    private void setJsonMedicationrequest() {
        try {
            if (tbResepDokter.getRowCount() == 0) {
                jsonMedicationRequest = "{}";
                return;
            }

            ArrayNode medicationRequests = mapper.createArrayNode();

            for (int i = 0; i < tbResepDokter.getRowCount(); i++) {
                // Extract data from table with proper null checks and indices
                String medicationCode = tbResepDokter.getValueAt(i, 3) != null ? sanitizeString(tbResepDokter.getValueAt(i, 3).toString()) : "";
                String medicationText = tbResepDokter.getValueAt(i, 6) != null ? sanitizeStringforNonASCII(sanitizeString(tbResepDokter.getValueAt(i, 6).toString())) : "";
                String requesterDisplay = tbResepDokter.getValueAt(i, 1) != null ? sanitizeStringforNonASCII(sanitizeString(tbResepDokter.getValueAt(i, 1).toString())) : "";
//                String requesterReference = Sequel.cariIsi("select no_ktp from pegawai where nama='" + requesterDisplay + "' ") != null ? sanitizeStringforNonASCII(sanitizeString(Sequel.cariIsi("select no_ktp from pegawai where nama='" + requesterDisplay + "' "))) : "";
                String lastUpdated = tbResepDokter.getValueAt(i, 15) != null ? sanitizeString(tbResepDokter.getValueAt(i, 15).toString()) : "";
                String dosageValue = tbResepDokter.getValueAt(i, 16) != null ? sanitizeString(tbResepDokter.getValueAt(i, 16).toString()) : "1";
                String routeCode = tbResepDokter.getValueAt(i, 10) != null ? sanitizeString(tbResepDokter.getValueAt(i, 10).toString()) : "002";
                String routeDisplay = tbResepDokter.getValueAt(i, 12) != null ? sanitizeString(tbResepDokter.getValueAt(i, 12).toString()) : "INTRAVENOUS";
                String formCode = tbResepDokter.getValueAt(i, 7) != null ? sanitizeString(tbResepDokter.getValueAt(i, 7).toString()) : "TAB";
                String dosageInstructionText = tbResepDokter.getValueAt(i, 18) != null ? sanitizeString(tbResepDokter.getValueAt(i, 18).toString()) : "1 kali";
                String identifierValue = tbResepDokter.getValueAt(i, 19) != null ? sanitizeString(tbResepDokter.getValueAt(i, 19).toString()) : generateResourceId("noresep-" + i);
                String kodedokterresep = Sequel.cariIsi("select kd_dokter from resep_obat where no_resep=" + identifierValue + "");
                // Get frequency and period from aturan pakai or other fields if available
                String frequency = "1"; // Default frequency
                String period = "1"; // Default period
                String periodUnit = "d"; // Default to 'd' for day

                String requesterReference = kodedokterresep;

                if (medicationCode.isEmpty() || medicationText.isEmpty()) {
                    System.out.println("Warning: Medication Request " + i + " has empty required fields, skipping");
                    continue;
                }

                ObjectNode medicationRequest = mapper.createObjectNode();
                medicationRequest.put("resourceType", "MedicationRequest");
                // Use the noresep as the unique ID following the requirement
                String noresep = identifierValue;
                medicationRequest.put("id", generateResourceIdForMedicationRequest(noresep));

                // Text
                ObjectNode text = mapper.createObjectNode();
                text.put("div", "<div>" + medicationText + "</div>");
                medicationRequest.set("text", text);

                // Identifier (using the resep number as the value)
                ObjectNode identifier = mapper.createObjectNode();
                identifier.put("system", "id_resep_pulang");
                identifier.put("value", generateResourceIdForMedicationRequest(noresep));
                medicationRequest.set("identifier", identifier);

                // Subject
                medicationRequest.set("subject", createReference(
                        "Patient/" + generateResourceIdForPatient(sanitizeString(nokaBPJS), sanitizeString(TNoSEP.getText())), // Using patient ID based on noKartu
                        sanitizeString(TPasien.getText())
                ));

                medicationRequest.put("intent", "final");

                // Medication Codeable Concept
                ObjectNode medicationCodeableConcept = mapper.createObjectNode();
                ArrayNode codingArray = mapper.createArrayNode();
                ObjectNode coding = mapper.createObjectNode();
                coding.put("code", medicationCode);
                coding.put("system", "http://sys-ids.kemkes.go.id/kfa");
                codingArray.add(coding);
                medicationCodeableConcept.set("coding", codingArray);
                medicationCodeableConcept.put("text", medicationText);
                medicationRequest.set("medicationCodeableConcept", medicationCodeableConcept);

                // Dosage Instruction
                ArrayNode dosageInstructions = mapper.createArrayNode();
                ObjectNode dosageInstruction = mapper.createObjectNode();

                // Dose Quantity - using form code from column 7 and dosage value from column 16
                ObjectNode doseQuantity = mapper.createObjectNode();
                doseQuantity.put("code", formCode); // Using form code (e.g., TAB, AMP) as the unit code
                doseQuantity.put("system", "http://unitsofmeasure.org");
                doseQuantity.put("unit", formCode); // Using form code as the unit display
                doseQuantity.put("value", dosageValue); // Using dosage value from table column 16
                dosageInstruction.set("doseQuantity", doseQuantity);

                // Route - using route code from column 10 and display from column 12
                ObjectNode route = mapper.createObjectNode();
                ArrayNode routeCoding = mapper.createArrayNode();
                ObjectNode routeCodeObj = mapper.createObjectNode();
                routeCodeObj.put("code", routeCode);
                routeCodeObj.put("display", routeDisplay);
                routeCodeObj.put("system", "http://snomed.info/sct");
                routeCoding.add(routeCodeObj);
                route.set("coding", routeCoding);
                dosageInstruction.set("route", route);

                // Parse timing from aturan pakai using helper method
                String[] timingInfo = parseTimingFromInstructions(dosageInstructionText);
                String parsedFrequency = timingInfo[0];
                String parsedPeriod = timingInfo[1];
                String parsedPeriodUnit = timingInfo[2];

                // Timing - with parsed frequency, period and unit from aturan pakai
                ObjectNode timing = mapper.createObjectNode();
                ObjectNode repeat = mapper.createObjectNode();
                repeat.put("frequency", parsedFrequency);
                repeat.put("period", parsedPeriod);
                repeat.put("periodUnit", parsedPeriodUnit);
                timing.set("repeat", repeat);
                dosageInstruction.set("timing", timing);

                // Additional Instruction - from aturan pakai in column 18
                ArrayNode additionalInstructions = mapper.createArrayNode();
                ObjectNode additionalInstruction = mapper.createObjectNode();
                additionalInstruction.put("text", dosageInstructionText);
                additionalInstructions.add(additionalInstruction);
                dosageInstruction.set("additionalInstruction", additionalInstructions);

                dosageInstructions.add(dosageInstruction);
                medicationRequest.set("dosageInstruction", dosageInstructions);

                // Reason Code - using diagnosis or condition codes if available
                ArrayNode reasonCodes = mapper.createArrayNode();
                // For now, using empty values, but could be populated from diagnosis data
                ObjectNode reasonCode = mapper.createObjectNode();
                ArrayNode reasonCoding = mapper.createArrayNode();
                // Using actual diagnosis codes would require getting them from tbDiagnosaPasien
                if (tbDiagnosaPasien.getRowCount() > 0) {
                    String diagCode = getSafeString(tbDiagnosaPasien, 0, 5, "");
                    String diagDisplay = getSafeString(tbDiagnosaPasien, 0, 6, "");
                    if (!diagCode.isEmpty()) {
                        ObjectNode diagReasonCode = mapper.createObjectNode();
                        diagReasonCode.put("code", diagCode);
                        diagReasonCode.put("display", diagDisplay);
                        diagReasonCode.put("system", "http://hl7.org/fhir/sid/icd-10");
                        reasonCoding.add(diagReasonCode);
                    }
                }
                reasonCode.set("coding", reasonCoding);
                reasonCode.put("text", getSafeString(tbDiagnosaPasien, 0, 6, ""));
                reasonCodes.add(reasonCode);
                medicationRequest.set("reasonCode", reasonCodes);

                // Requester
                ObjectNode requester = mapper.createObjectNode();
                ObjectNode agent = mapper.createObjectNode();
                agent.put("display", requesterDisplay);
                agent.put("reference", "Practitioner/" + generateResourceId(sanitizeString(requesterReference)));
                requester.set("agent", agent);

                ObjectNode onBehalfOf = mapper.createObjectNode();
                onBehalfOf.put("reference", "Organization/" + generateResourceId(sanitizeString(KdPPKBPJS.getText())));
                requester.set("onBehalfOf", onBehalfOf);
                medicationRequest.set("requester", requester);

                // Meta
                ObjectNode meta = mapper.createObjectNode();
                meta.put("lastUpdated", lastUpdated != null && !lastUpdated.isEmpty() ? lastUpdated : Sequel.cariIsi("select CURRENT_TIMESTAMP()"));
                medicationRequest.set("meta", meta);

                medicationRequests.add(medicationRequest);
            }

//            ObjectNode wrapper = mapper.createObjectNode();
//            wrapper.set("resource", medicationRequests);
            jsonMedicationRequest = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(medicationRequests);

        } catch (Exception e) {
            System.out.println("Error creating MedicationRequest JSON: " + e.getMessage());
            e.printStackTrace();
            jsonMedicationRequest = "{}";
        }
    }

    private void setJsonMrBundle() {
        // This method generates the complete bundle for the SMART Claim API
        // Following the manifest specifications: Composition, Encounter, Patient, Organization, Practitioner, Condition, Procedure, MedicationRequest, DiagnosticReport, Device
        try {
            // Create the bundle with proper entry format following the exact reference JSON structure
            StringBuilder bundleBuilder = new StringBuilder();
            bundleBuilder.append("{\n");
            bundleBuilder.append("  \"resourceType\": \"Bundle\",\n");
            bundleBuilder.append("  \"id\": \"").append(generateResourceId(sanitizeString(TNoSEP.getText()))).append("\",\n");
            bundleBuilder.append("  \"meta\": {\n");
            bundleBuilder.append("    \"lastUpdated\": \"").append(Sequel.cariIsi("select CURRENT_TIMESTAMP()")).append("\"\n");
            bundleBuilder.append("  },\n");
            bundleBuilder.append("  \"identifier\": {\n");
            bundleBuilder.append("    \"system\": \"sep\",\n");
            bundleBuilder.append("    \"value\": \"").append(TNoSEP.getText()).append("\"\n");
            bundleBuilder.append("  },\n");
            bundleBuilder.append("  \"type\": \"document\",\n");
            bundleBuilder.append("  \"entry\": [\n");

            // Following the order in the reference JSON (sukses2.json):
            // 1. Patient resource (patient information)
            if (ChkPatient.isSelected()) {
                addEntryToBundle(bundleBuilder, "Patient", jsonPatient);
            }

            // 2. Organization resources (multiple organizations for different departments)
            if (ChkOrganization.isSelected()) {
                addEntryToBundle(bundleBuilder, "Organization", jsonOrganization);
            }

            // 3. Practitioner resources (doctors and other healthcare professionals)
            if (ChkPractitioner.isSelected()) {
                addEntryToBundle(bundleBuilder, "Practitioner", jsonPractitioner);
            }

            // 4. Condition resources (diagnoses)
            if (ChkCondition.isSelected()) {
                addEntryToBundle(bundleBuilder, "Condition", jsonCondition);
            }

            // 5. Procedure resources (procedures performed)
            if (ChkProcedure.isSelected()) {
                addEntryToBundle(bundleBuilder, "Procedure", "[" + jsonProcedure + "]");
            }

            // 6. MedicationRequest resources (prescriptions)
            if (ChkMedReq.isSelected()) {
                addEntryToBundle(bundleBuilder, "MedicationRequest", "[" + jsonMedicationRequest + "]");
            }

            // 7. DiagnosticReport resources (lab results, radiology, etc.)
            if (ChkDiagnosticReport.isSelected()) {
                addEntryToBundle(bundleBuilder, "DiagnosticReport", "[" + jsonDiagnosticReport + "]");
            }

            // 8. Device resources (medical devices)
            if (ChkDevice.isSelected()) {
                addEntryToBundle(bundleBuilder, "Device", "[" + jsonDevice + "]");
            }

            // 9. Composition resource (document composition) - typically at the end in the reference
            if (ChkComposition.isSelected()) {
                addEntryToBundle(bundleBuilder, "Composition", jsonComposititon);
            }

            // 10. Encounter resource (the visit/encounter information) - typically after composition in the reference
            if (ChkEncounter.isSelected()) {
                addEntryToBundle(bundleBuilder, "Encounter", jsonEncounter);
            }

            // Remove the last comma if exists and close the entries array
            String bundleStr = bundleBuilder.toString();
            if (bundleStr.endsWith(",\n")) {
                bundleStr = bundleStr.substring(0, bundleStr.length() - 2) + "\n";
            }

            // Close the bundle
            bundleStr += "  ]\n";
            bundleStr += "}";

            jsonMrBundle = bundleStr;

        } catch (Exception e) {
            System.out.println("Error building bundle: " + e.getMessage());
            e.printStackTrace();

            // Fallback to original format if there's an error - but ensure valid JSON
            StringBuilder fallbackBundle = new StringBuilder();
            fallbackBundle.append("{\n");
            fallbackBundle.append("  \"resourceType\": \"Bundle\",\n");
            fallbackBundle.append("  \"id\": \"").append(sanitizeString(KdPPKBPJS.getText())).append("-").append(sanitizeString(KdPPKKemKes.getText())).append("-34-").append(processUUIDString(sanitizeString(TNoSEP.getText()))).append("\",\n");
            fallbackBundle.append("  \"meta\": {\n");
            fallbackBundle.append("    \"lastUpdated\": \"").append(Sequel.cariIsi("select CURRENT_TIMESTAMP()")).append("\"\n");
            fallbackBundle.append("  },\n");
            fallbackBundle.append("  \"identifier\": {\n");
            fallbackBundle.append("    \"system\": \"sep\",\n");
            fallbackBundle.append("    \"value\": \"").append(sanitizeString(TNoSEP.getText())).append("\"\n");
            fallbackBundle.append("  },\n");
            fallbackBundle.append("  \"type\": \"document\",\n");
            fallbackBundle.append("  \"entry\": [\n");

            // Collect all valid resources first to build proper JSON array in the order from ex.json:
            List<String> validEntries = new ArrayList<>();

            // Add resources in the order from ex.json: Patient, Organization, Practitioner, Condition, Composition, Encounter
            addResourceIfValidToList(validEntries, jsonPatient, "Patient");
            addResourceIfValidToList(validEntries, jsonOrganization, "Organization");
            addResourceIfValidToList(validEntries, jsonPractitioner, "Practitioner");
            addResourceIfValidToList(validEntries, jsonCondition, "Condition");
            addResourceIfValidToList(validEntries, jsonComposititon, "Composition");
            addResourceIfValidToList(validEntries, jsonEncounter, "Encounter");

            // Add all valid entries to the bundle
            for (int i = 0; i < validEntries.size(); i++) {
                fallbackBundle.append(validEntries.get(i));
                if (i < validEntries.size() - 1) {
                    fallbackBundle.append(",");
                }
                fallbackBundle.append("\n");
            }

            fallbackBundle.append("  ]\n");
            fallbackBundle.append("}");
            jsonMrBundle = fallbackBundle.toString();
        }
    }

    // Helper method to properly format bundle entries following official FHIR Bundle format (no fullUrl)
    private void addEntryToBundle(StringBuilder bundleBuilder, String resourceType, String jsonResource) {
        if (jsonResource != null && !jsonResource.isEmpty() && !jsonResource.equals("null")) {
            try {
                // Parse the JSON to extract the resource
                JsonNode resourceNode = mapper.readTree(jsonResource);

                // Check if resourceNode has a "resource" property
                JsonNode resourceData = resourceNode.path("resource");

                if (resourceData.isMissingNode() || resourceData.isNull()) {
                    // If no "resource" property, use the entire JSON
                    resourceData = resourceNode;
                }

                // If resource is an array, process each item
                if (resourceData.isArray()) {
                    int size = resourceData.size();
                    for (int i = 0; i < size; i++) {
                        JsonNode item = resourceData.get(i);
                        if (item != null) {
                            bundleBuilder.append("  {\n");
                            bundleBuilder.append("    \"resource\": ").append(item.toString()).append("\n");
                            bundleBuilder.append("  },\n");
                        }
                    }
                } else {
                    // If resource is a single object
                    bundleBuilder.append("  {\n");
                    bundleBuilder.append("    \"resource\": ").append(resourceData.toString()).append("\n");
                    bundleBuilder.append("  },\n");
                }
            } catch (Exception e) {
                System.out.println("Error processing " + resourceType + " resource: " + e.getMessage());
                // If parsing fails, add the resource as is but with proper structure (no fullUrl)
                bundleBuilder.append("  {\n");
                bundleBuilder.append("    \"resource\": ").append(jsonResource).append("\n");
                bundleBuilder.append("  },\n");
            }
        }
    }

    // Helper method to properly format bundle entries following official FHIR Bundle format (no fullUrl)
    private void addEntryToBundleforArray(StringBuilder bundleBuilder, String resourceType, String jsonResource) {
        if (jsonResource != null && !jsonResource.isEmpty() && !jsonResource.equals("null")) {
            try {
                // Parse the JSON to extract the resource
                JsonNode resourceNode = mapper.readTree(jsonResource);

                // Check if resourceNode has a "resource" property
                JsonNode resourceData = resourceNode.path("resource");

                // If resource is an array, process each item
                if (resourceData.isArray()) {
                    int size = resourceData.size();
                    for (int i = 0; i < size; i++) {
                        JsonNode item = resourceData.get(i);
                        if (item != null) {

                            bundleBuilder.append(item.toString()).append("\n");

                        }
                    }
                } else {
                    // If resource is a single object
                    bundleBuilder.append(resourceData.toString()).append("\n");
                }
            } catch (Exception e) {
                System.out.println("Error processing " + resourceType + " resource: " + e.getMessage());
                // If parsing fails, add the resource as is but with proper structure (no fullUrl)
                bundleBuilder.append(jsonResource).append("\n");
            }
        }
    }

    /**
     * Helper method to safely add a resource to the fallback bundle if it's
     * valid JSON Returns true if resource was added, false otherwise -
     * Following official FHIR Bundle format (no fullUrl)
     */
    private boolean addResourceIfValid(StringBuilder bundleBuilder, String jsonResource, String resourceType) {
        if (jsonResource != null && !jsonResource.isEmpty() && !jsonResource.equals("null") && !jsonResource.equals("{}")) {
            try {
                // Parse the JSON to validate it and extract the resource part
                JsonNode fullNode = mapper.readTree(jsonResource);
                JsonNode resourceNode = fullNode.get("resource");

                if (resourceNode != null) {
                    // If resource is an array, add each item separately
                    if (resourceNode.isArray()) {
                        for (JsonNode item : resourceNode) {
                            bundleBuilder.append("  {\n");
                            bundleBuilder.append("    \"resource\": ").append(item.toString()).append("\n");
                            bundleBuilder.append("  },\n");
                        }
                    } else {
                        // If resource is a single object
                        bundleBuilder.append("  {\n");
                        bundleBuilder.append("    \"resource\": ").append(resourceNode.toString()).append("\n");
                        bundleBuilder.append("  },\n");
                    }
                    return true;
                }
            } catch (Exception e) {
                // If parsing fails, skip this resource to avoid corrupting the bundle
                System.out.println("Skipping invalid " + resourceType + " resource: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * Helper method to safely add a resource to the fallback bundle list if
     * it's valid JSON - Following official FHIR Bundle format (no fullUrl)
     */
    private void addResourceIfValidToList(List<String> entries, String jsonResource, String resourceType) {
        if (jsonResource != null && !jsonResource.isEmpty() && !jsonResource.equals("null") && !jsonResource.equals("{}")) {
            try {
                // Parse the JSON to validate it and extract the resource part
                JsonNode fullNode = mapper.readTree(jsonResource);
                JsonNode resourceNode = fullNode.get("resource");

                if (resourceNode != null) {
                    // If resource is an array, add each item separately
                    if (resourceNode.isArray()) {
                        for (JsonNode item : resourceNode) {
                            StringBuilder entry = new StringBuilder();
                            entry.append("  {\n");
                            entry.append("    \"resource\": ").append(item.toString()).append("\n");
                            entry.append("  }");
                            entries.add(entry.toString());
                        }
                    } else {
                        // If resource is a single object
                        StringBuilder entry = new StringBuilder();
                        entry.append("  {\n");
                        entry.append("    \"resource\": ").append(resourceNode.toString()).append("\n");
                        entry.append("  }");
                        entries.add(entry.toString());
                    }
                }
            } catch (Exception e) {
                // If parsing fails, skip this resource to avoid corrupting the bundle
                System.out.println("Skipping invalid " + resourceType + " resource: " + e.getMessage());
            }
        }
    }

    /**
     * Helper method to generate bundle for display purposes This is a
     * simplified version that doesn't cause circular calls
     */
    private String generateBundleForDisplay() {
        try {
            // Create the bundle with proper entry format
            StringBuilder bundleBuilder = new StringBuilder();
            bundleBuilder.append("{\n");
            bundleBuilder.append("        \"resourceType\": \"Bundle\",\n");
            bundleBuilder.append("        \"id\": \"").append(KdPPKBPJS.getText()).append("-").append(KdPPKKemKes.getText()).append("-34-").append(processUUIDString(TNoSEP.getText())).append("\",\n");
            bundleBuilder.append("        \"meta\": {\n");
            bundleBuilder.append("            \"lastUpdated\": \"").append(Sequel.cariIsi("select CURRENT_TIMESTAMP()")).append("\"\n");
            bundleBuilder.append("        },\n");
            bundleBuilder.append("        \"identifier\": {\n");
            bundleBuilder.append("            \"system\": \"SEP\",\n");
            bundleBuilder.append("            \"value\": \"").append(TNoSEP.getText()).append("\"\n");
            bundleBuilder.append("        },\n");
            bundleBuilder.append("        \"type\": \"Document\",\n");
            bundleBuilder.append("        \"entry\": [\n");

            // Parse each JSON resource and add it to the bundle in the order from sukses2.json:
            addEntryToBundle(bundleBuilder, "Patient", jsonPatient);
            addEntryToBundle(bundleBuilder, "Organization", jsonOrganization);
            addEntryToBundle(bundleBuilder, "Practitioner", jsonPractitioner);
            addEntryToBundle(bundleBuilder, "Condition", jsonCondition);
            addEntryToBundle(bundleBuilder, "Procedure", jsonProcedure);
            addEntryToBundle(bundleBuilder, "MedicationRequest", jsonMedicationRequest);
            addEntryToBundle(bundleBuilder, "DiagnosticReport", jsonDiagnosticReport);
            addEntryToBundle(bundleBuilder, "Device", jsonDevice);
            addEntryToBundle(bundleBuilder, "Composition", jsonComposititon);
            addEntryToBundle(bundleBuilder, "Encounter", jsonEncounter);

            // Remove the last comma if exists and close the entries array
            String bundleStr = bundleBuilder.toString();
            if (bundleStr.endsWith(",\n")) {
                bundleStr = bundleStr.substring(0, bundleStr.length() - 2) + "\n";
            }

            // Close the bundle
            bundleStr += "        ]\n";
            bundleStr += "    }";

            return bundleStr;

        } catch (Exception e) {
            System.out.println("Error building display bundle: " + e.getMessage());
            return null;
        }
    }

    private void setJsonPractitioner() {
        try {
            ArrayNode practitionersArray = mapper.createArrayNode();

            // Process all practitioners from the table
            for (int i = 0; i < tbPractitioner.getRowCount(); i++) {
                String identifier = tbPractitioner.getValueAt(i, 3) != null ? sanitizeString(tbPractitioner.getValueAt(i, 3).toString()) : "";
                String name = tbPractitioner.getValueAt(i, 2) != null ? sanitizeString(tbPractitioner.getValueAt(i, 2).toString()) : "";
                String address = tbPractitioner.getValueAt(i, 6) != null ? sanitizeString(tbPractitioner.getValueAt(i, 6).toString()) : "";
                String birthDate = tbPractitioner.getValueAt(i, 5) != null ? sanitizeString(tbPractitioner.getValueAt(i, 5).toString()) : "";
                String gender = tbPractitioner.getValueAt(i, 4) != null ? sanitizeString(tbPractitioner.getValueAt(i, 4).toString()) : "";
                String tmpLahir = tbPractitioner.getValueAt(i, 7) != null ? sanitizeString(tbPractitioner.getValueAt(i, 7).toString()) : "";
                String golDrh = tbPractitioner.getValueAt(i, 8) != null ? sanitizeString(tbPractitioner.getValueAt(i, 8).toString()) : "";
                String agama = tbPractitioner.getValueAt(i, 9) != null ? sanitizeString(tbPractitioner.getValueAt(i, 9).toString()) : "";
                String sttsNikah = tbPractitioner.getValueAt(i, 10) != null ? sanitizeString(tbPractitioner.getValueAt(i, 10).toString()) : "";
                String kdSps = tbPractitioner.getValueAt(i, 11) != null ? sanitizeString(tbPractitioner.getValueAt(i, 11).toString()) : "";
                String alumni = tbPractitioner.getValueAt(i, 12) != null ? sanitizeString(tbPractitioner.getValueAt(i, 12).toString()) : "";
                String noIjnPraktek = tbPractitioner.getValueAt(i, 13) != null ? sanitizeString(tbPractitioner.getValueAt(i, 13).toString()) : "";
                String status = tbPractitioner.getValueAt(i, 14) != null ? sanitizeString(tbPractitioner.getValueAt(i, 14).toString()) : "";
                String email = tbPractitioner.getValueAt(i, 15) != null ? sanitizeString(tbPractitioner.getValueAt(i, 15).toString()) : "";
                String noTelp = tbPractitioner.getValueAt(i, 16) != null ? sanitizeString(tbPractitioner.getValueAt(i, 16).toString()) : "";
                String kdDokter = tbPractitioner.getValueAt(i, 1) != null ? sanitizeString(tbPractitioner.getValueAt(i, 1).toString()) : "";

                //validasi lenght address
                String spesialis = Sequel.cariIsi("select nm_sps from spesialis where kd_sps='" + kdSps + "'");
                if (address.length() > 50) {
                    address = address.substring(0, 50).trim();
                }

                if (tmpLahir.length() > 20) {
                    tmpLahir = tmpLahir.substring(0, 20).trim();
                }

                // Normalize gender value
                if (gender.equals("P")) {
                    gender = "female";
                } else if (gender.equals("L")) {
                    gender = "male";
                } else {
                    gender = "unknown";
                }

                // Create practitioner JSON object
                ObjectNode practitioner = mapper.createObjectNode();
                practitioner.put("resourceType", "Practitioner");
                // Use the kdDokter as the unique ID following the requirement
                practitioner.put("id", generateResourceIdForPractitioner(sanitizeString(kdDokter)));

                // Identifiers
                ArrayNode identifiers = mapper.createArrayNode();

                // Add SIP identifier from the no_ijn_praktek column
                if (!noIjnPraktek.isEmpty()) {
                    ObjectNode sipIdentifier = mapper.createObjectNode();
                    sipIdentifier.put("use", "official");
                    sipIdentifier.put("system", "urn:oid:nomor_sip");
                    sipIdentifier.put("value", noIjnPraktek);
                    identifiers.add(sipIdentifier);
                } else {
                    // Fallback if no SIP is available
                    ObjectNode sipIdentifier = mapper.createObjectNode();
                    sipIdentifier.put("use", "official");
                    sipIdentifier.put("system", "urn:oid:nomor_sip");
                    sipIdentifier.put("value", "1.2.01.3173.1834/14022/04.16.1");
                    identifiers.add(sipIdentifier);
                }

                // Add NIK identifier
                if (!identifier.isEmpty()) {
                    ObjectNode nikIdentifier = mapper.createObjectNode();
                    nikIdentifier.put("use", "official");
                    ObjectNode nikType = mapper.createObjectNode();
                    ArrayNode nikTypeCoding = mapper.createArrayNode();
                    nikTypeCoding.add(createCoding("http://hl7.org/fhir/v2/0203", "NNIDN", null));
                    nikType.set("coding", nikTypeCoding);
                    nikIdentifier.set("type", nikType);
                    nikIdentifier.put("value", identifier);
                    ObjectNode nikAssigner = mapper.createObjectNode();
                    nikAssigner.put("display", "KEMDAGRI");
                    nikIdentifier.set("assigner", nikAssigner);
                    identifiers.add(nikIdentifier);
                }

                practitioner.set("identifier", identifiers);

                // Name
                ArrayNode names = mapper.createArrayNode();
                ObjectNode nameObj = mapper.createObjectNode();
                nameObj.put("use", "official");
                nameObj.put("text", name);

                // Add additional name details if available
                if (!tmpLahir.isEmpty()) {
                    ArrayNode given = mapper.createArrayNode();
                    given.add(tmpLahir); // Using birthplace as given name component
                    nameObj.set("given", given);
                }
                names.add(nameObj);
                practitioner.set("name", names);

                // Telecom (phone and email)
                ArrayNode telecoms = mapper.createArrayNode();
                if (!noTelp.isEmpty()) {
                    telecoms.add(createTelecom("phone", noTelp, "work"));
                } else {
                    telecoms.add(createTelecom("phone", "0816-970-112", "work"));
                }
                if (!email.isEmpty()) {
                    telecoms.add(createTelecom("email", email, "work"));
                } else {
                    telecoms.add(createTelecom("email", "suzanna.immanuel@gmail.com", "work"));
                }
                practitioner.set("telecom", telecoms);

                // Address
                ArrayNode addresses = mapper.createArrayNode();
                ObjectNode addressObj = mapper.createObjectNode();
                addressObj.put("use", "home");
                addressObj.put("text", address);
                ArrayNode lines = mapper.createArrayNode();
                lines.add(address + ", " + tmpLahir);
                addressObj.set("line", lines);
                addressObj.put("city", "");
                addressObj.put("postalCode", "");
                addressObj.put("country", "INDONESIA");
                addresses.add(addressObj);
                practitioner.set("address", addresses);

                practitioner.put("gender", gender);
                practitioner.put("birthDate", birthDate);

                // Add qualifications if available
                if (!kdSps.isEmpty() || !alumni.isEmpty() || !noIjnPraktek.isEmpty()) {
                    ArrayNode qualifications = mapper.createArrayNode();
                    ObjectNode qualification = mapper.createObjectNode();

                    // Code for qualification
                    ObjectNode code = mapper.createObjectNode();
                    ArrayNode coding = mapper.createArrayNode();
                    ObjectNode codingObj = mapper.createObjectNode();
                    codingObj.put("system", "http://hl7.org/fhir/v2/0360");
                    codingObj.put("code", kdSps);
                    codingObj.put("display", spesialis);
                    coding.add(codingObj);
                    code.set("coding", coding);
                    code.put("text", "Doctor");
                    qualification.set("code", code);

                    // Period if needed
                    if (!noIjnPraktek.isEmpty()) {
                        ObjectNode period = mapper.createObjectNode();
                        period.put("start", birthDate); // Use birth date as start of qualification
                        qualification.set("period", period);
                    }

                    qualifications.add(qualification);
                    practitioner.set("qualification", qualifications);
                }

                practitionersArray.add(practitioner);
            }

            // If no practitioners were found, create a default one
            if (practitionersArray.size() == 0) {
                ObjectNode practitioner = mapper.createObjectNode();
                practitioner.put("resourceType", "Practitioner");
                practitioner.put("id", generateResourceId(sanitizeString(TNoSEP.getText())));

                ArrayNode identifiers = mapper.createArrayNode();
                ObjectNode sipIdentifier = mapper.createObjectNode();
                sipIdentifier.put("use", "official");
                sipIdentifier.put("system", "urn:oid:nomor_sip");
                sipIdentifier.put("value", "1.2.01.3173.1834/14022/04.16.1");
                identifiers.add(sipIdentifier);
                practitioner.set("identifier", identifiers);

                ArrayNode names = mapper.createArrayNode();
                ObjectNode nameObj = mapper.createObjectNode();
                nameObj.put("use", "official");
                nameObj.put("text", "Default Practitioner");
                names.add(nameObj);
                practitioner.set("name", names);

                ArrayNode telecoms = mapper.createArrayNode();
                telecoms.add(createTelecom("phone", "0816-970-112", "work"));
                practitioner.set("telecom", telecoms);

                ArrayNode addresses = mapper.createArrayNode();
                ObjectNode addressObj = mapper.createObjectNode();
                addressObj.put("use", "home");
                ArrayNode lines = mapper.createArrayNode();
                lines.add("Default Address, Godean, Sleman, DIY");
                addressObj.set("line", lines);
                addressObj.put("text", "Default Address, Godean, Sleman, DIY");
                addressObj.put("city", "");
                addressObj.put("postalCode", "");
                addressObj.put("country", "");
                addresses.add(addressObj);
                practitioner.set("address", addresses);

                practitioner.put("gender", "unknown");
                practitioner.put("birthDate", "1970-01-01");

                practitionersArray.add(practitioner);
            }

            ObjectNode wrapper = mapper.createObjectNode();
            wrapper.set("resource", practitionersArray);
            jsonPractitioner = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(wrapper);

        } catch (Exception e) {
            System.out.println("Error creating Practitioner JSON: " + e.getMessage());
            e.printStackTrace();
            jsonPractitioner = "{}";
        }

    }

    private void tampilDiagnosa(String norawat, String status) {
        Valid.tabelKosong(TabModeDiagnosaPasien);
        try {
            psdiagnosapasien = koneksi.prepareStatement("SELECT\n"
                    + "	reg_periksa.tgl_registrasi, \n"
                    + "	diagnosa_pasien.no_rawat, \n"
                    + "	reg_periksa.no_rkm_medis, \n"
                    + "	pasien.nm_pasien, \n"
                    + "	pasien.jk, \n"
                    + "	diagnosa_pasien.kd_penyakit, \n"
                    + "	penyakit.nm_penyakit, \n"
                    + "	diagnosa_pasien.`STATUS`, \n"
                    + "	diagnosa_pasien.status_penyakit, \n"
                    + "	diagnosa_pasien.prioritas, \n"
                    + "	reg_periksa.stts_daftar, \n"
                    + "	reg_periksa.status_poli, \n"
                    + "	mapping_snomed_icd10.kd_snomed, \n"
                    + "	mapping_snomed_icd10.display, \n"
                    + "	mapping_snomed_icd10.system\n"
                    + "FROM\n"
                    + "	diagnosa_pasien\n"
                    + "	INNER JOIN\n"
                    + "	reg_periksa\n"
                    + "	INNER JOIN\n"
                    + "	pasien\n"
                    + "	INNER JOIN\n"
                    + "	penyakit\n"
                    + "	ON \n"
                    + "		diagnosa_pasien.no_rawat = reg_periksa.no_rawat AND\n"
                    + "		reg_periksa.no_rkm_medis = pasien.no_rkm_medis AND\n"
                    + "		diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit\n"
                    + "	INNER JOIN\n"
                    + "	mapping_snomed_icd10\n"
                    + "	ON \n"
                    + "		penyakit.kd_penyakit = mapping_snomed_icd10.icd10\n"
                    + "WHERE\n"
                    + "	diagnosa_pasien.no_rawat ='" + norawat + "' and diagnosa_pasien.status='" + status + "'");
            try {
                rs = psdiagnosapasien.executeQuery();
                while (rs.next()) {
                    TabModeDiagnosaPasien.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString("prioritas"),
                        rs.getString("stts_daftar"),
                        rs.getString("status_poli"),
                        rs.getString("kd_snomed"),
                        rs.getString("display"),
                        rs.getString("system")});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (psdiagnosapasien != null) {
                    psdiagnosapasien.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

    }

    private void tampilProcedure(String norawat, String status) {
        Valid.tabelKosong(TabModeTindakanPasien);
        try {
            pstindakanpasien = koneksi.prepareStatement("SELECT\n"
                    + "	rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat,concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) as performstart,\n"
                    + "	rawat_jl_dr.no_rawat,\n"
                    + "	reg_periksa.no_rkm_medis,\n"
                    + "	pasien.nm_pasien,\n"
                    + "	rawat_jl_dr.kd_jenis_prw,\n"
                    + "	jns_perawatan.nm_perawatan,\n"
                    + "	rawat_jl_dr.kd_dokter,\n"
                    + "	dokter.nm_dokter,\n"
                    + "	\"\", \"\",\n"
                    + "	kategori_perawatan.snomed_code,\n"
                    + "	kategori_perawatan.snomed_display,kategori_perawatan.snomed_system,\n"
                    + "	\"\" \n"
                    + "FROM\n"
                    + "	pasien\n"
                    + "	INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis\n"
                    + "	INNER JOIN rawat_jl_dr ON reg_periksa.no_rawat = rawat_jl_dr.no_rawat\n"
                    + "	INNER JOIN dokter ON rawat_jl_dr.kd_dokter = dokter.kd_dokter\n"
                    + "	INNER JOIN jns_perawatan ON rawat_jl_dr.kd_jenis_prw = jns_perawatan.kd_jenis_prw\n"
                    + "	INNER JOIN poliklinik ON reg_periksa.kd_poli = poliklinik.kd_poli\n"
                    + "	INNER JOIN penjab ON reg_periksa.kd_pj = penjab.kd_pj\n"
                    + "	INNER JOIN kategori_perawatan ON jns_perawatan.kd_kategori = kategori_perawatan.kd_kategori \n"
                    + "WHERE\n"
                    + "	rawat_jl_dr.no_rawat = '" + norawat + "'");
            try {
                rs = pstindakanpasien.executeQuery();
                while (rs.next()) {
                    TabModeTindakanPasien.addRow(new Object[]{
                        rs.getString("tgl_perawatan") + " " + rs.getString("jam_rawat"), // Tgl.Rawat
                        rs.getString("no_rawat"), // No.Rawat
                        rs.getString("no_rkm_medis"), // No.R.M.
                        rs.getString("nm_pasien"), // Nama Pasien
                        rs.getString("kd_jenis_prw"), // Kode Tindakan
                        rs.getString("nm_perawatan"), // Nama Tindakan
                        rs.getString("kd_dokter"), // Kode Dokter
                        rs.getString("nm_dokter"), // nmpoli
                        rs.getString("performstart"), // Empty column 1
                        "", // Empty column 2
                        rs.getString("snomed_code"), // Kode SNOMED
                        rs.getString("snomed_display"), // SNOMED Display
                        rs.getString("snomed_system") // SNOMED System (empty)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pstindakanpasien != null) {
                    pstindakanpasien.close();
                }
            }

            pstindakanpasien = koneksi.prepareStatement("SELECT\n"
                    + "	rawat_inap_dr.no_rawat,\n"
                    + "	concat( reg_periksa.tgl_registrasi, ' ', reg_periksa.jam_reg ) AS performstart,\n"
                    + "	reg_periksa.no_rkm_medis,\n"
                    + "	pasien.nm_pasien,\n"
                    + "	rawat_inap_dr.kd_jenis_prw,\n"
                    + "	jns_perawatan_inap.nm_perawatan,\n"
                    + "	rawat_inap_dr.kd_dokter,\n"
                    + "	dokter.nm_dokter,\n"
                    + "	rawat_inap_dr.tgl_perawatan,\n"
                    + "	rawat_inap_dr.jam_rawat,\n"
                    + "	penjab.png_jawab,\n"
                    + "	poliklinik.nm_poli,\n"
                    + "	rawat_inap_dr.material,\n"
                    + "	rawat_inap_dr.bhp,\n"
                    + "	rawat_inap_dr.tarif_tindakandr,\n"
                    + "	rawat_inap_dr.kso,\n"
                    + "	rawat_inap_dr.menejemen,\n"
                    + "	rawat_inap_dr.biaya_rawat,\n"
                    + "	kategori_perawatan.snomed_code,\n"
                    + "	kategori_perawatan.snomed_display,\n"
                    + "	kategori_perawatan.snomed_system \n"
                    + "FROM\n"
                    + "	pasien\n"
                    + "	INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis\n"
                    + "	INNER JOIN rawat_inap_dr ON reg_periksa.no_rawat = rawat_inap_dr.no_rawat\n"
                    + "	INNER JOIN dokter ON rawat_inap_dr.kd_dokter = dokter.kd_dokter\n"
                    + "	INNER JOIN jns_perawatan_inap ON rawat_inap_dr.kd_jenis_prw = jns_perawatan_inap.kd_jenis_prw\n"
                    + "	INNER JOIN poliklinik ON reg_periksa.kd_poli = poliklinik.kd_poli\n"
                    + "	INNER JOIN penjab ON reg_periksa.kd_pj = penjab.kd_pj\n"
                    + "	INNER JOIN kategori_perawatan ON jns_perawatan_inap.kd_kategori = kategori_perawatan.kd_kategori \n"
                    + "WHERE\n"
                    + "	rawat_inap_dr.no_rawat = '" + norawat + "'");
            try {
                rs = pstindakanpasien.executeQuery();
                while (rs.next()) {
                    TabModeTindakanPasien.addRow(new Object[]{
                        rs.getString("tgl_perawatan") + " " + rs.getString("jam_rawat"), // Tgl.Rawat
                        rs.getString("no_rawat"), // No.Rawat
                        rs.getString("no_rkm_medis"), // No.R.M.
                        rs.getString("nm_pasien"), // Nama Pasien
                        rs.getString("kd_jenis_prw"), // Kode Tindakan
                        rs.getString("nm_perawatan"), // Nama Tindakan
                        rs.getString("kd_dokter"), // Kode Dokter
                        rs.getString("nm_dokter"), // nmpoli
                        rs.getString("performstart"), // Empty column 1
                        "", // Empty column 2
                        rs.getString("snomed_code"), // Kode SNOMED
                        rs.getString("snomed_display"), // SNOMED Display
                        rs.getString("snomed_system") // SNOMED System (empty)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pstindakanpasien != null) {
                    pstindakanpasien.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampilLabPasien(String norawat) {
        try {
            Valid.tabelKosong(TabModeLabPasien);
            pslabpasien = koneksi.prepareStatement(
                    "SELECT\n"
                    + "	periksa_lab.no_rawat,\n"
                    + "	periksa_lab.kd_jenis_prw,\n"
                    + "	detail_periksa_lab.tgl_periksa,\n"
                    + "	detail_periksa_lab.jam,\n"
                    + "	detail_periksa_lab.id_template,\n"
                    + "	detail_periksa_lab.nilai,\n"
                    + "	detail_periksa_lab.nilai_rujukan,\n"
                    + "	detail_periksa_lab.keterangan,\n"
                    + "	periksa_lab.`status`,\n"
                    + "	periksa_lab.kd_dokter,\n"
                    + "	template_laboratorium.Pemeriksaan,\n"
                    + "	jns_perawatan_lab.nm_perawatan,\n"
                    + "	satu_sehat_mapping_lab.sampel_code AS snomed,\n"
                    + "	satu_sehat_mapping_lab.code AS loinc\n"
                    + "FROM\n"
                    + "	periksa_lab\n"
                    + "	INNER JOIN detail_periksa_lab ON periksa_lab.no_rawat = detail_periksa_lab.no_rawat \n"
                    + "	AND periksa_lab.kd_jenis_prw = detail_periksa_lab.kd_jenis_prw\n"
                    + "	INNER JOIN template_laboratorium ON detail_periksa_lab.id_template = template_laboratorium.id_template\n"
                    + "	INNER JOIN jns_perawatan_lab ON detail_periksa_lab.kd_jenis_prw = jns_perawatan_lab.kd_jenis_prw \n"
                    + "	AND periksa_lab.kd_jenis_prw = jns_perawatan_lab.kd_jenis_prw \n"
                    + "	AND template_laboratorium.kd_jenis_prw = jns_perawatan_lab.kd_jenis_prw\n"
                    + "	LEFT JOIN satu_sehat_mapping_lab ON detail_periksa_lab.id_template = satu_sehat_mapping_lab.id_template \n"
                    + "WHERE\n"
                    + "	periksa_lab.no_rawat = '" + norawat + "' ORDER BY periksa_lab.tgl_periksa ASC, periksa_lab.jam ASC");

            try {

                rs = pslabpasien.executeQuery();
                while (rs.next()) {
                    TabModeLabPasien.addRow(new String[]{
                        rs.getString("tgl_periksa"),
                        rs.getString("jam"),
                        rs.getString("nm_perawatan"),
                        rs.getString("Pemeriksaan"),
                        rs.getString("snomed"),
                        rs.getString("loinc"),
                        rs.getString("snomed"),
                        rs.getString("loinc"),
                        rs.getString("nilai"),
                        rs.getString("nilai_rujukan"),
                        rs.getString("keterangan"),
                        rs.getString("kd_dokter"),
                        rs.getString("kd_dokter")});
                }
            } catch (Exception e) {
                System.out.println("Notif ps : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pslabpasien != null) {
                    pslabpasien.close();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    // Private function that takes a UUID string and returns a processed result string
    private String processUUIDString(String uuidString) {
        String finalUUID;
        try {
            // Attempt to use UUID from input
            UUID uuid = UUID.fromString(uuidString);
            finalUUID = uuid.toString();
        } catch (IllegalArgumentException e) {
            // If input is invalid, generate a static UUID based on the input string
            finalUUID = generateStaticUUID(uuidString);
        }
        return finalUUID;
    }

    private String generateStaticUUID(String input) {
        try {
            // Create a SHA-256 hash of the input string
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert the first 16 bytes of the hash to a UUID
            long mostSigBits = 0;
            long leastSigBits = 0;
            for (int i = 0; i < 8; i++) {
                mostSigBits = (mostSigBits << 8) | (hash[i] & 0xff);
            }
            for (int i = 8; i < 16; i++) {
                leastSigBits = (leastSigBits << 8) | (hash[i] & 0xff);
            }

            UUID staticUUID = new UUID(mostSigBits, leastSigBits);
            return staticUUID.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    private void tampilRadPasien(String norawat) {
        try {
            Valid.tabelKosong(TabModeRadPasien);
            String kesan = "IF ( LOCATE( 'kesan', hasil_radiologi.hasil ) > 0, SUBSTRING( hasil_radiologi.hasil, LOCATE( 'kesan', hasil_radiologi.hasil ) + LENGTH( 'kesan' ) + 1 ), hasil_radiologi.hasil  ) AS kesan";
            String klinis = "IF ( LOCATE( 'kesan', hasil_radiologi.hasil ) > 0, SUBSTRING( hasil_radiologi.hasil, 1, LOCATE( 'kesan', hasil_radiologi.hasil ) - 1 ), hasil_radiologi.hasil ) AS klinis";
            psradpasien = koneksi.prepareStatement(
                    "SELECT\n"
                    + "	periksa_radiologi.tgl_periksa,\n"
                    + "	periksa_radiologi.jam,\n"
                    + "	periksa_radiologi.no_rawat,\n"
                    + "	periksa_radiologi.kd_jenis_prw,\n"
                    + "	jns_perawatan_radiologi.nm_perawatan,\n"
                    + "	satu_sehat_mapping_radiologi.sampel_code,\n"
                    + "	satu_sehat_mapping_radiologi.code,\n"
                    + "	periksa_radiologi.kd_dokter,\n"
                    + "	dokter.nm_dokter,\n"
                    + "	" + klinis + " ,\n"
                    + "	" + kesan + " \n"
                    + "FROM\n"
                    + "	periksa_radiologi\n"
                    + "	INNER JOIN jns_perawatan_radiologi ON periksa_radiologi.kd_jenis_prw = jns_perawatan_radiologi.kd_jenis_prw\n"
                    + "	INNER JOIN dokter ON periksa_radiologi.kd_dokter = dokter.kd_dokter\n"
                    + "	LEFT JOIN satu_sehat_mapping_radiologi ON jns_perawatan_radiologi.kd_jenis_prw = satu_sehat_mapping_radiologi.kd_jenis_prw\n"
                    + "	INNER JOIN hasil_radiologi ON periksa_radiologi.no_rawat = hasil_radiologi.no_rawat \n"
                    + "	AND periksa_radiologi.tgl_periksa = hasil_radiologi.tgl_periksa \n"
                    + "	AND periksa_radiologi.jam = hasil_radiologi.jam \n"
                    + "WHERE\n"
                    + "	periksa_radiologi.no_rawat ='" + norawat + "' ORDER BY periksa_radiologi.tgl_periksa ASC, periksa_radiologi.jam ASC");

            try {

                rs = psradpasien.executeQuery();
                while (rs.next()) {
                    TabModeRadPasien.addRow(new String[]{
                        rs.getString("tgl_periksa"),
                        rs.getString("jam"),
                        rs.getString("nm_perawatan"),
                        rs.getString("sampel_code"),
                        rs.getString("sampel_code"),
                        rs.getString("code"),
                        rs.getString("klinis"),
                        "kesan " + rs.getString("kesan"),
                        rs.getString("nm_dokter"),});
                }
            } catch (Exception e) {
                System.out.println("Notif ps : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (psradpasien != null) {
                    psradpasien.close();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void tampilResume(String norawat) {
        if (lblStatusRawat != null && lblStatusRawat.getText().equals("Ralan")) {
            try {
                Valid.tabelKosong(TabModeResume);
                String kodedpjprajal = Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat='" + norawat + "'");
                psrsmpasien = koneksi.prepareStatement(""
                        + "SELECT "
                        + "    rp.no_rawat, "
                        + "    rp.tgl_registrasi, "
                        + "    rp.jam_reg, "
                        + "    res.keluhan_utama, "
                        + "    res.diagnosa_utama, "
                        + "    res.jalannya_penyakit AS kd_diagnosa_utama, "
                        + "    res.obat_pulang, "
                        + "    res.kondisi_pulang, "
                        + "    res.kd_dokter, "
                        + "    p.nama, "
                        + "    p.no_ktp, "
                        + "    p.tgl_lahir, "
                        + "    p.alamat, "
                        + "    'resume' AS source_type "
                        + "FROM "
                        + "    reg_periksa rp "
                        + "    INNER JOIN resume_pasien res ON rp.no_rawat = res.no_rawat "
                        + "    INNER JOIN pegawai p ON res.kd_dokter = p.nik "
                        + "WHERE "
                        + "    rp.no_rawat = '" + norawat + "' "
                        + "UNION ALL "
                        + "SELECT "
                        + "    rp.no_rawat, "
                        + "    rp.tgl_registrasi, "
                        + "    rp.jam_reg, "
                        + "    concat('Keluhan : ',pr.keluhan,' ',pr.pemeriksaan) AS keluhan_utama, "
                        + "    pr.penilaian AS diagnosa_utama, "
                        + "    CONCAT('Pemeriksaan: ', COALESCE(pr.pemeriksaan, ''),'Suhu: ', COALESCE(pr.suhu_tubuh, ''), ', Tensi: ', COALESCE(pr.tensi, ''), ', Nadi: ', COALESCE(pr.nadi, ''), ', Respirasi: ', COALESCE(pr.respirasi, ''), ', Tinggi: ', COALESCE(pr.tinggi, ''), ', Berat: ', COALESCE(pr.berat, ''), ', GCS: ', COALESCE(pr.gcs, ''), ', Kesadaran: ', COALESCE(pr.kesadaran, '')) AS kd_diagnosa_utama, " // Concatenated vitals
                        + "    pr.rtl AS obat_pulang, "
                        + "    pr.instruksi AS kondisi_pulang, "
                        + "    pr.nip AS kd_dokter, "
                        + "    p.nama, "
                        + "    p.no_ktp, "
                        + "    p.tgl_lahir, "
                        + "    p.alamat, "
                        + "    'soap' AS source_type "
                        + "FROM "
                        + "    reg_periksa rp "
                        + "    INNER JOIN pemeriksaan_ralan pr ON rp.no_rawat = pr.no_rawat "
                        + "    INNER JOIN pegawai p ON pr.nip = p.nik "
                        + "WHERE "
                        + "    rp.no_rawat = '" + norawat + "' "
                        + "    AND pr.nip = '" + kodedpjprajal + "' "
                        + "    AND NOT EXISTS ( "
                        + "        SELECT 1 FROM resume_pasien res  "
                        + "        WHERE res.no_rawat = rp.no_rawat "
                        + "    )");

                try {
                    rs = psrsmpasien.executeQuery();
                    while (rs.next()) {
                        TabModeResume.addRow(new Object[]{
                            rs.getString("tgl_registrasi"),
                            rs.getString("jam_reg"),
                            rs.getString("keluhan_utama"),
                            rs.getString("kd_diagnosa_utama"),
                            rs.getString("diagnosa_utama"),
                            rs.getString("obat_pulang"),
                            rs.getString("kondisi_pulang"),
                            rs.getString("kd_dokter"),
                            rs.getString("nama"),
                            rs.getString("no_ktp"),
                            rs.getString("tgl_lahir"),
                            rs.getString("alamat")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psrsmpasien != null) {
                        psrsmpasien.close();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            try {
                Valid.tabelKosong(TabModeResume);
                String kodedpjpranap = Sequel.cariIsi("select dpjp_ranap.kd_dokter from dpjp_ranap where dpjp_ranap.no_rawat='" + TNoRw.getText() + "'");

                psrsmpasienRanap = koneksi.prepareStatement(""
                        + "SELECT "
                        + "    rp.no_rawat, "
                        + "    rp.tgl_registrasi, "
                        + "    rp.jam_reg, "
                        + "    res.keluhan_utama, "
                        + "    res.diagnosa_utama, "
                        + "    res.pemeriksaan_fisik AS kd_diagnosa_utama, " // Changed to pemeriksaan_fisik
                        + "    res.obat_pulang, "
                        + "    CONCAT(res.dilanjutkan, ' ', res.kontrol) AS dilanjutkan_kontrol, "
                        + "    res.kd_dokter, "
                        + "    p.nama, "
                        + "    p.no_ktp, "
                        + "    p.tgl_lahir, "
                        + "    p.alamat, "
                        + "    'resume' AS source_type "
                        + "FROM "
                        + "    reg_periksa rp "
                        + "    INNER JOIN resume_pasien_ranap res ON rp.no_rawat = res.no_rawat "
                        + "    INNER JOIN pegawai p ON res.kd_dokter = p.nik "
                        + "WHERE "
                        + "    rp.no_rawat = '" + norawat + "' "
                        + "UNION ALL "
                        + "SELECT "
                        + "    rp.no_rawat, "
                        + "    rp.tgl_registrasi, "
                        + "    rp.jam_reg, "
                        + "    concat('Keluhan : ',pr.keluhan, ' ',pr.pemeriksaan) AS keluhan_utama, "
                        + "    pr.penilaian AS diagnosa_utama, "
                        + "    CONCAT('Pemeriksaan: ', COALESCE(pr.pemeriksaan, ''), ' | Suhu: ', COALESCE(pr.suhu_tubuh, ''), ', Tensi: ', COALESCE(pr.tensi, ''), ', Nadi: ', COALESCE(pr.nadi, ''), ', Respirasi: ', COALESCE(pr.respirasi, ''), ', Tinggi: ', COALESCE(pr.tinggi, ''), ', Berat: ', COALESCE(pr.berat, ''), ', GCS: ', COALESCE(pr.gcs, ''), ', Kesadaran: ', COALESCE(pr.kesadaran, '')) AS kd_diagnosa_utama, " // Concatenated pemeriksaan + vitals
                        + "    pr.rtl AS obat_pulang, "
                        + "    pr.instruksi AS dilanjutkan_kontrol, "
                        + "    pr.nip AS kd_dokter, "
                        + "    p.nama, "
                        + "    p.no_ktp, "
                        + "    p.tgl_lahir, "
                        + "    p.alamat, "
                        + "    'soap' AS source_type "
                        + "FROM "
                        + "    reg_periksa rp "
                        + "    INNER JOIN ("
                        + "        SELECT * FROM pemeriksaan_ranap "
                        + "        WHERE no_rawat = '" + norawat + "' "
                        + "        AND nip = '" + kodedpjpranap + "' "
                        + "        ORDER BY tgl_perawatan DESC, jam_rawat DESC "
                        + "        LIMIT 1"
                        + "    ) pr ON rp.no_rawat = pr.no_rawat "
                        + "    INNER JOIN pegawai p ON pr.nip = p.nik "
                        + "WHERE "
                        + "    rp.no_rawat = '" + norawat + "' "
                        + "    AND NOT EXISTS ( "
                        + "        SELECT 1 FROM resume_pasien_ranap res "
                        + "        WHERE res.no_rawat = rp.no_rawat "
                        + "    )");

                try {
                    rs = psrsmpasienRanap.executeQuery();
                    while (rs.next()) {
                        TabModeResume.addRow(new Object[]{
                            rs.getString("tgl_registrasi"),
                            rs.getString("jam_reg"),
                            rs.getString("keluhan_utama"),
                            rs.getString("kd_diagnosa_utama"),
                            rs.getString("diagnosa_utama"),
                            rs.getString("obat_pulang"),
                            rs.getString("dilanjutkan_kontrol"),
                            rs.getString("kd_dokter"),
                            rs.getString("nama"),
                            rs.getString("no_ktp"),
                            rs.getString("tgl_lahir"),
                            rs.getString("alamat")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psrsmpasienRanap != null) {
                        psrsmpasienRanap.close();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private void tampilLapOP(String norawat) {
        try {
            Valid.tabelKosong(TabModeLapOP);
            pslapop = koneksi.prepareStatement(
                    "SELECT\n"
                    + "                    	reg_periksa.tgl_registrasi,\n"
                    + "                    	reg_periksa.jam_reg,\n"
                    + "                    	laporan_operasi.no_rawat,\n"
                    + "                    	laporan_operasi.tanggal,\n"
                    + "                    	laporan_operasi.diagnosa_preop,\n"
                    + "                    	laporan_operasi.diagnosa_postop,\n"
                    + "                    	laporan_operasi.selesaioperasi,\n"
                    + "                    	laporan_operasi.laporan_operasi,\n"
                    + "                    	operasi.operator1 as kd_dokter,\n"
                    + "                    	dokter.nm_dokter \n"
                    + "                    FROM\n"
                    + "                    	reg_periksa\n"
                    + "                    	INNER JOIN laporan_operasi ON reg_periksa.no_rawat = laporan_operasi.no_rawat\n"
                    + "                    	\n"
                    + "											INNER JOIN operasi ON laporan_operasi.no_rawat=operasi.no_rawat\n"
                    + "											INNER JOIN dokter ON operasi.operator1 = dokter.kd_dokter \n"
                    + "                    WHERE\n"
                    + "                    	laporan_operasi.no_rawat =  '" + norawat + "' ORDER BY reg_periksa.tgl_registrasi ASC,  reg_periksa.jam_reg ASC, laporan_operasi.tanggal ASC");

            try {

                rs = pslapop.executeQuery();
                while (rs.next()) {
                    TabModeLapOP.addRow(new String[]{
                        rs.getString("tanggal"),
                        rs.getString("diagnosa_preop"),
                        rs.getString("diagnosa_postop"),
                        rs.getString("selesaioperasi"),
                        rs.getString("laporan_operasi"),
                        rs.getString("kd_dokter"),
                        rs.getString("nm_dokter")});
                }
            } catch (Exception e) {
                System.out.println("Notif ps : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pslapop != null) {
                    pslapop.close();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void tampilResep(String norawat) {
        try {
            Valid.tabelKosong(TabModeResep);
            psresep = koneksi.prepareStatement(
                    "SELECT\n"
                    + "	reg_periksa.tgl_registrasi,\n"
                    + "	reg_periksa.jam_reg,\n"
                    + "	reg_periksa.no_rawat,\n"
                    + "	reg_periksa.no_rkm_medis,\n"
                    + "	pasien.nm_pasien,\n"
                    + "	pasien.no_ktp,\n"
                    + "	pegawai.nama,\n"
                    + "	pegawai.no_ktp AS ktppraktisi,\n"
                    + "	ssmo1.obat_code AS kfa_code,\n"
                    + "	resep_dokter.kode_brng,\n"
                    + "	ssmo1.obat_display AS kfa_name,\n"
                    + "	ssmo1.form_code AS formcode,\n"
                    + "	ssmo1.form_display AS formdisplay,\n"
                    + "	ssmo1.route_code AS routecode,\n"
                    + "	'http://www.whocc.no/atc' AS keterangan,\n"
                    + "	ssmo1.route_display AS routedisplay,\n"
                    + "	resep_obat.tgl_peresepan,\n"
                    + "	resep_obat.jam_peresepan,\n"
                    + "	resep_dokter.jml,\n"
                    + "	satu_sehat_medication.id_medication,\n"
                    + "	resep_dokter.aturan_pakai,\n"
                    + "	resep_dokter.no_resep\n"
                    + "FROM\n"
                    + "	reg_periksa\n"
                    + "	INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis\n"
                    + "	INNER JOIN resep_obat ON reg_periksa.no_rawat = resep_obat.no_rawat\n"
                    + "	INNER JOIN pegawai ON resep_obat.kd_dokter = pegawai.nik\n"
                    + "	INNER JOIN resep_dokter ON resep_dokter.no_resep = resep_obat.no_resep\n"
                    + "	INNER JOIN satu_sehat_mapping_obat AS ssmo1 ON ssmo1.kode_brng = resep_dokter.kode_brng\n"
                    + "	INNER JOIN databarang ON ssmo1.kode_brng = databarang.kode_brng\n"
                    + "	INNER JOIN satu_sehat_medication ON satu_sehat_medication.kode_brng = ssmo1.kode_brng\n"
                    + "	INNER JOIN kodesatuan ON databarang.kode_sat = kodesatuan.kode_sat \n"
                    + "WHERE\n"
                    + "	resep_obat.no_rawat = '" + norawat + "'");

            try {

                rs = psresep.executeQuery();
                while (rs.next()) {
                    TabModeResep.addRow(new String[]{
                        rs.getString("tgl_registrasi") + " " + rs.getString("jam_reg"),
                        rs.getString("nama"),
                        rs.getString("no_rawat"),
                        rs.getString("kfa_code"),
                        "http://sys-ids.kemkes.go.id/kfa",
                        rs.getString("kode_brng"),
                        rs.getString("kfa_name"),
                        rs.getString("formcode"),
                        "http://terminology.kemkes.go.id/CodeSystem/medication-form",
                        rs.getString("formdisplay"),
                        rs.getString("routecode"),
                        "http://www.whocc.no/atc",
                        rs.getString("routedisplay"),
                        "",
                        "http://terminology.hl7.org/CodeSystem/v3-orderableDrugForm",
                        rs.getString("tgl_peresepan") + " " + rs.getString("jam_peresepan"),
                        rs.getString("jml"),
                        rs.getString("id_medication"),
                        rs.getString("aturan_pakai"),
                        rs.getString("no_resep"),
                        "", // Column 20 - ID Medication Request
                        "", // Column 21 - No Racik
                        "Ralan"}); // Column 22 - Status
                }
            } catch (Exception e) {
                System.out.println("Notif ps : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (psresep != null) {
                    psresep.close();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void tampilTebusObat(String norawat) {
        try {

            Valid.tabelKosong(TabModeTebusObat);
            pstebus = koneksi.prepareStatement(
                    "SELECT\n"
                    + "	reg_periksa.tgl_registrasi,\n"
                    + "	reg_periksa.jam_reg,\n"
                    + "	reg_periksa.no_rawat,\n"
                    + "	reg_periksa.no_rkm_medis,\n"
                    + "	pasien.nm_pasien,\n"
                    + "	pasien.no_ktp,\n"
                    + "	pegawai.nama,\n"
                    + "	pegawai.no_ktp AS ktppraktisi,\n"
                    + "	satu_sehat_encounter.id_encounter,\n"
                    + "	satu_sehat_mapping_obat.obat_code AS kfa_code,\n"
                    + "	detail_pemberian_obat.kode_brng,\n"
                    + "	satu_sehat_mapping_obat.obat_display AS kfa_name,\n"
                    + "	satu_sehat_mapping_obat.form_code AS formcode,\n"
                    + "	satu_sehat_mapping_obat.form_display AS formdisplay,\n"
                    + "	satu_sehat_mapping_obat.route_code AS routecode,\n"
                    + "	'http://www.whocc.no/atc' AS keterangan,\n"
                    + "	satu_sehat_mapping_obat.route_display AS routedisplay,\n"
                    + "	resep_obat.tgl_peresepan,\n"
                    + "	resep_obat.jam_peresepan,\n"
                    + "	detail_pemberian_obat.jml,\n"
                    + "	satu_sehat_medication.id_medication,\n"
                    + "	aturan_pakai.aturan,\n"
                    + "	resep_obat.no_resep,\n"
                    + "	IFNULL(satu_sehat_medicationdispense.id_medicationdispanse, '') AS id_medicationdispanse,\n"
                    + "	detail_pemberian_obat.no_batch,\n"
                    + "	detail_pemberian_obat.no_faktur,\n"
                    + "	detail_pemberian_obat.tgl_perawatan,\n"
                    + "	detail_pemberian_obat.jam,\n"
                    + "	satu_sehat_mapping_lokasi_depo_farmasi.id_lokasi_satusehat,\n"
                    + "	bangsal.nm_bangsal,\n"
                    + "	IFNULL(satu_sehat_medicationrequest.id_medicationrequest, '') AS id_medicationrequest\n"
                    + "FROM\n"
                    + "	reg_periksa\n"
                    + "	INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis\n"
                    + "	INNER JOIN resep_obat ON reg_periksa.no_rawat = resep_obat.no_rawat\n"
                    + "	INNER JOIN pegawai ON resep_obat.kd_dokter = pegawai.nik\n"
                    + "	LEFT JOIN satu_sehat_encounter ON satu_sehat_encounter.no_rawat = reg_periksa.no_rawat\n"
                    + "	INNER JOIN detail_pemberian_obat ON detail_pemberian_obat.no_rawat = resep_obat.no_rawat\n"
                    + "		AND detail_pemberian_obat.tgl_perawatan = resep_obat.tgl_perawatan\n"
                    + "		AND detail_pemberian_obat.jam = resep_obat.jam\n"
                    + "	INNER JOIN aturan_pakai ON detail_pemberian_obat.no_rawat = aturan_pakai.no_rawat\n"
                    + "		AND detail_pemberian_obat.tgl_perawatan = aturan_pakai.tgl_perawatan\n"
                    + "		AND detail_pemberian_obat.jam = aturan_pakai.jam\n"
                    + "		AND detail_pemberian_obat.kode_brng = aturan_pakai.kode_brng\n"
                    + "	INNER JOIN satu_sehat_mapping_obat ON satu_sehat_mapping_obat.kode_brng = detail_pemberian_obat.kode_brng\n"
                    + "	INNER JOIN bangsal ON bangsal.kd_bangsal = detail_pemberian_obat.kd_bangsal\n"
                    + "	INNER JOIN satu_sehat_mapping_lokasi_depo_farmasi ON satu_sehat_mapping_lokasi_depo_farmasi.kd_bangsal = bangsal.kd_bangsal\n"
                    + "	INNER JOIN satu_sehat_medication ON satu_sehat_medication.kode_brng = satu_sehat_mapping_obat.kode_brng\n"
                    + "	INNER JOIN databarang ON satu_sehat_mapping_obat.kode_brng = databarang.kode_brng\n"
                    + "	INNER JOIN nota_jalan ON nota_jalan.no_rawat = reg_periksa.no_rawat\n"
                    + "	INNER JOIN kodesatuan ON databarang.kode_sat = kodesatuan.kode_sat\n"
                    + "	LEFT JOIN satu_sehat_medicationdispense ON satu_sehat_medicationdispense.no_rawat = detail_pemberian_obat.no_rawat\n"
                    + "		AND satu_sehat_medicationdispense.tgl_perawatan = detail_pemberian_obat.tgl_perawatan\n"
                    + "		AND satu_sehat_medicationdispense.jam = detail_pemberian_obat.jam\n"
                    + "		AND satu_sehat_medicationdispense.kode_brng = detail_pemberian_obat.kode_brng\n"
                    + "		AND satu_sehat_medicationdispense.no_batch = detail_pemberian_obat.no_batch\n"
                    + "		AND satu_sehat_medicationdispense.no_faktur = detail_pemberian_obat.no_faktur\n"
                    + "	LEFT JOIN satu_sehat_medicationrequest ON resep_obat.no_resep = satu_sehat_medicationrequest.no_resep\n"
                    + "		AND detail_pemberian_obat.kode_brng = satu_sehat_medicationrequest.kode_brng\n"
                    + "WHERE\n"
                    + "	detail_pemberian_obat.no_rawat = '" + norawat + "'");

            try {

                rs = pstebus.executeQuery();
                while (rs.next()) {
                    TabModeTebusObat.addRow(new String[]{
                        rs.getString("tgl_registrasi") + " " + rs.getString("jam_reg"), rs.getString("nama"), rs.getString("id_encounter"), rs.getString("kfa_code"), "http://sys-ids.kemkes.go.id/kfa", rs.getString("kode_brng"),
                        rs.getString("kfa_name"), rs.getString("formcode"), "http://terminology.kemkes.go.id/CodeSystem/medication-form", rs.getString("formdisplay"), rs.getString("routecode"), "http://www.whocc.no/atc",
                        rs.getString("routedisplay"), "", "http://terminology.hl7.org/CodeSystem/v3-orderableDrugForm", rs.getString("tgl_peresepan") + " " + rs.getString("jam_peresepan"),
                        rs.getString("jml"), rs.getString("id_medication"), rs.getString("aturan"), rs.getString("no_resep"), rs.getString("id_medicationdispanse"), rs.getString("no_batch"),
                        rs.getString("no_faktur"), rs.getString("tgl_perawatan") + " " + rs.getString("jam"), "Ralan", rs.getString("id_lokasi_satusehat"), rs.getString("nm_bangsal"), rs.getString("id_medicationrequest")});
                }
            } catch (Exception e) {
                System.out.println("Notif ps : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pstebus != null) {
                    pstebus.close();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void tampilAlergi(String norawat) {
        try {
            Valid.tabelKosong(TabModeAlergi);
            psalergi = koneksi.prepareStatement(
                    "SELECT\n"
                    + "	reg_periksa.no_rawat, \n"
                    + "	pasien.nm_pasien, \n"
                    + "	pasien.no_ktp, \n"
                    + "	pegawai.nama, \n"
                    + "	pegawai.no_ktp AS ktpdokter, \n"
                    + "	poliklinik.nm_poli, \n"
                    + "	satu_sehat_mapping_lokasi_ralan.id_lokasi_satusehat, \n"
                    + "	reg_periksa.stts, \n"
                    + "	reg_periksa.status_lanjut, \n"
                    + "	reg_periksa.kd_dokter,"
                    + " reg_periksa.kd_poli, \n"
                    + "	reg_periksa.no_rkm_medis, \n"
                    + "	satu_sehat_encounter.id_encounter, \n"
                    + "	alergi_pasien.allergy_code, \n"
                    + "	satu_sehat_ref_allergy.system, \n"
                    + "	satu_sehat_ref_allergy.display, \n"
                    + "	alergi_pasien.note, \n"
                    + "	DATE_FORMAT(alergi_pasien.tgl_perawatan, '%Y-%m-%dT%H:%i:%s+00:00') AS tgl_perawatan, \n"
                    + "	alergi_pasien.nippetugas, \n"
                    + "	petugas.nama AS petugasallergy, \n"
                    + "	alergi_pasien.category,"
                    + " satu_sehat_allergy.id_allergy \n"
                    + "FROM\n"
                    + "	reg_periksa INNER JOIN pasien\n"
                    + "	ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis\n"
                    + "	INNER JOIN pegawai ON pegawai.nik = reg_periksa.kd_dokter\n"
                    + "	INNER JOIN poliklinik ON reg_periksa.kd_poli = poliklinik.kd_poli\n"
                    + "	INNER JOIN satu_sehat_mapping_lokasi_ralan ON satu_sehat_mapping_lokasi_ralan.kd_poli = poliklinik.kd_poli\n"
                    + "	INNER JOIN satu_sehat_encounter ON satu_sehat_encounter.no_rawat = reg_periksa.no_rawat\n"
                    + "	INNER JOIN alergi_pasien ON  reg_periksa.no_rawat = alergi_pasien.no_rawat\n"
                    + "	INNER JOIN satu_sehat_ref_allergy ON alergi_pasien.allergy_code = satu_sehat_ref_allergy.kode\n"
                    + "	INNER JOIN satu_sehat_ref_allergy_reaction ON alergi_pasien.reactioncode = satu_sehat_ref_allergy_reaction.kode\n"
                    + "	LEFT JOIN satu_sehat_allergy ON  reg_periksa.no_rawat = satu_sehat_allergy.no_rawat\n"
                    + "	INNER JOIN pegawai AS petugas ON petugas.nik = alergi_pasien.nippetugas \n"
                    + "WHERE alergi_pasien.no_rawat ='" + norawat + "'");

            try {

                rs = psalergi.executeQuery();
                while (rs.next()) {
                    TabModeAlergi.addRow(new String[]{
                        rs.getString("nama"), rs.getString("ktpdokter"), rs.getString("kd_poli"), rs.getString("nm_poli"),
                        rs.getString("id_lokasi_satusehat"), rs.getString("stts"), rs.getString("status_lanjut"), rs.getString("tgl_perawatan"),
                        rs.getString("id_encounter"), rs.getString("category"), rs.getString("allergy_code"), rs.getString("system"),
                        rs.getString("display"), rs.getString("note"), rs.getString("id_allergy")});
                }
            } catch (Exception e) {
                System.out.println("Notif ps : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (psalergi != null) {
                    psalergi.close();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void tampilLamaPelayanan(String norawat) {
        if (lblStatusRawat != null && !lblStatusRawat.getText().equals("Ranap")) {
            try {
                Valid.tabelKosong(TabModeLP);

                psLP = koneksi.prepareStatement("select reg_periksa.no_rkm_medis,pasien.nm_pasien,dokter.nm_dokter,poliklinik.nm_poli,"
                        + "reg_periksa.tgl_registrasi,reg_periksa.jam_reg,nota_jalan.tanggal,nota_jalan.jam,"
                        + "round((TIME_TO_SEC(concat(nota_jalan.tanggal,' ',nota_jalan.jam))-TIME_TO_SEC(concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg)))/60,2) as durasi "
                        + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join nota_jalan "
                        + "on reg_periksa.kd_dokter=dokter.kd_dokter "
                        + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "and reg_periksa.kd_poli=poliklinik.kd_poli "
                        + "and reg_periksa.no_rawat=nota_jalan.no_rawat "
                        + "WHERE reg_periksa.no_rawat = '" + norawat + "'");

                try {

                    rs = psLP.executeQuery();
                    while (rs.next()) {
                        TabModeLP.addRow(new String[]{
                            rs.getString("no_rkm_medis"),
                            rs.getString("tgl_registrasi"),
                            rs.getString("jam_reg"),
                            rs.getString("tanggal"),
                            rs.getString("jam")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psLP != null) {
                        psLP.close();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            try {
                Valid.tabelKosong(TabModeLP);
                psLPRanap = koneksi.prepareStatement("SELECT kamar_inap.no_rawat, reg_periksa.no_rkm_medis, reg_periksa.tgl_registrasi, "
                        + "reg_periksa.jam_reg, pasien.nm_pasien, "
                        + "CONCAT(reg_periksa.tgl_registrasi, ' ', reg_periksa.jam_reg) AS waktureg, "
                        + "CONCAT(pasien.alamat, ', ', kelurahan.nm_kel, ', ', kecamatan.nm_kec, ', ', kabupaten.nm_kab) AS alamat, "
                        + "reg_periksa.p_jawab, reg_periksa.hubunganpj, penjab.png_jawab, "
                        + "CONCAT(kamar_inap.kd_kamar, ' ', bangsal.nm_bangsal, ' - |', kamar.kelas, '|') AS kamar, "
                        + "kamar_inap.trf_kamar, kamar_inap.diagnosa_awal, "
                        + "CONCAT(kamar_inap.tgl_masuk, ' ', kamar_inap.jam_masuk) AS waktumasuk, "
                        + "IF(kamar_inap.tgl_keluar = '0000-00-00', '', kamar_inap.tgl_keluar) AS tgl_keluar, "
                        + "IF(kamar_inap.jam_keluar = '00:00:00', '', kamar_inap.jam_keluar) AS tgl_keluar, "
                        + "CONCAT(tgl_keluar, ' ', jam_keluar) AS waktukeluar, "
                        + "kamar_inap.kd_kamar, bangsal.nm_bangsal, reg_periksa.kd_pj, "
                        + "CONCAT(reg_periksa.umurdaftar, ' ', reg_periksa.sttsumur) AS umur, "
                        + "reg_periksa.status_bayar, pasien.agama, "
                        + "CONCAT(nota_inap.tanggal, ' ', nota_inap.jam) AS jambilling "
                        + "FROM kamar_inap "
                        + "INNER JOIN reg_periksa ON kamar_inap.no_rawat = reg_periksa.no_rawat "
                        + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                        + "INNER JOIN kamar ON kamar_inap.kd_kamar = kamar.kd_kamar "
                        + "INNER JOIN bangsal ON kamar.kd_bangsal = bangsal.kd_bangsal "
                        + "INNER JOIN kelurahan ON pasien.kd_kel = kelurahan.kd_kel "
                        + "INNER JOIN kecamatan ON pasien.kd_kec = kecamatan.kd_kec "
                        + "INNER JOIN kabupaten ON pasien.kd_kab = kabupaten.kd_kab "
                        + "INNER JOIN dokter ON reg_periksa.kd_dokter = dokter.kd_dokter "
                        + "INNER JOIN penjab ON reg_periksa.kd_pj = penjab.kd_pj "
                        + "INNER JOIN nota_inap ON kamar_inap.no_rawat = nota_inap.no_rawat "
                        + "WHERE kamar_inap.no_rawat = '" + norawat + "'");

                try {

                    rs = psLPRanap.executeQuery();
                    while (rs.next()) {
                        TabModeLP.addRow(new Object[]{
                            rs.getString("no_rkm_medis"),
                            rs.getString("tgl_registrasi"),
                            rs.getString("jam_reg"),
                            rs.getString("tgl_keluar"),
                            rs.getString("waktukeluar")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psLPRanap != null) {
                        psLPRanap.close();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

        }
    }

    private void tampilAlkes(String norawat) {
        try {

            Valid.tabelKosong(TabModeAlkes);
            psalkes = koneksi.prepareStatement(
                    "SELECT\n"
                    + "    reg_periksa.tgl_registrasi,\n"
                    + "    reg_periksa.jam_reg,\n"
                    + "    reg_periksa.no_rawat,\n"
                    + "    reg_periksa.no_rkm_medis,\n"
                    + "    pasien.nm_pasien,\n"
                    + "    pasien.no_ktp,\n"
                    + "    satu_sehat_mapping_obat.obat_code AS kfa_code,\n"
                    + "    detail_pemberian_obat.kode_brng,\n"
                    + "    databarang.nama_brng,\n"
                    + "    satu_sehat_mapping_obat.obat_display AS kfa_name,\n"
                    + "    satu_sehat_mapping_obat.form_code AS formcode,\n"
                    + "    satu_sehat_mapping_obat.form_display AS formdisplay,\n"
                    + "    satu_sehat_mapping_obat.route_code AS routecode,\n"
                    + "    satu_sehat_mapping_obat.route_display AS routedisplay,\n"
                    + "    detail_pemberian_obat.jml,\n"
                    + "    satu_sehat_medication.id_medication,\n"
                    + "    detail_pemberian_obat.no_batch,\n"
                    + "    detail_pemberian_obat.no_faktur,\n"
                    + "    detail_pemberian_obat.tgl_perawatan,\n"
                    + "    detail_pemberian_obat.jam,\n"
                    + "    bangsal.nm_bangsal,\n"
                    + "    databarang.nama_brng AS nama_barang,\n"
                    + "    databarang.kode_industri AS manufacturer,\n"
                    + "    databarang.kode_sat AS model,\n"
                    + "    detail_pemberian_obat.tgl_perawatan AS manufacture_date,\n"
                    + "    DATE_ADD(detail_pemberian_obat.tgl_perawatan, INTERVAL 365 DAY) AS expiration_date\n"
                    + "FROM\n"
                    + "    reg_periksa\n"
                    + "    INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis\n"
                    + "    INNER JOIN detail_pemberian_obat ON reg_periksa.no_rawat = detail_pemberian_obat.no_rawat\n"
                    + "    INNER JOIN databarang ON detail_pemberian_obat.kode_brng = databarang.kode_brng\n"
                    + "    INNER JOIN jenis ON databarang.kdjns = jenis.kdjns\n"
                    + "    LEFT JOIN satu_sehat_mapping_obat ON satu_sehat_mapping_obat.kode_brng = detail_pemberian_obat.kode_brng\n"
                    + "    LEFT JOIN bangsal ON bangsal.kd_bangsal = detail_pemberian_obat.kd_bangsal\n"
                    + "    LEFT JOIN satu_sehat_mapping_lokasi_depo_farmasi ON satu_sehat_mapping_lokasi_depo_farmasi.kd_bangsal = bangsal.kd_bangsal\n"
                    + "    LEFT JOIN satu_sehat_medication ON satu_sehat_medication.kode_brng = satu_sehat_mapping_obat.kode_brng\n"
                    + "    LEFT JOIN nota_jalan ON nota_jalan.no_rawat = reg_periksa.no_rawat\n"
                    + "    LEFT JOIN kodesatuan ON databarang.kode_sat = kodesatuan.kode_sat\n"
                    + "WHERE\n"
                    + "    detail_pemberian_obat.no_rawat = '" + norawat + "' \n"
                    + "    AND (jenis.kdjns = 'J010' OR jenis.kdjns = 'J011' OR jenis.kdjns = 'J013')");

            try {

                rs = psalkes.executeQuery();
                while (rs.next()) {
                    TabModeAlkes.addRow(new String[]{
                        rs.getString("tgl_registrasi") + " " + rs.getString("jam_reg"),
                        rs.getString("nm_pasien"),
                        rs.getString("kfa_code"),
                        "http://sys-ids.kemkes.go.id/kfa",
                        rs.getString("kode_brng"),
                        rs.getString("nama_brng"),
                        rs.getString("jml"),
                        rs.getString("tgl_perawatan") + " " + rs.getString("jam"),
                        rs.getString("nm_bangsal"),
                        rs.getString("kfa_name"),
                        rs.getString("formcode"),
                        "http://terminology.kemkes.go.id/CodeSystem/medication-form",
                        rs.getString("formdisplay"),
                        rs.getString("routecode"),
                        "http://www.whocc.no/atc",
                        rs.getString("routedisplay"),
                        "http://terminology.hl7.org/CodeSystem/v3-orderableDrugForm",
                        rs.getString("id_medication"),
                        rs.getString("no_batch"),
                        rs.getString("no_faktur"),
                        Sequel.cariIsi("select nama_industri from industrifarmasi where kode_industri='" + rs.getString("manufacturer") + "'"),
                        rs.getString("model"),
                        rs.getString("no_batch"), // Use batch number as lot number
                        rs.getString("manufacture_date"),
                        rs.getString("expiration_date")});
                }
            } catch (Exception e) {
                System.out.println("Notif ps : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (psalkes != null) {
                    psalkes.close();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void tampilpract(String norawat) {
        if (lblStatusRawat != null && lblStatusRawat.getText().equals("Ralan")) {
            try {
                Valid.tabelKosong(TabModePract);
                psPract = koneksi.prepareStatement("SELECT\n"
                        + "	reg_periksa.no_rawat,\n"
                        + "	reg_periksa.kd_dokter,\n"
                        + "	dokter.nm_dokter,\n"
                        + "	dokter.jk,\n"
                        + "	dokter.tgl_lahir,\n"
                        + "	pegawai.alamat,\n"
                        + "	pegawai.no_ktp,\n"
                        + "	dokter.tmp_lahir,\n"
                        + "	dokter.gol_drh,\n"
                        + "	dokter.agama,\n"
                        + "	dokter.stts_nikah,\n"
                        + "	dokter.kd_sps,\n"
                        + "	dokter.alumni,\n"
                        + "	dokter.no_ijn_praktek,\n"
                        + "	dokter.status,\n"
                        + "	'email@email.com' as email,\n"
                        + "	dokter.no_telp\n"
                        + "FROM\n"
                        + "	reg_periksa\n"
                        + "	INNER JOIN dokter ON reg_periksa.kd_dokter = dokter.kd_dokter\n"
                        + "	INNER JOIN pegawai ON dokter.kd_dokter = pegawai.nik \n"
                        + "WHERE\n"
                        + "	reg_periksa.no_rawat = '" + norawat + "'");

                try {

                    rs = psPract.executeQuery();
                    while (rs.next()) {
                        TabModePract.addRow(new Object[]{
                            rs.getString("no_rawat"),
                            rs.getString("kd_dokter"),
                            rs.getString("nm_dokter"),
                            rs.getString("no_ktp"),
                            rs.getString("jk"),
                            rs.getString("tgl_lahir"),
                            rs.getString("alamat"),
                            rs.getString("tmp_lahir"),
                            rs.getString("gol_drh"),
                            rs.getString("agama"),
                            rs.getString("stts_nikah"),
                            rs.getString("kd_sps"),
                            rs.getString("alumni"),
                            rs.getString("no_ijn_praktek"),
                            rs.getString("status"),
                            rs.getString("email"),
                            rs.getString("no_telp")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psPract != null) {
                        psPract.close();
                    }
                }

                psPract = koneksi.prepareStatement("SELECT\n"
                        + "	periksa_lab.no_rawat,\n"
                        + "	periksa_lab.kd_jenis_prw,\n"
                        + "	detail_periksa_lab.tgl_periksa,\n"
                        + "	detail_periksa_lab.jam,\n"
                        + "	detail_periksa_lab.id_template,\n"
                        + "	detail_periksa_lab.nilai,\n"
                        + "	detail_periksa_lab.nilai_rujukan,\n"
                        + "	detail_periksa_lab.keterangan,\n"
                        + "	periksa_lab.`status`,\n"
                        + "	periksa_lab.kd_dokter,\n"
                        + "	template_laboratorium.Pemeriksaan,\n"
                        + "	jns_perawatan_lab.nm_perawatan,\n"
                        + "	satu_sehat_mapping_lab.sampel_code AS snomed,\n"
                        + "	satu_sehat_mapping_lab.code AS loinc,\n"
                        + "	satu_sehat_mapping_lab.sampel_code AS subsnomedcode,\n"
                        + "	satu_sehat_mapping_lab.code AS subloinccode,\n"
                        + "	dokter.nm_dokter,\n"
                        + "	dokter.jk,\n"
                        + "	dokter.tgl_lahir,\n"
                        + "	pegawai.alamat,\n"
                        + "	pegawai.no_ktp,\n"
                        + "	dokter.tmp_lahir,\n"
                        + "	dokter.gol_drh,\n"
                        + "	dokter.agama,\n"
                        + "	dokter.stts_nikah,\n"
                        + "	dokter.kd_sps,\n"
                        + "	dokter.alumni,\n"
                        + "	dokter.no_ijn_praktek,\n"
                        + "	dokter.STATUS,\n"
                        + "	'email@email.com' as email,\n"
                        + "	dokter.no_telp \n"
                        + "FROM\n"
                        + "	periksa_lab\n"
                        + "	INNER JOIN detail_periksa_lab ON periksa_lab.no_rawat = detail_periksa_lab.no_rawat \n"
                        + "	AND periksa_lab.kd_jenis_prw = detail_periksa_lab.kd_jenis_prw\n"
                        + "	INNER JOIN template_laboratorium ON detail_periksa_lab.id_template = template_laboratorium.id_template\n"
                        + "	INNER JOIN jns_perawatan_lab ON detail_periksa_lab.kd_jenis_prw = jns_perawatan_lab.kd_jenis_prw \n"
                        + "	AND periksa_lab.kd_jenis_prw = jns_perawatan_lab.kd_jenis_prw \n"
                        + "	AND template_laboratorium.kd_jenis_prw = jns_perawatan_lab.kd_jenis_prw\n"
                        + "	INNER JOIN dokter ON periksa_lab.kd_dokter=dokter.kd_dokter\n"
                        + "	INNER JOIN pegawai ON dokter.kd_dokter=pegawai.nik\n"
                        + "	LEFT JOIN satu_sehat_mapping_lab ON detail_periksa_lab.id_template = satu_sehat_mapping_lab.id_template \n"
                        + "WHERE\n"
                        + "	periksa_lab.no_rawat = '" + norawat + "' GROUP BY dokter.kd_dokter ");

                try {

                    rs = psPract.executeQuery();
                    while (rs.next()) {
                        TabModePract.addRow(new Object[]{
                            rs.getString("no_rawat"),
                            rs.getString("kd_dokter"),
                            rs.getString("nm_dokter"),
                            rs.getString("no_ktp"),
                            rs.getString("jk"),
                            rs.getString("tgl_lahir"),
                            rs.getString("alamat"),
                            rs.getString("tmp_lahir"),
                            rs.getString("gol_drh"),
                            rs.getString("agama"),
                            rs.getString("stts_nikah"),
                            rs.getString("kd_sps"),
                            rs.getString("alumni"),
                            rs.getString("no_ijn_praktek"),
                            rs.getString("status"),
                            rs.getString("email"),
                            rs.getString("no_telp")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psPract != null) {
                        psPract.close();
                    }
                }

                psPract = koneksi.prepareStatement("SELECT\n"
                        + "	periksa_radiologi.tgl_periksa,\n"
                        + "	periksa_radiologi.jam,\n"
                        + "	periksa_radiologi.no_rawat,\n"
                        + "	periksa_radiologi.kd_jenis_prw,\n"
                        + "	jns_perawatan_radiologi.nm_perawatan,\n"
                        + "	satu_sehat_mapping_radiologi.sampel_code as snomed,\n"
                        + "	satu_sehat_mapping_radiologi.code as loincode,\n"
                        + "	periksa_radiologi.kd_dokter,\n"
                        + "	dokter.nm_dokter,\n"
                        + "	dokter.jk,\n"
                        + "	dokter.tgl_lahir,\n"
                        + "	pegawai.alamat,\n"
                        + "	pegawai.no_ktp,\n"
                        + "	dokter.tmp_lahir,\n"
                        + "	dokter.gol_drh,\n"
                        + "	dokter.agama,\n"
                        + "	dokter.stts_nikah,\n"
                        + "	dokter.kd_sps,\n"
                        + "	dokter.alumni,\n"
                        + "	dokter.no_ijn_praktek,\n"
                        + "	dokter.STATUS,\n"
                        + "	'email@email.com' as email,\n"
                        + "	dokter.no_telp \n"
                        + "FROM\n"
                        + "	periksa_radiologi\n"
                        + "	INNER JOIN jns_perawatan_radiologi ON periksa_radiologi.kd_jenis_prw = jns_perawatan_radiologi.kd_jenis_prw\n"
                        + "	INNER JOIN dokter ON periksa_radiologi.kd_dokter = dokter.kd_dokter\n"
                        + "	INNER JOIN pegawai ON dokter.kd_dokter=pegawai.nik\n"
                        + "	LEFT JOIN satu_sehat_mapping_radiologi ON jns_perawatan_radiologi.kd_jenis_prw = satu_sehat_mapping_radiologi.kd_jenis_prw\n"
                        + "	INNER JOIN hasil_radiologi ON periksa_radiologi.no_rawat = hasil_radiologi.no_rawat \n"
                        + "	AND periksa_radiologi.tgl_periksa = hasil_radiologi.tgl_periksa \n"
                        + "	AND periksa_radiologi.jam = hasil_radiologi.jam \n"
                        + "       WHERE\n"
                        + "	periksa_radiologi.no_rawat = '" + norawat + "' GROUP BY dokter.kd_dokter ");

                try {
                    rs = psPract.executeQuery();
                    while (rs.next()) {
                        TabModePract.addRow(new Object[]{
                            rs.getString("no_rawat"),
                            rs.getString("kd_dokter"),
                            rs.getString("nm_dokter"),
                            rs.getString("no_ktp"),
                            rs.getString("jk"),
                            rs.getString("tgl_lahir"),
                            rs.getString("alamat"),
                            rs.getString("tmp_lahir"),
                            rs.getString("gol_drh"),
                            rs.getString("agama"),
                            rs.getString("stts_nikah"),
                            rs.getString("kd_sps"),
                            rs.getString("alumni"),
                            rs.getString("no_ijn_praktek"),
                            rs.getString("status"),
                            rs.getString("email"),
                            rs.getString("no_telp")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psPract != null) {
                        psPract.close();
                    }
                }

                psPract = koneksi.prepareStatement("SELECT\n"
                        + "	reg_periksa.tgl_registrasi,\n"
                        + "	reg_periksa.jam_reg,\n"
                        + "	laporan_operasi.no_rawat,\n"
                        + "	laporan_operasi.tanggal,\n"
                        + "	laporan_operasi.diagnosa_preop,\n"
                        + "	laporan_operasi.diagnosa_postop,\n"
                        + "	laporan_operasi.selesaioperasi,\n"
                        + "	laporan_operasi.laporan_operasi,\n"
                        + "	operasi.operator1 as kd_dokter,\n"
                        + "	dokter.nm_dokter,\n"
                        + "	dokter.jk,\n"
                        + "	dokter.tgl_lahir,\n"
                        + "	pegawai.alamat,\n"
                        + "	pegawai.no_ktp,\n"
                        + "	dokter.tmp_lahir,\n"
                        + "	dokter.gol_drh,\n"
                        + "	dokter.agama,\n"
                        + "	dokter.stts_nikah,\n"
                        + "	dokter.kd_sps,\n"
                        + "	dokter.alumni,\n"
                        + "	dokter.no_ijn_praktek,\n"
                        + "	dokter.STATUS,\n"
                        + "	'email@email.com' as email,\n"
                        + "	dokter.no_telp \n"
                        + "FROM\n"
                        + "	reg_periksa\n"
                        + "	INNER JOIN laporan_operasi ON reg_periksa.no_rawat = laporan_operasi.no_rawat"
                        + " INNER JOIN operasi ON laporan_operasi.no_rawat=operasi.no_rawat\n"
                        + "	INNER JOIN dokter ON operasi.operator1 = dokter.kd_dokter \n"
                        + "	INNER JOIN pegawai ON dokter.kd_dokter=pegawai.nik\n"
                        + "WHERE\n"
                        + "	laporan_operasi.no_rawat = '" + norawat + "' GROUP BY dokter.kd_dokter ");

                try {
                    rs = psPract.executeQuery();
                    while (rs.next()) {
                        TabModePract.addRow(new Object[]{
                            rs.getString("no_rawat"),
                            rs.getString("kd_dokter"),
                            rs.getString("nm_dokter"),
                            rs.getString("no_ktp"),
                            rs.getString("jk"),
                            rs.getString("tgl_lahir"),
                            rs.getString("alamat"),
                            rs.getString("tmp_lahir"),
                            rs.getString("gol_drh"),
                            rs.getString("agama"),
                            rs.getString("stts_nikah"),
                            rs.getString("kd_sps"),
                            rs.getString("alumni"),
                            rs.getString("no_ijn_praktek"),
                            rs.getString("status"),
                            rs.getString("email"),
                            rs.getString("no_telp")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psPract != null) {
                        psPract.close();
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

        } else {
            try {
                Valid.tabelKosong(TabModePract);
                psPractRanap = koneksi.prepareStatement("SELECT\n"
                        + "    reg_periksa.no_rawat,\n"
                        + "    pr.nip AS kd_dokter,\n"
                        + "    dokter.nm_dokter,\n"
                        + "    dokter.jk,\n"
                        + "    dokter.tgl_lahir,\n"
                        + "    pegawai.alamat,\n"
                        + "    pegawai.no_ktp,\n"
                        + "    dokter.tmp_lahir,\n"
                        + "    dokter.gol_drh,\n"
                        + "    dokter.agama,\n"
                        + "    dokter.stts_nikah,\n"
                        + "    dokter.kd_sps,\n"
                        + "    dokter.alumni,\n"
                        + "    dokter.no_ijn_praktek,\n"
                        + "    dokter.status,\n"
                        + "    'email@email.com' as email,\n"
                        + "    dokter.no_telp\n"
                        + "FROM\n"
                        + "    reg_periksa\n"
                        + "    INNER JOIN pemeriksaan_ranap pr ON reg_periksa.no_rawat = pr.no_rawat\n"
                        + "    INNER JOIN dokter ON pr.nip = dokter.kd_dokter\n"
                        + "    INNER JOIN pegawai ON dokter.kd_dokter = pegawai.nik \n"
                        + "WHERE\n"
                        + "    reg_periksa.no_rawat = '" + norawat + "' GROUP BY 2");

                try {

                    rs = psPractRanap.executeQuery();
                    while (rs.next()) {
                        TabModePract.addRow(new Object[]{
                            rs.getString("no_rawat"),
                            rs.getString("kd_dokter"),
                            rs.getString("nm_dokter"),
                            rs.getString("no_ktp"),
                            rs.getString("jk"),
                            rs.getString("tgl_lahir"),
                            rs.getString("alamat"),
                            rs.getString("tmp_lahir"),
                            rs.getString("gol_drh"),
                            rs.getString("agama"),
                            rs.getString("stts_nikah"),
                            rs.getString("kd_sps"),
                            rs.getString("alumni"),
                            rs.getString("no_ijn_praktek"),
                            rs.getString("status"),
                            rs.getString("email"),
                            rs.getString("no_telp")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psPractRanap != null) {
                        psPractRanap.close();
                    }
                }

                psPract = koneksi.prepareStatement("SELECT\n"
                        + "	periksa_lab.no_rawat,\n"
                        + "	periksa_lab.kd_jenis_prw,\n"
                        + "	detail_periksa_lab.tgl_periksa,\n"
                        + "	detail_periksa_lab.jam,\n"
                        + "	detail_periksa_lab.id_template,\n"
                        + "	detail_periksa_lab.nilai,\n"
                        + "	detail_periksa_lab.nilai_rujukan,\n"
                        + "	detail_periksa_lab.keterangan,\n"
                        + "	periksa_lab.`status`,\n"
                        + "	periksa_lab.kd_dokter,\n"
                        + "	template_laboratorium.Pemeriksaan,\n"
                        + "	jns_perawatan_lab.nm_perawatan,\n"
                        + "	satu_sehat_mapping_lab.sampel_code AS snomed,\n"
                        + "	satu_sehat_mapping_lab.code AS loinc,\n"
                        + "	satu_sehat_mapping_lab.sampel_code AS subsnomedcode,\n"
                        + "	satu_sehat_mapping_lab.code AS subloinccode,\n"
                        + "	dokter.nm_dokter,\n"
                        + "	dokter.jk,\n"
                        + "	dokter.tgl_lahir,\n"
                        + "	pegawai.alamat,\n"
                        + "	pegawai.no_ktp,\n"
                        + "	dokter.tmp_lahir,\n"
                        + "	dokter.gol_drh,\n"
                        + "	dokter.agama,\n"
                        + "	dokter.stts_nikah,\n"
                        + "	dokter.kd_sps,\n"
                        + "	dokter.alumni,\n"
                        + "	dokter.no_ijn_praktek,\n"
                        + "	dokter.STATUS,\n"
                        + "	'email@email.com' as email,\n"
                        + "	dokter.no_telp \n"
                        + "FROM\n"
                        + "	periksa_lab\n"
                        + "	INNER JOIN detail_periksa_lab ON periksa_lab.no_rawat = detail_periksa_lab.no_rawat \n"
                        + "	AND periksa_lab.kd_jenis_prw = detail_periksa_lab.kd_jenis_prw\n"
                        + "	INNER JOIN template_laboratorium ON detail_periksa_lab.id_template = template_laboratorium.id_template\n"
                        + "	INNER JOIN jns_perawatan_lab ON detail_periksa_lab.kd_jenis_prw = jns_perawatan_lab.kd_jenis_prw \n"
                        + "	AND periksa_lab.kd_jenis_prw = jns_perawatan_lab.kd_jenis_prw \n"
                        + "	AND template_laboratorium.kd_jenis_prw = jns_perawatan_lab.kd_jenis_prw\n"
                        + "	INNER JOIN dokter ON periksa_lab.kd_dokter=dokter.kd_dokter\n"
                        + "	INNER JOIN pegawai ON dokter.kd_dokter=pegawai.nik\n"
                        + "	LEFT JOIN satu_sehat_mapping_lab ON detail_periksa_lab.id_template = satu_sehat_mapping_lab.id_template \n"
                        + "WHERE\n"
                        + "	periksa_lab.no_rawat = '" + norawat + "' GROUP BY dokter.kd_dokter ");

                try {

                    rs = psPract.executeQuery();
                    while (rs.next()) {
                        TabModePract.addRow(new Object[]{
                            rs.getString("no_rawat"),
                            rs.getString("kd_dokter"),
                            rs.getString("nm_dokter"),
                            rs.getString("no_ktp"),
                            rs.getString("jk"),
                            rs.getString("tgl_lahir"),
                            rs.getString("alamat"),
                            rs.getString("tmp_lahir"),
                            rs.getString("gol_drh"),
                            rs.getString("agama"),
                            rs.getString("stts_nikah"),
                            rs.getString("kd_sps"),
                            rs.getString("alumni"),
                            rs.getString("no_ijn_praktek"),
                            rs.getString("status"),
                            rs.getString("email"),
                            rs.getString("no_telp")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psPract != null) {
                        psPract.close();
                    }
                }

                psPract = koneksi.prepareStatement("SELECT\n"
                        + "	periksa_radiologi.tgl_periksa,\n"
                        + "	periksa_radiologi.jam,\n"
                        + "	periksa_radiologi.no_rawat,\n"
                        + "	periksa_radiologi.kd_jenis_prw,\n"
                        + "	jns_perawatan_radiologi.nm_perawatan,\n"
                        + "	satu_sehat_mapping_radiologi.`code` snomed,\n"
                        + "	satu_sehat_mapping_radiologi.code as loincode,\n"
                        + "	periksa_radiologi.kd_dokter,\n"
                        + "	dokter.nm_dokter,\n"
                        + "	dokter.jk,\n"
                        + "	dokter.tgl_lahir,\n"
                        + "	pegawai.alamat,\n"
                        + "	pegawai.no_ktp,\n"
                        + "	dokter.tmp_lahir,\n"
                        + "	dokter.gol_drh,\n"
                        + "	dokter.agama,\n"
                        + "	dokter.stts_nikah,\n"
                        + "	dokter.kd_sps,\n"
                        + "	dokter.alumni,\n"
                        + "	dokter.no_ijn_praktek,\n"
                        + "	dokter.STATUS,\n"
                        + "	'email@email.com' as email,\n"
                        + "	dokter.no_telp \n"
                        + "FROM\n"
                        + "	periksa_radiologi\n"
                        + "	INNER JOIN jns_perawatan_radiologi ON periksa_radiologi.kd_jenis_prw = jns_perawatan_radiologi.kd_jenis_prw\n"
                        + "	INNER JOIN dokter ON periksa_radiologi.kd_dokter = dokter.kd_dokter\n"
                        + "	INNER JOIN pegawai ON dokter.kd_dokter=pegawai.nik\n"
                        + "	LEFT JOIN satu_sehat_mapping_radiologi ON jns_perawatan_radiologi.kd_jenis_prw = satu_sehat_mapping_radiologi.kd_jenis_prw\n"
                        + "	INNER JOIN hasil_radiologi ON periksa_radiologi.no_rawat = hasil_radiologi.no_rawat \n"
                        + "	AND periksa_radiologi.tgl_periksa = hasil_radiologi.tgl_periksa \n"
                        + "	AND periksa_radiologi.jam = hasil_radiologi.jam \n"
                        + "       WHERE\n"
                        + "	periksa_radiologi.no_rawat = '" + norawat + "' GROUP BY dokter.kd_dokter ");

                try {
                    rs = psPract.executeQuery();
                    while (rs.next()) {
                        TabModePract.addRow(new Object[]{
                            rs.getString("no_rawat"),
                            rs.getString("kd_dokter"),
                            rs.getString("nm_dokter"),
                            rs.getString("no_ktp"),
                            rs.getString("jk"),
                            rs.getString("tgl_lahir"),
                            rs.getString("alamat"),
                            rs.getString("tmp_lahir"),
                            rs.getString("gol_drh"),
                            rs.getString("agama"),
                            rs.getString("stts_nikah"),
                            rs.getString("kd_sps"),
                            rs.getString("alumni"),
                            rs.getString("no_ijn_praktek"),
                            rs.getString("status"),
                            rs.getString("email"),
                            rs.getString("no_telp")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psPract != null) {
                        psPract.close();
                    }
                }

                psPract = koneksi.prepareStatement("SELECT\n"
                        + "	reg_periksa.tgl_registrasi,\n"
                        + "	reg_periksa.jam_reg,\n"
                        + "	laporan_operasi.no_rawat,\n"
                        + "	laporan_operasi.tanggal,\n"
                        + "	laporan_operasi.diagnosa_preop,\n"
                        + "	laporan_operasi.diagnosa_postop,\n"
                        + "	laporan_operasi.selesaioperasi,\n"
                        + "	laporan_operasi.laporan_operasi,\n"
                        + "	operasi.operator1 as kd_dokter,\n"
                        + "	dokter.nm_dokter,\n"
                        + "	dokter.jk,\n"
                        + "	dokter.tgl_lahir,\n"
                        + "	pegawai.alamat,\n"
                        + "	pegawai.no_ktp,\n"
                        + "	dokter.tmp_lahir,\n"
                        + "	dokter.gol_drh,\n"
                        + "	dokter.agama,\n"
                        + "	dokter.stts_nikah,\n"
                        + "	dokter.kd_sps,\n"
                        + "	dokter.alumni,\n"
                        + "	dokter.no_ijn_praktek,\n"
                        + "	dokter.STATUS,\n"
                        + "	'email@email.com' as email,\n"
                        + "	dokter.no_telp \n"
                        + "FROM\n"
                        + "	reg_periksa\n"
                        + "	INNER JOIN laporan_operasi ON reg_periksa.no_rawat = laporan_operasi.no_rawat\n"
                        + "	inner join operasi on laporan_operasi.no_rawat=operasi.no_rawat inner JOIN dokter ON operasi.operator1 = dokter.kd_dokter \n"
                        + "	INNER JOIN pegawai ON dokter.kd_dokter=pegawai.nik\n"
                        + "WHERE\n"
                        + "	laporan_operasi.no_rawat = '" + norawat + "' GROUP BY dokter.kd_dokter ");

                try {
                    rs = psPract.executeQuery();
                    while (rs.next()) {
                        TabModePract.addRow(new Object[]{
                            rs.getString("no_rawat"),
                            rs.getString("kd_dokter"),
                            rs.getString("nm_dokter"),
                            rs.getString("no_ktp"),
                            rs.getString("jk"),
                            rs.getString("tgl_lahir"),
                            rs.getString("alamat"),
                            rs.getString("tmp_lahir"),
                            rs.getString("gol_drh"),
                            rs.getString("agama"),
                            rs.getString("stts_nikah"),
                            rs.getString("kd_sps"),
                            rs.getString("alumni"),
                            rs.getString("no_ijn_praktek"),
                            rs.getString("status"),
                            rs.getString("email"),
                            rs.getString("no_telp")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psPract != null) {
                        psPract.close();
                    }
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public static String sanitizeStringforNonASCII(String input) {
        if (input == null) {
            return "";
        }
        String sanitized = input.replaceAll("[^\\x00-\\x7F]", "");
        sanitized = sanitized.replaceAll("\\s+", " ");
        sanitized = sanitized.trim();

        return sanitized;
    }

    public static String sanitizeString(String input) {
        if (input == null) {
            return "";
        }

        try {
            String sanitized = input;

            // Remove quotes and backslashes - use simple character replacement instead of regex
            sanitized = replaceCharacters(sanitized, new String[]{"\"", "\\", "\n", "\r", "\t"});

            // Remove HTML tags - use safer approach
            sanitized = removeHtmlTags(sanitized);

            // Remove HTML entities - use safer approach
            sanitized = removeHtmlEntities(sanitized);

            sanitized = sanitized.trim();

            // Limit length to prevent extremely long strings that could cause issues
            if (sanitized.length() > 10000) {
                sanitized = sanitized.substring(0, 10000);
            }

            return sanitized;

        } catch (Exception e) {
            System.out.println("Error sanitizing string: " + e.getMessage());
            // Return a safe fallback
            return "sanitization_error";
        }
    }

// Safe character replacement without regex
    private static String replaceCharacters(String input, String[] charactersToRemove) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        try {
            String result = input;
            for (String charToRemove : charactersToRemove) {
                if (charToRemove != null && !charToRemove.isEmpty()) {
                    result = result.replace(charToRemove, "");
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println("Error in replaceCharacters: " + e.getMessage());
            return input;
        }
    }

// Safer HTML tag removal
    private static String removeHtmlTags(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        try {
            // Simple approach: remove content between < and >
            StringBuilder result = new StringBuilder();
            boolean inTag = false;

            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c == '<') {
                    inTag = true;
                } else if (c == '>') {
                    inTag = false;
                } else if (!inTag) {
                    result.append(c);
                }
            }

            return result.toString();
        } catch (Exception e) {
            System.out.println("Error in removeHtmlTags: " + e.getMessage());
            return input.replace("<", "").replace(">", "");
        }
    }

// Safer HTML entity removal
    private static String removeHtmlEntities(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        try {
            // Remove common HTML entities without causing recursive replacement
            String result = input;
            // Use a single pass to replace common entities
            result = result.replace("&amp;", "&")
                    .replace("&lt;", "<")
                    .replace("&gt;", ">")
                    .replace("&quot;", "\"")
                    .replace("&#39;", "'")
                    .replace("&nbsp;", " ")
                    .replace("&ndash;", "-")
                    .replace("&mdash;", "-")
                    .replace("&lsquo;", "'")
                    .replace("&rsquo;", "'")
                    .replace("&ldquo;", "\"")
                    .replace("&rdquo;", "\"")
                    .replace("&bull;", "*")
                    .replace("&hellip;", "...");

            // Process remaining entities with a more careful approach
            StringBuilder cleaned = new StringBuilder();
            int i = 0;
            while (i < result.length()) {
                char c = result.charAt(i);
                if (c == '&') {
                    // Look for the next semicolon to see if it's an entity
                    int semicolonIndex = result.indexOf(';', i);
                    if (semicolonIndex != -1) {
                        // Check if this looks like an HTML entity (not too long, contains common patterns)
                        String entity = result.substring(i, semicolonIndex + 1);
                        if (isLikelyHtmlEntity(entity)) {
                            // Skip this entity - just add a space instead
                            cleaned.append(' ');
                            i = semicolonIndex + 1;
                            continue;
                        }
                    }
                }
                cleaned.append(c);
                i++;
            }

            return cleaned.toString();
        } catch (Exception e) {
            System.out.println("Error in removeHtmlEntities: " + e.getMessage());
            return input.replace("&", "");
        }
    }

    // Helper method to determine if a string looks like an HTML entity
    private static boolean isLikelyHtmlEntity(String entity) {
        // Basic check: entities typically start with & and end with ;
        // and have a reasonable length (not too long to prevent recursion issues)
        if (entity.length() > 20) {
            return false; // Too long to be a real entity
        }

        // Check if it matches common entity patterns
        return entity.matches("&[a-zA-Z][a-zA-Z0-9]{0,10};")
                || entity.matches("&#\\d{1,7};")
                || entity.matches("&#x[0-9a-fA-F]{1,6};");
    }

    /**
     * Converts JSON to an HTML table representation for user-friendly display
     *
     * @param jsonString The JSON string to convert
     * @return HTML string representation of the JSON
     */
    public String convertJsonToHtmlTable(String jsonString) {
        // Call the overloaded method with an empty resource name for backward compatibility
        return convertJsonToHtmlTable(jsonString, "");
    }

    public String convertJsonToHtmlTable(String jsonString, String resourceName) {
        if (jsonString == null || jsonString.isEmpty()) {
            return "<table border=\"1\" style=\"border-collapse: collapse; width: 100%; font-family: Arial, sans-serif; font-size: 11px; margin: 3px;\"><tr><td>Data tidak tersedia</td></tr></table>";
        }

        try {
            StringBuilder html = new StringBuilder();
            JsonNode node = mapper.readTree(jsonString);

            html.append("<table border=\"1\" style=\"border-collapse: collapse; width: 100%; font-family: Arial, sans-serif; font-size: 11px; margin: 3px;\">\n");
            convertJsonNodeToHtml(node, html, 0, resourceName);
            html.append("</table>\n");

            return html.toString();
        } catch (Exception e) {
            System.out.println("Error converting JSON to HTML: " + e.getMessage());
            return "<table border=\"1\" style=\"border-collapse: collapse; width: 100%; font-family: Arial, sans-serif; font-size: 11px; margin: 3px;\"><tr><td>Error converting JSON to HTML: " + sanitizeString(e.getMessage()) + "</td></tr></table>";
        }
    }

    /**
     * Recursive helper method to convert JSON nodes to HTML table rows
     */
    private void convertJsonNodeToHtml(JsonNode node, StringBuilder html, int depth, String key) {
        // Prevent infinite recursion with a maximum depth check
        if (depth > 20) {
            html.append("<tr style=\"background-color: #f9f9f9;\">\n");
            html.append("  <td style=\"padding: 2px; font-weight: bold;\">[MAKSIMUM KEDALAMAN DICAPAI - Mencegah StackOverflow]</td>\n");
            html.append("</tr>\n");
            return;
        }

        if (node.isObject()) {
            // Handle JSON object - if it's the root object, show the key as header
            if (depth == 0 && !key.isEmpty()) {
                html.append("<tr><td colspan=\"1\" style=\"font-weight: bold; background-color: #f0f0f0; padding: 3px;\">").append(getIndonesianLabel(sanitizeString(key))).append(" (objek)</td></tr>\n");
            }

            node.fields().forEachRemaining(entry -> {
                String childKey = entry.getKey();
                JsonNode childNode = entry.getValue();

                // Avoid circular references by checking for self-references
                if (childNode == node) {
                    html.append("<tr style=\"background-color: #f9f9f9;\">\n");
                    html.append("  <td style=\"padding: 2px; font-weight: bold; color: red;\">[REFERENSI SIKLUS TERDETEKSI]</td>\n");
                    html.append("</tr>\n");
                } else if (childNode.isObject() || childNode.isArray()) {
                    // For nested objects/arrays, create a header row with the key and then expand the content below
                    html.append("<tr style=\"background-color: #f5f5f5;\">\n");
                    html.append("  <td style=\"padding: 2px; font-weight: bold; border-bottom: 1px solid #bdc3c7;\">" + getIndonesianLabel(sanitizeString(childKey)) + "</td>\n");
                    html.append("</tr>\n");
                    html.append("<tr>\n");
                    html.append("  <td style=\"padding: 2px;\">\n");
                    html.append("    <table border=\"0\" style=\"border-collapse: collapse; width: 100%;\">\n");
                    convertJsonNodeToHtml(childNode, html, depth + 1, "");
                    html.append("    </table>\n");
                    html.append("  </td>\n");
                    html.append("</tr>\n");
                } else {
                    html.append("<tr style=\"background-color: ").append(depth % 2 == 0 ? "#ffffff" : "#f9f9f9").append(";\">\n");
                    html.append("  <td style=\"padding: 2px;\"><strong>" + getIndonesianLabel(sanitizeString(childKey)) + ":</strong> " + sanitizeString(childNode.asText()) + "</td>\n");
                    html.append("</tr>\n");
                }
            });
        } else if (node.isArray()) {
            // Handle JSON array
            if (!key.isEmpty()) {
                html.append("<tr><td colspan=\"1\" style=\"font-weight: bold; background-color: #e0e0e0; padding: 3px;\">").append(getIndonesianLabel(sanitizeString(key))).append(" (larik)</td></tr>\n");
            }

            // Process each array item
            for (int i = 0; i < node.size(); i++) {
                JsonNode arrayItem = node.get(i);
                // Avoid circular references by checking for self-references
                if (arrayItem == node) {
                    html.append("<tr style=\"background-color: #f9f9f9;\">\n");
                    html.append("  <td style=\"padding: 2px; color: red;\">[REFERENSI SIKLUS TERDETEKSI]</td>\n");
                    html.append("</tr>\n");
                } else if (arrayItem.isObject() || arrayItem.isArray()) {
                    html.append("<tr style=\"background-color: #f5f5f5; padding: 2px;\">\n");
                    html.append("  <td style=\"padding: 2px; font-weight: bold; border-bottom: 1px solid #bdc3c7;\">[Item " + i + "]</td>\n");
                    html.append("</tr>\n");
                    html.append("<tr>\n");
                    html.append("  <td style=\"padding: 2px;\">\n");
                    html.append("    <table border=\"0\" style=\"border-collapse: collapse; width: 100%;\">\n");
                    convertJsonNodeToHtml(arrayItem, html, depth + 1, "");
                    html.append("    </table>\n");
                    html.append("  </td>\n");
                    html.append("</tr>\n");
                } else {
                    html.append("<tr style=\"background-color: ").append(depth % 2 == 0 ? "#ffffff" : "#f9f9f9").append(";\">\n");
                    html.append("  <td style=\"padding: 2px;\">[Item " + i + "]: " + sanitizeString(arrayItem.asText()) + "</td>\n");
                    html.append("</tr>\n");
                }
            }
        } else {
            // Handle simple value
            html.append("<tr style=\"background-color: ").append(depth % 2 == 0 ? "#ffffff" : "#f9f9f9").append(";\">\n");
            html.append("  <td style=\"padding: 2px;\">").append(sanitizeString(node.asText())).append("</td>\n");
            html.append("</tr>\n");
        }
    }

    /**
     * Translates technical JSON keys to user-friendly Indonesian labels for
     * medical staff
     *
     * @param key The technical JSON key
     * @return Human-readable Indonesian label
     */
    private String getIndonesianLabel(String key) {
        // Map technical keys to Indonesian user-friendly labels
        switch (key.toLowerCase()) {
            case "resourcetype":
                return "Jenis Sumber Daya";
            case "id":
                return "ID";
            case "identifier":
                return "Identitas";
            case "use":
                return "Penggunaan";
            case "system":
                return "Sistem";
            case "value":
                return "Nilai";
            case "assigner":
                return "Pemberi";
            case "display":
                return "Tampilan";
            case "active":
                return "Aktif";
            case "name":
                return "Nama";
            case "text":
                return "Teks";
            case "coding":
                return "Kode";
            case "type":
                return "Jenis";
            case "alias":
                return "Alias";
            case "telecom":
                return "Kontak";
            case "address":
                return "Alamat";
            case "line":
                return "Garis Alamat";
            case "city":
                return "Kota";
            case "state":
                return "Provinsi";
            case "postalCode":
                return "Kode Pos";
            case "country":
                return "Negara";
            case "contact":
                return "Kontak";
            case "purpose":
                return "Tujuan";
            case "gender":
                return "Jenis Kelamin";
            case "birthDate":
                return "Tanggal Lahir";
            case "deceasedBoolean":
                return "Status Kematian";
            case "maritalStatus":
                return "Status Perkawinan";
            case "subject":
                return "Subjek (Pasien)";
            case "class":
                return "Kelas Kunjungan";
            case "incomingReferral":
                return "Rujukan Masuk";
            case "reason":
                return "Alasan Kunjungan";
            case "diagnosis":
                return "Diagnosis";
            case "condition":
                return "Kondisi";
            case "role":
                return "Peran";
            case "rank":
                return "Peringkat";
            case "hospitalization":
                return "Rawat Inap";
            case "dischargeDisposition":
                return "Status Keluar";
            case "period":
                return "Periode Kunjungan";
            case "start":
                return "Tanggal Mulai";
            case "end":
                return "Tanggal Selesai";
            case "status":
                return "Status";
            case "clinicalStatus":
                return "Status Klinis";
            case "verificationStatus":
                return "Status Verifikasi";
            case "category":
                return "Kategori";
            case "code":
                return "Kode";
            case "onsetDateTime":
                return "Tanggal Awal Kondisi";
            case "performedPeriod":
                return "Periode Pelaksanaan";
            case "performer":
                return "Pelaksana";
            case "actor":
                return "Pelaku";
            case "location":
                return "Lokasi";
            case "dosageInstruction":
                return "Aturan Pemakaian";
            case "doseQuantity":
                return "Jumlah Dosis";
            case "route":
                return "Rute Pemberian";
            case "timing":
                return "Waktu Pemberian";
            case "repeat":
                return "Pengulangan";
            case "frequency":
                return "Frekuensi";
            case "periodUnit":
                return "Satuan Periode";
            case "medicationCodeableConcept":
                return "Konsep Obat";
            case "intent":
                return "Maksud Pelayanan";
            case "requester":
                return "Peminta";
            case "agent":
                return "Agen";
            case "onBehalfOf":
                return "Atas Nama";
            case "meta":
                return "Metadata";
            case "lastUpdated":
                return "Tanggal Terakhir Diperbarui";
            case "entry":
                return "Entri";
            case "fullUrl":
                return "URL Lengkap";
            case "resource":
                return "Sumber Daya";
            case "title":

                return "Judul";
            case "author":
                return "Penyusun";
            case "date":
                return "Tanggal";
            case "encounter":
                return "Kunjungan";
            case "section":
                return "Bagian";
            case "t":
                return "Keterangan";
            case "issued":
                return "Tanggal Diterbitkan";
            case "effectiveDateTime":
                return "Tanggal Efektif";
            case "result":
                return "Hasil";
            case "conclusion":
                return "Kesimpulan";
            case "image":
                return "Gambar";
            case "comment":
                return "Komentar";
            case "link":
                return "Tautan";
            case "lotNumber":
                return "Nomor Lot";
            case "manufacturer":
                return "Produsen";
            case "manufactureDate":
                return "Tanggal Produksi";
            case "expirationDate":
                return "Tanggal Kadaluarsa";
            case "model":
                return "Model";
            case "patient":
                return "Pasien";
            case "focalDevice":
                return "Perangkat Fokus";
            case "action":
                return "Tindakan";
            case "manipulated":
                return "Dirancang";
            case "note":
                return "Catatan";
            case "bodySite":
                return "Lokasi Tubuh";
            case "reasonCode":
                return "Kode Alasan";
            case "additionalInstruction":
                return "Instruksi Tambahan";
            case "tabel":
                return "Tabel";
            case "div":
                return "Konten HTML";
            case "noSep":
                return "Nomor SEP";
            case "noRm":
                return "Nomor Rekam Medis";
            case "noKartu":
                return "Nomor Kartu";
            case "noKtp":
                return "Nomor KTP";
            case "nm_pasien":
                return "Nama Pasien";
            default:
                // If no match, return the original key but with first letter capitalized
                if (key != null && !key.isEmpty()) {
                    return Character.toUpperCase(key.charAt(0)) + key.substring(1);
                }
                return key;
        }
    }

    /**
     * Displays the JSON bundle in human-readable format for user review Using
     * structured HTML table formatting for better visualization of the data
     * structure
     */
    public void displayHtmlBundleView() {
        try {
            // Generate all JSON resources first
            setJsonComposition();
            setJsonPatien();
            setJsonEncounter();
            setJsonMedicationrequest();
            setJsonPractitioner();
            setJsonOrganisasi();
            setJsonCondition();
            setJsonDiagnosticReport();
            setJsonProcedure();
            setJsonDevice();

            // Create HTML representation of the entire bundle
            StringBuilder htmlBundle = new StringBuilder();
            htmlBundle.append("<!DOCTYPE html>\n");
            htmlBundle.append("<html>\n<head>\n");
            htmlBundle.append("<style>\n");
            htmlBundle.append("body { font-family: Arial, sans-serif; margin: 5px; background-color: #ffffff; font-size: 11px; }\n");
            htmlBundle.append("h1 { color: #2c3e50; text-align: center; border-bottom: 2px solid #3498db; padding-bottom: 5px; font-size: 16px; margin: 5px 0; }\n");
            htmlBundle.append("h2, h3 { color: #2c3e50; margin-top: 10px; font-size: 12px; }\n");
            htmlBundle.append("table { border-collapse: collapse; width: 100%; margin-bottom: 10px; font-size: 11px; }\n");
            htmlBundle.append("th, td { border: 1px solid #bdc3c7; padding: 2px; text-align: left; vertical-align: top; }\n");
            htmlBundle.append("th { background-color: #ecf0f1; font-weight: bold; color: #2c3e50; font-size: 11px; }\n");
            htmlBundle.append("tr:nth-child(even) { background-color: #f9f9f9; }\n");
            htmlBundle.append("tr:nth-child(odd) { background-color: #ffffff; }\n");
            htmlBundle.append(".resource-header { background: linear-gradient(to right, #3498db, #2980b9); color: white; padding: 6px; margin: 8px 0 5px 0; font-size: 13px; font-weight: bold; }\n");
            htmlBundle.append(".note { background-color: #e8f6f3; border-left: 3px solid #1abc9c; padding: 6px; margin: 8px 0; }\n");
            htmlBundle.append(".container { max-width: 1200px; margin: 0 auto; }\n");
            htmlBundle.append(".summary-box { background: #f8f9fa; border: 1px solid #dee2e6; border-radius: 3px; padding: 8px; margin: 8px 0; font-size: 11px; }\n");
            htmlBundle.append("</style>\n");
            htmlBundle.append("</head>\n<body>\n");
            htmlBundle.append("<div class='container'>\n");

            htmlBundle.append("<h1>PREVIEW DOKUMEN SMART CLAIM JKN</h1>\n");
            htmlBundle.append("<div class='note'>Warning: Ini adalah data yang akan dikirim ke BPJS. Silakan tinjau sebelum disimpan dan dikirim.</div>\n\n");

            // Summary Information
            htmlBundle.append("<div class='summary-box'>\n");
            htmlBundle.append("<h3> ringkasan informasi pasien</h3>\n");
            htmlBundle.append("<p><strong>No. SEP:</strong> ").append(TNoSEP.getText()).append("</p>\n");
            htmlBundle.append("<p><strong>Nama Pasien:</strong> ").append(TPasien.getText()).append("</p>\n");
            htmlBundle.append("<p><strong>No. Rekam Medis:</strong> ").append(TNoRM.getText()).append("</p>\n");
            htmlBundle.append("<p><strong>Status Rawat:</strong> ").append(lblStatusRawat.getText()).append("</p>\n");
            htmlBundle.append("<p><strong>PPK Pelayanan:</strong> ").append(NmPPK.getText()).append(" (").append(KdPPKBPJS.getText()).append(")</p>\n");
            htmlBundle.append("</div>\n\n");

            // Add each resource with its HTML table representation in user-friendly format
            htmlBundle.append("<table class='resource-detail' style='width: 100%; border-collapse: collapse; margin: 8px 0; border: 1px solid #ddd;'>\n");
            htmlBundle.append("  <tr style='background: linear-gradient(to right, #3498db, #2980b9); color: white;'>\n");
            htmlBundle.append("    <td style='padding: 6px; font-size: 12px; font-weight: bold;'>Building: IDENTITAS FASILITAS PELAYANAN - Data Fasilitas Pelayanan Kesehatan</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("  <tr>\n");
            htmlBundle.append("    <td style='padding: 0;'>").append(convertJsonToHtmlTable(jsonOrganization, "Organization")).append("</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("</table>\n\n");

            htmlBundle.append("<table class='resource-detail' style='width: 100%; border-collapse: collapse; margin: 8px 0; border: 1px solid #ddd;'>\n");
            htmlBundle.append("  <tr style='background: linear-gradient(to right, #3498db, #2980b9); color: white;'>\n");
            htmlBundle.append("    <td style='padding: 6px; font-size: 12px; font-weight: bold;'>Person: DATA PASIEN - Informasi Identitas dan Data Kependudukan Pasien</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("  <tr>\n");
            htmlBundle.append("    <td style='padding: 0;'>").append(convertJsonToHtmlTable(jsonPatient, "Patient")).append("</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("</table>\n\n");

            htmlBundle.append("<table class='resource-detail' style='width: 100%; border-collapse: collapse; margin: 8px 0; border: 1px solid #ddd;'>\n");
            htmlBundle.append("  <tr style='background: linear-gradient(to right, #3498db, #2980b9); color: white;'>\n");
            htmlBundle.append("    <td style='padding: 6px; font-size: 12px; font-weight: bold;'>Bed: KUNJUNGAN RAWAT - Informasi Kunjungan dan Pelayanan Rawat Pasien</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("  <tr>\n");
            htmlBundle.append("    <td style='padding: 0;'>").append(convertJsonToHtmlTable(jsonEncounter, "Encounter")).append("</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("</table>\n\n");

            htmlBundle.append("<table class='resource-detail' style='width: 100%; border-collapse: collapse; margin: 8px 0; border: 1px solid #ddd;'>\n");
            htmlBundle.append("  <tr style='background: linear-gradient(to right, #3498db, #2980b9); color: white;'>\n");
            htmlBundle.append("    <td style='padding: 6px; font-size: 12px; font-weight: bold;'>Medical: DOKTER PELAYAN - Data Dokter yang Memberikan Pelayanan</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("  <tr>\n");
            htmlBundle.append("    <td style='padding: 0;'>").append(convertJsonToHtmlTable(jsonPractitioner, "Practitioner")).append("</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("</table>\n\n");

            htmlBundle.append("<table class='resource-detail' style='width: 100%; border-collapse: collapse; margin: 8px 0; border: 1px solid #ddd;'>\n");
            htmlBundle.append("  <tr style='background: linear-gradient(to right, #3498db, #2980b9); color: white;'>\n");
            htmlBundle.append("    <td style='padding: 6px; font-size: 12px; font-weight: bold;'>Clipboard: KESIMPULAN PELAYANAN - Ringkasan dan Kesimpulan Pelayanan Medis</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("  <tr>\n");
            htmlBundle.append("    <td style='padding: 0;'>").append(convertJsonToHtmlTable(jsonComposititon, "Composition")).append("</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("</table>\n\n");

            htmlBundle.append("<table class='resource-detail' style='width: 100%; border-collapse: collapse; margin: 8px 0; border: 1px solid #ddd;'>\n");
            htmlBundle.append("  <tr style='background: linear-gradient(to right, #3498db, #2980b9); color: white;'>\n");
            htmlBundle.append("    <td style='padding: 6px; font-size: 12px; font-weight: bold;'>Stethoscope: DIAGNOSIS KLINIS - Diagnosis dan Temuan Klinis Pasien</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("  <tr>\n");
            htmlBundle.append("    <td style='padding: 0;'>").append(convertJsonToHtmlTable(jsonCondition, "Condition")).append("</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("</table>\n\n");

            htmlBundle.append("<table class='resource-detail' style='width: 100%; border-collapse: collapse; margin: 8px 0; border: 1px solid #ddd;'>\n");
            htmlBundle.append("  <tr style='background: linear-gradient(to right, #3498db, #2980b9); color: white;'>\n");
            htmlBundle.append("    <td style='padding: 6px; font-size: 12px; font-weight: bold;'>Microscope: HASIL PEMERIKSAAN - Hasil Pemeriksaan Laboratorium dan Diagnostik</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("  <tr>\n");
            htmlBundle.append("    <td style='padding: 0;'>").append(convertJsonToHtmlTable(jsonDiagnosticReport, "DiagnosticReport")).append("</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("</table>\n\n");

            htmlBundle.append("<table class='resource-detail' style='width: 100%; border-collapse: collapse; margin: 8px 0; border: 1px solid #ddd;'>\n");
            htmlBundle.append("  <tr style='background: linear-gradient(to right, #3498db, #2980b9); color: white;'>\n");
            htmlBundle.append("    <td style='padding: 6px; font-size: 12px; font-weight: bold;'>Tools: ALAT MEDIS - Informasi Alat Medis yang Digunakan</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("  <tr>\n");
            htmlBundle.append("    <td style='padding: 0;'>").append(convertJsonToHtmlTable(jsonDevice, "Device")).append("</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("</table>\n\n");

            htmlBundle.append("<table class='resource-detail' style='width: 100%; border-collapse: collapse; margin: 8px 0; border: 1px solid #ddd;'>\n");
            htmlBundle.append("  <tr style='background: linear-gradient(to right, #3498db, #2980b9); color: white;'>\n");
            htmlBundle.append("    <td style='padding: 6px; font-size: 12px; font-weight: bold;'>Pill: RESEP OBAT - Data Obat yang Diresepkan dan Aturan Pemakaian</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("  <tr>\n");
            htmlBundle.append("    <td style='padding: 0;'>").append(convertJsonToHtmlTable(jsonMedicationRequest, "MedicationRequest")).append("</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("</table>\n\n");

            htmlBundle.append("<table class='resource-detail' style='width: 100%; border-collapse: collapse; margin: 8px 0; border: 1px solid #ddd;'>\n");
            htmlBundle.append("  <tr style='background: linear-gradient(to right, #3498db, #2980b9); color: white;'>\n");
            htmlBundle.append("    <td style='padding: 6px; font-size: 12px; font-weight: bold;'>Medical: TINDAKAN MEDIS - Prosedur dan Tindakan Medis yang Dilakukan</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("  <tr>\n");
            htmlBundle.append("    <td style='padding: 0;'>").append(convertJsonToHtmlTable(jsonProcedure, "Procedure")).append("</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("</table>\n\n");

            // Generate the complete bundle and add it to the display
            // We'll generate the bundle for display purposes but won't call the full setJsonMrBundle method
            // to avoid circular calls
            String tempBundle = generateBundleForDisplay();
            htmlBundle.append("<table class='resource-detail' style='width: 100%; border-collapse: collapse; margin: 8px 0; border: 1px solid #ddd;'>\n");
            htmlBundle.append("  <tr style='background: linear-gradient(to right, #3498db, #2980b9); color: white;'>\n");
            htmlBundle.append("    <td style='padding: 6px; font-size: 12px; font-weight: bold;'>Box: DOKUMEN LENGKAP (BUNDLE) - Struktur Dokumen Lengkap untuk Klaim BPJS</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("  <tr>\n");
            htmlBundle.append("    <td style='padding: 0;'>");
            if (tempBundle != null && !tempBundle.isEmpty()) {
                htmlBundle.append(convertJsonToHtmlTable(tempBundle));
            } else {
                htmlBundle.append("<table border='1' style='border-collapse: collapse; width: 100%; font-family: Arial, sans-serif; font-size: 11px; margin: 3px; padding: 2px;'><tr><td>Dokumen belum dibuat dengan benar</td></tr></table>");
            }
            htmlBundle.append("</td>\n");
            htmlBundle.append("  </tr>\n");
            htmlBundle.append("</table>\n\n");

            htmlBundle.append("</div>\n"); // Close container
            htmlBundle.append("</body>\n</html>");

            // Display in the editorpane
            TJsonBody.setText(htmlBundle.toString());
            System.out.println("HTML Bundle view displayed successfully.");
        } catch (Exception e) {
            System.out.println("Error creating HTML bundle view: " + e.getMessage());
            e.printStackTrace();

            // Fallback: Display error message in HTML format
            String errorHtml = "<!DOCTYPE html>\n<html><head><style>"
                    + "body { font-family: Arial, sans-serif; margin: 10px; background-color: #ffffff; } "
                    + ".error { background-color: #fadbd8; color: #c0392b; padding: 15px; border-radius: 5px; margin: 10px 0; } "
                    + ".summary-box { background: #f8f9fa; border: 1px solid #dee2e6; border-radius: 5px; padding: 15px; margin: 10px 0; } "
                    + ".container { max-width: 1200px; margin: 0 auto; }"
                    + "</style></head><body>"
                    + "<div class='container'>"
                    + "<h1>Error menampilkan tampilan HTML terformat</h1>"
                    + "<div class='error'>Keterangan: " + sanitizeString(e.getMessage()) + "</div>"
                    + "<div class='summary-box'>"
                    + "<h3> ringkasan informasi pasien</h3>"
                    + "<p><strong>No. SEP:</strong> " + sanitizeString(TNoSEP.getText()) + "</p>"
                    + "<p><strong>Nama Pasien:</strong> " + sanitizeString(TPasien.getText()) + "</p>"
                    + "<p><strong>No. Rekam Medis:</strong> " + sanitizeString(TNoRM.getText()) + "</p>"
                    + "<p><strong>Status Rawat:</strong> " + sanitizeString(lblStatusRawat.getText()) + "</p>"
                    + "<p><strong>PPK Pelayanan:</strong> " + sanitizeString(NmPPK.getText()) + " (" + sanitizeString(KdPPKBPJS.getText()) + ")</p>"
                    + "</div>"
                    + "<h3>Data JSON Mentah:</h3>"
                    + "<pre style='background-color: #f8f9fa; padding: 10px; border-radius: 4px; overflow-x: auto; font-size: 11px;'>" + sanitizeString(jsonMrBundle) + "</pre>"
                    + "</div>"
                    + "</body></html>";

            TJsonBody.setText(errorHtml);
        }

        TJsonBody.setSelectionStart(0);
        TJsonBody.setSelectionEnd(0);
    }

    /**
     * Formats JSON as pretty-printed text for better readability
     */
    private String formatJsonAsPrettyText(String jsonString) {
        if (jsonString == null || jsonString.isEmpty() || jsonString.equals("null")) {
            return "No data available";
        }

        try {
            // Use Jackson's pretty printer for JSON formatting
            JsonNode jsonNode = mapper.readTree(jsonString);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            // If JSON parsing fails, return the original string with basic formatting
            return jsonString;
        }
    }

    // =============================================================================
// HELPER METHODS - Reusable JSON builders
// =============================================================================
    private ObjectNode createIdentifier(String system, String value) {
        ObjectNode identifier = mapper.createObjectNode();
        try {
            identifier.put("system", system);
            identifier.put("value", sanitizeString(value));
        } catch (Exception e) {
            logError("Failed to create identifier with system: " + system + ", value: " + value, e);
            // Return minimal valid object or rethrow as runtime exception
            return mapper.createObjectNode(); // or handle based on your error strategy
        }
        return identifier;
    }

    private ObjectNode createIdentifier(String use, String system, String value, String assigner) {
        ObjectNode identifier = mapper.createObjectNode();
        try {
            identifier.put("use", use);
            identifier.put("system", system);
            identifier.put("value", sanitizeString(value));
            if (assigner != null && !assigner.isEmpty()) {
                ObjectNode assignerObj = mapper.createObjectNode();
                assignerObj.put("display", sanitizeString(assigner));
                identifier.set("assigner", assignerObj);
            }
        } catch (Exception e) {
            logError("Failed to create identifier with use: " + use + ", system: " + system + ", value: " + value, e);
            // Return what we have or handle appropriately
        }
        return identifier;
    }

    private ObjectNode createCoding(String system, String code, String display) {
        ObjectNode coding = mapper.createObjectNode();
        try {
            coding.put("system", system);
            coding.put("code", sanitizeString(code));
            if (display != null && !display.isEmpty()) {
                coding.put("display", sanitizeString(display));
            }
        } catch (Exception e) {
            logError("Failed to create coding with system: " + system + ", code: " + code, e);
            // Consider returning partial object or empty
        }
        return coding;
    }

    // Fixed createCodeableConcept method to avoid recursion
    private ObjectNode createCodeableConcept(String system, String code, String display, String text) {
        try {
            ObjectNode codeableConcept = mapper.createObjectNode();

            // Create coding array - ensure it's a proper ArrayNode
            ArrayNode coding = mapper.createArrayNode();
            ObjectNode codingObj = mapper.createObjectNode();
            codingObj.put("system", (system != null && !system.isEmpty()) ? system : "http://hl7.org/fhir/sid/icd-10");
            codingObj.put("code", (code != null && !code.isEmpty()) ? code : "default");
            if (display != null && !display.isEmpty()) {
                codingObj.put("display", display);
            }
            // Properly add to array
            coding.add(codingObj);
            codeableConcept.set("coding", coding);

            // Add text if provided
            if (text != null && !text.isEmpty()) {
                codeableConcept.put("text", text);
            }

            return codeableConcept;
        } catch (Exception e) {
            System.out.println("Error in createCodeableConcept: " + e.getMessage());
            // Return minimal valid object with properly constructed arrays
            ObjectNode fallback = mapper.createObjectNode();
            ArrayNode fallbackCoding = mapper.createArrayNode();
            ObjectNode fallbackCodingObj = mapper.createObjectNode();
            fallbackCodingObj.put("system", "http://hl7.org/fhir/sid/icd-10");
            fallbackCodingObj.put("code", "default");
            fallbackCoding.add(fallbackCodingObj); // Ensure this is added to the array properly
            fallback.set("coding", fallbackCoding);
            return fallback;
        }
    }

    // Helper method to create sections safely
    private ObjectNode createSection(String title, String code, String display, String divContent, String textStatus) {
        try {
            ObjectNode section = mapper.createObjectNode();

            // Add title first
            section.put("title", title);

            // Code
            ObjectNode codeObj = mapper.createObjectNode();
            ArrayNode coding = mapper.createArrayNode();
            ObjectNode codingObj = mapper.createObjectNode();
            codingObj.put("system", "http://loinc.org");
            codingObj.put("code", code);
            if (display != null && !display.isEmpty()) {
                codingObj.put("display", display);
            }
            coding.add(codingObj);
            codeObj.set("coding", coding);
            if (display != null && !display.isEmpty()) {
                codeObj.put("text", display);
            }
            section.set("code", codeObj);

            // Text
            ObjectNode text = mapper.createObjectNode();
            text.put("status", textStatus != null ? textStatus : "generated");
            String safeDivContent = divContent != null ? divContent : "<div>No content</div>";
            // Ensure div content is properly formatted HTML
            if (!safeDivContent.startsWith("<div>")) {
                safeDivContent = "<div>" + safeDivContent + "</div>";
            }
            text.put("div", safeDivContent);
            section.set("text", text);

            return section;
        } catch (Exception e) {
            System.out.println("Error creating section: " + e.getMessage());
            return mapper.createObjectNode();
        }
    }

    // Safe string extraction helper
    private String getSafeString(JTable table, int row, int column, String defaultValue) {
        try {
            if (table == null || row >= table.getRowCount() || row < 0) {
                return defaultValue;
            }
            Object value = table.getValueAt(row, column);
            if (value == null) {
                return defaultValue;
            }
            String stringValue = value.toString();
            return stringValue != null ? stringValue.trim() : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

// Fixed createReference method
    private ObjectNode createReference(String reference, String display) {
        try {
            ObjectNode ref = mapper.createObjectNode();
            ref.put("reference", reference != null ? reference : "");
            if (display != null && !display.isEmpty()) {
                ref.put("display", display);
            }
            return ref;
        } catch (Exception e) {
            System.out.println("Error in createReference: " + e.getMessage());
            ObjectNode fallback = mapper.createObjectNode();
            fallback.put("reference", "Unknown");
            return fallback;
        }
    }

    private ObjectNode createTelecom(String system, String value, String use) {
        ObjectNode telecom = mapper.createObjectNode();
        try {
            telecom.put("system", (system != null) ? system : "");
            telecom.put("value", (value != null) ? sanitizeString(value) : "");
            if (use != null && !use.isEmpty()) {
                telecom.put("use", use);
            }
        } catch (Exception e) {
            logError("Failed to create telecom with system: " + system + ", value: " + value, e);
            // Handle error
        }
        return telecom;
    }

// Utility method for consistent error logging
    private void logError(String message, Exception e) {
        // Use your preferred logging framework
        System.err.println(message);
        if (e != null) {
            e.printStackTrace();
        }
        // Alternatively, use SLF4J/Log4j:
        // logger.error(message, e);
    }

    private String generateResourceId(String suffix) {
        String statusValue;
        if (lblStatusRawat != null && lblStatusRawat.getText() != null && lblStatusRawat.getText().contains("Ralan")) {
            statusValue = "2"; // Outpatient (Ralan)
        } else {
            statusValue = "1"; // Inpatient (Ranap)
        }
        return sanitizeString(KdPPKBPJS.getText()) + "-"
                + sanitizeString(KdPPKKemKes.getText()) + "-" + statusValue + "-"
                + processUUIDString(sanitizeString(suffix));
    }

    /**
     * Generate a patient-specific resource ID using noKartu as the unique
     * identifier
     */
    private String generateResourceIdForPatient(String noKartu, String noSEP) {
        String statusValue;
        if (lblStatusRawat != null && lblStatusRawat.getText() != null && lblStatusRawat.getText().contains("Ralan")) {
            statusValue = "2"; // Outpatient (Ralan)
        } else {
            statusValue = "1"; // Inpatient (Ranap)
        }
        // Use noKartu as the primary identifier for patient resources
        return sanitizeString(KdPPKBPJS.getText()) + "-"
                + sanitizeString(KdPPKKemKes.getText()) + "-" + statusValue + "-"
                + processUUIDString(sanitizeString(noKartu));
    }

    /**
     * Generate an encounter-specific resource ID using noSEP as the unique
     * identifier
     */
    private String generateResourceIdForEncounter(String noSEP) {
        String statusValue;
        if (lblStatusRawat != null && lblStatusRawat.getText() != null && lblStatusRawat.getText().contains("Ralan")) {
            statusValue = "2"; // Outpatient (Ralan)
        } else {
            statusValue = "1"; // Inpatient (Ranap)
        }
        // Use noSEP as the primary identifier for encounter resources
        return sanitizeString(KdPPKBPJS.getText()) + "-"
                + sanitizeString(KdPPKKemKes.getText()) + "-" + statusValue + "-"
                + processUUIDString(sanitizeString(noSEP));
    }

    /**
     * Generate a condition-specific resource ID using noSEP and rank as the
     * unique identifier
     */
    private String generateResourceIdForCondition(String noSEP, String rank) {
        String statusValue;
        if (lblStatusRawat != null && lblStatusRawat.getText() != null && lblStatusRawat.getText().contains("Ralan")) {
            statusValue = "2"; // Outpatient (Ralan)
        } else {
            statusValue = "1"; // Inpatient (Ranap)
        }
        // Use noSEP and rank as the primary identifier for condition resources
        return sanitizeString(KdPPKBPJS.getText()) + "-"
                + sanitizeString(KdPPKKemKes.getText()) + "-" + statusValue + "-"
                + processUUIDString(sanitizeString(noSEP + "-" + rank));
    }

    /**
     * Generate a medication request-specific resource ID using noResep as the
     * unique identifier
     */
    private String generateResourceIdForMedicationRequest(String noResep) {
        String statusValue;
        if (lblStatusRawat != null && lblStatusRawat.getText() != null && lblStatusRawat.getText().contains("Ralan")) {
            statusValue = "2"; // Outpatient (Ralan)
        } else {
            statusValue = "1"; // Inpatient (Ranap)
        }
        // Use noResep as the primary identifier for medication request resources
        return sanitizeString(KdPPKBPJS.getText()) + "-"
                + sanitizeString(KdPPKKemKes.getText()) + "-" + statusValue + "-"
                + processUUIDString(sanitizeString(noResep));
    }

    /**
     * Generate a procedure-specific resource ID using noSEP and result number
     * as the unique identifier
     */
    private String generateResourceIdForProcedure(String noSEP, int resultNumber) {
        String statusValue;
        if (lblStatusRawat != null && lblStatusRawat.getText() != null && lblStatusRawat.getText().contains("Ralan")) {
            statusValue = "2"; // Outpatient (Ralan)
        } else {
            statusValue = "1"; // Inpatient (Ranap)
        }
        // Use noSEP and result number as the primary identifier for procedure resources
        return sanitizeString(KdPPKBPJS.getText()) + "-"
                + sanitizeString(KdPPKKemKes.getText()) + "-" + statusValue + "-"
                + processUUIDString(sanitizeString(noSEP + "-" + resultNumber));
    }

    /**
     * Generate a practitioner-specific resource ID using kdDokter as the unique
     * identifier
     */
    private String generateResourceIdForPractitioner(String kdDokter) {
        String statusValue;
        if (lblStatusRawat != null && lblStatusRawat.getText() != null && lblStatusRawat.getText().contains("Ralan")) {
            statusValue = "2"; // Outpatient (Ralan)
        } else {
            statusValue = "1"; // Inpatient (Ranap)
        }
        // Use kdDokter as the primary identifier for practitioner resources
        return sanitizeString(KdPPKBPJS.getText()) + "-"
                + sanitizeString(KdPPKKemKes.getText()) + "-" + statusValue + "-"
                + processUUIDString(sanitizeString(kdDokter));
    }

    /**
     * Generate a composition-specific resource ID using noSEP as the unique
     * identifier
     */
    private String generateResourceIdForComposition(String noSEP) {
        String statusValue;
        if (lblStatusRawat != null && lblStatusRawat.getText() != null && lblStatusRawat.getText().contains("Ralan")) {
            statusValue = "2"; // Outpatient (Ralan)
        } else {
            statusValue = "1"; // Inpatient (Ranap)
        }
        // Use noSEP as the primary identifier for composition resources
        return sanitizeString(KdPPKBPJS.getText()) + "-"
                + sanitizeString(KdPPKKemKes.getText()) + "-" + statusValue + "-"
                + processUUIDString(sanitizeString(noSEP));
    }

    /**
     * Generate a diagnostic report-specific resource ID using noSEP and
     * category as the unique identifier
     */
    private String generateResourceIdForDiagnosticReport(String noSEP, String category) {
        String statusValue;
        if (lblStatusRawat != null && lblStatusRawat.getText() != null && lblStatusRawat.getText().contains("Ralan")) {
            statusValue = "2"; // Outpatient (Ralan)
        } else {
            statusValue = "1"; // Inpatient (Ranap)
        }
        // Use noSEP and category as the primary identifier for diagnostic report resources
        return sanitizeString(KdPPKBPJS.getText()) + "-"
                + sanitizeString(KdPPKKemKes.getText()) + "-" + statusValue + "-"
                + processUUIDString(sanitizeString(noSEP + "-" + category));
    }

    // Helper method to create coding array
    private ArrayNode createCodingArray(String system, String code, String display) {
        ArrayNode codingArray = mapper.createArrayNode();
        try {
            ObjectNode coding = mapper.createObjectNode();

            // Use simple sanitization for critical parameters
            String safeSystem = (system != null) ? sanitizeString(system) : "";
            String safeCode = (code != null) ? sanitizeString(code) : "";
            String safeDisplay = (display != null) ? sanitizeString(display) : "";

            // Add system with validation
            if (!safeSystem.isEmpty()) {
                coding.put("system", safeSystem);
            } else {
                coding.put("system", "http://example.com/unknown-system");
            }

            // Add code with validation - never leave empty
            if (!safeCode.isEmpty()) {
                coding.put("code", safeCode);
            } else {
                // Use a fallback code instead of empty
                coding.put("code", "unknown-code-" + System.currentTimeMillis());
            }

            // Add display if provided and not empty
            if (safeDisplay != null && !safeDisplay.isEmpty()) {
                coding.put("display", safeDisplay);
            }

            codingArray.add(coding);

        } catch (Exception e) {
            System.out.println("Error creating coding array: " + e.getMessage());

            // Return a minimal valid coding array
            try {
                ObjectNode fallbackCoding = mapper.createObjectNode();
                fallbackCoding.put("system", "http://example.com/fallback");
                fallbackCoding.put("code", "fallback-code");
                codingArray.add(fallbackCoding);
            } catch (Exception fallbackException) {
                System.out.println("Critical error in fallback coding creation: " + fallbackException.getMessage());
            }
        }

        return codingArray;
    }

    /**
     * Parses timing information from dosage instruction text
     *
     * @param instruction The dosage instruction text (e.g., "3 X SEHARI 1 TAB
     * SETELAH MAKAN")
     * @return String array containing [frequency, period, periodUnit]
     */
    private String[] parseTimingFromInstructions(String instruction) {
        if (instruction == null || instruction.isEmpty()) {
            return new String[]{"1", "1", "d"}; // Default: once per day
        }

        String freq = "1";
        String period = "1";
        String periodUnit = "d"; // Default to day
        String lowerInstruction = instruction.toLowerCase();

        // Handle common frequency indicators
        if (lowerInstruction.contains("x sehari") || lowerInstruction.contains("x hari")
                || lowerInstruction.contains("x/hari") || lowerInstruction.contains("xsehari")) {
            String[] parts = instruction.split("x sehari| x hari|/hari|xsehari", 2);
            if (parts.length > 0) {
                String freqPart = parts[0].trim();
                // Extract first number before 'x'
                if (freqPart.contains(" ")) {
                    String[] freqParts = freqPart.split(" ", 2);
                    freq = extractNumber(freqParts[0]);
                } else {
                    freq = extractNumber(freqPart);
                }
            }
            period = "1";
            periodUnit = "d";
        } else if (lowerInstruction.contains("x seminggu") || lowerInstruction.contains("x minggu")) {
            String[] parts = instruction.split("x seminggu| x minggu", 2);
            if (parts.length > 0) {
                String freqPart = parts[0].trim();
                if (freqPart.contains(" ")) {
                    String[] freqParts = freqPart.split(" ", 2);
                    freq = extractNumber(freqParts[0]);
                } else {
                    freq = extractNumber(freqPart);
                }
            }
            period = "1";
            periodUnit = "wk";
        } else if (lowerInstruction.contains("x sebulan") || lowerInstruction.contains("x bulan")) {
            String[] parts = instruction.split("x sebulan| x bulan", 2);
            if (parts.length > 0) {
                String freqPart = parts[0].trim();
                if (freqPart.contains(" ")) {
                    String[] freqParts = freqPart.split(" ", 2);
                    freq = extractNumber(freqParts[0]);
                } else {
                    freq = extractNumber(freqPart);
                }
            }
            period = "1";
            periodUnit = "mo";
        } else if (lowerInstruction.contains("pagi") && lowerInstruction.contains("siang") && lowerInstruction.contains("malam")) {
            freq = "3";
            period = "1";
            periodUnit = "d";
        } else if (lowerInstruction.contains("pagi") && lowerInstruction.contains("malam")) {
            freq = "2";
            period = "1";
            periodUnit = "d";
        } else if (lowerInstruction.contains("pagi") && lowerInstruction.contains("siang")) {
            freq = "2";
            period = "1";
            periodUnit = "d";
        } else if (lowerInstruction.contains("pagi")) {
            freq = "1";
            period = "1";
            periodUnit = "d";
        } else if (lowerInstruction.contains("siang")) {
            freq = "1";
            period = "1";
            periodUnit = "d";
        } else if (lowerInstruction.contains("malam")) {
            freq = "1";
            period = "1";
            periodUnit = "d";
        } else if (lowerInstruction.contains("setiap") || lowerInstruction.contains("tiap")) {
            // Handle "SETIAP X JAM", "SETIAP X HARI", etc.
            if (lowerInstruction.contains("jam")) {
                String[] parts = instruction.split("setiap|tiap", 2);
                if (parts.length > 1) {
                    String timePart = parts[1].trim();
                    String[] words = timePart.split("\\s+");
                    for (int i = 0; i < words.length; i++) {
                        String num = extractNumber(words[i]);
                        if (!num.equals("0") && !num.equals("")) {
                            period = num;
                            periodUnit = "h";
                            freq = "1";
                            break;
                        }
                    }
                }
            } else if (lowerInstruction.contains("hari")) {
                String[] parts = instruction.split("setiap|tiap", 2);
                if (parts.length > 1) {
                    String timePart = parts[1].trim();
                    String[] words = timePart.split("\\s+");
                    for (int i = 0; i < words.length; i++) {
                        String num = extractNumber(words[i]);
                        if (!num.equals("0") && !num.equals("")) {
                            period = num;
                            periodUnit = "d";
                            freq = "1";
                            break;
                        }
                    }
                }
            }
        } else if (lowerInstruction.contains("bila")) {
            // For 'bila sakit' or 'bila nyeri' - use PRN (as needed)
            freq = "0"; // As needed
            period = "1";
            periodUnit = "d";
        }

        // Ensure we have valid numbers
        try {
            Double.parseDouble(freq);
        } catch (NumberFormatException e) {
            freq = "1";
        }

        try {
            Double.parseDouble(period);
        } catch (NumberFormatException e) {
            period = "1";
        }

        // Default values if not parsed
        if (freq.equals("")) {
            freq = "1";
        }
        if (period.equals("")) {
            period = "1";
        }
        if (periodUnit.equals("")) {
            periodUnit = "d";
        }

        return new String[]{freq, period, periodUnit};
    }

    /**
     * Extracts a number (including decimals) from a string
     *
     * @param str The input string
     * @return The first number found, or empty string if none found
     */
    private String extractNumber(String str) {
        if (str == null) {
            return "";
        }

        // Find pattern like "1", "1.5", "1/2", "1 1/2", etc.
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\d+\\.?\\d*|\\d+\\/\\d+|\\d+\\s+\\d+\\/\\d+");
        java.util.regex.Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            String match = matcher.group();
            // Handle mixed fractions like "1 1/2"
            if (match.contains(" ")) {
                String[] parts = match.split("\\s+");
                if (parts.length == 2) {
                    try {
                        double whole = Double.parseDouble(parts[0]);
                        String[] fraction = parts[1].split("/");
                        if (fraction.length == 2) {
                            double num = Double.parseDouble(fraction[0]);
                            double den = Double.parseDouble(fraction[1]);
                            return String.valueOf(whole + (num / den));
                        }
                    } catch (Exception e) {
                        return parts[0]; // Return just the whole number
                    }
                }
            } // Handle simple fractions like "1/2"
            else if (match.contains("/")) {
                String[] fraction = match.split("/");
                if (fraction.length == 2) {
                    try {
                        double num = Double.parseDouble(fraction[0]);
                        double den = Double.parseDouble(fraction[1]);
                        return String.valueOf(num / den);
                    } catch (Exception e) {
                        return "0.5"; // Default for fraction if parse fails
                    }
                }
            }
            return match;
        }

        return "";
    }

    /**
     * Method to get all location data for outpatient visits
     *
     * @param noRawat The medical record number
     * @return List of JSONObjects containing outpatient location data
     */
    private List<ObjectNode> getOutpatientLocationData(String noRawat) {
        List<ObjectNode> results = new ArrayList<>();
        try {
            String query = "SELECT "
                    + "reg_periksa.no_rawat, "
                    + "reg_periksa.tgl_registrasi, "
                    + "reg_periksa.jam_reg, "
                    + "reg_periksa.status_lanjut, "
                    + "reg_periksa.kd_dokter, "
                    + "dokter.nm_dokter, "
                    + "reg_periksa.umurdaftar, "
                    + "reg_periksa.sttsumur, "
                    + "poliklinik.kd_poli, "
                    + "poliklinik.nm_poli, "
                    + "penjab.png_jawab, "
                    + "satu_sehat_mapping_lokasi_ralan.id_lokasi_satusehat "
                    + "FROM "
                    + "reg_periksa "
                    + "INNER JOIN "
                    + "dokter "
                    + "ON "
                    + "reg_periksa.kd_dokter = dokter.kd_dokter "
                    + "INNER JOIN "
                    + "poliklinik "
                    + "ON "
                    + "reg_periksa.kd_poli = poliklinik.kd_poli "
                    + "INNER JOIN "
                    + "penjab "
                    + "ON "
                    + "reg_periksa.kd_pj = penjab.kd_pj "
                    + "INNER JOIN "
                    + "satu_sehat_mapping_lokasi_ralan "
                    + "ON "
                    + "poliklinik.kd_poli = satu_sehat_mapping_lokasi_ralan.kd_poli "
                    + "WHERE "
                    + "reg_periksa.no_rawat = ?";

            PreparedStatement ps = koneksi.prepareStatement(query);
            ps.setString(1, noRawat);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ObjectNode locationData = mapper.createObjectNode();
                locationData.put("no_rawat", rs.getString("no_rawat"));
                locationData.put("tgl_registrasi", rs.getString("tgl_registrasi"));
                locationData.put("jam_reg", rs.getString("jam_reg"));
                locationData.put("nm_poli", rs.getString("nm_poli"));
                locationData.put("id_lokasi_satusehat", rs.getString("id_lokasi_satusehat"));
                results.add(locationData);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error getting outpatient location data: " + e.getMessage());
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Method to get all location data for inpatient visits
     *
     * @param noRawat The medical record number
     * @return List of JSONObjects containing inpatient location data
     */
    private List<ObjectNode> getInpatientLocationData(String noRawat) {
        List<ObjectNode> results = new ArrayList<>();
        try {
            String query = "SELECT "
                    + "kamar_inap.tgl_masuk, "
                    + "kamar_inap.jam_masuk, "
                    + "kamar_inap.kd_kamar, "
                    + "bangsal.nm_bangsal, "
                    + "satu_sehat_mapping_lokasi_ranap.id_lokasi_satusehat "
                    + "FROM "
                    + "kamar_inap "
                    + "INNER JOIN "
                    + "kamar "
                    + "ON "
                    + "kamar_inap.kd_kamar = kamar.kd_kamar "
                    + "INNER JOIN "
                    + "bangsal "
                    + "ON "
                    + "kamar.kd_bangsal = bangsal.kd_bangsal "
                    + "INNER JOIN "
                    + "satu_sehat_mapping_lokasi_ranap "
                    + "ON "
                    + "kamar.kd_kamar = satu_sehat_mapping_lokasi_ranap.kd_kamar "
                    + "WHERE "
                    + "kamar_inap.no_rawat = ?";

            PreparedStatement ps = koneksi.prepareStatement(query);
            ps.setString(1, noRawat);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ObjectNode locationData = mapper.createObjectNode();
                locationData.put("tgl_masuk", rs.getString("tgl_masuk"));
                locationData.put("jam_masuk", rs.getString("jam_masuk"));
                locationData.put("nm_bangsal", rs.getString("nm_bangsal"));
                locationData.put("id_lokasi_satusehat", rs.getString("id_lokasi_satusehat"));
                results.add(locationData);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error getting inpatient location data: " + e.getMessage());
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Helper method to add a resource to a bundle entry with proper error
     * handling This prevents the casting errors by properly handling both
     * objects and arrays
     */
    private ObjectNode addResourceToEntry(String jsonResource, String resourceType) {
        try {
            if (jsonResource == null || jsonResource.isEmpty() || jsonResource.equals("null") || jsonResource.equals("{}")) {
                return null;
            }

            JsonNode resourceNode = mapper.readTree(jsonResource);
            JsonNode resourceData = resourceNode.path("resource");

            // If no "resource" property exists at root, use the whole resource
            if (resourceData.isMissingNode() || resourceData.isNull()) {
                resourceData = resourceNode;
            }

            // Create a bundle entry
            ObjectNode entry = mapper.createObjectNode();

            // If the resource data is an array, we need to create separate entries for each item
            if (resourceData.isArray()) {
                // Return null here since we need to handle array items individually in the calling method
                return null;
            } else {
                entry.set("resource", resourceData);
                return entry;
            }
        } catch (Exception e) {
            System.out.println("Error adding " + resourceType + " to entry: " + e.getMessage());
            return null;
        }
    }

    /**
     * Helper method to process all resources in a JSON string that may contain
     * arrays
     */
    private ArrayNode processResourceArray(String jsonResource, String resourceType) {
        ArrayNode results = mapper.createArrayNode();
        try {
            if (jsonResource == null || jsonResource.isEmpty() || jsonResource.equals("null") || jsonResource.equals("{}")) {
                return results;
            }

            JsonNode resourceNode = mapper.readTree(jsonResource);
            JsonNode resourceData = resourceNode.path("resource");

            // If no "resource" property exists at root, use the whole resource
            if (resourceData.isMissingNode() || resourceData.isNull()) {
                resourceData = resourceNode;
            }

            if (resourceData.isArray()) {
                // Process each item in the array
                for (JsonNode item : resourceData) {
                    ObjectNode entry = mapper.createObjectNode();
                    entry.set("resource", item);
                    results.add(entry);
                }
            } else {
                // Single object, wrap in a single entry
                ObjectNode entry = mapper.createObjectNode();
                entry.set("resource", resourceData);
                results.add(entry);
            }

        } catch (Exception e) {
            System.out.println("Error proctaessing " + resourceType + " array: " + e.getMessage());
        }
        return results;
    }
}

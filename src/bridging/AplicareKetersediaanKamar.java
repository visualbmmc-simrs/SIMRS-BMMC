/**
 * @author M.Nurkholis
 */
package bridging;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import fungsi.koneksiDB;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;
import widget.Button;
import widget.CekBox;
import widget.ComboBox;
import widget.InternalFrame;
import widget.Label;
import widget.PanelBiasa;
import widget.ScrollPane;
import widget.Table;
import widget.TextBox;
import widget.panelisi;

public final class AplicareKetersediaanKamar extends javax.swing.JDialog {

    // ========== VARIABLES ==========
    private DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariBangsal bangsal;
    private KelasKamarAplicare kelasLokal;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private Timer autoUpdateTimer;

    // ========== POPUP MENU ==========
    private JPopupMenu jPopupMenu1;
    private javax.swing.JMenuItem MnPilihSemua;
    private javax.swing.JMenuItem MnBatalPilih;
    private javax.swing.JMenuItem MnAktifkanSemua;
    private javax.swing.JMenuItem MnNonaktifkanSemua;

    // ========== COMPONENTS ==========
    private InternalFrame internalFrame1;
    private ScrollPane Scroll;
    private Table tbJnsPerawatan;
    private JPanel jPanel3;
    private panelisi panelGlass8;
    private Button BtnSimpan;
    private Button BtnBatal;
    private Button BtnHapus;
    private Button BtnEdit;
    private Button BtnAplicare;
    private Button BtnUpdate;
    private Button BtnKeluar;
    private panelisi panelGlass9;
    private Label jLabel6;
    private TextBox TCari;
    private Button BtnCari;
    private Button BtnAll;
    private Label jLabel7;
    private Label LCount;
    private JPanel PanelInput;
    private PanelBiasa FormInput;
    private Label jLabel3;
    private TextBox TKd;
    private Label jLabel4;
    private TextBox KdKamar;
    private TextBox NmKamar;
    private Label jLabel5;
    private ComboBox Kelas;
    private Label jLabel8;
    private TextBox Kapasitas;
    private Label jLabel9;
    private TextBox Tersedia;
    private Label jLabel11;
    private TextBox TersediaPW;
    private Label jLabel10;
    private TextBox TersediaPria;
    private Label jLabel12;
    private TextBox TersediaWanita;
    private Label jLabel13;
    private TextBox KdKelas;
    private TextBox NmKelas;
    private CekBox ChkInput;
    private Button btnKelas;
    private Button btnKamar;
    private ComboBox CmbStatus;
    private Label jLabelStatus;

    public AplicareKetersediaanKamar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initCustomComponents();
        initPopupMenu();

        this.setLocation(8, 1);
        setSize(1100, 710);

        // ========== FIELD PREVIEW (READ-ONLY) ==========
        TKd.setEditable(false);
        KdKamar.setEditable(false);
        NmKamar.setEditable(false);
        Kelas.setEnabled(false);
        KdKelas.setEditable(false);
        NmKelas.setEditable(false);
        
        // ========== FIELD BISA DIEDIT ==========
        Kapasitas.setEditable(true);
        Kapasitas.setDocument(new batasInput((byte) 4).getOnlyAngka(Kapasitas));
        
        Tersedia.setEditable(true);
        Tersedia.setDocument(new batasInput((byte) 4).getOnlyAngka(Tersedia));
        
        TersediaPW.setEditable(true);
        TersediaPW.setDocument(new batasInput((byte) 4).getOnlyAngka(TersediaPW));
        
        TersediaPria.setEditable(true);
        TersediaPria.setDocument(new batasInput((byte) 4).getOnlyAngka(TersediaPria));
        
        TersediaWanita.setEditable(true);
        TersediaWanita.setDocument(new batasInput((byte) 4).getOnlyAngka(TersediaWanita));
        
        CmbStatus.setEnabled(true);

        TKd.setDocument(new batasInput((byte) 15).getKata(TKd));
        KdKamar.setDocument(new batasInput((byte) 5).getKata(KdKamar));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));

        ChkInput.setSelected(false);
        isForm();
        
        cekDataKetersediaan();
        
        // Start auto update timer untuk real-time
        startAutoUpdateTimer();
    }

    private void initComponents() {
        // ========== DIALOG ==========
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new BorderLayout());

        // ========== INTERNAL FRAME ==========
        internalFrame1 = new InternalFrame();
        internalFrame1.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(240, 245, 235)),
            "::[ Mapping Ketersediaan Kamar Aplicare BPJS ]::",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Tahoma", 0, 11),
            new Color(50, 50, 50)
        ));
        internalFrame1.setLayout(new BorderLayout(1, 1));

        // ========== SCROLL PANE & TABLE ==========
        Scroll = new ScrollPane();
        Scroll.setOpaque(true);

        tbJnsPerawatan = new Table();
        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Klik untuk preview data mapping");
        tbJnsPerawatan.setComponentPopupMenu(jPopupMenu1);
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

        internalFrame1.add(Scroll, BorderLayout.CENTER);

        // ========== BOTTOM PANEL ==========
        jPanel3 = new JPanel();
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new Dimension(44, 100));
        jPanel3.setLayout(new BorderLayout(1, 1));

        // ========== BUTTON PANEL ==========
        panelGlass8 = new panelisi();
        panelGlass8.setPreferredSize(new Dimension(44, 44));
        panelGlass8.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 9));

        BtnSimpan = new Button();
        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png")));
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S - Simpan data mapping baru");
        BtnSimpan.setPreferredSize(new Dimension(100, 30));
        BtnSimpan.addActionListener(evt -> BtnSimpanActionPerformed(evt));
        BtnSimpan.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal = new Button();
        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png")));
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B - Reset form");
        BtnBatal.setPreferredSize(new Dimension(100, 30));
        BtnBatal.addActionListener(evt -> BtnBatalActionPerformed(evt));
        BtnBatal.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus = new Button();
        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png")));
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H - Hapus data yang dicentang");
        BtnHapus.setPreferredSize(new Dimension(100, 30));
        BtnHapus.addActionListener(evt -> BtnHapusActionPerformed(evt));
        BtnHapus.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit = new Button();
        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png")));
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G - Update data mapping & status");
        BtnEdit.setPreferredSize(new Dimension(100, 30));
        BtnEdit.addActionListener(evt -> BtnEditActionPerformed(evt));
        BtnEdit.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        // ===== TOMBOL UPDATE APLICARE =====
        BtnUpdate = new Button();
        BtnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/42a.png")));
        BtnUpdate.setMnemonic('U');
        BtnUpdate.setText("Update Aplicare");
        BtnUpdate.setToolTipText("Alt+U - Update data ke tabel Aplicare (per Ruangan)");
        BtnUpdate.setPreferredSize(new Dimension(160, 30));
        BtnUpdate.addActionListener(evt -> BtnUpdateActionPerformed(evt));
        BtnUpdate.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                BtnUpdateKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnUpdate);

        BtnAplicare = new Button();
        BtnAplicare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/icon bpjs.png")));
        BtnAplicare.setMnemonic('T');
        BtnAplicare.setText("Aplicare");
        BtnAplicare.setToolTipText("Alt+T - Buka Aplicare BPJS");
        BtnAplicare.setPreferredSize(new Dimension(100, 30));
        BtnAplicare.addActionListener(evt -> BtnAplicareActionPerformed(evt));
        BtnAplicare.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                BtnAplicareKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAplicare);

        BtnKeluar = new Button();
        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setPreferredSize(new Dimension(100, 30));
        BtnKeluar.addActionListener(evt -> BtnKeluarActionPerformed(evt));
        BtnKeluar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, BorderLayout.CENTER);

        // ========== SEARCH PANEL ==========
        panelGlass9 = new panelisi();
        panelGlass9.setPreferredSize(new Dimension(44, 44));
        panelGlass9.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 9));

        jLabel6 = new Label();
        jLabel6.setText("Key Word :");
        jLabel6.setPreferredSize(new Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari = new TextBox();
        TCari.setPreferredSize(new Dimension(350, 23));
        TCari.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari = new Button();
        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3 - Cari data");
        BtnCari.setPreferredSize(new Dimension(28, 23));
        BtnCari.addActionListener(evt -> BtnCariActionPerformed(evt));
        BtnCari.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        BtnAll = new Button();
        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png")));
        BtnAll.setMnemonic('4');
        BtnAll.setToolTipText("Alt+4 - Tampilkan semua");
        BtnAll.setPreferredSize(new Dimension(28, 23));
        BtnAll.addActionListener(evt -> BtnAllActionPerformed(evt));
        BtnAll.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnAll);

        jLabel7 = new Label();
        jLabel7.setText("Record :");
        jLabel7.setPreferredSize(new Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount = new Label();
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setPreferredSize(new Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, BorderLayout.PAGE_START);
        internalFrame1.add(jPanel3, BorderLayout.PAGE_END);

        // ========== INPUT PANEL ==========
        PanelInput = new JPanel();
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new Dimension(192, 120));
        PanelInput.setLayout(new BorderLayout(1, 1));

        FormInput = new PanelBiasa();
        FormInput.setPreferredSize(new Dimension(100, 160));
        FormInput.setLayout(null);

        // ===== BARIS 1: Nomer Bed + Kamar + Kelas =====
        jLabel3 = new Label();
        jLabel3.setText("Nomer Bed :");
        jLabel3.setBounds(0, 8, 82, 23);
        FormInput.add(jLabel3);

        TKd = new TextBox();
        TKd.setHighlighter(null);
        TKd.setBounds(86, 8, 80, 23);
        FormInput.add(TKd);

        jLabel4 = new Label();
        jLabel4.setText("Kamar :");
        jLabel4.setBounds(175, 8, 55, 23);
        FormInput.add(jLabel4);

        KdKamar = new TextBox();
        KdKamar.setHighlighter(null);
        KdKamar.setBounds(234, 8, 55, 23);
        FormInput.add(KdKamar);

        NmKamar = new TextBox();
        NmKamar.setHighlighter(null);
        NmKamar.setBounds(293, 8, 160, 23);
        FormInput.add(NmKamar);

        btnKamar = new Button();
        btnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png")));
        btnKamar.setMnemonic('3');
        btnKamar.setToolTipText("Alt+3 - Pilih Kamar/Ruang");
        btnKamar.setPreferredSize(new Dimension(28, 23));
        btnKamar.addActionListener(evt -> btnKamarActionPerformed(evt));
        btnKamar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                btnKamarKeyPressed(evt);
            }
        });
        FormInput.add(btnKamar);
        btnKamar.setBounds(456, 8, 28, 23);

        jLabel5 = new Label();
        jLabel5.setText("Kelas :");
        jLabel5.setBounds(490, 8, 45, 23);
        FormInput.add(jLabel5);

        Kelas = new ComboBox();
        Kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Kelas 1", "Kelas 2", "Kelas 3", "Kelas Utama", "Kelas VIP", "Kelas VVIP"}));
        Kelas.setBounds(540, 8, 110, 23);
        FormInput.add(Kelas);

        // ===== BARIS 2: Kode BPJS + Nama BPJS + Kapasitas + Tersedia =====
        jLabel13 = new Label();
        jLabel13.setText("Kode BPJS :");
        jLabel13.setBounds(0, 38, 82, 23);
        FormInput.add(jLabel13);

        KdKelas = new TextBox();
        KdKelas.setHighlighter(null);
        KdKelas.setBounds(86, 38, 77, 23);
        FormInput.add(KdKelas);

        NmKelas = new TextBox();
        NmKelas.setHighlighter(null);
        NmKelas.setBounds(166, 38, 190, 23);
        FormInput.add(NmKelas);

        btnKelas = new Button();
        btnKelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png")));
        btnKelas.setMnemonic('1');
        btnKelas.setToolTipText("Alt+1 - Pilih Kelas Aplicare");
        btnKelas.setPreferredSize(new Dimension(28, 23));
        btnKelas.addActionListener(evt -> btnKelasActionPerformed(evt));
        btnKelas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                btnKelasKeyPressed(evt);
            }
        });
        FormInput.add(btnKelas);
        btnKelas.setBounds(359, 38, 28, 23);

        jLabel8 = new Label();
        jLabel8.setText("Kapasitas :");
        jLabel8.setBounds(395, 38, 70, 23);
        FormInput.add(jLabel8);

        Kapasitas = new TextBox();
        Kapasitas.setText("1");
        Kapasitas.setHighlighter(null);
        Kapasitas.setBounds(470, 38, 50, 23);
        Kapasitas.setEditable(true);
        FormInput.add(Kapasitas);

        jLabel9 = new Label();
        jLabel9.setText("Tersedia :");
        jLabel9.setBounds(530, 38, 65, 23);
        FormInput.add(jLabel9);

        Tersedia = new TextBox();
        Tersedia.setText("0");
        Tersedia.setHighlighter(null);
        Tersedia.setBounds(600, 38, 50, 23);
        Tersedia.setEditable(true);
        FormInput.add(Tersedia);

        // ===== BARIS 3: Tersedia Pria + Tersedia Wanita + Tersedia PW =====
        jLabel10 = new Label();
        jLabel10.setText("Tersedia Pria :");
        jLabel10.setBounds(0, 68, 82, 23);
        FormInput.add(jLabel10);

        TersediaPria = new TextBox();
        TersediaPria.setText("0");
        TersediaPria.setHighlighter(null);
        TersediaPria.setBounds(86, 68, 50, 23);
        TersediaPria.setEditable(true);
        FormInput.add(TersediaPria);

        jLabel12 = new Label();
        jLabel12.setText("Tersedia Wanita :");
        jLabel12.setBounds(145, 68, 100, 23);
        FormInput.add(jLabel12);

        TersediaWanita = new TextBox();
        TersediaWanita.setText("0");
        TersediaWanita.setHighlighter(null);
        TersediaWanita.setBounds(250, 68, 50, 23);
        TersediaWanita.setEditable(true);
        FormInput.add(TersediaWanita);

        jLabel11 = new Label();
        jLabel11.setText("Tersedia PW :");
        jLabel11.setBounds(310, 68, 82, 23);
        FormInput.add(jLabel11);

        TersediaPW = new TextBox();
        TersediaPW.setText("0");
        TersediaPW.setHighlighter(null);
        TersediaPW.setBounds(395, 68, 50, 23);
        TersediaPW.setEditable(true);
        FormInput.add(TersediaPW);

        // ===== BARIS 4: Status Aktif/Tidak Aktif =====
        jLabelStatus = new Label();
        jLabelStatus.setText("Status :");
        jLabelStatus.setBounds(470, 68, 55, 23);
        FormInput.add(jLabelStatus);

        CmbStatus = new ComboBox();
        CmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"AKTIF", "TIDAK AKTIF"}));
        CmbStatus.setBounds(530, 68, 120, 23);
        CmbStatus.setEnabled(true);
        FormInput.add(CmbStatus);

        PanelInput.add(FormInput, BorderLayout.CENTER);

        ChkInput = new CekBox();
        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Preview Data Mapping");
        ChkInput.setToolTipText("Alt+I - Toggle form input");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setPreferredSize(new Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png")));
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png")));
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png")));
        ChkInput.addActionListener(evt -> ChkInputActionPerformed(evt));
        PanelInput.add(ChkInput, BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, BorderLayout.CENTER);

        pack();
    }

    private void initCustomComponents() {
        tabMode = new DefaultTableModel(null, new String[]{
            "Pilih", "Nomer Bed", "Kode Ruang", "Nama Ruang", "Kelas", 
            "Kode BPJS", "Nama Kelas BPJS", "Kapasitas", "Tersedia", 
            "Tersedia Pria", "Tersedia Wanita", "Tersedia PW", "Status"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return colIndex == 0;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class
            };
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        
        tbJnsPerawatan.setModel(tabMode);
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        // Set lebar kolom - NOMER BED diperlebar 2x lipat (dari 100 menjadi 200)
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(1150, 480));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < tabMode.getColumnCount(); i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(50);
                column.setMinWidth(40);
                column.setMaxWidth(60);
            } else if (i == 1) {
                column.setPreferredWidth(200);
                column.setMinWidth(150);
            } else if (i == 2) {
                column.setPreferredWidth(90);
                column.setMinWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(200);
                column.setMinWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(100);
                column.setMinWidth(80);
            } else if (i == 5) {
                column.setPreferredWidth(110);
                column.setMinWidth(90);
            } else if (i == 6) {
                column.setPreferredWidth(150);
                column.setMinWidth(120);
            } else if (i == 7) {
                column.setPreferredWidth(70);
                column.setMinWidth(60);
            } else if (i == 8) {
                column.setPreferredWidth(70);
                column.setMinWidth(60);
            } else if (i == 9) {
                column.setPreferredWidth(90);
                column.setMinWidth(70);
            } else if (i == 10) {
                column.setPreferredWidth(100);
                column.setMinWidth(80);
            } else if (i == 11) {
                column.setPreferredWidth(100);
                column.setMinWidth(80);
            } else if (i == 12) {
                column.setPreferredWidth(130);
                column.setMinWidth(100);
            }
        }
        
        int totalWidth = 50 + 200 + 90 + 200 + 100 + 110 + 150 + 70 + 70 + 90 + 100 + 100 + 130;
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(totalWidth + 50, 480));
    }

    // ========== POPUP MENU ==========
    private void initPopupMenu() {
        jPopupMenu1 = new JPopupMenu();
        jPopupMenu1.setName("jPopupMenu1");

        MnPilihSemua = new javax.swing.JMenuItem();
        MnPilihSemua.setBackground(new java.awt.Color(255, 255, 254));
        MnPilihSemua.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnPilihSemua.setForeground(new java.awt.Color(50, 50, 50));
        MnPilihSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png")));
        MnPilihSemua.setText("Pilih Semua");
        MnPilihSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPilihSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPilihSemua.setName("MnPilihSemua");
        MnPilihSemua.setPreferredSize(new java.awt.Dimension(200, 28));
        MnPilihSemua.addActionListener(evt -> MnPilihSemuaActionPerformed(evt));
        jPopupMenu1.add(MnPilihSemua);

        MnBatalPilih = new javax.swing.JMenuItem();
        MnBatalPilih.setBackground(new java.awt.Color(255, 255, 254));
        MnBatalPilih.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnBatalPilih.setForeground(new java.awt.Color(50, 50, 50));
        MnBatalPilih.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png")));
        MnBatalPilih.setText("Batal Pilih");
        MnBatalPilih.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBatalPilih.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBatalPilih.setName("MnBatalPilih");
        MnBatalPilih.setPreferredSize(new java.awt.Dimension(200, 28));
        MnBatalPilih.addActionListener(evt -> MnBatalPilihActionPerformed(evt));
        jPopupMenu1.add(MnBatalPilih);
        
        jPopupMenu1.addSeparator();

        MnAktifkanSemua = new javax.swing.JMenuItem();
        MnAktifkanSemua.setBackground(new java.awt.Color(255, 255, 254));
        MnAktifkanSemua.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnAktifkanSemua.setForeground(new java.awt.Color(0, 150, 0));
        MnAktifkanSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        MnAktifkanSemua.setText("Aktifkan Semua");
        MnAktifkanSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnAktifkanSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnAktifkanSemua.setName("MnAktifkanSemua");
        MnAktifkanSemua.setPreferredSize(new java.awt.Dimension(200, 28));
        MnAktifkanSemua.addActionListener(evt -> MnAktifkanSemuaActionPerformed(evt));
        jPopupMenu1.add(MnAktifkanSemua);

        MnNonaktifkanSemua = new javax.swing.JMenuItem();
        MnNonaktifkanSemua.setBackground(new java.awt.Color(255, 255, 254));
        MnNonaktifkanSemua.setFont(new java.awt.Font("Tahoma", 0, 11));
        MnNonaktifkanSemua.setForeground(new java.awt.Color(200, 0, 0));
        MnNonaktifkanSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png")));
        MnNonaktifkanSemua.setText("Nonaktifkan Semua");
        MnNonaktifkanSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNonaktifkanSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNonaktifkanSemua.setName("MnNonaktifkanSemua");
        MnNonaktifkanSemua.setPreferredSize(new java.awt.Dimension(200, 28));
        MnNonaktifkanSemua.addActionListener(evt -> MnNonaktifkanSemuaActionPerformed(evt));
        jPopupMenu1.add(MnNonaktifkanSemua);

        tbJnsPerawatan.setComponentPopupMenu(jPopupMenu1);
        Scroll.setComponentPopupMenu(jPopupMenu1);
    }

    // ========== POPUP MENU ACTION ==========
    private void MnPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {
        for (i = 0; i < tbJnsPerawatan.getRowCount(); i++) {
            tbJnsPerawatan.setValueAt(true, i, 0);
        }
        JOptionPane.showMessageDialog(null, "Semua data telah dipilih.");
    }

    private void MnBatalPilihActionPerformed(java.awt.event.ActionEvent evt) {
        for (i = 0; i < tbJnsPerawatan.getRowCount(); i++) {
            tbJnsPerawatan.setValueAt(false, i, 0);
        }
        JOptionPane.showMessageDialog(null, "Semua pilihan telah dibatalkan.");
    }

    private void MnAktifkanSemuaActionPerformed(java.awt.event.ActionEvent evt) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Aktifkan semua data mapping yang SUDAH MAPPING?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sqlUpdate = "UPDATE mapping_aplicare_ketersediaan_kamar SET status='AKTIF'";
                PreparedStatement psUpdate = koneksi.prepareStatement(sqlUpdate);
                int updated = psUpdate.executeUpdate();
                psUpdate.close();
                
                JOptionPane.showMessageDialog(null, updated + " data berhasil diaktifkan.");
                runBackground(() -> tampilMapping());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }

    private void MnNonaktifkanSemuaActionPerformed(java.awt.event.ActionEvent evt) {
        int confirm = JOptionPane.showConfirmDialog(null,
            "Nonaktifkan semua data mapping yang SUDAH MAPPING?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sqlUpdate = "UPDATE mapping_aplicare_ketersediaan_kamar SET status='TIDAK AKTIF'";
                PreparedStatement psUpdate = koneksi.prepareStatement(sqlUpdate);
                int updated = psUpdate.executeUpdate();
                psUpdate.close();
                
                JOptionPane.showMessageDialog(null, updated + " data berhasil dinonaktifkan.");
                runBackground(() -> tampilMapping());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }

    // ========== PIN BUTTON HANDLERS ==========
    private void btnKelasActionPerformed(java.awt.event.ActionEvent evt) {
        if (kelasLokal == null || !kelasLokal.isDisplayable()) {
            kelasLokal = new KelasKamarAplicare(null, false);
            kelasLokal.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            kelasLokal.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if (kelasLokal.getTable().getSelectedRow() != -1) {
                        KdKelas.setText(kelasLokal.getTable().getValueAt(kelasLokal.getTable().getSelectedRow(), 1).toString());
                        NmKelas.setText(kelasLokal.getTable().getValueAt(kelasLokal.getTable().getSelectedRow(), 2).toString());
                    }
                    KdKamar.requestFocus();
                    kelasLokal = null;
                }
            });
            kelasLokal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            kelasLokal.setLocationRelativeTo(internalFrame1);
        }
        if (kelasLokal == null) {
            return;
        }
        if (kelasLokal.isVisible()) {
            kelasLokal.toFront();
            return;
        }
        kelasLokal.setVisible(true);
    }

    private void btnKelasKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            btnKelasActionPerformed(null);
        }
    }

    private void btnKamarActionPerformed(java.awt.event.ActionEvent evt) {
        if (bangsal == null || !bangsal.isDisplayable()) {
            bangsal = new DlgCariBangsal(null, false);
            bangsal.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            bangsal.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if (bangsal.getTable().getSelectedRow() != -1) {
                        KdKamar.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString());
                        NmKamar.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 1).toString());
                        hitungKapasitasDanTersedia();
                    }
                    KdKamar.requestFocus();
                    bangsal = null;
                }
            });
            bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            bangsal.setLocationRelativeTo(internalFrame1);
        }
        if (bangsal == null) {
            return;
        }
        if (!bangsal.isVisible()) {
            bangsal.isCek();
            bangsal.emptTeks();
        }
        if (bangsal.isVisible()) {
            bangsal.toFront();
            return;
        }
        bangsal.setVisible(true);
    }

    private void btnKamarKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            btnKamarActionPerformed(null);
        }
    }

    // ========== FUNGSI HITUNG KAPASITAS & TERSEDIA OTOMATIS ==========
    private void hitungKapasitasDanTersedia() {
        if (KdKamar.getText().trim().equals("") || Kelas.getSelectedItem() == null || TKd.getText().trim().equals("")) {
            return;
        }

        try {
            String sql = "SELECT status FROM kamar " +
                        "WHERE statusdata='1' AND kd_bangsal=? AND kd_kamar=? AND kelas=?";
            PreparedStatement psCount = koneksi.prepareStatement(sql);
            psCount.setString(1, KdKamar.getText());
            psCount.setString(2, TKd.getText());
            psCount.setString(3, Kelas.getSelectedItem().toString());
            ResultSet rsCount = psCount.executeQuery();

            if (rsCount.next()) {
                String status = rsCount.getString("status");
                int tersedia = (status.equals("KOSONG") || status.equals("DIBERSIHKAN")) ? 1 : 0;
                
                Kapasitas.setText("1");
                Tersedia.setText(String.valueOf(tersedia));
                TersediaPW.setText(String.valueOf(tersedia));
                TersediaPria.setText("0");
                TersediaWanita.setText("0");
            } else {
                Kapasitas.setText("1");
                Tersedia.setText("0");
                TersediaPW.setText("0");
                TersediaPria.setText("0");
                TersediaWanita.setText("0");
            }
            rsCount.close();
            psCount.close();

        } catch (Exception e) {
            System.out.println("Error hitung kapasitas: " + e);
            Kapasitas.setText("1");
            Tersedia.setText("0");
            TersediaPW.setText("0");
            TersediaPria.setText("0");
            TersediaWanita.setText("0");
        }
    }

    // ========== CEK DATA KETERSEDIAAN ==========
    private void cekDataKetersediaan() {
        try {
            String sqlCek = "SELECT COUNT(*) FROM mapping_aplicare_ketersediaan_kamar";
            PreparedStatement psCek = koneksi.prepareStatement(sqlCek);
            ResultSet rsCek = psCek.executeQuery();
            int count = 0;
            if (rsCek.next()) {
                count = rsCek.getInt(1);
            }
            rsCek.close();
            psCek.close();

            if (count == 0) {
                BtnUpdate.setEnabled(false);
                BtnUpdate.setToolTipText("Update tidak aktif - belum ada data mapping");
            } else {
                BtnUpdate.setEnabled(true);
                BtnUpdate.setToolTipText("Alt+U - Update data ke tabel Aplicare (per Ruangan)");
            }
        } catch (Exception e) {
            System.out.println("Error cek data: " + e);
        }
    }

    // ========== AUTO UPDATE TIMER REAL-TIME ==========
    private void startAutoUpdateTimer() {
        if (autoUpdateTimer != null) {
            autoUpdateTimer.stop();
        }
        
        autoUpdateTimer = new Timer(30000, e -> { // Update setiap 30 detik
            if (isDisplayable()) {
                // Update mapping dari tabel kamar
                updateMappingOtomatis();
                // Refresh tampilan
                runBackground(() -> tampilMapping());
            }
        });
        autoUpdateTimer.start();
        System.out.println("[REAL-TIME] Auto update timer started (30 detik).");
    }

    // ========== UPDATE MAPPING OTOMATIS (REAL-TIME) ==========
    private void updateMappingOtomatis() {
        try {
            String sqlGetAll = "SELECT kode_kelas_aplicare, kd_bangsal, kd_kamar, kelas FROM mapping_aplicare_ketersediaan_kamar";
            PreparedStatement psGet = koneksi.prepareStatement(sqlGetAll);
            ResultSet rsGet = psGet.executeQuery();

            int updated = 0;

            while (rsGet.next()) {
                String kodeKelas = rsGet.getString("kode_kelas_aplicare");
                String kdBangsal = rsGet.getString("kd_bangsal");
                String kdKamar = rsGet.getString("kd_kamar");
                String kelas = rsGet.getString("kelas");

                // Ambil status real dari tabel kamar
                String sqlHitung = "SELECT status FROM kamar " +
                                  "WHERE statusdata='1' AND kd_bangsal=? AND kd_kamar=? AND kelas=?";
                PreparedStatement psHitung = koneksi.prepareStatement(sqlHitung);
                psHitung.setString(1, kdBangsal);
                psHitung.setString(2, kdKamar);
                psHitung.setString(3, kelas);
                ResultSet rsHitung = psHitung.executeQuery();

                int kapasitas = 1; // Default per bed
                int tersedia = 0;
                
                if (rsHitung.next()) {
                    String status = rsHitung.getString("status");
                    tersedia = (status.equals("KOSONG") || status.equals("DIBERSIHKAN")) ? 1 : 0;
                }
                rsHitung.close();
                psHitung.close();

                // Update mapping
                String sqlUpdate = "UPDATE mapping_aplicare_ketersediaan_kamar SET " +
                                  "kapasitas=?, tersedia=?, " +
                                  "tersediapria=?, tersediawanita=?, tersediapriawanita=? " +
                                  "WHERE kode_kelas_aplicare=? AND kd_bangsal=? AND kd_kamar=? AND kelas=?";
                PreparedStatement psUpdate = koneksi.prepareStatement(sqlUpdate);
                psUpdate.setInt(1, kapasitas);
                psUpdate.setInt(2, tersedia);
                psUpdate.setInt(3, 0);
                psUpdate.setInt(4, 0);
                psUpdate.setInt(5, 0);
                psUpdate.setString(6, kodeKelas);
                psUpdate.setString(7, kdBangsal);
                psUpdate.setString(8, kdKamar);
                psUpdate.setString(9, kelas);
                psUpdate.executeUpdate();
                psUpdate.close();

                updated++;
            }

            rsGet.close();
            psGet.close();

            if (updated > 0) {
                System.out.println("[REAL-TIME] Updated " + updated + " data mapping.");
            }

        } catch (Exception e) {
            System.out.println("Error update mapping otomatis: " + e);
        }
    }

    // ========== REPLACE ALL KE TABEL APLICARE (PER RUANGAN) ==========
    private void updateKeTabelAplicare() throws Exception {
        // Map untuk mengelompokkan data per Ruangan
        // Key = kode_kelas_aplicare + "|" + kd_bangsal + "|" + kelas
        Map<String, GroupData> groupMap = new HashMap<>();

        // 1. Ambil semua data mapping yang AKTIF
        String sqlMapping = "SELECT kode_kelas_aplicare, kd_bangsal, kelas, kd_kamar FROM mapping_aplicare_ketersediaan_kamar WHERE status='AKTIF'";
        PreparedStatement psMapping = koneksi.prepareStatement(sqlMapping);
        ResultSet rsMapping = psMapping.executeQuery();

        while (rsMapping.next()) {
            String kodeKelas = rsMapping.getString("kode_kelas_aplicare");
            String kdBangsal = rsMapping.getString("kd_bangsal");
            String kelas = rsMapping.getString("kelas");
            String kdKamar = rsMapping.getString("kd_kamar");
            
            // Group by kode_kelas_aplicare + kd_bangsal + kelas (sesuai PRIMARY KEY)
            String key = kodeKelas + "|" + kdBangsal + "|" + kelas;
            
            GroupData data = groupMap.get(key);
            if (data == null) {
                data = new GroupData();
                data.kodeKelas = kodeKelas;
                data.kdBangsal = kdBangsal;
                data.kelas = kelas;
                data.totalKapasitas = 0;
                data.totalTersedia = 0;
                groupMap.put(key, data);
            }
            
            data.totalKapasitas += 1; // Setiap bed = 1 kapasitas
            
            // Cek status real dari tabel kamar
            String sqlStatus = "SELECT status FROM kamar WHERE statusdata='1' AND kd_bangsal=? AND kd_kamar=? AND kelas=?";
            PreparedStatement psStatus = koneksi.prepareStatement(sqlStatus);
            psStatus.setString(1, kdBangsal);
            psStatus.setString(2, kdKamar);
            psStatus.setString(3, kelas);
            ResultSet rsStatus = psStatus.executeQuery();
            
            if (rsStatus.next()) {
                String status = rsStatus.getString("status");
                if (status.equals("KOSONG") || status.equals("DIBERSIHKAN")) {
                    data.totalTersedia += 1;
                }
            }
            rsStatus.close();
            psStatus.close();
        }
        rsMapping.close();
        psMapping.close();

        // 2. Hapus semua data di tabel aplicare_ketersediaan_kamar (REPLACE ALL)
        String sqlDelete = "DELETE FROM aplicare_ketersediaan_kamar";
        PreparedStatement psDelete = koneksi.prepareStatement(sqlDelete);
        int deleted = psDelete.executeUpdate();
        psDelete.close();
        System.out.println("[REPLACE ALL] Deleted " + deleted + " old records.");

        // 3. Insert data baru dari mapping (per Ruangan)
        int totalInsert = 0;
        for (GroupData data : groupMap.values()) {
            String sqlInsert = "INSERT INTO aplicare_ketersediaan_kamar " +
                              "(kode_kelas_aplicare, kd_bangsal, kelas, kapasitas, tersedia, " +
                              "tersediapria, tersediawanita, tersediapriawanita) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement psInsert = koneksi.prepareStatement(sqlInsert);
            psInsert.setString(1, data.kodeKelas);
            psInsert.setString(2, data.kdBangsal);
            psInsert.setString(3, data.kelas);
            psInsert.setInt(4, data.totalKapasitas);
            psInsert.setInt(5, data.totalTersedia);
            psInsert.setInt(6, 0);
            psInsert.setInt(7, 0);
            psInsert.setInt(8, 0);
            psInsert.executeUpdate();
            psInsert.close();
            totalInsert++;
        }

        // 4. Tampilkan detail hasil dalam JScrollPane
        StringBuilder detail = new StringBuilder();
        detail.append("=== UPDATE APLICARE (PER RUANGAN) ===\n\n");
        detail.append("Data lama dihapus: ").append(deleted).append(" record\n");
        detail.append("Data baru diinsert: ").append(totalInsert).append(" record\n\n");
        detail.append("Detail per Ruangan:\n");
        detail.append("-----------------------------------\n");
        
        for (GroupData data : groupMap.values()) {
            String namaBangsal = Sequel.cariIsi("SELECT nm_bangsal FROM bangsal WHERE kd_bangsal=?", data.kdBangsal);
            detail.append("Ruang: ").append(namaBangsal).append(" (").append(data.kdBangsal).append(")\n");
            detail.append("  Kelas: ").append(data.kelas).append("\n");
            detail.append("  Kode BPJS: ").append(data.kodeKelas).append("\n");
            detail.append("  Kapasitas: ").append(data.totalKapasitas).append(" bed\n");
            detail.append("  Tersedia: ").append(data.totalTersedia).append(" bed\n");
            detail.append("-----------------------------------\n");
        }

        // Buat JTextArea untuk scroll
        JTextArea textArea = new JTextArea(detail.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setRows(15);
        textArea.setColumns(50);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        
        JOptionPane.showMessageDialog(null, 
            scrollPane,
            "Sukses Update Aplicare",
            JOptionPane.INFORMATION_MESSAGE);
    }

    // Inner class untuk grouping data per Ruangan
    private static class GroupData {
        String kodeKelas;
        String kdBangsal;
        String kelas;
        int totalKapasitas;
        int totalTersedia;
    }

    // ========== TAMPIL DATA MAPPING (DIKELOMPOKKAN PER KELAS) ==========
    private void tampilMapping() {
        Valid.tabelKosong(tabMode);
        try {
            String sql = 
                "SELECT " +
                "  k.kd_kamar, " +
                "  k.kd_bangsal, " +
                "  b.nm_bangsal, " +
                "  k.kelas, " +
                "  k.status as status_kamar, " +
                "  COALESCE(m.kode_kelas_aplicare, '') as kode_kelas_aplicare, " +
                "  COALESCE(ka.nama_kelas, '-') as nama_kelas_aplicare, " +
                "  COALESCE(m.kapasitas, 1) as kapasitas, " +
                "  COALESCE(m.tersedia, 0) as tersedia, " +
                "  COALESCE(m.tersediapria, 0) as tersediapria, " +
                "  COALESCE(m.tersediawanita, 0) as tersediawanita, " +
                "  COALESCE(m.tersediapriawanita, 0) as tersediapriawanita, " +
                "  COALESCE(m.status, 'BELUM MAPPING') as status_mapping " +
                "FROM kamar k " +
                "INNER JOIN bangsal b ON k.kd_bangsal = b.kd_bangsal " +
                "LEFT JOIN mapping_aplicare_ketersediaan_kamar m " +
                "  ON k.kd_kamar = m.kd_kamar " +
                "  AND k.kd_bangsal = m.kd_bangsal " +
                "  AND k.kelas = m.kelas " +
                "LEFT JOIN kelas_kamar_aplicare ka ON m.kode_kelas_aplicare = ka.kode_kelas " +
                "WHERE k.statusdata = '1' " +
                (TCari.getText().trim().equals("") ? "" : 
                 "AND (k.kd_kamar LIKE ? OR b.nm_bangsal LIKE ? OR k.kelas LIKE ? OR " +
                 "k.status LIKE ? OR m.kode_kelas_aplicare LIKE ? OR ka.nama_kelas LIKE ? OR " +
                 "m.status LIKE ?) ") +
                "ORDER BY " +
                "  CASE k.kelas " +
                "    WHEN 'Kelas 1' THEN 1 " +
                "    WHEN 'Kelas 2' THEN 2 " +
                "    WHEN 'Kelas 3' THEN 3 " +
                "    WHEN 'Kelas Utama' THEN 4 " +
                "    WHEN 'Kelas VIP' THEN 5 " +
                "    WHEN 'Kelas VVIP' THEN 6 " +
                "    ELSE 7 " +
                "  END, " +
                "  b.nm_bangsal, " +
                "  k.kd_kamar";
            
            ps = koneksi.prepareStatement(sql);
            
            try {
                int idx = 1;
                if (!TCari.getText().trim().equals("")) {
                    ps.setString(idx++, "%" + TCari.getText() + "%");
                    ps.setString(idx++, "%" + TCari.getText() + "%");
                    ps.setString(idx++, "%" + TCari.getText() + "%");
                    ps.setString(idx++, "%" + TCari.getText() + "%");
                    ps.setString(idx++, "%" + TCari.getText() + "%");
                    ps.setString(idx++, "%" + TCari.getText() + "%");
                    ps.setString(idx++, "%" + TCari.getText() + "%");
                }

                rs = ps.executeQuery();
                
                // Variable untuk tracking kelompok kelas
                String lastKelas = "";
                int totalKapasitasPerKelas = 0;
                int totalTersediaPerKelas = 0;
                
                while (rs.next()) {
                    String currentKelas = rs.getString("kelas");
                    
                    // Jika berganti kelas, tampilkan total ringkasan untuk kelas sebelumnya
                    if (!lastKelas.equals("") && !lastKelas.equals(currentKelas)) {
                        // Tampilkan total untuk kelas sebelumnya
                        tabMode.addRow(new Object[]{
                            false,
                            "TOTAL " + lastKelas,
                            "",
                            "",
                            "",
                            "",
                            "",
                            totalKapasitasPerKelas,
                            totalTersediaPerKelas,
                            "",
                            "",
                            "",
                            ""
                        });
                        // Reset counter
                        totalKapasitasPerKelas = 0;
                        totalTersediaPerKelas = 0;
                    }
                    
                    // Jika kelas baru atau baris pertama, tampilkan header kelas (BOLD) - HANYA NAMA KELAS
                    if (!currentKelas.equals(lastKelas)) {
                        // Ubah "Kelas 1" menjadi "KELAS 1", "Kelas VIP" menjadi "KELAS VIP", dll.
                        String headerKelas = "KELAS " + currentKelas.replace("Kelas ", "").toUpperCase();
                        tabMode.addRow(new Object[]{
                            false,
                            "<html><b>" + headerKelas + "</b></html>",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            ""
                        });
                    }
                    
                    // Tampilkan data bed
                    String statusMapping = rs.getString("status_mapping");
                    
                    String displayStatus;
                    if (statusMapping.equals("BELUM MAPPING")) {
                        displayStatus = "BELUM MAPPING";
                    } else if (statusMapping.equals("AKTIF")) {
                        displayStatus = "<html><font color='#00AA00'><b>AKTIF</b></font></html>";
                    } else {
                        displayStatus = "<html><font color='#FF0000'><b>TIDAK AKTIF</b></font></html>";
                    }
                    
                    int kapasitas = rs.getInt("kapasitas");
                    int tersedia = rs.getInt("tersedia");
                    
                    tabMode.addRow(new Object[]{
                        false,
                        rs.getString("kd_kamar"),
                        rs.getString("kd_bangsal"),
                        rs.getString("nm_bangsal"),
                        rs.getString("kelas"),
                        rs.getString("kode_kelas_aplicare"),
                        rs.getString("nama_kelas_aplicare"),
                        kapasitas,
                        tersedia,
                        rs.getInt("tersediapria"),
                        rs.getInt("tersediawanita"),
                        rs.getInt("tersediapriawanita"),
                        displayStatus
                    });
                    
                    // Update counter
                    totalKapasitasPerKelas += kapasitas;
                    totalTersediaPerKelas += tersedia;
                    
                    lastKelas = currentKelas;
                }
                
                // Tampilkan total untuk kelas terakhir
                if (!lastKelas.equals("")) {
                    tabMode.addRow(new Object[]{
                        false,
                        "TOTAL " + lastKelas,
                        "",
                        "",
                        "",
                        "",
                        "",
                        totalKapasitasPerKelas,
                        totalTersediaPerKelas,
                        "",
                        "",
                        "",
                        ""
                    });
                }
                
            } catch (Exception e) {
                System.out.println("Notif Mapping : " + e);
            } finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    // ========== EVENT HANDLERS (SAMA SEPERTI SEBELUMNYA, TIDAK DIUBAH) ==========
    
    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        if (KdKamar.getText().trim().equals("") || TKd.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Pilih bed terlebih dahulu dengan mengklik baris!");
            return;
        }

        String kodeKelas = KdKelas.getText().trim();
        if (kodeKelas.equals("")) {
            JOptionPane.showMessageDialog(null, "Kode Kelas Aplicare tidak boleh kosong!\n" +
                "Silakan pilih Kode BPJS terlebih dahulu.");
            return;
        }

        // Validasi Kapasitas
        int kapasitasBaru;
        try {
            kapasitasBaru = Integer.parseInt(Kapasitas.getText().trim());
            if (kapasitasBaru < 1) {
                JOptionPane.showMessageDialog(null, "Kapasitas minimal 1!");
                Kapasitas.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Kapasitas harus berupa angka!");
            Kapasitas.requestFocus();
            return;
        }

        // Validasi Tersedia
        int tersediaBaru;
        try {
            tersediaBaru = Integer.parseInt(Tersedia.getText().trim());
            if (tersediaBaru < 0) {
                JOptionPane.showMessageDialog(null, "Tersedia tidak boleh negatif!");
                Tersedia.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Tersedia harus berupa angka!");
            Tersedia.requestFocus();
            return;
        }

        try {
            String kdBangsal = KdKamar.getText();
            String kdKamar = TKd.getText();
            String kelas = Kelas.getSelectedItem().toString();
            int tersediaPria = Integer.parseInt(TersediaPria.getText().trim());
            int tersediaWanita = Integer.parseInt(TersediaWanita.getText().trim());
            int tersediaPW = Integer.parseInt(TersediaPW.getText().trim());
            String status = CmbStatus.getSelectedItem().toString();

            // Validasi tersedia tidak boleh melebihi kapasitas
            if (tersediaBaru > kapasitasBaru) {
                JOptionPane.showMessageDialog(null, "Tersedia tidak boleh melebihi Kapasitas!");
                return;
            }

            String cekSQL = "SELECT COUNT(*) FROM mapping_aplicare_ketersediaan_kamar " +
                           "WHERE kode_kelas_aplicare=? AND kd_bangsal=? AND kd_kamar=? AND kelas=?";
            PreparedStatement psCek = koneksi.prepareStatement(cekSQL);
            psCek.setString(1, kodeKelas);
            psCek.setString(2, kdBangsal);
            psCek.setString(3, kdKamar);
            psCek.setString(4, kelas);
            ResultSet rsCek = psCek.executeQuery();
            boolean exists = rsCek.next() && rsCek.getInt(1) > 0;
            rsCek.close();
            psCek.close();

            if (exists) {
                int confirm = JOptionPane.showConfirmDialog(null,
                    "Data mapping sudah ada!\n" +
                    "Bed: " + kdKamar + "\n" +
                    "Kelas: " + kelas + "\n\n" +
                    "Apakah Anda ingin mengupdate data ini?",
                    "Konfirmasi Update",
                    JOptionPane.YES_NO_OPTION);
                    
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
                
                String updateSQL = "UPDATE mapping_aplicare_ketersediaan_kamar SET " +
                                 "kapasitas=?, tersedia=?, " +
                                 "tersediapria=?, tersediawanita=?, tersediapriawanita=?, " +
                                 "status=? " +
                                 "WHERE kode_kelas_aplicare=? AND kd_bangsal=? AND kd_kamar=? AND kelas=?";
                PreparedStatement psUpdate = koneksi.prepareStatement(updateSQL);
                psUpdate.setInt(1, kapasitasBaru);
                psUpdate.setInt(2, tersediaBaru);
                psUpdate.setInt(3, tersediaPria);
                psUpdate.setInt(4, tersediaWanita);
                psUpdate.setInt(5, tersediaPW);
                psUpdate.setString(6, status);
                psUpdate.setString(7, kodeKelas);
                psUpdate.setString(8, kdBangsal);
                psUpdate.setString(9, kdKamar);
                psUpdate.setString(10, kelas);
                psUpdate.executeUpdate();
                psUpdate.close();

                JOptionPane.showMessageDialog(null, 
                    "Data mapping berhasil diupdate!\n" +
                    "Bed: " + kdKamar + "\n" +
                    "Kapasitas: " + kapasitasBaru + "\n" +
                    "Status: " + status);
            } else {
                String insertSQL = "INSERT INTO mapping_aplicare_ketersediaan_kamar " +
                                 "(kode_kelas_aplicare, kd_bangsal, kd_kamar, kelas, kapasitas, tersedia, " +
                                 "tersediapria, tersediawanita, tersediapriawanita, status) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement psInsert = koneksi.prepareStatement(insertSQL);
                psInsert.setString(1, kodeKelas);
                psInsert.setString(2, kdBangsal);
                psInsert.setString(3, kdKamar);
                psInsert.setString(4, kelas);
                psInsert.setInt(5, kapasitasBaru);
                psInsert.setInt(6, tersediaBaru);
                psInsert.setInt(7, tersediaPria);
                psInsert.setInt(8, tersediaWanita);
                psInsert.setInt(9, tersediaPW);
                psInsert.setString(10, status);
                psInsert.executeUpdate();
                psInsert.close();

                JOptionPane.showMessageDialog(null, 
                    "Data mapping berhasil disimpan!\n" +
                    "Bed: " + kdKamar + "\n" +
                    "Kapasitas: " + kapasitasBaru + "\n" +
                    "Status: " + status);
            }

            cekDataKetersediaan();
            runBackground(() -> tampilMapping());

        } catch (Exception ex) {
            System.out.println("Error simpan: " + ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, Kelas, BtnBatal);
        }
    }

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {
        emptTeks();
        TCari.setText("");
        runBackground(() -> tampilMapping());
    }

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnBatalActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
    }

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        int count = 0;
        for (i = 0; i < tbJnsPerawatan.getRowCount(); i++) {
            if (tbJnsPerawatan.getValueAt(i, 0).toString().equals("true")) {
                count++;
            }
        }
        
        if (count == 0) {
            JOptionPane.showMessageDialog(null, "Silakan centang data yang akan dihapus terlebih dahulu!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(null,
            "Hapus " + count + " data mapping yang dipilih?", 
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int deleted = 0;
            for (i = 0; i < tbJnsPerawatan.getRowCount(); i++) {
                if (tbJnsPerawatan.getValueAt(i, 0).toString().equals("true")) {
                    String statusMapping = tbJnsPerawatan.getValueAt(i, 12).toString();
                    if (statusMapping.contains("BELUM MAPPING")) {
                        continue;
                    }
                    try {
                        String deleteSQL = "DELETE FROM mapping_aplicare_ketersediaan_kamar " +
                                         "WHERE kd_bangsal=? AND kd_kamar=? AND kelas=? AND kode_kelas_aplicare=?";
                        PreparedStatement psDelete = koneksi.prepareStatement(deleteSQL);
                        psDelete.setString(1, tbJnsPerawatan.getValueAt(i, 2).toString());
                        psDelete.setString(2, tbJnsPerawatan.getValueAt(i, 1).toString());
                        psDelete.setString(3, tbJnsPerawatan.getValueAt(i, 4).toString());
                        psDelete.setString(4, tbJnsPerawatan.getValueAt(i, 5).toString());
                        psDelete.executeUpdate();
                        psDelete.close();
                        deleted++;
                    } catch (Exception ex) {
                        System.out.println("Error hapus: " + ex);
                    }
                }
            }
            JOptionPane.showMessageDialog(null, deleted + " data mapping berhasil dihapus!");
            cekDataKetersediaan();
            runBackground(() -> tampilMapping());
            emptTeks();
        }
    }

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
    }

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {
        if (KdKamar.getText().trim().equals("") || TKd.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Pilih bed terlebih dahulu dengan mengklik baris!");
            return;
        }

        // Cek apakah data sudah mapping
        String statusMapping = null;
        for (i = 0; i < tbJnsPerawatan.getRowCount(); i++) {
            if (tbJnsPerawatan.getValueAt(i, 1).toString().equals(TKd.getText())) {
                statusMapping = tbJnsPerawatan.getValueAt(i, 12).toString();
                break;
            }
        }

        if (statusMapping != null && statusMapping.contains("BELUM MAPPING")) {
            JOptionPane.showMessageDialog(null, 
                "Data belum mapping! Silakan klik 'Simpan' untuk membuat mapping baru.",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Validasi Kapasitas
        int kapasitasBaru;
        try {
            kapasitasBaru = Integer.parseInt(Kapasitas.getText().trim());
            if (kapasitasBaru < 1) {
                JOptionPane.showMessageDialog(null, "Kapasitas minimal 1!");
                Kapasitas.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Kapasitas harus berupa angka!");
            Kapasitas.requestFocus();
            return;
        }

        // Validasi Tersedia
        int tersediaBaru;
        try {
            tersediaBaru = Integer.parseInt(Tersedia.getText().trim());
            if (tersediaBaru < 0) {
                JOptionPane.showMessageDialog(null, "Tersedia tidak boleh negatif!");
                Tersedia.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Tersedia harus berupa angka!");
            Tersedia.requestFocus();
            return;
        }

        try {
            String kdBangsal = KdKamar.getText();
            String kdKamar = TKd.getText();
            String kelas = Kelas.getSelectedItem().toString();
            String kodeKelas = KdKelas.getText();
            int tersediaPria = Integer.parseInt(TersediaPria.getText().trim());
            int tersediaWanita = Integer.parseInt(TersediaWanita.getText().trim());
            int tersediaPW = Integer.parseInt(TersediaPW.getText().trim());
            String status = CmbStatus.getSelectedItem().toString();

            // Validasi tersedia tidak boleh melebihi kapasitas
            if (tersediaBaru > kapasitasBaru) {
                JOptionPane.showMessageDialog(null, "Tersedia tidak boleh melebihi Kapasitas!");
                return;
            }

            String updateSQL = "UPDATE mapping_aplicare_ketersediaan_kamar SET " +
                             "kapasitas=?, tersedia=?, " +
                             "tersediapria=?, tersediawanita=?, tersediapriawanita=?, " +
                             "status=? " +
                             "WHERE kode_kelas_aplicare=? AND kd_bangsal=? AND kd_kamar=? AND kelas=?";
            PreparedStatement psUpdate = koneksi.prepareStatement(updateSQL);
            psUpdate.setInt(1, kapasitasBaru);
            psUpdate.setInt(2, tersediaBaru);
            psUpdate.setInt(3, tersediaPria);
            psUpdate.setInt(4, tersediaWanita);
            psUpdate.setInt(5, tersediaPW);
            psUpdate.setString(6, status);
            psUpdate.setString(7, kodeKelas);
            psUpdate.setString(8, kdBangsal);
            psUpdate.setString(9, kdKamar);
            psUpdate.setString(10, kelas);
            psUpdate.executeUpdate();
            psUpdate.close();

            JOptionPane.showMessageDialog(null, 
                "Data mapping berhasil diganti!\n" +
                "Bed: " + kdKamar + "\n" +
                "Kapasitas: " + kapasitasBaru + "\n" +
                "Tersedia: " + tersediaBaru + "\n" +
                "Status: " + status);
            
            cekDataKetersediaan();
            emptTeks();
            runBackground(() -> tampilMapping());

        } catch (Exception ex) {
            System.out.println("Error edit: " + ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnUpdate);
        }
    }

    private void BtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Cek apakah ada data mapping AKTIF
            String sqlCek = "SELECT COUNT(*) FROM mapping_aplicare_ketersediaan_kamar WHERE status='AKTIF'";
            PreparedStatement psCek = koneksi.prepareStatement(sqlCek);
            ResultSet rsCek = psCek.executeQuery();
            int count = 0;
            if (rsCek.next()) {
                count = rsCek.getInt(1);
            }
            rsCek.close();
            psCek.close();

            if (count == 0) {
                JOptionPane.showMessageDialog(null,
                    "Tidak ada data mapping AKTIF!\n\n" +
                    "Silakan buat mapping terlebih dahulu dengan:\n" +
                    "1. Pilih bed dari tabel (klik baris)\n" +
                    "2. Pilih Kode BPJS\n" +
                    "3. Klik tombol 'Simpan'\n" +
                    "4. Pastikan status AKTIF",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

        int confirm = JOptionPane.showConfirmDialog(null,
    "Perbarui semua data Aplicare?\n" +
    "Data lama akan diganti dengan data terbaru.",
    "Konfirmasi",
    JOptionPane.YES_NO_OPTION);

if (confirm != JOptionPane.YES_OPTION) {
    return;
}

            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                updateKeTabelAplicare();
                runBackground(() -> tampilMapping());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            } finally {
                this.setCursor(Cursor.getDefaultCursor());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void BtnUpdateKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnUpdateActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnAplicare);
        }
    }

    private void BtnAplicareActionPerformed(java.awt.event.ActionEvent evt) {
        AplicareBpjsView view = new AplicareBpjsView(null, false);
        view.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        view.setResizable(true);

        java.awt.Point parentLocation = this.getLocation();
        java.awt.Dimension parentSize = this.getSize();
        java.awt.Dimension viewSize = view.getSize();
        
        int x = parentLocation.x + parentSize.width + 5;
        int y = parentLocation.y + parentSize.height - viewSize.height - 30;
        
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        if (x + viewSize.width > screenSize.width) {
            x = screenSize.width - viewSize.width - 10;
        }
        if (y < 0) y = 0;
        if (y + viewSize.height > screenSize.height) {
            y = screenSize.height - viewSize.height - 30;
        }
        
        view.setLocation(x, y);
        view.setVisible(true);
    }

    private void BtnAplicareKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAplicareActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnUpdate, BtnKeluar);
        }
    }

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnAplicare, TCari);
        }
    }

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbJnsPerawatan.requestFocus();
        }
    }

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {
        runBackground(() -> tampilMapping());
    }

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {
        TCari.setText("");
        runBackground(() -> tampilMapping());
    }

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCari, TKd);
        }
    }

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {
        if (tabMode.getRowCount() != 0) {
            try {
                previewData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }

    private void tbJnsPerawatanKeyPressed(java.awt.event.KeyEvent evt) {
        if (tabMode.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCari.setText("");
                TCari.requestFocus();
            } else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                int row = tbJnsPerawatan.getSelectedRow();
                if (row != -1) {
                    boolean current = (boolean) tbJnsPerawatan.getValueAt(row, 0);
                    tbJnsPerawatan.setValueAt(!current, row, 0);
                }
            }
        }
    }

    private void tbJnsPerawatanKeyReleased(java.awt.event.KeyEvent evt) {
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    previewData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {
        isForm();
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        runBackground(() -> tampilMapping());
        emptTeks();
        cekDataKetersediaan();
        
        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        runBackground(() -> tampilMapping());
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        runBackground(() -> tampilMapping());
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        runBackground(() -> tampilMapping());
                    }
                }
            });
        }
    }

    public void emptTeks() {
        TKd.setText("");
        KdKamar.setText("");
        NmKamar.setText("");
        Kapasitas.setText("1");
        Tersedia.setText("0");
        TersediaPW.setText("0");
        TersediaPria.setText("0");
        TersediaWanita.setText("0");
        KdKelas.setText("");
        NmKelas.setText("");
        Kelas.setSelectedIndex(0);
        CmbStatus.setSelectedIndex(0);
        TKd.requestFocus();
    }

    private void previewData() {
        int row = tbJnsPerawatan.getSelectedRow();
        if (row == -1) return;

        TKd.setText(tbJnsPerawatan.getValueAt(row, 1).toString());
        KdKamar.setText(tbJnsPerawatan.getValueAt(row, 2).toString());
        NmKamar.setText(tbJnsPerawatan.getValueAt(row, 3).toString());
        Kelas.setSelectedItem(tbJnsPerawatan.getValueAt(row, 4).toString());
        
        String statusMapping = tbJnsPerawatan.getValueAt(row, 12).toString();
        String kodeBPJS = tbJnsPerawatan.getValueAt(row, 5).toString();
        
        boolean isBelumMapping = statusMapping.contains("BELUM MAPPING");
        
        if (isBelumMapping) {
            KdKelas.setText("");
            NmKelas.setText("");
            CmbStatus.setSelectedIndex(0);
            CmbStatus.setEnabled(true);
            Kapasitas.setEditable(true);
            Tersedia.setEditable(true);
            TersediaPW.setEditable(true);
            TersediaPria.setEditable(true);
            TersediaWanita.setEditable(true);
            hitungKapasitasDanTersedia();
        } else {
            KdKelas.setText(kodeBPJS);
            NmKelas.setText(tbJnsPerawatan.getValueAt(row, 6).toString());
            CmbStatus.setEnabled(true);
            Kapasitas.setEditable(true);
            Tersedia.setEditable(true);
            TersediaPW.setEditable(true);
            TersediaPria.setEditable(true);
            TersediaWanita.setEditable(true);
            
            // Ambil data REAL dari tabel kamar (bukan dari mapping)
            try {
                String sqlReal = "SELECT status FROM kamar " +
                                "WHERE statusdata='1' AND kd_bangsal=? AND kd_kamar=? AND kelas=?";
                PreparedStatement psReal = koneksi.prepareStatement(sqlReal);
                psReal.setString(1, tbJnsPerawatan.getValueAt(row, 2).toString());
                psReal.setString(2, tbJnsPerawatan.getValueAt(row, 1).toString());
                psReal.setString(3, tbJnsPerawatan.getValueAt(row, 4).toString());
                ResultSet rsReal = psReal.executeQuery();
                
                if (rsReal.next()) {
                    String status = rsReal.getString("status");
                    int tersediaReal = (status.equals("KOSONG") || status.equals("DIBERSIHKAN")) ? 1 : 0;
                    
                    Kapasitas.setText("1");
                    Tersedia.setText(String.valueOf(tersediaReal));
                    TersediaPW.setText(String.valueOf(tersediaReal));
                    TersediaPria.setText("0");
                    TersediaWanita.setText("0");
                } else {
                    Kapasitas.setText("1");
                    Tersedia.setText("0");
                    TersediaPW.setText("0");
                    TersediaPria.setText("0");
                    TersediaWanita.setText("0");
                }
                rsReal.close();
                psReal.close();
            } catch (Exception e) {
                System.out.println("Error preview real data: " + e);
                Kapasitas.setText("1");
                Tersedia.setText("0");
                TersediaPW.setText("0");
                TersediaPria.setText("0");
                TersediaWanita.setText("0");
            }
            
            // Ambil status dari database
            try {
                String sqlCari = "SELECT status FROM mapping_aplicare_ketersediaan_kamar " +
                               "WHERE kode_kelas_aplicare=? AND kd_bangsal=? AND kd_kamar=? AND kelas=?";
                PreparedStatement psCari = koneksi.prepareStatement(sqlCari);
                psCari.setString(1, kodeBPJS);
                psCari.setString(2, tbJnsPerawatan.getValueAt(row, 2).toString());
                psCari.setString(3, tbJnsPerawatan.getValueAt(row, 1).toString());
                psCari.setString(4, tbJnsPerawatan.getValueAt(row, 4).toString());
                ResultSet rsCari = psCari.executeQuery();
                
                if (rsCari.next()) {
                    CmbStatus.setSelectedItem(rsCari.getString("status"));
                }
                rsCari.close();
                psCari.close();
            } catch (Exception e) {
                System.out.println("Error preview status: " + e);
            }
        }
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 140));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    private void runBackground(Runnable task) {
        if (ceksukses) {
            return;
        }
        if (executor.isShutdown() || executor.isTerminated()) {
            return;
        }
        if (!isDisplayable()) {
            return;
        }

        ceksukses = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            executor.submit(() -> {
                try {
                    task.run();
                } finally {
                    ceksukses = false;
                    SwingUtilities.invokeLater(() -> {
                        if (isDisplayable()) {
                            setCursor(Cursor.getDefaultCursor());
                        }
                    });
                }
            });
        } catch (RejectedExecutionException ex) {
            ceksukses = false;
        }
    }

    @Override
    public void dispose() {
        if (autoUpdateTimer != null) {
            autoUpdateTimer.stop();
            System.out.println("[REAL-TIME] Auto update timer stopped.");
        }
        executor.shutdownNow();
        super.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            AplicareKetersediaanKamar dialog = new AplicareKetersediaanKamar(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }
}
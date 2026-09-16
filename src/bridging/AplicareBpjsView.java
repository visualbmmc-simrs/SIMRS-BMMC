/**
 * @author M.Nurkholis
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.koneksiDB;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import widget.Button;
import widget.InternalFrame;
import widget.Label;
import widget.ScrollPane;
import widget.Table;
import widget.TextBox;
import widget.panelisi;

public final class AplicareBpjsView extends javax.swing.JDialog {

    // ========== VARIABLES ==========
    private DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private String URL = "", CONSIDAPIAPLICARE = "", kodeppk = "", utc = "";
    private String requestJson = "";
    private ApiBPJSAplicare api = new ApiBPJSAplicare();
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    // ========== WATERMARK ==========
    private WatermarkPanel watermarkPanel;

    // ========== COMPONENTS ==========
    private InternalFrame internalFrame1;
    private ScrollPane Scroll;
    private Table tbKetersediaan;
    private JPanel jPanel3;
    private panelisi panelGlass8;
    private panelisi panelGlass9;
    private Button BtnRefresh;
    private Button BtnHapus;
    private Button BtnKeluar;
    private Button BtnCari;
    private Label jLabel6;
    private Label jLabel7;
    private Label jLabel8;
    private Label jLabel10;
    private Label LCount;
    private Label LKodePPK;
    private Label LStatus;
    private TextBox TCari;

    public AplicareBpjsView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initCustomComponents();

        this.setLocation(8, 1);
        setSize(750, 674);

        // ===== SET WATERMARK =====
        setWatermark();

        try {
            URL = koneksiDB.URLAPIAPLICARE();
            CONSIDAPIAPLICARE = koneksiDB.CONSIDAPIAPLICARE();
            kodeppk = koneksiDB.KODEPPKAPLICAREBPJS();
            LKodePPK.setText(kodeppk);
        } catch (Exception e) {
            System.out.println("E : " + e);
        }

        // Auto load data saat form dibuka
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
    }

    private void initComponents() {
        // ========== DIALOG ==========
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());

        // ========== INTERNAL FRAME ==========
        internalFrame1 = new InternalFrame();
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(240, 245, 222), 1, true),
            "::[ Data Ketersediaan Kamar BPJS Aplicare ]::",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Tahoma", 0, 11)
        );
        internalFrame1.setBorder(titledBorder);
        internalFrame1.setName("internalFrame1");
        internalFrame1.setLayout(new BorderLayout());

        // ========== SCROLL PANE & TABLE ==========
        Scroll = new ScrollPane();
        Scroll.setName("Scroll");
        Scroll.setOpaque(true);

        tbKetersediaan = new Table();
        tbKetersediaan.setAutoCreateRowSorter(true);
        tbKetersediaan.setToolTipText("Silahkan klik untuk memilih data");
        tbKetersediaan.setName("tbKetersediaan");
        Scroll.setViewportView(tbKetersediaan);

        internalFrame1.add(Scroll, BorderLayout.CENTER);

        // ========== BOTTOM PANEL ==========
        jPanel3 = new JPanel();
        jPanel3.setName("jPanel3");
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new Dimension(44, 100));
        jPanel3.setLayout(new BorderLayout());

        // ========== BUTTON PANEL ==========
        panelGlass8 = new panelisi();
        panelGlass8.setName("panelGlass8");
        panelGlass8.setPreferredSize(new Dimension(44, 44));
        panelGlass8.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 9));

        // ===== TOMBOL REFRESH =====
        BtnRefresh = new Button();
        BtnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/42a.png")));
        BtnRefresh.setMnemonic('R');
        BtnRefresh.setText("Refresh");
        BtnRefresh.setToolTipText("Alt+R");
        BtnRefresh.setName("BtnRefresh");
        BtnRefresh.setPreferredSize(new Dimension(100, 30));
        BtnRefresh.addActionListener(evt -> BtnRefreshActionPerformed(evt));
        BtnRefresh.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                BtnRefreshKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnRefresh);

        // ===== TOMBOL HAPUS =====
        BtnHapus = new Button();
        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png")));
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H - Hapus data yang dipilih");
        BtnHapus.setName("BtnHapus");
        BtnHapus.setPreferredSize(new Dimension(100, 30));
        BtnHapus.addActionListener(evt -> BtnHapusActionPerformed(evt));
        BtnHapus.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        // ===== TOMBOL KELUAR =====
        BtnKeluar = new Button();
        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/101.png")));
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar");
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
        panelGlass9.setName("panelGlass9");
        panelGlass9.setPreferredSize(new Dimension(44, 44));
        panelGlass9.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 9));

        jLabel6 = new Label();
        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6");
        jLabel6.setPreferredSize(new Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari = new TextBox();
        TCari.setName("TCari");
        TCari.setPreferredSize(new Dimension(200, 23));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        TCari.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari = new Button();
        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/checked.png")));
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setName("BtnCari");
        BtnCari.setPreferredSize(new Dimension(28, 23));
        BtnCari.addActionListener(evt -> BtnCariActionPerformed(evt));
        BtnCari.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        jLabel7 = new Label();
        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7");
        jLabel7.setPreferredSize(new Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount = new Label();
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount");
        LCount.setPreferredSize(new Dimension(50, 23));
        panelGlass9.add(LCount);

        jLabel8 = new Label();
        jLabel8.setText("Kode PPK :");
        jLabel8.setName("jLabel8");
        jLabel8.setPreferredSize(new Dimension(65, 23));
        panelGlass9.add(jLabel8);

        LKodePPK = new Label();
        LKodePPK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LKodePPK.setText("-");
        LKodePPK.setName("LKodePPK");
        LKodePPK.setPreferredSize(new Dimension(100, 23));
        panelGlass9.add(LKodePPK);

        jLabel10 = new Label();
        jLabel10.setText("Status :");
        jLabel10.setName("jLabel10");
        jLabel10.setPreferredSize(new Dimension(55, 23));
        panelGlass9.add(jLabel10);

        LStatus = new Label();
        LStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LStatus.setText("-");
        LStatus.setName("LStatus");
        LStatus.setPreferredSize(new Dimension(100, 23));
        panelGlass9.add(LStatus);

        jPanel3.add(panelGlass9, BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, BorderLayout.CENTER);

        pack();
    }

    private void initCustomComponents() {
        // ===== HAPUS KOLOM KELAS =====
        Object[] row = {"No", "Kode Kelas", "Nama Kelas", "Kode Ruang", "Kamar/Ruang", 
                        "Kapasitas", "Tersedia", "Tersedia P&W", "Tersedia Pria", "Tersedia Wanita"};
        
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
            Class[] types = new Class[]{
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
        tbKetersediaan.setModel(tabMode);

        tbKetersediaan.setPreferredScrollableViewportSize(new Dimension(700, 500));
        tbKetersediaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // ===== SESUAIKAN LEBAR KOLOM =====
        for (i = 0; i < 10; i++) {
            TableColumn column = tbKetersediaan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(30);
            } else if (i == 1) {
                column.setPreferredWidth(90);
            } else if (i == 2) {
                column.setPreferredWidth(110);
            } else if (i == 3) {
                column.setPreferredWidth(80);
            } else if (i == 4) {
                column.setPreferredWidth(140);
            } else if (i == 5) {
                column.setPreferredWidth(65);
            } else if (i == 6) {
                column.setPreferredWidth(60);
            } else if (i == 7) {
                column.setPreferredWidth(100);
            } else if (i == 8) {
                column.setPreferredWidth(80);
            } else if (i == 9) {
                column.setPreferredWidth(85);
            }
        }
        tbKetersediaan.setDefaultRenderer(Object.class, new WarnaTable());
    }

    // ========== SET WATERMARK ==========
    private void setWatermark() {
        // Buat panel watermark di layer atas
        watermarkPanel = new WatermarkPanel();
        watermarkPanel.setOpaque(false);
        watermarkPanel.setLayout(new BorderLayout());
        
        // Tambahkan ke content pane sebagai glass pane
        setGlassPane(watermarkPanel);
        getGlassPane().setVisible(true);
    }

    // ========== INNER CLASS WATERMARK ==========
    private class WatermarkPanel extends JPanel {
        private Image watermarkImage;
        private float alpha = 0.15f; // Transparansi 15%

        public WatermarkPanel() {
            try {
                // Load gambar watermark
                ImageIcon icon = new ImageIcon(getClass().getResource("/picture/bpjs-amiz.png"));
                watermarkImage = icon.getImage();
            } catch (Exception e) {
                System.out.println("Error loading watermark: " + e);
                watermarkImage = null;
            }
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (watermarkImage != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Set transparansi
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                
                // Hitung posisi tengah
                int x = (getWidth() - watermarkImage.getWidth(null)) / 2;
                int y = (getHeight() - watermarkImage.getHeight(null)) / 2;
                
                // Gambar watermark di tengah
                g2d.drawImage(watermarkImage, x, y, null);
                
                g2d.dispose();
            }
        }
    }

    // ========== CEK DATA TERPILIH ==========
    private boolean cekDataTerpilih() {
        int selectedRow = tbKetersediaan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih data yang akan dihapus terlebih dahulu!");
            return false;
        }
        return true;
    }

    // ========== EVENT HANDLERS ==========
    
    private void BtnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        runBackground(() -> tampil());
    }

    private void BtnRefreshKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnRefreshActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnHapus);
        }
    }

    // ===== TOMBOL HAPUS =====
    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        // 1. Validasi data terpilih
        if (!cekDataTerpilih()) return;
        
        int selectedRow = tbKetersediaan.getSelectedRow();
        if (selectedRow == -1) return;
        
        // 2. Konfirmasi user
        int confirm = JOptionPane.showConfirmDialog(
            null, 
            "Apakah Anda yakin ingin menghapus data ini?\n" +
            "Kode Kelas: " + tbKetersediaan.getValueAt(selectedRow, 1).toString() + "\n" +
            "Kode Ruang: " + tbKetersediaan.getValueAt(selectedRow, 3).toString(),
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;
        
        // 3. Proses hapus
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        try {
            // Setup Header
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID", CONSIDAPIAPLICARE);
            utc = String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("X-Timestamp", utc);
            headers.add("X-Signature", api.getHmac(utc));
            headers.add("user_key", koneksiDB.USERKEYAPIAPLICARE());
            
            // Buat JSON
            requestJson = "{\"kodekelas\":\"" + 
                          tbKetersediaan.getValueAt(selectedRow, 1).toString() + 
                          "\", \"koderuang\":\"" + 
                          tbKetersediaan.getValueAt(selectedRow, 3).toString() + 
                          "\"}";
            requestEntity = new HttpEntity(requestJson, headers);
            
            // Kirim ke BPJS
            root = mapper.readTree(
                api.getRest().exchange(
                    URL + "/rest/bed/delete/" + kodeppk, 
                    HttpMethod.POST, 
                    requestEntity, 
                    String.class
                ).getBody()
            );
            nameNode = root.path("metadata");
            
            // Cek response
            String message = nameNode.path("message").asText();
            String code = nameNode.path("code").asText();
            
            System.out.println("Response Code: " + code);
            System.out.println("Response Message: " + message);
            
            if (message.equals("Data berhasil dihapus.") || code.equals("200")) {
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus dari server BPJS!");
                // Refresh data
                runBackground(() -> tampil());
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + message);
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
            } else {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnRefresh, BtnKeluar);
        }
    }

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnHapus, TCari);
        }
    }

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {
        runBackground(() -> tampil());
    }

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnRefresh);
        }
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        runBackground(() -> tampil());
    }

    // ========== TAMPIL DATA DARI API ==========
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID", CONSIDAPIAPLICARE);
            utc = String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("X-Timestamp", utc);
            headers.add("X-Signature", api.getHmac(utc));
            headers.add("user_key", koneksiDB.USERKEYAPIAPLICARE());
            headers.add("Accept", "application/json");

            requestEntity = new HttpEntity(headers);

            int start = 1;
            int limit = 10;
            String apiUrl = URL + "/rest/bed/read/" + kodeppk + "/" + start + "/" + limit;

            if (!TCari.getText().trim().equals("")) {
                apiUrl = apiUrl + "?keyword=" + TCari.getText().trim();
            }

            root = mapper.readTree(api.getRest().exchange(apiUrl, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metadata");
            LStatus.setText(nameNode.path("message").asText());

            JsonNode response = root.path("response");
            JsonNode list = response.path("list");

            if (list.isArray()) {
                int no = 1;
                for (JsonNode node : list) {
                    tabMode.addRow(new Object[]{
                        no++,
                        node.path("kodekelas").asText(),
                        node.path("namakelas").asText(),
                        node.path("koderuang").asText(),
                        node.path("namaruang").asText(),
                        node.path("kapasitas").asText(),
                        node.path("tersedia").asText(),
                        node.path("tersediapriawanita").asText(),
                        node.path("tersediapria").asText(),
                        node.path("tersediawanita").asText()
                    });
                }
            }
            ceksukses = true;
        } catch (Exception ex) {
            System.out.println("Notifikasi Bridging : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                LStatus.setText("Koneksi ke server BPJS terputus...!");
            } else {
                LStatus.setText("Error: " + ex.getMessage());
            }
            ceksukses = false;
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    public void runBackground(Runnable task) {
        BtnRefresh.setText("Load...");
        BtnRefresh.setEnabled(false);
        LStatus.setText("Sedang mengambil data...");
        executor.execute(() -> {
            try {
                task.run();
            } catch (Exception ex) {
                System.out.println("Notifikasi Background: " + ex);
            }
            SwingUtilities.invokeLater(() -> {
                BtnRefresh.setText("Refresh");
                BtnRefresh.setEnabled(true);
                if (ceksukses) {
                    LStatus.setText("Data berhasil dimuat");
                }
            });
        });
    }

    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }
}
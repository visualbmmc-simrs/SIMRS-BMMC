/*
 * Kontribusi Oleh Ferry Ardiansyah - RSIAP 3326051
 * Modified by M.Nurkholis
 *
 * Created on May 22, 2010, 11:58:21 AM
 * *Modified on June 2026
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public final class BPJSAntreanPerTanggal extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0, tot_belum = 0, tot_selesai = 0, tot_batal = 0, tot_non_bpjs = 0, jkn_capaian_angka = 0, mjkn_capaian_angka = 0;
    private double jkn_capaian, mjkn_capaian, jkn_belum, jkn_selesai, mjkn_belum, mjkn_selesai, sep, sep_selesai;
    private ApiMobileJKN api = new ApiMobileJKN();
    private String URL = "", link = "", utc = "";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    private List<Object[]> allTableData = new ArrayList<>();

    public BPJSAntreanPerTanggal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8, 1);
        setSize(628, 674);

        tabMode = new DefaultTableModel(null, new Object[]{
            "Kode Booking", "Tanggal", "Kode Poli", "Kode Dokter", "Jam Praktek", "NIK", "Noka", "No. HP", "RM", "Jenis Kunjungan", "No. Ref", "Sumber Data", "Peserta", "No. Antrean", "Estimasi Dilayani", "Created Time", "Status"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbJnsPerawatan.setModel(tabMode);

        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(110);
            } else if (i == 1) {
                column.setPreferredWidth(70);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(83);
            } else if (i == 4) {
                column.setPreferredWidth(90);
            } else if (i == 5) {
                column.setPreferredWidth(120);
            } else if (i == 6) {
                column.setPreferredWidth(100);
            } else if (i == 7) {
                column.setPreferredWidth(100);
            } else if (i == 8) {
                column.setPreferredWidth(60);
            } else if (i == 9) {
                column.setPreferredWidth(100);
            } else if (i == 10) {
                column.setPreferredWidth(140);
            } else if (i == 11) {
                column.setPreferredWidth(100);
            } else if (i == 12) {
                column.setPreferredWidth(70);
            } else if (i == 13) {
                column.setPreferredWidth(70);
            } else if (i == 14) {
                column.setPreferredWidth(120);
            } else if (i == 15) {
                column.setPreferredWidth(120);
            } else if (i == 16) {
                column.setPreferredWidth(90);
            }
        }

        setupTableHighlight();

        try {
            link = koneksiDB.URLAPIMOBILEJKN();
        } catch (Exception e) {
            System.out.println("E : " + e);
        }

        tambahMenuBatal();
    }

    private void tambahMenuBatal() {
        JPopupMenu popup = (JPopupMenu) tbJnsPerawatan.getComponentPopupMenu();
        if (popup != null) {
            popup.addSeparator();

            JMenuItem mnBatalDefault = new JMenuItem();
            mnBatalDefault.setText("Batal Antrean (Alasan Default)");
            mnBatalDefault.setFont(new Font("Tahoma", Font.PLAIN, 11));
            mnBatalDefault.setForeground(new java.awt.Color(50, 50, 50));
            mnBatalDefault.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png")));
            mnBatalDefault.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            mnBatalDefault.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            mnBatalDefault.setPreferredSize(new java.awt.Dimension(200, 26));
            mnBatalDefault.addActionListener(e -> batalAntreanDefault());
            popup.add(mnBatalDefault);

            JMenuItem mnBatalAlasan = new JMenuItem();
            mnBatalAlasan.setText("Batal Antrean (Isi Alasan)");
            mnBatalAlasan.setFont(new Font("Tahoma", Font.PLAIN, 11));
            mnBatalAlasan.setForeground(new java.awt.Color(50, 50, 50));
            mnBatalAlasan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png")));
            mnBatalAlasan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            mnBatalAlasan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
            mnBatalAlasan.setPreferredSize(new java.awt.Dimension(200, 26));
            mnBatalAlasan.addActionListener(e -> batalAntreanDenganAlasan());
            popup.add(mnBatalAlasan);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCekKodeBooking = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();
        jPanel2 = new javax.swing.JPanel();
        panelGlass9 = new widget.panelisi();
        jLabel12 = new widget.Label();
        MJknBelum = new widget.Label();
        MJknCapaian = new widget.Label();
        jLabel13 = new widget.Label();
        MJknSelesai = new widget.Label();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        BtnCari = new widget.Button();
        filterCerdas = new javax.swing.JComboBox();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar1 = new widget.Button();
        panelGlass8 = new widget.panelisi();
        jLabel8 = new widget.Label();
        TotBelum = new widget.Label();
        jLabel9 = new widget.Label();
        TotSelesai = new widget.Label();
        jLabel14 = new widget.Label();
        SEPTerbit = new widget.Label();
        jLabel10 = new widget.Label();
        JknBelum = new widget.Label();
        jLabel11 = new widget.Label();
        JknSelesai = new widget.Label();
        JknCapaian = new widget.Label();
        LNonBpjsLabel = new widget.Label();
        LNonBpjsValue = new widget.Label();
        LPasienCapaianLabel = new widget.Label();
        LPasienCapaianValue = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCekKodeBooking.setBackground(new java.awt.Color(255, 255, 254));
        MnCekKodeBooking.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCekKodeBooking.setForeground(new java.awt.Color(50, 50, 50));
        MnCekKodeBooking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCekKodeBooking.setText("Cek Kode Booking");
        MnCekKodeBooking.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCekKodeBooking.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCekKodeBooking.setName("MnCekKodeBooking"); // NOI18N
        MnCekKodeBooking.setPreferredSize(new java.awt.Dimension(160, 26));
        MnCekKodeBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCekKodeBookingActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCekKodeBooking);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Antrean Per Tanggal Mobile JKN ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setComponentPopupMenu(jPopupMenu1);
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        Scroll.setViewportView(tbJnsPerawatan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel12.setForeground(new java.awt.Color(0, 153, 0));
        jLabel12.setText("Mobile JKN Belum :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(115, 23));
        panelGlass9.add(jLabel12);

        MJknBelum.setForeground(new java.awt.Color(0, 153, 0));
        MJknBelum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MJknBelum.setText("0");
        MJknBelum.setName("MJknBelum"); // NOI18N
        MJknBelum.setPreferredSize(new java.awt.Dimension(35, 23));
        panelGlass9.add(MJknBelum);

        MJknCapaian.setForeground(new java.awt.Color(0, 153, 0));
        MJknCapaian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MJknCapaian.setText("0");
        MJknCapaian.setName("MJknCapaian"); // NOI18N
        MJknCapaian.setPreferredSize(new java.awt.Dimension(45, 23));
        panelGlass9.add(MJknCapaian);

        jLabel13.setForeground(new java.awt.Color(0, 153, 0));
        jLabel13.setText("Mobile JKN Selesai :");
        jLabel13.setName("jLabel13"); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass9.add(jLabel13);

        MJknSelesai.setForeground(new java.awt.Color(0, 153, 0));
        MJknSelesai.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MJknSelesai.setText("0");
        MJknSelesai.setName("MJknSelesai"); // NOI18N
        MJknSelesai.setPreferredSize(new java.awt.Dimension(35, 23));
        panelGlass9.add(MJknSelesai);

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-06-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-06-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        filterCerdas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "🔍 Semua Pasien", "🏥 BPJS", "👤 Non-BPJS", "✅ Selesai", "⏳ Belum Selesai", "❌ Batal" }));
        filterCerdas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        filterCerdas.setName("filterCerdas"); // NOI18N
        filterCerdas.setPreferredSize(new java.awt.Dimension(160, 25));
        filterCerdas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterCerdasActionPerformed(evt);
            }
        });
        panelGlass9.add(filterCerdas);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        BtnKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar1.setMnemonic('K');
        BtnKeluar1.setText("Keluar");
        BtnKeluar1.setToolTipText("Alt+K");
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(90, 30));
        BtnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar1ActionPerformed(evt);
            }
        });
        BtnKeluar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar1KeyPressed(evt);
            }
        });
        panelGlass9.add(BtnKeluar1);

        jPanel2.add(panelGlass9, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel8.setForeground(new java.awt.Color(204, 51, 0));
        jLabel8.setText("Total Belum :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel8);

        TotBelum.setForeground(new java.awt.Color(204, 51, 0));
        TotBelum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TotBelum.setText("0");
        TotBelum.setName("TotBelum"); // NOI18N
        TotBelum.setPreferredSize(new java.awt.Dimension(35, 23));
        panelGlass8.add(TotBelum);

        jLabel9.setForeground(new java.awt.Color(0, 102, 0));
        jLabel9.setText("Total Selesai :");
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass8.add(jLabel9);

        TotSelesai.setForeground(new java.awt.Color(0, 102, 0));
        TotSelesai.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TotSelesai.setText("0");
        TotSelesai.setName("TotSelesai"); // NOI18N
        TotSelesai.setPreferredSize(new java.awt.Dimension(35, 23));
        panelGlass8.add(TotSelesai);

        jLabel14.setForeground(new java.awt.Color(0, 0, 204));
        jLabel14.setText("SEP Terbit :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass8.add(jLabel14);

        SEPTerbit.setForeground(new java.awt.Color(0, 0, 204));
        SEPTerbit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SEPTerbit.setText("0");
        SEPTerbit.setName("SEPTerbit"); // NOI18N
        SEPTerbit.setPreferredSize(new java.awt.Dimension(35, 23));
        panelGlass8.add(SEPTerbit);

        jLabel10.setForeground(new java.awt.Color(0, 153, 153));
        jLabel10.setText("BPJS Onsite Belum :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(115, 23));
        panelGlass8.add(jLabel10);

        JknBelum.setForeground(new java.awt.Color(0, 153, 153));
        JknBelum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        JknBelum.setText("0");
        JknBelum.setName("JknBelum"); // NOI18N
        JknBelum.setPreferredSize(new java.awt.Dimension(35, 23));
        panelGlass8.add(JknBelum);

        jLabel11.setForeground(new java.awt.Color(0, 153, 153));
        jLabel11.setText("BPJS Onsite Selesai :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass8.add(jLabel11);

        JknSelesai.setForeground(new java.awt.Color(0, 153, 153));
        JknSelesai.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        JknSelesai.setText("0");
        JknSelesai.setName("JknSelesai"); // NOI18N
        JknSelesai.setPreferredSize(new java.awt.Dimension(35, 23));
        panelGlass8.add(JknSelesai);

        JknCapaian.setForeground(new java.awt.Color(0, 153, 153));
        JknCapaian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        JknCapaian.setText("0 ");
        JknCapaian.setName("JknCapaian"); // NOI18N
        JknCapaian.setPreferredSize(new java.awt.Dimension(45, 23));
        panelGlass8.add(JknCapaian);

        LNonBpjsLabel.setForeground(new java.awt.Color(52, 52, 46));
        LNonBpjsLabel.setText("NON BPJS :");
        LNonBpjsLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LNonBpjsLabel.setName("LNonBpjsLabel"); // NOI18N
        LNonBpjsLabel.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass8.add(LNonBpjsLabel);

        LNonBpjsValue.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        LNonBpjsValue.setForeground(new java.awt.Color(127, 140, 127));
        LNonBpjsValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LNonBpjsValue.setText("0 pasien");
        LNonBpjsValue.setName("LNonBpjsValue"); // NOI18N
        LNonBpjsValue.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(LNonBpjsValue);

        LPasienCapaianLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        LPasienCapaianLabel.setForeground(new java.awt.Color(52, 52, 46));
        LPasienCapaianLabel.setText("👥 ANTROL ALL SUMBER :");
        LPasienCapaianLabel.setName("LPasienCapaianLabel"); // NOI18N
        LPasienCapaianLabel.setPreferredSize(new java.awt.Dimension(175, 23));
        panelGlass8.add(LPasienCapaianLabel);

        LPasienCapaianValue.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        LPasienCapaianValue.setForeground(new java.awt.Color(96, 174, 39));
        LPasienCapaianValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPasienCapaianValue.setText("0% (0/0)");
        LPasienCapaianValue.setToolTipText("Persentase capaian layanan: SEP Selesai Dilayani (Onsite+MJKN) dibagi Total SEP Tercetak");
        LPasienCapaianValue.setName("LPasienCapaianValue"); // NOI18N
        LPasienCapaianValue.setPreferredSize(new java.awt.Dimension(130, 23));
        panelGlass8.add(LPasienCapaianValue);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        emptTeks();
        runBackground(() -> tampil());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluar1ActionPerformed

    private void BtnKeluar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        }
    }//GEN-LAST:event_BtnKeluar1KeyPressed

    private void MnCekKodeBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCekKodeBookingActionPerformed
        if (tbJnsPerawatan.getSelectedRow() != -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BPJSCekKodeBooking detail = new BPJSCekKodeBooking(null, false);
            detail.tampil(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 0).toString());
            detail.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            detail.setLocationRelativeTo(internalFrame1);
            detail.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data yang mau dicek...!!!!");
            tbJnsPerawatan.requestFocus();
        }
    }//GEN-LAST:event_MnCekKodeBookingActionPerformed

    private void filterCerdasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterCerdasActionPerformed
        applyFilterCerdas();
    }//GEN-LAST:event_filterCerdasActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSAntreanPerTanggal dialog = new BPJSAntreanPerTanggal(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    private void emptTeks() {
        SEPTerbit.setText("");
        TotBelum.setText("");
        TotSelesai.setText("");
        JknBelum.setText("");
        JknSelesai.setText("");
        MJknBelum.setText("");
        MJknSelesai.setText("");
        JknCapaian.setText("");
        MJknCapaian.setText("");
        LPasienCapaianValue.setText("0% (0/0)");
        LNonBpjsValue.setText("0 pasien");

        sep = 0;
        sep_selesai = 0;
        tot_belum = 0;
        tot_selesai = 0;
        tot_batal = 0;
        tot_non_bpjs = 0;
        jkn_belum = 0;
        jkn_selesai = 0;
        mjkn_belum = 0;
        mjkn_selesai = 0;
        jkn_capaian = 0;
        mjkn_capaian = 0;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnCari;
    private widget.Button BtnKeluar1;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Label JknBelum;
    private widget.Label JknCapaian;
    private widget.Label JknSelesai;
    private widget.Label LCount;
    private widget.Label LNonBpjsLabel;
    private widget.Label LNonBpjsValue;
    private widget.Label LPasienCapaianLabel;
    private widget.Label LPasienCapaianValue;
    private widget.Label MJknBelum;
    private widget.Label MJknCapaian;
    private widget.Label MJknSelesai;
    private javax.swing.JMenuItem MnCekKodeBooking;
    private widget.Label SEPTerbit;
    private widget.ScrollPane Scroll;
    private widget.Label TotBelum;
    private widget.Label TotSelesai;
    private javax.swing.JComboBox filterCerdas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        // Simpan filter terakhir sebelum load data baru
        String currentFilter = null;
        if (filterCerdas != null && filterCerdas.getSelectedItem() != null) {
            currentFilter = filterCerdas.getSelectedItem().toString();
        }

        Valid.tabelKosong(tabMode);
        allTableData.clear();
        tot_non_bpjs = 0;

        try {
            ps = koneksi.prepareStatement(
                    "SELECT reg_periksa.tgl_registrasi FROM reg_periksa WHERE reg_periksa.tgl_registrasi BETWEEN ? AND ? group by reg_periksa.tgl_registrasi");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                rs = ps.executeQuery();
                while (rs.next()) {
                    try {
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("x-cons-id", koneksiDB.CONSIDAPIMOBILEJKN());
                        utc = String.valueOf(api.GetUTCdatetimeAsString());
                        headers.add("x-timestamp", utc);
                        headers.add("x-signature", api.getHmac(utc));
                        headers.add("user_key", koneksiDB.USERKEYAPIMOBILEJKN());
                        requestEntity = new HttpEntity(headers);
                        URL = link + "/antrean/pendaftaran/tanggal/" + rs.getString("tgl_registrasi");
                        System.out.println("URL : " + URL);
                        root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                        nameNode = root.path("metadata");
                        if (nameNode.path("code").asText().equals("200")) {
                            response = mapper.readTree(api.Decrypt(root.path("response").asText(), utc));
                            if (response.isArray()) {
                                for (JsonNode list : response) {
                                    boolean isPeserta = list.path("ispeserta").asBoolean();
                                    Object[] rowData = new Object[]{
                                        list.path("kodebooking").asText(), list.path("tanggal").asText(), list.path("kodepoli").asText(),
                                        list.path("kodedokter").asText(), list.path("jampraktek").asText(), list.path("nik").asText(),
                                        list.path("nokapst").asText(), list.path("nohp").asText(), list.path("norekammedis").asText(),
                                        list.path("jeniskunjungan").asText(), list.path("nomorreferensi").asText(),
                                        list.path("sumberdata").asText(), isPeserta ? "Ya" : "Tidak",
                                        list.path("noantrean").asText(), list.path("estimasidilayani").asText(),
                                        list.path("createdtime").asText(), list.path("status").asText()
                                    };

                                    allTableData.add(rowData);

                                    String statusAntrean = list.path("status").asText();
                                    String sumberData = list.path("sumberdata").asText();

                                    if (!isPeserta) {
                                        tot_non_bpjs += 1;
                                    }

                                    if (statusAntrean.equals("Belum dilayani")) {
                                        tot_belum += 1;
                                        if (isPeserta) {
                                            if (sumberData.equals("Bridging Antrean")) {
                                                jkn_belum += 1;
                                            }
                                            if (sumberData.equals("Mobile JKN")) {
                                                mjkn_belum += 1;
                                            }
                                        }
                                    } else if (statusAntrean.equals("Selesai dilayani")) {
                                        tot_selesai += 1;
                                        if (isPeserta) {
                                            sep_selesai += 1;
                                            if (sumberData.equals("Bridging Antrean")) {
                                                jkn_selesai += 1;
                                            }
                                            if (sumberData.equals("Mobile JKN")) {
                                                mjkn_selesai += 1;
                                            }
                                        }
                                    } else if (statusAntrean.equals("Batal")) {
                                        tot_batal += 1;
                                    }
                                }
                            }
                        } else {
                            System.out.println("Notif : " + nameNode.path("message").asText());
                        }
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : " + ex);
                        if (ex.toString().contains("UnknownHostException")) {
                            JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
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

        sep = Sequel.cariInteger("select count(bridging_sep.no_rawat) from bridging_sep where bridging_sep.tglsep between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bridging_sep.jnspelayanan = '2' and bridging_sep.kdpolitujuan <> 'IGD'")
                + Sequel.cariInteger("select count(bridging_sep_internal.no_rawat) from bridging_sep_internal where bridging_sep_internal.tglsep between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bridging_sep_internal.jnspelayanan = '2' and bridging_sep_internal.kdpolitujuan <> 'IGD'");

        if (sep > 0) {
            jkn_capaian = (jkn_selesai / sep) * 100;
            mjkn_capaian = (mjkn_selesai / sep) * 100;
        } else {
            jkn_capaian = 0;
            mjkn_capaian = 0;
        }

        jkn_capaian_angka = (int) jkn_capaian;
        mjkn_capaian_angka = (int) mjkn_capaian;

        // Kembalikan filter ke pilihan terakhir (atau "Semua Pasien" jika null)
        if (filterCerdas != null) {
            if (currentFilter != null) {
                filterCerdas.setSelectedItem(currentFilter);
            } else {
                filterCerdas.setSelectedIndex(0);
            }
            // Panggil applyFilterCerdas() untuk memasukkan data ke tabel & dashboard sesuai filter
            applyFilterCerdas();
        }
    }

     private void setupTableHighlight() {
        tbJnsPerawatan.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);

                if (!isSelected) {
                    try {
                        String peserta = (String) table.getValueAt(row, 12);
                        String status = (String) table.getValueAt(row, 16);

                        boolean isNonBpjs = (peserta != null && peserta.equalsIgnoreCase("Tidak"));

                        if (status != null) {
                            if (status.equalsIgnoreCase("Batal")) {
                                // Semua batal: Merah sangat tipis
                                c.setBackground(new java.awt.Color(255, 200, 200, 55));
                            } else if (status.equalsIgnoreCase("Belum dilayani")) {
                                // Semua belum selesai: Kuning tipis
                                c.setBackground(new java.awt.Color(255, 252, 175, 50));
                            } else if (status.equalsIgnoreCase("Selesai dilayani")) {
                                if (isNonBpjs) {
                                    // Pasien Umum/Non-BPJS Selesai: Biru dongker sangat tipis
                                    c.setBackground(new java.awt.Color(20, 30, 120, 30));
                                } else {
                                    // Pasien BPJS Selesai: Hijau khas BPJS sangat tipis
                                    c.setBackground(new java.awt.Color(0, 104, 55, 30));
                                }
                            } else {
                                // Status lain (default)
                                c.setBackground(isNonBpjs ? new java.awt.Color(240, 240, 255) : java.awt.Color.WHITE);
                            }
                        } else {
                            c.setBackground(isNonBpjs ? new java.awt.Color(240, 240, 255) : java.awt.Color.WHITE);
                        }
                    } catch (Exception e) {
                        c.setBackground(java.awt.Color.WHITE);
                    }
                }
                return c;
            }
        });
    }

    private void applyFilterCerdas() {
        if (filterCerdas == null) {
            return;
        }

        String selected = (filterCerdas.getSelectedItem() != null) ? filterCerdas.getSelectedItem().toString() : "🔍 Semua Pasien";
        Valid.tabelKosong(tabMode);

        for (Object[] rowData : allTableData) {
            if (rowData == null) {
                continue;
            }

            // Validasi null untuk setiap kolom penting
            String peserta = (rowData[12] != null) ? rowData[12].toString() : "";
            String status = (rowData[16] != null) ? rowData[16].toString() : "";

            if (selected.equals("🔍 Semua Pasien")) {
                tabMode.addRow(rowData);
            } else if (selected.equals("🏥 BPJS")) {
                if (peserta.equalsIgnoreCase("Ya")) {
                    tabMode.addRow(rowData);
                }
            } else if (selected.equals("👤 Non-BPJS")) {
                if (peserta.equalsIgnoreCase("Tidak")) {
                    tabMode.addRow(rowData);
                }
            } else if (selected.equals("✅ Selesai")) {
                if (status.equalsIgnoreCase("Selesai dilayani")) {
                    tabMode.addRow(rowData);
                }
            } else if (selected.equals("⏳ Belum Selesai")) {
                if (status.equalsIgnoreCase("Belum dilayani")) {
                    tabMode.addRow(rowData);
                }
            } else if (selected.equals("❌ Batal")) {
                if (status.equalsIgnoreCase("Batal")) {
                    tabMode.addRow(rowData);
                }
            }
        }

        LCount.setText("" + tabMode.getRowCount());

        // Panggil method hitung ulang dashboard dari tabel
        hitungUlangDashboardDariTabel();
    }

    private void hitungUlangDashboardDariTabel() {
        int f_tot_belum = 0, f_tot_selesai = 0, f_tot_batal = 0, f_tot_non_bpjs = 0;
        double f_jkn_belum = 0, f_jkn_selesai = 0, f_mjkn_belum = 0, f_mjkn_selesai = 0, f_sep_selesai = 0;

        // Mengambil jumlah SEP asli dari DB (tidak terpengaruh filter, kecuali filter Non-BPJS)
        String selected = (filterCerdas.getSelectedItem() != null) ? filterCerdas.getSelectedItem().toString() : "🔍 Semua Pasien";
        double f_sep = 0;
        boolean tampilkanBPJS = (!selected.equals("👤 Non-BPJS") && !selected.equals("❌ Batal"));

        if (tampilkanBPJS) {
            f_sep = Sequel.cariInteger("select count(bridging_sep.no_rawat) from bridging_sep where bridging_sep.tglsep between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bridging_sep.jnspelayanan = '2' and bridging_sep.kdpolitujuan <> 'IGD'")
                    + Sequel.cariInteger("select count(bridging_sep_internal.no_rawat) from bridging_sep_internal where bridging_sep_internal.tglsep between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bridging_sep_internal.jnspelayanan = '2' and bridging_sep_internal.kdpolitujuan <> 'IGD'");
        }

        for (int i = 0; i < tabMode.getRowCount(); i++) {
            Object valPeserta = tabMode.getValueAt(i, 12);
            Object valSumber = tabMode.getValueAt(i, 11);
            Object valStatus = tabMode.getValueAt(i, 16);

            String isPesertaStr = (valPeserta != null) ? valPeserta.toString() : "";
            String sumberData = (valSumber != null) ? valSumber.toString() : "";
            String statusAntrean = (valStatus != null) ? valStatus.toString() : "";

            boolean isPeserta = isPesertaStr.equalsIgnoreCase("Ya");

            if (!isPeserta) {
                f_tot_non_bpjs += 1;
            }

            if (statusAntrean.equals("Belum dilayani")) {
                f_tot_belum += 1;
                if (isPeserta) {
                    if (sumberData.equals("Bridging Antrean")) {
                        f_jkn_belum += 1;
                    }
                    if (sumberData.equals("Mobile JKN")) {
                        f_mjkn_belum += 1;
                    }
                }
            } else if (statusAntrean.equals("Selesai dilayani")) {
                f_tot_selesai += 1;
                if (isPeserta) {
                    f_sep_selesai += 1;
                    if (sumberData.equals("Bridging Antrean")) {
                        f_jkn_selesai += 1;
                    }
                    if (sumberData.equals("Mobile JKN")) {
                        f_mjkn_selesai += 1;
                    }
                }
            } else if (statusAntrean.equals("Batal")) {
                f_tot_batal += 1;
            }
        }

        double f_jkn_capaian = 0, f_mjkn_capaian = 0;
        if (f_sep > 0) {
            f_jkn_capaian = (f_jkn_selesai / f_sep) * 100;
            f_mjkn_capaian = (f_mjkn_selesai / f_sep) * 100;
        }

        // Update UI Dashboard
        SEPTerbit.setText("" + (int) f_sep);
        TotBelum.setText("" + f_tot_belum);
        TotSelesai.setText("" + f_tot_selesai);
        JknBelum.setText("" + (int) f_jkn_belum);
        JknSelesai.setText("" + (int) f_jkn_selesai);
        JknCapaian.setText("(" + (int) f_jkn_capaian + "%)");
        MJknBelum.setText("" + (int) f_mjkn_belum);
        MJknSelesai.setText("" + (int) f_mjkn_selesai);
        MJknCapaian.setText("(" + (int) f_mjkn_capaian + "%)");

        LNonBpjsValue.setText(f_tot_non_bpjs + " pasien");

        double persen = 0;
        if (f_sep > 0) {
            persen = (f_sep_selesai / f_sep) * 100;
        }

        if (LPasienCapaianValue != null) {
            LPasienCapaianValue.setText(String.format("%.1f%% (%d/%d)", persen, (int) f_sep_selesai, (int) f_sep));
            if (persen >= 80) {
                LPasienCapaianValue.setForeground(new java.awt.Color(39, 174, 96));
            } else if (persen >= 50) {
                LPasienCapaianValue.setForeground(new java.awt.Color(241, 196, 15));
            } else {
                LPasienCapaianValue.setForeground(new java.awt.Color(231, 76, 60));
            }
        }
    }

    private void simpanDataBatalKeDatabase(int row, String keterangan) {
        if (row < 0) {
            return;
        }

        try {
            Object valRM = tabMode.getValueAt(row, 8);
            Object valRef = tabMode.getValueAt(row, 10);
            Object valBooking = tabMode.getValueAt(row, 0);

            String no_rkm_medis = (valRM != null) ? valRM.toString() : "";
            String nomorreferensi = (valRef != null) ? valRef.toString() : "";
            String nobooking = (valBooking != null) ? valBooking.toString() : "";
            String tanggalbatal = Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " " + new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date());

            // Cek apakah nomor referensi sudah pernah dibatalkan sebelumnya
            int cekExist = Sequel.cariInteger("SELECT COUNT(nomorreferensi) FROM referensi_mobilejkn_bpjs_batal WHERE nomorreferensi='" + nomorreferensi + "'");
            if (cekExist > 0) {
                System.out.println("Data batal untuk nomor referensi " + nomorreferensi + " sudah ada di database.");
                return;
            }

            String sql = "INSERT INTO referensi_mobilejkn_bpjs_batal (no_rkm_medis, no_rawat_batal, nomorreferensi, tanggalbatal, keterangan, statuskirim, nobooking) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = koneksi.prepareStatement(sql);
            ps.setString(1, no_rkm_medis);
            ps.setString(2, null); // no_rawat_batal dikosongkan/nullable
            ps.setString(3, nomorreferensi);
            ps.setString(4, tanggalbatal);
            ps.setString(5, keterangan);
            ps.setString(6, "Belum"); // Default 'Belum'
            ps.setString(7, nobooking);

            ps.executeUpdate();
            ps.close();

            System.out.println("Data batal berhasil disimpan ke database lokal.");
        } catch (Exception e) {
            System.out.println("Gagal menyimpan data batal ke database: " + e.getMessage());
        }
    }

    private void runBackground(Runnable task) {
        if (ceksukses) {
            return;
        }
        ceksukses = true;

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

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
    }

    private boolean batalAntreanKeBPJS(String kodebooking, String keterangan) {
        boolean berhasil = false;
        try {
            HttpHeaders headersReq = new HttpHeaders();
            headersReq.setContentType(MediaType.APPLICATION_JSON);
            headersReq.add("x-cons-id", koneksiDB.CONSIDAPIMOBILEJKN());

            String utcReq = String.valueOf(api.GetUTCdatetimeAsString());
            headersReq.add("x-timestamp", utcReq);
            headersReq.add("x-signature", api.getHmac(utcReq));
            headersReq.add("user_key", koneksiDB.USERKEYAPIMOBILEJKN());

            String jsonBody = "{\"kodebooking\":\"" + kodebooking + "\",\"keterangan\":\"" + keterangan + "\"}";
            HttpEntity<String> requestEntityReq = new HttpEntity<>(jsonBody, headersReq);

            String urlBatal = link + "/antrean/batal";
            System.out.println("URL Batal: " + urlBatal);
            System.out.println("Kode Booking: " + kodebooking);
            System.out.println("Alasan: " + keterangan);

            String responseStr = api.getRest().exchange(urlBatal, HttpMethod.POST, requestEntityReq, String.class).getBody();
            System.out.println("Response: " + responseStr);

            ObjectMapper mapperResp = new ObjectMapper();
            JsonNode rootResp = mapperResp.readTree(responseStr);
            JsonNode metadata = rootResp.path("metadata");

            int code = metadata.path("code").asInt();
            String message = metadata.path("message").asText();

            if (code == 200) {
                berhasil = true;
            } else {
                JOptionPane.showMessageDialog(this, "Gagal membatalkan antrean!\n" + message, "Gagal", JOptionPane.ERROR_MESSAGE);
                berhasil = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            berhasil = false;
        }
        return berhasil;
    }

    private void batalAntreanDefault() {
        int row = tbJnsPerawatan.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data antrean yang akan dibatalkan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String kodebooking = (tbJnsPerawatan.getValueAt(row, 0) != null) ? tbJnsPerawatan.getValueAt(row, 0).toString() : "";
        String status = (tbJnsPerawatan.getValueAt(row, 16) != null) ? tbJnsPerawatan.getValueAt(row, 16).toString() : "";

        if (!"Belum dilayani".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "Status '" + status + "' tidak bisa dibatalkan!\nHanya antrean dengan status 'Belum dilayani' yang dapat dibatalkan.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin akan membatalkan antrean?\nKode Booking: " + kodebooking, "Konfirmasi Batal", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String alasan = "Pembatalan oleh petugas RS - " + akses.getnamauser();

            // Simpan row sebelum eksekusi background (karena model tabel bisa berubah)
            final int selectedRow = row;
            final String finalAlasan = alasan;

            runBackground(() -> {
                if (batalAntreanKeBPJS(kodebooking, finalAlasan)) {
                    SwingUtilities.invokeLater(() -> {
                        simpanDataBatalKeDatabase(selectedRow, finalAlasan);
                        emptTeks();
                        tampil();
                        JOptionPane.showMessageDialog(this, "Data antrean berhasil dibatalkan dan data telah di-refresh.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    });
                }
            });
        }
    }

    private void batalAntreanDenganAlasan() {
        int row = tbJnsPerawatan.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data antrean yang akan dibatalkan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String kodebooking = (tbJnsPerawatan.getValueAt(row, 0) != null) ? tbJnsPerawatan.getValueAt(row, 0).toString() : "";
        String status = (tbJnsPerawatan.getValueAt(row, 16) != null) ? tbJnsPerawatan.getValueAt(row, 16).toString() : "";

        if (!"Belum dilayani".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "Status '" + status + "' tidak bisa dibatalkan!\nHanya antrean dengan status 'Belum dilayani' yang dapat dibatalkan.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String alasan = JOptionPane.showInputDialog(this, "Masukkan alasan pembatalan:", "Input Alasan Batal", JOptionPane.QUESTION_MESSAGE);
        if (alasan != null && !alasan.trim().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin akan membatalkan antrean?\nKode Booking: " + kodebooking + "\nAlasan: " + alasan, "Konfirmasi Batal", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {

                final int selectedRow = row;
                final String finalAlasan = alasan + " - " + akses.getnamauser();

                runBackground(() -> {
                    if (batalAntreanKeBPJS(kodebooking, finalAlasan)) {
                        SwingUtilities.invokeLater(() -> {
                            simpanDataBatalKeDatabase(selectedRow, finalAlasan);
                            emptTeks();
                            tampil();
                            JOptionPane.showMessageDialog(this, "Data antrean berhasil dibatalkan dan data telah di-refresh.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        });
                    }
                });
            }
        } else if (alasan != null) {
            JOptionPane.showMessageDialog(this, "Alasan tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }
}

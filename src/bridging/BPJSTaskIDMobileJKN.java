/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatanRalan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

package bridging;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 *
 * @author dosen
 */
public final class BPJSTaskIDMobileJKN extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;    
    private int i=0;
    private ApiMobileJKN api=new ApiMobileJKN();
    private String URL="",link="",utc="",requestJson="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;
    
    // Warna BPJS
    private final Color WARNA_BPJS = new Color(30, 136, 229);
    private final Color WARNA_BPJS_LIGHT = new Color(227, 242, 253);
    
    // Popup menu
    private JPopupMenu popupMenu;
    private JMenuItem menuKirimTaskId;

    public BPJSTaskIDMobileJKN(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.RM","Nama Pasien","No.HP","No.Kartu","NIK","Tanggal","Poliklinik","Dokter","Waktu RS","Waktu","Task Name","Task ID"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbJnsPerawatan.setModel(tabMode);

        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(110);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(83);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(103);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(140);
            }else if(i==8){
                column.setPreferredWidth(160);
            }else if(i==9){
                column.setPreferredWidth(115);
            }else if(i==10){
                column.setPreferredWidth(115);
            }else if(i==11){
                column.setPreferredWidth(190);
            }else if(i==12){
                column.setPreferredWidth(50);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
        setupPopupMenu();
        
        try {
            link=koneksiDB.URLAPIMOBILEJKN();
        } catch (Exception e) {
            System.out.println("E : "+e);
        }
    }
    
    private void setupPopupMenu() {
        popupMenu = new JPopupMenu();
        popupMenu.setBackground(WARNA_BPJS);
        popupMenu.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        menuKirimTaskId = new JMenuItem(" Kirim Task ID ");
        try {
            menuKirimTaskId.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        } catch (Exception e) {}
        menuKirimTaskId.setFont(new Font("Tahoma", Font.BOLD, 12));
        menuKirimTaskId.setForeground(Color.WHITE);
        menuKirimTaskId.setBackground(WARNA_BPJS);
        menuKirimTaskId.setPreferredSize(new Dimension(150, 28));
        
        menuKirimTaskId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tbJnsPerawatan.getSelectedRow();
                if (selectedRow >= 0) {
                    showKirimTaskIdDialog(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Silahkan pilih data terlebih dahulu!");
                }
            }
        });
        
        popupMenu.add(menuKirimTaskId);
        
        tbJnsPerawatan.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = tbJnsPerawatan.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        tbJnsPerawatan.setRowSelectionInterval(row, row);
                    }
                    popupMenu.show(tbJnsPerawatan, e.getX(), e.getY());
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = tbJnsPerawatan.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        tbJnsPerawatan.setRowSelectionInterval(row, row);
                    }
                    popupMenu.show(tbJnsPerawatan, e.getX(), e.getY());
                }
            }
        });
    }
    
    // ==================== CEK APAKAH PASIEN BARU ====================
    private boolean isPasienBaru(String noRawat) {
        boolean isBaru = false;
        PreparedStatement psBaru = null;
        ResultSet rsBaru = null;
        
        try {
            // 1. CEK DARI referensi_mobilejkn_bpjs (SUMBER UTAMA UNTUK PASIEN MOBILE JKN)
            psBaru = koneksi.prepareStatement(
                "SELECT pasienbaru FROM referensi_mobilejkn_bpjs WHERE no_rawat LIKE ? OR nobooking = ?"
            );
            psBaru.setString(1, "%" + noRawat + "%");
            psBaru.setString(2, noRawat);
            rsBaru = psBaru.executeQuery();
            if (rsBaru.next()) {
                String pasienbaru = rsBaru.getString("pasienbaru");
                if (pasienbaru != null && pasienbaru.equals("1")) {
                    isBaru = true;
                }
            }
            rsBaru.close();
            psBaru.close();
        } catch (Exception e) {
            System.out.println("Error cek pasien baru dari referensi_mobilejkn_bpjs: " + e);
        }
        
        // 2. JIKA TIDAK DITEMUKAN DI referensi_mobilejkn_bpjs, CEK DARI reg_periksa
        if (!isBaru) {
            try {
                psBaru = koneksi.prepareStatement(
                    "SELECT stts_daftar FROM reg_periksa WHERE no_rawat = ?"
                );
                psBaru.setString(1, noRawat);
                rsBaru = psBaru.executeQuery();
                if (rsBaru.next()) {
                    String stts = rsBaru.getString("stts_daftar");
                    if (stts != null && stts.equalsIgnoreCase("Baru")) {
                        isBaru = true;
                    }
                }
                rsBaru.close();
                psBaru.close();
            } catch (Exception e) {
                System.out.println("Error cek pasien baru dari reg_periksa: " + e);
            }
        }
        
        System.out.println("isPasienBaru(" + noRawat + ") = " + isBaru);
        return isBaru;
    }
    
    // ==================== CEK APAKAH PASIEN DAPAT RESEP ====================
    private boolean punyaResep(String noRawat) {
        boolean adaResep = false;
        PreparedStatement psResep = null;
        ResultSet rsResep = null;
        try {
            // CEK DARI TABEL resep_obat (PASTI ADA)
            psResep = koneksi.prepareStatement(
                "SELECT COUNT(*) FROM resep_obat WHERE no_rawat = ? AND status = 'ralan'"
            );
            psResep.setString(1, noRawat);
            rsResep = psResep.executeQuery();
            if (rsResep.next() && rsResep.getInt(1) > 0) {
                adaResep = true;
            }
            rsResep.close();
            psResep.close();
            
            // HAPUS QUERY KE resep_dokter_ralan (TIDAK ADA)
            // HAPUS QUERY KE resep_dokter (TIDAK PUNYA no_rawat)
            // HAPUS QUERY stts_daftar = 'Resep' (TIDAK ADA NILAI TERSEBUT)
            
        } catch (Exception e) {
            System.out.println("Error cek resep: " + e);
            adaResep = false;
        }
        System.out.println("punyaResep(" + noRawat + ") = " + adaResep);
        return adaResep;
    }
    
    // ==================== DAPATKAN TASK YANG SUDAH TERKIRIM DARI API BPJS ====================
    private List<String> getSentTaskIds(String noRawat) {
        List<String> sentTasks = new ArrayList<>();
        try {
            for (int j = 0; j < tabMode.getRowCount(); j++) {
                String rowNoRawat = tabMode.getValueAt(j, 0).toString();
                if (rowNoRawat.equals(noRawat)) {
                    Object taskIdObj = tabMode.getValueAt(j, 12);
                    if (taskIdObj != null && !taskIdObj.toString().isEmpty() && !taskIdObj.toString().equals("null")) {
                        sentTasks.add(taskIdObj.toString());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error baca task dari API: " + e);
        }
        return sentTasks;
    }
    
    // ==================== DAPATKAN WAKTU RS DARI TASK ID ====================
    private String getWaktuRSByTaskId(String noRawat, String taskId) {
        for (int j = 0; j < tabMode.getRowCount(); j++) {
            String rowNoRawat = tabMode.getValueAt(j, 0).toString();
            if (rowNoRawat.equals(noRawat)) {
                Object taskIdObj = tabMode.getValueAt(j, 12);
                if (taskIdObj != null && taskIdObj.toString().equals(taskId)) {
                    Object waktuObj = tabMode.getValueAt(j, 9);
                    return waktuObj != null ? waktuObj.toString() : "";
                }
            }
        }
        return "";
    }
    
    // ==================== SIMPAN TASK ID KE DATABASE LOKAL ====================
    private void simpanTaskId(String noRawat, String taskid, String waktu) {
        PreparedStatement psSimpan = null;
        try {
            psSimpan = koneksi.prepareStatement(
                "INSERT INTO referensi_mobilejkn_bpjs_taskid (no_rawat, taskid, waktu) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE waktu = ?"
            );
            psSimpan.setString(1, noRawat);
            psSimpan.setString(2, taskid);
            psSimpan.setString(3, waktu);
            psSimpan.setString(4, waktu);
            psSimpan.executeUpdate();
            psSimpan.close();
            System.out.println("✅ TERSIMPAN/UPDATE DB: " + noRawat + " - Task " + taskid + " - Waktu: " + waktu);
        } catch (Exception e) {
            System.out.println("Error simpan task id: " + e);
        }
    }
    
    // ==================== SHOW KIRIM TASK ID DIALOG ====================
    private void showKirimTaskIdDialog(int row) {
        String noRawat = tabMode.getValueAt(row, 0).toString();
        String noRM = tabMode.getValueAt(row, 1).toString();
        String namaPasien = tabMode.getValueAt(row, 2).toString();
        String poli = tabMode.getValueAt(row, 7) != null ? tabMode.getValueAt(row, 7).toString() : "-";
        String dokter = tabMode.getValueAt(row, 8) != null ? tabMode.getValueAt(row, 8).toString() : "-";
        String tanggalPelayanan = tabMode.getValueAt(row, 6) != null ? tabMode.getValueAt(row, 6).toString() : "";
        
        // CEK STATUS PASIEN
        boolean isBaru = isPasienBaru(noRawat);
        boolean adaResep = punyaResep(noRawat);
        
        // DAPATKAN TASK YANG SUDAH TERKIRIM DARI API
        List<String> sentTasks = getSentTaskIds(noRawat);
        
        System.out.println("=== CEK DATA UNTUK " + noRawat + " ===");
        System.out.println("Pasien Baru: " + isBaru);
        System.out.println("Ada Resep: " + adaResep);
        System.out.println("Task Terkirim (API): " + sentTasks);
        System.out.println("====================================");
        
        // ========== TENTUKAN TASK YANG TERSEDIA ==========
        List<Integer> availableTasks = new ArrayList<>();
        
        // Task 1 & 2 untuk PASIEN BARU
        if (isBaru) {
            if (!sentTasks.contains("1")) availableTasks.add(1);
            if (!sentTasks.contains("2")) availableTasks.add(2);
        }
        
        // Task 3,4,5 selalu tersedia (LOGIKA ASLI)
        for (int t = 3; t <= 5; t++) {
            if (!sentTasks.contains(String.valueOf(t))) {
                availableTasks.add(t);
            }
        }
        
        // Task 6,7 hanya jika ada resep (LOGIKA ASLI)
        if (adaResep) {
            if (!sentTasks.contains("6")) availableTasks.add(6);
            if (!sentTasks.contains("7")) availableTasks.add(7);
        }
        
        System.out.println("availableTasks: " + availableTasks);
        
        if (availableTasks.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Semua task ID sudah terkirim untuk pasien ini!");
            return;
        }
        
        // ========== BUAT DIALOG DENGAN CHECKBOX ==========
        final javax.swing.JDialog dialog = new javax.swing.JDialog(this, "Kirim Task ID", true);
        dialog.setUndecorated(true);
        dialog.setLayout(new java.awt.BorderLayout());
        dialog.setSize(1000, 700);
        dialog.setLocationRelativeTo(this);
        
        // Panel utama
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createLineBorder(WARNA_BPJS, 2));
        
        // Header
        javax.swing.JPanel headerPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
        headerPanel.setBackground(WARNA_BPJS);
        headerPanel.setPreferredSize(new Dimension(1000, 45));
        
        try {
            javax.swing.JLabel lblIcon = new javax.swing.JLabel(new javax.swing.ImageIcon(getClass().getResource("/picture/icon bpjs.png")));
            headerPanel.add(lblIcon);
        } catch (Exception e) {}
        
        javax.swing.JLabel lblHeader = new javax.swing.JLabel("KIRIM TASK ID MOBILE JKN");
        lblHeader.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblHeader.setForeground(Color.WHITE);
        headerPanel.add(lblHeader);
        mainPanel.add(headerPanel, java.awt.BorderLayout.NORTH);
        
        // Content
        javax.swing.JPanel contentPanel = new javax.swing.JPanel(new java.awt.GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(8, 8, 8, 8);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        
        // Panel Informasi Pasien
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        javax.swing.JPanel infoPanel = new javax.swing.JPanel(new java.awt.GridLayout(7, 2, 10, 10));
        infoPanel.setBackground(WARNA_BPJS_LIGHT);
        infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(WARNA_BPJS), "Informasi Pasien", 
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, 
            new Font("Tahoma", Font.BOLD, 13), WARNA_BPJS));
        
        infoPanel.add(createLabelInfo("No. Rawat :"));
        infoPanel.add(createValueLabel(noRawat));
        infoPanel.add(createLabelInfo("No. RM :"));
        infoPanel.add(createValueLabel(noRM));
        infoPanel.add(createLabelInfo("Nama Pasien :"));
        infoPanel.add(createValueLabel(namaPasien));
        infoPanel.add(createLabelInfo("Poliklinik / Dokter :"));
        infoPanel.add(createValueLabel(poli + " / " + dokter));
        infoPanel.add(createLabelInfo("Tanggal Pelayanan :"));
        infoPanel.add(createValueLabel(tanggalPelayanan));
        infoPanel.add(createLabelInfo("Status Pasien :"));
        infoPanel.add(createValueLabel(isBaru ? "BARU" : "LAMA"));
        infoPanel.add(createLabelInfo("Ada Resep :"));
        infoPanel.add(createValueLabel(adaResep ? "YA ✅" : "TIDAK"));
        contentPanel.add(infoPanel, gbc);
        
        // Panel Task dengan Checkbox
        gbc.gridy = 1;
        javax.swing.JPanel taskPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        taskPanel.setBackground(WARNA_BPJS_LIGHT);
        taskPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(WARNA_BPJS), 
            "Pilih Task ID (centang yang akan dikirim)", 
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, 
            new Font("Tahoma", Font.BOLD, 13), WARNA_BPJS));
        taskPanel.setPreferredSize(new Dimension(950, 280));
        
        // Panel checkbox dengan GridLayout 2 kolom
        javax.swing.JPanel checkboxPanel = new javax.swing.JPanel(new java.awt.GridLayout(0, 2, 20, 15));
        checkboxPanel.setBackground(WARNA_BPJS_LIGHT);
        checkboxPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        Map<Integer, String> taskNames = new HashMap<>();
        taskNames.put(1, "Waktu tunggu admisi");
        taskNames.put(2, "Akhir waktu tunggu admisi / Mulai waktu layan admisi");
        taskNames.put(3, "Akhir waktu layan admisi / Mulai waktu tunggu poli");
        taskNames.put(4, "Akhir waktu tunggu poli / Mulai waktu layan poli");
        taskNames.put(5, "Akhir waktu layan poli");
        taskNames.put(6, "Mulai waktu layan farmasi");
        taskNames.put(7, "Akhir waktu layan farmasi");
        
        Map<Integer, javax.swing.JCheckBox> checkboxes = new HashMap<>();
        for (int taskId : availableTasks) {
            String label = taskId + " - " + taskNames.getOrDefault(taskId, "Task " + taskId);
            boolean isWajib = (taskId >= 3 && taskId <= 5);
            boolean isBaruTask = (isBaru && (taskId == 1 || taskId == 2));
            if (isWajib || isBaruTask) {
                label += " (WAJIB)";
            }
            javax.swing.JCheckBox chk = new javax.swing.JCheckBox(label);
            chk.setBackground(WARNA_BPJS_LIGHT);
            chk.setFont(new Font("Tahoma", Font.PLAIN, 13));
            chk.setSelected(true);
            chk.setPreferredSize(new Dimension(400, 35));
            checkboxes.put(taskId, chk);
            checkboxPanel.add(chk);
        }
        taskPanel.add(checkboxPanel, java.awt.BorderLayout.CENTER);
        
        // Info urutan dan task terkirim
        javax.swing.JPanel infoUrutanPanel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1, 5, 5));
        infoUrutanPanel.setBackground(new Color(255, 248, 225));
        infoUrutanPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 193, 7), 2));
        infoUrutanPanel.setPreferredSize(new Dimension(950, 60));
        
        String urutanInfo;
        if (isBaru) {
            urutanInfo = "⚠ Pasien BARU: urutan wajib 1 → 2 → 3 → 4 → 5" + (adaResep ? " → 6 → 7" : "");
        } else {
            urutanInfo = "⚠ Urutan wajib: 3 → 4 → 5" + (adaResep ? " → 6 → 7" : "");
        }
        if (adaResep) {
            urutanInfo += " (Task 7 SELALU terakhir)";
        }
        javax.swing.JLabel lblUrutan = new javax.swing.JLabel(urutanInfo);
        lblUrutan.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblUrutan.setForeground(WARNA_BPJS);
        infoUrutanPanel.add(lblUrutan);
        
        // Tampilkan task dari API
        StringBuilder infoSent = new StringBuilder();
        infoSent.append("✓ Task terkirim (API): ");
        if (sentTasks.isEmpty()) {
            infoSent.append("(belum ada)");
        } else {
            infoSent.append(String.join(", ", sentTasks));
        }
        javax.swing.JLabel lblSent = new javax.swing.JLabel(infoSent.toString());
        lblSent.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblSent.setForeground(new Color(0, 150, 0));
        infoUrutanPanel.add(lblSent);
        
        taskPanel.add(infoUrutanPanel, java.awt.BorderLayout.SOUTH);
        contentPanel.add(taskPanel, gbc);
        
        // Button panel
        gbc.gridy = 2;
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(Color.WHITE);
        
        // Tombol Pilih Semua
        javax.swing.JButton btnSelectAll = new javax.swing.JButton(" Pilih Semua ");
        try {
            btnSelectAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        } catch (Exception e) {}
        btnSelectAll.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSelectAll.setBackground(new Color(100, 180, 100));
        btnSelectAll.setForeground(Color.WHITE);
        btnSelectAll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnSelectAll.setFocusPainted(false);
        btnSelectAll.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSelectAll.addActionListener(e -> {
            for (javax.swing.JCheckBox chk : checkboxes.values()) {
                chk.setSelected(true);
            }
        });
        
        // Tombol Hapus Pilihan
        javax.swing.JButton btnUnselectAll = new javax.swing.JButton(" Hapus Pilihan ");
        btnUnselectAll.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnUnselectAll.setBackground(new Color(200, 100, 100));
        btnUnselectAll.setForeground(Color.WHITE);
        btnUnselectAll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnUnselectAll.setFocusPainted(false);
        btnUnselectAll.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUnselectAll.addActionListener(e -> {
            for (javax.swing.JCheckBox chk : checkboxes.values()) {
                chk.setSelected(false);
            }
        });
        
        javax.swing.JButton btnKirim = new javax.swing.JButton(" Kirim Task ID Terpilih ");
        try {
            btnKirim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        } catch (Exception e) {}
        btnKirim.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnKirim.setBackground(WARNA_BPJS);
        btnKirim.setForeground(Color.WHITE);
        btnKirim.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnKirim.setFocusPainted(false);
        btnKirim.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        javax.swing.JButton btnBatal = new javax.swing.JButton(" Batal ");
        try {
            btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
        } catch (Exception e) {}
        btnBatal.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnBatal.setBackground(new Color(150, 150, 150));
        btnBatal.setForeground(Color.WHITE);
        btnBatal.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnBatal.setFocusPainted(false);
        btnBatal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnKirim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedTasks = new ArrayList<>();
                for (Map.Entry<Integer, javax.swing.JCheckBox> entry : checkboxes.entrySet()) {
                    if (entry.getValue().isSelected()) {
                        selectedTasks.add(entry.getKey());
                    }
                }
                
                if (selectedTasks.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Silahkan pilih minimal 1 task ID!");
                    return;
                }
                
                Collections.sort(selectedTasks);
                
                // Validasi: task 7 harus terakhir
                if (selectedTasks.contains(7) && selectedTasks.get(selectedTasks.size() - 1) != 7) {
                    JOptionPane.showMessageDialog(dialog, 
                        "⚠ Task ID 7 (Akhir waktu layan farmasi) HARUS menjadi task terakhir!",
                        "Validasi Urutan", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Validasi urutan
                int firstSelected = selectedTasks.get(0);
                int minTask = isBaru ? 1 : 3;
                for (int t = minTask; t < firstSelected; t++) {
                    if (!sentTasks.contains(String.valueOf(t)) && !selectedTasks.contains(t)) {
                        int confirm = JOptionPane.showConfirmDialog(dialog, 
                            "⚠ PERINGATAN: Task " + t + " belum dikirim dan tidak dipilih!\n\n" +
                            "Urutan yang benar: " + (isBaru ? "1→2→3→4→5" : "3→4→5") + (adaResep ? "→6→7" : "") + "\n\n" +
                            "Task yang dipilih: " + selectedTasks + "\n\n" +
                            "Apakah Anda tetap ingin melanjutkan?",
                            "Validasi Urutan", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (confirm != JOptionPane.YES_OPTION) {
                            return;
                        }
                        break;
                    }
                }
                
                // Konfirmasi
                StringBuilder taskList = new StringBuilder();
                for (int id : selectedTasks) {
                    taskList.append("  ").append(id).append(" - ").append(taskNames.getOrDefault(id, "Task " + id)).append("\n");
                }
                
                int confirm = JOptionPane.showConfirmDialog(dialog, 
                    "Yakin akan mengirim " + selectedTasks.size() + " Task ID?\n\n" +
                    "No. Rawat       : " + noRawat + "\n" +
                    "Pasien          : " + namaPasien + "\n" +
                    "Status Pasien   : " + (isBaru ? "BARU" : "LAMA") + "\n" +
                    "Ada Resep       : " + (adaResep ? "YA" : "TIDAK") + "\n\n" +
                    "Task yang akan dikirim (berurutan):\n" + taskList.toString(),
                    "Konfirmasi Kirim Multiple Task ID", 
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    dialog.dispose();
                    kirimMultipleTask(noRawat, selectedTasks, tanggalPelayanan);
                }
            }
        });
        
        btnBatal.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(btnSelectAll);
        buttonPanel.add(btnUnselectAll);
        buttonPanel.add(btnKirim);
        buttonPanel.add(btnBatal);
        contentPanel.add(buttonPanel, gbc);
        
        mainPanel.add(contentPanel, java.awt.BorderLayout.CENTER);
        
        // Footer
        javax.swing.JPanel footerPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
        footerPanel.setBackground(WARNA_BPJS);
        footerPanel.setPreferredSize(new Dimension(1000, 30));
        
        String footerText = isBaru ? "Pasien BARU: 1→2→3→4→5" : "Pasien LAMA: 3→4→5";
        if (adaResep) footerText += "→6→7";
        javax.swing.JLabel lblFooter = new javax.swing.JLabel("BPJS Kesehatan - Mobile JKN | " + footerText + " | Sumber: API BPJS");
        lblFooter.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblFooter.setForeground(Color.WHITE);
        footerPanel.add(lblFooter);
        mainPanel.add(footerPanel, java.awt.BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    // ==================== KIRIM MULTIPLE TASK SECARA BERURUTAN ====================
    private void kirimMultipleTask(String kodebooking, List<Integer> taskIds, String tanggalPelayanan) {
        runBackground(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
                
                List<Integer> successTasks = new ArrayList<>();
                List<String> failedTasks = new ArrayList<>();
                String lastWaktuRS = "";
                
                for (int i = 0; i < taskIds.size(); i++) {
                    int taskId = taskIds.get(i);
                    
                    String waktu = hitungWaktuKirim(kodebooking, String.valueOf(taskId), lastWaktuRS, tanggalPelayanan);
                    lastWaktuRS = waktu;
                    
                    boolean success = kirimTaskIdSync(kodebooking, String.valueOf(taskId), waktu);
                    
                    if (success) {
                        successTasks.add(taskId);
                        simpanTaskId(kodebooking, String.valueOf(taskId), waktu);
                        System.out.println("✅ Task " + taskId + " berhasil dikirim");
                    } else {
                        failedTasks.add(String.valueOf(taskId));
                        System.out.println("❌ Task " + taskId + " gagal dikirim");
                    }
                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                
                final String resultMsg = buildResultMessage(taskIds.size(), successTasks, failedTasks);
                
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        if (failedTasks.isEmpty()) {
                            JOptionPane.showMessageDialog(rootPane, 
                                resultMsg + "\n\n" +
                                "Semua Task ID berhasil dikirim & tersimpan di database!",
                                "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(rootPane, 
                                resultMsg,
                                "Hasil Kirim", JOptionPane.WARNING_MESSAGE);
                        }
                        tampil();
                    }
                });
            }
        });
    }
    
    // ==================== BUILD RESULT MESSAGE ====================
    private String buildResultMessage(int total, List<Integer> success, List<String> failed) {
        StringBuilder msg = new StringBuilder();
        msg.append("=== HASIL KIRIM MULTIPLE TASK ===\n\n");
        msg.append("Total Task: ").append(total).append("\n");
        msg.append("Berhasil: ").append(success.size()).append(" task\n");
        msg.append("Gagal: ").append(failed.size()).append(" task\n\n");
        
        if (!success.isEmpty()) {
            msg.append("✅ Task berhasil: ").append(success).append("\n");
        }
        if (!failed.isEmpty()) {
            msg.append("❌ Task gagal: ").append(failed);
        }
        return msg.toString();
    }
    
    // ==================== HITUNG WAKTU KIRIM ====================
    private String hitungWaktuKirim(String kodebooking, String taskId, String previousWaktuRS, String tanggalPelayanan) {
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        
        List<String> sentTasks = getSentTaskIds(kodebooking);
        int currentTaskId = Integer.parseInt(taskId);
        
        String prevWaktuRS = "";
        if (previousWaktuRS != null && !previousWaktuRS.isEmpty()) {
            prevWaktuRS = previousWaktuRS;
        } else {
            for (String tid : sentTasks) {
                try {
                    int id = Integer.parseInt(tid);
                    if (id < currentTaskId) {
                        String waktuRS = getWaktuRSByTaskId(kodebooking, tid);
                        if (!waktuRS.isEmpty()) {
                            prevWaktuRS = waktuRS;
                        }
                    }
                } catch (NumberFormatException e) {}
            }
        }
        
        Date waktuAcuan;
        if (!prevWaktuRS.isEmpty()) {
            try {
                waktuAcuan = sdfInput.parse(prevWaktuRS);
            } catch (ParseException e) {
                waktuAcuan = new Date();
            }
        } else {
            waktuAcuan = new Date();
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(waktuAcuan);
        cal.add(Calendar.MINUTE, 5);
        
        return tanggalPelayanan + " " + sdfTime.format(cal.getTime());
    }
    
    // ==================== KIRIM TASK ID SYNC ====================
    private boolean kirimTaskIdSync(String kodebooking, String taskid, String waktu) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("x-cons-id", koneksiDB.CONSIDAPIMOBILEJKN());
            utc = String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("x-timestamp", utc);
            headers.add("x-signature", api.getHmac(utc));
            headers.add("user_key", koneksiDB.USERKEYAPIMOBILEJKN());
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(waktu);
            long timestamp = date.getTime();
            
            requestJson = "{" +
                    "\"kodebooking\": \"" + kodebooking + "\"," +
                    "\"taskid\": \"" + taskid + "\"," +
                    "\"waktu\": " + timestamp +
                    "}";
            
            System.out.println("URL : " + link + "/antrean/updatewaktu");
            System.out.println("JSON : " + requestJson);
            
            requestEntity = new HttpEntity(requestJson, headers);
            URL = link + "/antrean/updatewaktu";
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            nameNode = root.path("metadata");
            
            String responseCode = nameNode.path("code").asText();
            return responseCode.equals("200");
            
        } catch (Exception ex) {
            System.err.println("Error kirim task " + taskid + ": " + ex.getMessage());
            return false;
        }
    }
    
    private javax.swing.JLabel createLabelInfo(String text) {
        javax.swing.JLabel label = new javax.swing.JLabel(text);
        label.setFont(new Font("Tahoma", Font.BOLD, 12));
        label.setForeground(WARNA_BPJS);
        return label;
    }
    
    private javax.swing.JLabel createValueLabel(String text) {
        javax.swing.JLabel label = new javax.swing.JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label.setForeground(new Color(0, 0, 0));
        return label;
    }
    
    private void kirimTaskId(String kodebooking, String taskid, String waktu) {
        runBackground(new Runnable() {
            @Override
            public void run() {
                try {
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("x-cons-id", koneksiDB.CONSIDAPIMOBILEJKN());
                    utc = String.valueOf(api.GetUTCdatetimeAsString());
                    headers.add("x-timestamp", utc);
                    headers.add("x-signature", api.getHmac(utc));
                    headers.add("user_key", koneksiDB.USERKEYAPIMOBILEJKN());
                    
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = sdf.parse(waktu);
                    long timestamp = date.getTime();
                    
                    requestJson = "{" +
                            "\"kodebooking\": \"" + kodebooking + "\"," +
                            "\"taskid\": \"" + taskid + "\"," +
                            "\"waktu\": " + timestamp +
                            "}";
                    
                    System.out.println("URL : " + link + "/antrean/updatewaktu");
                    System.out.println("JSON : " + requestJson);
                    
                    requestEntity = new HttpEntity(requestJson, headers);
                    URL = link + "/antrean/updatewaktu";
                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                    nameNode = root.path("metadata");
                    
                    final String responseCode = nameNode.path("code").asText();
                    final String responseMessage = nameNode.path("message").asText();
                    
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if (responseCode.equals("200")) {
                                simpanTaskId(kodebooking, taskid, waktu);
                                
                                JOptionPane.showMessageDialog(rootPane, 
                                    "✅ Task ID berhasil dikirim & tersimpan di database!\n\n" +
                                    "Kodebooking : " + kodebooking + "\n" +
                                    "Task ID     : " + taskid + "\n" +
                                    "Waktu       : " + waktu,
                                    "Sukses",
                                    JOptionPane.INFORMATION_MESSAGE);
                                tampil();
                            } else {
                                JOptionPane.showMessageDialog(rootPane, 
                                    "Gagal mengirim Task ID!\n\n" +
                                    "Response Code : " + responseCode + "\n" +
                                    "Message       : " + responseMessage, 
                                    "Error", 
                                    JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                    
                } catch (Exception ex) {
                    System.out.println("Error kirim task id: " + ex);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            JOptionPane.showMessageDialog(rootPane, 
                                "Terjadi kesalahan: " + ex.getMessage(), 
                                "Error", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                    });
                }
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Task ID Mobile JKN ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setToolTipText("Klik kanan pada data untuk mengirim Task ID");
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        Scroll.setViewportView(tbJnsPerawatan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-02-2026" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-02-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass9.add(BtnKeluar);

        internalFrame1.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnCari,TCari);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        runBackground(new Runnable() {
            @Override
            public void run() {
                tampil();
            }
        });
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        runBackground(new Runnable() {
            @Override
            public void run() {
                tampil();
            }
        });
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            runBackground(new Runnable() {
                @Override
                public void run() {
                    tampil();
                }
            });
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(new Runnable() {
                            @Override
                            public void run() {
                                tampil();
                            }
                        });
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(new Runnable() {
                            @Override
                            public void run() {
                                tampil();
                            }
                        });
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        runBackground(new Runnable() {
                            @Override
                            public void run() {
                                tampil();
                            }
                        });
                    }
                }
            });
        }
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSTaskIDMobileJKN dialog = new BPJSTaskIDMobileJKN(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    // ==================== TAMPIL DATA ====================
    private void tampil() {
        Valid.tabelKosong(tabMode);
        
        // ========== QUERY 1: DATA DARI REG_PERIKSA ==========
        try{
            ps=koneksi.prepareStatement(
                   "SELECT reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.no_tlp,pasien.no_peserta,"+
                   "pasien.no_ktp,reg_periksa.tgl_registrasi,poliklinik.nm_poli,dokter.nm_dokter "+
                   "FROM reg_periksa INNER JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "INNER JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli "+
                   "INNER JOIN dokter ON reg_periksa.kd_dokter=dokter.kd_dokter "+
                   "WHERE reg_periksa.tgl_registrasi BETWEEN ? AND ? "+(TCari.getText().equals("")?"":
                   "and (reg_periksa.no_rawat LIKE ? OR reg_periksa.no_rkm_medis LIKE ? OR pasien.nm_pasien LIKE ? OR "+
                   "pasien.no_tlp LIKE ? OR pasien.no_peserta LIKE ? OR pasien.no_ktp LIKE ? OR "+
                   "poliklinik.nm_poli LIKE ? OR dokter.nm_dokter LIKE ?) ")+
                   "order by reg_periksa.tgl_registrasi");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    try {
                        // ===== AMBIL DATA TASK DARI API BPJS =====
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                        utc=String.valueOf(api.GetUTCdatetimeAsString());
                        headers.add("x-timestamp",utc);
                        headers.add("x-signature",api.getHmac(utc));
                        headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                        requestJson ="{" +
                                        "\"kodebooking\": \""+rs.getString("no_rawat")+"\"" +
                                     "}";
                        requestEntity = new HttpEntity(requestJson,headers);
                        URL = link+"/antrean/getlisttask";	
                        System.out.println("URL : "+URL);
                        System.out.println("JSON : "+requestJson);
                        root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                        nameNode = root.path("metadata");
                        if(nameNode.path("code").asText().equals("200")){
                            response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc));
                            if(response.isArray()){
                                for(JsonNode list:response){
                                    tabMode.addRow(new Object[]{
                                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                                        rs.getString("no_tlp"),rs.getString("no_peserta"),rs.getString("no_ktp"),
                                        rs.getString("tgl_registrasi"),rs.getString("nm_poli"),rs.getString("nm_dokter"),
                                        list.path("wakturs").asText(),list.path("waktu").asText(),list.path("taskname").asText(),
                                        list.path("taskid").asText()
                                    });
                                }
                            }
                        }else {
                            System.out.println("Notif : "+nameNode.path("message").asText());               
                        }   
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : "+ex);
                        if(ex.toString().contains("UnknownHostException")){
                            JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
        // ========== QUERY 2: DATA DARI REFERENSI_MOBILEJKN_BPJS ==========
        try{
            ps=koneksi.prepareStatement(
                   "SELECT referensi_mobilejkn_bpjs.nobooking,reg_periksa.no_rkm_medis,pasien.nm_pasien,referensi_mobilejkn_bpjs.nohp,referensi_mobilejkn_bpjs.nomorkartu,"+
                   "referensi_mobilejkn_bpjs.nik,referensi_mobilejkn_bpjs.tanggalperiksa,poliklinik.nm_poli,dokter.nm_dokter "+
                   "FROM referensi_mobilejkn_bpjs INNER JOIN reg_periksa ON referensi_mobilejkn_bpjs.no_rawat=reg_periksa.no_rawat "+
                   "INNER JOIN pasien ON reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "INNER JOIN poliklinik ON reg_periksa.kd_poli=poliklinik.kd_poli "+
                   "INNER JOIN dokter ON reg_periksa.kd_dokter=dokter.kd_dokter "+
                   "WHERE referensi_mobilejkn_bpjs.tanggalperiksa BETWEEN ? AND ? "+(TCari.getText().equals("")?"":
                   "and (referensi_mobilejkn_bpjs.nobooking LIKE ? OR reg_periksa.no_rkm_medis LIKE ? OR pasien.nm_pasien LIKE ? OR "+
                   "referensi_mobilejkn_bpjs.nohp LIKE ? OR referensi_mobilejkn_bpjs.nomorkartu LIKE ? OR referensi_mobilejkn_bpjs.nik LIKE ? OR "+
                   "poliklinik.nm_poli LIKE ? OR dokter.nm_dokter LIKE ?) ")+
                   "order by referensi_mobilejkn_bpjs.tanggalperiksa");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    try {
                        // ===== AMBIL DATA TASK DARI API BPJS =====
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                        utc=String.valueOf(api.GetUTCdatetimeAsString());
                        headers.add("x-timestamp",utc);
                        headers.add("x-signature",api.getHmac(utc));
                        headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                        requestJson ="{" +
                                        "\"kodebooking\": \""+rs.getString("nobooking")+"\"" +
                                     "}";
                        requestEntity = new HttpEntity(requestJson,headers);
                        URL = link+"/antrean/getlisttask";	
                        System.out.println("URL : "+URL);
                        System.out.println("JSON : "+requestJson);
                        root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                        nameNode = root.path("metadata");
                        if(nameNode.path("code").asText().equals("200")){
                            response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc));
                            if(response.isArray()){
                                for(JsonNode list:response){
                                    tabMode.addRow(new Object[]{
                                        rs.getString("nobooking"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                                        rs.getString("nohp"),rs.getString("nomorkartu"),rs.getString("nik"),
                                        rs.getString("tanggalperiksa"),rs.getString("nm_poli"),rs.getString("nm_dokter"),
                                        list.path("wakturs").asText(),list.path("waktu").asText(),list.path("taskname").asText(),
                                        list.path("taskid").asText()
                                    });
                                }
                            }
                        }else {
                            System.out.println("Notif : "+nameNode.path("message").asText());                  
                        }   
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : "+ex);
                        if(ex.toString().contains("UnknownHostException")){
                            JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
        LCount.setText(""+tabMode.getRowCount());
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        if (executor.isShutdown() || executor.isTerminated()) return;
        if (!isDisplayable()) return;

        ceksukses = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        task.run();
                    } finally {
                        ceksukses = false;
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                if (isDisplayable()) {
                                    setCursor(Cursor.getDefaultCursor());
                                }
                            }
                        });
                    }
                }
            });
        } catch (RejectedExecutionException ex) {
            ceksukses = false;
        }
    }
    
    @Override
    public void dispose() {
        executor.shutdownNow();
        super.dispose();
    }
}
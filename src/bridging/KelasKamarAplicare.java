package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.koneksiDB;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public final class KelasKamarAplicare extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;    
    private int i = 0;
    
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private ApiBPJSAplicare api = new ApiBPJSAplicare();
    private String URL = "", utc = "";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean ceksukses = false;

    public KelasKamarAplicare(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,450);

        tabMode=new DefaultTableModel(null,new Object[]{"No.","Kode Kelas","Nama Kelas"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamar.setModel(tabMode);

        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(140);
            }else if(i==2){
                column.setPreferredWidth(470);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
        TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(TCari.getText().length()>0){
                    tampil();
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if(TCari.getText().length()>0){
                    tampil();
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                if(TCari.getText().length()>0){
                    tampil();
                }
            }
        });
        
        try {
            URL = koneksiDB.URLAPIAPLICARE()+"/rest/ref/kelas";
        } catch (Exception e) {
            System.out.println("E : "+e);
        } 
        
        tampil();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelGlass6 = new widget.panelisi();
        jLabel16 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel17 = new widget.Label();
        BtnUpdate = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Referensi Kelas Kamar Aplicare (Lokal) ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(10, 10));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setToolTipText("Double klik atau tekan Enter untuk memilih data");
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel16.setText("Kode/Nama Kelas :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(105, 23));
        panelGlass6.add(jLabel16);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass6.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6 - Cari Data");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnCari);

        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass6.add(jLabel17);

        BtnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/42a.png"))); // NOI18N
        BtnUpdate.setMnemonic('U');
        BtnUpdate.setText("Update");
        BtnUpdate.setToolTipText("Alt+U - Tarik Data BPJS & Update Lokal");
        BtnUpdate.setName("BtnUpdate"); // NOI18N
        BtnUpdate.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUpdateActionPerformed(evt);
            }
        });
        BtnUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnUpdateKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnUpdate);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K - Tutup Form");
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
        panelGlass6.add(BtnKeluar);

        internalFrame1.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            if(tbKamar.getRowCount()>0){
                tbKamar.requestFocus();
                tbKamar.setRowSelectionInterval(0,0);
            }
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUpdateActionPerformed
        int konfirmasi = JOptionPane.showConfirmDialog(null,"Data referensi kamar akan ditarik dari BPJS dan menghapus data lama di database lokal.\nLanjutkan?","Konfirmasi Update",JOptionPane.YES_NO_OPTION);
        if(konfirmasi == JOptionPane.YES_OPTION){
            runBackground(() -> {
                tampilBPJS();
                
                if(tabMode.getRowCount()==0){
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null,"Gagal menarik data dari BPJS. Proses dibatalkan.");
                    });
                    return;
                }
                
                int berhasil = 0;
                int gagal = 0;
                try {
                    Sequel.queryu("DELETE FROM kelas_kamar_aplicare");
                    
                    for(int r=0; r<tabMode.getRowCount(); r++){
                        try {
                            String kodeKelas = tabMode.getValueAt(r,1).toString();
                            String namaKelas = tabMode.getValueAt(r,2).toString();
                            
                            Sequel.queryu("INSERT INTO kelas_kamar_aplicare (kode_kelas, nama_kelas) VALUES ('"+kodeKelas+"','"+namaKelas+"')");
                            berhasil++;
                        } catch (Exception e) {
                            gagal++;
                            System.out.println("Gagal insert baris ke-"+r+" : "+e);
                        }
                    }
                    
                    final int b = berhasil;
                    final int g = gagal;
                    SwingUtilities.invokeLater(() -> {
                        if(g == 0){
                            JOptionPane.showMessageDialog(null,"Berhasil menyimpan "+b+" data ke database lokal.");
                        } else {
                            JOptionPane.showMessageDialog(null,"Proses selesai.\nBerhasil: "+b+" data\nGagal: "+g+" data");
                        }
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat menghapus data lama: "+ex.getMessage());
                    });
                }
            });
        }
    }//GEN-LAST:event_BtnUpdateActionPerformed

    private void BtnUpdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnUpdateKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnUpdateActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnCari,BtnKeluar);
        }
    }//GEN-LAST:event_BtnUpdateKeyPressed

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if(evt.getClickCount()==2){
            dispose();
        }
    }//GEN-LAST:event_tbKamarMouseClicked

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE||evt.getKeyCode()==KeyEvent.VK_ENTER){
            dispose();
        }
    }//GEN-LAST:event_tbKamarKeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KelasKamarAplicare dialog = new KelasKamarAplicare(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnUpdate;
    private widget.TextBox TCari;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.InternalFrame internalFrame1;
    private widget.panelisi panelGlass6;
    private widget.ScrollPane Scroll;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        tabMode.setRowCount(0);
        try {
            String sql = "SELECT kode_kelas, nama_kelas FROM kelas_kamar_aplicare ";
            if(!TCari.getText().trim().equals("")){
                sql += "WHERE kode_kelas LIKE ? OR nama_kelas LIKE ? ";
            }
            sql += "ORDER BY kode_kelas";
            
            ps = koneksi.prepareStatement(sql);
            
            if(!TCari.getText().trim().equals("")){
                ps.setString(1, "%" + TCari.getText() + "%");
                ps.setString(2, "%" + TCari.getText() + "%");
            }
            
            rs = ps.executeQuery();
            i=1;
            while(rs.next()){
                tabMode.addRow(new Object[]{
                    i+".",rs.getString("kode_kelas"),rs.getString("nama_kelas")
                });
                i++;
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        } finally {
            try {
                if(rs!=null){ rs.close(); }
                if(ps!=null){ ps.close(); }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }
        }
    }    

    public void tampilBPJS() {        
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID",koneksiDB.CONSIDAPIAPLICARE());
            utc=String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("X-Timestamp",utc);
            headers.add("X-Signature",api.getHmac(utc));
            headers.add("user_key",koneksiDB.USERKEYAPIAPLICARE());
            requestEntity = new HttpEntity(headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metadata");
            if(nameNode.path("message").asText().equals("OK")){
                Valid.tabelKosong(tabMode);
                response = root.path("response");
                if(response.path("list").isArray()){
                    i=1;
                    for(JsonNode list:response.path("list")){
                        if(list.path("kodekelas").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                list.path("namakelas").asText().toLowerCase().contains(TCari.getText().toLowerCase())){
                            tabMode.addRow(new Object[]{
                                i+".",list.path("kodekelas").asText(),list.path("namakelas").asText()
                            });
                            i++;
                        }
                    }
                }
            }else {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
                });
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(rootPane,"Koneksi ke server Aplicare terputus...!");
                });
            }
        }
    }   
    
    public JTable getTable(){
        return tbKamar;
    }
    
    private void runBackground(Runnable task) {
        if (ceksukses) return;
        if (executor.isShutdown() || executor.isTerminated()) return;
        if (!isDisplayable()) return;

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
        executor.shutdownNow();
        super.dispose();
    }
}
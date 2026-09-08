package integration_idrg;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class WarnaTableIDRGClaim extends DefaultTableCellRenderer {

    // ===== WARNA UTAMA =====
    private final Color TOSCA = new Color(43, 179, 177);        // #2BB3B1
    private final Color TOSCA_LIGHT = new Color(234, 247, 247); // #EAF7F7
    private final Color GREEN_SOFT = new Color(76, 175, 154);   // #4CAF9A
    private final Color YELLOW_SOFT = new Color(255, 243, 205); // #FFF3CD
    private final Color RED_SOFT = new Color(229, 115, 115);    // #E57373
    private final Color WHITE = Color.WHITE;

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        String statusVerif = table.getValueAt(row, 9).toString();
        String statusKoding = table.getValueAt(row, 11).toString();
        String statusKirim = table.getValueAt(row, 13).toString();
        String statusDownload = table.getValueAt(row, 15).toString();

        // ================= BACKGROUND =================
        if (statusDownload.equals("Sudah Download")) {
            c.setBackground(GREEN_SOFT);
            c.setForeground(Color.WHITE);

        } else if (statusKirim.equals("Sudah Kirim")) {
            c.setBackground(GREEN_SOFT);
            c.setForeground(Color.WHITE);

        } else if (statusKoding.equals("Sudah Koding")) {
            c.setBackground(TOSCA);
            c.setForeground(Color.WHITE);

        } else if (statusVerif.equals("Sudah Verif")) {
            c.setBackground(TOSCA_LIGHT);
            c.setForeground(Color.BLACK);

        } else if (statusVerif.equals("Siap Verif")) {
            c.setBackground(TOSCA);
            c.setForeground(Color.WHITE);

        } else if (statusVerif.equals("Perlu Perbaikan")) {
            c.setBackground(YELLOW_SOFT);
            c.setForeground(Color.BLACK);

        } else {
            // Zebra row default
            if (row % 2 == 0) {
                c.setBackground(WHITE);
            } else {
                c.setBackground(TOSCA_LIGHT);
            }
            c.setForeground(Color.BLACK);
        }

        // ================= SELECTED =================
        if (isSelected) {
            c.setBackground(TOSCA);
            c.setForeground(Color.WHITE);
        }

        return c;
    }
}

package integration_idrg;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class WarnaTableDiagnosaIDRG extends DefaultTableCellRenderer {

    // Warna utama
    private final Color TOSCA = new Color(43, 179, 177);      // #2BB3B1
    private final Color TOSCA_LIGHT = new Color(234, 247, 247); // #EAF7F7
    private final Color WHITE = Color.WHITE;
    private final Color RED_SOFT = new Color(229, 115, 115);  // #E57373

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        String status = table.getValueAt(row, 3).toString();

        // ================= BACKGROUND =================
        if (status.equals("0")) {
            // Data bermasalah
            c.setBackground(RED_SOFT);
        } else {
            // Zebra row lembut
            if (row % 2 == 0) {
                c.setBackground(WHITE);
            } else {
                c.setBackground(TOSCA_LIGHT);
            }
        }

        // ================= SELECTED =================
        if (isSelected) {
            c.setBackground(TOSCA);
            c.setForeground(Color.WHITE);
        } else {
            // ================= FOREGROUND =================
            if (status.equals("0")) {
                c.setForeground(Color.WHITE);
            } else {
                c.setForeground(Color.BLACK);
            }
        }

        return c;
    }
}

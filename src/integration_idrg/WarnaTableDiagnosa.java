package integration_idrg;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class WarnaTableDiagnosa extends DefaultTableCellRenderer {

    // ===== WARNA TEMA TOSCA =====
    private final Color TOSCA_PRIMARY   = new Color(43, 179, 177);   // #2BB3B1
    private final Color TOSCA_SECONDARY = new Color(76, 175, 154);   // #4CAF9A
    private final Color TOSCA_LIGHT     = new Color(234, 247, 247);  // #EAF7F7
    private final Color WHITE           = Color.WHITE;

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        String status = table.getValueAt(row, 2).toString();

        // ===== DEFAULT ZEBRA =====
        if (row % 2 == 0) {
            c.setBackground(WHITE);
        } else {
            c.setBackground(TOSCA_LIGHT);
        }
        c.setForeground(Color.BLACK);

        // ===== STATUS =====
        if (status.equals("Primary")) {
            c.setBackground(TOSCA_PRIMARY);
            c.setForeground(Color.WHITE);

        } else if (status.equals("Secondary")) {
            c.setBackground(TOSCA_SECONDARY);
            c.setForeground(Color.WHITE);
        }

        // ===== SELECTED =====
        if (isSelected) {
            c.setBackground(TOSCA_PRIMARY);
            c.setForeground(Color.WHITE);
        }

        return c;
    }
}

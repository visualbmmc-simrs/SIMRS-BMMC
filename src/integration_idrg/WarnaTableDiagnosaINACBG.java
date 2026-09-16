package integration_idrg;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class WarnaTableDiagnosaINACBG extends DefaultTableCellRenderer {

    private static final Color TOSCA_PRIMARY   = new Color(0, 121, 107);
    private static final Color TOSCA_SECONDARY = new Color(0, 96, 100);
    private static final Color TOSCA_ZEBRA     = new Color(235, 245, 244);
    private static final Color ERROR           = new Color(239, 83, 80);

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        String status = String.valueOf(table.getValueAt(row, 2));
        String statusDiagnosa = String.valueOf(table.getValueAt(row, 3));

        // default (zebra)
        c.setBackground(row % 2 == 0 ? Color.WHITE : TOSCA_ZEBRA);
        c.setForeground(Color.BLACK);

        // error (prioritas tertinggi)
        if ("0".equals(statusDiagnosa)) {
            c.setBackground(ERROR);
            c.setForeground(Color.WHITE);
            return c;
        }

        // status
        if ("Primary".equalsIgnoreCase(status)) {
            c.setBackground(TOSCA_PRIMARY);
            c.setForeground(Color.WHITE);
        } else if ("Secondary".equalsIgnoreCase(status)) {
            c.setBackground(TOSCA_SECONDARY);
            c.setForeground(Color.WHITE);
        }

        return c;
    }
}

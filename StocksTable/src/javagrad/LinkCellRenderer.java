import javax.swing.JLabel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTable;
import java.awt.Component;
public class LinkCellRenderer extends JLabel implements TableCellRenderer {

    private ColumnLink link;

    public LinkCellRenderer(ColumnLink link) {
	this.link=link;

    }
    
    public Component getTableCellRendererComponent(JTable table, Object value,
						   boolean isSelected, 
						   boolean hasFocus, 
						   int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
    
            // Select the current value
            setText((String)link.keyToValue(value));
            return this;
        }
    }
    

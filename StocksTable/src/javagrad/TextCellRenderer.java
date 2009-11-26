import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JTable;
public class TextCellRenderer extends DefaultTableCellRenderer{
    public Component getTableCellRendererComponent(JTable table, Object value,
						   boolean isSelected, 
						   boolean hasFocus, 
						   int row, int column) {
	if (value==null)
	    return nullCellRenderer(table,value,isSelected,hasFocus,row,column);
	else
	    return super.getTableCellRendererComponent(table,value,isSelected,
						hasFocus,row,column);
    }

    private Component nullCellRenderer(JTable table, Object value,
				     boolean isSelected, 
				     boolean hasFocus, 
				     int row, int column){
	JLabel blank=new JLabel("");

	if (isSelected) {
	    blank.setForeground(table.getSelectionForeground());
	    super.setBackground(table.getSelectionBackground());
	} else {
	    blank.setForeground(table.getForeground());
	    blank.setBackground(table.getBackground());
	}
	return blank;
    }
}

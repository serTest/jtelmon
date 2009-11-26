import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
public class SQLCellRenderer extends DefaultTableCellRenderer{

    private SQLColumn metaColumn=null;

    public SQLCellRenderer(SQLColumn col){
	metaColumn=col;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
						   boolean isSelected, 
						   boolean hasFocus, 
						   int row, int column) {
	if (value==null){
	    return nullCellRenderer(table,value,isSelected,hasFocus,row,column);
	} else {
	    Object renderVal;
	    
	    if (metaColumn.isYesNo()){
		YesNoBox box=new YesNoBox(SwingConstants.CENTER);
		box.setWidgetValue(value);
		return box;
	    }

	    if (metaColumn.isLinked()){
		renderVal=metaColumn.getLink().keyToValue(value);
	    } else {
		renderVal=value;
	    }
	    return super.getTableCellRendererComponent(table,renderVal,
						       isSelected,
						hasFocus,row,column);
	}
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

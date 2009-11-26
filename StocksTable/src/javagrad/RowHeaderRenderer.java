
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import javax.swing.JTable;
import javax.swing.JList;
import java.awt.Component;
import javax.swing.table.JTableHeader;
import javax.swing.UIManager;

public class RowHeaderRenderer extends JLabel implements ListCellRenderer {
    
    /**
     * Constructor creates all cells the same
     * To change look for individual cells put code in
     * getListCellRendererComponent method
     **/
    private JTable table;
    RowHeaderRenderer(JTable table) {
        JTableHeader header = table.getTableHeader();
	this.table=table;
        setOpaque(true);
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(CENTER);
        setForeground(header.getForeground());
        setBackground(header.getBackground());
        setFont(header.getFont());
    }
    
    /**
     * Returns the JLabel after setting the text of the cell
     **/
    public Component getListCellRendererComponent( JList list,
						   Object value, 
						   int index, 
						   boolean isSelected, 
						   boolean cellHasFocus) {
	ResultSetTableModel model=(ResultSetTableModel)table.getModel();
	if (index>=model.getRowCount()-1 && model.addInProgress()){
	    setIcon(new AddIcon(table.getRowHeight(),table.getRowHeight()));
	} else {
	    setIcon(new DeleteIcon(table.getRowHeight(),table.getRowHeight()));
	}
	return this;
    }

}

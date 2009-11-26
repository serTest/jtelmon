import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
public class RowSummaryPanel extends JPanel{
    RowSummaryPanel(JTable table, int row){
	setLayout(new GridLayout(0,2));
	TableColumnModel colModel=table.getColumnModel();
	

	for (int i=0; i<table.getColumnCount(); i++){
	    TableColumn col=colModel.getColumn(i);
	    add(new JLabel(col.getHeaderValue().toString()));
	    Object val=table.getValueAt(row,i);
	    add(new JLabel(val==null ? "" : val.toString()));
	}

    }
}

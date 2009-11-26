// copied from javaalmanac.com
import javax.swing.event.ListSelectionListener;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

public class SelectionListener implements ListSelectionListener {
    JTable table;
    
    RowClient rowClient;
    
    // It is necessary to keep the table since it is not possible
    // to determine the table from the event's source
    SelectionListener(JTable table) {
	this.table = table;
    }

    public void addRowClient(RowClient client){
	rowClient=client;
    }

    public void valueChanged(ListSelectionEvent e) {
	
	int row;
	
	if (e.getSource() == table.getSelectionModel()){
	    row=table.getSelectedRow();
	    if (!e.getValueIsAdjusting()) {
		// mouse released
		rowClient.rowSelected(row);
	    }
	    
	} 
    
    }
}


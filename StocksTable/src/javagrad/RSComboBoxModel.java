import javax.swing.DefaultComboBoxModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.event.ListDataListener;
import javax.swing.ComboBoxModel;
import javax.swing.ListModel;
import java.util.Vector;
import javax.swing.AbstractListModel;
/**
 * Describe class <code>ResultSetComboBoxModel</code> here.
 *
 * @author <a href="mailto:bremner@unb.ca">David Bremner</a>
 * @version 1.0
 */
public class RSComboBoxModel extends AbstractListModel implements ComboBoxModel {

    // Implementation of javax.swing.ListModel

    
    int dataIndex=1, listIndex=2;
    SQLResultSet results;             // The SQLResultSet to interpret
    ResultSetMetaData metadata;    // Additional information about the results
    int numRows=0;
    IKVTriple selectedItem;

    public int getSize() {
	return numRows;
    }

    public Object getElementAt(int n) {
	try{
	    results.absolute(n+1);
	    return new IKVTriple(n,results.getObject(dataIndex),results.getObject(listIndex));
	} catch (SQLException e){
	    
	    throw new RuntimeException(e);
	}
	    
    }

    
    // Implementation of javax.swing.ComboBoxModel

    public void setSelectedItem(Object object){


	
	IKVTriple triple=(IKVTriple)object;
	selectedItem=triple;
	fireContentsChanged(selectedItem,
			    selectedItem.getIndex(),
			    selectedItem.getIndex());
	
    }

    public Object getSelectedItem() {
	
	return selectedItem;
    }

    public RSComboBoxModel(){
    }


    protected void initWith(String dataColumn,
			    String listColumn,
			    SQLResultSet results) throws SQLException{
	this.results = results;                 // Save the results

	
	dataIndex=results.findColumn(dataColumn);
	listIndex=results.findColumn(listColumn);

	results.last();                         // Move to last row
	numRows = results.getRow();             // How many rows?

    }

}

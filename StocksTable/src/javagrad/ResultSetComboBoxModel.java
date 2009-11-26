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
public class ResultSetComboBoxModel extends AbstractListModel implements ComboBoxModel {

    // Implementation of javax.swing.ListModel

    
    SQLResultSet results;             // The ResultSet to interpret
    ResultSetMetaData metadata;    // Additional information about the results
    int numRows=0;
    Object selectedItem;

    public int getSize() {
	return numRows;
    }

    public Object getElementAt(int n) {
	try{
	    results.absolute(n+1);
	    return new IKVTriple(n,results.getObject(1),results.getObject(2));
	} catch (SQLException e){
	    throw new RuntimeException("Failed getElementAt "+n+"\n"+
				       e.getMessage());
	}
	    
    }

    
    // Implementation of javax.swing.ComboBoxModel

    public void setSelectedItem(Object object) {
	
	IKVTriple triple=(IKVTriple)object;


	fireContentsChanged(triple,
			    triple.getIndex(),
			    triple.getIndex());
	selectedItem=triple;

	
    }

    public Object getSelectedItem() {
	return selectedItem;
    }

    public ResultSetComboBoxModel(SQLResultSet results) throws SQLException {
	this.results = results;                 // Save the results
	metadata = results.getMetaData();       // Get metadata on them

	int numCols = metadata.getColumnCount();    // How many columns?
	if (numCols!=2){
	    throw new IllegalArgumentException(
					       "exactly 2 columns"
					       +"must be specified");
	}

	results.last();                         // Move to last row
	numRows = results.getRow();             // How many rows?
    }


}

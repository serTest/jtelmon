import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import java.sql.ResultSet;
import java.sql.SQLException;
public class SQLTextField extends JTextField implements ActionListener {

    SQLResultSet results;
    String field_name;
    int field_column;

    // Implementation of java.awt.event.ActionListener

    /**
     * update the corresponding SQL field
     *
     * @param actionEvent an <code>ActionEvent</code> value
     */
    public void actionPerformed(ActionEvent actionEvent) {
	if (actionEvent.getSource()==this){
	    try {
		results.updateString(field_name,this.getText());
		// is this a good idea? Seems fast enough.
		results.updateRow();
	    } 
	    catch (SQLException e){throw new RuntimeException(e.getMessage());}

	}
    }
    
    public SQLTextField(SQLResultSet row, String field){

	results=row;
	field_name=field;

	try{
	    row.absolute(1);
	    field_column=results.findColumn(field);

	    setColumns(results.getMetaData().getColumnDisplaySize(field_column));
	    setText(results.getString(field));
	    
	    addActionListener(this);
	}
	catch (SQLException e){throw new RuntimeException(e.getMessage());}

    }
}

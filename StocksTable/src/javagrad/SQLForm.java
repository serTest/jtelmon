import javax.swing.JPanel;
import java.sql.ResultSet;
import java.awt.GridBagLayout;
import java.sql.SQLException;
public class SQLForm extends JPanel{

    public SQLForm(SQLTable table, String []fields, int columns, 
		   SQLResultSet results){
	setLayout(new GridBagLayout());

	int limit;
	
	limit=fields.length/columns;

	if (fields.length % columns!=0)
	    limit++;

	for(int i=0; i<limit; i++){
	    for (int j=0; (j<columns) && (i*columns+j)<fields.length; j++){
		int index=(i*columns+j);

		SQLField field=new SQLField(table
					    .findColumn(fields[index]),
					    results);
		field.addToGridBag(this,i,j*2);
	    }
	}
    }
}

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.sql.ResultSet;
public class IDPanel extends JPanel{
    public IDPanel(SQLDatabase database, int id, SQLResultSet results){

	    SQLTable table=database.findTable("student");
	    setLayout(new GridBagLayout());
	    
	    String[] fields={"id", "last_name", "first_name"};
	    
	    for(int i=0; i< fields.length; i++){
		SQLField field=new SQLField(table.findField(fields[i]),results);
		field.addToGridBag(this,0,i*2);
	    }
	    
    }

    
}

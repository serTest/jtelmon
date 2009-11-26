import javax.swing.BoxLayout;
import java.sql.ResultSet;
import javax.swing.JPanel;
import java.sql.SQLException;
import java.awt.GridBagLayout;
public class BasicStudentPanel extends JPanel{

    public BasicStudentPanel(SQLDatabase database,
		       String tableName, 
		       int id){
	try{
	    SQLTable table=database.findTable(tableName);
	    ResultSet results= database.query("select * from "+tableName
					  +" where id="+id);
	
	    results.last();
	    if (results.getRow()==0){
		results.moveToInsertRow();
		results.updateInt("id",id);
		results.insertRow();
	    }

	    setLayout( new GridBagLayout());
	    // NOTE: this makes assumptions about the database layout
	    int i;
	    for( i=0; i< table.getNumColumns()-1; i+=2){
		SQLField field=new SQLField(table.getColumn(i),results);
		field.addToGridBag(this,i/2,0);
		if (i<table.getNumColumns()-1){
		    SQLField field2=new SQLField(table.getColumn(i+1),results);
		    field2.addToGridBag(this,i/2,2);
		}
	    }
	    SQLField gcField=new SQLField(table.findField("General_comments"),results);
	    gcField.addToGridBag(this,i/2+1,0,3);

	} catch (SQLException e){
	    throw new RuntimeException(e.getMessage());
	}

    }
}

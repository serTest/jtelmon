import javax.swing.BoxLayout;
import java.sql.ResultSet;
import javax.swing.JPanel;
import java.sql.SQLException;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
public class PrereqPanel extends JPanel{

    SQLResultSet results=null;
    SQLTable table;
    public PrereqPanel(final SQLDatabase database,
		       final int id,final SQLResultSet studentRow){

	    final String tableName="prereqcourses";
	    
	    table=database.findTable(tableName);

	    setLayout(new BorderLayout());
	    IDPanel idPanel=new IDPanel(database,id,studentRow);
	    add(idPanel,BorderLayout.NORTH);


	    SwingWorker worker=new SwingWorker(){
		    public Object construct(){
			try{
			    results= 
				database.query("select * from "+tableName
					       +" where student="+id);
	
			    results.last();
			    if (results.getRow()==0){
				results.moveToInsertRow();
				results.updateInt("student",id);
				results.insertRow();
			    }
			} catch (SQLException e){
			    throw new RuntimeException(e);
			}
			return results;
		    }
		    public void finished(){
			layoutForm();
		    }
		};
	    worker.start();
		

    }

    private void layoutForm(){
	JPanel fieldPanel=new JPanel();
	    
	fieldPanel.setLayout( new GridBagLayout());
	// NOTE: this makes assumptions about the database layout
	// skip zeroth column, add course fields
	int i;
	for( i=1; i< table.getNumColumns()-4; i+=2){
	    SQLField field=new SQLField(table.getColumn(i),results);
	    field.addToGridBag(fieldPanel,i/2,0);
	    if (i<table.getNumColumns()-1){
		SQLField field2=new SQLField(table.getColumn(i+1),results);
		field2.addToGridBag(fieldPanel,i/2,2);
			    }
			}
	SQLField gcField=new SQLField(table.findField("General_comments"),results);
	SQLField doneField=new SQLField(table.findField("completed"),results);
	SQLField whoField=new SQLField(table.findField("GAU_member"),results);
	gcField.addToGridBag(fieldPanel,i/2+1,0,3);
	whoField.addToGridBag(fieldPanel,i/2+2,0);
	doneField.addToGridBag(fieldPanel,i/2+2,2);

	add(fieldPanel,BorderLayout.CENTER);
    }

}

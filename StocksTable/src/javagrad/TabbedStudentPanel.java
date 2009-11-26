import javax.swing.JTabbedPane;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.awt.EventQueue;
public class TabbedStudentPanel extends JTabbedPane{
   

    public TabbedStudentPanel(final SQLDatabase database, int id){
	EditStudentPanel mainPanel=null;
	try{
	    if (id==0){
	    }




	    String tableName="student"; 
	    SQLResultSet results= database.query("select * from "+tableName
					  +" where id="+id);

	    Debug.println("got ID"+id);
	    
	
	    results.last();
	    if (results.getRow()==0){
		results.moveToInsertRow();
		results.updateInt("id",id);
		results.insertRow();
		id=results.getInt("id");
	    }


	    final SQLResultSet finalResults=results;

	    mainPanel=new EditStudentPanel(database,id,results);
	    insertTab("Student", null, mainPanel, null,0);

	    ScorePanel scorePanel=new ScorePanel(database,id,finalResults);
	    insertTab("Tests",null,scorePanel,null,1); 
	    PrereqPanel prereqPanel= new PrereqPanel(database, 
						     id,finalResults);
	    insertTab("Prerequisites",null,prereqPanel,null,2);


	    CountryPanel countryPanel=new CountryPanel(database);
	    insertTab("Countries", null, countryPanel, null,3);
	    UniversityPanel uniPanel=new UniversityPanel(database);
	    insertTab("Universities",null,uniPanel,null,4);	    

	} catch (SQLException e){
	    UI.message(e.getMessage());
	    throw new RuntimeException(e.getMessage());
	}
	





    }
}

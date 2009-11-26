import javax.swing.BoxLayout;
import java.sql.ResultSet;
import javax.swing.JPanel;
import java.sql.SQLException;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
public class ScorePanel extends JPanel{

    public ScorePanel(SQLDatabase database,
		       int id,SQLResultSet results){

	    setLayout(new BorderLayout());

	    
	    add(new IDPanel(database,id,results),BorderLayout.NORTH);

	    add(new SQLForm(database.findTable("student"),
			    new String[] {
				"GRE_V",        
				"GRE_V_%",
				"GRE_Q",        
				"GRE_Q_%",      
				"GRE_A",        
				"GRE_A_%",
				"GRE_waived",   
				"TOEFL",
				"ELT",
				"TWE"},2,results),BorderLayout.CENTER);

    }
}

import javax.swing.JPanel;
import java.sql.*;
import java.awt.FlowLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import java.sql.ResultSet;
import java.awt.EventQueue;
public class EditStudentPanel extends JPanel{

    private int studentId=0;
    public EditStudentPanel(final SQLDatabase database,
			    final int id,final SQLResultSet results) 
	throws SQLException{

	studentId=id;



	UI.message("Retrieving student information");

	final SQLTable table=database.findTable("student");
	
	setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	
	JPanel topPanel=new JPanel();
	topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.X_AXIS));
	

	topPanel.add(new SQLForm(table,
			new String[] {
			    "id",
			    "program",
			    "last_name",
			    "first_name",
			    "address",
			    "address2",
			    "country",
			    "citizenship",
			    "StudentVisa",
			    "status",
			    "status_date",
			    "general_comments"},1,results));

	TablePanel resintPanel=new TablePanel(database,"research interest",
							  "student",new Integer(studentId),
					      new String[] {
						  "id","student","interest"});
	topPanel.add(resintPanel);

	add(topPanel);
	
    
	
	TablePanel degreePanel=new TablePanel(database,"degree",
					      "student", new Integer(studentId),
					  new String[] {"id","student", 
							"university", 
							"degree", "marks", 
							"Graduation",
							"Comments", 
							"Completed"});
	add(degreePanel);

	TablePanel recommendPanel=new TablePanel(database,
							     "recommendation",
						 "student", new Integer(studentId),
				      new String[] {"id","student", "rate", 
						    "weight", 
						    "name_of_referee",
						    "institution",
						    "Comments", "GAU_member", 
						    "Completed"});
	add(recommendPanel);
    }
    
}

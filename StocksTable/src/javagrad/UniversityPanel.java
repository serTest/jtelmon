import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class UniversityPanel extends SelectionPanel{


    public UniversityPanel(SQLDatabase database) {
	super(database,"universities", 
	      null, "name", "id");
    }


}

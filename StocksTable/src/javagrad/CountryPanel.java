import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.awt.EventQueue;
public class CountryPanel extends SelectionPanel{


    public CountryPanel(SQLDatabase database) {
	super(database,"country", 
	      new String[] {"id", "name"}, "name", "id");
    }


}

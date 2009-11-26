import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.awt.EventQueue;
public class SelectionPanel extends JPanel implements ActionListener{

    SQLDatabase database;
    JTextField  searchField;
    String searchFieldName;
    String [] queryFields;
    TablePanel tablePanel;
    String tableName;
    RowClient client;

    public SelectionPanel(SQLDatabase database,
			  String tableName, 
			  String[] fields,
			  String searchFieldName,
			  String keyFieldName ) {

	this.database = database;
	this.tableName=tableName;
	this.searchFieldName=searchFieldName;
	queryFields=fields;
	// Create the Swing components we'll be using
	searchField = new JTextField(20);     // Lets the user enter a query
	tablePanel = 
	    new TablePanel(database, tableName, keyFieldName, null,
			   searchFieldName,
		       fields);         

	setLayout(new BorderLayout());
	JPanel topPanel=new JPanel();
	topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.X_AXIS));
	topPanel.add(new JLabel(searchFieldName));
	topPanel.add(searchField);

	// Place the components within this window
	add(topPanel, BorderLayout.NORTH);
	add(tablePanel, BorderLayout.CENTER);
	// Now hook up the JTextField so that when the user types a query
	// and hits ENTER, the query results get displayed in the JTable
	searchField.addActionListener(this);

    } 

    public void setSelectable(boolean flag){
	tablePanel.setSelectable(flag);
    }
    public void actionPerformed(ActionEvent e) {
	
	database.select( queryFields, 
			 tableName, 
			 searchFieldName +
			 " like \""+searchField.getText()+"\"", 
			 searchFieldName, 
			 tablePanel.getTable());
	tablePanel.setRowHeader();
    }
    public JTable getTable(){
	return tablePanel.getTable();
    }

    public void addKeyClient(KeyClient client){
	tablePanel.addKeyClient(client);
    }

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.awt.DisplayMode;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Top user interface class. Used to make a frame or an applet.
 *
 * @author <a href="mailto:bremner@unb.ca">David Bremner</a>
 * @version 1.0
 */
public class StudentPanel extends JPanel implements ActionListener,  KeyClient{

    String username,password;
    String databaseName;
    String databaseType=null;
    SQLDatabase database=null;
    StudentSelectionPanel selectionPanel=null;
    TabbedStudentPanel editPanel=null;
    JScrollPane scrollPane=null;

    
    final static String hostname="this.host.needs.to.be.changed.ca";

    JButton selStudentButton=null;
    JTextArea messageField;
    public StudentPanel()	throws ClassNotFoundException,SQLException{

	setLayout(new BorderLayout());


	
	messageField=new JTextArea(2,80);
	messageField.setEditable(false);
	JScrollPane messageScroller=new JScrollPane(messageField);
	messageScroller.setBorder(new TitledBorder(new EtchedBorder(),"Messages:"));

	UI.setMessageField(messageField);

	add(messageScroller, BorderLayout.SOUTH);
	setVisible(true);
	


	boolean trying=true;

	while(trying){
	    getIDandPassword(this);
	    try{
		database=new SQLDatabase(
					 hostname+"/"+databaseName,
					 username,password);
		trying=false;
	    } catch (SQLException e){
		String state=e.getSQLState();
		if (!state.equals(SQLError.SQL_STATE_INVALID_AUTH_SPEC))
		    trying=false;
		else
		    UI.popupMessage("Error","Login Failed");
	    }
	    
	    
	}

	makeButtonPanel();

	






	UI.setUsername(username);


	
	SQLTable studentTable=database.findTable("student");
 	studentTable.findColumn("country").link("country","id","name");

	studentTable.findColumn("citizenship").
	    link("country","id","name");

	studentTable.findColumn("id").setWritable(false);

 	database.findTable("universities").findColumn("country").
	    link("country","id","name");

	SQLTable degreeTable=database.findTable("degree");
	degreeTable.findColumn("university").link("universities","id", "name");
	degreeTable.findColumn("student").setHidden(true);
	degreeTable.findColumn("id").setHidden(true);
    
	SQLTable recoTable=database.findTable("recommendation");
	recoTable.findColumn("institution").link("universities","id", "name");
	recoTable.findColumn("id").setHidden(true);
	recoTable.findColumn("student").setHidden(true);

	SQLTable resintTable=database.findTable("research interest");
	resintTable.findColumn("id").setHidden(true);
	resintTable.findColumn("student").setHidden(true);
	selectionMode();
	
    } 


    /**
     * Describe <code>makeButtonPanel</code> method here.
     *
     */
    private void makeButtonPanel(){


	
	JPanel buttonPanel =new JPanel();

	selStudentButton=new JButton("Select Student");
	JButton quitButton=new JButton("Quit");

	quitButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    System.exit(0);
		}
	    });

	selStudentButton.addActionListener(this);
	JLabel databaseLabel=new JLabel("Editing "+databaseType +" data");
	buttonPanel.add(databaseLabel);
	buttonPanel.add(selStudentButton);
	buttonPanel.add(quitButton);

	this.add(buttonPanel,BorderLayout.NORTH);
    }



    private void selectionMode(){
	if (scrollPane != null){
	    remove(scrollPane);
	}
	selectionPanel=new StudentSelectionPanel(database);
	selectionPanel.addKeyClient(this);

	add(selectionPanel,   BorderLayout.CENTER);
	validate();
	repaint();
    }


    public void keySelected(Object obj){
	int id=0;

	if (obj==null ||    ((Integer)obj).intValue()==0){
	    ResultSetTableModel model=((ResultSetTableModel)selectionPanel.getTable().getModel());
	    model.rowAddCommit();
	    id=((Integer)model.getValueAt(model.getRowCount()-1,"id")).intValue();
	    
	} else {
	    id = ((Integer)obj).intValue();
	}

	remove(selectionPanel);


	editPanel= new TabbedStudentPanel(database,id);
	scrollPane=new JScrollPane(editPanel);
	add(scrollPane,  BorderLayout.CENTER);
	validate();

	setVisible(true);
    }
		

    private void getIDandPassword(JPanel parent) {
        JPanel      connectionPanel;
	String[] connectOptionNames = { "Login", "Cancel" };
	String   connectTitle = "Grad Admissions Database";

	
        // Create the labels and text fields.
        JLabel     userNameLabel = new JLabel("MySQL user:  ", JLabel.RIGHT);
        JTextField userNameField = new JTextField(10);
        JLabel     passwordLabel = new JLabel("Password:   ", JLabel.RIGHT);
        JPasswordField passwordField = new JPasswordField(10);
        connectionPanel = new JPanel(false);
        connectionPanel.setLayout(new BoxLayout(connectionPanel,
                                                BoxLayout.X_AXIS));
        JPanel namePanel = new JPanel(false);
        namePanel.setLayout(new GridLayout(0, 1));
        namePanel.add(userNameLabel);
        namePanel.add(passwordLabel);
        JPanel fieldPanel = new JPanel(false);
        fieldPanel.setLayout(new GridLayout(0, 1));
        fieldPanel.add(userNameField);
        fieldPanel.add(passwordField);

	JPanel databasePanel=new JPanel(false);
	databasePanel.setLayout(new GridLayout(0,1));
	databasePanel.add(new JLabel("database"));
	JComboBox databaseComboBox=new JComboBox(new String[] {"Live", "Test"});
	databasePanel.add(databaseComboBox);
						 
        connectionPanel.add(namePanel);
        connectionPanel.add(fieldPanel);
	connectionPanel.add(databasePanel);
        // Connect or quit
        if(JOptionPane.showOptionDialog(parent, connectionPanel, 
                                        connectTitle,
                                        JOptionPane.OK_CANCEL_OPTION, 
                                        JOptionPane.INFORMATION_MESSAGE,
                                        null, connectOptionNames, 
                                        connectOptionNames[0]) != 0) 
	    {
		System.exit(0);
	    }
        username = userNameField.getText();
        password = new String(passwordField.getPassword());
	databaseType=(String)databaseComboBox.getSelectedItem();
	if (databaseType==null){
	    databaseType="Live";
	}
	if (databaseType.equalsIgnoreCase("Test")){
	    databaseName="testgradadmin";
	} else{
	    databaseName="gradadmission";
	}
    }
	


    // Implementation of java.awt.event.ActionListener

    /**
     * Handle events from button panel
     *
     * @param actionEvent an <code>ActionEvent</code> value
     */
    public void actionPerformed(ActionEvent actionEvent) {
	if (actionEvent.getSource()==selStudentButton){
	    selectionMode();
	}
	
    }
    
}

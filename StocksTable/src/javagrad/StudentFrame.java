import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.awt.DisplayMode;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;


public class StudentFrame extends JFrame {


    JLabel messageField;
    public StudentFrame()	throws ClassNotFoundException,SQLException{

	
	getContentPane().add(new StudentPanel(),   BorderLayout.CENTER);
	pack();
	
    } 



    public static void main(String args[]) throws Exception {

// 	  DisplayMode display=GraphicsEnvironment.
// 	    getLocalGraphicsEnvironment().getDefaultScreenDevice().
// 	    getDisplayMode();


	System.setProperty("java.net.preferIPv4Stack","true");

	JFrame sf=new StudentFrame();
	// Set the size of the StudentFrame, then pop it up

// 	sf.setSize(Math.min(display.getWidth()-50,1200), 
// 		   Math.min(display.getHeight()-100,700));


	sf.setSize(1019,650);
	sf.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) { System.exit(0); }
	    });
	sf.setVisible(true);
    }
}

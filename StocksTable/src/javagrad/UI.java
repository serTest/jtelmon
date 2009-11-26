/**
 * Provide a convenient way to access global user interface 
 * information.
 *
 * @author <a href="mailto:bremner@unb.ca">David Bremner</a>
 * @version 1.0
 */

import javax.swing.JTextArea;
import java.awt.EventQueue;
import java.io.StringWriter;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import java.awt.FontMetrics;

public class UI{


    private static JTextArea messageField=null;
    private static String username;

    
    /**
     * Gets the value of username
     *
     * @return the value of username
     */
    public static String getUsername()  {
	return UI.username;
    }

    /**
     * Sets the value of username
     *
     * @param argUsername Value to assign to this.username
     */
    public static void setUsername(String argUsername) {
	UI.username = argUsername;
    }

    
    public static void setMessageField(JTextArea field){
	messageField=field;
	field.setEditable(false);
    }
    
    

    /**
     * Gets the value of messageField
     *
     * @return the value of messageField
     */
    public static JTextArea getMessageField()  {
	return messageField;
    }

    
    public static void message(final String text){
	if (messageField !=null){
	    // make thread safe
	    EventQueue.invokeLater(new Runnable(){
		    public void run(){
			messageField.append(text+"\n");
		    }
		});
				   
	}
    }

    public static void fatal (Exception e){
	fatal("",e);
    }
    public static void fatal (String s, Exception e){
	StringWriter dest=new StringWriter();
	PrintWriter buf=new PrintWriter(dest);
	e.printStackTrace(buf);
	fatal(s+"\n"+dest.toString());

    }
    public static void fatal (String s){
	JOptionPane.showMessageDialog(null,  s, "Fatal Error",
				      JOptionPane.ERROR_MESSAGE);
	BugReport.report(username,s);

	System.exit(1);
    }
    
    public static void popupMessage(String title, String message){
	
	JOptionPane.showMessageDialog(messageField.getRootPane(),   
				      message,title,
				      JOptionPane.ERROR_MESSAGE);
    }
    
    public static FontMetrics getDefaultMetrics(){
	return messageField.getFontMetrics(messageField.getFont());
    }
}

/**
 * Provide a simple interface to updating the GUI look and feel.
 *
 * @author <a href="mailto:bremner@unb.ca">David Bremner</a>
 * @version 1.0
 */import java.awt.Font;
import java.util.Vector;
import java.awt.Component;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import java.util.Enumeration;
import javax.swing.SwingUtilities;

public class AppGUI{

    private int fontSize=12;
    private Font font;
    private Vector fontDefaults;
    private  Component root;
    
    
    /**
     * Creates a new <code>AppGUI</code> instance.
     *
     * @param rootPane a <code>Component</code> value
     */
    public AppGUI(Component rootPane){
	font = new Font("Dialog", Font.PLAIN, fontSize);
    }

    private void findFontDefaults(){
	UIDefaults defaults=UIManager.getDefaults();
	fontDefaults=new Vector();
	for (Enumeration e = defaults.keys() ; e.hasMoreElements() ;) {
	    String key=(String)e.nextElement();
	    int len=key.length();

	    if (key.substring(len-5,len).equals(".font")){
		Debug.println(key);
		fontDefaults.addElement(key);
	    }
	}
     }
 
    /**
     * Propagate any changes to the AppGUI object to the application
     *
     */
    public void update(){
	try {
	    
	    for (Enumeration e=fontDefaults.elements(); e.hasMoreElements();){
		UIManager.put(e.nextElement(), font);
	    }
	    SwingUtilities.updateComponentTreeUI(root);
	}
	catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

}

import javax.swing.JApplet;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import java.io.StringWriter;
import java.io.PrintWriter;
public class StudentApplet extends JApplet{
    public void init(){
	try{
	    getContentPane().add(new StudentPanel(),   BorderLayout.CENTER);
	} 
	catch(Exception e){
	    StringWriter dest=new StringWriter();
	    PrintWriter buf=new PrintWriter(dest);
	    e.printStackTrace(buf);
	    JOptionPane.showMessageDialog(this,
				   dest.toString());
		
	    
	    throw new RuntimeException(e.getMessage());
	}
     
    }

}

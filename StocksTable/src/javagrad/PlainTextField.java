import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;
public class PlainTextField extends JTextField 
    implements CellEditorWidget{


    

    public void setWidgetValue(Object o){
	setText(o==null ? "" : o.toString());
    }

    public Object getWidgetValue(){
	return getText();
    }

    public PlainTextField(){
	
    }
}

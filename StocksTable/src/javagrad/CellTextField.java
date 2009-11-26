import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatter;
import javax.swing.CellEditor;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.text.Format;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
public class CellTextField extends JFormattedTextField 
    implements CellEditorWidget{

    AbstractFormatter formatter;
    Object defaultValue=null;

    public CellTextField(AbstractFormatter formatter){
	super(formatter);
	this.formatter=formatter;
    }



    public void setWidgetValue(Object o){
	setValue(o);
    }

    public Object getWidgetValue(){
 	try{
 	    Debug.println("committing Edits"+getFormatter());
 	    commitEdit();
 	}  catch (ParseException e){
 	    UI.message("Unable to parse "+getText());
	    Object oldValue=getValue();
	    try {
		String editString=formatter.valueToString(oldValue);
		Object test=formatter.stringToValue(editString);
	    } catch (ParseException pe){
		UI.message("Old value also bad, reverting to default");
		if (defaultValue==null){
		    UI.fatal("unable to recover",e);
		}
		setValue(defaultValue);
	    }
 	}
	return getValue();
    }
}

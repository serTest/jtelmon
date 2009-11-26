import javax.swing.DefaultCellEditor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
/**
 * this class is no longer really needed
 * @author <a href="mailto:bremner@unb.ca">David Bremner</a>
 * @version 1.0
 */
public class TextCellEditor extends DefaultCellEditor  {

    /**
     * Creates a new <code>TextCellEditor</code> instance from a given
     * JTextField
     *
     * @param textField a <code>JTextField</code> value
     */
    public TextCellEditor(JTextField textField){
	super(textField);
    }

    /**
     * The default constructor uses a JTextField
     *
     */
    public TextCellEditor(){
	super(new JTextField());
	setClickCountToStart(1);
    }



}

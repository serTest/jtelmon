import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import java.util.Vector;
public class ComboBoxEditor extends DefaultCellEditor {
    public ComboBoxEditor(Vector items) {
	super(new JComboBox(items));
    }
}

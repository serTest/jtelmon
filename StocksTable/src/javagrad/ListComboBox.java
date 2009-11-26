import javax.swing.JComboBox;
import java.util.Vector;


public class ListComboBox extends JComboBox implements CellEditorWidget {

    public ListComboBox(Vector list){
	super(list);
    }
    
    
    // implement CellEditorWidget

    public void setWidgetValue(Object o){
	setSelectedItem(o);
    }
    
    public Object getWidgetValue(){
	return  getSelectedItem();
    }
}

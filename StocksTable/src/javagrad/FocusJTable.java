import javax.swing.JTable;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.table.TableCellEditor;
import java.awt.Component;
public class FocusJTable extends JTable implements FocusListener {

    // Implementation of     java.awt.event.FocusListener

    /**
     * Describe <code>focusGained</code> method here.
     *
     * @param focusEvent a <code>FocusEvent</code> value
     */
    public void focusGained(FocusEvent focusEvent) {
	// ignored 
    }

    /**
     * Describe <code>focusLost</code> method here.
     *
     * @param focusEvent a <code>FocusEvent</code> value
     */
    public void focusLost(FocusEvent focusEvent) {
	
	Component opposite=focusEvent.getOppositeComponent();

	if (this==opposite)
	    return;

	Debug.println("this ="+this.hashCode()+
		      "opposite="+
		      (opposite==null ? -1 : opposite.hashCode() ));
	

	if (focusEvent.isTemporary())
	    return;

	if (hasFocus() || !isEditing()) 
	    return;

	TableCellEditor editor=getCellEditor();
	Component component=getEditorComponent();
	if (editor == null || component==null || component.hasFocus())
	    return;

	if (editor!=null && !focusEvent.isTemporary()){
	    Debug.println("table lost focus; stopping editing");
	    editor.stopCellEditing();
	}
    }



    

    public Component prepareEditor(TableCellEditor editor, int row ,int col ){
	Component comp = super.prepareEditor(editor,row,col);
	comp.addFocusListener(this);
	return comp;
    }
}

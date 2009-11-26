import java.awt.Component;
import javax.swing.event.CellEditorListener;
import java.util.EventObject;
import javax.swing.JTable;
import javax.swing.CellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.AbstractCellEditor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.text.DefaultFormatter;
public class SQLCellEditor extends AbstractCellEditor  
    implements ActionListener, ItemListener, TableCellEditor, PropertyChangeListener {

    private SQLWidgetFactory factory;
    private SQLColumn metaColumn;
    
    private CellEditorWidget widget=null;

    public SQLCellEditor(){
	factory=null;
	metaColumn=null;
    }

    public SQLCellEditor(SQLColumn col){
	factory=new SQLWidgetFactory(col);
	metaColumn=col;
    }

    public Component getTableCellEditorComponent(JTable JTable, 
						 Object value, 
						 boolean isSelected, 
						 int row, int column) {

	Component rval;

	widget=factory.getComponent();


	Debug.println("generated new editor widget "+widget.hashCode());
	
	rval=(Component)widget;

	if (widget instanceof JComboBox){
	   ((JComboBox)widget).addItemListener(this);
	}

	if (widget instanceof YesNoBox){
	    ((YesNoBox)widget).addActionListener(this);
	}

	if (value==null && !metaColumn.isNullEditable())
	    value=metaColumn.getDefault();

	widget.setWidgetValue(value);
	rval.addPropertyChangeListener("value",this);
	
	return rval;
    }
    
    public void propertyChange(PropertyChangeEvent e){
	    
       	Debug.println("property changed "+e.getPropertyName()+" "+e.getNewValue());
	fireEditingStopped();

    }
    public Object getCellEditorValue(){
	    Debug.println("retrieving value from "+widget.hashCode());
	    return widget.getWidgetValue();
    }

    // Implementation of java.awt.event.ItemListener

    /**
     * Describe <code>itemStateChanged</code> method here.
     *
     * @param itemEvent an <code>ItemEvent</code> value
     */
    public void itemStateChanged(ItemEvent itemEvent) {
	Debug.println("got item event");
	fireEditingStopped();
    }


   
    // Implementation of java.awt.event.ActionListener

    /**
     * Describe <code>actionPerformed</code> method here.
     *
     * @param actionEvent an <code>ActionEvent</code> value
     */
    public void actionPerformed(ActionEvent actionEvent) {
	Debug.println("Got action event");
	fireEditingStopped();
    }
    
    public boolean shouldSelectCell(EventObject anEvent){
	return true;
    }
}

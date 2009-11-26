import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventObject;
import javax.swing.event.CellEditorListener;
import javax.swing.CellEditor;
import javax.swing.ListCellRenderer;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
public class LinkComboBoxEditor  extends DefaultCellEditor {

    LinkComboBox comboBox=null;
    public LinkComboBoxEditor(ColumnLink link)throws SQLException{
       
	super(new LinkComboBox(link));
	comboBox=(LinkComboBox)getComponent();
    }

    
    
    public Object getCellEditorValue(){
	IKVTriple current=(IKVTriple) comboBox.getSelectedItem();

	return current.getKey();
    }
	

}




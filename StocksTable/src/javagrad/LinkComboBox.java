import java.sql.ResultSet;
import javax.swing.JComboBox;
import java.sql.SQLException;

public class LinkComboBox extends JComboBox implements CellEditorWidget {
    public LinkComboBox(ColumnLink link)throws SQLException{
	setModel(new LinkComboBoxModel( link));
	setRenderer(new RSComboBoxRenderer());
    }
    
    
    // note, I think having to add this is a bug, either in my code or 
    // in swing
    public void setSelectedItem(Object o){

	getModel().setSelectedItem(o);
    }

    // implement CellEditorWidget

    public void setWidgetValue(Object o){
	setSelectedItem(o);
    }
    
    public Object getWidgetValue(){
	IKVTriple current=(IKVTriple) getSelectedItem();

	return current.getKey();
    }
}

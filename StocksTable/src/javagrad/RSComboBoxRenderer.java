import javax.swing.ListCellRenderer;
import javax.swing.JList;
import java.awt.Component;
import javax.swing.JLabel;
public class RSComboBoxRenderer implements ListCellRenderer {
    public Component getListCellRendererComponent(
						  JList list, 
						  Object value, 
						  int index, 
						  boolean isSelected, 
						  boolean cellHasFocus){
	JLabel l = new JLabel();
	String text="";
	if (value !=null){
	    Object o=((IKVTriple)value).getValue();
	    if (o!=null)
		text=o.toString();
	}
	l.setText(text);
	return l;
    };
};

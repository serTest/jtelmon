import javax.swing.JCheckBox;


public class YesNoBox extends JCheckBox implements CellEditorWidget {
    
    
    public YesNoBox(int alignment){
	setHorizontalAlignment(alignment);
    }
    // implement CellEditorWidget

    public void setWidgetValue(Object o){
	if (((String)o).equalsIgnoreCase("yes"))
	    setSelected(true);
	else 
	    setSelected(false);
    }
    
    public Object getWidgetValue(){
	if (isSelected())
	    return "yes";
	else 
	    return "no";
    }
}

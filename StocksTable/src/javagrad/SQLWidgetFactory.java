import java.awt.Component;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.sql.SQLException;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;
public class SQLWidgetFactory{
    private SQLColumn column;


    public static final int FT_STRING=0;
    public static final int FT_DATE=1;
    public static final int FT_ENUM=2;
    public static final int FT_YES_NO=3;
    public static final int FT_LINK=4;
    public static final int FT_FLOAT=5;
    public static final int FT_INT=6;
    
    private int fieldType=FT_STRING;

    
    public SQLWidgetFactory(SQLColumn column){
	this.column=column;
	
	if (column.isLinked())
	    fieldType=FT_LINK;
	else if (column.isYesNo()){
	    fieldType=FT_YES_NO;
	} else {

	    switch (column.getType()){
	    case SQLColumn.CT_ENUM:
		fieldType=FT_ENUM;
		break;
	    case SQLColumn.CT_CHAR:
	    case SQLColumn.CT_VARCHAR:
	    case SQLColumn.CT_TEXT:
		fieldType=FT_STRING;
		break;
	    case SQLColumn.CT_DATE:
		fieldType=FT_DATE;
		break;
	    case SQLColumn.CT_INT:
		fieldType=FT_INT;
		break;
	    case  SQLColumn.CT_FLOAT:
		fieldType=FT_FLOAT;
		break;
	    default:
		UI.fatal("Unimplimented column type "+column.getType());
	    }
	}
	Debug.println("choose field type "+fieldType+"for "+column.getColumnName()+column.isNullEditable());
	
    }
    CellEditorWidget getComponent(){
	switch (fieldType){
	case FT_LINK:
	    try{return (new LinkComboBox(column.getLink())); } 
	    catch (SQLException e){UI.fatal(e);}
	    break;
	    
	case FT_ENUM:
	    return (new ListComboBox(column.getValueList()));
	case FT_DATE:
	    return (new SQLDateField());
	case FT_FLOAT:
	    return new CellTextField(new FloatFormatter());
	case FT_YES_NO:
	    return new YesNoBox(SwingConstants.CENTER);
	}
	return new PlainTextField();
    }
	    
}

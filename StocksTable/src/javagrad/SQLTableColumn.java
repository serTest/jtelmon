import javax.swing.table.TableColumn;
import java.sql.SQLException;
import java.awt.FontMetrics;
import java.util.Vector;
import javax.swing.UIManager;
import java.util.Enumeration;
import javax.swing.JComboBox;
public class SQLTableColumn extends TableColumn{
    public SQLTableColumn(int index,SQLColumn column) 
	throws SQLException{

	super(index, 
	      0,
	      new SQLCellRenderer(column),
	      new SQLCellEditor(column));

	int pixelWidth=guessPixelWidth(column);

	setPreferredWidth(pixelWidth);
	setHeaderValue(column.getLabel());

    }


    public int guessPixelWidth(SQLColumn column){

	Vector labels;
	
	FontMetrics metrics=UI.getDefaultMetrics();
	int width=0;
	if (column.isLinked() || column.getType()==SQLColumn.CT_ENUM ){
	    
	    if (column.isLinked() )
		labels=column.getLink().getDisplayList();
	    else
		labels=column.getValueList();
	    JComboBox cbox=new JComboBox(labels);
	    width=cbox.getPreferredSize().width;
	} else{
	    if (column.getType()==SQLColumn.CT_DATE){
		width=metrics.stringWidth("0000-00-00");
	    } else {
		int columns=Math.min(column.getDisplayColumns(),20);
		width=metrics.charWidth('m')*columns;
	    }
	}
	
	Debug.println("guessing "+width+"for field"
			   +column.getLabel());
	
	return Math.max(width, metrics.stringWidth(column.getLabel()));
    }


}

import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
public class SQLDateField extends CellTextField{

    public SQLDateField(){

	super( new SQLDateFormatter());
	
    }


    public void setWidgetValue(Object o){
	
	setValue( o);
    }

}

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.text.ParseException;
public class SQLDateFormatter extends DateFormatter{
    private final static  String formatString="yyyy-MM-dd";

    
    public SQLDateFormatter(){
	super(new SimpleDateFormat(formatString));
	setValueClass(java.sql.Date.class);
	setOverwriteMode(true);
    }

    public Object stringToValue(String str) throws ParseException{
	try{
	    return java.sql.Date.valueOf(str);
	} catch (IllegalArgumentException e){
	    throw new ParseException(e.getMessage(),0);
	}
    }
}

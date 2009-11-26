import javax.swing.text.NumberFormatter;
import java.text.ParseException;
public class FloatFormatter extends NumberFormatter{
    public FloatFormatter(){
	setValueClass(Float.class);
    }

    public Object stringToValue(String str) throws ParseException{
	Debug.println("converting "+str);
	
	if (str==null)
	    return new Float(0.0);
	else
	    return super.stringToValue(str);
    }
		    
    
}

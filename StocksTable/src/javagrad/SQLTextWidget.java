import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class SQLTextWidget extends JFormattedTextField{
    public SQLTextWidget(SQLColumn col){

	try{
	    if (col.getType()==SQLColumn.CT_DATE){
		String formatString="####-##-##";
		MaskFormatter mask=new MaskFormatter(formatString);
		mask.setPlaceholderCharacter('0');
		setFormatter(mask);
	    }
	} catch (ParseException e){
	    UI.fatal(e);
	}
		
	
    }
}

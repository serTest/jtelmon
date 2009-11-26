import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.Vector;
import java.io.IOException;
import javax.swing.table.TableColumn;
import javax.swing.table.TableCellEditor;
import java.sql.SQLException;
import javax.swing.table.TableCellRenderer;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import java.sql.Date;
import javax.swing.JTextField;
import java.awt.FontMetrics;
public class SQLColumn{

    private int type=-1;
    private Vector valueList=null;
    private int width=0;  // in characters
    private String columnName;
    private SQLTable table;

    private boolean isPrimary=false;
    private boolean isWritable=false;
    private boolean isNullable=false;
    private Object  defaultValue=null;
 
    private boolean isLinked=false;
    private boolean isHidden=false;
    private ColumnLink link=null;


    final private int DEFAULT_TEXT_WIDTH=20;
    final private int DEFAULT_FLOAT_WIDTH=6;
    final private int DEFAULT_DATE_WIDTH=11;

    final static public int CT_CHAR=0;
    final static public int CT_DATE=1;
    final static public int CT_ENUM=2;
    final static public int CT_FLOAT=3;
    final static public int CT_INT=4;
    final static public int CT_VARCHAR=5;
    final static public int CT_TEXT=6;


    /**
     * Gets the value of valueList
     *
     * @return the value of valueList
     */
    public Vector getValueList()  {
	return this.valueList;
    }

    /**
     * Sets the value of valueList
     *
     * @param argValueList Value to assign to this.valueList
     */
    public void setValueList(Vector argValueList) {
	this.valueList = argValueList;
    }



    /**
     * Gets the value of type
     *
     * @return the value of type
     */
    public int getType()  {
	return this.type;
    }

    /**
     * Sets the value of type
     *
     * @param argType Value to assign to this.type
     */
    public void setType(int argType) {
	this.type = argType;
    }


    /**
     * Gets the value of columnName
     *
     * @return the value of columnName
     */
    public String getColumnName()  {
	return this.columnName;
    }

    /**
     * Sets the value of columnName
     *
     * @param argColumnName Value to assign to this.columnName
     */
    public void setColumnName(String argColumnName) {
	this.columnName = argColumnName;
    }

    /**
     * Sets the value of table
     *
     * @param argTable Value to assign to this.table
     */
    public void setTable(SQLTable argTable) {
	this.table = argTable;
    }


    /**
     * Gets the value of link
     *
     * @return the value of link
     */
    public ColumnLink getLink()  {
	return this.link;
    }

    /**
     * Sets the value of link
     *
     * @param argLink Value to assign to this.link
     */
    public void setLink(ColumnLink argLink) {
	this.link = argLink;
    }

    


    private int identToInt(String ident){
	if (ident.equalsIgnoreCase("char"))
	    return CT_CHAR;
	if (ident.equalsIgnoreCase("date"))
	    return CT_DATE;
	if (ident.equalsIgnoreCase("enum"))
	    return CT_ENUM;
	if (ident.equalsIgnoreCase("float"))
	    return CT_FLOAT;
	if (ident.equalsIgnoreCase("int"))
	    return CT_INT;
	if (ident.equalsIgnoreCase("varchar"))
	    return CT_VARCHAR;
	if (ident.equalsIgnoreCase("text"))
	    return CT_TEXT;
	throw new IllegalArgumentException("illegal type identifier "+ident);
	
    }
    public SQLColumn(SQLTable table,String name,String typeString,
		     String keyType,boolean isNullable, String defaultValue){
	this.table=table;
	this.isPrimary=keyType.equalsIgnoreCase("PRI");
	this.isWritable=(!this.isPrimary);
	this.isNullable=isNullable;
	columnName=name;
	parseType(typeString);
	this.defaultValue=interpretDefault(defaultValue);
    }


    public Object interpretDefault(String defaultString){
	if (defaultString==null)
	    return nullReplacement();
   
	switch (type){
	case CT_FLOAT:
	    return  Float.valueOf(defaultString);
	case CT_INT:
	    return Integer.valueOf(defaultString);
	case CT_DATE:
	    if (defaultString.equals("0000-00-00"))
		return new Date(System.currentTimeMillis());
	    else
		return Date.valueOf(defaultString);
	default:
	    return defaultString;
	}
    }


    private Object nullReplacement(){
	switch(type){
	case CT_DATE:
	    return new Date(System.currentTimeMillis());
	case CT_FLOAT:
	    return new Float(0.0);
	case CT_INT:
	    return new Integer(0);
	default:
	    return null;
	}
    }

    public boolean isNullEditable(){
	switch(type){
	case CT_DATE:
	case CT_FLOAT:
	    return false;
	case CT_INT:
	    return !isPrimary();
	default:
	    return true;
	}
    }
	    
    public boolean isPrimary(){
	return this.isPrimary;
    }
    private void parseType(String typeString){

	StreamTokenizer st=new StreamTokenizer(new StringReader(typeString));
	st.resetSyntax();
	st.wordChars('A','Z');
	st.wordChars('a','z');
	st.wordChars('0','9');
	st.lowerCaseMode(true);
	st.whitespaceChars(',',',');
	st.quoteChar('\'');

	try{

	    match(st,StreamTokenizer.TT_WORD);

	    this.type=identToInt(st.sval);

	    switch (this.type){
	    case CT_VARCHAR:
	    case CT_INT:
		match(st,'(');
		match(st,StreamTokenizer.TT_WORD);
		this.width=Integer.parseInt(st.sval);
		match(st,')');
		break;
	    case CT_CHAR:
	    case CT_FLOAT:
	    case CT_DATE:
	    case CT_TEXT:
		// do nothing
		break;
	    case CT_ENUM:
		valueList=new Vector();
		matchValueList(st);
		break;
	    default:
		throw new IllegalArgumentException("Unimplemented type "+
						   type+ " "+typeString);
	    }
	} catch (IOException e){
	    throw 
		new IllegalArgumentException("Error parsing "+
					     "type description "+typeString+
					     e.getMessage());
	}
    }

    private void match(StreamTokenizer st, int token) throws IOException{
	int nextToken=st.nextToken();
	    
	if (nextToken!=token){
	    throw new IllegalArgumentException("Expected token "+token+"got "
					       +st.toString());
	}
    }
    
    private void matchValueList(StreamTokenizer st) throws IOException{
	match(st,'(');
	int token=st.nextToken();
	while (token!= ')'){
	    if (token!='\''){
		String msg="unexpected token "+token+" in valueList"+ st.toString();
		throw new IllegalArgumentException(msg);
	    }
	    valueList.addElement(st.sval);
	    token=st.nextToken();
	}
    }

    /**
     * Gets the value of width
     *
     * @return the value of width
     */
    public int getWidth()  {
	return this.width;
    }

    /**
     * Sets the value of width
     *
     * @param argWidth Value to assign to this.width
     */
    public void setWidth(int argWidth) {
	this.width = argWidth;
    }

    /**
     * Gets the value of isLinked
     *
     * @return the value of isLinked
     */
    public boolean isIsLinked()  {
	return this.isLinked;
    }

    /**
     * Sets the value of isLinked
     *
     * @param argIsLinked Value to assign to this.isLinked
     */
    public void setIsLinked(boolean argIsLinked) {
	this.isLinked = argIsLinked;
    }

	
	public String getLabel(){
		return columnName.replace('_',' ');
	}   
    public void link(String tableName, String dataColumnName, String listColumnName) throws SQLException{
	isLinked=true;
	
	link=new ColumnLink(this,
			    tableName,
			    dataColumnName,
			    listColumnName);
    }

    public SQLTable getTable(){
	return table;
    }
    public int getDisplayColumns(){

	switch(type){
	case CT_TEXT:
	    return DEFAULT_TEXT_WIDTH;
	case CT_FLOAT:
	    return DEFAULT_FLOAT_WIDTH;
	case CT_DATE:
	    return DEFAULT_DATE_WIDTH;
	default:
	    return width;
	}
    }
    public boolean isLinked(){
	return this.isLinked;
    }
    public boolean isWritable(){
	return this.isWritable;
    }

    public void setWritable(boolean arg){
	this.isWritable=arg;
    }

    public boolean isHidden(){
	return this.isHidden;
    }

    public boolean isNullable(){
	return this.isNullable;
    }

    public void setHidden(boolean arg){
	this.isHidden=arg;
    }
    
    public Object getDefault(){
	return defaultValue;
    }

    public String toString(){
	return "SQLColumn["+
	    " columnName="+columnName+
	    " type="+type+
	    " defaultValue="+defaultValue+
	    " isNullable="+isNullable+
	    "]";
    }


    public boolean isYesNo(){

	if( (type==CT_ENUM) &&  (valueList.size()==2)){
	    String one=((String)valueList.elementAt(0)).toLowerCase();
	    String two=((String)valueList.elementAt(1)).toLowerCase();
	    return ( one.equals("yes") && two.equals("no") 
		     || one.equals("no") && two.equals("yes") );
	} else {
	    return false;
	}
    };
		

}


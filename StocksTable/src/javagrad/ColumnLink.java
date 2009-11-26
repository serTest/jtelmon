import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import java.util.Enumeration;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
public class ColumnLink  implements PropertyChangeListener {

    // Implementation of java.beans.PropertyChangeListener

    private SQLDatabase database=null;
    private SQLTable translationTable=null;
    
    private String tableName,dataColumnName,listColumnName;
    
    Vector displayList=null;
    Vector dataList=null;
    HashMap forwardMap=null;
    PropertyChangeSupport proper=null;
    

    /**
     * Catch events from the modelled table.
     *
     * @param propertyChangeEvent a <code>PropertyChangeEvent</code> value
     */
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
	try{
	    load();
	} catch (SQLException e){
	    UI.fatal(e);
	}
	firePropertyChange(propertyChangeEvent);
    }
    

    
    // Code for delegation of java.beans.PropertyChangeSupport methods to proper

    /**
     * Describe <code>addPropertyChangeListener</code> method here.
     *
     * @param propertyChangeListener a <code>PropertyChangeListener</code> value
     */
    public synchronized void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
	proper.addPropertyChangeListener(propertyChangeListener);
    }

    /**
     * Describe <code>addPropertyChangeListener</code> method here.
     *
     * @param string a <code>String</code> value
     * @param propertyChangeListener a <code>PropertyChangeListener</code> value
     */
    public synchronized void addPropertyChangeListener(String string, PropertyChangeListener propertyChangeListener) {
	proper.addPropertyChangeListener(string, propertyChangeListener);
    }

    /**
     * Describe <code>removePropertyChangeListener</code> method here.
     *
     * @param propertyChangeListener a <code>PropertyChangeListener</code> value
     */
    public synchronized void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
	proper.removePropertyChangeListener(propertyChangeListener);
    }

    /**
     * Describe <code>removePropertyChangeListener</code> method here.
     *
     * @param string a <code>String</code> value
     * @param propertyChangeListener a <code>PropertyChangeListener</code> value
     */
    public synchronized void removePropertyChangeListener(String string, PropertyChangeListener propertyChangeListener) {
	proper.removePropertyChangeListener(string, propertyChangeListener);
    }

    /**
     * Describe <code>firePropertyChange</code> method here.
     *
     * @param string a <code>String</code> value
     * @param object an <code>Object</code> value
     * @param object1 an <code>Object</code> value
     */
    public void firePropertyChange(String string, Object object, Object object1) {
	proper.firePropertyChange(string, object, object1);
    }

    /**
     * Describe <code>firePropertyChange</code> method here.
     *
     * @param string a <code>String</code> value
     * @param n an <code>int</code> value
     * @param n1 an <code>int</code> value
     */
    public void firePropertyChange(String string, int n, int n1) {
	proper.firePropertyChange(string, n, n1);
    }

    /**
     * Describe <code>firePropertyChange</code> method here.
     *
     * @param string a <code>String</code> value
     * @param flag a <code>boolean</code> value
     * @param flag1 a <code>boolean</code> value
     */
    public void firePropertyChange(String string, boolean flag, boolean flag1) {
	proper.firePropertyChange(string, flag, flag1);
    }

    /**
     * Describe <code>firePropertyChange</code> method here.
     *
     * @param propertyChangeEvent a <code>PropertyChangeEvent</code> value
     */
    public void firePropertyChange(PropertyChangeEvent propertyChangeEvent) {
	proper.firePropertyChange(propertyChangeEvent);
    }

    /**
     * Describe <code>getPropertyChangeListeners</code> method here.
     *
     * @return a <code>PropertyChangeListener[]</code> value
     */
    public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
	return proper.getPropertyChangeListeners();
    }

    /**
     * Describe <code>getPropertyChangeListeners</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>PropertyChangeListener[]</code> value
     */
    public synchronized PropertyChangeListener[] getPropertyChangeListeners(String string) {
	return proper.getPropertyChangeListeners(string);
    }

    /**
     * Describe <code>hasListeners</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>boolean</code> value
     */
    public synchronized boolean hasListeners(String string) {
	return proper.hasListeners(string);
    }
    
    public ColumnLink(SQLColumn col, 
		      String table,
		      String dataColumn,
		      String listColumn) throws SQLException{
    
	database=col.getTable().getDatabase();
	tableName=table;

	translationTable=database.findTable(tableName);
	translationTable.addPropertyChangeListener(this);

	dataColumnName=dataColumn;
	listColumnName=listColumn;
	proper=new PropertyChangeSupport(this);

	load();

    }


    private void load() throws SQLException{
	SQLResultSet results=database.query("select * from "+
					    tableName +" order by "+listColumnName,true,true);

	int dataIndex=results.findColumn(dataColumnName);
	int listIndex=results.findColumn(listColumnName);
	
	// The c programmer in me cringes, but I guess a little 
	// garbage is ok
	displayList= new Vector();
	dataList=new Vector();
	forwardMap=new HashMap();
	results.first();

	while(!results.isAfterLast()){
	    Object key=results.getObject(dataIndex);
	    Object val=results.getObject(listIndex);
	    if (val instanceof String){
		String str=(String)val;
		val=str.trim();
	    }
	    dataList.addElement(key);
	    displayList.addElement(val);
	    forwardMap.put(key,val);
	    results.relative(1);
	}
    }


    public int getSize(){
	if (displayList==null)
	    return 0;
	else
	    return displayList.size();
    }


    public Object getDisplayElement(int n){
	return displayList.elementAt(n);
    }

    public Object getDataElement(int n){
	return dataList.elementAt(n);
    }

    public Object keyToValue(Object key){
	Object val=forwardMap.get(key);
	return val;
    }


    public String toString(){
	return "ColumnLink[tableName="+ tableName+
	    ",dataColumn="+dataColumnName+
	    ",listColumn="+listColumnName+
	    ",database="+database+"]";
    }

    public Vector getDisplayList(){
	return displayList;
    } 
    public void tableChanged(TableChangeEvent e){
	try{
	    load();
	}
	catch (SQLException except){
	    UI.fatal(except);
	}
    }
}    

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.table.DefaultTableColumnModel;
import java.util.Vector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
public class SQLTable{

    private String tableName=null;
    private SQLDatabase database=null;

    private HashMap columns=null; // index columns by name
    private SQLColumn[] fieldList=null;  // index columns in order
    private int numCols=0;

    private PropertyChangeSupport proper;


    
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
    
    
    public SQLTable(SQLDatabase parent, String tableName) 
	throws SQLException{

	database=parent;

	this.tableName=tableName;
	columns=new HashMap();
	proper=new PropertyChangeSupport(this);
	SQLResultSet desc=SQL.query(parent.getConnection(),"show columns from `"+tableName+"`",true,false);
	int fieldCol=desc.findColumn("field");
	int typeCol=desc.findColumn("type");
	int keyCol=desc.findColumn("key");

	int nullCol=desc.findColumn("null");
	int defaultCol=desc.findColumn("default");

	       
	if (!desc.first())
	    throw new RuntimeException("No columns found for "+tableName);

	desc.last();
	numCols=desc.getRow();

	fieldList=new SQLColumn[numCols];

	desc.first();
	int i=0;
	do {
	    String field=desc.getString(fieldCol);
	    String type=desc.getString(typeCol);
	    String keyType=desc.getString(keyCol);
	    boolean nullable=(desc.getString(nullCol).equalsIgnoreCase("yes"));
	    String def=desc.getString(defaultCol);
	    
	    SQLColumn col=new SQLColumn(this,field,type,keyType,nullable,def);
	    
	    columns.put(field.toLowerCase(),col );
	    fieldList[i++]=col;
	    desc.relative(1);
	} while (!desc.isAfterLast());
    }
    public SQLColumn findColumn(String name){
	return findField(name);
    }

    public SQLColumn getColumn(int i){
	return fieldList[i];
    }
    public SQLColumn findField(String name){
	SQLColumn col= (SQLColumn)columns.get(name.toLowerCase());
	if (col==null){
	    throw new RuntimeException("field "+name+
				       " not found in table "+tableName);
	}
	return col;
    }

    /** return a column model suitable for JTable */
    public DefaultTableColumnModel columnModel(String [] fields) throws SQLException{
	DefaultTableColumnModel model= new DefaultTableColumnModel();
	
	int limit=0;

	if (fields==null){
	    limit=fieldList.length;
	} else {
	    limit=fields.length;
	}

	for (int i=0; i< limit; i++){
	    SQLColumn meta=null;
	    int skipped=0;
	    if (fields==null){
		meta=fieldList[i];
	    } else {
		meta=findField(fields[i]);
	    }
	    if (meta.isHidden()){
		skipped++;
	    } else {
		model.addColumn( new SQLTableColumn(i-skipped,meta));
	    }
	}
	return model;
    }
    public SQLDatabase getDatabase(){
	if (database==null)
	    throw new RuntimeException("Table "+tableName+"database is null");
	return database;
    }

    public int getNumColumns(){
	return numCols;
    }
    

}


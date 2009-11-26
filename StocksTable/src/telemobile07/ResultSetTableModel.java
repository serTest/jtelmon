// http://www.cs.unb.ca/~bremner//teaching/java_examples/javagrad/src/ResultSetTableModel.java/

package telemobile07;

import java.sql.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;

/**
 * This class takes a JDBC ResultSet object and implements the TableModel
 * interface in terms of it so that a Swing JTable component can display the
 * contents of the ResultSet.  Note that it requires a scrollable JDBC 2.0
 * ResultSet.  Also note that it provides read-only access to the results
 **/
public class ResultSetTableModel extends AbstractTableModel
    implements TableModel {
    SQLResultSet results;             // The ResultSet to interpret
    SQLTable metaTable;  // parsed from the database
    SQLDatabase database;
    ResultSetMetaData metadata;    // Additional information about the results
    int numcols, numrows;          // How many rows and columns in the table

    boolean addingRow=false;
    Object [] insertRow;

    /**
     * This constructor creates a TableModel from a ResultSet.  It is package
     * private because it is only intended to be used by
     * ResultSetTableModelFactory, which is what you should use to obtain a
     * ResultSetTableModel
     **/
    ResultSetTableModel(SQLTable metaTable, SQLResultSet results) throws SQLException {
	this.results = results;                 // Save the results
	this.metaTable= metaTable;
	this.database=metaTable.getDatabase();
	metadata = results.getMetaData();       // Get metadata on them
	numcols = metadata.getColumnCount();    // How many columns?
	results.last();                         // Move to last row
	numrows = results.getRow();             // How many rows?
    }


    // These two TableModel methods return the size of the table
    public int getColumnCount() { return numcols; }
    public int getRowCount() { return numrows; }

    // This TableModel method returns columns names from the ResultSetMetaData
    public String getColumnName(int column) {
	try {
	    return metadata.getColumnLabel(column+1);
	} catch (SQLException e) { return e.toString(); }
    }

    // This TableModel method specifies the data type for each column.
    public Class getColumnClass(int column) {
	Class theClass=null;
	try{
	    String className=metadata.getColumnClassName(column+1);
	    theClass =Class.forName(className);
	}
	catch (SQLException e){
	    UI.fatal(e);
	}
	catch (ClassNotFoundException e){
	    UI.fatal(e);
	}

	return theClass;


    }

    /**
     * This is the key method of TableModel: it returns the value at each cell
     * of the table.
     * Note that SQL row and column numbers start at 1, but TableModel column
     * numbers start at 0.
     **/
    public Object getValueAt(int row, int column) {
	if (addingRow && (row==numrows-1))
	    return insertRow[column];

	Object obj=null;
	try {
	    results.absolute(row+1);                // Go to the specified row
	    obj = results.getObject(column+1); // Get value of the column
	} catch (SQLException e) {
	    UI.fatal("row "+row+" column "+column,e);
	}
	return obj;
    }


    public boolean isCellEditable (int row, int column) {
	boolean isIt=false;
	try{

	    isIt= metaTable.getColumn(column).isWritable()
		&& metadata.isWritable(column);

	} catch (SQLException e){
	    UI.fatal("row "+row+" column "+column,e);
	}
	return isIt;
    }


    public void setValueAt(final Object value, final int row, final int column) {

	Debug.println("set table("+row+","+column+")="+value+" "+value.getClass());

	if (row==-1 || column==-1)
	    return;

	if (addingRow && (row==numrows-1)){
	    insertRow[column]=value;
	    return;
	}
	try{
	    String columnName=metadata.getColumnName(column+1);
	    UI.message("Updating "+columnName +" to " +value);

	    results.absolute(row+1);
	    results.updateObject(column+1,value);
	    results.updateRow();
	    metaTable.firePropertyChange("entry",null,value);
	} catch (SQLException e){
	    UI.fatal("value="+value+" row="+row+" column="+column,e);
	}
    }


    public void addRow(String field, Object value){
	Object[] row= new Object[numcols];

	try{

	    int index=results.findColumn(field);
	    row[index-1]=value;
	    results.moveToInsertRow();
	    results.updateObject(field,value);

	    addRow(row);
	} catch (SQLException e){
	    UI.fatal(e);
	}


    }

    public void addRow(Object []elements){

	insertRow=new Object[elements.length];
	for (int i=0; i< insertRow.length; i++){
	    if (elements[i]==null ){
		String colName=null;
		try{
		    colName=metadata.getColumnName(i+1);
		}
		catch (SQLException e){
		    UI.fatal(e);
		}

		SQLColumn metaCol=metaTable.findColumn(colName);
		boolean nullable=metaCol.isNullable() &&
		    metaCol.isNullEditable();
		if (!nullable)
		    insertRow[i]=metaCol.getDefault();
		else
		    insertRow[i]=null;
	    }
	    else
		insertRow[i]=elements[i];
	}
	addingRow=true;
	numrows++;
	fireTableRowsInserted(numrows-1,numrows-1);
    }

    private void dumpInsertRow(){
	for (int i=0; i< insertRow.length; i++){
	    System.out.print("insertRow["+i+"]="+insertRow[i]);
	    if (insertRow[i]!=null)
		System.out.println(insertRow[i].getClass());
	    else
		System.out.println("");
	}

    }


    public void rowAddCommit() {
	if (!addingRow)
	    throw new IllegalArgumentException("No row add to commit");

	try {
	    //	    dumpInsertRow();

	    results.moveToInsertRow();

	    for (int i=0; i<insertRow.length; i++){
		results.updateObject(i+1,  insertRow[i]);
	    }
	    results.insertRow();
	} catch (SQLException e){
	    UI.fatal(e);
	}
	// only if that succeeds:
	addingRow=false;
	metaTable.firePropertyChange("rows",numrows-1,numrows);
    }

    public void deleteRow(int row){
	try {
	    results.absolute(row+1);
	    results.deleteRow();
	    numrows--;
	    fireTableRowsDeleted(row,row);
	    metaTable.firePropertyChange("rows",numrows+1,numrows);
	} catch (SQLException e){
	    throw new RuntimeException(e.getMessage());
	}

    }

    public Object getValueAt(int row, String colName){
	Object val=null;
	try{
	    int colIndex=results.findColumn(colName);

	    val=getValueAt(row,colIndex-1);
	} catch (SQLException e){
	    UI.fatal(e);
	}
	return val;

    }


    public boolean addInProgress(){
	return addingRow;
    }
}

/*
 * http://www.camick.com/java/source/ListTableModel.java
 * http://code.google.com/p/jtelmon/source/browse/Tips4Java/src/ListTableModel/ListTableModel.java
 *
 * 
 */

package ListTableModel;

import java.awt.BorderLayout;
import java.util.*;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class ListTableModel extends RowTableModel<List>
{
	/**
	 *  Initialize the List with null values. This will set the size of the
	 *  List and will prevent an IndexOutOfBoundsException when trying to
	 *  access an Element in the List.
	 *
	 *  @param size the number of Elements to add to the List
	 *
	 */
	protected static List<String> newList(int size)
	{
		ArrayList<String> list = new ArrayList<String>(size);

		for (int i = 0; i < size; i++)
		{
			list.add( null );
		}

		return list;
	}

	/**
	 *  Constructs an empty <code>ListTableModel</code> with default
	 *  column names for the specified number of <code>columns</code>.
	 *
	 *  @param columns the number of columns the table holds
	 */
	public ListTableModel(int columns)
	{
		super( newList(columns) );
		setRowClass( List.class );
	}

	/**
	 *  Constructs an empty <code>ListTableModel</code> with customized
	 *  column names. The number of columns is determined bye the
	 *  number of items in the <code>columnNames</code> List.
	 *
	 *  @param columnNames	   <code>List</code> containing the names
	 *							of the new columns
	 */
	public ListTableModel(List<String> columnNames)
	{
		super(columnNames);
		setRowClass( List.class );
	}

	/**
	 *  Constructs a <code>ListTableModel</code> with the specified number
	 *  of rows. Default column names will be used for the specified number
	 *  of <code>columns</code>.
	 *
	 *  @param rows	the number of initially empty rows to create
	 *  @param columns the number of columns the table holds
	 */
	public ListTableModel(int rows, int columns)
	{
		super( newList(columns) );
		setRowClass( List.class );

		List<List> data = new ArrayList<List>(rows);

		for (int i = 0; i < rows; i++)
			data.add( new ArrayList(columns) );

		insertRows(0, data);
	}

	/**
	 *  Constructs a <code>ListTableModel</code> with initial data and
	 *  customized column names.
	 *
	 *  Each item in the <code>modelData</code> List must also be a List Object
	 *  containing items for each column of the row.
	 *
	 *  Each column's name will be taken from the <code>columnNames</code>
	 *  List and the number of colums is determined by thenumber of items
	 *  in the <code>columnNames</code> List.
	 *
	 *  @param modelData		 the data of the table
	 *  @param columnNames	   <code>List</code> containing the names
	 *							of the new columns
	 */
	public ListTableModel(List<List> modelData, List<String> columnNames)
	{
		super(modelData, columnNames);
		setRowClass( List.class );
	}
//
//  Implement the TableModel interface
//
	/**
	 *  Returns an attribute value for the cell at <code>row</code>
	 *  and <code>column</code>.
	 *
	 *  @param   row		the row whose value is to be queried
	 *  @param   column	 the column whose value is to be queried
	 *  @return			 the value Object at the specified cell
	 *  @exception  IndexOutOfBoundsException
	 *					  if an invalid row or column was given
	 */
	public Object getValueAt(int row, int column)
	{
		List rowData = getRow( row );
		return rowData.get( column );
	}

	/**
	 * Sets the object value for the cell at <code>column</code> and
	 * <code>row</code>.  <code>value</code> is the new value.  This method
	 * will generate a <code>tableChanged</code> notification.
	 *
	 * @param   value		   the new value; this can be null
	 * @param   row			 the row whose value is to be changed
	 * @param   column		  the column whose value is to be changed
	 * @exception  IndexOutOfBoundsException  if an invalid row or
	 *			   column was given
	 */
	@SuppressWarnings("unchecked")
	public void setValueAt(Object value, int row, int column)
	{
		List rowData = getRow( row );
		rowData.set(column, value);
		fireTableCellUpdated(row, column);
	}

	/**
	 *  Insert a row of data at the <code>row</code> location in the model.
	 *  Notification of the row being added will be generated.
	 *
	 *  @param   row	  row in the model where the data will be inserted
	 *  @param   rowData  data of the row being added
	 */
	@Override
	public void insertRow(int row, List rowData)
	{
		justifyRow( rowData );
		super.insertRow(row, rowData);
	}

	/**
	 *  Insert multiple rows of data at the <code>row</code> location in the model.
	 *  Notification of the row being added will be generated.
	 *
	 * @param   row	  row in the model where the data will be inserted
	 * @param   rowList  each item in the list is a separate row of data
	 */
	@Override
	public void insertRows(int row, List<List> rowList)
	{
		for (List rowData: rowList)
		{
			justifyRow( rowData );
		}

		super.insertRows(row, rowList);
	}

	/*
	 *  Make sure each List row contains the required number of columns.
	 */
	@SuppressWarnings("unchecked")
	private void justifyRow(List rowData)
	{
		for (int i = rowData.size(); i < getColumnCount(); i++)
		{
			rowData.add( null );
		}
	}

	/**
	 *  Adds a row of data to the end of the model.
	 *  Notification of the row being added will be generated.
	 *
	 * @param   rowData		 data of the row being added
	 */
	public void addRow(Object[] rowData)
	{
		insertRow(getRowCount(), rowData);
	}

	/**
	 *  Insert a row of data at the <code>row</code> location in the model.
	 *  Notification of the row being added will be generated.
	 *
	 *  @param   row	  row in the model where the data will be inserted
	 *  @param   rowData  data of the row being added
	 */
	public void insertRow(int row, Object[] rowData)
	{
		insertRow(row, copyToList(rowData));
	}

	/**
	 *  Insert multiple rows of data at the <code>row</code> location in the model.
	 *  Notification of the row being added will be generated.
	 *
	 * @param   row	  row in the model where the data will be inserted
	 * @param   rowArray  each item in the Array is a separate row of data
	 */
	public void insertRows(int row, Object[][] rowArray)
	{
		List<List> data = new ArrayList<List>(rowArray.length);

		for (int i = 0; i < rowArray.length; i++)
		{
			data.add( copyToList(rowArray[i]) );
		}

		insertRows(row, data);
	}

	/*
	 *  Copy the information in the Array to a List so a List can be added
	 *  to the model
	 */
	private List copyToList(Object[] rowData)
	{
		List<Object> row = new ArrayList<Object>(rowData.length);

		for (int i = 0; i < rowData.length; i++)
		{
			row.add( rowData[i] );
		}

		return row;
	}

	/**
	 *  Create a ListTableModel given a specific ResultSet.
	 *
	 *  The column names and class type will be retrieved from the
	 *  ResultSetMetaData. The data is retrieved from each row found in the
	 *  ResultSet. The class of
	 *
	 *  @param      resultSet  ResultSet containing results of a database query
	 *  @return	    a newly created ListTableModel
	 *  @exception  SQLException  when an SQL error is encountered
	 */
	public static ListTableModel createModelFromResultSet(ResultSet resultSet)
		throws SQLException
	{
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columns = metaData.getColumnCount();

		//  Create empty model using the column names

		ArrayList<String> columnNames = new ArrayList<String>();

		for (int i = 1; i <= columns; i++)
		{
			String columnName = metaData.getColumnName(i);
			String columnLabel = metaData.getColumnLabel(i);

			if (columnLabel.equals(columnName))
				columnNames.add( formatColumnName(columnName) );
			else
				columnNames.add( columnLabel );
		}

		ListTableModel model = new ListTableModel( columnNames );
		model.setModelEditable( false );

		//  Assign the class of each column

		for (int i = 1; i <= columns; i++)
		{
			try
			{
				String className = metaData.getColumnClassName( i );
				model.setColumnClass(i - 1, Class.forName(className));
			}
    		catch ( Exception exception ) {}
		}

		//  Get row data

		ArrayList<List> data = new ArrayList<List>();

		while (resultSet.next())
		{
			ArrayList<Object>row = new ArrayList<Object>(columns);

			for (int i = 1; i <= columns; i++)
			{
				Object o = resultSet.getObject(i);
				row.add( o );
			}

			data.add( row );
		}

		model.insertRows(0, data);

		return model;
	}

        public static void main(String[] args) {

            String[] cn = {"First Name", "Last Name", "Age"};
            ListTableModel model = new ListTableModel(Arrays.asList(cn));
            model.setColumnClass(2, Integer.class);
            model.setColumnEditable(2, false);
            Object[] r1 = {"Homer", "Simpson", new Integer(40)};
            Object[] r2 = {"Marge", "Simpson", new Integer(35)};
            model.addRow(r1);
            model.addRow(r2);
            JTable table = new JTable(model);

            JScrollPane scrollPane = new JScrollPane(table);
            JFrame frame = new JFrame("JFrame and JTable example 2");
            frame.setSize(300, 100);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Action when window closes
            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            frame.setVisible(true);
        }
}

/*
The ListTableModel is a concrete implementation of the RowTableModel introduced in a previous entry.
 It may provide a more flexible solution, than can be achieved by using the DefaultTableModel,
 because of the increased functionality inherited from the RowTableModel.
First, it is called a ListTableModel because each row in the TableModel is represented by a List Object.
 Therefore, it is backwards compatible with old data because you can add
 rows of Vectors or it is compatible with the new Collections API as you can add rows of ArrayLists, for example.

Two additional methods have been added to support Arrays.

insertRow – insert an Array of Objects at the specified row
insertRows – insert a 2 dimensional array of Objects at the specified row
In both cases the data in the Array will be copied to a List and the List will be inserted into the model.
 Because data is simply copied from an Array to a List, it it better to use a List from the beginning when possible.

There is not much more to say about the ListTableModel. You would use it the same way you would use a DefaultTableModel.
 If you create a simple model like this…

String[] cn = {"First Name", "Last Name", "Age"};
ListTableModel model = new ListTableModel(Arrays.asList(cn));
model.setColumnClass(2, Integer.class);
model.setColumnEditable(2, false);
Object[] r1 = {"Homer", "Simpson", new Integer(40)};
Object[] r2 = {"Marge", "Simpson", new Integer(35)};
model.addRow(r1);
model.addRow(r2);
JTable table = new JTable(model);
 */
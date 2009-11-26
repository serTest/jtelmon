// http://java.sun.com/docs/books/tutorial/uiswing/components/table.html
// http://www.pioverpi.net/category/tech/jtables-jdbc/
// http://www.pioverpi.net/2009/07/02/jtables-jdbc-pt-4-putting-it-all-together/
    
package telemobile08;

import java.awt.*;
import java.awt.event.*;
// import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
// import java.io.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;


public class pgTelmTable01 extends JFrame
{
  protected JTable m_table;

  // ExpenseReportData extends AbstractTableModel
  protected ExpenseReportData m_data;
  protected JLabel m_title;

  public pgTelmTable01() {
    super("Telefoane Mobile");
    setSize(570, 200);
    m_data = new ExpenseReportData(this);
    m_table = new JTable();

    // We are manually creating the columns
    m_table.setAutoCreateColumnsFromModel(false);
    // m_table.setAutoCreateColumnsFromModel(true);

// Every JTable object uses a table model object to manage the actual table data.
// A table model object must implement the TableModel interface
    m_table.setModel(m_data);

// http://www.exampledepot.com/egs/javax.swing.table/RowHeight.html
    m_table.setRowHeight(25);


/*
public abstract class AbstractTableModel
extends Object
implements TableModel, Serializable
This abstract class provides default implementations for most of the methods
  in the TableModel interface. It takes care of the management of listeners and provides
  some conveniences for generating TableModelEvents and dispatching them to the listeners.
  To create a concrete TableModel as a subclass of AbstractTableModel you need only provide
  implementations for the following three methods:
  public int getRowCount();
  public int getColumnCount();
  public Object getValueAt(int row, int column);
 ~ ~ ~ 
     Instances of TableModel are responsible for storing a table’s data in a 2-dimensional structure
     such as a 2-dimensional array or a  vector of vectors.
     A set of methods is declared for use in retrieving data from a table's cells.
     The getValueAt() method should retrieve  data from a given row and column index as an Object,
     and setValueAt() should assign the provided
     data object to the specified  location (if valid).
     getColumnClass() should return the Class describing the data objects stored
     in the specified column (used to assign  a default renderer and editor for that column),
2     and getColumnName() should return the String name
     associated with the specified  column (often used for that column’s header).
     The getColumnCount() and getRowCount() methods
     should return the number of contained columns and rows respectively.
*/

// By default, a table component allows multiple selections.
// http://www.exampledepot.com/egs/javax.swing.table/MultiSel.html
    m_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    // m_table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    // m_table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
    for (int k = 0; k < ExpenseReportData.m_columns.length; k++) {
      TableCellRenderer renderer;
      if (k==ExpenseReportData.COL_6_ANULAT)
        renderer = new CheckCellRenderer();
      else {
        DefaultTableCellRenderer textRenderer = 
          new DefaultTableCellRenderer();
        textRenderer.setHorizontalAlignment(
          ExpenseReportData.m_columns[k].m_alignment);
        renderer = textRenderer;
      }

      TableCellEditor editor;

      if (k==ExpenseReportData.COL_3_FUNCTIE)
        editor = new DefaultCellEditor(new JComboBox(
          ExpenseReportData.FUNCTII));
      else if (k==ExpenseReportData.COL_6_ANULAT)
        editor = new DefaultCellEditor(new JCheckBox());
      else
        editor = new DefaultCellEditor(new JTextField());

      TableColumn column = new TableColumn(k, 
        ExpenseReportData.m_columns[k].m_width, 
          renderer, editor);
        m_table.addColumn(column);   
    }

    JTableHeader header = m_table.getTableHeader();
    header.setUpdateTableInRealTime(false);

    JScrollPane ps = new JScrollPane();
    ps.setSize(550, 150);
    ps.getViewport().add(m_table);
    getContentPane().add(ps, BorderLayout.CENTER);

    JPanel p = new JPanel();
    p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

    ImageIcon penny = new ImageIcon("penny.gif");
    m_title = new JLabel("Total: $", 
      penny, JButton.LEFT);
    m_title.setForeground(Color.black);
    m_title.setAlignmentY(0.5f);
    p.add(m_title);
    p.add(Box.createHorizontalGlue());

    JButton bt = new JButton("Insert before");
    bt.setMnemonic('b');
    bt.setAlignmentY(0.5f);
    ActionListener lst = new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        int row = m_table.getSelectedRow();
        m_data.insert(row);
        m_table.tableChanged(new TableModelEvent(
          m_data, row, row, TableModelEvent.ALL_COLUMNS, 
          TableModelEvent.INSERT)); 
        m_table.repaint();
      }
    };
    bt.addActionListener(lst);
    p.add(bt);

    bt = new JButton("Insert after");
    bt.setMnemonic('a');
    bt.setAlignmentY(0.5f);
    lst = new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        int row = m_table.getSelectedRow();
        m_data.insert(row+1);
        m_table.tableChanged(new TableModelEvent(
          m_data, row+1, row+1, TableModelEvent.ALL_COLUMNS,
          TableModelEvent.INSERT)); 
        m_table.repaint();
      }
    };
    bt.addActionListener(lst);
    p.add(bt);

    bt = new JButton("Delete row");
    bt.setMnemonic('d');
    bt.setAlignmentY(0.5f);
    lst = new ActionListener() { 
      public void actionPerformed(ActionEvent e) { 
        int row = m_table.getSelectedRow();
        if (m_data.delete(row)) {
          m_table.tableChanged(new TableModelEvent(
            m_data, row, row, TableModelEvent.ALL_COLUMNS,
            TableModelEvent.INSERT)); 
          m_table.repaint();
          calcTotal();
        }
      }
    };
    bt.addActionListener(lst);
    p.add(bt);
      
    getContentPane().add(p, BorderLayout.SOUTH);

    calcTotal();

    WindowListener wndCloser = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    };
    addWindowListener(wndCloser);
    
    setVisible(true);
  }

  public void calcTotal() {
    double total = 0;
    for (int k=0; k<m_data.getRowCount(); k++) {
      /*
        Double amount = (Double)m_data.getValueAt(k,
        ExpenseReportData.COL_AMOUNT);
      total += amount.doubleValue();
       */
    }
    m_title.setText("Total: $"+total);
  }

  public static void main(String argv[]) {
    new pgTelmTable01();
  }
}
class CheckCellRenderer extends JCheckBox implements TableCellRenderer
{
  protected static Border m_noFocusBorder;

  public CheckCellRenderer() {
    super();
    m_noFocusBorder = new EmptyBorder(1, 2, 1, 2);
    setOpaque(true);
    setBorder(m_noFocusBorder);
  }

  public Component getTableCellRendererComponent(JTable table,
   Object value, boolean isSelected, boolean hasFocus, 
   int row, int column) 
  {
    if (value instanceof Boolean) {
      Boolean b = (Boolean)value;
      setSelected(b.booleanValue());
    }

    setBackground(isSelected && !hasFocus ? 
      table.getSelectionBackground() : table.getBackground());
    setForeground(isSelected && !hasFocus ? 
      table.getSelectionForeground() : table.getForeground());
        
    setFont(table.getFont());
    setBorder(hasFocus ? UIManager.getBorder(
      "Table.focusCellHighlightBorder") : m_noFocusBorder);

    return this;
  }
}

class RowData
{
  public String  m_numar_telefon;
  public String  m_nume_prenume;
  public String  m_functie;
  public String  m_localitate;
  public Double  m_deductibil;
  public Boolean m_anulat;

  public RowData()
  {
  }

  public RowData( String numar_telefon, String  nume_prenume,
          String functie, String  localitate, double deductibil, Boolean anulat)
  {
    m_numar_telefon = numar_telefon;
    m_nume_prenume  = nume_prenume;
    m_functie       = functie;
    m_localitate    = localitate;
    m_deductibil    = deductibil;
    m_anulat        = anulat;
  }
}

class ColumnData
{
  public String  m_title;
  int m_width;
  int m_alignment;

  public ColumnData(String title, int width, int alignment) {
    m_title = title;
    m_width = width;
    m_alignment = alignment;
  }
}

class ExpenseReportData extends AbstractTableModel 
{
  public static final ColumnData m_columns[] = {
    new ColumnData( "Nr Tel", 120, JLabel.LEFT ),
    new ColumnData( "Nume prenume", 120, JLabel.RIGHT ),
    new ColumnData( "Functie", 120, JLabel.LEFT ),
    new ColumnData( "Localitate", 120, JLabel.LEFT ),
    new ColumnData( "Deductibil", 80, JLabel.LEFT ),
    new ColumnData( "Anulat", 80, JLabel.LEFT )
  };

  public static final int COL_DATE = 90;
  public static final int COL_1_NUMAR = 0;
  public static final int COL_AMOUNT = 91;
  public static final int COL_2_NUME = 1;
  public static final int COL_CATEGORY = 92;
  public static final int COL_3_FUNCTIE = 2;
  public static final int COL_APPROVED = 93;
  public static final int COL_4_LOCALITATE = 3;
  public static final int COL_DESCR = 94;
  public static final int COL_5_DEDUCTIBIL = 4;
  public static final int COL_6_ANULAT = 5;

  public static final String[] CATEGORIES = {
    "Fares", "Logging", "Business meals", "Others"
  };

  public static final String[] FUNCTII = {
    "Director", "Sef", "MZ", "KA", "AG.COM"
  };


  protected pgTelmTable01 m_parent;
  protected SimpleDateFormat m_frm;
  protected Vector m_vector;

  public ExpenseReportData(pgTelmTable01 parent) {
    m_parent = parent;
    m_frm = new SimpleDateFormat("MM/dd/yy");
    m_vector = new Vector();
    setDefaultData();
    retrieveData();
  }

  public void setDefaultData() {
    m_vector.removeAllElements();
    // try
    {
      m_vector.addElement(new RowData( "0724353580", "Alex Untaru",
              "Programator", "Resita", 25, new Boolean(false) ));
    }
    // catch (java.text.ParseException ex) {}
  }

  public int getRowCount() {
    return m_vector==null ? 0 : m_vector.size(); 
  }

  public int getColumnCount() { 
    return m_columns.length; 
  } 

  public String getColumnName(int column) { 
    return m_columns[column].m_title; 
  }
 
  public boolean isCellEditable(int nRow, int nCol) {
    return true;
  }

  // getValueAt - ca sa se vada datele initiale
  public Object getValueAt(int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return "";
    RowData row = (RowData)m_vector.elementAt(nRow);
    switch (nCol) {
      case COL_1_NUMAR:    return row.m_numar_telefon;
      case COL_2_NUME:     return row.m_nume_prenume;
      case COL_3_FUNCTIE:  return row.m_functie;
      case COL_4_LOCALITATE:  return row.m_localitate;
      case COL_5_DEDUCTIBIL:  return row.m_deductibil;
      case COL_6_ANULAT:  return row.m_anulat;
      // case COL_CATEGORY: return CATEGORIES[row.m_category.intValue()];

    }
    return "";
  }

  // setValueAt - ca sa si ramana datele modificate
  public void setValueAt(Object value, int nRow, int nCol) {
    if (nRow < 0 || nRow>=getRowCount())
      return;
    RowData row = (RowData)m_vector.elementAt(nRow);
    String svalue = value.toString();

    switch (nCol) {
/*
        case COL_DATE:
        Date  date = null;
        try { 
          date = m_frm.parse(svalue); 
        }
        catch (java.text.ParseException ex) { 
          date = null; 
        }
        if (date == null) {
          JOptionPane.showMessageDialog(null, 
            svalue+" is not a valid date",
            "Warning", JOptionPane.WARNING_MESSAGE);
          return;
        }
        break;
      case COL_AMOUNT:
        try {

        }
        catch (NumberFormatException e) { break; }
        m_parent.calcTotal();
        break;
      case COL_CATEGORY:
        for (int k=0; k<CATEGORIES.length; k++)
          if (svalue.equals(CATEGORIES[k])) {
            //row.m_category = new Integer(k);
            break;
          }
        break;
      case COL_APPROVED:
        //row.m_approved = (Boolean)value;
        break;
      case COL_DESCR:
        //row.m_description = svalue;
        break;
*/
      case COL_1_NUMAR:
        row.m_numar_telefon = svalue;
        break;
      case COL_2_NUME:
        row.m_nume_prenume = svalue;
        break;
      case COL_3_FUNCTIE:
        row.m_functie = svalue;
        break;
      case COL_4_LOCALITATE:
        row.m_localitate = svalue;
        break;
      case COL_5_DEDUCTIBIL:
        row.m_deductibil= new Double(svalue);
        break;
      case COL_6_ANULAT:
        row.m_anulat = new Boolean(svalue);
        break;

    }
  }

  public void insert(int row) {
    if (row < 0)
      row = 0;
    if (row > m_vector.size())
      row = m_vector.size();
    m_vector.insertElementAt(new RowData(), row);
  }

  public boolean delete(int row) {
    if (row < 0 || row >= m_vector.size())
      return false;
    m_vector.remove(row);
      return true;
  }

    public int retrieveData() {
    // GregorianCalendar calendar = new GregorianCalendar();
    // final int m_result = 0;
       // static int m_result = 0;
    //calendar.setTime(date);
    //int month = calendar.get(Calendar.MONTH)+1;
    //int day = calendar.get(Calendar.DAY_OF_MONTH);
    //int year = calendar.get(Calendar.YEAR);

    final String query = "SELECT u.numar_telefon, u.nume_prenume, " +
      " u.functie, u.localitate, u.deductibil, u.anulat " +
      " FROM utilizatori u" ;

    final String  url_sursa = "jdbc:postgresql://192.168.61.200:5432/telefoane_mobile";
    // final String  url_sursa = "jdbc:postgresql://192.168.101.222:5432/javamarket";
    final String username_sursa = "postgres";
    final String password_sursa = "telinit";

    Thread runner = new Thread() {
      public void run() {
        try {
          Class.forName("org.postgresql.Driver");
          Connection conn = DriverManager.getConnection(url_sursa,username_sursa,password_sursa);


          Statement stmt = conn.createStatement();
          ResultSet results = stmt.executeQuery(query);

          boolean hasData = false;
          while (results.next()) {
            if (!hasData) {
              m_vector.removeAllElements();
              hasData = true;
            }
            String  rs_numar      = results.getString(1);
            String  rs_nume       = results.getString(2);
            String  rs_functie    = results.getString(3);
            String  rs_localitate = results.getString(4);
            double  rs_deductibil = results.getDouble(5);
            boolean rs_anulat     = results.getBoolean(6);
            m_vector.addElement(new RowData( rs_numar, rs_nume , rs_functie,
                    rs_localitate, rs_deductibil, new Boolean(rs_anulat) ));
          }
          results.close();
          stmt.close();
          conn.close();

          if (!hasData){
              // We've got nothing
              // m_result = 1;
          }
        }
        catch (Exception e) {
          e.printStackTrace();
          System.err.println("Load data error: "+e.toString());
          // m_result = -1;
        }
        // m_date = date;
        // Collections.sort(m_vector, new StockComparator(m_sortCol, m_sortAsc));
        //m_result = 0;
      }
    };
    runner.start();
    // return m_result;
    return 1;
  }

}

/*

 // http://www.pioverpi.net/category/tech/jtables-jdbc/
// http://www.pioverpi.net/2009/07/02/jtables-jdbc-pt-4-putting-it-all-together/


 JFrame myFrame = new JFrame("My Table");

MyTableModel mt = new MyTableModel();
JTable jt = new JTable(mt);
JScrollPane jsp = new JScrollPane(jt);

myFrame.add(jsp);
myFrame.setVisible(true);



import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel{

	private Object[][] content;
	private String[] colNames;

	public MyTableModel () {
		try {
			Connection conn = DatabaseData.connect();

			content = getTableContent(conn, "TABLE_NAME");
			colNames = getTableColumnNames(conn, "TABLE_NAME");

			disconnect(conn);
		}
		catch (SQLException sqx) {
			content = new Object [][] {{""}};
			colNames = new String [] {"Error"};
			System.err.println("Could not pull data from database");
		}
	}

	public int getColumnCount() {
		return colNames.length;
	}

	public int getRowCount() {
		return content.length;
	}

	public Object getValueAt(int arg0, int arg1) {
		return content[arg0][arg1];
	}

	public boolean isCellEditable(int row, int col) {
		return true;
	}
	public void setValueAt(Object aValue, int row, int col) {
		content[row][col] = aValue;
	}

	public String getColumnName(int col) {
		return colNames[col];
	}
}




public void Connection connect() {
	String url = "jdbc:postgresql://localhost:5432/myDatabase";
	String user = "username";
	String pass = "password";

	try {
		Class.forName("org.postgresql.Driver");
		Connection db = DriverManager.getConnection(url, user, pass);

		return db;
	} catch (ClassNotFoundException e) {
		System.err.println("Could not find PostgreSQL driver");
	} catch (SQLException e) {
		System.err.println("Failed to establish connection with db");
	}
	return null;
}
  
 public void disconnect(Connection conn) {
	try {
		conn.close();
	}
	catch (SQLException sqx) {
		System.err.println("Could not disconnect from db, trying again");
	}
}
 *
 public String[] getTableColumnNames(Connection conn, String tableName)
                                                          throws SQLException {
	Statement st = conn.createStatement();
	ResultSet rs = st.executeQuery("SELECT * FROM " + tableName + ";");

	ResultSetMetaData rsMeta = rs.getMetaData();

	String [] colNames = new String[rsMeta.getColumnCount()];

	for (int i = 0; i < rsMeta.getColumnCount(); i++) {
	    colNames[i] = rsMeta.getColumnName(i+1);
	}

	rs.close();
	st.close();
	return colNames;
}
 * 
 * 
  
 public String[] getTableColumnClasses(Connection conn, String tableName)
                                                        throws SQLException {
	Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM " + tableName + ";");

	ResultSetMetaData rsMeta = rs.getMetaData();

	String [] colClasses = new String [rsMeta.getColumnCount()];

	for (int i = 0; i < rsMeta.getColumnCount(); i++) {
	    colClasses[i] = rsMeta.getColumnClassName(i + 1);
	}

	rs.close();
	st.close();
	return colClasses;
} 
 
   
 public static Object[][] getTableContent(Connection conn, String tableName)
                                                         throws SQLException {
	String [] colNames = DatabaseData.getTableColumnNames(conn, tableName);
	String [] colClasses = DatabaseData.getTableColumnClasses(conn,
                                                                 tableName);

	Statement st = conn.createStatement();
	ResultSet rs = st.executeQuery("SELECT * FROM " + tableName + ";");

	ArrayList< Object[] > rowList = new ArrayList< Object[] >();
	while (rs.next()) {
		ArrayList< Object> cellList = new ArrayList< Object >();
		for (int i = 0; i < colClasses.length; i++) {
			Object cellValue = null;

			if (colClasses[i].equals(String.class.getName())) {
				cellValue = rs.getString (colNames[i]);
			}
			else if (colClasses[i].equals(Integer.class.getName())) {
				cellValue = new Integer
                                                  (rs.getInt (colNames[i]));
			}
			else if (colClasses[i].equals(Double.class.getName())) {
				cellValue = new Double
                                                (rs.getDouble (colNames[i]));
			}
			else if (colClasses[i].equals(Date.class.getName())) {
				cellValue = rs.getDate (colNames[i]);
			}
			else if (colClasses[i].equals(Float.class.getName())) {
				cellValue = rs.getFloat(colNames[i]);
			}
			else {
				System.out.println("PSQL Read Warning: "
                                  + "Unknown type encountered, "
                                  + "attempting to read as String");
				cellValue = rs.getString(colNames[i]);
			}
			cellList.add (cellValue);
		}
		Object[] cells = cellList.toArray();
		rowList.add(cells);
	}

	rs.close();
	st.close();

	Object [][] content = new Object[rowList.size()] [];
	for (int i=0; i< content.length; i++) {
		content[i] = rowList.get(i);
	}

	return content;
}
 
 */
// http://java.sun.com/docs/books/tutorial/uiswing/components/table.html
package telemobile07;

import java.awt.*;
import java.awt.event.*;
// import java.math.BigInteger;
import java.util.*;
// import java.io.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;


public class telmJTable extends JFrame
{
  protected JTable m_table;

  // ExpenseReportData extends AbstractTableModel
  protected ExpenseReportData m_data;
  protected JLabel m_title;

  public telmJTable() {
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
     and getColumnName() should return the String name
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
      if (k==ExpenseReportData.COL_4_DEDUCTIBIL)
        renderer = new CheckCellRenderer();
      else {
        DefaultTableCellRenderer textRenderer = 
          new DefaultTableCellRenderer();
        textRenderer.setHorizontalAlignment(
          ExpenseReportData.m_columns[k].m_alignment);
        renderer = textRenderer;
      }

      TableCellEditor editor;

      if (k==ExpenseReportData.COL_CATEGORY)
        editor = new DefaultCellEditor(new JComboBox(
          ExpenseReportData.FUNCTII));
      else if (k==ExpenseReportData.COL_4_DEDUCTIBIL)
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
    new telmJTable();
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

  public RowData()
  {
  }

  public RowData( String numar_telefon, String  nume_prenume,
          String functie, String  localitate, double deductibil)
  {
    m_numar_telefon = numar_telefon;
    m_nume_prenume  = nume_prenume;
    m_functie       = functie;
    m_localitate    = localitate;
    m_deductibil    = deductibil;
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
    new ColumnData( "Nr Tel", 80, JLabel.LEFT ),
    new ColumnData( "Nume prenume", 80, JLabel.RIGHT ),
    new ColumnData( "Functie", 130, JLabel.LEFT ),
    new ColumnData( "Localitate", 80, JLabel.LEFT ),
    new ColumnData( "Deductibil", 180, JLabel.LEFT )
  };

  public static final int COL_DATE = 90;
  public static final int COL_0_NUMAR = 0;
  public static final int COL_AMOUNT = 91;
  public static final int COL_1_NUME = 1;
  public static final int COL_CATEGORY = 92;
  public static final int COL_2_FUNCTIE = 2;
  public static final int COL_APPROVED = 93;
  public static final int COL_3_LOCALITATE = 3;
  public static final int COL_DESCR = 94;
  public static final int COL_4_DEDUCTIBIL = 4;

  public static final String[] CATEGORIES = {
    "Fares", "Logging", "Business meals", "Others"
  };

  public static final String[] FUNCTII = {
    "Director", "Sef", "MZ", "KA", "AG.COM"
  };


  protected telmJTable m_parent;
  protected SimpleDateFormat m_frm;
  protected Vector m_vector;

  public ExpenseReportData(telmJTable parent) {
    m_parent = parent;
    m_frm = new SimpleDateFormat("MM/dd/yy");
    m_vector = new Vector();
    setDefaultData();
  }

  public void setDefaultData() {
    m_vector.removeAllElements();
    // try
    {
      m_vector.addElement(new RowData( "0724353580", "Alex Untaru",
              "Programator", "Resita", 25));
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
      case COL_0_NUMAR:    return row.m_numar_telefon;
      case COL_1_NUME:     return row.m_nume_prenume;
      case COL_2_FUNCTIE:  return row.m_functie;
      case COL_3_LOCALITATE:  return row.m_localitate;
      // case COL_CATEGORY: return CATEGORIES[row.m_category.intValue()];
      // case COL_APPROVED: return "";
      case COL_DESCR: return "";
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
      case COL_0_NUMAR:
        row.m_numar_telefon = svalue;
        break;
      case COL_1_NUME:
        row.m_nume_prenume = svalue;
        break;
      case COL_2_FUNCTIE:
        row.m_functie = svalue;
        break;
      case COL_3_LOCALITATE:
        row.m_localitate = svalue;
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
}

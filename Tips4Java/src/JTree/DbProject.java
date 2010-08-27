/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JTree;

import java.sql.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class DbProject extends JFrame
      implements ActionListener, TreeSelectionListener
{
      private JTextField tfSqlStr;
      private JTextArea status;

      private JMenuBar menubar;
      private JMenu filemenu;
      private JMenuItem exititem, clearitem;

      private JScrollPane tableScroll;
      private JTable dbTable;
      ResultsModel model;

      private JScrollPane treeScroll;
      private DefaultMutableTreeNode dbNode;      // Root node for the database tree
        private DefaultTreeModel dbTreeModel;
      private JTree dbTree;

      private JPanel pN;

      private JSplitPane split;

      /*String user = "myguest";                       //put your own database here
      String pass = "ourguest";
      String url = "jdbc:odbc:mcprince_technicalLibrary";
      String driver = "sun.jdbc.odbc.JdbcOdbcDriver"; */

      Connection conn;
      Statement state;

      public static void main(String[] args)
      {
            DbProject dbInst = new DbProject();
            dbInst.setSize(500, 400);
            dbInst.setVisible(true);
      }

      public DbProject()
      {
            super("JDBC Project");

            getContentPane().setLayout(new BorderLayout());
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            addWindowListener(new WindowAdapter()
                  {
                        public void windowClosing(WindowEvent e)
                        {
                              System.exit(0);
                        }
                  });

            pN = new JPanel();
            pN.setLayout(new FlowLayout());
            pN.add(new JLabel("Enter SQL String:"));
            pN.add(tfSqlStr = new JTextField(35));
            tfSqlStr.addActionListener(this);      //register ActionListener
            getContentPane().add(pN, BorderLayout.NORTH);

            menubar = new JMenuBar();
            filemenu = new JMenu("File");
            filemenu.setMnemonic('F');
            exititem = new JMenuItem("Exit");
            clearitem = new JMenuItem("Clear");
            exititem.addActionListener(this);      //register ActionListener
            clearitem.addActionListener(this);      //register ActionListener
            filemenu.add(clearitem);
            filemenu.add(exititem);
            menubar.add(filemenu);
            setJMenuBar(menubar);

            // Create tree to go in left split pane
          dbNode = new DefaultMutableTreeNode("No database");
          dbTreeModel = new DefaultTreeModel(dbNode);
          dbTree = new JTree(dbTreeModel);
            dbTree.addTreeSelectionListener(this);

          // Create table to go in right split pane
          model = new ResultsModel();
          dbTable = new JTable(model);
          dbTable.setPreferredSize(new Dimension(800,400));
          dbTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            treeScroll = new JScrollPane(dbTree);
          treeScroll.setBorder(BorderFactory.createLineBorder(Color.darkGray));
            tableScroll = new JScrollPane(dbTable);
            tableScroll.setBorder(BorderFactory.createLineBorder(Color.darkGray));

            //split = new JSplitPane(HORIZONTAL_SPLIT, true, treeScroll, tableScroll);
            split = new JSplitPane();
            split.setOrientation(1);
            split.setContinuousLayout(true);
            split.setLeftComponent(treeScroll);
            split.setRightComponent(tableScroll);

            getContentPane().add(split, BorderLayout.CENTER);

            status = new JTextArea();
            status.setEditable(false);
            getContentPane().add(status, BorderLayout.SOUTH);

            try
            {
                  Class.forName("org.postgresql.Driver");
                  String url = "jdbc:postgresql://ftp.pangram.ro:5432/license";
                  conn = DriverManager.getConnection(url, "postgres", "telinit");
                  state = conn.createStatement();

                  dbNode = new DefaultMutableTreeNode(url);       // Root node is URL
                  dbTreeModel.setRoot(dbNode);                    // Set root in model
                  setupTree(conn.getMetaData());            // Set up tree with metadata

                  treeScroll.setBorder(BorderFactory.createTitledBorder(
                                     BorderFactory.createLineBorder(Color.darkGray),
                                     url,
                                     TitledBorder.CENTER,
                                     TitledBorder.DEFAULT_POSITION));
                  dbTree.setRootVisible(false);                   // Now show the root node
                  dbTreeModel.reload();                           // Get the tree redisplayed
            }
            catch(ClassNotFoundException cnfe)
            {
                  System.err.println(cnfe);
                  tfSqlStr.setText("Driver load failed" + cnfe);
            }
            catch(SQLException sqle)
          {
                  System.err.println(sqle);                    // error connection to database
          }

            tfSqlStr.requestFocus();
      }

      public void actionPerformed(ActionEvent evt)
      {
            //String actionCommand = evt.getActionCommand();
            Object source = evt.getSource();
            if(source.equals(tfSqlStr))
            {
                  try
                  {
                        String sql = tfSqlStr.getText();
                        model.setResultSet(state.executeQuery(sql));
                        status.setText("Resultset has " + model.getRowCount() + " rows.");
                  }
                  catch(SQLException sqle)
                  {
                        status.setText(sqle.getMessage());
                  }
            }
            else if(source.equals(clearitem))
            {
                  tfSqlStr.setText("");
            }
            else if(source.equals(exititem))
            {
                  dispose();
                  System.exit(0);
            }
      }

      private void setupTree(DatabaseMetaData metadata) throws SQLException
        {
          String[] tableTypes = { "TABLE"};                   // We want only tables
          ResultSet tables = metadata.getTables(              // Get the tables info
                                                null,
                                                null,
                                                null,
                                                tableTypes);

            String tableName;                           // Stores a table name
            DefaultMutableTreeNode tableNode;           // Stores a tree node for a table
            while(tables.next())                              // For each table
            {
              tableName = tables.getString("TABLE_NAME");     // get the table name
              tableNode = new DefaultMutableTreeNode(tableName);
              dbNode.add(tableNode);                          // Add the node to the tree

              // Get all the columns for the current table
              ResultSet columnNames = metadata.getColumns(null, null, tableName, null);

              // Add nodes for the columns as children of the table node
              while(columnNames.next())
                      tableNode.add(
                       new DefaultMutableTreeNode(columnNames.getString("COLUMN_NAME")));
            }
        }

      public void valueChanged(TreeSelectionEvent e)
        {
          TreePath[] paths = dbTree.getSelectionPaths();
          if(paths == null)
                  return;

          boolean tableSelected = false;             // Set true if a table is selected
          String column;                             // Stores a column name from a path
          String table;                              // Stores a table name from a path
          String columnsParam = null;                // Column names in SQL SELECT
          String tableParam = null;                  // Table name in SQL SELECT
          String message = null;                     // Message for status area
          for(int  j = 0; j < paths.length ; j++)
          {
                  switch(paths[j].getPathCount())
                  {
              case 2:                                // We have a table selected
                      tableParam = (String)
                                (((DefaultMutableTreeNode)
                                        (paths[j].getPathComponent(1))).getUserObject());
                      columnsParam = "*";               // Select all columns
                      tableSelected = true;             // Set flag for a table selected
                      message = "Complete " + tableParam + " table displayed";
                      break;

              case 3:                               // Column selected
                      table = (String)
                            (((DefaultMutableTreeNode)
                                        (paths[j].getPathComponent(1))).getUserObject());
                      if(tableParam == null)
                        tableParam = table;

                      else if(tableParam != table)
                        break;
                      column = (String)
                             (((DefaultMutableTreeNode)
                                        (paths[j].getPathComponent(2))).getUserObject());
                      if(columnsParam == null)           // If no previous columns
                        columnsParam = column;           // add the column
                      else                               // otherwise
                        columnsParam += "," + column;    // we need a comma too
                      message = columnsParam + " displayed from " + tableParam + " table.";
                     break;
                  }
                  if(tableSelected)                      // If a table was selected
                    break;                               // we are done
          }

          if(message != null)
                status.setText(message);
        }
}


class ResultsModel extends AbstractTableModel
{

  String[] columnNames = new String[0];
  Vector dataRows;              // Empty vector of rows


  public void setResultSet(ResultSet results)
  {

    if(results == null)
    {
      columnNames = new String[0];        // Reset the columns names
      dataRows.clear();                   // Remove all entries in the Vector
      fireTableChanged(null);             // Tell the table there is new model data
      return;
    }

    try
    {
      ResultSetMetaData metadata = results.getMetaData();

      int columns =  metadata.getColumnCount();    // Get number of columns
      columnNames = new String[columns];           // Array to hold names

      // Get the column names
      for(int i = 0; i < columns; i++)
        columnNames[i] = metadata.getColumnLabel(i+1);


      // Get all rows.
      dataRows = new Vector();                     // New Vector to store the data
      String[] rowData;                            // Stores one row
      while(results.next())                        // For each row...
      {
        rowData = new String[columns];             // create array to hold the data
        for(int i = 0; i < columns; i++)           // For each column
           rowData[i] = results.getString(i+1);    // retrieve the data item

        dataRows.addElement(rowData);              // Store the row in the vector
      }

      fireTableChanged(null);           // Signal the table there is new model data
    }
    catch (SQLException sqle)
    {
      System.err.println(sqle);
    }

  }

  public int getColumnCount()
  {
    return columnNames.length;
  }

  public int getRowCount()
  {
    if(dataRows == null)
      return 0;
    else
      return dataRows.size();
  }

  public Object getValueAt(int row, int column)
  {
    return ((String[])(dataRows.elementAt(row)))[column];
  }

  public String getColumnName(int column)
  {
    return columnNames[column] == null ? "No Name" : columnNames[column];
  }


}

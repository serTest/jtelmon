/*
code.google.com/p/jtelmon/source/browse/Java4Kids/src/JD031/MyTableModel/MyTableModel.java
www.pioverpi.net/2009/06/23/jtables-jdbc-introduction/
www.pioverpi.net/2009/06/24/jtables-jdbc-pt-2-extending-abstracttablemodel/
www.pioverpi.net/2009/06/26/jtables-jdbc-pt-3-connecting-to-your-database-through-jdbc/
www.pioverpi.net/2009/07/02/jtables-jdbc-pt-4-putting-it-all-together/
onjava.com/pub/a/onjava/excerpt/swinghks_hack24/index.html?page=1
onjava.com/pub/a/onjava/excerpt/swinghks_hack24/index.html?page=2
onjava.com/pub/a/onjava/excerpt/swinghks_hack24/index.html?page=3
*/

package JD031.MyTableModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel{

    private Object[][] content;
    private String[] colNames;


   public static void main(String[] args) {

        JFrame myFrame = new JFrame("My Table");
        MyTableModel mt = new MyTableModel();
        JTable jt = new JTable(mt);
        JScrollPane jsp = new JScrollPane(jt);
        myFrame.add(jsp);
        myFrame.setVisible(true);

   }


    public MyTableModel () {
        try {
            // Connection conn = DatabaseData.connect();
            Connection conn = connect();

            content = getTableContent(conn, "app.address");
            colNames = getTableColumnNames(conn, "app.address");

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

    public Object[][] getTableContent(Connection conn, String tableName)
                                                         throws SQLException {
    // String [] colNames = DatabaseData.getTableColumnNames(conn, tableName);
    // String [] colClasses = DatabaseData.getTableColumnClasses(conn, tableName);
    String [] colNames = getTableColumnNames(conn, tableName);
    String [] colClasses = getTableColumnClasses(conn, tableName);


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


    public void disconnect(Connection conn) {
    try {
        conn.close();
    }
    catch (SQLException sqx) {
        System.err.println("Could not disconnect from db, trying again");
    }
}

    public Connection connect(){
    String url = "jdbc:postgresql://localhost:5432/DefaultAddressBook";
    String user = "postgres";
    String pass = "telinit";
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

}

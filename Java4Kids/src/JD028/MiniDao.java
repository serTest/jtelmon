/* 
 * code.google.com/p/jtelmon/source/browse/AddressBookDB/src/pgAddressBook/TemplateDao.java
 * code.google.com/p/jtelmon/source/browse/Java4Kids/src/JD028/MiniDao.java
 * Using Java DB in Desktop Applications
 * java.sun.com/developer/technicalArticles/J2SE/Desktop/javadb/
 */
package JD028;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// printStackTrace();
@SuppressWarnings("CallToThreadDumpStack")
//

public class MiniDao {

    public MiniDao() {
        dbUrl = "jdbc:postgresql://192.168.61.205/DefaultAddressBook?user=postgres&password=telinit";
        String driverName = "org.postgresql.Driver";
        loadDatabaseDriver(driverName);
    }


    private void loadDatabaseDriver(String driverName) {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public boolean connect() {
        try {
            // jdbc.postgresql.org/documentation/84/index.html
            dbConnection = DriverManager.getConnection(dbUrl);
            System.out.println ("Database connection established");
            isConnected = dbConnection != null;
        } catch (SQLException ex) {
            System.out.println ("Database connection couldn't be established");
            isConnected = false;
        }
        return isConnected;
    }


    public void disconnect() {
        if(isConnected) {
            try {
                DriverManager.getConnection(dbUrl).close() ;
                System.out.println ("Database connection terminated");
            } catch (SQLException ex) {
                //
            }
            isConnected = false;
        }
    }


    public List<ListEntry> getListEntries() {
        List<ListEntry> listEntries = new ArrayList<ListEntry>();
        Statement queryStatement = null;
        ResultSet results = null;

        try {
            queryStatement = dbConnection.createStatement();
            results = queryStatement.executeQuery(strGetListEntries);
            while(results.next()) {
                int id = results.getInt(1);
                String lName = results.getString(2);
                String fName = results.getString(3);
                String mName = results.getString(4);

                ListEntry entry = new ListEntry(lName, fName, mName, id);
                listEntries.add(entry);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();

        }

        return listEntries;
    }

    public static void main(String[] args) {
        MiniDao db = new MiniDao();
        db.connect();
        List<ListEntry> entries = db.getListEntries();
        Iterator i = entries.iterator();
        while(i.hasNext())
        {
          ListEntry value= (ListEntry) i.next();
          System.out.println("Value :"+value.getFirstName()+value.getLastName());
        }
        db.disconnect();
    }

    private Connection dbConnection;
    private boolean isConnected;
    private String dbUrl;

    private static final String strGetListEntries =
            "SELECT ID, LASTNAME, FIRSTNAME, MIDDLENAME FROM APP.ADDRESS "  +
            "ORDER BY LASTNAME ASC";

}


/*
 *
 * code.google.com/p/jtelmon/source/browse/AddressBookDB/src/pgAddressBook/TemplateDao.java
 * Template DAO =  Sablon DataAccessObject
 * Initial article :
 *   java.sun.com/developer/technicalArticles/J2SE/Desktop/javadb/
 *
 */


package JD028;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// printStackTrace();
@SuppressWarnings("CallToThreadDumpStack")
//

public class TemplateDao {

    /** Creates a new instance of TemplateDao */
    public TemplateDao() {
       this("DefaultAddressBook");
    }

    public TemplateDao(String addressBookName) {
        // this.dbName = addressBookName;
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
            //stmtSaveNewRecord = dbConnection.prepareStatement(strSaveAddress, Statement.RETURN_GENERATED_KEYS);
            //stmtUpdateExistingRecord = dbConnection.prepareStatement(strUpdateAddress);
            stmtGetAddress = dbConnection.prepareStatement(strGetAddress);
            //stmtDeleteAddress = dbConnection.prepareStatement(strDeleteAddress);

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

    // @SuppressWarnings("CallToThreadDumpStack")
    public Address getAddress(int index) {
        Address address = null;
        try {
            stmtGetAddress.clearParameters();
            stmtGetAddress.setInt(1, index);
            ResultSet result = stmtGetAddress.executeQuery();
            if (result.next()) {
                String lastName = result.getString("LASTNAME");
                String firstName = result.getString("FIRSTNAME");
                String middleName = result.getString("MIDDLENAME");
                String phone = result.getString("PHONE");
                String email = result.getString("EMAIL");
                String add1 = result.getString("ADDRESS1");
                String add2 = result.getString("ADDRESS2");
                String city = result.getString("CITY");
                String state = result.getString("STATE");
                String postalCode = result.getString("POSTALCODE");
                String country = result.getString("COUNTRY");
                int id = result.getInt("ID");
                address = new Address(lastName, firstName, middleName, phone,
                        email, add1, add2, city, state, postalCode,
                        country, id);
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }

        return address;
    }

    public static void main(String[] args) {
        TemplateDao db = new TemplateDao();
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
    private PreparedStatement stmtSaveNewRecord;
    private PreparedStatement stmtUpdateExistingRecord;
    private PreparedStatement stmtGetAddress;
    private PreparedStatement stmtDeleteAddress;


    private static final String strGetAddress =
            "SELECT * FROM APP.ADDRESS " +
            "WHERE ID = ?";


    private static final String strGetListEntries =
            "SELECT ID, LASTNAME, FIRSTNAME, MIDDLENAME FROM APP.ADDRESS "  +
            "ORDER BY LASTNAME ASC";


}

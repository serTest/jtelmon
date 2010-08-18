/*
 * http://code.google.com/p/jtelmon/source/browse/Java4Kids/src/JD028/MiniDao02.java
 * http://code.google.com/p/jtelmon/source/browse/Java4Kids/src/JD028/User.java
 * code.google.com/p/jtelmon/source/browse/AddressBookDB/src/pgAddressBook/TemplateDao.java
 * code.google.com/p/jtelmon/source/browse/Java4Kids/src/JD028/MiniDao.java
 * Using Java DB in Desktop Applications
 * java.sun.com/developer/technicalArticles/J2SE/Desktop/javadb/
 */
package JD028;

/*
  Java Day 029c, Data Access Objects (D.A.O.)
select * from users where user_first_name like 'Server'
=> 61028; "192.168.61.28"; "Server"; "Saratele"; "SQL Server 2008"
        ToDo:
   Sa se introduca de la tastatura numele utilizatorului
iar programul sa afiseze restul informatiilor din baza de date LICENSE
referitoare la utilizatorul respectiv
*/

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

public class MiniDao03 {

    public MiniDao03() {
        //dbUrl = "jdbc:postgresql://192.168.61.205/license?user=postgres&password=telinit";
        dbUrl = "jdbc:postgresql://ftp.pangram.ro/license?user=postgres&password=telinit";
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


    public List<User> getListEntries() {
        List<User> userList = new ArrayList<User>();
        Statement queryStatement = null;
        ResultSet results = null;
        try {
            queryStatement = dbConnection.createStatement();
            results = queryStatement.executeQuery(strGetListEntries);
            while(results.next()) {
                long id = results.getLong(1);
                String user_ip = results.getString(2);
                String firstName = results.getString(3);
                String lastName = results.getString(4);
                User entry = new User(id, user_ip, firstName, lastName);
                userList.add(entry);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return userList;
    }

    public static void main(String[] args) {
        MiniDao03 db = new MiniDao03();
        db.connect();
        List<User> entries = db.getListEntries();
        Iterator i = entries.iterator();
        while(i.hasNext())
        {
          User value= (User) i.next();
          System.out.println(value.getUserId() + " - " + value.getUserIp() + " - " +
                  value.getUserFirstName()+ " - " + value.getUserLastName());
        }
        db.disconnect();
    }

    private Connection dbConnection;
    private boolean isConnected;
    private String dbUrl;

    private static final String strGetListEntries =
            " SELECT user_id, user_ip, user_first_name, user_last_name, alias " +
            " FROM USERS ORDER BY USER_ID ASC ";

}

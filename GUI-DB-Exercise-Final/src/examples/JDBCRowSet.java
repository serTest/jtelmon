package examples;

import java.sql.*;
import com.sun.rowset.JdbcRowSetImpl;

/**
 * Simple wrapper around <code>JdbcRowSetImpl</code> that allows
 * to use the <code>RowSet</code> as a JavaBean.
 *
 * @author Jan Stola
 */
public class JDBCRowSet extends JdbcRowSetImpl {
    /** Class name of the driver used to load this row set. */
    private String driver;

    /**
     * Setter for <code>username</code> property.
     *
     * @param userName user name used by the rowSet to connect to the database.
     */
    public void setUsername(String userName) {
        super.setUsername(userName);
        executeIfPossible();
    }

    /**
     * Setter for <code>password</code> property.
     *
     * @param password password used by the rowSet to connect to the database.
     */
    public void setPassword(String password) {
        super.setPassword(password);
        executeIfPossible();
    }

    /**
     * Setter for <code>command</code> property.
     *
     * @param command SQL command used by this rowSet to populate its data.
     */
    public void setCommand(String command) throws SQLException {
        super.setCommand(command);
        executeIfPossible();
    }

    /**
     * Setter for <code>url</code> property.
     *
     * @param url database URL.
     */
    public void setUrl(String url) throws SQLException {
        super.setUrl(url);
        executeIfPossible();
    }

    /**
     * Getter for <code>driver</code> property.
     *
     * @return class name of the driver used to load this row set.
     */
    public String getDriver() {
        return driver;
    }

    /**
     * Setter for <code>driver</code> property.
     *
     * @return class name of the driver used to load this row set.
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    // Helper method called when some important property is changed.
    // Checks whether all important properties are set and if so
    // then loads/reloads the row set.
    private void executeIfPossible() {
        try {
            if ((getUsername() != null)
            && (getPassword() != null)
            && (getCommand() != null)
            && (getUrl() != null)
            && (getDriver() != null)) {
                Class driverClass = Class.forName(driver);
                // DriverManager invoked through RowSet uses context classloader
                // to find the correct driver => making sure it is set correctly.
                Thread currentThread = Thread.currentThread();
                ClassLoader classLoader = currentThread.getContextClassLoader();
                currentThread.setContextClassLoader(driverClass.getClassLoader());
                execute();
                // Restore the original context classloader in case it is needed somewhere else.
                currentThread.setContextClassLoader(classLoader);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } catch (ClassNotFoundException cnfex) {
            cnfex.printStackTrace();
        }
    }
    
    // Returns connection object used by this row set.
    protected Connection getConnection() {
        return super.getConnection();
    }

}

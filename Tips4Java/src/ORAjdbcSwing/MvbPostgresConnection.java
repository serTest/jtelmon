/*
 *
 */

package ORAjdbcSwing;

import java.sql.*; 

public class MvbPostgresConnection
{
    private static MvbPostgresConnection _mvb = null;
    protected Connection con = null;
    protected boolean driverLoaded = false;


    protected MvbPostgresConnection()
    {
	// empty
    }

    

    public static MvbPostgresConnection getInstance()
    {
	if (_mvb == null)
	{
	    _mvb = new MvbPostgresConnection(); 
	}

	return _mvb;
    }



    public boolean connect(String username, String password)
    {
	try
	{
	    // change the url if the branch table is located somewhere else
	    // String url = "jdbc:oracle:thin:@192.168.61.3:1521:XE";
            String url = "jdbc:postgresql://192.168.61.205:5432/license";
            // jdbc:postgresql://localhost:5432/DefaultAddressBook


	    if (!driverLoaded)
	    {
		// DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                DriverManager.registerDriver(new org.postgresql.Driver());
		driverLoaded = true; 
	    }
 
	    con = DriverManager.getConnection(url, username, password);

	    con.setAutoCommit(false);

	    return true; 
	}
	catch (SQLException ex)
	{
	    return false; 
	}
    }


    /*
     * Returns the connection
     */
    public Connection getConnection()
    {
	return con; 
    }


    /*
     * Sets the connection
     */
    public void setConnection(Connection connect)
    {
	con = connect; 
    }


    /*
     * Returns true if the driver is loaded; false otherwise
     */ 
    public boolean isDriverLoaded()
    {
	return driverLoaded; 
    }


    /*
     * This method allows members of this class to clean up after itself 
     * before it is garbage collected. It is called by the garbage collector.
     */ 
    protected void finalize() throws Throwable
    {		
	if (con != null)
	{
	    con.close();
	}

	// finalize() must call super.finalize() as the last thing it does
	super.finalize();	
    }
}

/*
  www.herongyang.com/JDBC/Oracle-JDBC-Driver-Connection-URL.html
  JDBC Driver Connection URL
  If you want to use the DriverManager class to create connection objects,
  you need to know how to make a connection URL that provides access 
  information to the Oracle server. The Oracle connection URL for the thin 
  client-side driver ojdbc14.jar has the following format:
  jdbc:oracle:thin:[user/password]@[host][:port]:SID
  jdbc:oracle:thin:[user/password]@//[host][:port]/SID
       Here are some example connection URLs:
  jdbc:oracle:thin:Herong/TopSecret@localhost:1521:XE
  jdbc:oracle:thin:Herong/TopSecret@:1521:XE
  jdbc:oracle:thin:Herong/TopSecret@//localhost:1521/XE
  jdbc:oracle:thin:Herong/TopSecret@//:1521/XE
  jdbc:oracle:thin:Herong/TopSecret@//localhost/XE
  jdbc:oracle:thin:Herong/TopSecret@///XE

  user - The login user name defined in the Oracle server.
  password - The password for the login user.
  host - The host name where Oracle server is running. 
         Default is 127.0.0.1 - the IP address of localhost.
  port - The port number where Oracle is listening for connection.
         Default is 1521.
  SID  - System ID of the Oracle server database instance. 
         SID is a required value. By default, Oracle Database 10g Express 
         Edition creates one database instance called XE.
 */

// package ORAjdbcSwing;

import java.sql.*; 


/*
 * This class is a singleton class that provides methods 
 * to connect to an Oracle database, return the connection, 
 * set the connection, and determine whether or not the Oracle
 * JDBC driver has been loaded. To obtain a reference to an
 * instance of this class, use the getInstance() method.
 */ 
public class MvbOracleConnection
{
    private static MvbOracleConnection _mvb = null;
    protected Connection con = null;
    protected boolean driverLoaded = false;


    /*
     * The constructor is declared protected so that only subclasses
     * can access it.
     */ 
    protected MvbOracleConnection()
    {
	// empty
    }

    
    /*
     * Returns an instance of MvbOracleConnection
     */ 
    public static MvbOracleConnection getInstance()
    {
	if (_mvb == null)
	{
	    _mvb = new MvbOracleConnection(); 
	}

	return _mvb;
    }


    /* 
     * Loads the Oracle JDBC driver and connects to the database named ug using 
     * the given username and password.
     * Returns true if the connection is successful; false otherwise.
     */ 
    public boolean connect(String username, String password)
    {
	try
	{
	    // change the url if the branch table is located somewhere else
	    String url = "jdbc:oracle:thin:@192.168.61.3:1521:XE";

	    if (!driverLoaded)
	    {
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
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

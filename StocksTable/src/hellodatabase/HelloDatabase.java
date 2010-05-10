//  SQLiteJDBC 
// http://www.zentus.com/sqlitejdbc/
// http://sqlitebrowser.sourceforge.net/
// http://plugins.netbeans.org/
// http://blog.code-purity.com/archives/2009/2/15/sqlite_jdbc_driver_for_netbeans/
// http://en.wikibooks.org/wiki/An_Introduction_to_Java_JDBC_using_SqLite/Connecting


package hellodatabase;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.ResultSetMetaData;
//import java.sql.DatabaseMetaData;


public class HelloDatabase
{
public static void main (String[] args)
{
// register the driver
String sDriverName = "org.sqlite.JDBC";
try
{
Class.forName(sDriverName);
}
catch(Exception e)
{
System.err.println(e);
}
// now we set up a set of fairly basic string variables to use in the body of the code proper
String sTempDb = "tel.db";
String sJdbc = "jdbc:sqlite"; String sDbUrl = sJdbc + ":" + sTempDb;
// which will produce a legitimate Url for SqlLite JDBC :
// jdbc:sqlite:hello.db
int iTimeout = 30;
String sMakeTable  = "CREATE TABLE telefoane(numar numeric, nume text)";
String sMakeInsert = "INSERT INTO telefoane VALUES(0724353580,'Alex Untaru')";
String sMakeSelect = "SELECT nume from telefoane";
try
{ // create a database connection
Connection conn = DriverManager.getConnection(sDbUrl);
Statement stmt = conn.createStatement();
stmt.setQueryTimeout(iTimeout);
stmt.executeUpdate( "drop table if exists telefoane" );
stmt.executeUpdate( sMakeTable );
stmt.executeUpdate( sMakeInsert );
ResultSet rs = stmt.executeQuery(sMakeSelect);
while(rs.next())
{
String sResult = rs.getString("nume");
System.out.println(sResult);
}
}
catch(SQLException e)
{
// connection failed.
System.err.println(e);
}
}
}

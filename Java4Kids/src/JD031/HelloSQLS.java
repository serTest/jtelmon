/*  SQLS JDBC
 wiki.netbeans.org/DatabasesAndDrivers
 sourceforge.net/projects/jtds/files/
 www.zentus.com/sqlitejdbc/
 sqlitebrowser.sourceforge.net/
 plugins.netbeans.org/
 blog.code-purity.com/archives/2009/2/15/sqlite_jdbc_driver_for_netbeans/
 en.wikibooks.org/wiki/An_Introduction_to_Java_JDBC_using_SqLite/Connecting
*/

package JD031;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class HelloSQLS
{
public static void main (String[] args)
{
  //String sDriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String sDriverName = "net.sourceforge.jtds.jdbc.Driver";
try
{
    Class.forName(sDriverName);
}
    catch(Exception e)
{
    System.err.println(e);
}

int iTimeout = 30;
String sMakeSelect = "select * from PersoanaFizica,Utilizator where " +
 " PersoanaFizica.PersoanaFizica_ID=Utilizator.UtilizatorID order by Nume";
try
{ 
    Connection conn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.61.28:1433;databaseName=pangram;user=sa;password=wy7c1kqt_+");
    Statement stmt = conn.createStatement();
    stmt.setQueryTimeout(iTimeout);
    ResultSet rs = stmt.executeQuery(sMakeSelect);
    while(rs.next())
    {
        String sResult = rs.getString("nume");
        System.out.println(sResult);
    }
}
    catch(SQLException e)
{
    System.err.println(e);
}
}
}

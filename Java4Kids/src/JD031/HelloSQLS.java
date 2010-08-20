/*
 SQLS JDBC
 code.google.com/p/jtelmon/source/browse/Java4Kids/src/JD031/HelloSQLS.java
 wiki.netbeans.org/DatabasesAndDrivers
 sourceforge.net/projects/jtds/files/
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
String sMakeSelect = "select Nume, Prenume, UtilizatorID, Parola " 
        + " from PersoanaFizica,Utilizator "
        + " where PersoanaFizica.PersoanaFizica_ID=Utilizator.UtilizatorID "
        + " order by Nume ";
try
{ 
    Connection conn = DriverManager.getConnection("jdbc:jtds:sqlserver:"
        + "//192.168.61.28:1433;databaseName=pangram;user=sa;password=wy7c1kqt_+");
    Statement stmt = conn.createStatement();
    stmt.setQueryTimeout(iTimeout);
    ResultSet rs = stmt.executeQuery(sMakeSelect);
    while(rs.next())
    {
        String sResult = rs.getString("Nume")+" "+ rs.getString("Prenume")+
                " "+ rs.getString("UtilizatorID")+" "+ rs.getString("Parola");
        System.out.println(sResult);
    }
}
    catch(SQLException e)
{
    System.err.println(e);
}
}
}

/*
    jTDS driver for the SQL Server database
    Driver Location 	jTDS
    Driver Version 	jTDS 1.2.1 jtds-1.2.1-dist.zip
    Driver JAR File 	jtds-1.2.1.jar
    Driver Classname 	net.sourceforge.jtds.jdbc.Driver
    Example URL 	jdbc:jtds:sqlserver://rave-cheetah.sfbay.sun.com:1433/travel
    Open source JDBC 3.0 type 4 driver for Microsoft SQL Server (6.5 up to 2008) and Sybase ASE.
    jTDS is a complete implementation of the JDBC 3.0 spec and the fastest JDBC driver for MS SQL Server.
    For more information see jtds.sourceforge.net
 */


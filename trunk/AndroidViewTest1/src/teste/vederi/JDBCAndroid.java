/*
 * http://premkumarmanivannan.blogspot.com/2010/02/android-jdbc.html
 */

package teste.vederi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

@SuppressWarnings("unused")
public class JDBCAndroid extends Activity {

/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

try {
	String userName = getDataFromOraDB();
	TextView tv = new TextView(this);
	tv.setText(userName);
	setContentView(tv);
} catch (SQLException e) {
	Toast.makeText(this, e.getMessage(), 1).show();
} catch (ClassNotFoundException e) {
	Toast.makeText(this, e.getMessage(), 1).show();
}

}

public String getDataFromOraDB() throws SQLException,
ClassNotFoundException {

String name = null;
String jdbcURL = "jdbc:oracle:thin:@hostname:portname:sid";
String user = "uname";
String passwd = "pwd";
// Load the Oracle JDBC driver

try {
// DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	Connection conn;
	ResultSet rs;
	Statement stmt;
	conn = DriverManager.getConnection(jdbcURL, user, passwd);
	stmt = conn.createStatement();
	rs = stmt.executeQuery("select USERNAME from SomeTableName");
	if (rs.next()) {
		name = rs.getString("USERNAME");
		}
} 
catch (java.sql.SQLException e) {
// TODO Auto-generated catch block
	System.out.println("the exception is" + e.toString());
}
Toast.makeText(getApplicationContext(), name, 1).show();
return name;
}

}

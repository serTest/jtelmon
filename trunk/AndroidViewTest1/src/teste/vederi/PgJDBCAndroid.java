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
public class PgJDBCAndroid extends Activity {

/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

try {
	String userName = getDataFromDB();
	TextView tv = new TextView(this);
	tv.setText(userName);
	setContentView(tv);
} catch (SQLException e) {
	Toast.makeText(this, e.getMessage(), 1).show();
} catch (ClassNotFoundException e) {
	Toast.makeText(this, e.getMessage(), 1).show();
}

}

public String getDataFromDB() throws SQLException,ClassNotFoundException {

String name = null;
String jdbcURL = "jdbc:postgresql://192.168.61.8:5432/license";
String user = "postgres";
String passwd = "telinit";

try {
    Class.forName("org.postgresql.Driver");
    System.out.println("Driver PostgreSQL OK");
} catch (Exception e) {
    System.err.println("Failed to load Postgres Driver");
}

try {
    // DriverManager.registerDriver(new org.postgresql.Driver());
	Connection conn = null;
	ResultSet rs = null;;
	Statement stmt = null;
	conn = DriverManager.getConnection(jdbcURL, user, passwd);
	stmt = conn.createStatement();
	rs = stmt.executeQuery("select user_first_name from users");
	if (rs.next()) {
		name = rs.getString("user_first_name");
		}
} 
catch (java.sql.SQLException e) {
// TODO Auto-generated catch block
	name=e.toString();
	System.out.println("the exception is" + e.toString());
}
	Toast.makeText(getApplicationContext(), name, 1).show();
return name;
}

}

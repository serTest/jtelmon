/*
onjava.com/pub/a/onjava/excerpt/swinghks_hack24/index.html?page=1
onjava.com/pub/a/onjava/excerpt/swinghks_hack24/index.html?page=2
onjava.com/pub/a/onjava/excerpt/swinghks_hack24/index.html?page=3
 */

package JD031.MyTableModel;


	import javax.swing.*;
	import javax.swing.table.*;
	import java.sql.*;
	import java.util.*;
	import java.io.*;
	public class TestJDBCTable {
		public static void main (String[] args) {
			try {

		Class.forName("org.postgresql.Driver").newInstance();

                String url = "jdbc:postgresql://localhost:5432/DefaultAddressBook";
                String user = "postgres";
                String pass = "telinit";

		Connection conn =
			DriverManager.getConnection (url, user, pass);
		// create db table to use
		String tableName = createSampleTable(conn);

		// get a model for this db table and add to a JTable
		TableModel mod = new JDBCTableModel (conn, tableName);
		JTable jtable  = new JTable (mod);
		JScrollPane scroller =
			new JScrollPane (jtable,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JFrame frame = new JFrame ("JDBCTableModel demo");
		frame.getContentPane().add (scroller);
		frame.pack();
		frame.setVisible (true);

		conn.close();

			} catch (Exception e) {
		e.printStackTrace();
			}
		}

		public static String createSampleTable (Connection conn)
			throws SQLException {

			Statement statement = conn.createStatement();
			// drop table if it exists
			try {

		statement.execute ("DROP TABLE EMPLOYEES");
			} catch (SQLException sqle) {
		sqle.printStackTrace(); // if table !exists
			}

			statement.execute ("CREATE TABLE EMPLOYEES " +
				   "(Name CHAR(20), Title CHAR(30), Salary INT)");
			statement.execute ("INSERT INTO EMPLOYEES VALUES " +
				   "('Jill', 'CEO', 200000 )");
			statement.execute ("INSERT INTO EMPLOYEES VALUES " +
				   "('Bob', 'VP', 195000 )");
			statement.execute ("INSERT INTO EMPLOYEES VALUES " +
				       "('Omar', 'VP', 190000 )");
			statement.execute ("INSERT INTO EMPLOYEES VALUES " +
				   "('Amy', 'Software Engineer', 50000 )");
			statement.execute ("INSERT INTO EMPLOYEES VALUES " +
				   "('Greg', 'Software Engineer', 45000 )");

	     statement.close();
		 return "employees";
		 }
	}

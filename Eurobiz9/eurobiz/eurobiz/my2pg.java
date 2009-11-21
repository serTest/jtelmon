package eurobiz;
import java.sql.*;

public class my2pg {
	public static void runImport() {
		String myJdbc = "jdbc:mysql://192.168.1.3/pgrap";
		String usernameJdbc = "root";
		String passwordJdbc = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("Failed to load JDBC driver.");
			return;
		}
		Statement myStmtJdbc = null;
		Connection myConJdbc=null;
		try {
			myConJdbc = DriverManager.getConnection (
					myJdbc,
				usernameJdbc,
				passwordJdbc);
			myStmtJdbc = myConJdbc.createStatement();
		} catch (Exception e) {
			System.err.println("problems connecting to "+myJdbc);
		}
		String pgJdbc2 = "jdbc:postgresql://192.168.1.24/alextest3";
		String usernameJdbc2 = "postgres";
		String passwordJdbc2 = "";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			System.out.println("Failed to load JDBC2 driver.");
			return;
		}
		Statement pgStmtJdbc2 = null;
		Connection pgConJdbc2=null;
		ResultSet myRsJdbc = null;
		try {
			pgConJdbc2 = DriverManager.getConnection (
				pgJdbc2,
				usernameJdbc2,
				passwordJdbc2);
			pgStmtJdbc2 = pgConJdbc2.createStatement();
		} catch (Exception e) {
			System.err.println("problems connecting to "+pgJdbc2);
		}
		try {
			String delete = "drop table q2004";
			pgStmtJdbc2.execute(delete);
		} catch (Exception e) {
			System.out.println("the table does not exist");
		}
		try {
			String SQL = " CREATE TABLE q2004( denmat CHAR(78), " +
						 " cls VARCHAR(16), reclamant VARCHAR(84), cant DOUBLE PRECISION, " +
						 " valoare DOUBLE PRECISION, denconst VARCHAR(100), " +
						 " denmodval VARCHAR(100), seimputa VARCHAR(4), rap_nr INTEGER, " +
						 " data_rap DATE, nrdoc INTEGER, um VARCHAR(12)) ";
			pgStmtJdbc2.execute(SQL);
			myRsJdbc = myStmtJdbc.executeQuery("select * from q2004");
			String denmat="";
			String cls="";
			String reclamant="";
			Double cant = new Double(0);
			Double valoare;
			String denconst="";
			String denmodval="";
			String seimputa="";
			Integer rap_nr;
			Date data_rap;
			Integer nrdoc;
			String um="";
 		    String SQL_4pg = " insert into q2004(denmat,cls,reclamant,cant,valoare," +
 		    					"denconst,denmodval,seimputa,rap_nr,data_rap,nrdoc,um) " +
									"values(?,?,?,?,?,?,?,?,?,?,?,?) ";
		    PreparedStatement ps = pgConJdbc2.prepareStatement(SQL_4pg);
		    while (myRsJdbc.next()) {
				denmat = myRsJdbc.getString("denmat");
				cls = myRsJdbc.getString("cls");
				reclamant = myRsJdbc.getString("reclamant");
				// cant = Double.valueOf(myRsJdbc.getDouble("cant"));
                                // cant = myRsJdbc.getDouble("cant");
				// valoare = Double.valueOf(myRsJdbc.getDouble("valoare"));
				denconst = myRsJdbc.getString("denconst");
				denmodval = myRsJdbc.getString("denmodval");
				seimputa = myRsJdbc.getString("seimputa");
				// rap_nr = Integer.valueOf(myRsJdbc.getInt("rap_nr"));
				data_rap = Date.valueOf(myRsJdbc.getString("data_rap"));
				// nrdoc = Integer.valueOf(myRsJdbc.getInt("nrdoc"));
				um = myRsJdbc.getString("um");
				System.out.println(denmat+" "+reclamant + "  "+cls+" "+denconst);
				ps.setString(1,denmat);
				ps.setString(2,cls);
				ps.setString(3,reclamant);
				// ps.setDouble(4, cant.doubleValue());
                                ps.setDouble(4, myRsJdbc.getDouble("cant"));
				//ps.setDouble(5, valoare.doubleValue());
                                ps.setDouble(5, myRsJdbc.getDouble("valoare"));
				ps.setString(6,denconst);
				ps.setString(7,denmodval);
				ps.setString(8,seimputa);
				// ps.setInt(9,rap_nr.intValue());
                                ps.setInt(9,myRsJdbc.getInt("rap_nr"));
				ps.setDate(10,data_rap);
                                // ps.setInt(11,nrdoc.intValue());
                                ps.setInt(11,myRsJdbc.getInt("nrdoc"));
                                ps.setString(12,um);
			  	//ps.executeQuery();
                                ps.execute();
                        }
			myRsJdbc.close();
			myStmtJdbc.close();
			pgStmtJdbc2.close();
			myConJdbc.close();
			pgConJdbc2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		System.out.println(" Start preluare ...  ");
		runImport();
		System.out.println(" Gata preluare ! ");
	}
}

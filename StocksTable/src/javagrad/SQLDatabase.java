import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.util.Set;
import java.awt.EventQueue;
import javax.swing.JTextArea;
import java.io.StringWriter;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
public class SQLDatabase{

    private final static String DRIVER_NAME= "com.mysql.jdbc.Driver";
    private final static String DATABASE_PREFIX="jdbc:mysql://";
    HashMap tables=null;
    Connection connection=null;
    String username;
    

    public SQLDatabase(String database,String username, String password )
	throws SQLException{
	try{
	    this.username=username;
	    Class driver = Class.forName(DRIVER_NAME);
	    connection = DriverManager.getConnection(
						     DATABASE_PREFIX+database, 
						     username, password);

	    parseTables(connection);
	} catch (ClassNotFoundException e){
	    UI.fatal("Unable to load SQL connector");

	}
	    
    }




    public SQLDatabase(Connection theConnection) {
	connection=theConnection;
	parseTables(connection);
    }
    
   
    public Connection getConnection(){
	return connection;
    }

    public SQLTable findTable(String name){
	SQLTable table=(SQLTable)tables.get(name.toLowerCase());
	if (table==null){
	    Set keySet=tables.keySet();
	    UI.fatal("table "+name+" not found in "+
				       keySet);
	}
	return table;
    }

    private void parseTables(Connection connection)	{

	try{
	    tables=new HashMap();


	    SQLResultSet desc=SQL.query(connection,"show tables",true,false);

	    if (!desc.first())
		UI.fatal("No tables found");
	    
	    do{
		String name=desc.getString(1);
		tables.put(name.toLowerCase(), new SQLTable(this,name));
		desc.relative(1);
	    } while(!desc.isAfterLast());
	}
	catch (SQLException e){
	    System.out.println("Unable to parse database structure");
	    e.printStackTrace();
	    System.exit(1);
	}
	

	
    }

    
    public SQLResultSet query(String queryString){
	return query(queryString,false,false);
    }



    public SQLResultSet query(final String queryString, final boolean readOnly, 
			   final boolean sensitive){

	SQLResultSet queryResults=null;
	queryResults=
	    SQL.query(connection,queryString,readOnly, sensitive);


	return queryResults;

    }


    public void select(String[] fields,
		       String tableName,
		       String whereClause,
		       String orderBy,
		       JTable outputTable){
	select(fields,
	       tableName,
	       whereClause,
	       orderBy,
	       outputTable, false, false);

    }
    public void select(String[] fields,
		       String tableName,
		       String whereClause,
		       JTable outputTable){

	select(fields,
	       tableName,
	       whereClause,
	       null,
	       outputTable, false, false);
    };
	       
	       
    private String makeQueryString(String[] fields,
				   String tableName,
				   String whereClause,
				   String orderBy){
	String result="select ";
	if (fields==null){
	    result += "*";
	} else {
	    for (int i=0 ;i< fields.length; i++){
		if (i>0)  result+=",";
		result+=fields[i];
	    }
	}
	result += " from `"+tableName+"`";

	if (whereClause != null)
	    result += " where " + whereClause;

	if (orderBy !=null)
	    result+= " order by \'"+orderBy+"\'";

	return result;
    }


    public void select(final String[] fields,
		       final String tableName,
		       final String whereClause,
		       final String orderBy,
		       final JTable outputTable, 
		       final boolean readOnly, 
		       final boolean sensitive){


	String queryString=makeQueryString(fields,tableName,whereClause,orderBy);

	try{
	    SQLResultSet results=query(queryString,readOnly,sensitive);
	    SQLTable metaTable=findTable(tableName);
	    outputTable.setModel(new ResultSetTableModel(metaTable,results));
	    outputTable.setColumnModel(metaTable.columnModel(fields));
	}  

	catch (SQLException ex) {
	    UI.fatal("Failed select ",ex);
	}

    }



}


import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

public class SQL{
    public static SQLResultSet query(Connection connection, String queryString){
	return query(connection,queryString,false,false);
    }
    public  static SQLResultSet query(Connection connection, String queryString, boolean readOnly, boolean sensitive){
	
	int scroll_type=ResultSet.TYPE_SCROLL_INSENSITIVE;
	int access_type=ResultSet.CONCUR_UPDATABLE;

	if (readOnly)
	    access_type=ResultSet.CONCUR_READ_ONLY;

	if (sensitive)
	    scroll_type=ResultSet.TYPE_SCROLL_SENSITIVE;

	ResultSet results=null;
	Statement statement=null;
	try {
	    
	    statement =
		connection.createStatement(scroll_type, access_type);
	    // Run the query, creating a ResultSet
	    results = statement.executeQuery(queryString);
	    if (results.getType()!=scroll_type)
		throw new RuntimeException(
			  "Requested scroll type not returned for "+queryString+","+readOnly+","+sensitive);
	    
	    if (results.getConcurrency()!=access_type)
		throw new RuntimeException(
		      "Requested access type "+access_type+" not returned for "+queryString+","+readOnly+","+sensitive);
	}
	catch (SQLException ex) {
	    System.out.println("Failed query "+queryString);
	    
	    throw new RuntimeException(ex.getMessage());
	}


	return new SQLResultSet(statement,results);
    }

}






// Fig. 25.32: JdbcRowSetTest.java
// Displaying the contents of the authors table using JdbcRowSet.

package telemobile08;


import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.sql.rowset.JdbcRowSet;
import com.sun.rowset.JdbcRowSetImpl; // Sun�s JdbcRowSet implementation

public class JdbcRowSetTest 
{
   // JDBC driver name and database URL                              
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        
   static final String DATABASE_URL = "jdbc:mysql://localhost/books";
   static final String USERNAME = "jhtp6";
   static final String PASSWORD = "jhtp6";
   
   // constructor connects to database, queries database, processes 
   // results and displays results in window
   public JdbcRowSetTest() 
   {
      // connect to database books and query database
      try 
      {
         Class.forName( JDBC_DRIVER ); // load database driver class

         // specify properties of JdbcRowSet
         JdbcRowSet rowSet = new JdbcRowSetImpl();  
         rowSet.setUrl( DATABASE_URL ); // set database URL
         rowSet.setUsername( USERNAME ); // set username
         rowSet.setPassword( PASSWORD ); // set password
         rowSet.setCommand( "SELECT * FROM authors" ); // set query
         rowSet.execute(); // execute query

         // process query results
         ResultSetMetaData metaData = rowSet.getMetaData();
         int numberOfColumns = metaData.getColumnCount();
         System.out.println( "Authors Table of Books Database:" );

         // display rowset header
         for ( int i = 1; i <= numberOfColumns; i++ )
            System.out.printf( "%-8s\t", metaData.getColumnName( i ) );
         System.out.println();
         
         // display each row
         while ( rowSet.next() ) 
         {
            for ( int i = 1; i <= numberOfColumns; i++ )
               System.out.printf( "%-8s\t", rowSet.getObject( i ) );
            System.out.println();
         } // end while
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
         System.exit( 1 );
      } // end catch
      catch ( ClassNotFoundException classNotFound ) 
      {
         classNotFound.printStackTrace();            
         System.exit( 1 );
      } // end catch                                 
   } // end DisplayAuthors constructor
   
   // launch the application
   public static void main( String args[] )
   {
      JdbcRowSetTest window = new JdbcRowSetTest();      
   } // end main
} // end class JdbcRowSetTest

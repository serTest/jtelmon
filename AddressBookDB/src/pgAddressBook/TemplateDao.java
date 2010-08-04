/*
 *
 * code.google.com/p/jtelmon/source/browse/AddressBookDB/src/pgAddressBook/TemplateDao.java
 * Template DAO =  Sablon DataAccessObject
 * Initial article :
 *   java.sun.com/developer/technicalArticles/J2SE/Desktop/javadb/
 *
 */

package pgAddressBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TemplateDao {
    
    /** Creates a new instance of TemplateDao */
    public TemplateDao() {
       this("DefaultAddressBook");
    }
    
    public TemplateDao(String addressBookName) {
        // this.dbName = addressBookName;
        dbUrl = "jdbc:postgresql://192.168.61.205/DefaultAddressBook?user=postgres&password=telinit";
        String driverName = "org.postgresql.Driver";
        loadDatabaseDriver(driverName);
    }
        
    private void loadDatabaseDriver(String driverName) {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
   
    public boolean connect() {
        try {
            // jdbc.postgresql.org/documentation/84/index.html
           
            dbConnection = DriverManager.getConnection(dbUrl);
            System.out.println ("Database connection established");
            stmtSaveNewRecord = dbConnection.prepareStatement(strSaveAddress, Statement.RETURN_GENERATED_KEYS);
            stmtUpdateExistingRecord = dbConnection.prepareStatement(strUpdateAddress);
            stmtGetAddress = dbConnection.prepareStatement(strGetAddress);
            stmtDeleteAddress = dbConnection.prepareStatement(strDeleteAddress);
            
            isConnected = dbConnection != null;
        } catch (SQLException ex) {
            System.out.println ("Database connection couldn't be established");
            isConnected = false;
        }
        return isConnected;
    }
    
   
    public void disconnect() {
        if(isConnected) {
            try {
                DriverManager.getConnection(dbUrl).close() ;
                System.out.println ("Database connection terminated");
            } catch (SQLException ex) {
                //
            }
            isConnected = false;
        }
    }
    
   
    public int saveRecord(Address record) {
        int id = -1;
        try {
            stmtSaveNewRecord.clearParameters();
            
            stmtSaveNewRecord.setString(1, record.getLastName());
            stmtSaveNewRecord.setString(2, record.getFirstName());
            stmtSaveNewRecord.setString(3, record.getMiddleName());
            stmtSaveNewRecord.setString(4, record.getPhone());
            stmtSaveNewRecord.setString(5, record.getEmail());
            stmtSaveNewRecord.setString(6, record.getAddress1());
            stmtSaveNewRecord.setString(7, record.getAddress2());
            stmtSaveNewRecord.setString(8, record.getCity());
            stmtSaveNewRecord.setString(9, record.getState());
            stmtSaveNewRecord.setString(10, record.getPostalCode());
            stmtSaveNewRecord.setString(11, record.getCountry());
            int rowCount = stmtSaveNewRecord.executeUpdate();
            ResultSet results = stmtSaveNewRecord.getGeneratedKeys();
            if (results.next()) {
                id = results.getInt(1);
            }
            
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        return id;
    }
    
    public boolean editRecord(Address record) {
        boolean bEdited = false;
        try {
            stmtUpdateExistingRecord.clearParameters();
            
            stmtUpdateExistingRecord.setString(1, record.getLastName());
            stmtUpdateExistingRecord.setString(2, record.getFirstName());
            stmtUpdateExistingRecord.setString(3, record.getMiddleName());
            stmtUpdateExistingRecord.setString(4, record.getPhone());
            stmtUpdateExistingRecord.setString(5, record.getEmail());
            stmtUpdateExistingRecord.setString(6, record.getAddress1());
            stmtUpdateExistingRecord.setString(7, record.getAddress2());
            stmtUpdateExistingRecord.setString(8, record.getCity());
            stmtUpdateExistingRecord.setString(9, record.getState());
            stmtUpdateExistingRecord.setString(10, record.getPostalCode());
            stmtUpdateExistingRecord.setString(11, record.getCountry());
            stmtUpdateExistingRecord.setInt(12, record.getId());
            stmtUpdateExistingRecord.executeUpdate();
            bEdited = true;
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        return bEdited;
        
    }
    
    public boolean deleteRecord(int id) {
        boolean bDeleted = false;
        try {
            stmtDeleteAddress.clearParameters();
            stmtDeleteAddress.setInt(1, id);
            stmtDeleteAddress.executeUpdate();
            bDeleted = true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        
        return bDeleted;
    }
    
    public boolean deleteRecord(Address record) {
        int id = record.getId();
        return deleteRecord(id);
    }
    
    public List<ListEntry> getListEntries() {
        List<ListEntry> listEntries = new ArrayList<ListEntry>();
        Statement queryStatement = null;
        ResultSet results = null;
        
        try {
            queryStatement = dbConnection.createStatement();
            results = queryStatement.executeQuery(strGetListEntries);
            while(results.next()) {
                int id = results.getInt(1);
                String lName = results.getString(2);
                String fName = results.getString(3);
                String mName = results.getString(4);
                
                ListEntry entry = new ListEntry(lName, fName, mName, id);
                listEntries.add(entry);
            }
            
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            
        }
        
        return listEntries;
    }
    
    // @SuppressWarnings("CallToThreadDumpStack")
    public Address getAddress(int index) {
        Address address = null;
        try {
            stmtGetAddress.clearParameters();
            stmtGetAddress.setInt(1, index);
            ResultSet result = stmtGetAddress.executeQuery();
            if (result.next()) {
                String lastName = result.getString("LASTNAME");
                String firstName = result.getString("FIRSTNAME");
                String middleName = result.getString("MIDDLENAME");
                String phone = result.getString("PHONE");
                String email = result.getString("EMAIL");
                String add1 = result.getString("ADDRESS1");
                String add2 = result.getString("ADDRESS2");
                String city = result.getString("CITY");
                String state = result.getString("STATE");
                String postalCode = result.getString("POSTALCODE");
                String country = result.getString("COUNTRY");
                int id = result.getInt("ID");
                address = new Address(lastName, firstName, middleName, phone,
                        email, add1, add2, city, state, postalCode,
                        country, id);
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        
        return address;
    }
    
    public static void main(String[] args) {
        TemplateDao db = new TemplateDao();
        db.connect();
        List<ListEntry> entries = db.getListEntries();
        Iterator i = entries.iterator();
        while(i.hasNext())
        {
          ListEntry value= (ListEntry) i.next();
          System.out.println("Value :"+value.getFirstName()+value.getLastName());
        }
        db.disconnect();
    }
    
    private Connection dbConnection;
    private boolean isConnected;
    private String dbUrl;
    private PreparedStatement stmtSaveNewRecord;
    private PreparedStatement stmtUpdateExistingRecord;
    private PreparedStatement stmtGetAddress;
    private PreparedStatement stmtDeleteAddress;
    
    
    private static final String strGetAddress =
            "SELECT * FROM APP.ADDRESS " +
            "WHERE ID = ?";
    
    private static final String strSaveAddress =
            "INSERT INTO APP.ADDRESS " +
            "   (LASTNAME, FIRSTNAME, MIDDLENAME, PHONE, EMAIL, ADDRESS1, ADDRESS2, " +
            "    CITY, STATE, POSTALCODE, COUNTRY) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    
    private static final String strGetListEntries =
            "SELECT ID, LASTNAME, FIRSTNAME, MIDDLENAME FROM APP.ADDRESS "  +
            "ORDER BY LASTNAME ASC";
    
    private static final String strUpdateAddress =
            "UPDATE APP.ADDRESS " +
            "SET LASTNAME = ?, " +
            "    FIRSTNAME = ?, " +
            "    MIDDLENAME = ?, " +
            "    PHONE = ?, " +
            "    EMAIL = ?, " +
            "    ADDRESS1 = ?, " +
            "    ADDRESS2 = ?, " +
            "    CITY = ?, " +
            "    STATE = ?, " +
            "    POSTALCODE = ?, " +
            "    COUNTRY = ? " +
            "WHERE ID = ?";
    
    private static final String strDeleteAddress =
            "DELETE FROM APP.ADDRESS " +
            "WHERE ID = ?";
    
}



/*
-- DROP DATABASE "DefaultAddressBook";
CREATE DATABASE "DefaultAddressBook"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;
-- DROP SCHEMA app;
CREATE SCHEMA app
  AUTHORIZATION postgres;
CREATE SEQUENCE APP.address_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE address_seq OWNER TO postgres;
CREATE TABLE APP.address
(
  id integer NOT NULL DEFAULT nextval('address_seq'::regclass),
  lastname character varying(30),
  firstname character varying(30),
  middlename character varying(30),
  phone character varying(20),
  email character varying(30),
  address1 character varying(30),
  address2 character varying(30),
  city character varying(30),
  state character varying(30),
  postalcode character varying(20),
  country character varying(30),
  CONSTRAINT address_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE address OWNER TO postgres;
 *
 *  SERIAL
NOTICE:  CREATE TABLE will create implicit sequence "address_id_seq" for serial column "address.id"
NOTICE:  CREATE TABLE / PRIMARY KEY will create implicit index "address_pkey" for table "address"
 *
 * CREATE TABLE APP.address
(
  id serial NOT NULL,
  lastname character varying(30),
  firstname character varying(30),
  middlename character varying(30),
  phone character varying(20),
  email character varying(30),
  address1 character varying(30),
  address2 character varying(30),
  city character varying(30),
  state character varying(30),
  postalcode character varying(20),
  country character varying(30),
  CONSTRAINT address_pkey PRIMARY KEY (id)
)
 *
 *
 */


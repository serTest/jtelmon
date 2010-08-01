/*
 * AddressDao.java
 *
 * Copyright 2006 Sun Microsystems, Inc. ALL RIGHTS RESERVED Use of 
 * this software is authorized pursuant to the terms of the license 
 * found at http://developers.sun.com/berkeley_license.html .

 */

package pgAddressBook;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author John O'Conner
 */
public class AddressDao {
    
    /** Creates a new instance of AddressDao */
    public AddressDao() {
        this("DefaultAddressBook");
    }
    
    public AddressDao(String addressBookName) {
        this.dbName = addressBookName;
        
        dbProperties = loadDBProperties();
        String driverName = dbProperties.getProperty("driver"); 
        loadDatabaseDriver(driverName);
        
    }
    
   
    
    private void loadDatabaseDriver(String driverName) {
        // load Derby driver
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
    }
    
    private Properties loadDBProperties() {
        InputStream dbPropInputStream = null;
        dbPropInputStream = AddressDao.class.getResourceAsStream("Config2.properties");
        dbProperties = new Properties();
        try {
            dbProperties.load(dbPropInputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return dbProperties;
    }
    
    
    
    
    public boolean connect() {
        String dbUrl = getDatabaseUrl();
        try {
            dbConnection = DriverManager.getConnection(dbUrl, dbProperties);
            stmtSaveNewRecord = dbConnection.prepareStatement(strSaveAddress, Statement.RETURN_GENERATED_KEYS);
            stmtUpdateExistingRecord = dbConnection.prepareStatement(strUpdateAddress);
            stmtGetAddress = dbConnection.prepareStatement(strGetAddress);
            stmtDeleteAddress = dbConnection.prepareStatement(strDeleteAddress);
            
            isConnected = dbConnection != null;
        } catch (SQLException ex) {
            isConnected = false;
        }
        return isConnected;
    }
    
   
    public void disconnect() {
        if(isConnected) {
            String dbUrl = getDatabaseUrl();
            dbProperties.put("shutdown", "true");
            try {
                DriverManager.getConnection(dbUrl, dbProperties);
            } catch (SQLException ex) {
            }
            isConnected = false;
        }
    }
    

    public String getDatabaseUrl() {
        String dbUrl = dbProperties.getProperty("url") + dbName;
        return dbUrl;
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
        AddressDao db = new AddressDao();
        System.out.println(db.getDatabaseUrl());
        db.connect();
        db.disconnect();
    }
    
    
    private Connection dbConnection;
    private Properties dbProperties;
    private boolean isConnected;
    private String dbName;
    private PreparedStatement stmtSaveNewRecord;
    private PreparedStatement stmtUpdateExistingRecord;
    private PreparedStatement stmtGetListEntries;
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
 */


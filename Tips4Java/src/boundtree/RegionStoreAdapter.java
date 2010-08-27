/*
  Copyright (c) 2005, Ulrich Hilger, Light Development, http://www.lightdev.com
  All rights reserved.

  Redistribution and use in source and binary forms, with or without modification, 
  are permitted provided that the following conditions are met:

    - Redistributions of source code must retain the above copyright notice, this 
       list of conditions and the following disclaimer.
       
    - Redistributions in binary form must reproduce the above copyright notice, 
       this list of conditions and the following disclaimer in the documentation 
       and/or other materials provided with the distribution.
       
    - Neither the name of Light Development nor the names of its contributors may be 
       used to endorse or promote products derived from this software without specific 
       prior written permission.

  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
  SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT 
  OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
  HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
  TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
*/

package boundtree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * RegionStoreAdapter.java
 * 
 * <p>In the generic context of JPersistentTree and TreeStorageController this class 
 * is the specific implementation required to actually store to and retrieve 
 * from a particular data store. Typically for each data store such a class needs 
 * to be built. However, depending on the actual implementation such class can as well be 
 * re-used for various data stores too. E.g. when implementing a variable way to connect to a 
 * JDBC data source such RegionStore class could be re-used with different region data 
 * sources accordingly.</p> 
 * 
 * <p>To not make our example not overly complicated, RegionStoreAdapter implements interface 
 * HierarchicalDataStore specifically for reading and writing via JDBC to and from 
 * a relational MySQL database using fixed schema, table and field names. It refers to 
 * a database table as defined with the SQL script 'create_region_table.sql' included 
 * in this example package.</p>
 * 
 * <p>What should be demonstrated is the fact that the persistent tree framework can remain 
 * unchanged entirely for all kinds of data stores. Only the part relating to the specific 
 * data store, i.e. type (file, database, XML, etc.) and the way to connect 
 * (local, JDBC, app server, etc.) needs to be implementend individually with a single adapter 
 * class such as RegionStoreAdapter.</p>
 * 
 * <p>Certainly the framework can be extended by adding generic JDBCDataStores, XMLDataStores, 
 * RPCDataStores, etc. but this would exceed the scope of this example.</p>
 * 
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License,
 *      for details see file license.txt in the distribution package of this software
 *
 * @version 1, 18.09.2005
 */
public class RegionStoreAdapter implements HierarchicalDataStore {

  /**
   * construct a new RegionStoreAdapter
   * @param serverName  the server this store refers to
   * @param dbName  the database name this adapter refers to
   * @param userName  the user name to connect under
   * @param password  the password to use to connect
   */
  public RegionStoreAdapter(String serverName, String dbName, String userName, String password) {
    super();
    this.serverName = serverName;
    this.userName = userName;
    this.dbName = dbName;
    this.password = password;
  }

  /**
   * get the item with the given id
   * @param id  the id of the item to get
   * @return the item with the given id or null, if none was found with that id
   * @throws StorageException
   */
  public HierarchicalItem getItem(Object id) throws StorageException {
    Region r = null;
    // String sql = "select * from " + dbName + ".regions where rgId=" + id.toString();
    String sql = "select * from regions where rgId=" + id.toString();
    try {
      ResultSet rs = getConnection().createStatement().executeQuery(sql);
      if (rs.first() && !rs.isAfterLast()) {
        r = new Region(rs.getInt("rgId"), rs.getString("rgName"));
        r.setParentId(rs.getInt("parentId"));
      }
      rs.close();
    }
    catch (SQLException e) {
      throw new StorageException(e);
    }
    return r;
  }

  /**
   * get all child items of a given item
   * @param id the id of the item to get child items for
   * @return  a Vector of HierarchicalItem objects 
   * or null if an item with the given id was not found
   * @throws StorageException
   */
  public Vector<HierarchicalItem> getChildItems(Object id) throws StorageException {
    Vector<HierarchicalItem> v = new Vector();
    StringBuffer sql = new StringBuffer();
    sql.append("select * from ");
    // sql.append(dbName);
    sql.append(" regions where parentId=");
    sql.append(id.toString());
    try {
      ResultSet rs = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sql.toString());
      if (rs.first()) {
        while(!rs.isAfterLast()) {
          Region r = new Region(rs.getInt("rgId"), rs.getString("rgName"));
          r.setParentId(id);
          v.addElement(r);
          rs.next();
        }
      }
      else {
        v = null;
      }
    }
    catch (SQLException e) {
      throw new StorageException(e);
    }
    return v;
  }

  /**
   * get all root items in the data store, i.e. all top level items in the item 
   * hierarchy that have no parent (parent ID = -1)
   * @return a Vector of HierarchicalItems with all root items
   * @throws StorageException
   */
	public Vector<HierarchicalItem> getRootItems() throws StorageException {
    Vector<HierarchicalItem> v = new Vector();
    StringBuffer sql = new StringBuffer();
    sql.append("select * from ");
    // sql.append(dbName);
    sql.append(" regions where parentId=0");
    try {
      ResultSet rs = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sql.toString());
      if (rs.first() && !rs.isAfterLast()) {
        while(!rs.isAfterLast()) {
          Region r = new Region(rs.getInt("rgId"), rs.getString("rgName"));
          r.setParentId(new Integer(-1));
          v.addElement(r);
          rs.next();
        }
      }
      else {
        v = null;
      }
    }
    catch (SQLException e) {
      throw new StorageException(e);
    }
    return v;
	}
	
  /**
   * store an item with the given id
   * @param item  the item to store
   * @return the item that has been stored or null if no item with that id was found
   * @throws StorageException
   */
  public HierarchicalItem putItem(HierarchicalItem item) throws StorageException {
    HierarchicalItem returnItem = null;
    StringBuffer sql = new StringBuffer();
    sql.append("update ");
    // sql.append(dbName);
    sql.append(" regions set rgName='");
    sql.append(item.getData().toString());
    sql.append("', parentId=");
    sql.append(item.getParentId().toString());
    sql.append(" where rgId=");
    sql.append(item.getId().toString());
    try {
      int result = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(sql.toString());
      if(result > 0) {
        returnItem = item;
      }
    }
    catch (SQLException e) {
      throw new StorageException(e);
    }
    return returnItem;
  }

  /**
   * create a new item in the data store
   * @param item  the new item to create
   * @return  the id of the newly create item
   * @throws StorageException
   */
  public Object createItem(HierarchicalItem item) throws StorageException {
    Integer newId = null;
    StringBuffer sql = new StringBuffer();
    sql.append("insert into ");
    // sql.append(dbName);
    sql.append(" regions (rgname,parentid) values ( '");
    sql.append(item.getData().toString());
    sql.append(" ', ");
    sql.append(item.getParentId().toString());
    sql.append(" )");
    try {
      Connection c = getConnection();
      c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(sql.toString());
      sql = new StringBuffer();
      sql.append("select rgId from ");
      //sql.append(dbName);
      sql.append(" regions where rgname='");
      sql.append(item.getData().toString());
      sql.append("'");
      ResultSet rs = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sql.toString());
      if (rs.first() && !rs.isAfterLast()) {
        newId = rs.getInt("rgId");
      }
      rs.close();
    }
    catch(Exception e) {
      throw new StorageException(e);
    }
    return newId;
  }

  /**
   * delete the item with the given id from the data store 
   * @param id
   * @return  the id of the deleted item or null if an item with the given id was not found
   * @throws StorageException
   */
  public Object deleteItem(Object id) throws StorageException {
    Object deletedId = null;
    StringBuffer sql = new StringBuffer();
    sql.append("delete from ");
    // sql.append(dbName);
    sql.append(" regions where rgId=");
    sql.append(id.toString());
    try {
      int result = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).executeUpdate(sql.toString());
      if(result > 0) {
        deletedId = id;
      }
    }
    catch (SQLException e) {
      throw new StorageException(e);
    }
    return deletedId;
  }
  
  private Connection getConnection() {
    if(connection == null) {
      try {
        Class.forName("org.postgresql.Driver").newInstance();
        StringBuffer buf = new StringBuffer();
        buf.append("jdbc:postgresql://");
        buf.append(serverName);
        buf.append("/");
        buf.append(dbName);
        buf.append("?user=");
        buf.append(userName);
        buf.append("&password=");
        buf.append(password);
        connection = (Connection) DriverManager.getConnection(buf.toString());
      }
      catch (Exception e) {
        e.printStackTrace();
      }      
    }
    return connection;
  }

  /**
   * close the connection this adapter holds (if any)
   */
  public void closeConnection() {
    if(connection != null) {
      try {
        connection.close();
        connection = null;
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
    }
  } 

  private Connection connection;
  private String serverName;
  private String userName;
  private String password;
  private String dbName;

}

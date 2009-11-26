package telemobile07;
// http://www.cs.unb.ca/~bremner//teaching/java_examples/javagrad/

import java.sql.ResultSet;
import java.sql.Array;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.SQLWarning;
import java.sql.Blob;
import java.sql.Ref;
import java.util.Calendar;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.math.BigDecimal;
import java.io.Reader;
import java.sql.SQLException;
import java.io.InputStream;
import java.util.Map;
import java.net.URL;

public class SQLResultSet implements ResultSet{

    Statement statement=null;
    ResultSet results=null;

    // Code for delegation of java.sql.ResultSet methods to results

    /**
     * Describe <code>getObject</code> method here.
     *
     * @param string a <code>String</code> value
     * @return an <code>Object</code> value
     * @exception SQLException if an error occurs
     */
    public Object getObject(String string) throws SQLException {
	return results.getObject(string);
    }

    /**
     * Describe <code>getObject</code> method here.
     *
     * @param n an <code>int</code> value
     * @param map a <code>Map</code> value
     * @return an <code>Object</code> value
     * @exception SQLException if an error occurs
     */
    public Object getObject(int n, Map map) throws SQLException {
	return results.getObject(n, map);
    }

    /**
     * Describe <code>getObject</code> method here.
     *
     * @param n an <code>int</code> value
     * @return an <code>Object</code> value
     * @exception SQLException if an error occurs
     */
    public Object getObject(int n) throws SQLException {
	return results.getObject(n);
    }

    /**
     * Describe <code>getObject</code> method here.
     *
     * @param string a <code>String</code> value
     * @param map a <code>Map</code> value
     * @return an <code>Object</code> value
     * @exception SQLException if an error occurs
     */
    public Object getObject(String string, Map map) throws SQLException {
	return results.getObject(string, map);
    }

    /**
     * Describe <code>getBoolean</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean getBoolean(int n) throws SQLException {
	return results.getBoolean(n);
    }

    /**
     * Describe <code>getBoolean</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean getBoolean(String string) throws SQLException {
	return results.getBoolean(string);
    }

    /**
     * Describe <code>getByte</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>byte</code> value
     * @exception SQLException if an error occurs
     */
    public byte getByte(String string) throws SQLException {
	return results.getByte(string);
    }

    /**
     * Describe <code>getByte</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>byte</code> value
     * @exception SQLException if an error occurs
     */
    public byte getByte(int n) throws SQLException {
	return results.getByte(n);
    }

    /**
     * Describe <code>getShort</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>short</code> value
     * @exception SQLException if an error occurs
     */
    public short getShort(String string) throws SQLException {
	return results.getShort(string);
    }

    /**
     * Describe <code>getShort</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>short</code> value
     * @exception SQLException if an error occurs
     */
    public short getShort(int n) throws SQLException {
	return results.getShort(n);
    }

    /**
     * Describe <code>getInt</code> method here.
     *
     * @param string a <code>String</code> value
     * @return an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public int getInt(String string) throws SQLException {
	return results.getInt(string);
    }

    /**
     * Describe <code>getInt</code> method here.
     *
     * @param n an <code>int</code> value
     * @return an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public int getInt(int n) throws SQLException {
	return results.getInt(n);
    }

    /**
     * Describe <code>getLong</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>long</code> value
     * @exception SQLException if an error occurs
     */
    public long getLong(String string) throws SQLException {
	return results.getLong(string);
    }

    /**
     * Describe <code>getLong</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>long</code> value
     * @exception SQLException if an error occurs
     */
    public long getLong(int n) throws SQLException {
	return results.getLong(n);
    }

    /**
     * Describe <code>getFloat</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>float</code> value
     * @exception SQLException if an error occurs
     */
    public float getFloat(String string) throws SQLException {
	return results.getFloat(string);
    }

    /**
     * Describe <code>getFloat</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>float</code> value
     * @exception SQLException if an error occurs
     */
    public float getFloat(int n) throws SQLException {
	return results.getFloat(n);
    }

    /**
     * Describe <code>getDouble</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>double</code> value
     * @exception SQLException if an error occurs
     */
    public double getDouble(int n) throws SQLException {
	return results.getDouble(n);
    }

    /**
     * Describe <code>getDouble</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>double</code> value
     * @exception SQLException if an error occurs
     */
    public double getDouble(String string) throws SQLException {
	return results.getDouble(string);
    }

    /**
     * Describe <code>getBytes</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>byte[]</code> value
     * @exception SQLException if an error occurs
     */
    public byte[] getBytes(String string) throws SQLException {
	return results.getBytes(string);
    }

    /**
     * Describe <code>getBytes</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>byte[]</code> value
     * @exception SQLException if an error occurs
     */
    public byte[] getBytes(int n) throws SQLException {
	return results.getBytes(n);
    }

    /**
     * Describe <code>getArray</code> method here.
     *
     * @param n an <code>int</code> value
     * @return an <code>Array</code> value
     * @exception SQLException if an error occurs
     */
    public Array getArray(int n) throws SQLException {
	return results.getArray(n);
    }

    /**
     * Describe <code>getArray</code> method here.
     *
     * @param string a <code>String</code> value
     * @return an <code>Array</code> value
     * @exception SQLException if an error occurs
     */
    public Array getArray(String string) throws SQLException {
	return results.getArray(string);
    }

    /**
     * Describe <code>getURL</code> method here.
     *
     * @param n an <code>int</code> value
     * @return an <code>URL</code> value
     * @exception SQLException if an error occurs
     */
    public URL getURL(int n) throws SQLException {
	return results.getURL(n);
    }

    /**
     * Describe <code>getURL</code> method here.
     *
     * @param string a <code>String</code> value
     * @return an <code>URL</code> value
     * @exception SQLException if an error occurs
     */
    public URL getURL(String string) throws SQLException {
	return results.getURL(string);
    }

    /**
     * Describe <code>next</code> method here.
     *
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean next() throws SQLException {
	return results.next();
    }

    /**
     * Describe <code>getType</code> method here.
     *
     * @return an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public int getType() throws SQLException {
	return results.getType();
    }

    /**
     * Describe <code>previous</code> method here.
     *
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean previous() throws SQLException {
	return results.previous();
    }

    /**
     * Describe <code>close</code> method here.
     *
     * @exception SQLException if an error occurs
     */
    public void close() throws SQLException {
	results.close();
    }

    /**
     * Describe <code>getRef</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>Ref</code> value
     * @exception SQLException if an error occurs
     */
    public Ref getRef(int n) throws SQLException {
	return results.getRef(n);
    }

    /**
     * Describe <code>getRef</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>Ref</code> value
     * @exception SQLException if an error occurs
     */
    public Ref getRef(String string) throws SQLException {
	return results.getRef(string);
    }

    /**
     * Describe <code>getTime</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>Time</code> value
     * @exception SQLException if an error occurs
     */
    public Time getTime(int n) throws SQLException {
	return results.getTime(n);
    }

    /**
     * Describe <code>getTime</code> method here.
     *
     * @param n an <code>int</code> value
     * @param calendar a <code>Calendar</code> value
     * @return a <code>Time</code> value
     * @exception SQLException if an error occurs
     */
    public Time getTime(int n, Calendar calendar) throws SQLException {
	return results.getTime(n, calendar);
    }

    /**
     * Describe <code>getTime</code> method here.
     *
     * @param string a <code>String</code> value
     * @param calendar a <code>Calendar</code> value
     * @return a <code>Time</code> value
     * @exception SQLException if an error occurs
     */
    public Time getTime(String string, Calendar calendar) throws SQLException {
	return results.getTime(string, calendar);
    }

    /**
     * Describe <code>getTime</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>Time</code> value
     * @exception SQLException if an error occurs
     */
    public Time getTime(String string) throws SQLException {
	return results.getTime(string);
    }

    /**
     * Describe <code>getDate</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>Date</code> value
     * @exception SQLException if an error occurs
     */
    public Date getDate(int n) throws SQLException {
	return results.getDate(n);
    }

    /**
     * Describe <code>getDate</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>Date</code> value
     * @exception SQLException if an error occurs
     */
    public Date getDate(String string) throws SQLException {
	return results.getDate(string);
    }

    /**
     * Describe <code>getDate</code> method here.
     *
     * @param n an <code>int</code> value
     * @param calendar a <code>Calendar</code> value
     * @return a <code>Date</code> value
     * @exception SQLException if an error occurs
     */
    public Date getDate(int n, Calendar calendar) throws SQLException {
	return results.getDate(n, calendar);
    }

    /**
     * Describe <code>getDate</code> method here.
     *
     * @param string a <code>String</code> value
     * @param calendar a <code>Calendar</code> value
     * @return a <code>Date</code> value
     * @exception SQLException if an error occurs
     */
    public Date getDate(String string, Calendar calendar) throws SQLException {
	return results.getDate(string, calendar);
    }

    /**
     * Describe <code>first</code> method here.
     *
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean first() throws SQLException {
	return results.first();
    }

    /**
     * Describe <code>getString</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>String</code> value
     * @exception SQLException if an error occurs
     */
    public String getString(String string) throws SQLException {
	return results.getString(string);
    }

    /**
     * Describe <code>getString</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>String</code> value
     * @exception SQLException if an error occurs
     */
    public String getString(int n) throws SQLException {
	return results.getString(n);
    }

    /**
     * Describe <code>getWarnings</code> method here.
     *
     * @return a <code>SQLWarning</code> value
     * @exception SQLException if an error occurs
     */
    public SQLWarning getWarnings() throws SQLException {
	return results.getWarnings();
    }

    /**
     * Describe <code>clearWarnings</code> method here.
     *
     * @exception SQLException if an error occurs
     */
    public void clearWarnings() throws SQLException {
	results.clearWarnings();
    }

    /**
     * Describe <code>getMetaData</code> method here.
     *
     * @return a <code>ResultSetMetaData</code> value
     * @exception SQLException if an error occurs
     */
    public ResultSetMetaData getMetaData() throws SQLException {
	return results.getMetaData();
    }

    /**
     * Describe <code>updateBytes</code> method here.
     *
     * @param string a <code>String</code> value
     * @param byteArray a <code>byte[]</code> value
     * @exception SQLException if an error occurs
     */
    public void updateBytes(String string, byte[] byteArray) throws SQLException {
	results.updateBytes(string, byteArray);
    }

    /**
     * Describe <code>updateBytes</code> method here.
     *
     * @param n an <code>int</code> value
     * @param byteArray a <code>byte[]</code> value
     * @exception SQLException if an error occurs
     */
    public void updateBytes(int n, byte[] byteArray) throws SQLException {
	results.updateBytes(n, byteArray);
    }

    /**
     * Describe <code>last</code> method here.
     *
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean last() throws SQLException {
	return results.last();
    }

    /**
     * Describe <code>updateTime</code> method here.
     *
     * @param string a <code>String</code> value
     * @param time a <code>Time</code> value
     * @exception SQLException if an error occurs
     */
    public void updateTime(String string, Time time) throws SQLException {
	results.updateTime(string, time);
    }

    /**
     * Describe <code>updateTime</code> method here.
     *
     * @param n an <code>int</code> value
     * @param time a <code>Time</code> value
     * @exception SQLException if an error occurs
     */
    public void updateTime(int n, Time time) throws SQLException {
	results.updateTime(n, time);
    }

    /**
     * Describe <code>getTimestamp</code> method here.
     *
     * @param n an <code>int</code> value
     * @param calendar a <code>Calendar</code> value
     * @return a <code>Timestamp</code> value
     * @exception SQLException if an error occurs
     */
    public Timestamp getTimestamp(int n, Calendar calendar) throws SQLException {
	return results.getTimestamp(n, calendar);
    }

    /**
     * Describe <code>getTimestamp</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>Timestamp</code> value
     * @exception SQLException if an error occurs
     */
    public Timestamp getTimestamp(int n) throws SQLException {
	return results.getTimestamp(n);
    }

    /**
     * Describe <code>getTimestamp</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>Timestamp</code> value
     * @exception SQLException if an error occurs
     */
    public Timestamp getTimestamp(String string) throws SQLException {
	return results.getTimestamp(string);
    }

    /**
     * Describe <code>getTimestamp</code> method here.
     *
     * @param string a <code>String</code> value
     * @param calendar a <code>Calendar</code> value
     * @return a <code>Timestamp</code> value
     * @exception SQLException if an error occurs
     */
    public Timestamp getTimestamp(String string, Calendar calendar) throws SQLException {
	return results.getTimestamp(string, calendar);
    }

    /**
     * Describe <code>relative</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean relative(int n) throws SQLException {
	return results.relative(n);
    }

    /**
     * Describe <code>getCharacterStream</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>Reader</code> value
     * @exception SQLException if an error occurs
     */
    public Reader getCharacterStream(String string) throws SQLException {
	return results.getCharacterStream(string);
    }

    /**
     * Describe <code>getCharacterStream</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>Reader</code> value
     * @exception SQLException if an error occurs
     */
    public Reader getCharacterStream(int n) throws SQLException {
	return results.getCharacterStream(n);
    }

    /**
     * Describe <code>isFirst</code> method here.
     *
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean isFirst() throws SQLException {
	return results.isFirst();
    }

    /**
     * Describe <code>setFetchDirection</code> method here.
     *
     * @param n an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public void setFetchDirection(int n) throws SQLException {
	results.setFetchDirection(n);
    }

    /**
     * Describe <code>getFetchDirection</code> method here.
     *
     * @return an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public int getFetchDirection() throws SQLException {
	return results.getFetchDirection();
    }

    /**
     * Describe <code>setFetchSize</code> method here.
     *
     * @param n an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public void setFetchSize(int n) throws SQLException {
	results.setFetchSize(n);
    }

    /**
     * Describe <code>getFetchSize</code> method here.
     *
     * @return an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public int getFetchSize() throws SQLException {
	return results.getFetchSize();
    }

    /**
     * Describe <code>wasNull</code> method here.
     *
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean wasNull() throws SQLException {
	return results.wasNull();
    }

    /**
     * Describe <code>getBigDecimal</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>BigDecimal</code> value
     * @exception SQLException if an error occurs
     */
    public BigDecimal getBigDecimal(int n) throws SQLException {
	return results.getBigDecimal(n);
    }

    /**
     * Describe <code>getBigDecimal</code> method here.
     *
     * @param n an <code>int</code> value
     * @param n1 an <code>int</code> value
     * @return a <code>BigDecimal</code> value
     * @exception SQLException if an error occurs
     */
    public BigDecimal getBigDecimal(int n, int n1) throws SQLException {
	return results.getBigDecimal(n, n1);
    }

    /**
     * Describe <code>getBigDecimal</code> method here.
     *
     * @param string a <code>String</code> value
     * @param n an <code>int</code> value
     * @return a <code>BigDecimal</code> value
     * @exception SQLException if an error occurs
     */
    public BigDecimal getBigDecimal(String string, int n) throws SQLException {
	return results.getBigDecimal(string, n);
    }

    /**
     * Describe <code>getBigDecimal</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>BigDecimal</code> value
     * @exception SQLException if an error occurs
     */
    public BigDecimal getBigDecimal(String string) throws SQLException {
	return results.getBigDecimal(string);
    }

    /**
     * Describe <code>getAsciiStream</code> method here.
     *
     * @param string a <code>String</code> value
     * @return an <code>InputStream</code> value
     * @exception SQLException if an error occurs
     */
    public InputStream getAsciiStream(String string) throws SQLException {
	return results.getAsciiStream(string);
    }

    /**
     * Describe <code>getAsciiStream</code> method here.
     *
     * @param n an <code>int</code> value
     * @return an <code>InputStream</code> value
     * @exception SQLException if an error occurs
     */
    public InputStream getAsciiStream(int n) throws SQLException {
	return results.getAsciiStream(n);
    }

    /**
     * Describe <code>getUnicodeStream</code> method here.
     *
     * @param n an <code>int</code> value
     * @return an <code>InputStream</code> value
     * @exception SQLException if an error occurs
     */
    public InputStream getUnicodeStream(int n) throws SQLException {
	return results.getUnicodeStream(n);
    }

    /**
     * Describe <code>getUnicodeStream</code> method here.
     *
     * @param string a <code>String</code> value
     * @return an <code>InputStream</code> value
     * @exception SQLException if an error occurs
     */
    public InputStream getUnicodeStream(String string) throws SQLException {
	return results.getUnicodeStream(string);
    }

    /**
     * Describe <code>getBinaryStream</code> method here.
     *
     * @param n an <code>int</code> value
     * @return an <code>InputStream</code> value
     * @exception SQLException if an error occurs
     */
    public InputStream getBinaryStream(int n) throws SQLException {
	return results.getBinaryStream(n);
    }

    /**
     * Describe <code>getBinaryStream</code> method here.
     *
     * @param string a <code>String</code> value
     * @return an <code>InputStream</code> value
     * @exception SQLException if an error occurs
     */
    public InputStream getBinaryStream(String string) throws SQLException {
	return results.getBinaryStream(string);
    }

    /**
     * Describe <code>getCursorName</code> method here.
     *
     * @return a <code>String</code> value
     * @exception SQLException if an error occurs
     */
    public String getCursorName() throws SQLException {
	return results.getCursorName();
    }

    /**
     * Describe <code>findColumn</code> method here.
     *
     * @param string a <code>String</code> value
     * @return an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public int findColumn(String string) throws SQLException {
	return results.findColumn(string);
    }

    /**
     * Describe <code>isBeforeFirst</code> method here.
     *
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean isBeforeFirst() throws SQLException {
	return results.isBeforeFirst();
    }

    /**
     * Describe <code>isAfterLast</code> method here.
     *
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean isAfterLast() throws SQLException {
	return results.isAfterLast();
    }

    /**
     * Describe <code>isLast</code> method here.
     *
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean isLast() throws SQLException {
	return results.isLast();
    }

    /**
     * Describe <code>beforeFirst</code> method here.
     *
     * @exception SQLException if an error occurs
     */
    public void beforeFirst() throws SQLException {
	results.beforeFirst();
    }

    /**
     * Describe <code>afterLast</code> method here.
     *
     * @exception SQLException if an error occurs
     */
    public void afterLast() throws SQLException {
	results.afterLast();
    }

    /**
     * Describe <code>getRow</code> method here.
     *
     * @return an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public int getRow() throws SQLException {
	return results.getRow();
    }

    /**
     * Describe <code>absolute</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean absolute(int n) throws SQLException {
	return results.absolute(n);
    }

    /**
     * Describe <code>getConcurrency</code> method here.
     *
     * @return an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public int getConcurrency() throws SQLException {
	return results.getConcurrency();
    }

    /**
     * Describe <code>rowUpdated</code> method here.
     *
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean rowUpdated() throws SQLException {
	return results.rowUpdated();
    }

    /**
     * Describe <code>rowInserted</code> method here.
     *
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean rowInserted() throws SQLException {
	return results.rowInserted();
    }

    /**
     * Describe <code>rowDeleted</code> method here.
     *
     * @return a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public boolean rowDeleted() throws SQLException {
	return results.rowDeleted();
    }

    /**
     * Describe <code>updateNull</code> method here.
     *
     * @param string a <code>String</code> value
     * @exception SQLException if an error occurs
     */
    public void updateNull(String string) throws SQLException {
	results.updateNull(string);
    }

    /**
     * Describe <code>updateNull</code> method here.
     *
     * @param n an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public void updateNull(int n) throws SQLException {
	results.updateNull(n);
    }

    /**
     * Describe <code>updateBoolean</code> method here.
     *
     * @param n an <code>int</code> value
     * @param flag a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public void updateBoolean(int n, boolean flag) throws SQLException {
	results.updateBoolean(n, flag);
    }

    /**
     * Describe <code>updateBoolean</code> method here.
     *
     * @param string a <code>String</code> value
     * @param flag a <code>boolean</code> value
     * @exception SQLException if an error occurs
     */
    public void updateBoolean(String string, boolean flag) throws SQLException {
	results.updateBoolean(string, flag);
    }

    /**
     * Describe <code>updateByte</code> method here.
     *
     * @param n an <code>int</code> value
     * @param b a <code>byte</code> value
     * @exception SQLException if an error occurs
     */
    public void updateByte(int n, byte b) throws SQLException {
	results.updateByte(n, b);
    }

    /**
     * Describe <code>updateByte</code> method here.
     *
     * @param string a <code>String</code> value
     * @param b a <code>byte</code> value
     * @exception SQLException if an error occurs
     */
    public void updateByte(String string, byte b) throws SQLException {
	results.updateByte(string, b);
    }

    /**
     * Describe <code>updateShort</code> method here.
     *
     * @param n an <code>int</code> value
     * @param s a <code>short</code> value
     * @exception SQLException if an error occurs
     */
    public void updateShort(int n, short s) throws SQLException {
	results.updateShort(n, s);
    }

    /**
     * Describe <code>updateShort</code> method here.
     *
     * @param string a <code>String</code> value
     * @param s a <code>short</code> value
     * @exception SQLException if an error occurs
     */
    public void updateShort(String string, short s) throws SQLException {
	results.updateShort(string, s);
    }

    /**
     * Describe <code>updateInt</code> method here.
     *
     * @param n an <code>int</code> value
     * @param n1 an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public void updateInt(int n, int n1) throws SQLException {
	results.updateInt(n, n1);
    }

    /**
     * Describe <code>updateInt</code> method here.
     *
     * @param string a <code>String</code> value
     * @param n an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public void updateInt(String string, int n) throws SQLException {
	results.updateInt(string, n);
    }

    /**
     * Describe <code>updateLong</code> method here.
     *
     * @param n an <code>int</code> value
     * @param l a <code>long</code> value
     * @exception SQLException if an error occurs
     */
    public void updateLong(int n, long l) throws SQLException {
	results.updateLong(n, l);
    }

    /**
     * Describe <code>updateLong</code> method here.
     *
     * @param string a <code>String</code> value
     * @param l a <code>long</code> value
     * @exception SQLException if an error occurs
     */
    public void updateLong(String string, long l) throws SQLException {
	results.updateLong(string, l);
    }

    /**
     * Describe <code>updateFloat</code> method here.
     *
     * @param n an <code>int</code> value
     * @param f a <code>float</code> value
     * @exception SQLException if an error occurs
     */
    public void updateFloat(int n, float f) throws SQLException {
	results.updateFloat(n, f);
    }

    /**
     * Describe <code>updateFloat</code> method here.
     *
     * @param string a <code>String</code> value
     * @param f a <code>float</code> value
     * @exception SQLException if an error occurs
     */
    public void updateFloat(String string, float f) throws SQLException {
	results.updateFloat(string, f);
    }

    /**
     * Describe <code>updateDouble</code> method here.
     *
     * @param string a <code>String</code> value
     * @param d a <code>double</code> value
     * @exception SQLException if an error occurs
     */
    public void updateDouble(String string, double d) throws SQLException {
	results.updateDouble(string, d);
    }

    /**
     * Describe <code>updateDouble</code> method here.
     *
     * @param n an <code>int</code> value
     * @param d a <code>double</code> value
     * @exception SQLException if an error occurs
     */
    public void updateDouble(int n, double d) throws SQLException {
	results.updateDouble(n, d);
    }

    /**
     * Describe <code>updateBigDecimal</code> method here.
     *
     * @param n an <code>int</code> value
     * @param bigDecimal a <code>BigDecimal</code> value
     * @exception SQLException if an error occurs
     */
    public void updateBigDecimal(int n, BigDecimal bigDecimal) throws SQLException {
	results.updateBigDecimal(n, bigDecimal);
    }

    /**
     * Describe <code>updateBigDecimal</code> method here.
     *
     * @param string a <code>String</code> value
     * @param bigDecimal a <code>BigDecimal</code> value
     * @exception SQLException if an error occurs
     */
    public void updateBigDecimal(String string, BigDecimal bigDecimal) throws SQLException {
	results.updateBigDecimal(string, bigDecimal);
    }

    /**
     * Describe <code>updateString</code> method here.
     *
     * @param string a <code>String</code> value
     * @param string1 a <code>String</code> value
     * @exception SQLException if an error occurs
     */
    public void updateString(String string, String string1) throws SQLException {
	results.updateString(string, string1);
    }

    /**
     * Describe <code>updateString</code> method here.
     *
     * @param n an <code>int</code> value
     * @param string a <code>String</code> value
     * @exception SQLException if an error occurs
     */
    public void updateString(int n, String string) throws SQLException {
	results.updateString(n, string);
    }

    /**
     * Describe <code>updateDate</code> method here.
     *
     * @param string a <code>String</code> value
     * @param date a <code>Date</code> value
     * @exception SQLException if an error occurs
     */
    public void updateDate(String string, Date date) throws SQLException {
	results.updateDate(string, date);
    }

    /**
     * Describe <code>updateDate</code> method here.
     *
     * @param n an <code>int</code> value
     * @param date a <code>Date</code> value
     * @exception SQLException if an error occurs
     */
    public void updateDate(int n, Date date) throws SQLException {
	results.updateDate(n, date);
    }

    /**
     * Describe <code>updateTimestamp</code> method here.
     *
     * @param n an <code>int</code> value
     * @param timestamp a <code>Timestamp</code> value
     * @exception SQLException if an error occurs
     */
    public void updateTimestamp(int n, Timestamp timestamp) throws SQLException {
	results.updateTimestamp(n, timestamp);
    }

    /**
     * Describe <code>updateTimestamp</code> method here.
     *
     * @param string a <code>String</code> value
     * @param timestamp a <code>Timestamp</code> value
     * @exception SQLException if an error occurs
     */
    public void updateTimestamp(String string, Timestamp timestamp) throws SQLException {
	results.updateTimestamp(string, timestamp);
    }

    /**
     * Describe <code>updateAsciiStream</code> method here.
     *
     * @param n an <code>int</code> value
     * @param inputStream an <code>InputStream</code> value
     * @param n1 an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public void updateAsciiStream(int n, InputStream inputStream, int n1) throws SQLException {
	results.updateAsciiStream(n, inputStream, n1);
    }

    /**
     * Describe <code>updateAsciiStream</code> method here.
     *
     * @param string a <code>String</code> value
     * @param inputStream an <code>InputStream</code> value
     * @param n an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public void updateAsciiStream(String string, InputStream inputStream, int n) throws SQLException {
	results.updateAsciiStream(string, inputStream, n);
    }

    /**
     * Describe <code>updateBinaryStream</code> method here.
     *
     * @param string a <code>String</code> value
     * @param inputStream an <code>InputStream</code> value
     * @param n an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public void updateBinaryStream(String string, InputStream inputStream, int n) throws SQLException {
	results.updateBinaryStream(string, inputStream, n);
    }

    /**
     * Describe <code>updateBinaryStream</code> method here.
     *
     * @param n an <code>int</code> value
     * @param inputStream an <code>InputStream</code> value
     * @param n1 an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public void updateBinaryStream(int n, InputStream inputStream, int n1) throws SQLException {
	results.updateBinaryStream(n, inputStream, n1);
    }

    /**
     * Describe <code>updateCharacterStream</code> method here.
     *
     * @param n an <code>int</code> value
     * @param reader a <code>Reader</code> value
     * @param n1 an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public void updateCharacterStream(int n, Reader reader, int n1) throws SQLException {
	results.updateCharacterStream(n, reader, n1);
    }

    /**
     * Describe <code>updateCharacterStream</code> method here.
     *
     * @param string a <code>String</code> value
     * @param reader a <code>Reader</code> value
     * @param n an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public void updateCharacterStream(String string, Reader reader, int n) throws SQLException {
	results.updateCharacterStream(string, reader, n);
    }

    /**
     * Describe <code>updateObject</code> method here.
     *
     * @param string a <code>String</code> value
     * @param object an <code>Object</code> value
     * @exception SQLException if an error occurs
     */
    public void updateObject(String string, Object object) throws SQLException {
	results.updateObject(string, object);
    }

    /**
     * Describe <code>updateObject</code> method here.
     *
     * @param string a <code>String</code> value
     * @param object an <code>Object</code> value
     * @param n an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public void updateObject(String string, Object object, int n) throws SQLException {
	results.updateObject(string, object, n);
    }

    /**
     * Describe <code>updateObject</code> method here.
     *
     * @param n an <code>int</code> value
     * @param object an <code>Object</code> value
     * @exception SQLException if an error occurs
     */
    public void updateObject(int n, Object object) throws SQLException {
	results.updateObject(n, object);
    }

    /**
     * Describe <code>updateObject</code> method here.
     *
     * @param n an <code>int</code> value
     * @param object an <code>Object</code> value
     * @param n1 an <code>int</code> value
     * @exception SQLException if an error occurs
     */
    public void updateObject(int n, Object object, int n1) throws SQLException {
	results.updateObject(n, object, n1);
    }

    /**
     * Describe <code>insertRow</code> method here.
     *
     * @exception SQLException if an error occurs
     */
    public void insertRow() throws SQLException {
	results.insertRow();
    }

    /**
     * Describe <code>updateRow</code> method here.
     *
     * @exception SQLException if an error occurs
     */
    public void updateRow() throws SQLException {
	results.updateRow();
    }

    /**
     * Describe <code>deleteRow</code> method here.
     *
     * @exception SQLException if an error occurs
     */
    public void deleteRow() throws SQLException {
	results.deleteRow();
    }

    /**
     * Describe <code>refreshRow</code> method here.
     *
     * @exception SQLException if an error occurs
     */
    public void refreshRow() throws SQLException {
	results.refreshRow();
    }

    /**
     * Describe <code>cancelRowUpdates</code> method here.
     *
     * @exception SQLException if an error occurs
     */
    public void cancelRowUpdates() throws SQLException {
	results.cancelRowUpdates();
    }

    /**
     * Describe <code>moveToInsertRow</code> method here.
     *
     * @exception SQLException if an error occurs
     */
    public void moveToInsertRow() throws SQLException {
	results.moveToInsertRow();
    }

    /**
     * Describe <code>moveToCurrentRow</code> method here.
     *
     * @exception SQLException if an error occurs
     */
    public void moveToCurrentRow() throws SQLException {
	results.moveToCurrentRow();
    }

    /**
     * Describe <code>getStatement</code> method here.
     *
     * @return a <code>Statement</code> value
     * @exception SQLException if an error occurs
     */
    public Statement getStatement() throws SQLException {
	return results.getStatement();
    }

    /**
     * Describe <code>getBlob</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>Blob</code> value
     * @exception SQLException if an error occurs
     */
    public Blob getBlob(int n) throws SQLException {
	return results.getBlob(n);
    }

    /**
     * Describe <code>getBlob</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>Blob</code> value
     * @exception SQLException if an error occurs
     */
    public Blob getBlob(String string) throws SQLException {
	return results.getBlob(string);
    }

    /**
     * Describe <code>getClob</code> method here.
     *
     * @param n an <code>int</code> value
     * @return a <code>Clob</code> value
     * @exception SQLException if an error occurs
     */
    public Clob getClob(int n) throws SQLException {
	return results.getClob(n);
    }

    /**
     * Describe <code>getClob</code> method here.
     *
     * @param string a <code>String</code> value
     * @return a <code>Clob</code> value
     * @exception SQLException if an error occurs
     */
    public Clob getClob(String string) throws SQLException {
	return results.getClob(string);
    }

    /**
     * Describe <code>updateRef</code> method here.
     *
     * @param n an <code>int</code> value
     * @param ref a <code>Ref</code> value
     * @exception SQLException if an error occurs
     */
    public void updateRef(int n, Ref ref) throws SQLException {
	results.updateRef(n, ref);
    }

    /**
     * Describe <code>updateRef</code> method here.
     *
     * @param string a <code>String</code> value
     * @param ref a <code>Ref</code> value
     * @exception SQLException if an error occurs
     */
    public void updateRef(String string, Ref ref) throws SQLException {
	results.updateRef(string, ref);
    }

    /**
     * Describe <code>updateBlob</code> method here.
     *
     * @param string a <code>String</code> value
     * @param blob a <code>Blob</code> value
     * @exception SQLException if an error occurs
     */
    public void updateBlob(String string, Blob blob) throws SQLException {
	results.updateBlob(string, blob);
    }

    /**
     * Describe <code>updateBlob</code> method here.
     *
     * @param n an <code>int</code> value
     * @param blob a <code>Blob</code> value
     * @exception SQLException if an error occurs
     */
    public void updateBlob(int n, Blob blob) throws SQLException {
	results.updateBlob(n, blob);
    }

    /**
     * Describe <code>updateClob</code> method here.
     *
     * @param n an <code>int</code> value
     * @param clob a <code>Clob</code> value
     * @exception SQLException if an error occurs
     */
    public void updateClob(int n, Clob clob) throws SQLException {
	results.updateClob(n, clob);
    }

    /**
     * Describe <code>updateClob</code> method here.
     *
     * @param string a <code>String</code> value
     * @param clob a <code>Clob</code> value
     * @exception SQLException if an error occurs
     */
    public void updateClob(String string, Clob clob) throws SQLException {
	results.updateClob(string, clob);
    }

    /**
     * Describe <code>updateArray</code> method here.
     *
     * @param n an <code>int</code> value
     * @param array an <code>Array</code> value
     * @exception SQLException if an error occurs
     */
    public void updateArray(int n, Array array) throws SQLException {
	results.updateArray(n, array);
    }

    /**
     * Describe <code>updateArray</code> method here.
     *
     * @param string a <code>String</code> value
     * @param array an <code>Array</code> value
     * @exception SQLException if an error occurs
     */
    public void updateArray(String string, Array array) throws SQLException {
	results.updateArray(string, array);
    }

    public SQLResultSet(Statement statement,ResultSet results){
	this.statement=statement;
	this.results=results;
    }

    public void finalize() throws SQLException{
	results.close();
	statement.close();
    }
}

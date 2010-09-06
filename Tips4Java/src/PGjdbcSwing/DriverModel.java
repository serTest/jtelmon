/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PGjdbcSwing;

import java.sql.*;
import javax.swing.event.EventListenerList;

public class DriverModel
{
    protected PreparedStatement ps = null;
    protected EventListenerList listenerList = new EventListenerList();
    protected Connection con = null;

    public DriverModel()
    {
        con = MvbPostgresConnection.getInstance().getConnection();
    }

    public boolean insertDriver(Integer did, String dname, String daddr,
                      String dcity, java.sql.Date dbirth, Integer dphone)
    {
        try
        {
            ps = con.prepareStatement("INSERT INTO driver VALUES (?,?,?,?,?,?)");

            ps.setInt(1, did.intValue());
            ps.setString(2, dname);
            ps.setString(3, daddr);
            ps.setString(4, dcity);
            ps.setDate(5, dbirth);

            if (dphone != null)
            {
                ps.setInt(6, dphone.intValue());
            }
            else
            {
                ps.setNull(6, Types.INTEGER);
            }

            ps.executeUpdate();
            con.commit();

            return true;
        }
        catch (SQLException ex)
        {
            ExceptionEvent event = new ExceptionEvent(this, ex.getMessage());
            fireExceptionGenerated(event);

            try
            {
                con.rollback();

                return false;
            }
            catch (SQLException ex2)
            {
                event = new ExceptionEvent(this, ex2.getMessage());
                fireExceptionGenerated(event);

                return false;
            }
        }
    }

    public ResultSet showDriver()
    {
        try
        {
            ps = con.prepareStatement("SELECT * FROM driver d",
                                      ResultSet.TYPE_SCROLL_INSENSITIVE,
                                      ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            return rs;
        }
        catch (SQLException ex)
        {

            ExceptionEvent event = new ExceptionEvent(this, ex.getMessage());
            fireExceptionGenerated(event);

            return null;
        }
    }

    public boolean findDriver(int did)
    {
        try
        {
            ps = con.prepareStatement("SELECT driver_sin FROM driver where driver_sin = ?");

            ps.setInt(1, did);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException ex)
        {
            ExceptionEvent event = new ExceptionEvent(this, ex.getMessage());
            fireExceptionGenerated(event);

            return false;
        }
    }

    public Connection getConnection()
    {
        return con;
    }

    protected void finalize() throws Throwable
    {
        if (ps != null)
        {
            ps.close();
        }

        super.finalize();
    }

    public void addExceptionListener(ExceptionListener l)
    {
        listenerList.add(ExceptionListener.class, l);
    }

    public void removeExceptionListener(ExceptionListener l)
    {
        listenerList.remove(ExceptionListener.class, l);
    }

    public void fireExceptionGenerated(ExceptionEvent ex)
    {
        Object[] listeners = listenerList.getListenerList();

        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==ExceptionListener.class)
            {
                ((ExceptionListener)listeners[i+1]).exceptionGenerated(ex);
            }
         }
     }
}
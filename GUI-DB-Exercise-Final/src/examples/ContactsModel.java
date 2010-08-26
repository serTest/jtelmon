package examples;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Model of Contacts application.
 *
 * @author Jan Stola
 */
public class ContactsModel implements ListSelectionListener {
    // Constants for database objects
    private static final String CONTACTS_TABLE = "contacts";
    private static final String CONTACTS_KEY = "id";
    private static final String CONTACTS_ID = "id";
    private static final String CONTACTS_FIRST_NAME = "first_name";
    private static final String CONTACTS_LAST_NAME = "last_name";
    private static final String CONTACTS_TITLE = "title";
    private static final String CONTACTS_NICKNAME = "nickname";
    private static final String CONTACTS_DISPLAY_FORMAT = "display_format";
    private static final String CONTACTS_MAIL_FORMAT = "mail_format";
    private static final String CONTACTS_EMAIL_ADDRESSES = "email_addresses";
    
    // Constants for property names
    public static final String PROP_REMOVAL_ENABLED = "removalEnabled";
    public static final String PROP_EDITING_ENABLED = "editingEnabled";

    // RowSet with contacts
    private JDBCRowSet rowSet;
    // Contacts selection model
    private ListSelectionModel contactSelection;
    // Insert mode (e.g. are we about to insert a new contact)
    private boolean insertMode;

    /**
     * Getter for <code>rowSet</code> property.
     *
     * @return rowSet with contacts.
     */
    public JDBCRowSet getRowSet() {
        return rowSet;
    }

    /**
     * Setter for <code>rowSet</code> property.
     *
     * @param rowSet rowSet with contacts.
     */
    public void setRowSet(JDBCRowSet rowSet) {
        this.rowSet = rowSet;
    }

    /**
     * Getter for <code>contactSelection</code> property.
     *
     * @return contacts selection model. 
     */
    public ListSelectionModel getContactSelection() {
        if (contactSelection == null) {
            contactSelection = new DefaultListSelectionModel();
            contactSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            setContactSelection(contactSelection);
        }
        return contactSelection;
    }

    /**
     * Setter for <code>contactSelection</code> property.
     *
     * @param contactSelection contacts selection model.
     */
    public void setContactSelection(ListSelectionModel contactSelection) {
        if (this.contactSelection != null) {
            this.contactSelection.removeListSelectionListener(this);
        }
        this.contactSelection = contactSelection;
        contactSelection.addListSelectionListener(this);
    }

    /**
     * Setter for <code>insertMode</code> property.
     *
     * @param insertMode insert mode.
     */
    void setInsertMode(boolean insertMode) {
        this.insertMode = insertMode;
    }

    /**
     * Returns ID of the selected contact.
     *
     * @return ID of the selected contact.
     */
    public String getID() {
        return insertMode ? null : stringValue(CONTACTS_ID);
    }

    /**
     * Returns the first name of the selected contact.
     *
     * @return the first name of the selected contact.
     */
    public String getFirstName() {
        return insertMode ? "" : stringValue(CONTACTS_FIRST_NAME);
    }
    
    /**
     * Returns the last name of the selected contact.
     *
     * @return the last name of the selected contact.
     */
    public String getLastName() {
        return insertMode ? "" : stringValue(CONTACTS_LAST_NAME);
    }

    /**
     * Returns title of the selected contact.
     *
     * @return title of the selected contact.
     */
    public String getTitle() {
        return insertMode ? "" : stringValue(CONTACTS_TITLE);
    }

    /**
     * Returns nickname of the selected contact.
     *
     * @return nickname of the selected contact.
     */
    public String getNickname() {
        return insertMode ? "" : stringValue(CONTACTS_NICKNAME);
    }

    /**
     * Returns display format of the selected contact.
     *
     * @return display format of the selected contact.
     */
    public int getDisplayFormat() {
        return insertMode ? 0 : intValue(CONTACTS_DISPLAY_FORMAT);
    }

    /**
     * Returns mail format of the selected contact.
     *
     * @return mail format of the selected contact.
     */
    public int getMailFormat() {
        return insertMode ? 0 : intValue(CONTACTS_MAIL_FORMAT);
    }

    /**
     * Returns email addresses of the selected contact.
     *
     * @return email addresses of the selected contact.
     */
    public Object[] getEmails() {
        String encodedEmails = insertMode ? null : stringValue(CONTACTS_EMAIL_ADDRESSES);
        return decodeEmails(encodedEmails);
    }

    /**
     * Determines whether editing of the selected contact is enabled.
     *
     * @return <code>true</code> if the selected contact can be edited,
     * returns <code>false</code> otherwise.
     */
    public boolean isEditingEnabled() {
        // Additional business logic can go here
        return (getContactSelection().getMinSelectionIndex() != -1);
    }

    /**
     * Determines whether removal of the selected contact is enabled.
     *
     * @return <code>true</code> if the selected contact can be removed,
     * returns <code>false</code> otherwise.
     */
    public boolean isRemovalEnabled() {
        // Additional business logic can go here
        return (getContactSelection().getMinSelectionIndex() != -1);
    }

    // Helper method that returns value of a selected contact's attribute
    private String stringValue(String columnName) {
        String value = null;
        try {
            if ((rowSet != null) && selectContactInRowSet()) {
                value = rowSet.getString(columnName);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return value;
    }

    // Helper method that returns value of a selected contact's attribute
    private int intValue(String columnName) {
        int value = 0;
        try {
            if ((rowSet != null) && selectContactInRowSet()) {
                value = rowSet.getInt(columnName);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return value;
    }

    // Helper method that synchronizes rowSet position with selected contact
    private boolean selectContactInRowSet() {
        int index = getContactSelection().getMinSelectionIndex();
        if (index != -1) {
            try {
                rowSet.absolute(index+1);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
        return (index != -1);
    }

    // Helper method that decodes email addresses from the encoded string
    private Object[] decodeEmails(String encodedEmails) {
        if ((encodedEmails == null) || (encodedEmails.equals(""))) {
            return new String[0];
        }
        char sep = encodedEmails.charAt(0);
        List emails = new LinkedList();
        StringTokenizer st = new StringTokenizer(encodedEmails, String.valueOf(sep));
        while (st.hasMoreTokens()) {
            emails.add(st.nextToken());
        }
        return emails.toArray(new Object[emails.size()]);
    }

    // Helper method that encodes email addresses into one string
    private String encodeEmails(Object[] emails) {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<emails.length; i++) sb.append('|').append(emails[i].toString());
        return sb.toString();
    }

    /**
     * Removes the selected contact.
     */
    public void removeContact() {
        int selection = getContactSelection().getMinSelectionIndex();
        Statement stmt = null;
        try {
            rowSet.absolute(selection+1);
            Connection con = rowSet.getConnection();
            stmt = con.createStatement();
            String sql = "delete from " + CONTACTS_TABLE + " where " + CONTACTS_KEY + " = " + rowSet.getObject(CONTACTS_KEY);
            stmt.executeUpdate(sql);
            rowSet.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlex) {
                    sqlex.printStackTrace();
                }
            }
        }
    }

    /**
     * Updates the selected contact or inserts a new one (if we are
     * in the insert mode).
     *
     * @param firstName first name of the contact.
     * @param lastName last name of the contact.
     * @param title title of the contact.
     * @param nickname nickname of the contact.
     * @param displayFormat display format for the contact.
     * @param mailFormat mail format for the contact.
     * @param emails email addresses of the contact.
     */
    public void updateContact(String firstName, String lastName, String title, String nickname,
            int displayFormat, int mailFormat, Object[] emails) {
        int selection = getContactSelection().getMinSelectionIndex();
        Statement stmt = null;
        try {
            if (!insertMode) {
                rowSet.absolute(selection+1);
            }
            Connection con = rowSet.getConnection();
            stmt = con.createStatement();
            String sql;
            if (insertMode) {
                sql = "insert into " + CONTACTS_TABLE + "(" + CONTACTS_KEY + ", " + CONTACTS_FIRST_NAME + ", "
                    + CONTACTS_LAST_NAME + ", " + CONTACTS_TITLE + ", " + CONTACTS_NICKNAME + ", "
                    + CONTACTS_DISPLAY_FORMAT + ", " + CONTACTS_MAIL_FORMAT + ", " + CONTACTS_EMAIL_ADDRESSES 
                    + ") values ((case when (select max(" + CONTACTS_KEY + ") from " + CONTACTS_TABLE + ")"
                    + "IS NULL then 1 else (select max(" + CONTACTS_KEY + ") from " + CONTACTS_TABLE + ")+1 end), "
                    + encodeSQL(firstName) + ", " + encodeSQL(lastName) + ", " + encodeSQL(title) + ", "
                    + encodeSQL(nickname) + ", " + displayFormat + ", " + mailFormat + ", "
                    + encodeSQL(encodeEmails(emails)) + ")";
            } else {
                sql = "update " + CONTACTS_TABLE + " set ";
                sql += CONTACTS_FIRST_NAME + '=' + encodeSQL(firstName) + ", ";
                sql += CONTACTS_LAST_NAME + '=' + encodeSQL(lastName) + ", ";
                sql += CONTACTS_TITLE + '=' + encodeSQL(title) + ", ";
                sql += CONTACTS_NICKNAME + '=' + encodeSQL(nickname) + ", ";
                sql += CONTACTS_DISPLAY_FORMAT + '=' + displayFormat + ", ";
                sql += CONTACTS_MAIL_FORMAT + '=' + mailFormat + ", ";
                sql += CONTACTS_EMAIL_ADDRESSES + '=' + encodeSQL(encodeEmails(emails));
                sql += " where " + CONTACTS_KEY + '=' + rowSet.getObject(CONTACTS_KEY);
            }
            System.out.println(sql);
            stmt.executeUpdate(sql);
            rowSet.execute();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        } finally {
            setInsertMode(false);
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlex) {
                    sqlex.printStackTrace();
                }
            }
        }
    }

    // Helper method that escapes special SQL characters and encloses the text into quotes
    private String encodeSQL(String text) {
        text = text.replaceAll("'", "''");
        return "'" + text + "'";
    }

    /**
     * Release resources used by the model. It releases contacts rowSet.
     */
    public void dispose() {
        try {
            rowSet.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    // Property change support
    private PropertyChangeSupport propChangeSupport = new PropertyChangeSupport(this);
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propChangeSupport.removePropertyChangeListener(listener);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;
        propChangeSupport.firePropertyChange(null, null, null);
    }

}

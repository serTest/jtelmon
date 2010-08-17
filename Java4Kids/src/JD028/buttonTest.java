/*
 * http://www.tek-tips.com/viewthread.cfm?qid=1159255&page=1
 * 
 */

package JD028;

import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class buttonTest extends JFrame {

    private JPanel jContentPane = null;
    private JButton jButton = null;
    String sortVar = "user_first_name";

    public buttonTest() {
        Vector columnNames = new Vector();
        Vector data = new Vector();
        initialize();
        try {
            // Connect to the Database
            String driver = "org.postgresql.Driver";
            String url = "jdbc:postgresql://192.168.61.205/license";
            String userid = "postgres";
            String password = "telinit";
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, userid,
                    password);
            String sql = "Select * from users order by "+ sortVar;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            // Get column names
            for (int i = 1; i <= columns; i++) {
                columnNames.addElement(md.getColumnName(i));
            }
            // Get row data
            while (rs.next()) {
                Vector row = new Vector(columns);
                for (int i = 1; i <= columns; i++) {
                    row.addElement(rs.getObject(i));
                }
                data.addElement(row);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        JTable table = new JTable(data, columnNames) {
            public Class getColumnClass(int column) {
                for (int row = 0; row < getRowCount(); row++) {
                    Object o = getValueAt(row, column);
                    if (o != null) {
                        return o.getClass();
                    }
                }
                return Object.class;
            }
        };
        // JScrollPane scrollPane = new JScrollPane( table );
        getContentPane().add(new JScrollPane(table), BorderLayout.NORTH);
    }

    private void initialize() {
        this.setSize(300, 300);
        this.setContentPane(getJContentPane());
        this.setTitle("JFrame");
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJButton(), java.awt.BorderLayout.SOUTH);
        }
        return jContentPane;
    }

    private JButton getJButton() {
        if (jButton == null) {
            jButton = new JButton();
            jButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    //JOptionPane.showMessageDialog(null, "hello", "Message", JOptionPane.ERROR_MESSAGE);
                String sortVar = "user_id";
                buttonTest frame1 = new buttonTest();
                frame1.setDefaultCloseOperation(HIDE_ON_CLOSE);
                frame1.pack();
                frame1.setVisible(true);
                }
            });
        }
        return jButton;
    }

    public static void main(String[] args) {
        buttonTest frame = new buttonTest();
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

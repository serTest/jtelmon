/*
 * http://www.users.csbsju.edu/~lziegler/CS317/NetProgramming/JTable.html
 * 
 */

package JD031.MyTableModel;

import JD028.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
/**
 *
 * @author  lziegler
 */
public class TableForm extends javax.swing.JFrame {

    public TableForm() {
        initComponents();
        DataBaseTable.setVisible(false);
        try {
            Class.forName("org.postgresql.Driver");
            // "jdbc:postgresql://192.168.61.205/license?user=postgres&password=telinit"
            conn=DriverManager.getConnection(
                        "jdbc:postgresql://192.168.61.205/license","postgres","telinit");
            stmt=conn.createStatement();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        setSize(500,420);
    }


    private void initComponents() {//GEN-BEGIN:initComponents
        jScrollPane1 = new javax.swing.JScrollPane();
        DataBaseTable = new javax.swing.JTable();
        DoQuery = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ActionTextArea = new javax.swing.JTextArea();

        getContentPane().setLayout(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        DataBaseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] { ""}
            ) {
              Class[] types = new Class [] { java.lang.Object.class};
              boolean[]canEdit=new boolean[]{false};

              public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
              }

              public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
              }
        });

        DataBaseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                DataBaseTableMouseReleased(evt);
            }
        });

        jScrollPane1.setViewportView(DataBaseTable);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(3, 120, 460, 150);

        DoQuery.setText("Do Query");
        DoQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QueryTable(evt);
            }
        });

        getContentPane().add(DoQuery);
        DoQuery.setBounds(20, 20, 93, 27);

        jScrollPane2.setViewportView(ActionTextArea);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(3, 273, 460, 110);

        pack();
    }//GEN-END:initComponents

    private void DataBaseTableMouseReleased(java.awt.event.MouseEvent evt) {
        ActionTextArea.append(DataBaseTable.getSelectedRow()+
                "  "+DataBaseTable.getSelectedColumn()+'\n');
    }

    private void QueryTable(java.awt.event.ActionEvent evt) {
        try {
            ResultSet res=stmt.executeQuery("SELECT * FROM users");
            ResultSetMetaData RSMD=res.getMetaData();
            NumberOfColumns=RSMD.getColumnCount();
            AttributeNames=new String[NumberOfColumns];
            for(int i=0;i<NumberOfColumns;i++)
                AttributeNames[i]=RSMD.getColumnName(i+1);
            MyArray=new Object[10000][NumberOfColumns];
            int R=0;
            while(res.next()) {
                for(int C=1; C<=NumberOfColumns;C++)
                    MyArray[R][C-1]=res.getObject(C);
                R++;
            }
            res.close();
            NumberOfRows=R;
            Object[][] TempArray=MyArray;
            MyArray=new Object[NumberOfRows][NumberOfColumns];
            for(R=0;R<NumberOfRows;R++)
                for(int C=0;C<NumberOfColumns;C++)
                    MyArray[R][C]=TempArray[R][C];
            DataBaseTable.setModel(new MyTableModel());
            DataBaseTable.setVisible(true);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }

    public static void main(String args[]) {
        new TableForm().setVisible(true);
    }

    Object[][] MyArray=null;
    String [] AttributeNames;
    Connection conn;
    Statement stmt;
    String state;
    int NumberOfRows,NumberOfColumns;

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable DataBaseTable;
    private javax.swing.JButton DoQuery;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea ActionTextArea;


    class MyTableModel extends AbstractTableModel {
       //You can choose any long value for serialVersionUID.
       //I chose 317 because this is CSCI 317.
        private static final long serialVersionUID=317L;
        public int getColumnCount() {
            return (NumberOfColumns);
        }
        public int getRowCount() {
            return(NumberOfRows);
        }
        public String getColumnName(int i) {
            return (AttributeNames[i]);
        }
        public java.lang.Object getValueAt(int row, int column) {
            return(MyArray[row][column]);
        }
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 3) {
                return false;
            } else {
                return true;
            }
        }
        public void setValueAt(Object value, int row, int col) {
            MyArray[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }
}

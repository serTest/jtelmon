package tablesamples;

// Java SE 6
// jdj.sys-con.com/read/36362.htm
// Meet the Swing JTable
// Listing 1: Code for Simple JTable.
import com.sun.java.swing.*;
// import com.sun.java.swing.table.*;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SimpleTableTest extends JFrame {

   public SimpleTableTest() {
      setLocation(100,100);
      setSize(250,100);
      String[][] data = {  {"eggs", "sandwich", "steak", "snickers"},
                           {"bacon", "pickles", "potato", "apple"},
                           {"syrup", "mayo", "corn", "banana"},
                           {"pancakes", "chips", "broccoli", "crunch bar"},
                           {"sausage", "pizza", "pie", "protein shake"}};
      String[] headers = {"Breakfast", "Lunch", "Dinner", "Snack"};
      DefaultTableModel model = new DefaultTableModel(data, headers);
      JTable table = new JTable(model);
      this.getContentPane().add(table);
      setVisible(true);
   }

   public static void main(String[] args) {
      SimpleTableTest simpleTableTest = new SimpleTableTest();
   }
} 

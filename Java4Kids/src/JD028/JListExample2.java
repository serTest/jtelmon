/*
 * http://academicjava.com/Java_help/Tutorial_Examples_Swing_18.html
 * JList addListSelectionListener
 *
 * http://tips4java.wordpress.com/2008/11/21/row-table-model/
 * http://www.camick.com/java/source/RowTableModel.java
 *
 * http://tips4java.wordpress.com/2008/11/24/list-table-model/
 * http://www.dreamincode.net/forums/topic/62384-display-arraylist-string-object-in-jtable/
 * 
 */


package JD028;

// JList Example
import javax.swing.*;
import javax.swing.event.*;

class JListExample2 {

   public static void main(String[] args) {

      JList jlist = new JList(new String[] {
         "Latte","Mocha","Java","Americano",
         "Espresso","De-caf","Arabica"
      });

      jlist.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent e) {
            JList jListSource = (JList)e.getSource();
            Object[] selection = jListSource.getSelectedValues();
            if (!e.getValueIsAdjusting()) {
               System.out.println("----");
               for (int i = 0; i < selection.length; i++)
                  System.out.println("selection = "+selection[i]);
               }
            }
         });

      JFrame frame = new JFrame();
      frame.getContentPane().add(jlist);
      frame.pack();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}
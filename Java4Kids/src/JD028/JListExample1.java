/*
 * http://academicjava.com/Java_help/Tutorial_Examples_Swing_18.html
 * JList addListSelectionListener
 */

package JD028;

// JList Example
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

class JListExample1 {

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
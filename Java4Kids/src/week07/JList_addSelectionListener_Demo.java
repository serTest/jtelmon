/*
 * http://www.java2s.com/Code/JavaAPI/javax.swing/JListaddListSelectionListenerListSelectionListenerlistener.htm
 * JList addListSelectionListener
 */

package week07;

/*
 java.util.ArrayList:
 java.util.ArrayList: Collection
 java.util.ArrayList: int

 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JList_addSelectionListener_Demo extends JFrame {
  JList list = null;

  JList_addSelectionListener_Demo() {
    Container cp = getContentPane();
    cp.setLayout(new FlowLayout());
    ArrayList data = new ArrayList();
    data.add("Hi");
    data.add("Hello");
    data.add("Goodbye");
     list = new JList(data.toArray());
    list.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent evt) {
        if (evt.getValueIsAdjusting())
          return;
        System.out.println("Selected from " + evt.getFirstIndex() + " to " + evt.getLastIndex());
      }
    });
    cp.add(new JScrollPane(list), BorderLayout.CENTER);
  }

  public static void main(String[] s) {
    JList_addSelectionListener_Demo l = new JList_addSelectionListener_Demo();
    l.pack();
    l.setVisible(true);
  }
}
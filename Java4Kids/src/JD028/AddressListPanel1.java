/*
 * academicjava.com/JavaTutorial/JPanelExample.html
 * code.google.com/p/jtelmon/source/browse/Java4Kids/src/JD028/AddressListPanel1.java
 * code.google.com/p/jtelmon/source/browse/Java4Kids/src/JD028/ListEntryRenderer.java
 * code.google.com/p/jtelmon/source/browse/Java4Kids/src/JD028/MiniDao01.java
 * code.google.com/p/jtelmon/source/browse/Java4Kids/src/JD028/ListEntry.java
 * 
 */

package JD028;

import java.awt.GridLayout;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AddressListPanel1 {
    private static MiniDao01 dao;
    private static javax.swing.JList addressList;
    private static javax.swing.JScrollPane scrollPane;
    private static ListEntryRenderer renderer;
    private static DefaultListModel theModel;
    private static JPanel thePanel;

    public static void main(String[] args) {
        JFrame theFrame = new JFrame("JTable With ArrayList");
        thePanel = new JPanel(new GridLayout(0,1));
        theFrame.getContentPane().add(thePanel);
        renderer = new ListEntryRenderer();
        theModel = new DefaultListModel();
        scrollPane = new javax.swing.JScrollPane();
        addressList = new javax.swing.JList();
        addressList.setModel(theModel);
        addressList.setCellRenderer(renderer);
        scrollPane.setViewportView(addressList);
        thePanel.add(scrollPane);
        dao = new MiniDao01();
        dao.connect();
        List<ListEntry> entries = dao.getListEntries();
        Iterator i = entries.iterator();
        while(i.hasNext())
        {
          ListEntry value= (ListEntry) i.next();
          System.out.println("Value :"+value.getFirstName()+value.getLastName());
        }
        for(ListEntry entry: entries) {
            theModel.addElement(entry);
        }
        theFrame.pack();
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.setVisible(true);
        dao.disconnect();
    }
}

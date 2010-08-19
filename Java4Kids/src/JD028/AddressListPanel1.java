/*
 * code.google.com/p/jtelmon/source/browse/Java4Kids/src/JD028/AddressListPanel1.java
 * academicjava.com/JavaTutorial/JPanelExample.html
 */

package JD028;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class AddressListPanel1 {
    private static MiniDao01 db;
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
        db = new MiniDao01();
        db.connect();
        List<ListEntry> entries = db.getListEntries();
        for(ListEntry entry: entries) {
            theModel.addElement(entry);
        }
        theFrame.pack();
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.setVisible(true);
        db.disconnect();
    }
}

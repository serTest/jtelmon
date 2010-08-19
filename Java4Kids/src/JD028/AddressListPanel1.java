/*
 * code.google.com/p/jtelmon/source/browse/Java4Kids/src/JD028/AddressListPanel1.java
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
    private static DefaultListModel model;
    private static JPanel jpanel1;

    public static void main(String[] args) {

        JFrame frame = new JFrame("JTable With ArrayList");
        jpanel1 = new JPanel(new GridLayout(0,1));
        frame.getContentPane().add(jpanel1);
        renderer = new ListEntryRenderer();
        model = new DefaultListModel();
        scrollPane = new javax.swing.JScrollPane();
        addressList = new javax.swing.JList();
        addressList.setModel(model);
        addressList.setCellRenderer(renderer);
        scrollPane.setViewportView(addressList);
        jpanel1.add(scrollPane);

        db = new MiniDao01();
        db.connect();
        List<ListEntry> entries = db.getListEntries();
        addListEntries(entries);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        db.disconnect();
    }


    public static void addListEntry(ListEntry entry) {
        model.addElement(entry);
    }

    public static void addListEntries(List<ListEntry> list) {
        for(ListEntry entry: list) {
            addListEntry(entry);
        }
    }

}

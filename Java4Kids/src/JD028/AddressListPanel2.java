 /*
 * 
 * 
 */

package JD028;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionListener;


public class AddressListPanel2 {
    private static MiniDao01 db;
    private static javax.swing.JList addressList;
    private static javax.swing.JScrollPane scrollPane;
    private static ListEntryRenderer renderer;
    private static DefaultListModel model;
    private static JPanel jpanel1;

    public static void main(String[] args) {

        JFrame frame = new JFrame("TableWithList");
        jpanel1 = new JPanel(new GridLayout(0,1));
        frame.getContentPane().add(jpanel1);
        renderer = new ListEntryRenderer();
        model = new DefaultListModel();
        scrollPane = new javax.swing.JScrollPane();
        addressList = new javax.swing.JList();
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        addressList.setModel(model);
        addressList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
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

    }


    public static void addListEntry(ListEntry entry) {
        model.addElement(entry);
    }

    public static void addListEntries(List<ListEntry> list) {
        for(ListEntry entry: list) {
            addListEntry(entry);
        }
    }

    public int getSelectedIndex() {
        return addressList.getSelectedIndex();
    }

    public int setSelectedIndex(int index) {
        assert(index >= -1);
        DefaultListModel model = (DefaultListModel)addressList.getModel();
        int size = model.getSize();
        if (index < size) {
            addressList.setSelectedIndex(index);
        } else {
            addressList.setSelectedIndex(size-1);
            index = size -1;
        }
        return index;
    }

    public ListEntry getSelectedListEntry() {
        ListEntry entry = (ListEntry)addressList.getSelectedValue();
        return entry;
    }

    public int deleteSelectedEntry() {
        int selectedIndex = addressList.getSelectedIndex();
        if (selectedIndex >= 0) {
            DefaultListModel model = (DefaultListModel)addressList.getModel();
            model.remove(selectedIndex);
        }
        return selectedIndex;
    }
    public void addListSelectionListener(ListSelectionListener listener) {
        addressList.addListSelectionListener(listener);
    }

    public void removeListSelectionListener(ListSelectionListener listener) {
        addressList.removeListSelectionListener(listener);
    }

}

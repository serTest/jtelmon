/*
 * code.google.com/p/jtelmon/source/browse/Java4Kids/src/JD028/ListEntryRenderer.java
 *
 */

package JD028;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.DefaultListCellRenderer;


public class ListEntryRenderer extends DefaultListCellRenderer {
    
    /**
     * Creates a new instance of ListEntryRenderer
     */
    public ListEntryRenderer() {
    }

    public Component getListCellRendererComponent(JList list, Object value, 
            int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        ListEntry entry = (ListEntry) value;
        this.setText(entry.getName());
        return this;
    }
    
}

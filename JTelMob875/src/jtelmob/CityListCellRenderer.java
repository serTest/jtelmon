/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jtelmob;


import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
public class CityListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Ncities) {
            Ncities c = (Ncities) value;
            setText(c.getCityName());
        }
        return this;
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jtelmob;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
public class FunctionListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Nfunctions) {
            Nfunctions c = (Nfunctions) value;
            setText(c.getFunctionName());
        }
        return this;
    }
}
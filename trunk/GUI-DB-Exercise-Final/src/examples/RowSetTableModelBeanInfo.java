package examples;

import java.beans.*;

/**
 * BeanInfo for RowSetTableModel.
 *
 * @author Jan Stola
 */
public class RowSetTableModelBeanInfo extends SimpleBeanInfo {
    private PropertyDescriptor[] pds;
    
    private static PropertyDescriptor[] createPropertyDescriptors() throws IntrospectionException {
        int index = 0;
        PropertyDescriptor[] properties = new PropertyDescriptor[2];
        properties[index] = new PropertyDescriptor("rowSet", RowSetTableModel.class, "getRowSet", "setRowSet");
        properties[index++].setPreferred(true);
        properties[index] = new PropertyDescriptor("visibleColumns", RowSetTableModel.class, "getVisibleColumns", "setVisibleColumns");
        properties[index++].setPreferred(true);
        return properties;
    }

    @Override
    public synchronized PropertyDescriptor[] getPropertyDescriptors() {
        if (pds == null) {
            try {
                pds = createPropertyDescriptors();
            } catch (IntrospectionException e) {
                pds = super.getPropertyDescriptors();
            }
        }
        return pds;
    }
    
}
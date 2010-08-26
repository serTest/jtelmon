package examples;

import java.beans.*;

/**
 * BeanInfo for JDBCRowSet.
 *
 * @author Jan Stola
 */
public class JDBCRowSetBeanInfo extends SimpleBeanInfo {
    private PropertyDescriptor[] pds;

    private static PropertyDescriptor[] createPropertyDescriptors() throws IntrospectionException {
        PropertyDescriptor[] properties = new PropertyDescriptor[15];
        int index = 0;
        properties[index] = new PropertyDescriptor("command", JDBCRowSet.class, "getCommand", "setCommand");
        properties[index++].setPreferred(true);
        properties[index++] = new PropertyDescriptor("dataSourceName", JDBCRowSet.class, "getDataSourceName", "setDataSourceName");
        properties[index] = new PropertyDescriptor("driver", JDBCRowSet.class, "getDriver", "setDriver");
        properties[index++].setPreferred(true);
        properties[index] = new PropertyDescriptor("escapeProcessing", JDBCRowSet.class, "getEscapeProcessing", "setEscapeProcessing");
        properties[index++].setExpert(true);
        properties[index] = new PropertyDescriptor("fetchSize", JDBCRowSet.class, "getFetchSize", "setFetchSize");
        properties[index++].setExpert(true);
        properties[index] = new PropertyDescriptor("maxFieldSize", JDBCRowSet.class, "getMaxFieldSize", "setMaxFieldSize");
        properties[index++].setExpert(true);
        properties[index] = new PropertyDescriptor("maxRows", JDBCRowSet.class, "getMaxRows", "setMaxRows");
        properties[index++].setExpert(true);
        properties[index] = new PropertyDescriptor("password", JDBCRowSet.class, "getPassword", "setPassword");
        properties[index++].setPreferred(true);
        properties[index] = new PropertyDescriptor("queryTimeout", JDBCRowSet.class, "getQueryTimeout", "setQueryTimeout");
        properties[index++].setExpert(true);
        properties[index++] = new PropertyDescriptor("readOnly", JDBCRowSet.class, "isReadOnly", "setReadOnly");
        properties[index] = new PropertyDescriptor("showDeleted", JDBCRowSet.class, "getShowDeleted", "setShowDeleted");
        properties[index++].setExpert(true);
        properties[index++] = new PropertyDescriptor("transactionIsolation", JDBCRowSet.class, "getTransactionIsolation", "setTransactionIsolation");
        properties[index++] = new PropertyDescriptor("type", JDBCRowSet.class, "getType", "setType");
        properties[index] = new PropertyDescriptor("url", JDBCRowSet.class, "getUrl", "setUrl");
        properties[index++].setPreferred(true);
        properties[index] = new PropertyDescriptor("username", JDBCRowSet.class, "getUsername", "setUsername");
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
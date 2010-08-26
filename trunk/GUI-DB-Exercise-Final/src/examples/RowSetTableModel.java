package examples;

import java.sql.*;
import java.util.*;
import javax.sql.*;
import javax.swing.table.AbstractTableModel;

/**
 * TableModel adapter for a RowSet.
 *
 * @author Jan Stola
 */

@SuppressWarnings("CallToThreadDumpStack")

public class RowSetTableModel extends AbstractTableModel implements RowSetListener {
    // RowSet with the data
    private RowSet rowSet;
    // Number of rows
    private int rowCount;
    // Columns visible in the table model
    private String[] visibleColumns;

    /**
     * Returns number of rows in the table.
     *
     * @return number of rows in the table.
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Returns number of columns in the table.
     *
     * @return number of columns in the table.
     */
    public int getColumnCount() {
        int columnCount = 0;
        try {
            if (visibleColumns != null) {
                columnCount = visibleColumns.length;
            } else if (rowSet != null) {
                columnCount = rowSet.getMetaData().getColumnCount();
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return columnCount;
    }

    /**
     * Returns label of the specified column (can be set for example
     * via <code>select column as columnLabel ...</code>).
     *
     * @return name of the specified column.
     */
    @Override
    public String getColumnName(int column) {
        String name = super.getColumnName(column);
        if (visibleColumns != null) {
            name = visibleColumns[column].toUpperCase();
            column = findColumnIndex(visibleColumns[column]);
        }
        if (column != -1) {
            try {
                name = rowSet.getMetaData().getColumnLabel(column+1);
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
        return name;
    }

    /**
     * Returns value at the specified row and column.
     *
     * @return value at the specified row and column.
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        try {
            rowSet.absolute(rowIndex+1);
            if (visibleColumns != null) {
                columnIndex = findColumnIndex(visibleColumns[columnIndex]);
            }
            if (columnIndex != -1) {
                value = rowSet.getObject(columnIndex+1);
            }
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        return value;
    }

    // Helper method used when <code>visibleColumns</code> property is set.
    // Transforms columnName into index of the column in the model.
    private Map columnToIndexMap = new HashMap();
    private int findColumnIndex(String columnName) {
        columnName = columnName.toUpperCase();
        Integer index = (Integer)columnToIndexMap.get(columnName);
        if (index != null) return index.intValue();
        int columnIndex = -1;
        if (rowSet != null) {
            try {
                ResultSetMetaData metaData = rowSet.getMetaData();
                int columns = metaData.getColumnCount();
                for (int i=1; i<=columns; i++) {
                    if (metaData.getColumnName(i).toUpperCase().equals(columnName)) {
                        columnIndex = i-1;
                        break;
                    }
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
        return columnIndex;
    }

    /**
     * Getter for the <code>rowSet</code> property.
     *
     * @return row set used by this model.
     */
    public RowSet getRowSet() {
        return rowSet;
    }

    /**
     * Setter for the <code>rowSet</code> property.
     *
     * @param rowSet row set with the data.
     */ 
    public void setRowSet(RowSet rowSet) {
        if (this.rowSet != null) {
            this.rowSet.removeRowSetListener(this);
        }
        this.rowSet = rowSet;
        columnToIndexMap.clear();
        rowSet.addRowSetListener(this);
        updateRowCount();
        fireTableStructureChanged();
    }

    /**
     * Getter for the <code>visibleColumns</code> property.
     *
     * @return columns visible in the table model or <code>null</code>
     * if all columns of the row set should be visible.
     */
    public String[] getVisibleColumns() {
        return visibleColumns;
    }

    /**
     * Setter for the <code>visibleColumns</code> property.
     *
     * @param columns visible in the table model or <code>null</code>
     * if all columns of the row set should be visible.
     */
    public void setVisibleColumns(String[] visibleColumns) {
        this.visibleColumns = visibleColumns;
        columnToIndexMap.clear();
        fireTableStructureChanged();
    }

    // Helper method that updates <code>rowCount</code> field.
    private void updateRowCount() {
        int rowCount1 = 0;
        try {
            rowSet.beforeFirst();
            while (rowSet.next()) rowCount1++;
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
        this.rowCount = rowCount1;
    }

    // Implementation of RowSetListener
    public void rowSetChanged(RowSetEvent event) {
        updateRowCount();
        fireTableStructureChanged();
    }

    public void rowChanged(RowSetEvent event) {
        updateRowCount();
        fireTableDataChanged();
    }

    public void cursorMoved(RowSetEvent event) {}

}

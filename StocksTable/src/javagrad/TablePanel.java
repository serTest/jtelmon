import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.JList;
import java.awt.Component;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.event.ChangeEvent;
import java.awt.EventQueue;
import javax.swing.JFrame;
public class TablePanel extends JPanel implements ListSelectionListener,  ActionListener, RowClient{
    private SQLTable metaTable;
    private FocusJTable table;
    private JButton addRowButton;
    private String keyField;
    private Object value;
    private KeyClient client;
    private JScrollPane scrollPane;
    private JList rowHeader;
    private int rowHeaderWidth=60;

    public final static int MAX_DISPLAY_ROWS=20;

    public TablePanel(SQLDatabase database, 
		      String tableName,
		      String keyField,
		      Object value,
		      String [] fields){
	this(database,tableName,keyField,value,null,fields);
    }

    public TablePanel(final SQLDatabase database, 
		      final String tableName,
		      String keyField,
		      Object value,
		      final String orderField,
		      final String [] fields){

	String where=null;
	this.keyField=keyField;
	this.value=value;
	table=new FocusJTable();

	metaTable=database.findTable(tableName);

	if (keyField != null && value!=null){
	    where=keyField +"="+'\"'+value+'\"';
	}

	final String finalWhere=where;
	setLayout(new BorderLayout());

	
	setBorder(new TitledBorder(new EtchedBorder(),tableName));
	scrollPane=new JScrollPane(table);


	addRowButton=new JButton("new");
	rowHeaderWidth=addRowButton.getPreferredSize().width;

	addRowButton.addActionListener(this);
	scrollPane.setCorner("UPPER_LEFT_CORNER",addRowButton);

	add(scrollPane,BorderLayout.CENTER);

	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setColumnSelectionAllowed(false);
	table.setRowSelectionAllowed(false);
	SelectionListener listener = new SelectionListener(table);
	SelectionListener deleteListener;

	listener.addRowClient(this);
	table.getSelectionModel().addListSelectionListener(listener);
	table.getColumnModel().getSelectionModel().addListSelectionListener(listener);
	table.setSurrendersFocusOnKeystroke(true);
	//	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


	Dimension prefDim=table.getPreferredSize();
	prefDim.height=table.getRowHeight()*3;
	table.setPreferredScrollableViewportSize(prefDim);

	SwingWorker worker=new SwingWorker(){
		public Object construct(){
		    database.select(fields, 
				    tableName,finalWhere, orderField, table);
		    
		    
		    return null;
		}
		public void finished(){
		    setRowHeader();
		    if (table.getRowCount()<MAX_DISPLAY_ROWS){
			Dimension prefDim=table.getPreferredSize();
			table.setPreferredScrollableViewportSize(prefDim);
		    } else {
			Dimension prefDim=table.getPreferredSize();
			prefDim.height=MAX_DISPLAY_ROWS*table.getRowHeight();
			table.setPreferredScrollableViewportSize(prefDim);
		    }
			
		    revalidate();
		    // this will die in an applet
		    //		        ((JFrame)getTopLevelAncestor()).pack();

		}
	    };
	worker.start();
    }

    public void setSelectable(boolean flag){
	table.setRowSelectionAllowed(false);
    }
    private void modifyAction(){
	KeyStroke enter=KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true);
	
	table.getInputMap().put(enter,"CustomAction");
	table.getActionMap().put("CustomAction", new AbstractAction() {

		public void actionPerformed(ActionEvent ae) {
		    Debug.println("got enter");
		}
	    });
    }

    public void setRowHeader(){

	Color background = table.getTableHeader().getBackground();
	scrollPane.setBackground(background);

	
	rowHeader=new JList(new RowHeaderModel(table.getRowCount()));
	rowHeader.setBackground(background);

	int height=table.getRowHeight();
	rowHeader.setFixedCellWidth(rowHeaderWidth);
	rowHeader.setFixedCellHeight(height);

	
	rowHeader.addListSelectionListener(this);

	RowHeaderRenderer render = new RowHeaderRenderer(table) {
		
	    };

	rowHeader.setCellRenderer(render);
	scrollPane.setRowHeaderView(rowHeader);

    }
    public FocusJTable getTable(){
	return table;
    }

    public void actionPerformed(ActionEvent e){
	if (e.getSource()==addRowButton){ 
	    ResultSetTableModel model=(ResultSetTableModel)table.getModel();
	    if (model.addInProgress()){
		UI.popupMessage("Sorry!","Only one row add at a time");
	    } else {
		model.addRow(keyField,value);
		((RowHeaderModel)rowHeader.getModel()).addElement();
		final int last=model.getRowCount()-1;
		table.scrollRectToVisible(table.getCellRect(last,0,true));
		table.changeSelection(last,last,false,false);
	    }
	}    
    }

    public void rowSelected(int row){
	if (client!=null){
	    ResultSetTableModel model=(ResultSetTableModel)table.getModel();
	    Object key=model.getValueAt(row,keyField);

	    client.keySelected(key);
	}
    }
	
    public void addKeyClient(KeyClient clientObj){
	client=clientObj;
    }



    // Implementation of javax.swing.event.ListSelectionListener

    /**
     * handle events from the row header
     *
     * @param listSelectionEvent a <code>ListSelectionEvent</code> value
     */
    public void valueChanged(ListSelectionEvent e) {

	
	if (e.getSource()==rowHeader){
	    if (!e.getValueIsAdjusting())
		return;

	    Integer val=(Integer)rowHeader.getSelectedValue();
	    ResultSetTableModel model=(ResultSetTableModel)table.getModel();

	    if (model.addInProgress() && val!=null &&
		val.intValue()==model.getRowCount()-1){	    
		model.rowAddCommit();
		rowHeader.clearSelection();
		table.clearSelection();
		return;
	    }
	
	    if (val!=null) {
		int rowIndex=(val.intValue());
		if (JOptionPane.showOptionDialog(this,
				     new RowSummaryPanel(table,rowIndex),
						 "Delete Row?",
						 JOptionPane.OK_CANCEL_OPTION, 
						 JOptionPane.INFORMATION_MESSAGE,
						 null, 
						 new String[] {"Delete", "Cancel"}, "Cancel")==0){
		    model.deleteRow(rowIndex);
		    ((RowHeaderModel)rowHeader.getModel()).removeElementAt(rowIndex);
		} else {
		    rowHeader.clearSelection();
		}
	    }
	    
	}
    }
    

}

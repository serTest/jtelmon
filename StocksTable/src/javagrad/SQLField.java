import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.FocusListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;
import javax.swing.JCheckBox;

// NOTE: this should be updated to use SQLWidgetFactory

public class SQLField  implements ActionListener,ItemListener, FocusListener, DocumentListener {

    private SQLResultSet results;
    SQLColumn metadata;
    String fieldName;
    JComboBox comboBox=null;
    JTextField textField=null;
    JCheckBox checkBox=null;
    JLabel label;
    JComponent widget;
    int columnType;
    boolean pseudoBoolean=false;

    boolean isHighlighted=false;
    boolean isFocussed=false;
    Color oldLabelBackground=null;
    String oldLabel;
    SQLDatabase database;

    private void highlightOn(){
	if (!isHighlighted && isFocussed){
	    label.setBackground(textField.getSelectionColor());
	    oldLabel=label.getText();
	    label.setText("(**)"+oldLabel);
	    isHighlighted=true;
	}
    }

    private void highlightOff(){
	if (isHighlighted){
	    isHighlighted=false;
	    label.setBackground(oldLabelBackground);
	    label.setText(oldLabel);
	}
    }

    public void focusGained(FocusEvent e){
	isFocussed=true;
    }

    public void focusLost(FocusEvent e){
	if (!e.isTemporary()){
	    if (widget.isValid()){
		updateFromWidget();
		isFocussed=false;
	    }
	}
    }
    // Implementation of java.awt.event.ActionListener

    /**
     * update the corresponding SQL field
     *
     * @param actionEvent an <code>ActionEvent</code> value
     */
    public void actionPerformed(ActionEvent actionEvent) {
	if (actionEvent.getSource()==widget){
	    if (widget.isValid())
		updateFromWidget();
	}
    }

    private void updateFromWidget(){
	try {
	    results.updateString(fieldName,getText());
	    // is this a good idea? Seems fast enough.
	    results.updateRow();
	    UI.message("Updated "+fieldName);
	} 
	catch (SQLException e){throw new RuntimeException(e.getMessage());}
	highlightOff();

    }

    // Implementation of java.awt.event.ItemListener

    /**
     * Describe <code>itemStateChanged</code> method here.
     *
     * @param itemEvent an <code>ItemEvent</code> value
     */
    public void itemStateChanged(ItemEvent itemEvent) {
	if (itemEvent.getSource()==widget){
		updateFromWidget();
	}
    }
    
    public String getText(){
	if (metadata.isYesNo()){
	    if (checkBox.isSelected())
		return "yes";
	    else 
		return "no";
	}
	if (metadata.isLinked()){
	    return ((IKVTriple)comboBox.getSelectedItem()).getKey().toString();
	}
	switch (columnType){
	case SQLColumn.CT_ENUM:
	    return (String)comboBox.getSelectedItem();
	case SQLColumn.CT_DATE:
	case SQLColumn.CT_TEXT:
	case SQLColumn.CT_INT:
	case SQLColumn.CT_FLOAT:
	case SQLColumn.CT_VARCHAR:
	    return textField.getText();
	default:
	    throw new RuntimeException("Unimplemented column type "+columnType);
	}
    }

    public void setObject(Object value){
	if (metadata.isYesNo()){
	    if (((String)value).equalsIgnoreCase("yes"))
		checkBox.setSelected(true);
	    else 
		checkBox.setSelected(false);
	    return;
	}

	switch (columnType){
	case SQLColumn.CT_ENUM:
	    comboBox.setSelectedItem(value);
	    break;
	case SQLColumn.CT_DATE:
	case SQLColumn.CT_TEXT:
	case SQLColumn.CT_INT:
	case SQLColumn.CT_FLOAT:
	case SQLColumn.CT_VARCHAR:
	    textField.setText(value == null ? "" : value.toString());
	    break;
	default:
	    throw new RuntimeException("Unimplemented column type "+columnType);
	}
    }
    private void chooseWidget(SQLColumn meta){
	
	label=new JLabel(meta.getLabel(),JLabel.RIGHT);
	label.setOpaque(true);
	oldLabelBackground=label.getBackground();

	if (meta.isYesNo()){
	    checkBox=new JCheckBox();
	    checkBox.addActionListener(this);
	    widget=checkBox;
	    return;
	}
	    
	if (meta.isLinked()){
	    try{
		columnType=SQLColumn.CT_ENUM;
		
		comboBox=new LinkComboBox(meta.getLink());
		comboBox.addItemListener(this);
		widget=comboBox;
	    } catch (SQLException e){
		throw new RuntimeException(e);
	    }
	} else {
	
	    switch(meta.getType()){
	    case SQLColumn.CT_ENUM:
		    comboBox= new JComboBox(meta.getValueList());
		    comboBox.addItemListener(this);
		    widget=comboBox;
		break;
	    case SQLColumn.CT_DATE:
	    case SQLColumn.CT_TEXT:
	    case SQLColumn.CT_INT:
	    case SQLColumn.CT_FLOAT:
	    case SQLColumn.CT_VARCHAR:
		textField=new JTextField();
		textField.addActionListener(this);
		textField.setEditable(meta.isWritable());
		textField.setColumns(meta.getDisplayColumns());
		textField.addFocusListener(this);
		textField.getDocument().addDocumentListener(this);
		widget=textField;
		break;
	    default:
		throw new RuntimeException("Unimplemented field type "+meta.getType());
	    }
	}
	
    }
    
    public SQLField(SQLColumn meta, SQLResultSet row){


	results=row;
	metadata=meta;
	database=meta.getTable().getDatabase();

	fieldName=meta.getColumnName();
	columnType=meta.getType();

	try{
	    row.absolute(1);
	    chooseWidget(meta);

	    setObject(results.getObject(fieldName));

	}
	catch (SQLException e){throw new RuntimeException(e.getMessage());}

    }
    public JLabel getLabel(){
	return label;
    }
    public JComponent getWidget(){
	return widget;
    }

    
    public void addToGridBag(JPanel bag, int row, int column){
	addToGridBag(bag,row,column,1);
    }
    public void addToGridBag(JPanel bag, int row, int column,int colSpan){
	GridBagConstraints where=new GridBagConstraints();
	where.gridx=column;
	where.gridy=row;
	
       
	where.weightx=1;
	where.anchor=GridBagConstraints.LINE_END;
	bag.add(label,where);
	where.anchor=GridBagConstraints.LINE_START;
	where.gridx=column+1;
	where.gridwidth=colSpan;

	if (columnType==SQLColumn.CT_TEXT || columnType==SQLColumn.CT_VARCHAR){
	    where.fill=GridBagConstraints.HORIZONTAL;
	}
	bag.add(widget,where);

    }
    public void changedUpdate(DocumentEvent e){
    }
    public void insertUpdate(DocumentEvent e){
	highlightOn();
    }
    public void removeUpdate(DocumentEvent e){
 	highlightOn();
    }

}

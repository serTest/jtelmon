import javax.swing.ComboBoxModel;
import javax.swing.ListModel;
import javax.swing.AbstractListModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
/**
 * This class provides glue around the ColumnLink class in order to 
 * provide a ComboBoxModel.
 *
 * @author <a href="mailto:bremner@unb.ca">David Bremner</a>
 * @version 1.0
 */
public class LinkComboBoxModel extends AbstractListModel 
    implements PropertyChangeListener, ComboBoxModel {


    // Implementation of java.beans.PropertyChangeListener

    /**
     * Describe <code>propertyChange</code> method here.
     *
     * @param propertyChangeEvent a <code>PropertyChangeEvent</code> value
     */
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
	fireContentsChanged(this,-1,-1);
    }
    
    /**
     * The ColumnLink object is responsible for doing all of the 
     * translation and lookup
     *
     */
    private ColumnLink link=null;


    /**
     * Stores the selected item (index,key,value)
     *
     */
    private IKVTriple selectedItem=null;


    /**
     * Creates a new <code>LinkComboBoxModel</code> instance.
     * 
     *
     * @param link a <code>ColumnLink</code> value
     */
    public LinkComboBoxModel(ColumnLink link){
	
	this.link=link;
	link.addPropertyChangeListener(this);
    }

    // Implementation of javax.swing.ListModel

    /**
     * Return how many items in the list(s);
     *
     * @return an <code>int</code> value
     */
    public int getSize() {
	return link.getSize();
    }

    /**
     * Describe <code>getElementAt</code> method here.
     *
     * @param n an <code>int</code> value
     * @return an <code>Object</code> value
     */
    public Object getElementAt(int n) {
	return new IKVTriple(n, link.getDataElement(n), 
			     link.getDisplayElement(n));
    }
    
    /**
     * Set the selected item. Note that in order to
     * translate between data and display values, it 
     * uses (index,key,value) triples.
     *
     * @param object an <code>Object</code> value
     */
    public void setSelectedItem(Object object) {


	IKVTriple triple;
	if (!(object instanceof IKVTriple)){
	    Object key=object;
	    Object value=link.keyToValue(key);
	    
	    triple=new IKVTriple(-1,key,value);
	} else {
	    triple=(IKVTriple)object;
	}

	selectedItem=triple;
	
	fireContentsChanged(this,
			    selectedItem.getIndex(),
			    selectedItem.getIndex());
	

    }

    public Object getSelectedItem() {
	
	return selectedItem;
    }



}

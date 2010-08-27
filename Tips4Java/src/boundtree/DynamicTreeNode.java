package boundtree;

import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

public class DynamicTreeNode extends DefaultMutableTreeNode {
	
	/**
	 * Constructs a new DynamicTreeNode instance with o as the user
	 * object.
	 */
	public DynamicTreeNode(HierarchicalDataStore store, HierarchicalItem item) {
		super(item);
		this.store = store;
	}
	
	/**
	 * overridden to narrow user object use to HierarchicalItems
	 * @param userObject the HierarchicalItem this node represents
	 */
	public void setUserObject(HierarchicalItem userObject) {
		super.setUserObject(userObject);
	}
	
	/**
	 * overridden to return a HierarchicalItem
	 */
	public HierarchicalItem getUserObject() {
		return (HierarchicalItem) super.getUserObject();
	}
	
	public int getChildCount() {
		if(!loaded && !loadInProgress) {
			loadChildren();
		}
		return super.getChildCount();
	}
	
	private void loadChildren() {
		try {
			if(!loaded && !loadInProgress) {
				loadInProgress = true;
				HierarchicalItem item = getUserObject();
				Vector<HierarchicalItem> childNodes = store.getChildItems(item.getId());
				if(childNodes != null) {
					for(int i = 0; i < childNodes.size(); i++) {
						HierarchicalItem childItem = childNodes.elementAt(i);
						add(new DynamicTreeNode(store, childItem));
					}
				}
				loaded = true;
				loadInProgress = false;
			}
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
	}
	
	/**
	 * indicate whether or not children of this node have been loaded from the 
	 * data store
	 * @return true, when children haven been loaded
	 */
	public boolean isLoaded() {
		return loaded;
	}
	
	/** Have the children of this node been loaded yet? */
	private boolean loaded = false;
	
	private boolean loadInProgress = false;
	
	/** the data store this node dynamically loads child nodes from */
	private HierarchicalDataStore store;
	
}

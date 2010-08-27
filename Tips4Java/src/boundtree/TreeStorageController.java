/*
  Copyright (c) 2005, Ulrich Hilger, Light Development, http://www.lightdev.com
  All rights reserved.

  Redistribution and use in source and binary forms, with or without modification, 
  are permitted provided that the following conditions are met:

    - Redistributions of source code must retain the above copyright notice, this 
       list of conditions and the following disclaimer.
       
    - Redistributions in binary form must reproduce the above copyright notice, 
       this list of conditions and the following disclaimer in the documentation 
       and/or other materials provided with the distribution.
       
    - Neither the name of Light Development nor the names of its contributors may be 
       used to endorse or promote products derived from this software without specific 
       prior written permission.

  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
  SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT 
  OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
  HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
  TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
*/

package boundtree;

import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeNode;

/**
 * TreeStorageController.java
 * 
 * <p>This class is used by class JPersistenTree to for persistent storage of 
 * changes to tree data. TreeStorageController 
 * acts as a TreeModelListener to store changes to an associated data store.</p> 
 * 
 * <p>A data store itself is not modeled, instead a class implementing a DataStoreAdapter 
 * interface is used, in our case a class implementing interface RegionStoreAdapter.</p>
 * 
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License,
 *      for details see file license.txt in the distribution package of this software
 *
 * @version 1, 18.09.2005
 */
public class TreeStorageController implements TreeModelListener {

	/**
	 * create a new TreeStorageController that is bound to a given data store
	 * @param store  the HierarchicalDataStore to bind the new controller to
	 */
  public TreeStorageController(HierarchicalDataStore store) {
    super();
    this.store = store;
  }
  
  /**
   * get the topmost node in the data hierarchy as a tree node
   * @return  the root node or null if none could be obtained
   */
  public TreeNode getRoot() {
  	DynamicTreeNode root = null;
		try {
			Vector<HierarchicalItem> v = store.getRootItems();
			if(v != null && v.size() > 0) {
				root = new DynamicTreeNode(store, v.firstElement());
			}
		} catch (StorageException e) {
			e.printStackTrace();
		}
    return root;
  }
  
  /**
   * get thed data store this controller is bound to 
   * @return  the data store
   */
  public HierarchicalDataStore getStore() {
    return store;
  }
  
  /**
   * create a given item in the data store
   * @param item  the item to create
   * @return  the created item or null if item could not be created
   */
  public Object createItem(HierarchicalItem item) {
  	Object newId = null;
  	try {
  		newId = (Integer) store.createItem(item);
  	} catch (StorageException e) {
  		e.printStackTrace();
  	}
  	return newId;
  }
  
  /**
   * delete the item with the given ID from the data store
   * @param id  the id of the item to delete
   * @return  the deleted id or null if the item could not be deleted
   */
  public Object deleteItem(Object id) {
  	Object deletedId = null;
  	try {
			deletedId = store.deleteItem(id);
		} catch (StorageException e) {
			e.printStackTrace();
		}
		return deletedId;
  }

  /**
   * persistently store changes from tree
   * @param parent  the parent item affected by the change (may be null for cases where 
   * each item in the child array has a valid parent id)
   * @param children  an arry of child objects that were changed
   */
  private void updateChangedItems(DynamicTreeNode parent, Object[] children) {
    for(int i = 0; i < children.length; i++) {
    	DynamicTreeNode child = (DynamicTreeNode) children[i];
    	try {
    		HierarchicalItem item = child.getUserObject();
    		if(parent != null) {
    			item.setParentId(parent.getUserObject().getId());
    		}
    		if(store.putItem(item) == null) {
    			// error: item was not found
    		}
			} catch (StorageException e1) {
				e1.printStackTrace();
			}
    }
  }
  
  /* ------------------- TreeModelListener implementation start ------------------------- */

  public void treeNodesChanged(TreeModelEvent e) {
  	updateChangedItems(null, e.getChildren());
  }

  public void treeNodesInserted(TreeModelEvent e) {
  	updateChangedItems((DynamicTreeNode) e.getTreePath().getLastPathComponent(), e.getChildren());
  }

  public void treeNodesRemoved(TreeModelEvent e) {
  }

  public void treeStructureChanged(TreeModelEvent e) {
  }

  /* ------------------- TreeModelListener implementation end ------------------------- */
  
  /** the data store this controller is bound to */
  private HierarchicalDataStore store;

}

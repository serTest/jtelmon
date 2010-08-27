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

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * JPersistentTree.java
 * 
 * <p>An extension to JTree utilizing persistent storage of tree data</p> 
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
public class JPersistentTree extends JTree {

  /**
   * construct a tree component that persistently stores data in a given data store.
   * @param store  the data store the new tree stores data to
   */
  public JPersistentTree(HierarchicalDataStore store) {
    super();
    storageController = new TreeStorageController(store);
    setModel(new DefaultTreeModel(storageController.getRoot()));
  }
  
  /**
   * delete the currently selected tree node and its children 
   * from the tree and from the data store
   * 
   * Please note: this is not transactional safe and should only be used in the context of 
   * our example. In case of error during recursive deletion the data gets inconsistent 
   * unless this method is extended by a transaction that can be rolled back in case of error.
   */
  public void deleteSelectedNode() {
    TreePath selectedPath = getSelectionPath();
    if(selectedPath != null) {
      Object o = selectedPath.getLastPathComponent();
      if(o != null) {
        DynamicTreeNode selectedNode = (DynamicTreeNode) o;
        if(deleteItem(selectedNode)) {
        	((DefaultTreeModel) getModel()).removeNodeFromParent(selectedNode);
        }
      }
    }    
  }
  
  /**
   * recursivley delete user object of given node and user objects of all its children 
   * from data store before deleting node
   * 
   * Please note: this is not transactional safe and should only be used in the context of 
   * our example. In case of error during recursive deletion the data gets inconsistent 
   * unless this method is extended by a transaction that can be rolled back in case of error.
   * 
   * @param node  the node to delete including
   * @return true, if deletion was successful
   */
  private boolean deleteItem(DynamicTreeNode node) {
  	boolean success = true;
    if(!node.isLeaf()) {
      Enumeration<DynamicTreeNode> en = node.children();
      while(en.hasMoreElements() && success) {
        success = deleteItem(en.nextElement());
      }
    }
    if(success) {
	    HierarchicalItem item = (HierarchicalItem) node.getUserObject();
	    try {
	    	Object deletedId = storageController.deleteItem(item.getId());
	      if(deletedId == null) {
	      	success = false;
	      }
	    }
	    catch (Exception e) {
	    	success = false;
	      e.printStackTrace();
	    }
    }
    return success;
  }  
  
  /**
   * create a new item as child of the currently selected tree node. The newly created 
   * child is persistently created in the data store too.
   * @param item  the item to create as child of the currently selected node
   */
  public void createInSelectedNode(HierarchicalItem item) {
    TreePath selectedPath = getSelectionPath();
    if(selectedPath != null) {
      Object o = selectedPath.getLastPathComponent();
      if(o != null) {
        DynamicTreeNode selectedNode = (DynamicTreeNode) o;
        item.setParentId(selectedNode.getUserObject().getId());
        Object newId = storageController.createItem(item);
				item.setId(newId);
				DynamicTreeNode newChild = new DynamicTreeNode(storageController.getStore(), item);
				((DefaultTreeModel) getModel()).insertNodeInto(
				    newChild, selectedNode, selectedNode.getChildCount());
				TreePath newPath = selectedPath.pathByAddingChild(newChild);
				setSelectionPath(newPath);
				startEditingAtPath(newPath);
      }
    }    
  }
  
  /**
   * get the storage controller this tree is associated with
   * @return  the controller
   */
  public TreeStorageController getStorageController() {
    return storageController;
  }
  
  /**
   * overriden to add our storage controller as a TreeModelListener to the 
   * new tree model
   * @see javax.swing.JTree#setModel(javax.swing.tree.TreeModel)
   */
  public void setModel(TreeModel newModel) {
  	TreeModel model = getModel();
  	if(model != null) {
  		model.removeTreeModelListener(storageController);
  	}
    newModel.addTreeModelListener(storageController);
    super.setModel(newModel);
  }
  
  /** the storage controller used by this tree for persistence */
  private TreeStorageController storageController;

}

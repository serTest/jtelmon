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

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * UserTreeCellEditor.java
 * 
 * <p>This class overrides DefaultTreeCellEditor to allow for custom user object handling 
 * of a region tree of application DayMate. A region tree has user objects of class 
 * HierarchicalItem. To edit the name of one HierarchicalItem inside the tree requires 
 * to override the methods that return the default cell editor and the editor value.</p>
 * 
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License,
 *      for details see file license.txt in the distribution package of this software
 *
 * @version 1, 31.07.2005
 */
public class UserTreeCellEditor extends DefaultTreeCellEditor {

  /**
   * constructor
   * @param tree  the tree this editor is used for
   * @param renderer  the renderer to use
   */
  public UserTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
    super(tree, renderer);
  }

  /**
   * overridden to return the HierarchicalItem as the user object after editing
   */
  public Object getCellEditorValue() {
    Object returnValue = null;
    Object value = super.getCellEditorValue();
    if(item == null) {
      returnValue = value;
    }
    else {
      item.setData(value);
      returnValue = item;
    }
    return returnValue;
  }

  /**
   * overridden to save the HierarchicalItem which is stored in the user object of a 
   * tree during editing
   */
  public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
    if(value instanceof DefaultMutableTreeNode) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
      Object userObject = node.getUserObject();
      if(userObject instanceof HierarchicalItem) {
        item = (HierarchicalItem) node.getUserObject();
      }
    }
    return super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);
  }
  
  /** reference to user object */
  private HierarchicalItem item;

}

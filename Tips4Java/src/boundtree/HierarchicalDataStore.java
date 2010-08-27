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

/**
 * HierarchicalDataStore.java
 * 
 * <p>Classes wishing to persistently store or load hierarchical data need 
 * to implement this interface. It is assumed that hierarchical data items, i.e. 
 * items that are linked with a 1:n parent-child relation, are identified 
 * each with a unique id.</p>
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
public interface HierarchicalDataStore {
  
  /**
   * get the item with the given id
   * @param id  the id of the item to get
   * @return the item with the given id or null, if none was found with that id
   * @throws StorageException
   */
  public HierarchicalItem getItem(Object id) throws StorageException;
  
  /**
   * get all child items of a given item
   * @param id the id of the item to get child items for
   * @return  a Vector of HierarchicalItem objects 
   * or null if an item with the given id was not found
   * @throws StorageException
   */
  public Vector<HierarchicalItem> getChildItems(Object id) throws StorageException;
  
  /**
   * get all root items in the data store, i.e. all top level items in the item 
   * hierarchy that have no parent
   * @return a Vector of HierarchicalItems with all root items
   * @throws StorageException
   */
  public Vector<HierarchicalItem> getRootItems() throws StorageException;

  /**
   * store an item with the given id
   * @param item  the item to store
   * @return the item that has been stored or null if no item with that id was found
   * @throws StorageException
   */
  public HierarchicalItem putItem(HierarchicalItem item) throws StorageException;
  
  /**
   * create a new item in the data store
   * @param item  the new item to create
   * @return  the id of the newly create item
   * @throws StorageException
   */
  public Object createItem(HierarchicalItem item) throws StorageException;
  
  /**
   * delete the item with the given id from the data store 
   * @param id
   * @return  the id of the deleted item or null if an item with the given id was not found
   * @throws StorageException
   */
  public Object deleteItem(Object id) throws StorageException;
}

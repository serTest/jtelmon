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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * GenericTransferable.java
 * 
 * <p>This transferable takes an object as data that is to be transferred. It 
 * uses DataFlavor.stringFlavor, which is supported by all objects. This transferable 
 * can be used in cases where a special handling in terms of which data flavors are 
 * acceptable or which data is transported do not matter.</p>
 * 
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License,
 *      for details see file license.txt in the distribution package of this software
 *
 * @version 1, 30.07.2005
 */
public class GenericTransferable implements Transferable {

	/**
	 * construct a transferabe with a given object to transfer 
	 * @param data  the data object to transfer
	 */
  public GenericTransferable(Object data) {
    super();
    this.data = data;
  }

  /**
   * get the data flavors supported by this object
   * @return an array of supported data flavors
   */
  public DataFlavor[] getTransferDataFlavors() {
    return flavors;
  }

  /**
   * determine whether or not a given data flavor is supported by this transferable
   * @return true, if the given data flavor is supported
   */
  public boolean isDataFlavorSupported(DataFlavor flavor) {
    return true;
  }

  /**
   * get the data this transferable transports
   * @return the data transported by this transferable
   */
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    return data;
  }

  /** the data this transferable transports */
  private Object data;

  /** storage for data flavors supported of this transferable */
  private static final DataFlavor[] flavors = new DataFlavor[1];

  /** the actual flavors supported by this transferable */
  static {
    flavors[0] = DataFlavor.stringFlavor;
  }
}

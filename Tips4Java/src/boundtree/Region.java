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

/**
 * <p>Region.java - A class that models a geographic region.</p>
 * 
 * <p>A special characteristic of a region is that it may be 
 * any geographic area such as a continent, a country, a state, a city or county, etc. 
 * Regions are modeled hierarchical in the way that each region links to one superior 
 * region, e.g. Paris links to France, France links to Europe, Europe links to World.</p>
 * 
 * <p>Both fields id and name are considered to be unique i.e. when a region has not yet 
 * an id its name in lower case letters is considered to be equal to another region 
 * having the same name.</p> 
 *
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the BSD License,
 *      for details see file license.txt in the distribution package of this software
 *
 * @version 2, September 18, 2005
 */

public class Region implements HierarchicalItem {

  /**
   * constructor
   */
  public Region() {
  }

  /**
   * construct a region object with a given id and name
   * @param id Integer  the id of the region
   * @param rName String  the name of the region
   */
  public Region(Integer id, String rName) {
    this();
    setId(id);
    setName(rName);
  }
  
  /**
   * get a string representation of this object
   */
  public String toString() {
    return getName();
  }

  /**
   * determine whether or not a given object is equal to this object 
   */
  public boolean equals(Object obj) {
    boolean isEqual = false;
    if (obj instanceof Region) {
      Region compareTo = (Region) obj;
      Object thisId = getId();
      Object otherId = compareTo.getId();
      if (thisId != null && otherId != null && thisId.equals(otherId)) {
        isEqual = true;
      }
      else {
        String thisName = getName();
        String otherName = compareTo.getName();
        if (thisName != null && otherName != null && thisName.equalsIgnoreCase(otherName)) {
          isEqual = true;
        }
      }
    }
    return isEqual;
  }

  /**
   * set the unique id of this region
   * @param id int  the region id
   */
  public void setId(Integer itemId) {
    this.regionId = itemId;
  }

  /**
   * get the id of this region
   * @return int  the region id
   */
  public Object getId() {
    return regionId;
  }

  /**
   * set the id of the region superior to this region
   * @param superiorId Integer  the superior region id
   */
  public void setSuperiorId(Integer superiorId) {
    this.superId = superiorId;
  }

  /**
   * get the id of the region superior to this region
   * @return Integer  the superior region id or -1 if not set
   */
  public Integer getSuperiorId() {
    return superId;
  }

  /**
   * set the name of this region
   * @param name String  the region name to set
   */
  public void setName(String name) {
    this.regionName = name;
  }

  /**
   * get the name of this region
   * @return String  the region name
   */
  public String getName() {
    return regionName;
  }
  
  /* ----------------------- HierarchicalItem implementation start ------------------ */

  public void setData(Object data) {
    setName(data.toString());
  }

  public Object getData() {
    return getName();
  }

  public void setId(Object itemId) {
    if(itemId instanceof Integer) {
      setId((Integer) itemId);
    }
  }

  public Object getParentId() {
    return superId;
  }

  public void setParentId(Object parentId) {
    if(parentId instanceof Integer) {
      setSuperiorId((Integer) parentId);
    }
  }
  public boolean isRoot() {
    return (superId == null);
  }
  
  /* ----------------------- HierarchicalItem implementation end ------------------ */
  
  /* ----------------------- class fields ------------------ */

  /** the region id */
  private Integer regionId;
  /** the region name */
  private String regionName;
  /** superior region id */
  private Integer superId = null;

}

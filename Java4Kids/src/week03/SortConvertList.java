/*
 * Miercuri, 31 martie 2010
 * Java Day 016, Week 04
 */

package week03;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortConvertList {
    public static void main(String args[]){
    String[]strArray = new String[] { "Java", "Source", "And", "and","Support","java2s"};
    List l = Arrays.asList(strArray);
    Collections.sort(l);
    System.out.println(l);
  }
}
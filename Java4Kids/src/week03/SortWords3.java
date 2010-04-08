/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package week03;

// SortWords3: sorts words that that user enters.
//   31 Aug 1998, Fred Swartz
// Updated: 2000-05-18 to Java 2
// Updated: 2002-04-29 (Sicily) to main
// Updated: 2002-04-30 use Collections data structures

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.*;     // For ActionListener ...
import javax.swing.*;        // For components
import java.util.*;          // For StringTokenizer

///////////////////////////////////////////////////////////////// SortWords3
public class SortWords3 extends JFrame {
    //============================================================ variables
    JTextField inField;   // get the input from here
    JTextField outField;  // put the output here

    //========================================================== constructor
    public SortWords3() {
        inField = new JTextField(30);   // use this for input
        JButton sortWordsButton = new JButton("Sort Words");
        sortWordsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List words = stringToList(inField.getText());
                Collections.sort(words);
                outField.setText(listToString(words, ", "));
            }
          });

        outField = new JTextField(30);  // use this for output
        outField.setEditable(false);    // don't let the user change it

        // Layout the fields
        Container content = this.getContentPane();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("Input"));
        content.add(inField);
        content.add(sortWordsButton);
        content.add(new JLabel("Sorted output"));
        content.add(outField);

        this.setTitle("SortWords3");
        this.pack();
    }//end constructor


    //========================================================= stringToList
    // Put all the "words" in a string into an array.
    List stringToList(String wordString) {
        ArrayList result = new ArrayList(20);
        StringTokenizer st = new StringTokenizer(wordString);
        while (st.hasMoreTokens()) { //--- Loop, getting each token
            result.add(st.nextToken());
        }
        return result;
    }//end stringToArray


    //========================================================= listToString
    // Convert list of strings to one string with 'separator' between each.
    String listToString(List a, String separator) {
        StringBuffer result = new StringBuffer(40);
        for (Iterator iter = a.iterator(); iter.hasNext(); ) {
            result.append(iter.next() + separator);
        }
        return result.toString();
    }//endmethod arrayToString


    //================================================================= main
    public static void main(String[] args) {
        JFrame window = new SortWords3();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }//end main
}//endclass SortWords3

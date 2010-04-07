/*
 http://leepoint.net/notes-java/data/collections/lists/ex-alphabetize.html
 http://leepoint.net/notes-java/data/collections/lists/arraylist.html
 Purpose: An exercise using Scanner, ArrayList, and Collections.sort.
          Read words, sort them, print them.
 */

package week03;
import java.util.*;

public class Alphabetize2 {

    public static void main(String[] args) {
        Scanner in    = new Scanner(System.in);
        // ArrayList<String> words = new ArrayList<String>();
        Vector<String> words = new Vector<String>();
        String myWord = new String();
        boolean ePunct=false;
        //... Read input one word at a time.
        System.out.println("Introduceti cuvinte urmate de . punct - apoi ENTER");

        //... Read input one word at a time, adding it to an array list.
        while (!ePunct ) {
            myWord = in.next();
            words.add(myWord);
            ePunct = (myWord.compareTo(".")==0);
        }

        //... Sort the words.
        Collections.sort(words);

        //... Print the sorted list.
        System.out.println("\n\nSorted words\n");
        for (String word : words) {
            System.out.println(word);
        }
    }
}

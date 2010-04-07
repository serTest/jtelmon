/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package week03;

import java.util.Scanner;
import java.util.StringTokenizer;

public class TokenTest {

   public static void main (String[] args) {
      Scanner scan = new Scanner(System.in);
      System.out.println("Enter a sentence to tokenize and press Enter:");
      String sentence = scan.nextLine();

      // default delimiter is " \t\n\r\f"
      String delimiter = " ,\n";
      StringTokenizer tokens = new StringTokenizer(sentence, delimiter);
      System.out.printf("Number of elements: %d\n", tokens.countTokens());

      System.out.println("The tokens are:");
      while (tokens.hasMoreTokens())
         System.out.println(tokens.nextToken());
   }
}

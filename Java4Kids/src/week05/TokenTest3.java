package week05;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
// import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

public class TokenTest3 {

    public static void main (String[] args) {
      String fileName = new String();
      fileName = "D:\\Java\\Temp\\TextSample.txt";
      File file = new File(fileName);
      // file = "/home/depit/Downloads/Licente1.csv";
      String tempString = null;
      Vector<String> v = new Vector<String>();
      //ArrayList<String> v = new ArrayList<String>();
      try {
        Scanner scan_fisier    = new Scanner(file);
        System.out.println("Documentul tokenizat : ");
        String sentence = null;
        while (scan_fisier.hasNext()) {
           tempString = scan_fisier.nextLine();
           sentence = sentence + tempString;
         }     
        String delimiter = " ,\n";
        StringTokenizer tokens = new StringTokenizer(sentence, delimiter);
        System.out.printf("Number of elements: %d\n", tokens.countTokens());
        System.out.println("The tokens are:");
        tempString=null;
        while (tokens.hasMoreTokens()){
            tempString = tokens.nextToken();
            // v.add(tempString);
            v.addElement(tempString);
        }
            System.out.println(tempString);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       System.out.println("The tokens are:");
  }
}

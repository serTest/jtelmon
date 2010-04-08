package week05;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;

public class TokenTest2 {
    // To
   public static void main (String[] args) {
      // Scanner scan = new Scanner(System.in);
      String fileName = new String();
      fileName = "D:\\Java\\Temp\\TextSample.txt";
      File file = new File(fileName);
      // file = "/home/depit/Downloads/Licente1.csv";
      try {
        Scanner scan    = new Scanner(file);
        System.out.println("Documentul tokenizat : ");
        String sentence = scan.nextLine();
        // default delimiter is " \t\n\r\f"
        String delimiter = " ,\n";
        StringTokenizer tokens = new StringTokenizer(sentence, delimiter);
        System.out.printf("Number of elements: %d\n", tokens.countTokens());
        System.out.println("The tokens are:");
        while (tokens.hasMoreTokens())
            System.out.println(tokens.nextToken());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
  }
}

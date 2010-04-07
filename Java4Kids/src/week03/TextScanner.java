package week03;
import java.io.File;
   import java.io.FileNotFoundException;
   import java.util.Scanner;

   public class TextScanner {

     private static void readFile(String fileName) {
       try {
         File file = new File(fileName);
         Scanner scanner = new Scanner(file);
         while (scanner.hasNext()) {
           System.out.println(scanner.next());
         }
         scanner.close();
       } catch (FileNotFoundException e) {
         e.printStackTrace();
       }
     }

     public static void main(String[] args) {
       String file = new String();
       file = "D:\\Java\\Temp\\TextSample.txt";
       if (args.length != 1) {
         System.err.println("usage: java TextScanner1"
           + "file location");
         // System.exit(0);
       }
       // readFile(args[0]);
       readFile(file);
     }
   }

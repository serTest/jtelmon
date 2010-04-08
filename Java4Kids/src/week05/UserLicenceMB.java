/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package week05;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UserLicenceMB {
  private static void readFile(String fileName) {
      String temp =new String();
      String user =new String();
    try {
      File file = new File(fileName);
      Scanner fisier    = new Scanner(file);
      Scanner tastatura = new Scanner(System.in);
      fisier.useDelimiter(System.getProperty("line.separator"));
      user = tastatura.next();
      while (fisier.hasNext()) {
          temp = fisier.next();
            if(temp.indexOf(user) !=-1){
                System.out.println(temp);
                temp = fisier.next();
            }
      }
      fisier.close();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception{
        //String color = "Nomenclator aplicatii utilitare ";
        //int index = color.indexOf("");
        //System.out.println(index);
        //Scanner myScanner = null;
        Scanner myScanner = new Scanner(System.in);
        String Da = new String();
        Da="da";
        String Continuare = new String();
        boolean conditie;
        do{
            System.out.print("INTRODUCETI NUMELE UTILIZATORULUI ? ");
            readFile("/home/depit/Downloads/Licente1.csv");
            System.out.print("Mai doriti inca o interogare ? (da/nu)");
            Continuare  = myScanner.next();
                  conditie = Continuare.equals(Da);
        } while (conditie);
  }
}

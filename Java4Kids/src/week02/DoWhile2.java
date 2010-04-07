package week02;
import java.util.Scanner;
public class DoWhile2 {
public static void main(String[] args){
    Scanner myScanner = new Scanner(System.in);
    String Continuare = new String();
    do{
          System.out.print("Mai doriti inca o interogare ? (da/nu)");
          Continuare  = myScanner.next();
    }while(Continuare.equals("da"));
}
}

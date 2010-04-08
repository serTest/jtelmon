package week05;
import java.util.Scanner;
public class DoWhile4 {
    public static void main(String[] args){
        Scanner tastatura = new Scanner(System.in);
        String Continuare = new String();
        do{
              System.out.print("Mai doriti inca o interogare ? (da/nu)");
              Continuare  = tastatura.next();
        } while (Continuare.equalsIgnoreCase("da") );
    }
}

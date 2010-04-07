
package week02;

public class DoWhile1{
  public static void main(String[] args){
    int n = 12345;
    int t,r = 0;
    System.out.println("The original number : " + n);
    do{
      t = n % 10;
      r = r * 10 + t;
      n = n / 10;
    }while (n > 0);
    System.out.println("The reverse number : " + r);
  }
}

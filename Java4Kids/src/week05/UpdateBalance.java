package week05;

import java.util.Scanner;  //access to Scanner class
import java.io.*;          //access to file stream and exception classes

public class UpdateBalance
{
  public static void main( String args[] ) throws FileNotFoundException
 {
    int accountNumber;       //create variables for the fields in the file
    double accountBalance;
    String accountName;
    Scanner inFile      = new Scanner( new File( "AccountData.txt" ) );
    PrintWriter outFile = new PrintWriter( "AccountReport.txt" );

    while ( inFile.hasNext() ) //while there is data to process…
   {
      accountNumber  = inFile.nextInt();
      accountBalance = inFile.nextDouble();
      accountName    = inFile.nextLine();
      accountBalance = accountBalance + ( accountBalance * .10 );
      outFile.printf("%d     %-18s   $%,8.2f%n", accountNumber,
                     accountName, accountBalance);
  }

    inFile.close(); outFile.close();  //close the files
 }
}

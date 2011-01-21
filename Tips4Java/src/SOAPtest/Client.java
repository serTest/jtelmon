package SOAPtest;

//////////////////////////////////////////////////////
/// The following example illustrates a Client to a
/// WebService developed using C# and the .NET Framework.
/// author: Gopalan Suresh Raj
/// http://gsraj.tripod.com/dotnet/webservices/webservice_java_client.html
//////////////////////////////////////////////////////

import java.io.*;

/**
  * Web Service Client Class
  * @author Gopalan Suresh Raj
  */
public class Client {

  /** Entry Point to this Application */
  public static void main(String[] args) {
    try {
      // Create a proxy
      ApacheSoapProxy proxy = new ApacheSoapProxy ();

      // Invoke generateOID() over SOAP and get the new OID
      String result = proxy.generateResult ();

      // Print out the value
      System.out.println ("The Result is :"+result);
    }
    catch (java.net.MalformedURLException exception) {
      exception.printStackTrace ();
    }
    catch (org.apache.soap.SOAPException exception) {
      exception.printStackTrace ();
    }
  }
}
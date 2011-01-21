package SOAPtest;
/*
 *  This Class generates the SOAP message Body element
 *  http://gsraj.tripod.com/dotnet/webservices/webservice_java_client.html
 */
import java.io.*;
import org.apache.soap.*;
import org.apache.soap.util.*;
import org.apache.soap.util.xml.*;
import org.apache.soap.rpc.SOAPContext;

/**
  * This Class generates the SOAP message Body element
  * @author Gopalan Suresh Raj
  */
public class ApacheMessageBody extends Body {

  /** potential argument to the web method. */
  //public String inputString_;

  /**
    * Override the Apache default marshall method
    * and change how the SOAP Body element
    * is serialized.
    */
  public void marshall (String inScopeEncodingStyle,
                                    Writer sink,
                                    NSStack nameSpaceStack,
                                    XMLJavaMappingRegistry registry,
                                    SOAPContext context) throws IllegalArgumentException, IOException {
    // Set the Body element
    String soapEnvironmentNamespacePrefix = "SOAP-ENV";
    sink.write ('<'+soapEnvironmentNamespacePrefix+':'+
                     Constants.ELEM_BODY+'>'+
                     StringUtils.lineSeparator);

// Write out the method name and related argument (s)
//    sink.write ("<generateOID xmlns=\""http://tempuri.org/\">"+
            //"<inputString>"+inputString_+"</inputString>"+
//                     "</generateOID>");

    sink.write ("<generateOID xmlns=\"http://tempuri.org/\">"+"</generateOID>");



    // Close the Body element
    sink.write ("</" + soapEnvironmentNamespacePrefix+':'+
                     Constants.ELEM_BODY+'>'+
                     StringUtils.lineSeparator);
    nameSpaceStack.popScope ();
  }
}
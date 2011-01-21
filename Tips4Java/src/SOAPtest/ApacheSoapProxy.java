
package SOAPtest;

//////////////////////////////////////////////////////
/// This Class creates a Java Proxy for the .NET endpoint
/// author: Gopalan Suresh Raj
/// http://gsraj.tripod.com/dotnet/webservices/webservice_java_client.html
//////////////////////////////////////////////////////

import java.io.*;
import java.net.*;
import javax.activation.*;
import org.apache.soap.*;
import org.apache.soap.messaging.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.apache.xerces.parsers.SAXParser;

/**
  * This Class creates a Java Proxy for the .NET endpoint
  * @author Gopalan Suresh Raj
  */
public class ApacheSoapProxy {
  private URL url_ = null;
  private String soapActionUri_ = "";
  private Message message_ = new Message ();
  private Envelope envelope_ = new Envelope ();
  DataHandler soapMessage_ = null;

  /** Default No argument constructor */
  public ApacheSoapProxy () throws MalformedURLException {
    this.url_ = new URL ("http://192.168.61.28/TestWeb/HelloWorld.asmx");
  }

  /** Set the End Point URL */
  public synchronized void setEndPoint (URL url) {
    this.url_ = url;
  }

  /** Retrieve the End Point URL */
  public synchronized URL getEndPoint () {
    return this.url_;
  }

  /**
    * Apache 2.2 classes encode messages differently than .NET does.
    * Therefore we have to override the piece that builds the body and
    * the pieces that interpret the response.
    */
  public synchronized String generateResult () throws SOAPException {
    String returnValue = "";

    if (this.url_ == null) {
      throw new SOAPException (Constants.FAULT_CODE_CLIENT,
                                               "A URL must be specified through "+
                                               "ApacheSoapProxy.setEndPoint(URL)");
    }
    // Get this from the soapAction attribute on the
    // soap:operation element that is found within the SOAP
    // binding information in the WSDL
    this.soapActionUri_ = "http://tempuri.org/HelloWorldMethod";
    ApacheMessageBody ourBody = new ApacheMessageBody ();

    // Set the argument
    //theBody.inputString_ = "";

    // Replace the default body with our own
    this.envelope_.setBody (ourBody);
    message_.send (this.getEndPoint(), this.soapActionUri_, this.envelope_);

    try {
      // Since the Body.unmarshall() handler is static, we can't
      // replace the basic machinery easily. Instead, we must obtain
      // and parse the message on our own.
      this.soapMessage_ = this.message_.receive();
      XMLReader reader = (XMLReader)Class.forName("org.apache.xerces.parsers.SAXParser").newInstance();
      SAXHandler handler = new SAXHandler();
      // handler.setElementToSearchFor ("generateOIDResult");
      handler.setElementToSearchFor ("HelloWorldMethodResult");

      // Set the Content Handler
      reader.setContentHandler (handler);

      // Parse the file
      reader.parse ( new InputSource (new StringReader (this.soapMessage_.getContent().toString() )));

      // If we reached here, the result has been parsed and
      // stored in the handler instance.
      returnValue = handler.getResult ();
    }
    catch (Exception exception) {
      exception.printStackTrace ();
    }
    return returnValue;
  }

}
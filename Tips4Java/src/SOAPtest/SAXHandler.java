package SOAPtest;

//////////////////////////////////////////////////////
/// This Class parses the SOAP response
/// author: Gopalan Suresh Raj
/// http://gsraj.tripod.com/dotnet/webservices/webservice_java_client.html
//////////////////////////////////////////////////////

import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
  * This Class parses the SOAP response. This is a
  * general purpose class  that can obtain any single
  * element response.
  *
  * @author Gopalan Suresh Raj
  */
public class SAXHandler extends DefaultHandler {

  private String indent_ = "";
  private String result_ = "";
  private String elementToSearchFor_ = "";
  private boolean foundResult_ = false;

  /** Default No argument constructor */
  public SAXHandler () {
  }

  /**
    * Retrieve the result for a single element
    * The code has to be modified as necessary
    * for more complex types and arrays
    */
  public String getResult () {
    return result_;
  }

  /** Provide the element name to search for */
  public void setElementToSearchFor (String elementName) {
    elementToSearchFor_ = elementName;
  }

  /** Retrieve the set element name */
  public String getElementToSearchFor () {
    return elementToSearchFor_;
  }

  /**
    * Overriden method of the DefaultHandler class to
    * gain notification of all SAX events
    */
  public void startElement (String namespaceURI,
                                         String localName,
                                         String qName,
                                         Attributes attributes) throws SAXException {
    if (foundResult_ == false) {
      foundResult_ = (localName.compareTo (elementToSearchFor_) == 0);
    }
  }

  /**
    * Overriden method of the DefaultHandler class to
    * gain notification of all SAX events
    */
  public void characters (char[] characters, int start, int length) throws SAXException {
    if (foundResult_ == true) {
      result_ = String.valueOf (characters, start, length);
      foundResult_ = false;
    }
  }

}
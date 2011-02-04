/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BimBimWS;

import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


public class ServiceCall
{
	private static final String SOAP_ACTION1 = "http://tempuri.org/";
	private static final String NAMESPACE1   = "http://tempuri.org/";
	private static final String URL1         = "http://bimbim.in/Sample/TestService.asmx";

    private static final String SOAP_ACTION2 = "http://tempuri.org/";
    private static final String NAMESPACE2   = "http://tempuri.org/";
    private static final String URL2         = "http://www.pangram.ro/TestWeb/HelloWorld.asmx";

    private static final String SOAP_ACTION3 = "http://tempuri.org/";
    private static final String NAMESPACE3   = "http://tempuri.org/";
    private static final String URL3         = "http://bimbim.in/Sample/TestService.asmx";

    private static final String SOAP_ACTION4 = "http://tempuri.org/";
    private static final String NAMESPACE4   = "http://tempuri.org/";
    private static final String URL4         = "http://192.168.61.28/TestWeb/HelloWorld.asmx";

    private static final String SOAP_ACTION5 = "http://tempuri.org/";
    private static final String NAMESPACE5   = "http://tempuri.org/";
    private static final String URL5         = "http://localhost/TestWeb/HelloWorld.asmx";

    private static final String SOAP_ACTION6 = "http://tempuri.org/";
    private static final String NAMESPACE6   = "http://tempuri.org/";
    // private static final String URL6         = "http://192.168.61.3/TestWeb/HelloWorld.asmx";
    private static final String URL6         = "http://192.168.61.3/TestWeb/PersonPassport2.asmx";

    private static final String SOAP_ACTION7 = "http://tempuri.org/";
    private static final String NAMESPACE7   = "http://tempuri.org/";
    private static final String URL7         = "http://127.0.0.1/TestWeb/HelloWorld.asmx";


	private boolean isResultVector = false;

	protected Object call(String soapAction, SoapSerializationEnvelope envelope , String URL)
	{
		Object result = null;

		final HttpTransportSE transportSE = new HttpTransportSE(URL);

		transportSE.debug = false;

		// call and Parse Result.
		try
		{
			transportSE.call(soapAction, envelope);
			if (!isResultVector)
			{
				result = envelope.getResponse();
			} else
			{
				result = envelope.bodyIn;
			}
		} catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final XmlPullParserException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

    public String CallHelloWorld()
    {
           //final String sGetMethod2 = "HelloWorldMethod";
    	   //final String sGetMethod3 = "HelloWorld";
    	   //final String sGetMethod4 = "HelloWorldMethod";
    	   //final String sGetMethod5 = "HelloWorldMethod";
    	   final String sGetMethod6 = "HelloWorld";
    	   //final String sGetMethod7 = "HelloWorldMethod";

            // Create the outgoing message
            final SoapObject requestObject =
                    new SoapObject(NAMESPACE6, sGetMethod6);
            // Create soap envelop .use version 1.1 of soap
            final SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(
                            SoapEnvelope.VER11);
            envelope.dotNet = true;
            // add the outgoing object as the request
            envelope.setOutputSoapObject(requestObject);
            // envelope.addMapping(NAMESPACE2, String.class.getSimpleName(), String.class);

            // call and Parse Result.
            final Object response = this.call(SOAP_ACTION6 + sGetMethod6, envelope, URL6);
            SoapPrimitive sp1= (SoapPrimitive) response;
            //Person result = null;
            //if (response != null)
            //{
            //      result = new Person((SoapObject) response);
            //}

            String result = sp1.toString();
                    //              ((SoapObject) response).getProperty(0).toString();

            return result;
    }


	public Person CallGetSingle()
	{
		final String sGetSingle = "GetSingle";

		// Create the outgoing message
		final SoapObject requestObject =
			new SoapObject(NAMESPACE6, sGetSingle);
		// Create soap envelop .use version 1.1 of soap
		final SoapSerializationEnvelope envelope =
			new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// add the outgoing object as the request
		envelope.setOutputSoapObject(requestObject);
		envelope.addMapping(NAMESPACE6,
				Person.PERSON_CLASS.getSimpleName(),
				Person.PERSON_CLASS);

		// call and Parse Result.
		final Object response = this.call(
				SOAP_ACTION6 + sGetSingle, envelope,URL6);
		Person result = null;
		if (response != null)
		{
			result = new Person((SoapObject) response);
		}

		return result;
	}

	public Boolean CallSetValue(Person param)
	{
		final String sSetValue = "SetValue";
		final String svalue = "value";
		// Create the outgoing message
		final SoapObject requestObject =
			new SoapObject(NAMESPACE1, sSetValue);
		// Set Parameter type String
		requestObject.addProperty(svalue, param);
		// Create soap envelop .use version 1.1 of soap
		final SoapSerializationEnvelope envelope =
			new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// add the outgoing object as the request
		envelope.setOutputSoapObject(requestObject);
		envelope.addMapping(NAMESPACE1,
				Person.PERSON_CLASS.getSimpleName(),
				Person.PERSON_CLASS);
		// Register Marshaler
		// For date marshaling
		Marshal dateMarshal = new MarshalDate();
		dateMarshal.register(envelope);
		// For float marshaling
		Marshal floatMarshal = new MarshalFloat();
		floatMarshal.register(envelope);
		// call and Parse Result.
		final Object response = this.call(
				SOAP_ACTION1 + sSetValue, envelope, URL1);
		Boolean result = null;
		if (response != null)
		{
			try
			{
				if (response != null
						&& response.getClass() ==
							org.ksoap2.serialization.SoapPrimitive.class)
				{
					result = Boolean.parseBoolean(response.toString());
				}
			} catch (Exception e)
			{
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return result;
	}
}

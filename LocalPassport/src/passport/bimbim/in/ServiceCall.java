package passport.bimbim.in;

import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class ServiceCall
{
	private static final String SOAP_ACTION = "http://localhost/";
	private static final String NAMESPACE   = "http://localhost/";
	private static final String URL         = "http://localhost/TestWeb/PersonPassport2.asmx";

	
	private boolean isResultVector = false;

	protected Object call(String soapAction, 
			SoapSerializationEnvelope envelope)
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

	public Passport CallGetSingle()
	{
		final String sGetSingle = "GetSingle";

		// Create the outgoing message
		final SoapObject requestObject = 
			new SoapObject(NAMESPACE, sGetSingle);
		// Create soap envelop .use version 1.1 of soap
		final SoapSerializationEnvelope envelope = 
			new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// add the outgoing object as the request
		envelope.setOutputSoapObject(requestObject);
		envelope.addMapping(NAMESPACE, 
				Passport.PASSPORT_CLASS.getSimpleName(),
				Passport.PASSPORT_CLASS);

		// call and Parse Result.
		final Object response = this.call(
				SOAP_ACTION + sGetSingle, envelope);
		Passport result = null;
		if (response != null)
		{
			result = new Passport((SoapObject) response);
		}

		return result;
	}

	public Boolean CallSetValue(Passport out)
	{
		final String sSetValue = "SetValue";
		final String svalue = "value";
		// Create the outgoing message
		final SoapObject requestObject = 
			new SoapObject(NAMESPACE, sSetValue);
		// Set Parameter type String
		requestObject.addProperty(svalue, out);
		// Create soap envelop .use version 1.1 of soap
		final SoapSerializationEnvelope envelope = 
			new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// add the outgoing object as the request
		envelope.setOutputSoapObject(requestObject);
		envelope.addMapping(NAMESPACE, 
				Passport.PASSPORT_CLASS.getSimpleName(),
				Passport.PASSPORT_CLASS);
		// Register Marshaler
		// For date marshaling
		Marshal dateMarshal = new MarshalDate();
		dateMarshal.register(envelope);
		// For float marshaling
		Marshal floatMarshal = new MarshalFloat();
		floatMarshal.register(envelope);
		// call and Parse Result.
		final Object response = this.call(
				SOAP_ACTION + sSetValue, envelope);
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

/*
 * http://www.anddev.org/calling_a_web_service_from_android-t348.html
 * http://stackoverflow.com/questions/1052300/how-to-call-a-net-webservice-from-android-using-ksoap2
 * http://seesharpgears.blogspot.com/2010/10/ksoap-android-web-service-tutorial-with.html
 * http://seesharpgears.blogspot.com/2010/10/web-service-that-returns-array-of.html
 */

package Soap.Test2;

/*
import android.app.Activity;
import android.os.Bundle;

public class myWebService extends Activity {
    // Called when the activity is first created. 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
*/

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransport;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class myWebService extends Activity 
{
	 //private static final String SOAP_ACTION = "HelloYou";
	 private static final String SOAP_ACTION = "http://tempuri.org/HelloWorldMethod";
     private static final String METHOD_NAME = "HelloWorldMethod";
     private static final String NAMESPACE = "http://tempuri.org";
     private static final String URL = "http://192.168.61.28/TestWeb/HelloWorld.asmx";
     private Object resultRequestSOAP = null;
     
	    @Override
	    public void onCreate(Bundle icicle)
	    {
	        super.onCreate(icicle);
	        TextView tv = new TextView(this);
	        setContentView(tv);
	       

	    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


	    //SoapObject 
	    // request.addProperty("firstname", "John");
	    // request.addProperty("lastname", "Williams");
	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	    envelope.setOutputSoapObject(request);
	   

	    HttpTransport androidHttpTransport = new HttpTransport(URL);
	    try 
	    {
	        androidHttpTransport.call(SOAP_ACTION, envelope);
	        resultRequestSOAP =  envelope.getResponse();
	        String[] results = (String[])  resultRequestSOAP;
	        tv.setText( results[0]);
	    }
	    catch (Exception aE)
	    {
	    	aE.printStackTrace ();;
	    }
	   }
}
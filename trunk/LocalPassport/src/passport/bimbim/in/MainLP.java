/*
 * http://seesharpgears.blogspot.com/2010/10/web-service-that-returns-array-of.html
 * http://seesharpgears.blogspot.com/2010/10/ksoap-android-web-service-tutorial-with.html
 * http://seesharpgears.blogspot.com/2010/11/implementing-ksoap-marshal-interface.html
 * http://seesharpgears.blogspot.com/2010/11/basic-ksoap-android-tutorial.html
 * 
 * http://bimbim.in/post/2010/10/08/Android-Calling-Web-Service-with-complex-types.aspx
 * 
 * http://romenlaw.blogspot.com/2008/08/consuming-web-services-from-android.html
 * 
 */

package passport.bimbim.in;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
// import bimbimin.android.webservice.dto.Person;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
//import org.ksoap2.transport.HttpTransportSE;

public class MainLP extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // ServiceLPCall call = new ServiceLPCall();
        // Passport out = call.CallGetSingle();
        // Result2 = out.get_name();
        // Boolean res = call.CallSetValue(out);
        String URL = "http://192.168.61.3/TestWeb/PersonPassport2.asmx";
        String MethodName = "ReturnAll";
        String NAMESPACE = "http://tempuri.org/";
        Passport[] allPassports;
        Passport onePassport;
        
        // allPassports = GetAllPassports(URL, MethodName, NAMESPACE );
        allPassports = CallGetAll(URL, MethodName, NAMESPACE );
        String Result1 = new String();
        Result1 = allPassports[1].get_name();
        Toast.makeText(this, "Result: " + Result1 + "\n", Toast.LENGTH_LONG).show() ;
        
    }

    public static Passport[] CallGetAll(String URL, String MethodName, String NAMESPACE )
    {

    	SoapObject request = new SoapObject(NAMESPACE, MethodName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        SoapObject response = null;
        AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
        try
           {
       		envelope.addMapping(NAMESPACE, Passport.PASSPORT_CLASS.getSimpleName(), Passport.PASSPORT_CLASS);
       	 	androidHttpTransport.call(NAMESPACE + MethodName, envelope);
            response = (SoapObject)envelope.getResponse();
           }
        catch(Exception e)
        {
            e.printStackTrace();
            
        }
               
        return RetrievePassportFromSoap(response);
        
    }
    
    
    public static Passport[] GetAllPassports(String URL, String MethodName, String NAMESPACE )
    {
        SoapObject response = InvokeMethod(URL,MethodName, NAMESPACE);
        return RetrievePassportFromSoap(response);
        
    }

    public static SoapObject InvokeMethod(String URL, String MethodName, String NAMESPACE)
    {
        SoapObject request = GetSoapObject(MethodName,NAMESPACE);
        SoapSerializationEnvelope envelope = GetEnvelope(request);
        return  MakeCall(URL,envelope,NAMESPACE,MethodName);
    }    
    
    
    public static Passport[] RetrievePassportFromSoap(SoapObject soap)
    {
        //Category[] categories = new Category[soap.getPropertyCount()];
        Passport[] passports  = new Passport[soap.getPropertyCount()];
        for (int i = 0; i < passports.length; i++) {
            SoapObject pii = (SoapObject)soap.getProperty(i);
            //Category category = new Category();
            Passport passport = new Passport();
            //category.CategoryId = Integer.parseInt(pii.getProperty(0).toString());
            passport.set_name( pii.getProperty(0).toString()) ;
            //categories[i] = category;
            passports[i]  = passport;
        }
        // return categories;
        return passports;
    }
    
    
    public static SoapObject GetSoapObject(String MethodName, String NAMESPACE)
    {
        return new SoapObject(NAMESPACE,MethodName);
    }

    public static SoapSerializationEnvelope GetEnvelope(SoapObject Soap)
    {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(Soap);
        return envelope;
    }    
    
    public static SoapObject MakeCall(String URL, SoapSerializationEnvelope Envelope, String NAMESPACE, String METHOD_NAME)
    {
        AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
         try
            {
        	    //Envelope.addMapping(NAMESPACE, "Passport", new Passport().getClass());
        		Envelope.addMapping(NAMESPACE, Passport.PASSPORT_CLASS.getSimpleName(), Passport.PASSPORT_CLASS);
        	 	androidHttpTransport.call(NAMESPACE + METHOD_NAME, Envelope);
                SoapObject response = (SoapObject)Envelope.getResponse();
                return response;
            }
         catch(Exception e)
         {
             e.printStackTrace();
             
         }
         return null;
    }
   
}

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
 * http://www.telerik.com/help/aspnet-ajax/combo_loadondemandwebservice.html
 * http://www.daniweb.com/forums/thread151766.html
 * 
 */

package passport.bimbim.in;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;
import android.widget.Toast;
// import bimbimin.android.webservice.dto.Person;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
//import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class MainLP2 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // ServiceLPCall call = new ServiceLPCall();
        // Passport out = call.CallGetSingle();
        // Result2 = out.get_name();
        // Boolean res = call.CallSetValue(out);
        // String URL = "http://192.168.61.3/TestWeb/PersonPassport2.asmx";
        // String URL = "http://192.168.61.3/TestWeb/PersonPassport3a.asmx";
        // String URL = "http://192.168.61.3/TestWeb/PersonPassport4.asmx";
        String URL = "http://192.168.61.3/TestWeb/PersonPassport4a.asmx";
        //String MethodName = "ReturnAll";
        //String MethodName = "ReturnTable";
        // String MethodName = "ReturnArray";
        String MethodName = "GetPersonArrayList";
        String NAMESPACE = "http://tempuri.org/";
        Passport[] allPassports;
        // Passport onePassport;
        
        allPassports = GetAllPassports(URL, MethodName, NAMESPACE );

        String Result = new String();
        String Result0 = new String();
        String Result1 = new String();
        String Result2 = new String();

        Result0 = allPassports[0].get_name();
        Result1 = allPassports[1].get_name();
        Result2 = allPassports[2].get_name();
        Result = Result0 + "\n" + Result1 + "\n" + Result2 ;
        
        System.out.println(Result);
        System.out.println(Result1);
        Toast.makeText(this, "Result: " + Result + "\n", Toast.LENGTH_LONG).show() ;
        
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
        // return  MakeCall2(URL,envelope,NAMESPACE,MethodName);
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
        // HttpTransportSE androidHttpTransport  = new HttpTransportSE(URL);

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

    public static SoapObject MakeCall2(String URL, SoapSerializationEnvelope Envelope, String NAMESPACE, String METHOD_NAME)
    {
        // AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
        HttpTransportSE androidHttpTransport  = new HttpTransportSE(URL);

         try
            {
        	    //Envelope.addMapping(NAMESPACE, "Passport", new Passport().getClass());
        		Envelope.addMapping(NAMESPACE, Passport.PASSPORT_CLASS.getSimpleName(), Passport.PASSPORT_CLASS);
        	 	androidHttpTransport.call(NAMESPACE + METHOD_NAME, Envelope);

        	 	SoapObject soResult = null;
        	 	soResult = (SoapObject)Envelope.getResponse();

        	 	
        	 	KXmlParser parser=new KXmlParser();
        	 	// ByteArrayInputStream bais = new ByteArrayInputStream();
        	 	ByteArrayInputStream bais = null;
        	 	String str1 = new String();
        	 	// str1 = (String) Envelope.getResponse();
        	 	str1 = soResult.toString();
        	 	Reader reader=new InputStreamReader(new ByteArrayInputStream(str1.getBytes()));
        	 	
        	 	// Reader reader=new InputStreamReader(new ByteArrayInputStream(((String)Envelope.getResponse()).getBytes()));

        	 	Document doc = null;
        	 	try {
        	 	parser.setInput(reader);
        	 	doc=new Document();
        	 	doc.parse(parser);
        	 	} catch (XmlPullParserException e) {
        	 	e.printStackTrace();
        	 	}
        	 	String sResult = parser.getName();
        	 	System.out.println(sResult);

        	 	DescribeLayout resultObject = new DescribeLayout(null,soResult) ;
        	 	System.out.println("REEEEEEEESULTTTTTTTTOBJEEEEEECTTTTTTTTTT"+resultObject.toString());
        	 	resultObject.addSoapObject(null, soResult); // add top node
        	 	storeResult(resultObject, soResult); 
        	 	
        	 	//KvmSerializable ks = (KvmSerializable) Envelope.bodyIn;
        	 	
        	 	// CastException :=> 
        	 	// Vector<Passport> vPasports = (Vector<Passport>)Envelope.getResponse() ;  

        	 	//if(vPasports != null)
        	 	//{
        	 	//    for(Passport curEvent : vPasports)
        	 	//    {
        	 	        // Log.v("BILGICIFTLIGI", curEvent.toString());
        	 	//    }
        	 	//}
        	 	
                //for(int i=0;i<ks.getPropertyCount();i++)
                // {
                //    ks.getProperty(i); //if complex type is present then you can cast this to SoapObject and if primitive type is returned you can use toString() to get actuall value.
                // }

        	 	
            	SoapObject response = (SoapObject) Envelope.getResponse();
            	
      	 	
        	 	
               
         	   
                return response;
            }
         catch(Exception e)
         {
             e.printStackTrace();
             
         }
         return null;
         
         
         
    }

    public static ResultObject storeResult(ResultObject resultobj, SoapObject soap) {
        PropertyInfo propertyInfo = new PropertyInfo();
        int count = soap.getPropertyCount();
        System.out.println("Count in store Resultttt"+count);
       for (int i = 0; i < count; i++) {
          //threadYield();
          Object obj1 = soap.getProperty(i);
          System.out.println("protperty in object1"+obj1);
          soap.getPropertyInfo(i, propertyInfo);
          if (obj1 instanceof SoapObject) {
             // don't recurse empty objects
             if (((SoapObject)obj1).getPropertyCount() > 0) {
                // add child
                ResultObject child = resultobj.addSoapObject(propertyInfo.name, (SoapObject)obj1);
                System.out.println("chielddddddd"+child);
                // recurse into child
                if (child != null) {
                 ResultObject  progress = storeResult(child, (SoapObject)obj1);
                 System.out.println("Progresssssssss"+progress);
                }
             }
          }
       }
       return resultobj;
     }

/******************************************************************************************
     http://androiddevblog.blogspot.com/2010/04/serializing-and-parceling-ksoap2.html
*******************************************************************************************/     
    public static SoapObject MakeCall3(String URL, SoapSerializationEnvelope Envelope, String NAMESPACE, String METHOD_NAME)
    {
        // AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
        HttpTransportSE androidHttpTransport  = new HttpTransportSE(URL);

         try
            {
        	    //Envelope.addMapping(NAMESPACE, "Passport", new Passport().getClass());
        		Envelope.addMapping(NAMESPACE, Passport.PASSPORT_CLASS.getSimpleName(), Passport.PASSPORT_CLASS);
        	 	androidHttpTransport.call(NAMESPACE + METHOD_NAME, Envelope);

        	 	SoapObject soResult = null;
        	 	soResult = (SoapObject)Envelope.getResponse();

        	 	// import org.xmlpull.v1.XmlSerializer;
        	 	// org.xmlpull.v1.XmlSerializer aSerializer=Xml.newSerializer();
        	 	
        	 	
        	 	KXmlParser parser=new KXmlParser();
        	 	//ByteArrayInputStream bais = new ByteArrayInputStream();
        	 	ByteArrayInputStream bis = null;
        	 	String str1 = new String();
        	 	// str1 = (String) Envelope.getResponse();
        	 	str1 = soResult.toString();
        	 	Reader reader=new InputStreamReader(new ByteArrayInputStream(str1.getBytes()));
        	 	
        	 	// Reader reader=new InputStreamReader(new ByteArrayInputStream(((String)Envelope.getResponse()).getBytes()));

        	 	Document doc = null;
        	 	try {
        	 	parser.setInput(reader);
        	 	doc=new Document();
        	 	doc.parse(parser);
        	 	} catch (XmlPullParserException e) {
        	 	e.printStackTrace();
        	 	}
        	 	String sResult = parser.getName();
        	 	System.out.println(sResult);

        	 	DescribeLayout resultObject = new DescribeLayout(null,soResult) ;
        	 	System.out.println("REEEEEEEESULTTTTTTTTOBJEEEEEECTTTTTTTTTT"+resultObject.toString());
        	 	resultObject.addSoapObject(null, soResult); // add top node
        	 	storeResult(resultObject, soResult); 
        	 	
        	 	//KvmSerializable ks = (KvmSerializable) Envelope.bodyIn;
        	 	
        	 	// CastException :=> 
        	 	// Vector<Passport> vPasports = (Vector<Passport>)Envelope.getResponse() ;  

        	 	//if(vPasports != null)
        	 	//{
        	 	//    for(Passport curEvent : vPasports)
        	 	//    {
        	 	        // Log.v("BILGICIFTLIGI", curEvent.toString());
        	 	//    }
        	 	//}
        	 	
                //for(int i=0;i<ks.getPropertyCount();i++)
                // {
                //    ks.getProperty(i); //if complex type is present then you can cast this to SoapObject and if primitive type is returned you can use toString() to get actuall value.
                // }

        	 	
            	SoapObject response = (SoapObject) Envelope.getResponse();
            	
      	 	
        	 	
               
         	   
                return response;
            }
         catch(Exception e)
         {
             e.printStackTrace();
             
         }
         return null;
         
         
         
    }
    
    
}

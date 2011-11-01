/*
 * http://pantestmb.blogspot.com/2011/02/web-service-call-with-string-as.html
 * http://stackoverflow.com/questions/2717220/call-webservice-from-android-using-ksoap-simply-returning-error-string
 * http://w3schools.com/webservices/tempconvert.asmx
 * http://w3schools.com/webservices/tempconvert.asmx?op=CelsiusToFahrenheit
 */

package net.learn2develop.AndroidViews;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListActivity; 

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml; 
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONStringer;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.InputStream;
import org.json.JSONArray;
import java.io.BufferedReader;

public class GetPersons01 extends ListActivity {
	
	private final static String SERVICE_URI = "http://192.168.61.3/RestServicePost/RestServiceImpl.svc";
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listview);
        
        HttpGet request = new HttpGet(SERVICE_URI + "/json/getallpersons");       
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
 
        DefaultHttpClient httpClient = new DefaultHttpClient();

        JSONArray jsaPersons = null ;
        String theString = new String("");
        
        try {
        	HttpResponse response = httpClient.execute(request);
        	HttpEntity responseEntity = response.getEntity();
        	// Read response data into buffer
        	char[] buffer = new char[(int)responseEntity.getContentLength()];
        	InputStream stream = responseEntity.getContent();
        	// InputStreamReader reader = new InputStreamReader(stream);
        	BufferedReader reader = new BufferedReader(
					new InputStreamReader(stream));
        	
        	// reader.read(buffer);
        	
        	
        	// jsaPersons = new JSONArray(new String(buffer));
        	StringBuilder builder = new StringBuilder();
        	String line;
        	while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
        	stream.close();
        	theString = builder.toString();
 			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        


			// Toast.makeText(this, jsaPersons.getString(1) + "\n", Toast.LENGTH_LONG).show() ;
        	Toast.makeText(this, theString + "\n", Toast.LENGTH_LONG).show() ;

    }
    
}



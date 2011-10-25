/*
 * http://stackoverflow.com/questions/2559948/android-sending-xml-via-http-post-soap
 * http://www.vogella.de/articles/ApacheHttpClient/article.html
 * http://stackoverflow.com/questions/2559948/android-sending-xml-via-http-post-soap
 * http://code.google.com/p/myspace-android/source/browse/trunk/myspace-android/src/com/msdemo/android/myspaceid/MySpaceRESTClient.java
 */

package net.learn2develop.AndroidViews;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
// import org.json.JSONObject;
import org.json.JSONStringer;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class SavePerson extends Activity {

	private final static String SERVICE_URI = "http://192.168.61.3/RestServicePost/RestServiceImpl.svc";

	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        setContentView(R.layout.main);
	        // String plate = new String("test");
	        // POST request to <service>
            HttpPost request = new HttpPost(SERVICE_URI + "/json/adduser");
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
 
            try {
            // Build JSON string
            JSONStringer vehicle = new JSONStringer()
                .object()
                    .key("requestdata")
                        .object()
                            .key("details").value("barack|bobama|bobaba@house.gov|white")
                        .endObject()
                    .endObject();
            
            StringEntity entity = new StringEntity(vehicle.toString());
 
            request.setEntity(entity);
 
            // Send request to WCF service
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
            
            }catch (Exception e) {
                // TODO: handle exception
            } 
            
            Toast.makeText(this, "Gata: " + "\n", Toast.LENGTH_LONG).show() ;
            // Log.d("WebInvoke", "Saving : " + response.getStatusLine().getStatusCode());
	
	}    
}

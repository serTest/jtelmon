/*
 * http://bimbim.in/post/2010/10/08/Android-Calling-Web-Service-with-complex-types.aspx
 */

package bimbimin.android.webservice.client;

import bimbimin.android.webservice.dto.Person;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        ServiceCall call = new ServiceCall();

        String Result1 = call.CallHelloWorld();
        Toast.makeText(this, "Result: " + Result1 + "\n", Toast.LENGTH_LONG).show() ;
        
        Person out = call.CallGetSingle();
        String Result2 = out.get_name();
        Toast.makeText(this, "Result: " + Result2 + "\n", Toast.LENGTH_LONG).show(); 
        Boolean res = call.CallSetValue(out);
    }
}
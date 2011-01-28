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
        setContentView(R.layout.main);
        ServiceCall call = new ServiceCall();
        String Result1 = call.CallHelloWorld();
        Toast.makeText(this, "Result: " + Result1 + "\n", Toast.LENGTH_LONG).show() ;
        Person out = call.CallGetSingle();
        String Result2 = new String("bau"); 
        Result2 = out.get_name();
        // Boolean res = call.CallSetValue(out);
        Toast.makeText(this, "Result: " + Result2 + "\n", Toast.LENGTH_LONG).show() ;
    }
    
    public void DisplayTitle()
    {
        Toast.makeText(this,
                "id: " + getString(0) + "\n" +
                "ISBN: " + getString(1) + "\n" +
                "TITLE: " + getString(2) + "\n" +
                "PUBLISHER:  " + getString(3),
                Toast.LENGTH_LONG).show();       
    }       
}

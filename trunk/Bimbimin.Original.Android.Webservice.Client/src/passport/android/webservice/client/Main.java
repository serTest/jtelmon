package passport.android.webservice.client;

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
        Passport out = call.CallGetSingle();
        String Result2 = new String("bau"); 
        Result2 = out.get_name();
        Toast.makeText(this, "Result: " + Result2 + "\n", Toast.LENGTH_LONG).show() ;
        Boolean res = call.CallSetValue(out);
    }
}
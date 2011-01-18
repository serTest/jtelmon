package teste.vederi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class teste extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //---load the BasicViewsExample activity---
        // startActivity(new Intent(this, Vederi.class));
        
        // startActivity(new Intent(this, ListviewExample.class));
        
        // startActivity(new Intent(this, CustomListView.class));
        
        startActivity(new Intent(this, PgJDBCAndroid.class));
    }
}
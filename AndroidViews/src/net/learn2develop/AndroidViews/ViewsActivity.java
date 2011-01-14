package net.learn2develop.AndroidViews;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
 
public class ViewsActivity extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
 
        //---load the BasicViewsExample activity---
        startActivity(new Intent(this, BasicViewsExample.class));
 
    }
}
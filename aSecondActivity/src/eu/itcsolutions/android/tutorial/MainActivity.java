
// www.itcsolutions.eu/2011/08/31/android-tutorial-how-to-create-and-display-a-new-form-window-or-activity/comment-page-1

package eu.itcsolutions.android.tutorial;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
// import android.view.View.OnClickListener; 

public class MainActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
      //get the Button reference
    	//Button is a subclass of View
    	//buttonClick if from main.xml "@+id/buttonClick"
            View v = findViewById(R.id.buttonClick);
    	//set event listener
            v.setOnClickListener(this);
            
    }
    
    @Override
	public void onClick(View arg0) {
    	if(arg0.getId() == R.id.buttonClick){
    		//define a new Intent for the second Activity
    		Intent intent = new Intent(this,SecondActivity.class);
     
    		//start the second Activity
    		this.startActivity(intent);
    	}
	}
}

/*
 * 
 *  http://www.devx.com/wireless/Article/40842/1954
 *  http://developer.android.com/sdk/installing.html
 *  http://developer.android.com/resources/tutorials/hello-world.html
 *  https://code.google.com/p/jtelmon/source/browse/trunk/Database1Android/src/net/learn2develop/Databases/DatabaseActivity.java
 *    
 */

package net.learn2develop.Databases;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

public class DatabaseActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DBAdapter db = new DBAdapter(this);

        //---get all titles---
        db.open();
        // db.
       
        long id;

        if (db.deleteTitle(2))
            Toast.makeText(this, "Delete successful.",
                Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Delete failed.",
                Toast.LENGTH_LONG).show();           
     
        id = db.insertTitle(
                "2011010101",
                "La Multi Ani 2011 ! ",
                "JL Soft");             
       
       
        Cursor c = db.getAllTitles();
       
        if (c.moveToFirst())
        {
            do {         
                DisplayTitle(c);
            } while (c.moveToNext());
        }
        db.close();             
    }
   
    public void DisplayTitle(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                "ISBN: " + c.getString(1) + "\n" +
                "TITLE: " + c.getString(2) + "\n" +
                "PUBLISHER:  " + c.getString(3),
                Toast.LENGTH_LONG).show();       
    }       
}

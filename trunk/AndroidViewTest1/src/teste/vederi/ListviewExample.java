/*
 * http://www.androidpeople.com/android-listview-example/
 */
package teste.vederi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListviewExample extends Activity
{
private ListView lv1;
private String lv_arr[]={"Android","iPhone","BlackBerry","AndroidPeople"};
@Override
public void onCreate(Bundle savedInstanceState)
{
super.onCreate(savedInstanceState);
setContentView(R.layout.listview01);
lv1=(ListView)findViewById(R.id.ListView01);
// By using setAdpater method in _listview_ we an add string array in list.
lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , lv_arr));
}
}


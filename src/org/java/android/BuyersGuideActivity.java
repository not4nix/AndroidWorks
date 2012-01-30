package org.java.android;

import java.net.URL;
import java.util.ArrayList;

import org.java.android.adapter.JsonArrayAdapter;
import org.java.android.entities.JsonData;
import org.java.android.task.JsonTask;
import org.java.android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class BuyersGuideActivity extends Activity{
	private static final String id = "id";
	
    private static final String name = "name";
    
    private static final String url = "url";
    
    private static final String icon = "icon";
    
    public static ProgressBar progressBar;
    
    public static ArrayList<JsonData> data;
    
    private static ListView listView;
    
    static JsonArrayAdapter adapter;
    
    
    private static Object object;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView)findViewById(R.id.listView1);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        object = this;
        progressBar.setProgress(0);
        new JsonTask().execute();
        listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {
				Object object = listView.getItemAtPosition(position);
				JsonData jsonData = (JsonData) object;
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(jsonData.getURL()));
				startActivity(intent);
			}
        });
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_menu,menu);
		return true;
	}
    
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.Settings:
			Intent intent = new Intent(BuyersGuideActivity.this, Preferences.class);
			startActivity(intent);
			Toast.makeText(BuyersGuideActivity.this,"Hello",Toast.LENGTH_LONG).show();
			return true;
		case R.id.comments:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	public static void fillData(){
    	BuyersGuideActivity.progressBar.setVisibility(ProgressBar.GONE);
    	BuyersGuideActivity.adapter = new JsonArrayAdapter((Context)object,R.layout.list_item,data);
    	BuyersGuideActivity.listView.setAdapter(BuyersGuideActivity.adapter);
    }
}

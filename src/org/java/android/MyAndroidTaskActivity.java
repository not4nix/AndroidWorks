package org.java.android;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAndroidTaskActivity extends ListActivity {
	private ArrayList<JsonEntity> entity = new ArrayList<JsonEntity>();
    /** Called when the activity is first created. */
	/** OO */
	/****ooooo*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new MyTask().execute();
    }
    
    private class MyTask extends AsyncTask<Void,Void,Void>{
    	private ProgressDialog dialog;

    	@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(MyAndroidTaskActivity.this, "", "Loading. Please wait...", true);
		}
    	
    	@Override
		protected Void doInBackground(Void... arg0) {
			try {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet("http://buyersguide.caranddriver.com/api/feed/?mode=json&q=make");
				HttpResponse resp = client.execute(get);
				if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					String result = EntityUtils.toString(resp.getEntity());
					JSONObject root = new JSONObject(result); 
					JSONArray sessions = root.getJSONArray("results"); 
					for (int i = 0; i < sessions.length(); i++){
						JSONObject session = sessions.getJSONObject(i);
					}
					JsonEntity en = new JsonEntity();
					en.id = sessions.getString(Integer.parseInt("id"));
					en.name = sessions.getString(Integer.parseInt("name"));
					en.url = sessions.getString(Integer.parseInt("url"));
					en.make_icon = sessions.getString(Integer.parseInt("make_icon"));
					entity.add(en);
				}
			}
			catch(Exception ex){
				Log.e("MyAndroidTaskActivity", "Error loading JSON", ex);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			dialog.dismiss();
			setListAdapter(new JsonAdapter(MyAndroidTaskActivity.this, R.layout.list_item, entity));
		}
    }
    
    private class JsonAdapter extends ArrayAdapter<JsonEntity> {
		private ArrayList<JsonEntity> entities;
		
		public JsonAdapter(Context context, int textViewResourceId,ArrayList<JsonEntity> items) {
			super(context, textViewResourceId,items);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null){
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.list_item, null);
			}
			JsonEntity o = entities.get(position);
			TextView tt = (TextView) v.findViewById(R.id.toptext);
			TextView bt = (TextView) v.findViewById(R.id.bottomtext);
			tt.setText(o.name);
			bt.setText(o.url);
			bt.setText(o.id);
			bt.setText(o.make_icon);
			return v;
		}
    }
}

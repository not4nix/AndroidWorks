package org.java.android;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.java.android.adapters.JsonAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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

public class CarsListActivity extends ListActivity{
	private ArrayList<JsonData> data = new ArrayList<JsonData>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_items);
		new JsonTask().execute();
	}
	
	private class JsonTask extends AsyncTask<String,Void,ArrayList<JsonData>>{
		private ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(CarsListActivity.this, "", "Loading. Please wait...", true);
		}

		@Override
		protected ArrayList<JsonData> doInBackground(String... arg0) {
			try {
				String url = "http://buyersguide.caranddriver.com/api/feed/?mode=json&q=make";
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse resp = client.execute(get);
				if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					String result = EntityUtils.toString(resp.getEntity());
					JSONObject root = new JSONObject(result);
					JSONArray sessions = root.getJSONArray("results"); 
					for (int i = 0; i < sessions.length(); i++){
						JSONObject session = sessions.getJSONObject(i);
						JsonData jdata = new JsonData();
						jdata.id = session.getString("car id");
						jdata.name = session.getString("car name");
						jdata.url = session.getString("car url");
						jdata.make_icon = session.getString("car icon");
						data.add(jdata);
					}
				}
			}
			catch(IOException ex){
				Log.e("CarsListActivity", "Error input-output", ex);
			} 
			catch (JSONException ex) {
				Log.e("CarsListActivity", "Error JSON loading", ex);
			}
			return data;
		}

		@Override
		protected void onPostExecute(ArrayList<JsonData> result) {
			dialog.dismiss();
			setListAdapter(new JsonAdapter(CarsListActivity.this, R.layout.list_items, data));
		}
	}
	
	private class JsonAdapter extends ArrayAdapter<JsonData>{
		private ArrayList<JsonData> data;
		
		public JsonAdapter(Context context, int textViewResourceId,
				ArrayList<JsonData> items) {
			super(context, textViewResourceId, items);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null){
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.list_items, null);
			}
			JsonData o = data.get(position);
			return v;
		}
	}
}

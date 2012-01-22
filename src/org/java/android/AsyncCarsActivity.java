package org.java.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.java.android.adapter.MySimpleAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AsyncCarsActivity extends Activity {
	//constants
	private static final String id = "carId";
	private static final String name = "carName";
	private static final String url = "carUrl";
	ListView lv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new Task().execute();
    }
    
    private class Task extends AsyncTask<Void,Void,ArrayList<HashMap<String, Object>>>{
    	private ProgressDialog dialog;
    	
    	@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(AsyncCarsActivity.this, "","Fetching JSON, please wait..",true);
		}
    	
		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(Void... arg0) {
			try {
				String url = "http://buyersguide.caranddriver.com/api/feed/?mode=json&q=make";
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse resp = client.execute(get);
				String result = "";
				InputStream in = resp.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder str = new StringBuilder();
				String string = null;
				while((string = reader.readLine()) != null){
	                str.append(string + "\n");
	            }
	            in.close();
	            result = str.toString();
				String s = "";
				JSONArray entries = new JSONArray(s.substring(s.indexOf("[")));
				ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
				Map<Integer,JsonData> tmap = new TreeMap<Integer,JsonData>();
				HashMap<String, Object> hash = new HashMap<String, Object>();
				for (int i=0;i<entries.length();i++){
					JSONObject jobject = entries.getJSONObject(i);
					tmap.put(jobject.getInt("id"), new JsonData(jobject.getString("name"),jobject.getString("url"),R.drawable.pface));
				}
				for(Entry<Integer, JsonData> entry : tmap.entrySet()){
					Integer key = entry.getKey();
		    		JsonData value = entry.getValue();
		    		hash.put(id, key);
		    		hash.put(name, value.getName());
		    		hash.put(url, value.getURL());
		      	    list.add(hash);
				}
			}
			catch(IOException ex){
				Log.e("AsyncCarsActivity","IO Exception occured",ex);
			} 
			catch (JSONException ex) {
				Log.e("AsyncCarsActivity","JSONException occured",ex);
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, Object>> list) {
			ListAdapter adapter = new MySimpleAdapter(AsyncCarsActivity.this, list, R.layout.list_item,
					new String[]{id,name,url},
					new int[]{R.id.carId,R.id.carName,R.id.carUrl});
			lv.setAdapter(adapter);
			lv = (ListView)findViewById(R.id.listView1);
			dialog.dismiss();
		}
    }
}

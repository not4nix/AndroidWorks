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

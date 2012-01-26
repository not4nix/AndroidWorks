package org.java.android.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.java.android.BuyersGuideActivity;
import org.java.android.entities.JsonData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class JsonTask extends AsyncTask<Void,Integer,ArrayList<JsonData>>{

	
	@Override
	protected ArrayList<JsonData> doInBackground(Void... arg0) {
		String getJson = getJSON();
		ArrayList<JsonData> jsonData = new ArrayList<JsonData>();
		try {
			JSONArray entries = new JSONArray(getJson.substring(getJson.indexOf("[")));
			TreeMap<Integer,JsonData> tree = new TreeMap<Integer,JsonData>();
			for (int i=0;i<entries.length();i++){
				JSONObject object = entries.getJSONObject(i);
				String urlIcon = object.getString("make_icon");
				Bitmap bmp = getImageFromSource(urlIcon);
				tree.put(object.getInt("id"), new JsonData(object.getString("id"),object.getString("name"),object.getString("url"),bmp));
			}
			for(Entry<Integer, JsonData> entry : tree.entrySet()){
				Integer key = entry.getKey();
				JsonData value = entry.getValue();
				jsonData.add(value);
			}
		} catch (Exception e) {
			
		}
		return jsonData;
		
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		BuyersGuideActivity.progressBar.setProgress(values[0]);
	}

	@Override
	protected void onPostExecute(ArrayList<JsonData> result) {
		super.onPostExecute(result);
		BuyersGuideActivity.data = result;
		BuyersGuideActivity.fillData();
	}
	
	protected String getJSON() {
		String json="";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://buyersguide.caranddriver.com/api/feed/?mode=json&q=make");
		try {
			HttpResponse response = client.execute(request);
			json = getJSONFromSource(response);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return json;
	}
	
	protected String getJSONFromSource(HttpResponse response){
		String jsonResult = "";
		try {
			InputStream in = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String l = null;
			while((l = reader.readLine()) != null){
                sb.append(l + "\n");
            }
			in.close();
            jsonResult = sb.toString();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResult;
	}
	
	protected Bitmap getImageFromSource(String url){
		Bitmap bitmap = null;
		URL uri = null;
		Drawable drawable = null;
		try {
			uri = new URL(url);
			HttpURLConnection con = (HttpURLConnection)uri.openConnection();
			con.setDoInput(true);
			con.connect();
			InputStream is = con.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			drawable = new BitmapDrawable(bitmap);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}
}

package org.java.android.adapter;

import java.util.ArrayList;

import org.java.android.entities.JsonData;

import org.java.android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class JsonArrayAdapter extends ArrayAdapter{

	private Context context_;
	private ArrayList<JsonData> data_;
	@SuppressWarnings("unchecked")
	public JsonArrayAdapter(Context context,
			int ViewResourceId, ArrayList<JsonData> objects) {
		super(context, ViewResourceId, objects);
		context_ = context;
		data_ = objects;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null){
			LayoutInflater inflater = (LayoutInflater)context_.getSystemService
	    		      (Context.LAYOUT_INFLATER_SERVICE);
	        view = inflater.inflate(R.layout.list_item, null);
		}
		JsonData json = data_.get(position);
	    if (json != null) {
	              TextView id = (TextView) view.findViewById(R.id.carId);
	              TextView name =  (TextView) view.findViewById(R.id.carName);
	              TextView url = (TextView) view.findViewById(R.id.carUrl);
	              ImageView icon = (ImageView) view.findViewById(R.id.carIcon);
	              id.setText(json.getID());
	              name.setText(json.getName());
	              url.setText(json.getURL());
	              icon.setImageBitmap(json.getImage());
	    }
		return view;
	}
}

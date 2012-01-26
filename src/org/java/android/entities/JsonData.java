package org.java.android.entities;

import android.graphics.Bitmap;

public class JsonData {
	private String carId_;
	private String carName_;
	private String carURL_;
	private Bitmap carImage_;
	
	public JsonData(String id, String name, String url, Bitmap bmp){
		carId_ = id;
		carName_ = name;
		carURL_ = url;
		carImage_ = bmp;
	}
	
	public String getID(){
		return carId_;
	}
	
	public String getName(){
		return carName_;
	}
	
	public String getURL(){
		return carURL_;
	}
	
	public Bitmap getImage(){
		return carImage_;
	}
}

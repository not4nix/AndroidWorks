package org.java.android;

public class JsonData {
	private String name;
	private String url;
	private int icon;
	
	public JsonData(String name, String url, int icon){
		this.name = name;
		this.url = url;
		this.icon = icon;
	}
	
	public String getName(){
		return name;
	}
	
	public String getURL(){
		return url;
	}
	
	public int getIcon(){
		return icon;
	}
}

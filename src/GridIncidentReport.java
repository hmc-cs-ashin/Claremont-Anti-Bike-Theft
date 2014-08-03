package com.example.claremontantibiketheft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridIncidentReport extends BaseAdapter{
	private Context context;
	public SharedPreferences prefs; 
	public SharedPreferences.Editor editor;
	public static final String PREFS_NAME = "MyPrefsFile";
	public HashSet<String> uris;
	public Iterator<String> iter;
	public ArrayList<String> str = new ArrayList<String>();
	
	//initializes global variables
	public GridIncidentReport (Context c) {
		context = c;
		prefs = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = prefs.edit();
		uris = (HashSet<String>) prefs.getStringSet("key2", null);
		
		if (uris != null) {
			iter = uris.iterator();
			
			while (iter.hasNext()) {
				str.add(iter.next());
			}
		}
	}
	
	//premade
	public int getCount() {
		if (uris == null)
			return 0;
		return uris.size();
    }
 
	//premade
    public Object getItem(int position) {
        return null;
    }
	
    //premade
	public long getItemId(int position) {
		return 0;
	}
	
	//displays pictures in gridview for report
	public View getView(int position, View view, ViewGroup parent) {
		ImageView iview;
		
		if (view == null) {
			iview = new ImageView(context);
			iview.setLayoutParams(new GridView.LayoutParams(150,200));
			iview.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iview.setPadding(5, 5, 5, 5);
		} else {
			iview = (ImageView) view;	
		}
		
		iview.setImageURI(Uri.parse(str.get(position)));
		
		return iview;
	}
}

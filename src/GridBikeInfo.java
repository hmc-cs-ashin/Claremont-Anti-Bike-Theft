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

public class GridBikeInfo extends BaseAdapter{
	
	private Context context;
	public SharedPreferences prefs; 
	public SharedPreferences.Editor editor;
	public static final String PREFS_NAME = "MyPrefsFile";
	public HashSet<String> uris;
	public Iterator<String> iter;
	public static ArrayList<String> str = new ArrayList<String>();
	
	//initializes all global variables
	public GridBikeInfo(Context c) {
		context = c;
		prefs = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = prefs.edit();
		uris = (HashSet<String>) prefs.getStringSet("key", null);
		iter = uris.iterator();
		
		while (iter.hasNext()) {
			str.add(iter.next());
		}
	}
	
	//premade
	@Override
    public int getCount() {
        return uris.size();
    }
 
	//premade
    @Override
    public Object getItem(int position) {
        return null;
    }
	
    //premade
	public long getItemId(int position) {
		return 0;
	}
	
	/**
	 * displays pictures in gridview for bike info
	 */
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

package com.example.claremontantibiketheft;

import java.util.ArrayList; 
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class GridViewTesterIncident extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";
	public SharedPreferences prefs = null; 
	public SharedPreferences.Editor editor = null;
	public ImageAdapter imageAdapter;
	public ArrayList<String> imageUrls = new ArrayList<String>();
	public HashSet<String> uri = new HashSet<String>();
	public DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
	//setting up gridview for incident report to display images picked from android gallery
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.show_android_gallery);
	    
	    prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = prefs.edit();
	    imageAdapter = new ImageAdapter(this, imageUrls);
	    
	    final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
		final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
		
		Cursor imagecursor = managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
				null, orderBy + " DESC");
		
		for (int i = 0; i < imagecursor.getCount(); i++) {
			imagecursor.moveToPosition(i);
			int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
			imageUrls.add(imagecursor.getString(dataColumnIndex));
		}
		
		options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.stub_image)
			.showImageForEmptyUri(R.drawable.image_for_empty_url)
			.cacheInMemory()
			.cacheOnDisc()
			.build();
		
		GridView gridView = (GridView) findViewById(R.id.grid);
		gridView.setAdapter(imageAdapter);
	}
	
	//method when user presses button after choosing photos for incident report
	public void submit (View view) {
		ArrayList<String> selectedItems = imageAdapter.getCheckedItems();
		Set<String> set = new HashSet<String>();
		set.addAll(selectedItems);
		editor.putStringSet("key2", set);
		editor.commit();
		uri = (HashSet<String>) prefs.getStringSet("key2", null);
		
		if (prefs.getString("whichOne", "").equals("new")) {
			Intent intent = new Intent(this, NewReport.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, UnfinishedReport.class);
			startActivity(intent);
		}
	}
	
	//imageAdapter for gridview
	public class ImageAdapter extends BaseAdapter {
		
		ArrayList<String> mList;
		LayoutInflater mInflater;
		Context mContext;
		SparseBooleanArray mSparseBooleanArray;
		
		//initializes global vars
		public ImageAdapter(Context context, ArrayList<String> imageList) {
			// TODO Auto-generated constructor stub
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
			mSparseBooleanArray = new SparseBooleanArray();
			mList = new ArrayList<String>();
			this.mList = imageList;
		}
		
		//premade
		public ArrayList<String> getCheckedItems() {
			ArrayList<String> mTempArry = new ArrayList<String>();

			for(int i=0;i<mList.size();i++) {
				if(mSparseBooleanArray.get(i)) {
					mTempArry.add(mList.get(i));
				}
			}

			return mTempArry;
		}
		
		//premade
		@Override
		public int getCount() {
			return imageUrls.size();
		}

		//premade
		@Override
		public Object getItem(int position) {
			return null;
		}

		//premade
		@Override
		public long getItemId(int position) {
			return position;
		}

		//displays images
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null) {
				convertView = mInflater.inflate(R.layout.row_multiphoto_item, null);
			}

			CheckBox mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
			final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
			imageLoader.displayImage("file://"+imageUrls.get(position), imageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingComplete(Bitmap loadedImage) {
					Animation anim = AnimationUtils.loadAnimation(GridViewTesterIncident.this, R.anim.fade_in);
					imageView.setAnimation(anim);
					anim.start();
				}
			});
			
			mCheckBox.setTag(position);
			mCheckBox.setChecked(mSparseBooleanArray.get(position));
			mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);
			
			return convertView;
		}
		
		//listener for when photo is checked off and selected
		OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
			}
		};
	}
}

package com.example.claremontantibiketheft;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;  
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.claremontantibiketheft.TheFiveFrags.Section2Fragment.ImageAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class TheFiveFrags extends FragmentActivity{
	
	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
	Socket socketConnection = null;
	public Bike bike = null;
	public String name = "";
	public String streetAddress = "";
	public String emailAddress = "";
	public String phoneNum = "";
	public String fb = "";
	public String twitter = "";
	public String linkedin = "";
	public String googlePlus = "";
	public static final String PREFS_NAME = "MyPrefsFile";
	public SharedPreferences prefs = null; 
	public SharedPreferences.Editor editor = null;
	public ImageAdapter imageAdapter;
	public ArrayList<String> imageUrls = new ArrayList<String>();
	public Section2Fragment obj = new Section2Fragment();
	public HashSet<String> uri = new HashSet<String>();
	public static Button button;
	
	//premade
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//premade
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			Bundle args = null;
			
			if (position == 0) {
				fragment = new Section1Fragment();
				args = new Bundle();
				args.putInt(Section1Fragment.ARG_SECTION_NUMBER, position + 1);
			} else if (position == 1) {
				fragment = new Section2Fragment();
				 args= new Bundle();				
				 args.putInt(Section2Fragment.ARG_SECTION_NUMBER, position + 1);
			} else if (position == 2) {
				fragment = new Section3Fragment();
				args = new Bundle();
				args.putInt(Section3Fragment.ARG_SECTION_NUMBER, position + 1);
			} else if (position == 3) {
				fragment = new Section4Fragment();
				args = new Bundle();
				args.putInt(Section4Fragment.ARG_SECTION_NUMBER, position + 1);
			} else if (position == 4) {
				fragment = new Section5Fragment();
				args = new Bundle();
				args.putInt(Section5Fragment.ARG_SECTION_NUMBER, position + 1);
			}
			
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 5 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			case 4:
				return getString(R.string.title_section5).toUpperCase(l);
			}
			return null;
		}
	}
	
	/**
	 * displays the contact info to fill when the app is opened for the first time
	 * or goes to fragment 1 when app is opened after the first time
	 */
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
		prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = prefs.edit();
		
		if (firstrun || prefs.getString("name", "").equals("") || prefs.getString("streetAddress", "").equals("") || prefs.getString("emailAddress", "").equals("") || prefs.getString("phoneNum", "").equals("")){
	    	setContentView(R.layout.personal_info);
	    	editor.putBoolean("check", false);
	    	editor.putBoolean("report", false);
			editor.commit();
			
	    	if (firstrun) {
				EditText editText = (EditText) findViewById(R.id.name);
		    	name = editText.getText().toString();
		    	
		    	editText = (EditText) findViewById(R.id.street_address);
		    	streetAddress = editText.getText().toString();
		    	
		    	editText = (EditText) findViewById(R.id.email_address);
		    	emailAddress = editText.getText().toString();
		    	
		    	editText = (EditText) findViewById(R.id.phone_number);
		    	phoneNum = editText.getText().toString();
		    	
		    	editText = (EditText) findViewById(R.id.facebook);
		    	fb = editText.getText().toString();
		    	
		    	editText = (EditText) findViewById(R.id.twitter);
		    	twitter = editText.getText().toString();
		    	
		    	editText = (EditText) findViewById(R.id.google_plus);
		    	googlePlus = editText.getText().toString();
		    	
		    	editor.putString("name", name);
		    	editor.putString("streetAddress", streetAddress);
		    	editor.putString("emailAddress", emailAddress);
		    	editor.putString("phoneNum", phoneNum);
		    	editor.putString("fb", fb);
		    	editor.putString("twitter", twitter);
		    	editor.putString("googlePlus", googlePlus);
				editor.commit();
			}
	    	
	    	EditText editText = (EditText) findViewById(R.id.name);
	    	editText.setText(prefs.getString("name", ""), TextView.BufferType.EDITABLE);
	    	
	    	editText = (EditText) findViewById(R.id.street_address);
	    	editText.setText(prefs.getString("streetAddress", ""), TextView.BufferType.EDITABLE);
	    	
	    	editText = (EditText) findViewById(R.id.email_address);
	    	editText.setText(prefs.getString("emailAddress", ""), TextView.BufferType.EDITABLE);
	    	
	    	editText = (EditText) findViewById(R.id.phone_number);
	    	editText.setText(prefs.getString("phoneNum", ""), TextView.BufferType.EDITABLE);
	    	
	    	editText = (EditText) findViewById(R.id.facebook);
	    	editText.setText(prefs.getString("fb", ""), TextView.BufferType.EDITABLE);
	    	
	    	editText = (EditText) findViewById(R.id.twitter);
	    	editText.setText(prefs.getString("twitter", ""), TextView.BufferType.EDITABLE);
	    	
	    	editText = (EditText) findViewById(R.id.google_plus);
	    	editText.setText(prefs.getString("googlePlus", ""), TextView.BufferType.EDITABLE);
	    	
	    	getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("firstrun", false).commit();
		} else {
			setContentView(R.layout.activity_main);
			
			prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
			editor = prefs.edit();
			imageAdapter = obj.new ImageAdapter(this, imageUrls);
			
			mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
	
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);
		}
	}
	
	/**
	 * submit contact info and verify everything is filled out correctly
	 * @param view
	 */
	public void onClick(View view) {
		EditText editText = (EditText) findViewById(R.id.name);
    	name = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.street_address);
    	streetAddress = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.email_address);
    	emailAddress = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.phone_number);
    	phoneNum = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.facebook);
    	fb = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.twitter);
    	twitter = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.google_plus);
    	googlePlus = editText.getText().toString();
    	
    	String first = "The following items have not been completed properly: ";
		String second = "";
		String message = "";
		
		if (name.equals("")) {
			second += "name, ";
		}
		
		if (streetAddress.equals("")) {
			second += "street address, ";
		}
	
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern ePattern = Pattern.compile(EMAIL_PATTERN);
		Matcher eMatch = ePattern.matcher(emailAddress);
		
		if (!eMatch.matches())
		{
			second += "email address, ";
		}
		
		String expression = "(\\d{3}-){1,2}\\d{4}";
	    CharSequence inputStr = phoneNum;
	    Pattern pattern = Pattern.compile(expression);
	    Matcher matcher = pattern.matcher(inputStr);
	    
	    if(!matcher.matches()){
	    	second += "phone number, ";
	    }
	    
	    //validate street address, URLs
	    
		message = first + second;
		
    	if (!second.equals("")) {
    		new AlertDialog.Builder(this).setTitle("Not Completed").setMessage(message.substring(0,message.length()-2)).show();
    	} else {
    		editor.putString("name", name);
	    	editor.putString("streetAddress", streetAddress);
	    	editor.putString("emailAddress", emailAddress);
	    	editor.putString("phoneNum", phoneNum);
	    	editor.putString("fb", fb);
	    	editor.putString("twitter", twitter);
	    	editor.putString("googlePlus", googlePlus);
			editor.commit();
			
			//send name and user object to server
			//send message "user" to server
			
    		Intent intent = new Intent(this, TheFiveFrags.class);
    		startActivity(intent);
    	}
	}

	/**
	 * displays fragment 1
	 * @author shinaushin
	 *
	 */
	public class Section1Fragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section1, container, false);
			return rootView;
		}
		
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			addListenerOnButton();
		}
		
		/**
		 * button listener on Options button
		 */
		public void addListenerOnButton() {
			button = (Button) findViewById(R.id.button1);  
	        button.setOnClickListener(new OnClickListener() {  
	        	@Override  
	        	public void onClick(View v) {  
	        		PopupMenu popup = new PopupMenu(getActivity(), button);  
	        		popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());  

	        		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
	        			public boolean onMenuItemClick(MenuItem item) {  
	        				if (item.getTitle().equals("Edit Contact Info")) {
	        					onClick2();
	        				} else if (item.getTitle().equals("View Bike Info")) {
	        					if (prefs.getBoolean("check", false) == true) {
	        						viewBike();
	        					} else {
	        						Toast.makeText(getApplicationContext(), "You have not registered a bike.", Toast.LENGTH_LONG).show();
	        					}
	        				} else if (item.getTitle().equals("Deregister Bike")) {
	        					if (prefs.getBoolean("check", true) == false) {
	        						Toast.makeText(getApplicationContext(), "You have not registered a bike.", Toast.LENGTH_LONG).show();
	        					} else {
	        						String popup = "Are you sure you want to deregister bike? All info associated with bike will be deleted.";
	        						
	        						DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	        						    @Override
	        						    public void onClick(DialogInterface dialog, int which) {
	        						        switch (which){
	        						        //erases all info on bike
	        						        case DialogInterface.BUTTON_POSITIVE:
	        						        	editor.putString("description", "");
	        		        					editor.putString("serial", "");
	        		        					editor.putStringSet("key", new HashSet<String>());
	        		        					editor.putBoolean("check",  false);
	        		        					editor.commit();
	        		        					
	        		        					//sends serial number to server to remove bike from hashmap
	        		        					try {
	        		        						socketConnection = new Socket("192.168.1.66", 11111);
	        		        				    	
	        		        				    	OutputStream om = socketConnection.getOutputStream();
	        		        			            OutputStreamWriter ow = new OutputStreamWriter(om);
	        		        			            BufferedWriter b = new BufferedWriter(ow);
	        		        			            b.write("remove bike");
	        		        			            b.flush();
	        		        			            
	        		        			            socketConnection.close();
	        		        					} catch (UnknownHostException e) {
	        		        						e.printStackTrace();
	        		        					} catch (IOException e) {
	        		        						e.printStackTrace();
	        		        					}
	        		        					
	        		        					Toast.makeText(getApplicationContext(), "Bike has been deregistered.", Toast.LENGTH_LONG).show();
	        						            break;

	        						        case DialogInterface.BUTTON_NEGATIVE:
	        						            break;
	        						        }
	        						    }
	        						};

	        						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        						builder.setMessage(popup).setPositiveButton("Yes", dialogClickListener)
	        						    .setNegativeButton("No", dialogClickListener).show();
	        					}
	        				} else if (item.getTitle().equals("Cancel Current Report")) {
	        					if (prefs.getBoolean("report", true) == false) {
		        					Toast.makeText(getApplicationContext(), "There is no current report.", Toast.LENGTH_LONG).show();
	        					} else {
	        						String popup = "Has your bike been recovered";
	        						
	        						DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	        						    @Override
	        						    public void onClick(DialogInterface dialog, int which) {
	        						        switch (which){
	        						        case DialogInterface.BUTTON_POSITIVE:
	        						        	
	        						        	//changes isStolen = false for that bike
	        						        	try {
	        		        						socketConnection = new Socket("192.168.1.66", 11111);
	        		        				    	
	        		        				    	OutputStream om = socketConnection.getOutputStream();
	        		        			            OutputStreamWriter ow = new OutputStreamWriter(om);
	        		        			            BufferedWriter b = new BufferedWriter(ow);
	        		        			            b.write("isStolen is false");
	        		        			            b.flush();
	        		        			            
	        		        			            socketConnection.close();
	        		        					} catch (UnknownHostException e) {
	        		        						e.printStackTrace();
	        		        					} catch (IOException e) {
	        		        						e.printStackTrace();
	        		        					}
	        						        	
	        						        	Toast.makeText(getApplicationContext(), "Great to hear! We will cancel your report now.", Toast.LENGTH_LONG).show();
	        	        						editor.putBoolean("report", false);
	        	        						editor.commit();
	        						        	break;

	        						        case DialogInterface.BUTTON_NEGATIVE:
	        						        	
	        						        	String popup = "Do you still want to cancel your report?";
	        	        						
	        	        						DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	        	        						    @Override
	        	        						    public void onClick(DialogInterface dialog, int which) {
	        	        						        switch (which){
	        	        						        
	        	        						        //cleans out report to restart
	        	        						        case DialogInterface.BUTTON_POSITIVE:
	        	        						        	Toast.makeText(getApplicationContext(), "Report has been cancelled.", Toast.LENGTH_LONG).show();
	    	        						        		editor.putBoolean("report", false);
	    	        						        		editor.putString("reportNum", "");
	    	        						        		editor.putString("policeEmail", "");
	    	        						        		editor.putString("didLock", "");
	    	        						        		editor.putString("lockBrand",  "");
	    	        						        		editor.putString("bikeLoc", "");
	    	        						        		editor.putString("moreDescript",  "");
	    	        						        		editor.putInt("hour",  0);
	    	        						        		editor.putInt("minute", 0);
	    	        						        		editor.putInt("year",  2014);
	    	        						        		editor.putInt("month", 0);
	    	        						        		editor.putInt("day",  1);
	    	        						        		editor.commit();
	        	        						        	break;

	        	        						        case DialogInterface.BUTTON_NEGATIVE:
	        	        						            break;
	        	        						        }
	        	        						    }
	        	        						};

	        	        						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        	        						builder.setMessage(popup).setPositiveButton("Yes", dialogClickListener)
	        	        						    .setNegativeButton("No", dialogClickListener).show();
	    	        							
	        						            break;
	        						        }
	        						    }
	        						};

	        						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        						builder.setMessage(popup).setPositiveButton("Yes", dialogClickListener)
	        						    .setNegativeButton("No", dialogClickListener).show();
	        						
	        						editor.commit();
	        					}
	        				}
	        				return true;  
	        			}
	        		});  
	        		popup.show(); 
	        	}  
	        });
		}
	}
	
	/**
	 * goes to contact info layout and allows user to edit it
	 */
	public void onClick2() { 
		Intent intent = new Intent(this, EditContactInfo.class);
		startActivity(intent);
	}
	
	/**
	 * displays Bike info to let user view it
	 */
	public void viewBike () {
		Intent intent = new Intent(this, GridViewTesterBike.class);
		startActivity(intent);
	}
	
	/**
	 * method to create new report
	 * @param view
	 */
	public void report(View view) {
		if (prefs.getBoolean("check",  true) == false) {
			Toast.makeText(getApplicationContext(), "Cannot report. No bike registered.", Toast.LENGTH_LONG).show();
		} else if (prefs.getBoolean("report", false) == true) {
			Toast.makeText(getApplicationContext(), "Another report is currently in progress.\nPlease cancel current report to make a new report.\nOr click 'Go To Current Report' to continue current one.", Toast.LENGTH_LONG).show();
		} else {
			editor.putStringSet("key2", null);
			editor.putString("reportNum", "");
			editor.putString("policeEmail", "");
			editor.putString("didLock", "");
			editor.putString("lockBrand",  "");
			editor.putString("bikeLoc", "");
			editor.putString("moreDescript",  "");
			editor.putInt("hour",  0);
			editor.putInt("minute", 0);
			editor.putInt("year",  2014);
			editor.putInt("month", 0);
			editor.putInt("day",  1);
			editor.commit();
			
			GPS gps = new GPS(getBaseContext());

			// check if GPS enabled		
	        if(gps.canGetLocation()){
	        	
	        	float latitude = (float) gps.getLatitude();
	        	float longitude = (float) gps.getLongitude();
	        	
	        	editor.putFloat("lat", latitude);
	        	editor.putFloat("lat", longitude);
	        	editor.commit();
	        	
	        	//send serial num, latitude/longitude to server
	        	Long[] array = {prefs.getLong("lat", 0), prefs.getLong("long", 0) };
	        	try {
	        		socketConnection = new Socket("192.168.1.66", 11111);
			    	
			    	OutputStream om = socketConnection.getOutputStream();
		            OutputStreamWriter ow = new OutputStreamWriter(om);
		            BufferedWriter b = new BufferedWriter(ow);
		            b.write("report ping");
		            b.flush();
		            
		            ObjectOutputStream clientOutputStream = new ObjectOutputStream(socketConnection.getOutputStream());
			    	clientOutputStream.writeObject(array);
			    	
			    	socketConnection.close();
	        	} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        	
	        }else{
	        	// Ask user to enable GPS/network in settings
	        	gps.showSettingsAlert();
	        }  
	        
	        //makes isStolen = true for that bike
	        try {
		        socketConnection = new Socket("192.168.1.66", 11111);
		    	
		    	OutputStream om = socketConnection.getOutputStream();
	            OutputStreamWriter ow = new OutputStreamWriter(om);
	            BufferedWriter b = new BufferedWriter(ow);
	            b.write("new report, bike is stolen");
	            b.flush();
	            
	            socketConnection.close();
	        } catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	        Intent intent = new Intent(this, NewReport.class);
	        startActivity(intent);
		}
	}
	
	/**
	 * allows user to view current report
	 * @param view
	 */
	public void viewReport (View view) {
		if (prefs.getBoolean("report",  true) == false) {
			Toast.makeText(getApplicationContext(), "There is no report in progress.", Toast.LENGTH_LONG).show();
		} else {
			if (prefs.getBoolean("reportFinish", false) == true) {
				Intent intent = new Intent(this, FinishedReport.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent(this, UnfinishedReport.class);
				startActivity(intent);
			}
		}
	}
	
//------------------------------------------------------------------------------------------------//
	
	public class Section2Fragment extends Fragment {
		
		private DisplayImageOptions options;
		
		protected ImageLoader imageLoader = ImageLoader.getInstance();
		
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		//sets up fragment for user to put in bike info and submit
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
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
			
			GridView gridView = (GridView) findViewById(R.id.gridview);
			System.out.println(gridView);
			gridView.setAdapter(imageAdapter);
			
			addListenerOnButton();
		}
		
		//adds button listener on describing what photos the user should upload
		public void addListenerOnButton() {
			 
			ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
	 
			imageButton.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					String photo = "";
					photo += "1) A picture of you and your bike.";
					photo += "\n2) A picture of the bike by itself.";
					photo += "\n3) A picture of the bike's serial number.";
					photo += "\n4) A picture of the sales receipt.";
					photo += "\n\nFeel free to upload more photos after these four.";
					new AlertDialog.Builder(getActivity()).setTitle("The 4 Photos You Need to Upload").setMessage(photo).show();
				}
			});
		}
		
		//premade
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section2, container, false);
			return rootView;
		}
		
		//premade
		@Override
		public void onStop() {
			imageLoader.stop();
			super.onStop();
		}

		//imageAdapter for gridview
		public class ImageAdapter extends BaseAdapter {
			ArrayList<String> mList;
			LayoutInflater mInflater;
			Context mContext;
			SparseBooleanArray mSparseBooleanArray;
			
			public ImageAdapter(Context context, ArrayList<String> imageList) {
				// TODO Auto-generated constructor stub
				mContext = context;
				mInflater = LayoutInflater.from(mContext);
				mSparseBooleanArray = new SparseBooleanArray();
				mList = new ArrayList<String>();
				this.mList = imageList;
			}
			
			public ArrayList<String> getCheckedItems() {
				ArrayList<String> mTempArry = new ArrayList<String>();

				for(int i=0;i<mList.size();i++) {
					if(mSparseBooleanArray.get(i)) {
						mTempArry.add(mList.get(i));
					}
				}

				return mTempArry;
			}
			
			@Override
			public int getCount() {
				return imageUrls.size();
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}
			
			//displays pictures in gallery for user to click on
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
						Animation anim = AnimationUtils.loadAnimation(TheFiveFrags.this, R.anim.fade_in);
						imageView.setAnimation(anim);
						anim.start();
					}
				});
				
				mCheckBox.setTag(position);
				mCheckBox.setChecked(mSparseBooleanArray.get(position));
				mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);
				
				return convertView;
			}
			
			//check listener for when user checks on pictures to submit
			OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
				}
			};
		}
	}
	
	/**
	 * method for when user submits bike information and checks everything has been put in correctly
	 * @param v
	 */
	public void btnChoosePhotosClick(View v){
		EditText editText = (EditText) findViewById(R.id.serial);
		String serial = editText.getText().toString();
		boolean seri = serial=="";
		editor.putString("serial", serial);
		
		editText = (EditText) findViewById(R.id.description);
		String description = editText.getText().toString();
		boolean descript = description=="";
		editor.putString("description", description);
		
		editor.commit();
		
		ArrayList<String> selectedItems = imageAdapter.getCheckedItems();
		Set<String> set = new HashSet<String>();
		set.addAll(selectedItems);
		editor.putStringSet("key", set);
		editor.commit();
		uri = (HashSet<String>) prefs.getStringSet("key", null);
		String warning = "";
		
		if (seri) {
			warning += "Please fill in serial number.\n";
		}
		
		if (descript) {
			warning += "Please fill in the bike information section.\n";
		}
		
		if (selectedItems.size() < 4) {
			warning += "You must select at least 4 pictures. Please check photo requirements.";
		}
		
		if (selectedItems.size() > 6) {
			warning += "You can only select a maximum of 6 photos. Please check photo requirements.";
		}
		
		if (!warning.equals("")) {
			new AlertDialog.Builder(this).setTitle("Not Completed").setMessage(warning).show();
		} else {
			if (prefs.getBoolean("check",  true) == true) {
				Toast.makeText(getApplicationContext(), "A bike is already registered. Please deregister first.", Toast.LENGTH_LONG).show();
			} else {
				editor.putBoolean("check", true);
				editor.commit();
				
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
				
				Intent intent = new Intent(TheFiveFrags.this, GridViewTesterBike.class);
				startActivity(intent);
			}
		}
	}
	
	/**
	 * sends bike object and bike photos to server to store
	 * @author shinaushin
	 *
	 */
	private class SendMessage extends AsyncTask<Void, Void, Void> {
		 
		@Override
		protected Void doInBackground(Void... params) {
			try {
				bike = new Bike (prefs.getString("serial", ""), prefs.getString("description", ""), 0.0, 0.0);
		    	//add time stamp var
				
		    	socketConnection = new Socket("192.168.1.66", 11111);
		    	
		    	OutputStream om = socketConnection.getOutputStream();
	            OutputStreamWriter ow = new OutputStreamWriter(om);
	            BufferedWriter b = new BufferedWriter(ow);
	            b.write("bike object and photos incoming");
	            b.flush();
		    	
		    	//sends bike object
		    	ObjectOutputStream clientOutputStream = new ObjectOutputStream(socketConnection.getOutputStream());
		    	clientOutputStream.writeObject(bike); //check for duplicate on server side
		    	
		    	//sends number of photos to be saved
		    	OutputStream osm = socketConnection.getOutputStream();
	            OutputStreamWriter osw = new OutputStreamWriter(osm);
	            BufferedWriter bw = new BufferedWriter(osw);
	 
	            String sendMessage = GridBikeInfo.str.size() + "\n";
	            bw.write(sendMessage);
	            bw.flush();
	            
	            socketConnection.close();
				
				//sends photos
		    	for (int a = 0; a < GridBikeInfo.str.size(); a++) {
		    		socketConnection = new Socket("192.168.1.66", 11111);
		    		File myFile = new File (GridBikeInfo.str.get(a));
			        byte [] mybytearray  = new byte [(int)myFile.length()];
			        FileInputStream fis = new FileInputStream(myFile);
			        BufferedInputStream bis = new BufferedInputStream(fis);
			        bis.read(mybytearray,0,mybytearray.length);
			        OutputStream os = socketConnection.getOutputStream();
			        os.write(mybytearray,0,mybytearray.length);
			        os.flush();
			        socketConnection.close();
		    	}  
 
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
 
	}
	
	//premade
	public HashSet<String> getUri() {
		return uri;	
	}

	//premade
	public void setUri(HashSet<String> uri) {
		this.uri = uri;
	}
	
//------------------------------------------------------------------------------------------------//
	
	//Serial number search function
	public static class Section3Fragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		//premade
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section3, container, false);
			return rootView;
		}
	}
	
	public void search(View view) {
		//send message to server "search has been made"
		//send name and object with ping location and time stamp and put in user hashmap
		//send serial number
		//receive bike object and display it on new activity
	}
	
//------------------------------------------------------------------------------------------------//
	
	public static class Section4Fragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section4, container, false);
			return rootView;
		}
	}
	
	public static class Section5Fragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section5, container, false);
			return rootView;
		}
	}

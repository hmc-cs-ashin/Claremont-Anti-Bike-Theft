package com.example.claremontantibiketheft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditContactInfo extends FragmentActivity{
	
	public String name = "";
	public String streetAddress = "";
	public String emailAddress = "";
	public String phoneNum = "";
	public String fb = "";
	public String twitter = "";
	public String linkedin = "";
	public String googlePlus = "";
	public SharedPreferences prefs = null; 
	public SharedPreferences.Editor editor = null;
	public static final String PREFS_NAME = "MyPrefsFile";
	
	/**
	 * displays submitted contact info and allows user to change it
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info);
		
		prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = prefs.edit();
		
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
	}
	
	/**
	 * method for when user submits newly adjusted contact info and checks to make sure everything
	 * has been entered correctly
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
    		//send message "edit contact info" to server
    		//retrieve name first entered and send it to server
    		//sends new name, contact info, and boolean isReportFinished to server, which puts it in hashmap
    		
    		editor.putString("name", name);
	    	editor.putString("streetAddress", streetAddress);
	    	editor.putString("emailAddress", emailAddress);
	    	editor.putString("phoneNum", phoneNum);
	    	editor.putString("fb", fb);
	    	editor.putString("twitter", twitter);
	    	editor.putString("googlePlus", googlePlus);
			editor.commit();
			
    		Intent intent = new Intent(this, TheFiveFrags.class);
    		startActivity(intent);
    	}
	}
}

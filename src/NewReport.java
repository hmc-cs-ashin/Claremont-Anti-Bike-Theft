package com.example.claremontantibiketheft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TimePicker;

public class NewReport extends FragmentActivity {
	
	private TextView tvDisplayDate;
	private Button btnChangeDate;
	private TextView tvDisplayTime;
	private Button btnChangeTime;
 
	private int hour;
	private int minute;
 
	private int year;
	private int month;
	private int day;
 
	static final int DATE_DIALOG_ID = 999;
	static final int TIME_DIALOG_ID = 998;
	
	public String reportNum = "";
	public String policeEmail = "";
	public String didLock = "";
	public String lockBrand = "";
	public String bikeLoc = "";
	public String moreDescript = "";
	
	public static final String PREFS_NAME = "MyPrefsFile";
	public SharedPreferences prefs = null; 
	public SharedPreferences.Editor editor = null;
	
	//creates layout for new report
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_report);
		
		prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		editor = prefs.edit();
		
		setDate();
		addListenerOnDate();
		
		setTime();
		addListenerOnTime();
		
		GridView grid = (GridView) findViewById(R.id.theft_grid);
		grid.setBackgroundColor(Color.LTGRAY);
		final GridIncidentReport ga = new GridIncidentReport(this);
	    grid.setAdapter(ga);
	    
	    EditText editText = (EditText) findViewById(R.id.police_report);
		editText.setText(prefs.getString("reportNum", ""), TextView.BufferType.EDITABLE);
		
		editText = (EditText) findViewById(R.id.police_email);
		editText.setText(prefs.getString("policeEmail", ""), TextView.BufferType.EDITABLE);
		
		editText = (EditText) findViewById(R.id.yes_no);
		editText.setText(prefs.getString("didLock", ""), TextView.BufferType.EDITABLE);
		
		editText = (EditText) findViewById(R.id.lock_brand);
		editText.setText(prefs.getString("lockBrand", ""), TextView.BufferType.EDITABLE);
		
		editText = (EditText) findViewById(R.id.bike_place);
		editText.setText(prefs.getString("bikeLoc", ""), TextView.BufferType.EDITABLE);
		
		editText = (EditText) findViewById(R.id.description);
		editText.setText(prefs.getString("moreDescript", ""), TextView.BufferType.EDITABLE);
	}
	
	//saves info already put in and goes to android gallery to let user select photos
	public void choose (View view) {
		EditText editText = (EditText) findViewById(R.id.police_report);
    	reportNum = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.police_email);
    	policeEmail = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.yes_no);
    	didLock = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.lock_brand);
    	lockBrand = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.bike_place);
    	bikeLoc = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.description);
    	moreDescript = editText.getText().toString();
    	
    	editor.putString("reportNum", reportNum);
    	editor.putString("policeEmail",  policeEmail);
		editor.putString("didLock", didLock);
		editor.putString("lockBrand",  lockBrand);
		editor.putString("bikeLoc", bikeLoc);
		editor.putString("moreDescript",  moreDescript);
		editor.putInt("hour",  hour);
		editor.putInt("minute", minute);
		editor.putInt("year",  year);
		editor.putInt("month", month);
		editor.putInt("day",  day);
		editor.putString("whichOne", "new");
		editor.commit();
    	
    	Intent intent = new Intent(this, GridViewTesterIncident.class);
		startActivity(intent);
	}
	
	// display date
	public void setDate() {
 
		tvDisplayDate = (TextView) findViewById(R.id.tvDate);
 
		year = prefs.getInt("year", 2014);
		month = prefs.getInt("month", 0);
		day = prefs.getInt("day", 1);
 
		// set date
		tvDisplayDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(month + 1).append("-").append(day).append("-")
			.append(year).append(" "));
	}
 
	//listener for date
	public void addListenerOnDate() {
 
		btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
 
		btnChangeDate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
	}
 
	//dialog for date picker and time picker
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker 
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		   
		case TIME_DIALOG_ID: 
			// set time picker 
			return new TimePickerDialog(this, 
                                       timePickerListener, hour, minute,false);
		}
		
		return null;
	}
 
	//listener for date picker
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
 
			// set selected date into textview
			tvDisplayDate.setText(new StringBuilder().append(month + 1)
			   .append("-").append(day).append("-").append(year)
			   .append(" "));
		}
	};
	
	// display time
	public void setTime() {
		tvDisplayTime = (TextView) findViewById(R.id.tvTime);
 
		hour = prefs.getInt("hour", 0);
		minute = prefs.getInt("minute", 0);
 
		// set time into textview
		tvDisplayTime.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));
	}
 
	//listener on time picker
	public void addListenerOnTime() {
		btnChangeTime = (Button) findViewById(R.id.btnChangeTime);
 
		btnChangeTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});
	}
 
	//listener for time picker
	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;
 
			// set time into textview
			tvDisplayTime.setText(new StringBuilder().append(pad(hour))
					.append(":").append(pad(minute)));
		}
	};
 
	//method to help display time correctly
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
	
	//submits report and checks whether it is completed or not 
	public void onClick(View view) {
		EditText editText = (EditText) findViewById(R.id.police_report);
    	reportNum = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.police_email);
    	policeEmail = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.yes_no);
    	didLock = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.lock_brand);
    	lockBrand = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.bike_place);
    	bikeLoc = editText.getText().toString();
    	
    	editText = (EditText) findViewById(R.id.description);
    	moreDescript = editText.getText().toString();
    	
    	String second = "";
		
		if (reportNum.equals("")) {
			second += "police report number, ";
		}
		
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern ePattern = Pattern.compile(EMAIL_PATTERN);
		Matcher eMatch = ePattern.matcher(policeEmail);
		
		if (!eMatch.matches())
		{
			second += "police email address, ";
		}
		
		if (!didLock.toUpperCase().equals("Y") && !didLock.toUpperCase().equals("N")) {
			second += "Y/N question about whether or not bike was locked, ";
		}
		
		if (lockBrand.equals("")) {
			second += "lock brand, ";
		}
		
		if (bikeLoc.equals("")) {
			second += "location of bike when stolen, ";
		}
		
		if (moreDescript.equals("")) {
			second += "additional description of incident, ";
		}
		
		editor.putString("reportNum", reportNum);
		editor.putString("policeEmail", policeEmail);
		editor.putString("didLock", didLock);
		editor.putString("lockBrand",  lockBrand);
		editor.putString("bikeLoc", bikeLoc);
		editor.putString("moreDescript",  moreDescript);
		editor.putInt("hour",  hour);
		editor.putInt("minute", minute);
		editor.putInt("year",  year);
		editor.putInt("month", month);
		editor.putInt("day",  day);
		editor.putBoolean("report",  true);

		if (!second.equals("")) {
			editor.putBoolean("reportFinish",  false);
			editor.commit();
		} else {
			String popup = "It looks like you filled out everything. Are you sure everything is correct?"
					+ " We will be contacting law enforcement with the information on this report.";
			
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        switch (which){
			        //user says that report has been completed accurately
			        case DialogInterface.BUTTON_POSITIVE:
			        	editor.putBoolean("reportFinish", true);
			        	
			        	//send server message "report finished"
			        	
			            break;

			        //user wants to look over the report one more time
			        case DialogInterface.BUTTON_NEGATIVE:
			        	editor.putBoolean("reportFinish", false);
			            break;
			        }
			        editor.commit();
			        Intent intent = new Intent (NewReport.this, TheFiveFrags.class);
					startActivity(intent);
			    }
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(popup).setPositiveButton("Yes", dialogClickListener)
			    .setNegativeButton("No", dialogClickListener).show();
		}
	}
}

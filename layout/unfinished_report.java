<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical" >
    
	<ScrollView
        android:layout_width="fill_parent"
        android:layout_height="fill_parent" >
        
	   	<LinearLayout
		    android:layout_width="fill_parent"
		    android:layout_height="fill_parent"
		    android:orientation="vertical" >
		    
	   	    <TextView
		        android:text="BikeNab Report"
		        android:layout_gravity="center_horizontal"
		        android:textStyle="bold"
		        android:textSize="15sp"
		        android:layout_marginTop="10dp"
		      	android:layout_width="wrap_content"
		        android:layout_height="wrap_content" 
		        android:layout_weight="0.08"/>
        
	        <TextView
		        android:text="Police Report"
		        android:textStyle="bold"
		        android:textSize="14sp"
		        android:layout_marginLeft="5dp"
		        android:layout_marginTop="10dp"
		      	android:layout_width="wrap_content"
		        android:layout_height="wrap_content" 
		        android:layout_weight="0.08"/>
			
			<LinearLayout
		        android:layout_width="fill_parent" 
		        android:layout_height="35dp" 
		        android:orientation="horizontal" >	
				
				<TextView
		            android:text="Police Report Number"
		            android:textSize="13sp"
		        	android:layout_width="wrap_content"
		        	android:layout_marginLeft="10dp"
		        	android:layout_marginRight="5dp"
		        	android:layout_height="25dp" />
		        
		        <EditText
		            android:background="@drawable/rounded_edittext"
				 	android:id="@+id/police_report"
					android:layout_width="fill_parent"
				 	android:layout_height="30dp"
				 	android:textSize="13sp"
				 	android:layout_marginRight="5dp"
				 	android:padding="8dp" />
		        
		    </LinearLayout>
		    
			<LinearLayout
		        android:layout_width="fill_parent" 
		        android:layout_height="35dp" 
		        android:orientation="horizontal" >	
				
				<TextView
		            android:text="Police Email Address"
		            android:textSize="13sp"
		        	android:layout_width="wrap_content"
		        	android:layout_marginLeft="10dp"
		        	android:layout_marginRight="5dp"
		        	android:layout_height="25dp" />
		        
		        <EditText
		            android:background="@drawable/rounded_edittext"
				 	android:id="@+id/police_email"
					android:layout_width="fill_parent"
				 	android:layout_height="30dp"
				 	android:textSize="13sp"
				 	android:layout_marginRight="5dp"
				 	android:padding="8dp" />
		        
		    </LinearLayout>
		    
			<TextView
		        android:text="Incident Report"
		        android:textStyle="bold"
		        android:textSize="14sp"
		        android:layout_marginLeft="5dp"
		        android:layout_marginTop="10dp"
		      	android:layout_width="wrap_content"
		        android:layout_height="wrap_content" 
		        android:layout_weight="0.08"/>
				 
			<LinearLayout
		        android:layout_width="fill_parent" 
		        android:layout_height="20dp" 
		        android:orientation="horizontal"
		        android:layout_marginTop="10dp" >	
		        
			    <TextView
			        android:id="@+id/lblDate"
			        android:layout_width="wrap_content"
			        android:layout_height="wrap_content"
			        android:layout_marginLeft="10dp"
		        	android:layout_marginRight="5dp"
			        android:text="Date of Accident" />
	 
			    <TextView
			        android:id="@+id/tvDate"
			        android:layout_width="wrap_content"
			        android:layout_height="wrap_content"
			        android:text="" />	
			        
			</LinearLayout>
			
			<Button
		        android:id="@+id/btnChangeDate"
		        android:layout_width="wrap_content"
		        android:layout_height="30dp"
		        android:layout_marginLeft="10dp"
		        android:textSize="10sp"
		        android:text="Change Date"
		        android:minHeight="0dp"
				android:minWidth="0dp"
		        android:layout_marginBottom="3dp"/>
			
			<LinearLayout
		        android:layout_width="fill_parent" 
		        android:layout_height="20dp" 
		        android:orientation="horizontal"
		        android:layout_marginTop="3dp" >
			    
			    <TextView
			        android:id="@+id/lblTime"
			        android:layout_marginLeft="10dp"
		        	android:layout_marginRight="5dp"
			        android:layout_width="wrap_content"
			        android:layout_height="wrap_content"
			        android:text="Time of Accident"/>
			 
			    <TextView
			        android:id="@+id/tvTime"
			        android:layout_width="wrap_content"
			        android:layout_height="wrap_content"
			        android:text="" />
			    
			</LinearLayout>
			
			<Button
		        android:id="@+id/btnChangeTime"
		        android:layout_width="wrap_content"
		        android:layout_height="30dp"
		        android:layout_marginLeft="10dp"
		        android:textSize="10sp"
		        android:text="Change Time"
		        android:minHeight="0dp"
				android:minWidth="0dp"
		        android:layout_marginBottom="3dp" />
		    
			<LinearLayout
		        android:layout_width="fill_parent" 
		        android:layout_height="35dp" 
		        android:orientation="horizontal" >	
				
				<TextView
		            android:text="Did you lock the bike?"
		            android:textSize="13sp"
		        	android:layout_width="wrap_content"
		        	android:layout_marginLeft="10dp"
		        	android:layout_marginRight="5dp"
		        	android:layout_height="25dp" />
		        
		        <EditText
		            android:background="@drawable/rounded_edittext"
				 	android:id="@+id/yes_no"
					android:layout_width="fill_parent"
				 	android:layout_height="30dp"
				 	android:hint="Y/N"
				 	android:textSize="13sp"
				 	android:layout_marginRight="5dp"
				 	android:padding="8dp" />
		        
		    </LinearLayout>
			
			<LinearLayout
		        android:layout_width="fill_parent" 
		        android:layout_height="35dp" 
		        android:orientation="horizontal" >	
				
				<TextView
		            android:text="Type of Lock Used"
		            android:textSize="13sp"
		        	android:layout_width="wrap_content"
		        	android:layout_marginLeft="10dp"
		        	android:layout_marginRight="5dp"
		        	android:layout_height="25dp" />
		        
		        <EditText
		            android:background="@drawable/rounded_edittext"
				 	android:id="@+id/lock_brand"
					android:layout_width="fill_parent"
				 	android:layout_height="30dp"
				 	android:textSize="13sp"
				 	android:layout_marginRight="5dp"
				 	android:padding="8dp" />
		        
		    </LinearLayout>
		    
			<LinearLayout
		        android:layout_width="fill_parent" 
		        android:layout_height="35dp" 
		        android:orientation="horizontal" >	
				
				<TextView
		            android:text="Where Bike was Placed"
		            android:textSize="13sp"
		        	android:layout_width="wrap_content"
		        	android:layout_marginLeft="10dp"
		        	android:layout_marginRight="5dp"
		        	android:layout_height="25dp" />
		        
		        <EditText
		            android:background="@drawable/rounded_edittext"
				 	android:id="@+id/bike_place"
					android:layout_width="fill_parent"
				 	android:layout_height="30dp"
				 	android:textSize="13sp"
				 	android:layout_marginRight="5dp"
				 	android:padding="8dp" />
		        
		    </LinearLayout>
		    
			<LinearLayout
		        android:layout_width="fill_parent" 
		        android:layout_height="100dp" 
		        android:orientation="horizontal" >
		        
		        <TextView
		            android:text="Any Additional Info\nabout What Occurred"
		            android:textSize="13sp"
		        	android:layout_width="wrap_content"
		        	android:layout_marginLeft="10dp"
		        	android:layout_marginRight="5dp"
		        	android:layout_height="35dp" />
		        
		        <EditText
		            android:id="@+id/description"
				 	android:layout_width="fill_parent"
				 	android:layout_height="95dp"
				 	android:background="@drawable/rounded_edittext"
				 	android:textSize="13sp"
				 	android:layout_marginRight="5dp"
				 	android:padding="8dp" />
		        
		    </LinearLayout>
		    
			<Button
		        android:id="@+id/photo_button"
		        android:layout_width="wrap_content"
		        android:layout_height="30dp"
		        android:textSize="11sp"
		        android:minHeight="0dp"
				android:minWidth="0dp"
				android:layout_marginTop="5dp"
				android:layout_marginBottom="5dp"
		        android:text="Choose Photos"
		        android:onClick="choose" />
			
			<TextView
	            android:text="Your Chosen Photos"
	            android:textSize="13sp"
	        	android:layout_width="wrap_content"
	        	android:layout_marginLeft="10dp"
	        	android:layout_marginBottom="3dp"
	        	android:layout_height="15dp" />
			
			<GridView
			    android:id="@+id/theft_grid"
			    android:numColumns="3"
		        android:layout_marginLeft="2dp"
		        android:layout_width="fill_parent"
		        android:layout_height="175dp"
		        android:columnWidth="100dip"
		        android:gravity="center"
		        android:horizontalSpacing="4dip"
		        android:stretchMode="columnWidth"
		        android:verticalSpacing="2dip" />
			 
			<LinearLayout
		        android:layout_width="fill_parent" 
		        android:layout_height="wrap_content" 
		        android:orientation="horizontal" 
		        android:gravity="center"
		        android:layout_marginTop="10dp" >
			
				<Button
			        android:id="@+id/save_finish"
			        android:layout_width="wrap_content"
			        android:layout_height="40dp"
			        android:textSize="15sp"
			        android:minHeight="0dp"
					android:minWidth="0dp"
			        android:text="Save/Finish"
			        android:gravity="center"
			        android:onClick="onClick" />
			
			</LinearLayout>
			
		</LinearLayout>
        
	</ScrollView>
    
</LinearLayout>

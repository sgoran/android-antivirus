package com.antivirusapp9.app;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListAppActivity extends Activity implements OnClickListener{
    //android.permissions
	public String[] can_cost_money = {"android.permission.SEND_SMS","android.permission.CALL_PHONE"};
	public String[] can_see_personal_info = {"android.permission.READ_CALENDAR", "android.permission.READ_CALL_LOG", "android.permission.READ_CONTACTS",
											 "android.permission.READ_PROFILE", "android.permission.READ_SMS", "android.permission.READ_SOCIAL_STREAM"};
	public String[] can_impact_battery = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.BLUETOOTH",
										  "android.permission.CALL_PHONE", "android.permission.FLASHLIGHT", "android.permission.NFC"};
	public String[] can_change_system = {"android.permission.WRITE_SETTINGS"};
	public String[] can_see_location_info = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
	
	
	public boolean results = false;
	public int counter;
	public int c2;
	public ArrayList<PInfo> apps;
	
	public Handler mHandler = new Handler();
	
	
	//bitnoooopo
	public ArrayList<PInfo> can_cost_money_obj = new ArrayList<PInfo>();
	public ArrayList<PInfo> can_see_personal_info_obj = new ArrayList<PInfo>();
	public ArrayList<PInfo> can_impact_battery_obj = new ArrayList<PInfo>();
	public ArrayList<PInfo> can_change_system_obj = new ArrayList<PInfo>();
	public ArrayList<PInfo> can_see_location_info_obj = new ArrayList<PInfo>();
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.splash);
    	
    	final ListAppActivity something = this;
    	//setContentView(R.layout.main);
    	
    	new Handler().postDelayed(new Runnable() {
   			@Override
            public void run() {
            	apps = getInstalledApps(false); 
           }
        }, 500);
    	
    	
    	
    	new Handler().postDelayed(new Runnable() {
    		
    		@Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
            	setContentView(R.layout.main);
            	Button b = (Button) findViewById(R.id.button1);
                b.setOnClickListener(something); 
           
            }
        }, 4000);
    	
    	
    	
        
        
    }


	public static class PInfo implements Parcelable{
		
		public String appname = "";   
		public String pname = ""; 
	    public int icon = 0;
	    public ArrayList<String> permissions = new ArrayList<String>(); 
	    public ArrayList<String> critical = new ArrayList<String>();
	    
	    
	    public PInfo() { 
	    	this.appname = appname;   
	    	this.pname = pname;
		    
	    	this.icon = icon;
	    	this.permissions = permissions;
	    	this.critical = critical;
	    	;
		}
	    
	    private PInfo(Parcel in) {
			appname = in.readString();   
		    pname = in.readString();
		    
		    icon = in.readInt();
		    try{
			 	in.readStringList(permissions);
		    	in.readStringList(critical);
		    }catch(Exception e){
		    	//Log.v("PUCA readStringList ", e.toString());
		    };
		    
		 }
	    
		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			// TODO Auto-generated method stub
			 dest.writeString(appname);
			 dest.writeString(pname);
//			 dest.writeString(classname);
//			 dest.writeString(versionName);
//			 dest.writeInt(versionCode);

			 // Bitmap bitmap = (Bitmap)((BitmapDrawable) icon).getBitmap();
			 //dest.writeParcelable(bitmap, flags);
			 dest.writeInt(icon);
			 try{
				 dest.writeStringList(permissions);
				 dest.writeStringList(critical);
		    }catch(Exception e){
		    	//Log.v("PUCA writeStringList ", e.toString());
		    };

			
		}
		
		
		
		 public static final Parcelable.Creator<PInfo> CREATOR
		         = new Parcelable.Creator<PInfo>() {
		     public PInfo createFromParcel(Parcel in) {
		         return new PInfo(in);
		     }
		
		     public PInfo[] newArray(int size) {
		         return new PInfo[size];
		     }
		 };
		 
		 
		
		
	}
	
	
	private ArrayList<PInfo> getPackages() {
		final int max = apps.size();
	    final TextView percent = (TextView) findViewById(R.id.percent);
        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBar1);
        
    	mProgress.setVisibility(View.VISIBLE);
    	mProgress.setMax(max);
	    counter=0;
	
	    
	    
        mHandler.postDelayed(new Runnable(){
        	
	        public void run(){
	        	
	        	counter++;
	        	//
	        	if(counter<apps.size()){
	        		
		        	mProgress.setProgress(counter);
			        int percent_num = (counter*100 / max);
			         percent.setText(percent_num + " %");
		        	mHandler.postDelayed(this, 20);
		        	
			    }else{
			    	Button b1 = (Button) findViewById(R.id.button1);
			    	b1.setEnabled(true);
			    	b1.setText("SEE RESULTS");
			    	results = true;
			    	percent.setText("100 %");
			    	
			    }
          }
       }, 20);
	        
	    return apps;
	    
	}

	private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
		
	    ArrayList<PInfo> res = new ArrayList<PInfo>();        
	      
	    
	    List<PackageInfo> packs = getPackageManager().getInstalledPackages(getPackageManager().GET_PERMISSIONS);
	    c2 = 0;
	    
	    
	    for(int i=0;i<packs.size();i++) {
	        PackageInfo p = packs.get(i);
	        if ((!getSysPackages) && (p.versionName == null)) {
	            continue ;
	        }
	        
	        PInfo newInfo = new PInfo();
	        newInfo.appname = p.applicationInfo.loadLabel(getPackageManager()).toString();
	        newInfo.pname = p.packageName;
//	        newInfo.versionName = p.versionName;
//	        newInfo.versionCode = p.versionCode;
	        newInfo.icon = p.applicationInfo.icon; 
	        
	        try{
	        	if(p.requestedPermissions!=null)
			        for (String s : p.requestedPermissions) {
			        	newInfo.permissions.add(s);
			        }
		    }catch(Exception e){
		    	//Log.v("PUCA for  add permissions ", e.toString());
		    };
		    
	        
	        
 
	        boolean toAddcan_cost_money_obj = false;
	        boolean toAddcan_see_personal_info_obj = false;
	        boolean toAddcan_impact_battery_obj = false;
	        boolean toAddcan_change_system_obj = false;
	        boolean toAddcan_see_location_info_obj = false;
	        
	        
	        if(p.requestedPermissions!=null){
	        	
	        	 for(c2=0; c2<p.requestedPermissions.length; c2++){
		        	
		        	if(p.requestedPermissions[c2]!=null && Arrays.asList(can_cost_money).contains(p.requestedPermissions[c2])){
		        		toAddcan_cost_money_obj = true;
		        		try{
		    	        	if(p.requestedPermissions[c2]!=null){
	    			        	newInfo.critical.add(p.requestedPermissions[c2]);
		    	        	}
		    		    }catch(Exception e){
		    		    	//Log.v("PUCA for  add critical ", e.toString());
		    		    };
		        	}
		        	
		        	
		        	if(p.requestedPermissions[c2]!=null && Arrays.asList(can_see_personal_info).contains(p.requestedPermissions[c2])){
		        		toAddcan_see_personal_info_obj = true;
		        		try{
		    	        	if(p.requestedPermissions[c2]!=null){
	    			        	newInfo.critical.add(p.requestedPermissions[c2]);
		    	        	}
		    		    }catch(Exception e){
		    		    	//Log.v("PUCA for  add critical ", e.toString());
		    		    };
		        	}
		        	
		        	
		        	if(p.requestedPermissions[c2]!=null && Arrays.asList(can_see_personal_info).contains(p.requestedPermissions[c2])){
		        		toAddcan_impact_battery_obj = true;
		        		try{
		    	        	if(p.requestedPermissions[c2]!=null){
	    			        	newInfo.critical.add(p.requestedPermissions[c2]);
		    	        	}
		    		    }catch(Exception e){
		    		    	//Log.v("PUCA for  add critical ", e.toString());
		    		    };
		        	}
		        	
		        	
		        	if(p.requestedPermissions[c2]!=null && Arrays.asList(can_change_system).contains(p.requestedPermissions[c2])){
		        		toAddcan_change_system_obj = true;
		        		try{
		    	        	if(p.requestedPermissions[c2]!=null){
	    			        	newInfo.critical.add(p.requestedPermissions[c2]);
		    	        	}
		    		    }catch(Exception e){
		    		    	//Log.v("PUCA for  add critical ", e.toString());
		    		    };
		        	}
		        	
		        	
		        	if(p.requestedPermissions[c2]!=null && Arrays.asList(can_see_location_info).contains(p.requestedPermissions[c2])){
		        		toAddcan_see_location_info_obj = true;
		        		try{
		    	        	if(p.requestedPermissions[c2]!=null){
	    			        	newInfo.critical.add(p.requestedPermissions[c2]);
		    	        	}
		    		    }catch(Exception e){
		    		    	//Log.v("PUCA for  add critical ", e.toString());
		    		    };
		        	}
		        	
		        	
		        }
	        	 
	        	 
	        	newInfo.permissions = new ArrayList<String>();
	        	
	        	 
		        if(toAddcan_cost_money_obj){
		        	can_cost_money_obj.add(newInfo);
		        }
		        
		       
		        if(toAddcan_see_personal_info_obj){
		        	can_see_personal_info_obj.add(newInfo);
		        }
		        
		        
		        if(toAddcan_impact_battery_obj){
		        	can_impact_battery_obj.add(newInfo);
		        }
		        
		        
		        if(toAddcan_change_system_obj){
		        	can_change_system_obj.add(newInfo);
		        }
		        
		        
		        if(toAddcan_see_location_info_obj){
		        	can_see_location_info_obj.add(newInfo);
		        }
		        
		        
		        res.add(newInfo);
		        
	        }
	        
	        
	    }
	    
	    return res; 
	}
	
	
	@Override
	public void onClick(View arg0) {
		//do scan
		if(!results){
			arg0.setEnabled(false); 
			getPackages();
		}
		// go to results
		else{
//			Log.v("VELICINA" , "VELICINA "+can_cost_money_obj.size());
//			Log.v("VELICINA" , "VELICINA "+can_see_personal_info_obj.size());
//			Log.v("VELICINA" , "VELICINA "+can_impact_battery_obj.size());
//			Log.v("VELICINA" , "VELICINA "+can_change_system_obj.size());
//			Log.v("VELICINA" , "VELICINA "+can_see_location_info_obj.size());
			
			
			Intent myIntent = new Intent(ListAppActivity.this, Result.class);
			
			try{
			
			
				myIntent.putParcelableArrayListExtra("can_cost_money_obj", can_cost_money_obj);
				myIntent.putParcelableArrayListExtra("can_see_personal_info_obj", can_see_personal_info_obj);
				myIntent.putParcelableArrayListExtra("can_impact_battery_obj", can_impact_battery_obj);
				myIntent.putParcelableArrayListExtra("can_change_system_obj", can_change_system_obj);
				myIntent.putParcelableArrayListExtra("can_see_location_info_obj", can_see_location_info_obj);
				
				startActivity(myIntent);
				finish();
			}catch(Exception e){
		    	//Log.v("NE MOZE ADOVATI ", e.toString());
		    };
			
		}
	}
	
	

}























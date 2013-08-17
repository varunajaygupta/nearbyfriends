package com.example.nearbyfriends;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.android.gms.maps.model.LatLng;

public class Gpschecker extends SherlockActivity implements LocationListener {

	Boolean enable = false;
	LocationManager locationManager;
	int x = 0;
	Criteria criteria;
	Location location;
	String provider;
	LatLng myPosition;

	// Provider gpsprovider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frontpage);
		setUpMapIfNeeded();
		
		
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			
			Thread timer = new Thread(){
				public void run(){
					try{
						sleep(5000);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
					finally{
						// Intent openMainActivity = new Intent("com.example.bucky.MENU");
//						Intent openMainActivity = new Intent(Splash.this,Menu.class);
//						startActivity(openMainActivity);
						
						
						startActivity(new Intent(Gpschecker.this, MainActivity.class));
						finish();
						
						
					}
					
					           
					
				}
				
			};
			timer.start();
			
			Toast.makeText(Gpschecker.this, "GPS is Enabled in your devide",
					Toast.LENGTH_SHORT).show();
		} else {
			showGPSDisabledAlertToUser();
		}

	}



	private void showGPSDisabledAlertToUser() {
		// Toast.makeText(this, "Enabled your Gps and restart your app",
		// Toast.LENGTH_SHORT).show();

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage(
						"GPS is disabled in your device. Would you like to enable it?")
				.setCancelable(false)
				.setPositiveButton("Goto Settings Page To Enable GPS",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent callGPSSettingIntent = new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(callGPSSettingIntent);
								//setUpMapIfNeeded();
								new ultimate().execute("");
							}
						});
		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						//setUpMapIfNeeded();
						new cancelultimate().execute("");
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();

	}

	private class ultimate extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			Boolean status = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			Log.e("TAAAG", status.toString() + "1");
			while (!locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				Log.e("TAG", status.toString());
				if (locationManager
						.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

					try {
						Thread.sleep(25000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}

			}
			return null;

		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			 startActivity(new Intent(Gpschecker.this,MainActivity.class));
		finish();
		}
		
		
	}

	private class cancelultimate extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			Boolean status = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			Log.e("TAAAG", status.toString() + "1");
			while (location != null) {
				Log.e("TAG", status.toString());
				if (location != null) {

					try {
						Thread.sleep(25000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}

			}
			return null;

		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			 startActivity(new Intent(Gpschecker.this,MainActivity.class));
		finish();
		}
		
		
	}

private void setUpMapIfNeeded() {

			locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			criteria = new Criteria();
			provider = locationManager.getBestProvider(criteria, false);
			location = locationManager.getLastKnownLocation(provider);
			locationManager.requestLocationUpdates(provider, 400, 1, this);
			
			
			

			if (location != null) {
				System.out.println("Provider " + provider
						+ " has been selected.");
				onLocationChanged(location);
			}

			

		}

	

	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

		double lat = (double) (arg0.getLatitude());
		double lng = (double) (arg0.getLongitude());
		// TODO Auto-generated method stub

		myPosition = new LatLng(lat, lng);

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}

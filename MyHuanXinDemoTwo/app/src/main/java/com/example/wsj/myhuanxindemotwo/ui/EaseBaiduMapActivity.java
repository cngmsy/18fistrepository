/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wsj.myhuanxindemotwo.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.example.wsj.myhuanxindemotwo.R;


public class EaseBaiduMapActivity extends Activity {

	private final static String TAG = "map";
	static MapView mMapView = null;
	FrameLayout mMapViewContainer = null;
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	public NotifyLister mNotifyer = null;

	Button sendButton = null;

	EditText indexText = null;
	int index = 0;
	// LocationData locData = null;
	static BDLocation lastLocation = null;
	public static EaseBaiduMapActivity instance = null;
	ProgressDialog progressDialog;
	private BaiduMap mBaiduMap;
	
	private LocationMode mCurrentMode;


	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		//initialize SDK with context, should call this before setContentView
        SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_map);
		mMapView = (MapView) findViewById(R.id.bmapView);
		sendButton = (Button) findViewById(R.id.btn_sendmap);
		Intent intent = getIntent();
		double latitude = intent.getDoubleExtra("latitude", 0);
		mCurrentMode = LocationMode.NORMAL;
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu);
		initMapView();
		if (latitude == 0) {

			Log.e(TAG, "onCreate: "+"hhhhhhhhhhhhhhhhhhhhhhh发送" );
			mMapView = new MapView(this, new BaiduMapOptions());
			mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
							mCurrentMode, true, null));
			showMapWithLocationClient();
		} else {
			Log.e(TAG, "onCreate: "+"kkkkkkkkkkkkkkkkkkkkk有精度纬度" );
			double longtitude = intent.getDoubleExtra("longitude", 0);
			String address = intent.getStringExtra("address");
			LatLng p = new LatLng(latitude, longtitude);
			mMapView = new MapView(this,
					new BaiduMapOptions().mapStatus(new MapStatus.Builder()
							.target(p).build()));
			showMap(latitude, longtitude, address);
		}



	}

	private void showMap(double latitude, double longtitude, String address) {
		sendButton.setVisibility(View.GONE);
		LatLng llA = new LatLng(latitude, longtitude);
		CoordinateConverter converter= new CoordinateConverter();
		converter.coord(llA);
		converter.from(CoordinateConverter.CoordType.COMMON);
		LatLng convertLatLng = converter.convert();
		OverlayOptions ooA = new MarkerOptions().position(convertLatLng).icon(BitmapDescriptorFactory
				.fromResource(R.mipmap.ease_icon_marka))
				.zIndex(4).draggable(true);
		mBaiduMap.addOverlay(ooA);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, 17.0f);
		mBaiduMap.animateMapStatus(u);
	}

	private void showMapWithLocationClient() {
		String str1 = getResources().getString(R.string.Making_sure_your_location);
		progressDialog = new ProgressDialog(this);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(str1);

		progressDialog.setOnCancelListener(new OnCancelListener() {

			public void onCancel(DialogInterface arg0) {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				Log.d("map", "cancel retrieve location");
				finish();
			}
		});

		progressDialog.show();

		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// open gps
		// option.setCoorType("bd09ll"); 
		// Johnson change to use gcj02 coordination. chinese national standard
		// so need to conver to bd09 everytime when draw on baidu map
		option.setCoorType("gcj02");
		option.setScanSpan(30000);
		option.setAddrType("all");
		mLocClient.setLocOption(option);
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mLocClient != null) {
			mLocClient.stop();
		}
		super.onPause();
		lastLocation = null;
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (mLocClient != null) {
			mLocClient.start();
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		if (mLocClient != null)
			mLocClient.stop();
		mMapView.onDestroy();

		super.onDestroy();
	}
	private void initMapView() {
		mMapView.setLongClickable(true);
	}

	/**
	 * format new location to string and show on screen
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
			Log.d("map", "On location change received:" + location);
			Log.d("map", "addr:" + location.getAddrStr());
			sendButton.setEnabled(true);
			if (progressDialog != null) {
				progressDialog.dismiss();
			}

			if (lastLocation != null) {
				if (lastLocation.getLatitude() == location.getLatitude() && lastLocation.getLongitude() == location.getLongitude()) {
					Log.d("map", "same location, skip refresh");
					// mMapView.refresh(); //need this refresh?
					return;
				}
			}
			lastLocation = location;
			mBaiduMap.clear();
			LatLng llA = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
			CoordinateConverter converter= new CoordinateConverter();
			converter.coord(llA);
			converter.from(CoordinateConverter.CoordType.COMMON);
			LatLng convertLatLng = converter.convert();
			OverlayOptions ooA = new MarkerOptions().position(convertLatLng).icon(BitmapDescriptorFactory
					.fromResource(R.mipmap.ease_icon_marka))
					.zIndex(4).draggable(true);
			mBaiduMap.addOverlay(ooA);
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, 17.0f);
			mBaiduMap.animateMapStatus(u);
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	public class NotifyLister extends BDNotifyListener {
		public void onNotify(BDLocation mlocation, float distance) {
		}
	}

	public void back(View v) {
		finish();
	}

	public void sendLocation(View view) {
		Intent intent = this.getIntent();
		intent.putExtra("latitude", lastLocation.getLatitude());
		intent.putExtra("longitude", lastLocation.getLongitude());
		intent.putExtra("address", lastLocation.getAddrStr());
		this.setResult(RESULT_OK, intent);
		finish();
//		Toast.makeText(EaseBaiduMapActivity.this, "精度:"+lastLocation.getLatitude()+"aaa"+lastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
//		overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}

}

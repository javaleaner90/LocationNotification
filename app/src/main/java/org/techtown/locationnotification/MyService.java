package org.techtown.locationnotification;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService extends Service implements Runnable{

    private LocationManager manager;
    Thread thread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() { super.onCreate(); }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.interrupt();
        thread = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // run() 메소드 안에 넣으면 Can't Create handler inside Thread 라고 오류가 뜬다 씨발 왜 이러지.
        startLocationService();

        thread = new Thread(this);
        thread.start();
        return startId;
    }


    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String msg = "Latitude : " + latitude + "\nLongitude:" + longitude;
            Log.i("GPSListener",msg);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

    };

    public void startLocationService() {
        manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        long minTime = 10000;
        float mindIstance = 0;

        try {

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,mindIstance, locationListener
            );


        } catch (SecurityException e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        Log.e("서비스 시작","start");
    }


}












      /*
        while(true) {

            try{
              Thread.sleep(3000);
              Log.e("서비스" ,"Hi");

              Message msgTosend = Message.obtain();
              String str = "통신";
              msgTosend.obj = str;
              mainHandler.sendMessage(msgTosend);

              // mCallback.recvToastMessage("통신");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
      */


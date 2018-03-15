package org.techtown.locationnotification;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service implements Runnable{


    private LocationManager manager;
    private MainActivity.MainHandler mainHandler;

    private class GPSListener implements LocationListener{

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
    }

    private void startLocationService() {


        manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;
        float mindIstance = 0;

        try {

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,mindIstance,gpsListener
            );


        } catch (SecurityException e){
            e.printStackTrace();
        }


    }


    public void onCreate() {
            super.onCreate();
            Thread t = new Thread(this);
            t.start();
    }



    /*
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return startId;
    }


    @Override
    public void run() {
        while(true) {


        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    */
    // Activity에서 해당 서비스를 가져오는 Binder 생성 후 서비스 반환
    public class MyServiceBinder extends Binder {
        public MyService getService() { return MyService.this; }
    }


    // Activity에서 정의해 해당 서비스와 통신할 함수를 추상 함수로 정의
    public interface Icallback {
        void recvMessage(String message);
        void recvToastMessage(String message);
    }

    // Activity랑 통신할 Callback 객체..
    private Icallback mCallback;

    // Callback 객체 등록 함수..
    public void registerCallback(Icallback callback,MainActivity.MainHandler mainH) {

        Log.e("레지스터","레지스터콜백함수 호출");
        mCallback = callback;
        mainHandler = mainH;

    }

    private final IBinder mBinder = new MyServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void run() {

       // startLocationService();
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


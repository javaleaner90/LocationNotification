package org.techtown.locationnotification;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class MyService extends Service implements Runnable{

    MainActivity.MainHandler mainHandler;


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
    public void registerCallback(Icallback callback,MainActivity.MainHandler main) {
        Log.e("레지스터","레지스터콜백함수 호출");
        mCallback = callback;
        mainHandler = main;

    }

    private final IBinder mBinder = new MyServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void run() {

      //  Looper.prepare();
      //  Looper.loop();

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
    }

}




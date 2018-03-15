package org.techtown.locationnotification;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {

    private MyService mService;

    class MainHandler extends Handler {
        public void handleMessage(Message msg) {

            String str = (String)msg.obj;
            Log.e("서비스 에 온 문자 : ", str);
            suc(str);

        }
    }

    /*
        Service와 연결할 객체입니다. 단순히 연결만 하는 고리이기 때문에,
        서비스 연결 함수에서 서비스 객체를 생성하고 정의하여야 합니다.
     */

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyServiceBinder binder = (MyService.MyServiceBinder)service;
            mService = binder.getService();
            mService.registerCallback(mCallback, new MainHandler());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    // Service에서 선언한 ICallback 객체를 생성해 추상으로 정의한 함수를 구현합니다.
    private MyService.Icallback mCallback = new MyService.Icallback() {
        @Override
        public void recvMessage(String message) {
            // Todo: Activity에서 처리합니다.
        }

        @Override
        public void recvToastMessage(String message) {
            // Todo: Activity에서 처리합니다.
            Log.e("Tag", message);
            suc(message);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mService.onCreate();    // 예제로 쓴 함수입니다.


    }

    public void msg(View v) {

        Intent intent = new Intent(this,MyService.class);
        bindService(intent, // intent 객체
                mConnection, // 서비스와 연결에 대한 정의
                Context.BIND_AUTO_CREATE);

        startService(intent);

    }

    public void suc(String msg) {
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }


}



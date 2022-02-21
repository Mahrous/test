package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.company.NetSDK.CB_fDisConnect;
import com.company.NetSDK.INetSDK;
import com.company.NetSDK.NET_PARAM;

public class MainActivity extends AppCompatActivity {
    Handler mHandler=new Handler();
    boolean zRet=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Prevent the failure of library loading.
        INetSDK.LoadLibrarys();
// Call the interface before using all SDK functions and set reconnection callback. When the network of
        //     app and device disconnected, the callback will be triggered.
// Only need to be called once
        DeviceDisConnect mDisconnect = new DeviceDisConnect();
         zRet = INetSDK.Init(mDisconnect);
        if (!zRet) {
            Log.e("TAG", "init NetSDK error!");
            return;
        }
// Set the login network environment.
        NET_PARAM stNetParam = new NET_PARAM();
        stNetParam.nWaittime = 10; // Time out of common Interface.
        stNetParam.nSearchRecordTime = 30; // Time out of Playback interface.
        INetSDK.SetNetworkParam(stNetParam);


    }
    @Override
    protected void onDestroy() {
        // while exiting the application, please make sure to invoke cleanup.
        /// 退出应用后，调用 cleanup 清理资源
        INetSDK.Cleanup();
        super.onDestroy();
    }
    // When the network of app and device disconnected, the callback will be triggered. The detection time of
//    disconnection is 60 seconds by default.
    public class DeviceDisConnect implements CB_fDisConnect {
        @Override
        public void invoke(long loginHandle, String deviceIp, int devicePort) {
            Log.d("TAG", "Device " + deviceIp + " is disConnected !");

            mHandler.post(new Runnable() {
                @Override
                public void run() {
// You can be notified here.
// ToolKits.alertDisconnected();
                }
            });
        }
    }
}
package com.myemcu.messageget;

import com.myemcu.app_11aidl.Music;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button mGetData;
    private EditText mName;
    private EditText mAuthor;

    private Music musicBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        //Intent intent = new Intent("com.android.MYSERVICE");

        final Intent intent = new Intent();                     // 创建Intent对象
        intent.setAction("com.myemcu.app_11aidl.MyService");    // 设置Intent特征
        intent.setPackage("com.myemcu.app_11aidl");
        bindService(intent,conn, Service.BIND_AUTO_CREATE);     // 绑定Service

        mGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mName.setText(musicBinder.getName());
                    mAuthor.setText(musicBinder.getAuthor());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicBinder=Music.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBinder=null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    private void findViews() {
        mGetData = (Button)   findViewById(R.id.getData);
        mName = (EditText) findViewById(R.id.name);
        mAuthor = (EditText) findViewById(R.id.author);
    }
}

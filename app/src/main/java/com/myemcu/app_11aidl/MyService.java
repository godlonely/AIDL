package com.myemcu.app_11aidl;

//import com.myemcu.app_11aidl.Music.Stub;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    private String[] names=new String[]{"老男孩","春天里","在路上"};
    private String[] authors=new String[]{"筷子兄弟","汪峰","刘欢"};
    private String name,author;
    private MusicBinder musicBinder;

    private int rand=0;

    private Timer timer=new Timer();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return musicBinder;
    }

    @Override
    public void onCreate() {
        musicBinder=new MusicBinder();
        timer.schedule(new TimerTask() {
            public void run() {
                name=names[rand];
                author=authors[rand];

                rand++;
                if (rand>=3)
                    rand=0;

                System.out.println(rand);
            }
        }, 0,1000);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        timer.cancel();
    }


    public class MusicBinder extends Music.Stub{
        
        public String getName() throws RemoteException {
            return name;
        }
        public String getAuthor() throws RemoteException {
            return author;
        }

    }
}

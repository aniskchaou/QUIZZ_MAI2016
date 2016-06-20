package com.tmm.android.quizzGlid;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class ServiceMusique extends Service {
    public ServiceMusique() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.song);

        mediaPlayer.setVolume(1,1);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.song);


        mediaPlayer.setVolume(1,1);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


        return START_STICKY;
    }
}

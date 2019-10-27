package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GeoService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO: create Geolocation service to track phone's location
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

package services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;

import model.Destination;

public class SendData extends Service {


    private class UploadDestination extends AsyncTask<Destination, Void, Void> {
        protected void onPreExecute() {
            // Runs on the UI thread before doInBackground
            // Good for toggling visibility of a progress indicator
            Log.d("API", "Start connection");
        }

        protected Void doInBackground(Destination... destinations) {
            Gson gson = new Gson();
            for (Destination de :
                    destinations) {
                String json = gson.toJson(de);
                Unirest.post("http://192.168.0.5:8080/model/" + "destination" + "/create")
                        .header("Content-Type", "application/json")
                        .body(json);
            }

            return null;
        }

        protected void onPostExecute(String result) {
            // This method is executed in the UIThread
            // with access to the result of the long running task
            Log.d("API", "Server response: " + result);
        }
    }

    private class DestinationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

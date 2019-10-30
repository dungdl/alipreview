package api;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import model.CurrentLocation;
import model.Destination;

public class ConnectServer {

    private static class Upload extends AsyncTask<String, String, Void> {

        private HttpClient httpclient;
        private HttpPost httppost;
        private Destination destination;
        private CurrentLocation location;

        Upload(HttpClient httpclient, HttpPost httppost, Destination destination, CurrentLocation location) {
            this.httpclient = httpclient;
            this.httppost = httppost;
            this.destination = destination;
            this.location = location;
        }

        protected void onPreExecute() {
            // Runs on the UI thread before doInBackground
            // Good for toggling visibility of a progress indicator
            Log.d("HTTPasync", "pre execute");
        }

        protected Void doInBackground(String... strings) {
            // Some long-running task like downloading an image.
            try {

                Gson gson = new Gson();

                //upload image
                HttpResponse response = httpclient.execute(httppost);
                Log.d("HTTPresImage", response.toString());

                //upload destination
                String json = gson.toJson(destination);
                String resDestination = String.valueOf(Unirest.post("http://192.168.0.5:8080/mongoapi/" + "destination" + "/create")
                        .header("Content-Type", "application/json")
                        .body(json)
                        .asString()
                );

                Log.d("HTTPresJson", resDestination);

                //upload location
                json = gson.toJson(location);
                String resLocation = String.valueOf(Unirest.post("http://192.168.0.5:8080/mongoapi/" + "location" + "/create")
                        .header("Content-Type", "application/json")
                        .body(json)
                        .asString()
                );

                Log.d("HTTPresJson", resLocation);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnirestException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap result) {
            // This method is executed in the UIThread
            // with access to the result of the long running task
            Log.d("HTTPasync", "done");
        }
    }

    private HttpClient httpclient;
    private HttpPost httppost;
    private MultipartEntityBuilder builder;

    private static ConnectServer instance;
    private Context context;

    private ConnectServer() {
        this.httpclient = HttpClientBuilder.create().build();
        this.httppost = new HttpPost("http://192.168.0.5:8080/upload");
        this.builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    }

    public static ConnectServer getInstance(Context context) {
        if (instance == null) {
            instance = new ConnectServer();
        }
        instance.setContext(context);
        return instance;
    }

    public void upload(Bitmap bitmap, Destination destination, CurrentLocation location) {

        File f = new File(context.getCacheDir(), "mia.jpg");
        try {

            //Convert bitmap image to file
            f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            //Add file to entity
            ArrayList<File> listFile = new ArrayList<>();
            listFile.add(f);

            for (File file : listFile) {
                builder.addPart("photo", new FileBody(file));
            }

            HttpEntity entity = builder.build();

            httppost.setEntity(entity);

            //AsyncTask for network connection
            new Upload(this.httpclient, this.httppost, destination, location).execute("");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setContext(Context context) {
        this.context = context;
    }
}

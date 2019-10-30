package com.example.alimentopreview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import api.ConnectServer;
import model.CurrentLocation;
import model.Destination;
import services.GeoService;

public class AddActivity extends AppCompatActivity {



    //MARK:- UI Preferences
    LinearLayout lilayAddActivity;
    Toolbar toolbar;
    EditText edtDesName;
    EditText edtDesAddress;
    ImageButton btnAutoLocate;
    Button btnAddPhoto;
    Button btnDone;
    TextView tvLocation;

    //MARK:- Data
    CurrentLocation location;
    LocationReceiver locationReceiver;

    class LocationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            location = (CurrentLocation) intent.getSerializableExtra("location");
            Log.d("AddActivity", "onReceive: Received");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //bind views
        toolbar = findViewById(R.id.toolbar);
        lilayAddActivity = findViewById(R.id.lilayAddActivity);
        edtDesName = findViewById(R.id.edtDesName);
        edtDesAddress = findViewById(R.id.edtDesAddress);
        btnAutoLocate = findViewById(R.id.btnAutoLocate);
        btnAddPhoto = findViewById(R.id.btnAddPhoto);
        btnDone = findViewById(R.id.btnDone);
        tvLocation = findViewById(R.id.tvLocation);

        startGeoService();

        //register Location receiver
        registerLocationReceiver();

        //add back button
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        //handle back press
        setBackListener();

        //add photo button
        addBtnAddPhotoListener();

        //add auto locate button
        addBtnAutoLocateListener();

        //add done button
        addBtnDoneListener();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(locationReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerLocationReceiver();
    }

    //MARK:- Listener
    private void setBackListener() {
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void addBtnAddPhotoListener() {
        btnAddPhoto.setOnClickListener(v -> {
            PopupMenu uploadPhotoMenu = new PopupMenu(AddActivity.this, btnAddPhoto, Gravity.BOTTOM);
            uploadPhotoMenu.getMenuInflater().inflate(R.menu.photo_menu, uploadPhotoMenu.getMenu());
            uploadPhotoMenu.show();

        });
    }

    private void addBtnAutoLocateListener() {
        btnAutoLocate.setOnClickListener(v -> getCurrentLocation());
    }

    private void addBtnDoneListener() {
        btnDone.setOnClickListener(v -> {
            String id = String.valueOf(System.currentTimeMillis());
            String name = String.valueOf(edtDesName.getText());
            String address = String.valueOf(edtDesAddress.getText());
            Destination destination = new Destination(id, name, address, "mia_smoak", location.getId());

            ConnectServer connectServer = ConnectServer.getInstance(AddActivity.this);
            Drawable drawable = getResources().getDrawable(R.drawable.mia_smoak);
            if (drawable != null && location != null) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                connectServer.upload(bitmap, destination, location);
            } else {
                Toast.makeText(this, "location null", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent();
            intent.putExtra("destination", destination);
            intent.putExtra("location", location);
            setResult(RESULT_OK, intent);

            finish();
        });
    }


    //MARK:- Support methods

    private void getCurrentLocation() {
        if (location != null) {
            tvLocation.setText(location.getLat() + " | " + location.getLon());
        } else {
            tvLocation.setText("Not received");
        }
    }

    private void registerLocationReceiver() {
        IntentFilter intentFilter = new IntentFilter("current_location");
        locationReceiver = new LocationReceiver();
        registerReceiver(locationReceiver, intentFilter);
    }

    private void startGeoService() {
        Intent intent = new Intent(AddActivity.this, GeoService.class);
        startService(intent);
    }


}

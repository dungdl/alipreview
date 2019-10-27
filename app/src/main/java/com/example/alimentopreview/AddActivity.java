package com.example.alimentopreview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

public class AddActivity extends AppCompatActivity {

    //MARK:- UI Preferences
    LinearLayout lilayAddActivity;
    Toolbar toolbar;
    EditText edtDesName;
    EditText edtDesAddress;
    ImageButton btnAutoLocate;
    Button btnAddPhoto;

    //MARK:- Data
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        toolbar = findViewById(R.id.toolbar);
        lilayAddActivity = findViewById(R.id.lilayAddActivity);
        edtDesName = findViewById(R.id.edtDesName);
        edtDesAddress = findViewById(R.id.edtDesAddress);
        btnAutoLocate = findViewById(R.id.btnAutoLocate);
        btnAddPhoto = findViewById(R.id.btnAddPhoto);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        //handle back press
        setBackListener();

        //add photo button
        addbtnAddPhotoListener();

        //add auto locate button
        addBtnAutoLocateListener();

    }

    //MARK:- Listener
    private void setBackListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addbtnAddPhotoListener() {
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu uploadPhotoMenu = new PopupMenu(AddActivity.this, btnAddPhoto, Gravity.BOTTOM);
                uploadPhotoMenu.getMenuInflater().inflate(R.menu.photo_menu, uploadPhotoMenu.getMenu());
                uploadPhotoMenu.show();
            }
        });
    }

    private void addBtnAutoLocateListener() {
        btnAutoLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });
    }


    //MARK:- Support methods

    private void getCurrentLocation() {

    }


}

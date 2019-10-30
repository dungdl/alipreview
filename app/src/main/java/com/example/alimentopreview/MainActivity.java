package com.example.alimentopreview;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Objects;

import adapters.DestinationAdapter;
import api.App;
import model.CurrentLocation;
import model.CurrentLocationDao;
import model.DaoSession;
import model.Destination;
import model.DestinationDao;
import services.DataAccess;
import services.GeoService;

import static values.RequestCode.ADD_ACTIVITY_RESULT;
import static values.RequestCode.REQUEST_PERMISSION;

public class MainActivity extends AppCompatActivity {

    //handle Broadcast from DataAccess service
    class DestReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            listDes = (ArrayList<Destination>) intent.getSerializableExtra("list_destination");
            listDes.add(new Destination("id", "name", "address", "ava", "location"));
            //set adapter for rev
            adapter = new DestinationAdapter(listDes);
            rvDestination.setAdapter(adapter);
            rvDestination.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        }
    }


    //MARK:- UI Preferences
    private DrawerLayout drwDrawer;
    private ActionBarDrawerToggle toggleDrawer;
    private NavigationView nvDrawer;
    private RecyclerView rvDestination;
    private FloatingActionButton fabAdd;
    DestinationAdapter adapter;

    //MARK:- Data
    private ArrayList<Destination> listDes;
    private DestReceiver destReceiver;
    private boolean isAllowedLocation;

    //Get DAO session
    private DaoSession daoSession;

    //CurrentLocation
    private CurrentLocationDao locationDao;
    private Query<CurrentLocation> locationQuery;

    //Destination
    private DestinationDao destinationDao;
    private Query<Destination> destinationQuery;


    //MARK:- Main methods of this activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drwDrawer = findViewById(R.id.activity_main);
        rvDestination = findViewById(R.id.rvDestination);
        fabAdd = findViewById(R.id.fabAdd);
        toggleDrawer = new ActionBarDrawerToggle(this, drwDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drwDrawer.addDrawerListener(toggleDrawer);
        toggleDrawer.syncState();


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //initialize navigation drawer
        nvDrawer = findViewById(R.id.nv_drawer);
        setDrawerForNv();

        //auto hide fab when scroll rev
        onRevScroll();

        //set listener for fab
        fabOnClick();

        //start DataAccess service
        startDataAccessService();


        //create sample list
        initList();

        //check permission
        checkLocationPermission();

        if (isAllowedLocation) {
            //start GeoLocation service
//            startGeoService();
        } else {
            Log.d("PERM", "Not allowed");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggleDrawer.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == ADD_ACTIVITY_RESULT) {
                    assert data != null;
                    Destination destination = (Destination) data.getSerializableExtra("destination");
                    listDes.add(destination);
                    destinationDao.insert(destination);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                isAllowedLocation = true;
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(destReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter destIntent = new IntentFilter("get_destination");
        registerReceiver(destReceiver, destIntent);
    }


    //MARK:- Support methods

    //Start DataAccess service
    private void startDataAccessService() {
        Intent intent = new Intent(getApplicationContext(), DataAccess.class);
        startService(intent);
    }

    //Start GeoLocation service
    private void startGeoService() {
        Intent intent = new Intent(getApplicationContext(), GeoService.class);
        startService(intent);
    }

    //Get destination list from SQLite with greenDAO
    private void initList() {
        destReceiver = new DestReceiver();
        IntentFilter destIntent = new IntentFilter("get_destination");
        registerReceiver(destReceiver, destIntent);

        daoSession = ((App) getApplication()).getDaoSession();
        locationDao = daoSession.getCurrentLocationDao();
        destinationDao = daoSession.getDestinationDao();
    }

    private void onRevScroll() {
        rvDestination.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabAdd.getVisibility() == View.VISIBLE) {
                    fabAdd.hide();
                } else if (dy < 0 && fabAdd.getVisibility() != View.VISIBLE) {
                    fabAdd.show();
                } else {
                    fabAdd.show();
                }
            }
        });
    }

    private void setDrawerForNv() {
        nvDrawer.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.it_account:
                    Toast.makeText(MainActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.it_all:
                    Toast.makeText(MainActivity.this, "All", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.it_logout:
                    finish();
                    break;
                default:
                    return true;
            }
            return true;

        });
    }

    private void fabOnClick() {
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivityForResult(intent, ADD_ACTIVITY_RESULT);
        });
    }


    //check CurrentLocation Permission
    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION);
            isAllowedLocation = false;
        } else {
            isAllowedLocation = true;
        }
    }
}

package com.example.alimentopreview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import adapters.DestinationAdapter;
import model.Destination;

public class MainActivity extends AppCompatActivity {


    //MARK:- UI Preferences
    private DrawerLayout drwDrawer;
    private ActionBarDrawerToggle toggleDrawer;
    private NavigationView nvDrawer;
    private RecyclerView rvDestination;
    private FloatingActionButton fabAdd;

    //MARK:- Data
    private ArrayList<Destination> listDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drwDrawer = findViewById(R.id.activity_main);
        rvDestination = findViewById(R.id.rvDestination);
        fabAdd = findViewById(R.id.fabAdd);
        toggleDrawer = new ActionBarDrawerToggle(this, drwDrawer,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drwDrawer.addDrawerListener(toggleDrawer);
        toggleDrawer.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //initialize navigation drawer
        nvDrawer = findViewById(R.id.nv_drawer);
        setDrawerForNv();

        //create sample list
        initList();

        //set adapter for rev
        DestinationAdapter adapter = new DestinationAdapter(listDes);
        rvDestination.setAdapter(adapter);
        rvDestination.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //auto hide fab when scroll rev
        onRevScroll();

        //set listener for fab
        fabOnClick();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggleDrawer.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void initList() {
        listDes = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            listDes.add(new Destination(String.valueOf(System.currentTimeMillis()),
                    "Nhà hàng Hiếu Béo",
                    "Số 5, ngõ 77, Bùi Xương Trạch, quận Thanh Xuân",
                    "default"));
        }
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
        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.it_account:
                        Toast.makeText(MainActivity.this, "My Account",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.it_all:
                        Toast.makeText(MainActivity.this, "All",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.it_logout:
                        finish();
                        break;
                    default:
                        return true;
                }
                return true;

            }
        });
    }

    private void fabOnClick() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
}

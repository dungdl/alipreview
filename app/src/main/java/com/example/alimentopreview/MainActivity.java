package com.example.alimentopreview;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    //MARK:- UI Preferences
    private DrawerLayout drwDrawer;
    private ActionBarDrawerToggle toggleDrawer;
    private NavigationView nvDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drwDrawer = (DrawerLayout)findViewById(R.id.activity_main);
        toggleDrawer = new ActionBarDrawerToggle(this, drwDrawer,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drwDrawer.addDrawerListener(toggleDrawer);
        toggleDrawer.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nvDrawer = (NavigationView)findViewById(R.id.nv_drawer);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggleDrawer.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}

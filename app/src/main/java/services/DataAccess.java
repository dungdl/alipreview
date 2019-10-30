package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;

import api.App;
import model.CurrentLocation;
import model.CurrentLocationDao;
import model.DaoSession;
import model.Destination;
import model.DestinationDao;

public class DataAccess extends Service {

    //MARK:- Get DAO session
    private DaoSession daoSession;

    //MARK:- DAO for models

    //CurrentLocation
    private CurrentLocationDao locationDao;
    private Query<CurrentLocation> locationQuery;
//
//    //Destination
    private DestinationDao destinationDao;
    private Query<Destination> destinationQuery;

    //MARK:- Data
    private ArrayList<Destination> listDest;

    //MARK:- Overrided methods from service

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        daoSession = ((App) getApplication()).getDaoSession();
        locationDao = daoSession.getCurrentLocationDao();
        destinationDao = daoSession.getDestinationDao();

        //Query all Destination
        destinationQuery = destinationDao.queryBuilder().build();
        initListDestination();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //MARK:- Support methods
    private void initListDestination() {
        listDest = (ArrayList<Destination>) destinationQuery.list();
        Intent itListDest = new Intent("get_destination");
        itListDest.putExtra("list_destination",listDest);
        sendBroadcast(itListDest);
    }
}

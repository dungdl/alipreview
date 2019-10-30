package model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class Destination implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    private String name;
    private String address;
    private String picture;

    private String locationId;

    @ToOne(joinProperty = "locationId")
    private CurrentLocation currentLocation;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 682200642)
    private transient DestinationDao myDao;

    @Generated(hash = 631469120)
    public Destination(String id, String name, String address, String picture,
            String locationId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.picture = picture;
        this.locationId = locationId;
    }

    @Generated(hash = 703305004)
    public Destination() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Generated(hash = 1232470112)
    private transient String currentLocation__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 143469741)
    public CurrentLocation getCurrentLocation() {
        String __key = this.locationId;
        if (currentLocation__resolvedKey == null
                || currentLocation__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CurrentLocationDao targetDao = daoSession.getCurrentLocationDao();
            CurrentLocation currentLocationNew = targetDao.load(__key);
            synchronized (this) {
                currentLocation = currentLocationNew;
                currentLocation__resolvedKey = __key;
            }
        }
        return currentLocation;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1360616320)
    public void setCurrentLocation(CurrentLocation currentLocation) {
        synchronized (this) {
            this.currentLocation = currentLocation;
            locationId = currentLocation == null ? null : currentLocation.getId();
            currentLocation__resolvedKey = locationId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 169553300)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDestinationDao() : null;
    }

    }

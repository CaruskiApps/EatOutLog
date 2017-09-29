package com.caruski.eatoutlog.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.caruski.eatoutlog.domain.Restaurant;

import java.util.ArrayList;
import java.util.List;


/**
 * TODO: Make this package private with Dagger
 */
public class RestaurantRepositoryImpl extends AbstractRepository implements RestaurantRepository {

    //Constants for identifying table and columns
    private static final String TABLE_RESTAURANTS = "restaurants";
    private static final String REST_NAME = "restName";
    private static final String REST_CITY = "restCity";
    private static final String REST_STATE = "restState";
    private static final String REST_FIRST_VISIT = "firstVisit";

    //SQL to create table
    private static final String REST_TABLE_CREATE =
            "CREATE TABLE " + TABLE_RESTAURANTS + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    REST_NAME + " TEXT, " +
                    REST_CITY + " TEXT, " +
                    REST_STATE + " TEXT, " +
                    REST_FIRST_VISIT + " TEXT default CURRENT_DATE" +
                    ")";

    public RestaurantRepositoryImpl(Context context) {
        super(context);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create required tables
        db.execSQL(REST_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        //create new tables
        onCreate(db);
    }

    @Override
    public long createRestaurant(Restaurant restaurant){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(REST_NAME, restaurant.getName());
        values.put(REST_CITY, restaurant.getCity());
        values.put(REST_STATE, restaurant.getState());

        long newId = db.insert(TABLE_RESTAURANTS, null, values);

        return newId;
    }

    @Override
    public Restaurant getRestaurant(long rest_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANTS + " WHERE " + KEY_ID +
                " = " + rest_id;

        Cursor c = db.rawQuery(selectQuery, null);
        if(c != null){
            c.moveToFirst();
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setId(c.getLong(c.getColumnIndex(KEY_ID)));
        restaurant.setName(c.getString(c.getColumnIndex(REST_NAME)));
        restaurant.setState(c.getString(c.getColumnIndex(REST_STATE)));
        restaurant.setCity(c.getString(c.getColumnIndex(REST_CITY)));
        restaurant.setLastVisit(c.getString(c.getColumnIndex(REST_FIRST_VISIT)));

        c.close();

        return restaurant;
    }

    @Override
    public List<Restaurant> getAllRestaurants(){
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANTS + " order by restName";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //loop through all rows and add to List
        if(c.moveToFirst()){
            do{
                Restaurant restaurant = new Restaurant();
                restaurant.setId(c.getLong(c.getColumnIndex(KEY_ID)));
                restaurant.setName(c.getString(c.getColumnIndex(REST_NAME)));
                restaurant.setState(c.getString(c.getColumnIndex(REST_STATE)));
                restaurant.setCity(c.getString(c.getColumnIndex(REST_CITY)));
                restaurant.setLastVisit(c.getString(c.getColumnIndex(REST_FIRST_VISIT)));
                //add to list
                restaurants.add(restaurant);
            }while(c.moveToNext());
        }

        c.close();

        return restaurants;
    }
    @Override
    public void deleteRestaurant(long rest_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURANTS, KEY_ID + " = ?",
                new String[] {String.valueOf(rest_id)});
    }
}

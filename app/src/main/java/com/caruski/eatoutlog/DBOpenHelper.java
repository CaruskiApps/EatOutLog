package com.caruski.eatoutlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class DBOpenHelper extends SQLiteOpenHelper{

    //Constants for DB name and version
    private static final String DATABASE_NAME = "restLog.db";
    private static final int DATABASE_VERSION = 9;
    private SQLiteDatabase dbHelper;

    //Constants for identifying table and columns
    public static final String KEY_ID = "_id";

    public static final String TABLE_RESTAURANTS = "restaurants";
    public static final String REST_NAME = "restName";
    public static final String REST_CITY = "restCity";
    public static final String REST_STATE = "restState";
    public static final String REST_FIRST_VISIT = "firstVisit";

    public static final String TABLE_DISHES = "dishes";
    public static final String REST_ID = "restID";
    public static final String DISH_NAME = "dishName";
    public static final String LOOK = "look";
    public static final String TASTE = "taste";
    public static final String TEXTURE = "texture";
    public static final String DATE_ADDED = "dateAdded";
    public static final String COMMENTS = "comments";

    public static final String TABLE_IMAGES = "images";
    public static final String DISH_ID = "dishID";
    public static final String FILENAME = "filename";
    public static final String IMAGE_DATA = "imageData";

    //SQL to create table
    private static final String REST_TABLE_CREATE =
            "CREATE TABLE " + TABLE_RESTAURANTS + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    REST_NAME + " TEXT, " +
                    REST_CITY + " TEXT, " +
                    REST_STATE + " TEXT, " +
                    REST_FIRST_VISIT + " TEXT default CURRENT_DATE" +
                    ")";
    private static final String DISHES_TABLE_CREATE =
            "CREATE TABLE " + TABLE_DISHES + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    REST_ID + " INTEGER, " +
                    DISH_NAME + " TEXT, " +
                    LOOK + " DOUBLE, " +
                    TASTE + " DOUBLE, " +
                    TEXTURE + " DOUBLE, " +
                    COMMENTS + " TEXT " +
                    DATE_ADDED + " TEXT default CURRENT_DATE" +
                    ")";
    private static final String IMAGES_TABLE_CREATE =
            "CREATE TABLE " + TABLE_IMAGES + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    DISH_ID + " INTEGER, " +
                    FILENAME + " TEXT " +
                    IMAGE_DATA + " BLOB" +
                    ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create required tables
        db.execSQL(REST_TABLE_CREATE);
        db.execSQL(DISHES_TABLE_CREATE);
        db.execSQL(IMAGES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        //create new tables
        onCreate(db);
    }

    //------------"Restaurant" table methods------------------------//

    //Create new restaurant
    public long createRestaurant(Restaurant restaurant){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(REST_NAME, restaurant.getName());
        values.put(REST_CITY, restaurant.getCity());
        values.put(REST_STATE, restaurant.getState());

        long _id = db.insert(TABLE_RESTAURANTS, null, values);

        return _id;
    }

    //get single restaurant
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
    //get all restaurants
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
    //delete restaurant
    public void deleteRestaurant(long rest_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURANTS, KEY_ID + " = ?",
                new String[] {String.valueOf(rest_id)});
    }

    //------------------"Dish" table methods----------------//
    //Create dish
    public long createDish(Dish dish){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(REST_ID, dish.getRestId());
        values.put(DISH_NAME, dish.getName());
        values.put(LOOK, dish.getLook());
        values.put(TASTE, dish.getTaste());
        values.put(TEXTURE, dish.getTexture());
        values.put(COMMENTS, dish.getComments());

        long _id = db.insert(TABLE_DISHES, null, values);

        return _id;
    }
    //get single dish
    public Dish getDish(long dish_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_DISHES +  " WHERE " + KEY_ID + " = " + dish_id;

        Cursor c = db.rawQuery(selectQuery, null);
        if(c != null){
            c.moveToFirst();
        }

        Dish dish = new Dish();
        dish.setId(c.getLong(c.getColumnIndex(KEY_ID)));
        dish.setRestId(c.getLong(c.getColumnIndex(REST_ID)));
        dish.setName(c.getString(c.getColumnIndex(DISH_NAME)));
        dish.setLook(c.getFloat(c.getColumnIndex(LOOK)));
        dish.setTaste(c.getFloat(c.getColumnIndex(TASTE)));
        dish.setTexture(c.getFloat(c.getColumnIndex(TEXTURE)));
        dish.setComments((c.getString(c.getColumnIndex(COMMENTS))));

        c.close();

        return dish;
    }
    //get all dishes that match a restId
    public List<Dish> getAllDishes(long restId){
        List<Dish> dishes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_DISHES + " WHERE " + REST_ID + " = " + restId ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //loop through all rows and add to List
        if(c.moveToFirst()){
            do{
                Dish dish = new Dish();
                dish.setId(c.getLong(c.getColumnIndex(KEY_ID)));
                dish.setRestId(c.getLong(c.getColumnIndex(REST_ID)));
                dish.setName(c.getString(c.getColumnIndex(DISH_NAME)));
                dish.setLook(c.getFloat(c.getColumnIndex(LOOK)));
                dish.setTaste(c.getFloat(c.getColumnIndex(TASTE)));
                dish.setTexture(c.getFloat(c.getColumnIndex(TEXTURE)));
                dish.setComments(c.getString(c.getColumnIndex(COMMENTS)));
//                dish.setDate_added(c.getString(c.getColumnIndex(DATE_ADDED)));
                //add to list
                dishes.add(dish);
            }while(c.moveToNext());
        }

        c.close();

        return dishes;
    }
    //update dish
    public int updateDish(Dish dish){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DISH_NAME, dish.getName());
        values.put(LOOK, dish.getLook());
        values.put(TASTE, dish.getTaste());
        values.put(TEXTURE, dish.getTexture());
        values.put(COMMENTS, dish.getComments());
//        values.put(DATE_ADDED, dish.getDate_added());

        return db.update(TABLE_DISHES, values, KEY_ID + " = ?",
                new String[] {String.valueOf(dish.getId())});
    }
    //get number of dishes for given restaurant ID
    public int getDishCount(long restId){
        int count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_DISHES +
                " WHERE " + REST_ID + " = " + restId;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor != null) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
    //delete dish
    public void deleteDish(long dishId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISHES, KEY_ID + " = " + dishId, null);
        db.close();
    }

    //------------------------Image Table Methods---------------------//
    //Create image
    public long createImage(Image image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DISH_ID, image.getDishId());
        values.put(FILENAME, image.getFilename());
        values.put(IMAGE_DATA, image.getData());

        long _id = db.insert(TABLE_IMAGES, null, values);

        return _id;
    }
    //get single image
    public Image getImage(long imageId){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_IMAGES +  " WHERE " + KEY_ID + " = " + imageId;

        Cursor c = db.rawQuery(selectQuery, null);
        if(c != null){
            c.moveToFirst();
        }

        Image image = new Image();
        image.setId(c.getLong(c.getColumnIndex(KEY_ID)));
        image.setDishId(c.getLong(c.getColumnIndex(DISH_ID)));
        image.setData(c.getBlob(c.getColumnIndex(IMAGE_DATA)));
        image.setFilename(c.getString(c.getColumnIndex(FILENAME)));

        c.close();

        return image;
    }
    //get all images for given dishId
    public List<Image> getAllImages(long dishId){
        List<Image> images = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_IMAGES + " WHERE " + DISH_ID + " = " + dishId ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //loop through all rows and add to List
        if(c.moveToFirst()){
            do{
                Image image = new Image();
                image.setId(c.getLong(c.getColumnIndex(KEY_ID)));
                image.setDishId(c.getLong(c.getColumnIndex(DISH_ID)));
                image.setData(c.getBlob(c.getColumnIndex(IMAGE_DATA)));
                image.setFilename(c.getString(c.getColumnIndex(FILENAME)));

                //add to list
                images.add(image);
            }while(c.moveToNext());
        }

        c.close();

        return images;
    }
}
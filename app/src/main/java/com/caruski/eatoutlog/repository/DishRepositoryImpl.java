package com.caruski.eatoutlog.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.caruski.eatoutlog.domain.Dish;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;


@Module
class DishRepositoryImpl extends AbstractRepository implements DishRepository {

    private static final String TABLE_DISHES = "dishes";
    private static final String REST_ID = "restID";
    private static final String DISH_NAME = "dishName";
    private static final String LOOK = "look";
    private static final String TASTE = "taste";
    private static final String TEXTURE = "texture";
    private static final String DATE_ADDED = "dateAdded";
    private static final String COMMENTS = "comments";

    //SQL to create table
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

    DishRepositoryImpl(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create required tables
        db.execSQL(DISHES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISHES);
        //create new tables
        onCreate(db);
    }

    @Override
    public long createDish(Dish dish) {
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

    @Override
    public Dish getDish(long dish_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_DISHES + " WHERE " + KEY_ID + " = " + dish_id;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
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

    @Override
    public List<Dish> getAllDishes(long restId) {
        List<Dish> dishes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_DISHES + " WHERE " + REST_ID + " = " + restId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //loop through all rows and add to List
        if (c.moveToFirst()) {
            do {
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
            } while (c.moveToNext());
        }

        c.close();

        return dishes;
    }

    @Override
    public int updateDish(Dish dish) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DISH_NAME, dish.getName());
        values.put(LOOK, dish.getLook());
        values.put(TASTE, dish.getTaste());
        values.put(TEXTURE, dish.getTexture());
        values.put(COMMENTS, dish.getComments());
//        values.put(DATE_ADDED, dish.getDate_added());

        return db.update(TABLE_DISHES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(dish.getId())});
    }

    @Override
    public int getDishCount(long restId) {
        int count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_DISHES +
                " WHERE " + REST_ID + " = " + restId;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor != null) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    @Override
    public void deleteDish(long dishId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISHES, KEY_ID + " = " + dishId, null);
        db.close();
    }
}

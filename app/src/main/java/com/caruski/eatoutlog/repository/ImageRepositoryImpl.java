package com.caruski.eatoutlog.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.caruski.eatoutlog.domain.Image;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;


@Module
class ImageRepositoryImpl extends AbstractRepository implements ImageRepository {

    private static final String TABLE_IMAGES = "images";
    private static final String DISH_ID = "dishID";
    private static final String FILENAME = "filename";
    private static final String IMAGE_DATA = "imageData";

    //SQL to create table
    private static final String IMAGES_TABLE_CREATE =
            "CREATE TABLE " + TABLE_IMAGES + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    DISH_ID + " INTEGER, " +
                    FILENAME + " TEXT " +
                    IMAGE_DATA + " BLOB" +
                    ")";

    ImageRepositoryImpl(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create required tables
        db.execSQL(IMAGES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        //create new tables
        onCreate(db);
    }

    @Override
    public long createImage(Image image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DISH_ID, image.getDishId());
        values.put(FILENAME, image.getFilename());
        values.put(IMAGE_DATA, image.getData());

        long _id = db.insert(TABLE_IMAGES, null, values);

        return _id;
    }

    @Override
    public Image getImage(long imageId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_IMAGES + " WHERE " + KEY_ID + " = " + imageId;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
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

    @Override
    public List<Image> getAllImages(long dishId) {
        List<Image> images = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_IMAGES + " WHERE " + DISH_ID + " = " + dishId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //loop through all rows and add to List
        if (c.moveToFirst()) {
            do {
                Image image = new Image();
                image.setId(c.getLong(c.getColumnIndex(KEY_ID)));
                image.setDishId(c.getLong(c.getColumnIndex(DISH_ID)));
                image.setData(c.getBlob(c.getColumnIndex(IMAGE_DATA)));
                image.setFilename(c.getString(c.getColumnIndex(FILENAME)));

                //add to list
                images.add(image);
            } while (c.moveToNext());
        }

        c.close();

        return images;
    }
}

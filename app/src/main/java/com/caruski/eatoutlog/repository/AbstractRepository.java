package com.caruski.eatoutlog.repository;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by brand on 9/26/2017.
 */

public abstract class AbstractRepository extends SQLiteOpenHelper {

    //Constants for DB name and version
    private static final String DATABASE_NAME = "restLog.db";
    private static final int DATABASE_VERSION = 9;

    protected static final String KEY_ID = "_id";

    protected AbstractRepository(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, errorHandler);
    }

    protected AbstractRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}

package com.caruski.eatoutlog.repository;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteOpenHelper;

import static com.caruski.eatoutlog.constants.Constants.DATABASE_NAME;
import static com.caruski.eatoutlog.constants.Constants.DATABASE_VERSION;

abstract class AbstractRepository extends SQLiteOpenHelper {

    protected static final String KEY_ID = "_id";

    protected AbstractRepository(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, errorHandler);
    }

    protected AbstractRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}

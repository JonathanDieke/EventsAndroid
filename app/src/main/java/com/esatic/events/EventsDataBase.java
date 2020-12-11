package com.esatic.events;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static android.provider.BaseColumns._ID;
import static com.esatic.events.Constantes.TABLE_NAME;
import static com.esatic.events.Constantes.TIME;
import static com.esatic.events.Constantes.TITLE;

import androidx.annotation.Nullable;

public class EventsDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "events.db" ;
    private static final int DATABASE_VERSION = 1;

    public EventsDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                    " (" + _ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            TIME+ " TEXT NOT NULL," +
                            TITLE + " TEXT NOT NULL" +
                    "   );"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

package com.example.alejandro.miniwhacamole;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlaBD extends SQLiteOpenHelper {

    public ControlaBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    String sql = "CREATE TABLE Alumno(" +
            "nombre TEXT, " +
            "promedio INTEGER" +
            ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Alumno");
        db.execSQL(sql);
    }
}
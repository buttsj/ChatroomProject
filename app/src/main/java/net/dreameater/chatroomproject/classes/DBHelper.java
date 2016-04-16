package net.dreameater.chatroomproject.classes;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "crud.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ROOM = "CREATE TABLE " + Room.TABLE + "("
                + Room.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Room.KEY_name + " TEXT, "
                + Room.KEY_lat + " DOUBLE, "
                + Room.KEY_long + " DOUBLE )";
        db.execSQL(CREATE_TABLE_ROOM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Room.TABLE);
        onCreate(db);
    }
}

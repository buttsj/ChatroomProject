package net.dreameater.chatroomproject.classes;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomRepo {
    private DBHelper dbHelper;

    public RoomRepo(Context context)
    {
        dbHelper = new DBHelper(context);
    }

    public int insert(Room room) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Room.KEY_name, room.roomName);
        values.put(Room.KEY_lat, room.latitude);
        values.put(Room.KEY_long, room.longitude);

        long room_Id = db.insert(Room.TABLE, null, values);
        db.close();
        return (int) room_Id;
    }

    public void delete(int room_Id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Room.TABLE, Room.KEY_ID + "= ?", new String[] { String.valueOf(room_Id) });
        db.close();
    }

    public void update(Room room)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Room.KEY_name, room.roomName);
        values.put(Room.KEY_lat, room.latitude);
        values.put(Room.KEY_long, room.longitude);


        db.update(Room.TABLE, values, Room.KEY_ID + "= ?", new String[]{String.valueOf(room.room_ID)});
        db.close();
    }

    public ArrayList<HashMap<String, String>>  getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Room.KEY_ID + "," +
                Room.KEY_name + "," +
                Room.KEY_lat + "," +
                Room.KEY_long +
                " FROM " + Room.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> roomList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> room = new HashMap<String, String>();
                room.put("id", cursor.getString(cursor.getColumnIndex(Room.KEY_ID)));
                room.put("name", cursor.getString(cursor.getColumnIndex(Room.KEY_name)));
                roomList.add(room);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return roomList;

    }

    public Room getRoomById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Room.KEY_ID + "," +
                Room.KEY_name + "," +
                Room.KEY_lat + "," +
                Room.KEY_long +
                " FROM " + Room.TABLE
                + " WHERE " +
                Room.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );
        String name = "";
        int id = 0;
        double latitude = 0;
        double longitude = 0;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(Room.KEY_ID));
                name = cursor.getString(cursor.getColumnIndex(Room.KEY_name));
                latitude = cursor.getDouble(cursor.getColumnIndex(Room.KEY_lat));
                longitude = cursor.getDouble(cursor.getColumnIndex(Room.KEY_long));

            } while (cursor.moveToNext());
        }
        Room room = new Room(name, latitude, longitude);
        room.room_ID = id;
        cursor.close();
        db.close();
        return room;
    }

}

package net.dreameater.chatroomproject;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import net.dreameater.chatroomproject.classes.CustomAdapter;
import net.dreameater.chatroomproject.classes.Room;

import java.util.ArrayList;
import java.util.List;

// THIS IS THE LOBBY (CONTAINS THE "ROOMS")


public class LobbyActivity extends AppCompatActivity {

    private ListView lv;
    private CustomAdapter ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // store the top bar here
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lobby");
        //



        // store the listview and rooms here
        lv = (ListView) findViewById(R.id.listView);
        ArrayList<Room> roomList = new ArrayList<>();
        roomList.add(new Room("Dreese Labs", "red"));
        roomList.add(new Room("Caldwell", "red"));
        roomList.add(new Room("Bolz", "red"));
        roomList.add(new Room("Baker Systems", "red"));
        roomList.add(new Room("Hitchcock", "red"));
        roomList.add(new Room("Science and Eng Library", "red"));
        roomList.add(new Room("RPAC", "red"));
        roomList.add(new Room("University Hall", "red"));
        roomList.add(new Room("Thompson Library", "red"));
        roomList.add(new Room("Knowlton School of Architecture", "red"));
        roomList.add(new Room("Fisher School of Business", "red"));
        roomList.add(new Room("Cockins Hall", "red"));
        roomList.add(new Room("Scott Laboratory", "red"));
        roomList.add(new Room("Evans Laboratory", "red"));
        roomList.add(new Room("Celeste Laboratory", "red"));

        // create array adapter to populate the listview
        /*final ArrayAdapter<Room> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                roomList );*/

        ad = new CustomAdapter(this, roomList);

        // create array adapter to populate the listview
        /*final ArrayAdapter<Room> arrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.customlist,
                R.id.RoomItem,
                roomList );*/

        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Room selectedRoom = (Room) parent.getItemAtPosition(position);
                Intent i = new Intent(LobbyActivity.this, ChatroomActivity.class);
                i.putExtra("Room", selectedRoom);
                startActivity(i);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Log.d("test", "long click");
                ad.getItem(arg2).setImg("green");
                lv.setAdapter(ad);
                return true;
            }
        });

        if (isLocationEnabled(this)){
                Log.d("test", "Location services are enabled");
        }
        else {
            Snackbar snack = Snackbar.make(findViewById(R.id.listView), "Enable Location Service", Snackbar.LENGTH_INDEFINITE);
            snack.show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                startActivity(new Intent(LobbyActivity.this, AccountActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lobby, menu);
        return true;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }
        else{
            return false;
        }
    }

}

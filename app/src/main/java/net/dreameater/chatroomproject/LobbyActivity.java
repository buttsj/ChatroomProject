package net.dreameater.chatroomproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.dreameater.chatroomproject.classes.Room;

import java.util.ArrayList;
import java.util.List;

// THIS IS THE LOBBY (CONTAINS THE "ROOMS")


public class LobbyActivity extends AppCompatActivity {

    private ListView lv;

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

        // store the listview and rooms here
        lv = (ListView) findViewById(R.id.listView);
        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room("Dreese Labs"));
        roomList.add(new Room("Caldwell"));
        roomList.add(new Room("Bolz"));
        roomList.add(new Room("Baker Systems"));
        roomList.add(new Room("Hitchcock"));
        roomList.add(new Room("Science and Eng Library"));
        roomList.add(new Room("RPAC"));
        roomList.add(new Room("University Hall"));
        roomList.add(new Room("Thompson Library"));
        roomList.add(new Room("Knowlton School of Architecture"));
        roomList.add(new Room("Fisher School of Business"));
        roomList.add(new Room("Cockins Hall"));
        roomList.add(new Room("Scott Laboratory"));
        roomList.add(new Room("Evans Laboratory"));
        roomList.add(new Room("Celeste Laboratory"));

        // create array adapter to populate the listview
        final ArrayAdapter<Room> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                roomList );

        lv.setAdapter(arrayAdapter);
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

package net.dreameater.chatroomproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.dreameater.chatroomproject.classes.CustomAdapter;
import net.dreameater.chatroomproject.classes.Room;

import java.util.ArrayList;

// THIS IS THE LOBBY (CONTAINS THE "ROOMS")


public class LobbyActivity extends AppCompatActivity implements LocationListener {

    private ListView lv;
    private CustomAdapter ad;
    private LocationManager locationManager;

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

        // create the list of Rooms
        ArrayList<Room> roomList = new ArrayList<>();
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

        // create custom adapter (to put Green/Red dot and text on same line
        ad = new CustomAdapter(this, roomList);

        // apply custom adapter to the ListView
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Room selectedRoom = (Room) parent.getItemAtPosition(position);
                if (selectedRoom.isWithinRange()) {
                    Intent i = new Intent(LobbyActivity.this, ChatroomActivity.class);
                    i.putExtra("Room", selectedRoom);
                    startActivity(i);
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Room selectedRoom = (Room)parent.getItemAtPosition(position);
                selectedRoom.setImg("green");
                lv.setAdapter(ad);
                return true;
            }
        });

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria cri = new Criteria();
        //cri.setAccuracy(Criteria.ACCURACY_HIGH);
        String provider = locationManager.getBestProvider(cri, true);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (provider != null && !provider.equals("")){
            Toast.makeText(getApplicationContext(), provider, Toast.LENGTH_SHORT).show();
            Location location = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 2000, 1, this);
            if (location != null) {
                onLocationChanged(location);
            }
            else {
                Toast.makeText(getApplicationContext(), "Location not found", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Provider is null", Toast.LENGTH_LONG).show();
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
            case R.id.action_refresh:
                // refresh happens here (check for Rooms)
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lobby, menu);
        return true;
    }

    @Override
    public void onLocationChanged(Location location)
    {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {
    }

    @Override
    public void onProviderEnabled(String s)
    {
    }

    @Override
    public void onProviderDisabled(String s)
    {
    }

}

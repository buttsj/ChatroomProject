package net.dreameater.chatroomproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.dreameater.chatroomproject.classes.CustomAdapter;
import net.dreameater.chatroomproject.classes.Room;
import net.dreameater.chatroomproject.classes.RoomRepo;

import java.util.ArrayList;
import java.util.HashMap;

// THIS IS THE LOBBY (CONTAINS THE "ROOMS")


public class LobbyActivity extends AppCompatActivity {

    private ArrayList<Room> roomList;
    private ListView lv;
    private CustomAdapter ad;
    public Location lastLocation;
    public LocationManager locationManager;
    public double longitude;
    public double latitude;
    WifiManager wifi;
    public RoomRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);

        repo = new RoomRepo(this);
        if (repo.checkIfExists(this))
        {
            Log.d("TAG", "Database is loaded properly");
        } else
        {
            Log.d("TAG", "New database, load rooms.");
            repo.insert(new Room("Dreese Labs", 40.002446, -83.015817, 0));
            repo.insert(new Room("Caldwell", 40.002238, -83.015035, 1));
            repo.insert(new Room("Bolz", 39.999063, -83.017339, 2));
            repo.insert(new Room("Baker Systems", 40.001591, -83.015910, 3));
            repo.insert(new Room("Hitchcock", 40.003864, -83.015003, 4));
            repo.insert(new Room("Eighteenth Avenue Library", 40.001654, -83.013330, 5));
            repo.insert(new Room("RPAC", 40.001036, -83.012976, 6));
            repo.insert(new Room("University Hall", 40.000728, -83.013515, 7));
            repo.insert(new Room("Thompson Library", 39.999063, -83.017339, 8));
            repo.insert(new Room("Knowlton Hall", 40.004068, -83.017095, 9));
            repo.insert(new Room("Fisher Hall", 40.005166, -83.016008, 10));
            repo.insert(new Room("Cockins Hall", 40.001246, -83.015011, 11));
            repo.insert(new Room("Scott Laboratory", 40.002239, -83.014110, 12));
            repo.insert(new Room("Evans Laboratory", 40.002779, -83.011099, 13));
            repo.insert(new Room("Celeste Laboratory", 40.002164, -83.011224, 14));
        }

        // store the top bar here
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lobby");
        //

        // create the list of Rooms (name of room, latitude, longitude)
        roomList = repo.getRoomList();

        // create custom adapter (to put Green/Red dot and text on same line
        ad = new CustomAdapter(this, roomList);

        // apply custom adapter to the ListView
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(ad);

        // set listeners for the ListView items
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
                Room selectedRoom = (Room) parent.getItemAtPosition(position);
                selectedRoom.setImg("green");
                lv.setAdapter(ad);
                return true;
            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationListener);
        }
        else
        {
            startActivity(new Intent(LobbyActivity.this, MainScreen.class));
        }
    }

    @Override
    protected void onResume() {
        for(int i =0; i<roomList.size(); i++){
            Room room = roomList.get(i);
            room.chatTool.history.clear();
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationListener);
        }
        else
        {
            startActivity(new Intent(LobbyActivity.this, MainScreen.class));
        }
        if (!wifi.isWifiEnabled())
        {
            startActivity(new Intent(LobbyActivity.this, MainScreen.class));
        }
        super.onResume();

        Log.d("TAG", "onResume");
    }

    @Override
    protected void onPause() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.removeUpdates(locationListener);
        super.onPause();
        Log.d("TAG", "onPause");
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
                if (!wifi.isWifiEnabled())
                {
                    startActivity(new Intent(LobbyActivity.this, MainScreen.class));
                }
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    checkRooms(lastLocation);
                }
                else
                {
                    startActivity(new Intent(LobbyActivity.this, MainScreen.class));
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lobby, menu);
        return true;
    }


    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lastLocation = location;
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            Snackbar.make(findViewById(R.id.listView), String.valueOf(latitude + " " + longitude), Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private void checkRooms(Location location) {
        if (location != null)
        {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            for (int i = 0; i < roomList.size(); i++)
            {
                if (roomList.get(i).gpsCheck(latitude, longitude))
                {
                    roomList.get(i).setImg("green");
                }
                else
                {
                    roomList.get(i).setImg("red");
                }
            }
            lv.setAdapter(ad);
        }
    }

}

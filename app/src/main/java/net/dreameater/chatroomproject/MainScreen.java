package net.dreameater.chatroomproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

// THIS IS THE SPLASH SCREEN

public class MainScreen extends AppCompatActivity {

    boolean connect;
    WifiManager wifi;
    LocationManager locationManager;
    Snackbar wifiMessage;
    Snackbar gpsMessage;
    Snackbar comboMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        connect = false;
        wifiMessage = Snackbar.make(findViewById(R.id.button), "Please enable your WiFi.", Snackbar.LENGTH_INDEFINITE);
        comboMessage = Snackbar.make(findViewById(R.id.button), "Enable WiFi and GPS!", Snackbar.LENGTH_INDEFINITE);
        gpsMessage = Snackbar.make(findViewById(R.id.button), "Please enable your GPS.", Snackbar.LENGTH_INDEFINITE);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        // CHECK GPS AND WIFI
        if (wifi.isWifiEnabled() && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            connect = true;
        }
        else
        {
            connect = false;
        }
        if (!wifi.isWifiEnabled() && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            comboMessage.show();
        }
        else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsMessage.show();
        }
        else if (!wifi.isWifiEnabled()) {
            wifiMessage.show();
        }
        // store the top bar here
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().hide();


        Button btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connect();
            }
        });
    }

    @Override
    protected void onResume() {
        if (!wifi.isWifiEnabled())
        {
            connect = false;
            wifiMessage.show();
        }
        else
        {
            connect = true;
            wifiMessage.dismiss();
        }
        super.onResume();
    }

    private void Connect()
    {
        if (wifi.isWifiEnabled() && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            connect = true;
        }
        else
        {
            connect = false;
        }
        if (!wifi.isWifiEnabled() && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            comboMessage.show();
        }
        else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsMessage.show();
        }
        else if (!wifi.isWifiEnabled()) {
            wifiMessage.show();
        }
        if (connect)
        {
            startActivity(new Intent(MainScreen.this, LobbyActivity.class));
        }
    }
}

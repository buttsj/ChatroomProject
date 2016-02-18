package net.dreameater.chatroomproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        List<String> roomList = new ArrayList<>();
        roomList.add("Dreese Labs");
        roomList.add("Caldwell");
        roomList.add("Bolz");
        roomList.add("Baker Systems");
        roomList.add("Hitchcock");
        roomList.add("Science and Eng Library");
        roomList.add("RPAC");
        roomList.add("University Hall");
        roomList.add("Thompson Library");
        roomList.add("Knowlton School of Architecture");
        roomList.add("Fisher School of Business");
        roomList.add("Cockins Hall");
        roomList.add("Scott Laboratory");
        roomList.add("Evans Laboratory");
        roomList.add("Celeste Laboratory");

        // create array adapter to populate the listview
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                roomList );

        lv.setAdapter(arrayAdapter);
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


}

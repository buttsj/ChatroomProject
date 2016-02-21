package net.dreameater.chatroomproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.dreameater.chatroomproject.classes.Message;
import net.dreameater.chatroomproject.classes.Room;

import java.util.Calendar;

public class ChatroomActivity extends AppCompatActivity {

    private Room selectedRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        // store the top bar here
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        Intent i = getIntent();
        if (i.hasExtra("Room"))
        {
            selectedRoom = (Room)i.getSerializableExtra("Room");
            getSupportActionBar().setTitle(selectedRoom.toString());
        }




        Button btn = (Button)findViewById(R.id.button);

        /*EditText txt = (EditText) findViewById(R.id.editText);
        txt.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // send message
                    Message msg = new Message("message", Calendar.getInstance().getTimeInMillis());
                    return true;
                }
                return false;
            }
        });*/


        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send message
                Message msg = new Message("message", Calendar.getInstance().getTimeInMillis());
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}

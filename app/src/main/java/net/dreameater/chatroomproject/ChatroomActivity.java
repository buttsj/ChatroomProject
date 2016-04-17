package net.dreameater.chatroomproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.interceptors.ParseLogInterceptor;

import net.dreameater.chatroomproject.classes.CustomAdapter2;
import net.dreameater.chatroomproject.classes.Message;
import net.dreameater.chatroomproject.classes.Room;

import java.util.ArrayList;
import java.util.List;

public class ChatroomActivity extends AppCompatActivity {

    private Room selectedRoom; // Current room
    private ListView lv; // The listview holding the List below
    private List<Message> storedMessages; // List of messages ("history")
    private EditText txt;
    boolean firstLoad;
    public CustomAdapter2 arrayAdapter;

    static final int POLL_INTERVAL = 100;
    Handler handler = new Handler();
    Runnable RefreshMessagesRunnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this, POLL_INTERVAL);
        }
    };

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

        ParseObject.registerSubclass(Message.class);
        // UPDATE WITH PARSE ACCOUNT INFO
        ParseUser.enableAutomaticUser();

        if (ParseUser.getCurrentUser() != null) {
            startWithCurrentUser();
        }
        else {
            login();
        }

        handler.postDelayed(RefreshMessagesRunnable, POLL_INTERVAL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                clearMessages();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    void startWithCurrentUser() {
        setupMessagePosting();
    }

    void setupMessagePosting() {
        // Find the text field and button
        txt = (EditText) findViewById(R.id.chat_box);
        lv = (ListView) findViewById(R.id.listView2);
        storedMessages = new ArrayList<>();
        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lv.setTranscriptMode(1);
        firstLoad = true;
        final String userId = ParseUser.getCurrentUser().getObjectId();
        arrayAdapter = new CustomAdapter2(ChatroomActivity.this, "Anon", storedMessages);
        lv.setAdapter(arrayAdapter);

        txt.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER) && !(txt.getText().toString().equals(""))) {
                    ParseObject parseMessage = ParseObject.create(Message.class);
                    parseMessage.put(Message.USER_ID_KEY, "ANON");
                    parseMessage.put(Message.BODY_KEY, txt.getText().toString());
                    parseMessage.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            // message done
                            refreshMessages();
                            Log.d("TAG", "Message refreshed!");
                        }
                    });
                    txt.setText(null);
                    return true;
                }
                return false;
            }
        });
    }

    void clearMessages() {
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.findInBackground(new FindCallback<Message>() {
            @Override
            public void done(List<Message> objects, ParseException e) {
                for (int i = 0; i < objects.size(); i++)
                {
                    objects.get(i).deleteInBackground();
                }
                storedMessages.clear();
            }
        });
    }


    void refreshMessages() {
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.setLimit(50);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Message>() {
            @Override
            public void done(List<Message> messages, com.parse.ParseException e) {
                if (e == null)
                {
                    storedMessages.clear();
                    storedMessages.addAll(messages);
                    arrayAdapter.notifyDataSetChanged();
                    if (firstLoad)
                    {
                        lv.setSelection(arrayAdapter.getCount() -1 );
                        firstLoad = false;
                    }
                } else {
                    // message failed
                }
            }
        });
    }

    void login()
    {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, com.parse.ParseException e) {
                if (e != null) {
                    Log.e("TAG", "Anonymous login failed: " , e);
                } else {
                    startWithCurrentUser();
                }
            }
        });
    }
}

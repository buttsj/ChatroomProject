package net.dreameater.chatroomproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.interceptors.ParseLogInterceptor;

import net.dreameater.chatroomproject.classes.CustomAdapter2;
import net.dreameater.chatroomproject.classes.Message;
import net.dreameater.chatroomproject.classes.Room;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatroomActivity extends AppCompatActivity {

    private Room selectedRoom; // Current room
    private ListView lv; // The listview holding the List below
    private List<Message> storedMessages; // List of messages ("history")
    private EditText txt;
    private static final int SERVERPORT = 7500;
    private static final String SERVER_IP = "164.107.15.230";
    boolean firstLoad;
    public CustomAdapter2 arrayAdapter;
    public Button btnSend;
    //private static final String SERVER_IP = "192.168.0.117";

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
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("myAppId") // should correspond to APP_ID env variable
                .addNetworkInterceptor(new ParseLogInterceptor())
                .server("http://ohiostateroom.herokuapp.com/parse/").build());
        ParseUser.enableAutomaticUser();
        // https://myparseapp.herokuapp.com/parse/
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
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void updateChatLog() {
        lv = (ListView) findViewById(R.id.listView2);
        storedMessages = new ArrayList<>();
        for (Message m : selectedRoom.currentChat().retreiveHistory())
        {
            storedMessages.add(m);
        }

        final ArrayAdapter<Message> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                storedMessages);

        lv.setAdapter(arrayAdapter);
    }

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String response = "";

        MyClientTask(String addr, int port){
            dstAddress = addr;
            dstPort = port;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Socket socket = null;
            try {
                socket = new Socket(dstAddress, dstPort);
                ByteArrayOutputStream byteArrayOutputStream =
                        new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];

                int bytesRead;
                InputStream inputStream = socket.getInputStream();

    /*
     * notice:
     * inputStream.read() will block if no data return
     */
                while ((bytesRead = inputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");
                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            }finally{
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        /*@Override
        protected void onPostExecute(Void result) {
            Message msg = new Message(response, Calendar.getInstance().getTimeInMillis());
            selectedRoom.sendMessage(msg);
            updateChatLog();
            super.onPostExecute(result);
        }*/
    }

    void startWithCurrentUser() {
        setupMessagePosting();
    }

    void setupMessagePosting() {
        // Find the text field and button
        txt = (EditText) findViewById(R.id.chat_box);
        lv = (ListView) findViewById(R.id.listView2);
        btnSend = (Button) findViewById(R.id.btnSend);
        storedMessages = new ArrayList<>();
        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lv.setTranscriptMode(1);
        firstLoad = true;
        final String userId = ParseUser.getCurrentUser().getObjectId();
        arrayAdapter = new CustomAdapter2(ChatroomActivity.this, "Anon", storedMessages);
        //arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, storedMessages );
        lv.setAdapter(arrayAdapter);

        // When send button is clicked, create message object on Parse
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Message msg = new Message(txt.getText().toString(), Calendar.getInstance().getTimeInMillis());
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
                //selectedRoom.sendMessage(msg);
                txt.setText(null);
                //updateChatLog();
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
                    // clear ListView
                    // add "messages" back
                    // mAdapter.notifyDataSetChanged();
                } else {
                    // messages failed to load
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

package net.dreameater.chatroomproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import net.dreameater.chatroomproject.classes.Message;
import net.dreameater.chatroomproject.classes.Room;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatroomActivity extends AppCompatActivity {

    private Room selectedRoom; // Current room
    private ListView lv; // The listview holding the List below
    private List<Message> storedMessages; // List of messages ("history")
    private static final int SERVERPORT = 7500;
    private static final String SERVER_IP = "192.168.0.117";

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

        final EditText txt = (EditText) findViewById(R.id.chat_box);
        txt.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER) && !(txt.getText().toString().equals(""))) {
                    MyClientTask myClientTask = new MyClientTask(
                            SERVER_IP,
                            SERVERPORT);
                    myClientTask.execute();
                    // send message
                    Message msg = new Message(txt.getText().toString(), Calendar.getInstance().getTimeInMillis());
                    selectedRoom.sendMessage(msg);
                    txt.getText().clear();
                    updateChatLog();
                    return true;
                }
                return false;
            }
        });

        // store the listview and rooms here
        lv = (ListView) findViewById(R.id.listView2);
        storedMessages = new ArrayList<>();

        // create array adapter to populate the listview
        final ArrayAdapter<Message> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                storedMessages );

        lv.setAdapter(arrayAdapter);
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
                storedMessages );

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

        @Override
        protected void onPostExecute(Void result) {
            Message msg = new Message(response, Calendar.getInstance().getTimeInMillis());
            selectedRoom.sendMessage(msg);
            updateChatLog();
            super.onPostExecute(result);
        }
    }
}

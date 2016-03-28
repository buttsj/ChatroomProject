package net.dreameater.chatroomproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import net.dreameater.chatroomproject.classes.Message;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;

/**
 * Created by Patrick on 3/28/2016.
 */
public class SocketClient extends Activity {

    private Socket socket;
    private static final int SERVERPORT = 7500;
    private static final String SERVER_IP = "164.107.21.140";

    private boolean established = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new Thread(new ClientThread()).start();

        while (!established) {
            established = true;
        }
    }

    public void onClick(View view){
        try{
            EditText txt = (EditText) findViewById(R.id.chat_box);
            String str = txt.getText().toString();
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            out.println(str);

        }catch(UnknownHostException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    class ClientThread implements Runnable{

        @Override
        public void run(){
            try{
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
                established = true;
            }catch(UnknownHostException e1){
                e1.printStackTrace();
            }catch(IOException e1){
                e1.printStackTrace();
            }
        }
    }
}

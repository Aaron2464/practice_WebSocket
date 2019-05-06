package com.kerker.practice_websocket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final int SOCKET_CLIENT_CONNECT_TIMEOUT = 10 * 1000; // in milliseconds
    private static final int SOCKET_CLIENT_READ_TIMEOUT = 10 * 1000; // in milliseconds
    private static final int SOCKET_RETRY_COUNT_LIMIT = 30;
    private static final int SOCKET_PORT = 34918;
    private static final String SOCKET_IP = "192.168.0.1";

    private EditText mEtxtRoomId;
    private Button mBtnSend;
    private Button mBtnConnect;

    private String mRoomId;
    private String mCommand;

    private ExecutorService mService;
    private Socket mSocket;
    Object object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = Executors.newCachedThreadPool();

        mEtxtRoomId = findViewById(R.id.eTxtRoomId);
        mBtnSend = findViewById(R.id.btnSend);
        mBtnConnect = findViewById(R.id.btnConnect);

        mRoomId = mEtxtRoomId.getText().toString();
        mCommand = "0x10000001";

        mBtnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateSocket();
            }
        });

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendMsgSocket(mRoomId, mCommand);
            }
        });
    }

    private void onCreateSocket() {
        mService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket(SOCKET_IP, SOCKET_PORT);
                    Log.e("Aaron", "Socket Connect: " + mSocket.isConnected());
                } catch (IOException e) {
                    Log.e("Aaron", "Socket Connect: " + mSocket.isConnected());
                    e.printStackTrace();
                }
            }
        });
    }

    private void onSendMsgSocket(final String roomId, final String command) {
        object = new Object();
        mService.execute(new Runnable() {
            @Override
            public void run() {
                //
//        try {
//            mSocket = new Socket(SOCKET_IP,SOCKET_PORT);
//            mSocket.isConnected();
//            InputStream inputStream = mSocket.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            bufferedReader.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

                try {
                    OutputStream outputStream = mSocket.getOutputStream();

                    BufferedWriter bOut;
                    PrintWriter pOut;
                    DataOutputStream dOut;

//                    bOut = new BufferedWriter(new OutputStreamWriter(outputStream));
//                    bOut.write(roomId+"\n");
//                    bOut.write(command+ "\n");
//                    bOut.flush();
//                    bOut.close();

//                    pOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)), true);
//                    pOut.println(roomId+"\n");
//                    pOut.println((command+"\n"));
//                    pOut.flush();
//                    pOut.close();
//
//                    dOut = new DataOutputStream(outputStream);
//                    dOut.writeChars(roomId);
//                    dOut.write((command + "\n").getBytes());
//                    dOut.flush();
//                    dOut.close();

//                    outputStream.write((roomId).getBytes());
//                    outputStream.write((command+"\0").getBytes());
//                    outputStream.flush();
//                    outputStream.close();

                    RoomProto.RoomData.Builder roomProto = RoomProto.RoomData.newBuilder();
                    roomProto.setCommand(command);
                    roomProto.setRoomId(roomId);
                    roomProto.build().writeTo(mSocket.getOutputStream());

                    mSocket.close();
                    Log.e("Aaorn", "Socket close: " + mSocket.isConnected());
                } catch (IOException e) {
                    Log.e("Aaorn", "Socket close: " + mSocket.isConnected());
                    e.printStackTrace();
                }
            }
        });
    }
}

//
//#define PCMSG_SET_ROOM  0x10000001
//
//        typedef struct
//        {
//        char  pdata[64];
//        unsigned long type;
//        unsigned long length;
//        } PCMSG_Head;
//
//        port:0x8866 = "34918"                // Port
//        head->type = PCMSG_SET_ROOM // CMD
//        head->pdata= "2010101010102"   //Room ID
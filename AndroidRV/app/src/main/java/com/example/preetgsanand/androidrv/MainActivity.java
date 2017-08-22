package com.example.preetgsanand.androidrv;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String firebase_url = "https://chatmessage-3e4eb.firebaseio.com/messages";
    public FloatingActionButton fab;
    private ArrayList<Chat> chats;
    private Firebase ref;
    private RecyclerView recyclerView;
    private CustomChatListAdapter customChatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        chats = new ArrayList<>();
        initializeFirebase();
        initialiseUI();
    }

    public void initializeFirebase() {
        ref = new Firebase(firebase_url);
        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                chats.add(chat);
                refreshAdapter();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void sendMessage(Chat chat) {
        ref.child(chat.getTo()).setValue(chat);
    }

    public void initialiseUI() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View dialogView = View.inflate(getApplicationContext(),R.layout.dialog_view,null);
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(dialogView);
                dialog.show();

                Button submit = (Button) dialogView.findViewById(R.id.submit);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText to = (EditText) dialogView.findViewById(R.id.to);
                        EditText msg = (EditText) dialogView.findViewById(R.id.msg);
                        if(to.getText().toString() != "" && msg.getText().toString() != "") {
                            Chat chat = new Chat();
                            chat.setFrom("Test Name");
                            chat.setTo(to.getText().toString());
                            chat.setMsg(msg.getText().toString());
                            sendMessage(chat);
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "Details not properly entered",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        customChatListAdapter = new CustomChatListAdapter(MainActivity.this,chats);
        recyclerView.setAdapter(customChatListAdapter);
    }

    public void refreshAdapter() {
        customChatListAdapter = new CustomChatListAdapter(MainActivity.this,
                chats);
        recyclerView.setAdapter(customChatListAdapter);
    }

}

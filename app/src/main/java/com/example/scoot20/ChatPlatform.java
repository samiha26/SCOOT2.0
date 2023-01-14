package com.example.scoot20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.scoot20.databinding.ActivityChatPlatformBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ChatPlatform extends AppCompatActivity {
    ActivityChatPlatformBinding binding;
    String fullName;
    DatabaseReference databaseReferenceSender, databaseReferenceReceiver;
    String senderRoom, receiverRoom;
    MessageAdapter messageAdapter;
    MessagingMod messagingMod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatPlatformBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fullName = getIntent().getStringExtra("Full Name");

        senderRoom = FirebaseAuth.getInstance().getUid()+fullName;
        receiverRoom = fullName+FirebaseAuth.getInstance().getUid();

        messageAdapter = new MessageAdapter(this);
        binding.recycler.setAdapter(messageAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("Messages").child(senderRoom);
        databaseReferenceReceiver = FirebaseDatabase.getInstance().getReference("Messages").child(receiverRoom);


        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MessagingMod messagingMod = dataSnapshot.getValue(MessagingMod.class);
                    messageAdapter.add(messagingMod);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.editTxtMessage.getText().toString();
                if(message.trim().length()>0){
                    sendMessage(message);
                }
            }
        });
    }

    private void sendMessage(String message) {
        String messageID = UUID.randomUUID().toString();
        MessagingMod messagingMod = new MessagingMod(messageID, FirebaseAuth.getInstance().getUid(),message);

        messageAdapter.add(messagingMod);

        databaseReferenceSender.child(messageID).setValue(messagingMod);
        databaseReferenceReceiver.child(messageID).setValue(messagingMod);
        }
}

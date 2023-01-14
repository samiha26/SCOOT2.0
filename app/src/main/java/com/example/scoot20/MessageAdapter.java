package com.example.scoot20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{

    private Context context;
    private List<MessagingMod> messageModelList;

    public MessageAdapter(Context context) {
        this.context = context;
        messageModelList = new ArrayList<>();
    }

    public void add(MessagingMod readWriteUserDetails){
        messageModelList.add(readWriteUserDetails);
        notifyDataSetChanged();
    }

    public void clear(){
        messageModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row,parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessagingMod messagingMod = messageModelList.get(position);
        holder.msg.setText(messagingMod.getMessage());
        if (messagingMod.getFullName().equals(FirebaseAuth.getInstance().getUid())){
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.msg.setTextColor(context.getResources().getColor(R.color.white));
        } else{
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.msg.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView msg;
        private LinearLayout main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.chatmessage);
            main = itemView.findViewById(R.id.mainMessageLayout);

        }
    }
}

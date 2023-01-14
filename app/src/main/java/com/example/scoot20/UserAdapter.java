package com.example.scoot20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

    private Context context;
    private List<ReadWriteUserDetails> readWriteUserDetailsList;

    public UserAdapter(Context context) {
        this.context = context;
        readWriteUserDetailsList=new ArrayList<>();
    }

    public void add(ReadWriteUserDetails readWriteUserDetails){
        readWriteUserDetailsList.add(readWriteUserDetails);
        notifyDataSetChanged();
    }

    public void clear(){
        readWriteUserDetailsList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message,parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ReadWriteUserDetails readWriteUserDetails = readWriteUserDetailsList.get(position);
        holder.fullName.setText(readWriteUserDetails.getFullName());
        holder.email.setText(readWriteUserDetails.getEmailAddress());
    }

    @Override
    public int getItemCount() {
        return readWriteUserDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView fullName, email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.fullName);
            email = itemView.findViewById(R.id.email);

        }
    }
}

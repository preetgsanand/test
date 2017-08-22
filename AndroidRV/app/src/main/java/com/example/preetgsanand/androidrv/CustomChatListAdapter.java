package com.example.preetgsanand.androidrv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by preetgsanand on 8/22/17.
 */

public class CustomChatListAdapter extends RecyclerView.Adapter<CustomChatListAdapter.MyViewHolder> {

    private List<Chat> chats;
    private Context context;
    public CustomChatListAdapter(Context context,List<Chat> chats) {
        this.chats = chats;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.custom_chat_item,
                parent,
                false
        );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.from.setText(chats.get(position).getFrom());
        holder.msg.setText(chats.get(position).getMsg());

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView from;
        public TextView msg;

        public MyViewHolder(View itemView) {
            super(itemView);
            from = (TextView) itemView.findViewById(R.id.from);
            msg = (TextView) itemView.findViewById(R.id.msg);

        }
    }
}
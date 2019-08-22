package com.anand.alcatraz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private ArrayList receivedArrayList;
    private Context context;

    public ListAdapter(ArrayList receivedArrayList, Context context) {
        this.receivedArrayList = receivedArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_text, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.MyViewHolder myViewHolder, int position) {

        Log.i(ListAdapter.class.getName(), "onBindViewHolder: "+myViewHolder.textView+" mmmm"+receivedArrayList);

        myViewHolder.textView.setText(receivedArrayList.get(position).toString());

    }


    @Override
    public int getItemCount() {
        return receivedArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.num_value);
        }
    }
}

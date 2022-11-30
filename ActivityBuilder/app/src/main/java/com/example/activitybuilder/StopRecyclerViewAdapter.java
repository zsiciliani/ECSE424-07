package com.example.activitybuilder;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.activitybuilder.model.Stop;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class StopRecyclerViewAdapter extends RecyclerView.Adapter<StopRecyclerViewAdapter.ViewHolder> {
    private List<Stop> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    StopRecyclerViewAdapter(Context context, List<Stop> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.stop_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String stopLocation = mData.get(position).getLocation();
        holder.textView.setText(stopLocation);
        boolean isPaired = mData.get(position).isPaired();
        if (isPaired) {
            LinearLayout.LayoutParams warninglp = new LinearLayout.LayoutParams(0,0,0);
            holder.warningButton.setLayoutParams(warninglp);
            LinearLayout.LayoutParams textlp = new LinearLayout.LayoutParams(holder.textView.getLayoutParams());
            textlp.weight = 3;
            holder.textView.setLayoutParams(textlp);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        Button cloneButton;
        Button deleteButton;
        ImageButton warningButton;

        ViewHolder(View itemView) {
            super(itemView);
            warningButton = itemView.findViewById(R.id.noPairWarning);
            textView = itemView.findViewById(R.id.tvStopRow);
            cloneButton = itemView.findViewById(R.id.btnCloneStopRow);
            deleteButton = itemView.findViewById(R.id.btnDeleteStopRow);
            itemView.setOnClickListener(this);
            warningButton.setOnClickListener(this);
            textView.setOnClickListener(this);
            cloneButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == textView.getId()) {
                System.out.println("Calling onClick for textView");
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            } else if (view.getId() == cloneButton.getId()) {
                System.out.println("Calling onClick for cloneButton");
                if (mClickListener != null) mClickListener.clone(view, getAdapterPosition());
            } else if (view.getId() == deleteButton.getId()) {
                System.out.println("Calling onClick for deleteButton");
                if (mClickListener != null) mClickListener.delete(view, getAdapterPosition());
            } else if (view.getId() == warningButton.getId()) {
                System.out.println("Clicked on warning button");
                noPair(view);
            }
        }
    }

    //Warn that stop has never been paired with an NFC tag
    public void noPair(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setCancelable(true);
        builder.setMessage("You have not paired this stop to an NFC tag!");
        builder.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // convenience method for getting data at click position
    Stop getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void delete(View view, int position);
        void clone(View view, int position);
    }
}

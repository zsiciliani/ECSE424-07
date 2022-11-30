package com.example.activitybuilder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.activitybuilder.model.Event;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {
    private List<Event> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    EventRecyclerViewAdapter(Context context, List<Event> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.event_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String eventName = mData.get(position).getName();
        holder.textView.setText(eventName);
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

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvEventRow);
            cloneButton = itemView.findViewById(R.id.btnCloneEventRow);
            deleteButton = itemView.findViewById(R.id.btnDeleteEventRow);
            itemView.setOnClickListener(this);
            textView.setOnClickListener(this);
            cloneButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            System.out.println("On click was called for view with id " + view.getId());
            if (view.getId() == textView.getId()) {
                System.out.println("Calling onClick for textView");
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            } else if (view.getId() == cloneButton.getId()) {
                System.out.println("Calling onClick for cloneButton");
                //TODO if (mClickListener != null) mClickListener.clone(view, getAdapterPosition());
            } else if (view.getId() == deleteButton.getId()) {
                System.out.println("Calling onClick for deleteButton");
                if (mClickListener != null) mClickListener.delete(view, getAdapterPosition());
            }
        }
    }

    // convenience method for getting data at click position
    Event getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);

        void delete(View view, int adapterPosition);
    }
}

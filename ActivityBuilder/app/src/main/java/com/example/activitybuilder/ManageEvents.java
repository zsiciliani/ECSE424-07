package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activitybuilder.model.Event;

import java.util.ArrayList;
import java.util.List;

public class ManageEvents extends AppCompatActivity implements EventRecyclerViewAdapter.ItemClickListener {

    EventRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_events);

        List<Event> allEvents = Event.getAllEvents(getApplicationContext());

        TextView tvNoActivities = (TextView) findViewById(R.id.tvNoActivities);
        if (allEvents.size() == 0) {
            tvNoActivities.setVisibility(View.VISIBLE);
        } else {
            tvNoActivities.setVisibility(View.INVISIBLE);
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventRecyclerViewAdapter(this, allEvents);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(ManageEvents.this, EditEvent.class);
        intent.putExtra("event_id", adapter.getItem(position).getEventId());
        startActivity(intent);
    }

    public void finish(View view) {
        this.finish();
    }
}
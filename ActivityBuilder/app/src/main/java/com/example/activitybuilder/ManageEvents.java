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
        intent.putExtra("event_name", adapter.getItem(position).getName());
        intent.putExtra("event_SL", adapter.getItem(position).getStartingLocation());
        intent.putExtra("event_duration", adapter.getItem(position).getDuration());
        intent.putExtra("event_date", adapter.getItem(position).getDate());
        intent.putExtra("event_description", adapter.getItem(position).getDescription());

        startActivity(intent);
    }

    public void finish(View view) {
        this.finish();
    }

    public void returnHome(View view) {
        startActivity(new Intent(ManageEvents.this, MainActivity.class));
    }

}
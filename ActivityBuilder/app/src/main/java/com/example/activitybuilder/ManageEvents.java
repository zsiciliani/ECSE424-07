package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ManageEvents extends AppCompatActivity implements EventRecyclerViewAdapter.ItemClickListener {

    EventRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_events);

        ArrayList<String> eventNames = new ArrayList<>();
        eventNames.add("Event 1");
        eventNames.add("Event 2");
        eventNames.add("Event 3");
        eventNames.add("Event 4");
        eventNames.add("Event 5");
        eventNames.add("Event 1");
        eventNames.add("Event 2");
        eventNames.add("Event 3");
        eventNames.add("Event 4");
        eventNames.add("Event 5");
        eventNames.add("Event 1");
        eventNames.add("Event 2");
        eventNames.add("Event 3");
        eventNames.add("Event 4");
        eventNames.add("Event 5");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventRecyclerViewAdapter(this, eventNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void finish(View view) {
        this.finish();
    }
}
package com.example.activitybuilder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activitybuilder.model.Event;
import com.example.activitybuilder.model.Stop;


import java.util.List;

public class ManageStops extends AppCompatActivity implements StopRecyclerViewAdapter.ItemClickListener {
    long eventId;
    StopRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_stops);

        Intent intent = getIntent();
        eventId = intent.getLongExtra("event_id", 0);

        List<Stop> allStops = Stop.getStopsByEventId(getApplicationContext(), eventId);

        TextView tvNoActivities = (TextView) findViewById(R.id.tvNoActivities);
        if (allStops.size() == 0) {
            tvNoActivities.setVisibility(View.VISIBLE);
        } else {
            tvNoActivities.setVisibility(View.INVISIBLE);
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvStops);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StopRecyclerViewAdapter(this, allStops);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ManageStops.this, EditStop.class);
        intent.putExtra("stop_id", adapter.getItem(position).getStopId());
        intent.putExtra("event_id", adapter.getItem(position).getStopEventId());
        intent.putExtra("content", adapter.getItem(position).getContentUrl());
        intent.putExtra("location", adapter.getItem(position).getLocation());
        startActivity(intent);
    }

    @Override
    public void delete(View view, int position) {
        System.out.println("You called the delete method for Stop with ID " + adapter.getItem(position).getStopId());
    }

    @Override
    public void clone(View view, int position) {
        System.out.println("You called the clone method for Stop with ID " + adapter.getItem(position).getStopId());
    }

    public void finish(View view) {
        startActivity(new Intent(ManageStops.this, EditEvent.class));
    }

    public void addStop(View view){
        Intent intent = new Intent(ManageStops.this, CreateStop.class);
        intent.putExtra("event_id", eventId);
        System.out.println("Creating stop from manageStops for eventId " + eventId);
        startActivity(intent);
    }

    public void returnHome(View view) {
        startActivity(new Intent(ManageStops.this, MainActivity.class));
    }

    public void noPair(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage("You have not paired this stop to an NFC tag!");
        builder.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Event.deleteEventById(getApplicationContext(),eventId);
                        startActivity(new Intent(ManageStops.this, ManageStops.class));
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
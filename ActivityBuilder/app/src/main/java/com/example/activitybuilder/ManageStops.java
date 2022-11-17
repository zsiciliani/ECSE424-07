package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activitybuilder.model.Stop;

import java.util.List;

public class ManageStops extends AppCompatActivity implements StopRecyclerViewAdapter.ItemClickListener {

    int eventId;
    StopRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_stops);

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
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void finish(View view) {
        this.finish();
    }
}
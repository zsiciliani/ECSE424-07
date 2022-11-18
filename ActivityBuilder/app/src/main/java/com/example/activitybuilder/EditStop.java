package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.activitybuilder.model.Event;
import com.example.activitybuilder.model.Stop;

public class EditStop extends AppCompatActivity {
    long stopId;
    long eventId;
    String contentUrl;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stop);

        Intent intent = getIntent();
        eventId = intent.getLongExtra("event_id", 0);
        stopId = intent.getLongExtra("stop_id", 0);
        contentUrl = intent.getStringExtra("content");
        location = intent.getStringExtra("location");

        EditText locationText = (EditText) findViewById(R.id.locationText);
        locationText.setText(location);

        EditText contentUrlText = (EditText) findViewById(R.id.contentText);
        contentUrlText.setText(String.valueOf(contentUrl));

    }

    public void finish(View view) {
        this.finish();
    }

    public void pairNFC(View view) {
        startActivity(new Intent(EditStop.this, PairNfcTag.class));
    }

    public void save(View view){
        String content = ((TextView) this.findViewById(R.id.contentText)).getText().toString();
        String location = ((TextView) this.findViewById(R.id.locationText)).getText().toString();

        Stop stop = Stop.updateStop(getApplicationContext(), stopId, eventId, content, location);
        Intent intent = new Intent(EditStop.this, MainActivity.class);
        startActivity(intent);
    }

    public void delete(View view){
        Stop.deleteStopById(getApplicationContext(),stopId);
        startActivity(new Intent(EditStop.this, MainActivity.class));
    }
}
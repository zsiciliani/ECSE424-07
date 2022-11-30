package com.example.activitybuilder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.activitybuilder.model.Event;
import com.example.activitybuilder.model.Stop;

public class CreateStop extends AppCompatActivity {
    long eventId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_stop);

        Intent intent = getIntent();
        eventId = intent.getLongExtra("event_id", 0);
    }

    public void finish(View view) {
        this.finish();
    }

    public void returnHome(View view) {
        startActivity(new Intent(CreateStop.this, MainActivity.class));
    }

    public void pairNFC(View view) {
        EditText location_name = findViewById(R.id.locationField);
        String location = ((TextView) this.findViewById(R.id.locationField)).getText().toString();

        if (location.length() == 0) {
            location_name.setError("This field is required");
        } else{
            String content = ((TextView) this.findViewById(R.id.contentField)).getText().toString();
            System.out.println("About to create stop with eventId " + eventId);
            Stop stop = Stop.createStop(getApplicationContext(),eventId,content,location);

            Intent intent = new Intent(CreateStop.this, PairNfcTag.class);
            intent.putExtra("stop_id", stop.getStopId());
            intent.putExtra("stop_content", stop.getContentUrl());
            startActivity(intent);
        }
    }

    public void save(View view){
        EditText location_name = findViewById(R.id.locationField);
        String location = ((TextView) this.findViewById(R.id.locationField)).getText().toString();

        if (location.length() == 0) {
            location_name.setError("This field is required");
        } else{
            String content = ((TextView) this.findViewById(R.id.contentField)).getText().toString();
            System.out.println("About to create stop with eventId " + eventId);
            Stop stop = Stop.createStop(getApplicationContext(),eventId,content,location);

            Intent intent = new Intent(CreateStop.this, ManageStops.class);
            intent.putExtra("event_id", eventId);
            startActivity(intent);
        }

    }


    public void information(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage("Pair this stop with an NFC tag to add the content URL that will be used at this stop.");
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

}
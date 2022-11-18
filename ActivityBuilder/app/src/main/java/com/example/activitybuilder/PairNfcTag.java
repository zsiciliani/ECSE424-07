package com.example.activitybuilder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activitybuilder.model.Stop;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class PairNfcTag extends AppCompatActivity {

    public static final String Error_Detected = "No NFC Tag Detected";
    public static final String Write_Success = "Text Written Successfully";
    public static final String Write_Error = "Error during writing, try again";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writingTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;
    Button ActivateButton;

    String stopContent;
    long stopId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair_nfc_tag);

        Intent intent = getIntent();
        stopId = intent.getLongExtra("stop_id", 0);
        stopContent = intent.getStringExtra("stop_content");

        System.out.println("Stop content is " + stopContent);
        ActivateButton = findViewById(R.id.ActivateButton);
        context = this;
        ActivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (myTag == null) {
                        Toast.makeText(context, Error_Detected, Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println("Writing stop content " + stopContent);
                        write(stopContent, myTag);
                        Stop.setStopToPaired(getApplicationContext(), stopId);
                        Toast.makeText(context, Write_Success, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PairNfcTag.this, SuccessfulPair.class));
                    }
                } catch (IOException e) {
                    Toast.makeText(context, Write_Error, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (FormatException e) {
                    Toast.makeText(context, Write_Error, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(context, "This device does not support NFC", Toast.LENGTH_SHORT).show();
            //finish
        }
        readFromIntent(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writingTagFilters = new IntentFilter[] { tagDetected };
    }

    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i ++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }

    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;

        String text = "";

        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 0063;

        try {
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }
    }

    private void write(String text, Tag tag) throws IOException, FormatException {
        NdefRecord[] records = { createRecord(text) };
        NdefMessage message = new NdefMessage(records);

        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];

        payload[0] = (byte) langLength;

        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        return recordNFC;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        readFromIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        writeModeOff();
    }

    @Override
    protected void onResume() {
        super.onResume();
        writeModeOn();
    }

    private void writeModeOn() {
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writingTagFilters, null);
    }

    private void writeModeOff() {
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }

    public void finish(View view) {
        this.finish();
    }

    public void returnHome(View view) {
        startActivity(new Intent(PairNfcTag.this, MainActivity.class));
    }

}
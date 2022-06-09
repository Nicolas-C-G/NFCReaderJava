package com.nicolas.nfcreaderjava;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.nicolas.nfcreaderjava.log.Log;
import com.nicolas.nfcreaderjava.nfc.se.desfire.InvalidParameterException;

public class MainActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback {
    private static final String TAG = "MainActivity";
    private final LibraryReader libraryReader = new LibraryReader(this);
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        TextView logTextView = findViewById(R.id.logTextView);
        Log.setLogTextView(logTextView);
        Log.reset(TAG, "onCreate()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
        nfcAdapter.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, null);
        Log.i(TAG, "NFC adapter enabled. Waiting for a card...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
        nfcAdapter.disableReaderMode(this);
        Log.i(TAG, "NFC adapter disabled.");
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        Log.reset(TAG, "onTagDiscovered()");
        try {
            libraryReader.processTag(tag);
        } catch (InvalidParameterException e) {
            Log.i(TAG, e.getMessage());
        }
    }
}

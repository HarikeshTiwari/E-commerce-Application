package com.example.techapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NoteHandle extends AppCompatActivity {

    TextView tvNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_handle);

        tvNot=findViewById(R.id.tvNot);
        tvNot.setText("Technology is going to change the way of living");
    }
}
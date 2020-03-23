package com.example.dronecontroller;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button up;
    private Button left;
    private Button right;
    private Button down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        up = findViewById(R.id.btn_up);
        left = findViewById(R.id.btn_left);
        right = findViewById(R.id.btn_right);
        down = findViewById(R.id.btn_down);

        up.setOnClickListener(v -> {

        });

        left.setOnClickListener(v -> {

        });

        right.setOnClickListener(v -> {

        });

        down.setOnClickListener(v -> {

        });
    }
}

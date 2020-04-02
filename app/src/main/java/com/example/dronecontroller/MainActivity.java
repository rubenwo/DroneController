package com.example.dronecontroller;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dronecontroller.Services.Conn.Messages.ThrottleMessage;
import com.example.dronecontroller.Services.Conn.TcpManagerService;

public class MainActivity extends AppCompatActivity {
    private TcpManagerService tcpManagerService;

    private SeekBar throttle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tcpManagerService = TcpManagerService.getInstance();
        tcpManagerService.subscribeToErrorEvents(error -> Log.d("ERROR_TAG", "" + error));
        tcpManagerService.subscribeToMessageEvents(message -> Log.d("MESSAGE_TAG", "" + message));

        throttle = findViewById(R.id.seekBar);
        throttle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tcpManagerService.submitMessage(new ThrottleMessage(progress, progress, progress, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}

package com.example.dronecontroller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dronecontroller.Services.Conn.Messages.InfoMessage;
import com.example.dronecontroller.Services.Conn.Messages.ThrottleMessage;
import com.example.dronecontroller.Services.Conn.TcpManagerService;

public class MainActivity extends AppCompatActivity {
    private TcpManagerService tcpManagerService;

    private SeekBar throttle;


    private TextView batt_level_tv;
    private TextView angle_x_tv;
    private TextView angle_y_tv;
    private TextView angle_z_tv;

    private Button take_off_btn;
    private Button kill_btn;
    private Button preflight_btn;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batt_level_tv = findViewById(R.id.batt_level_tv);
        angle_x_tv = findViewById(R.id.angle_x_tv);
        angle_y_tv = findViewById(R.id.angle_y_tv);
        angle_z_tv = findViewById(R.id.angle_z_tv);
        throttle = findViewById(R.id.seekBar);
        take_off_btn = findViewById(R.id.take_off_btn);
        kill_btn = findViewById(R.id.kill_btn);
        preflight_btn = findViewById(R.id.test_btn);

        tcpManagerService = TcpManagerService.getInstance();
        tcpManagerService.subscribeToErrorEvents(error -> Log.d("ERROR_TAG", "" + error));
        tcpManagerService.subscribeToMessageEvents(message -> {
            Handler mainHandler = new Handler(getApplicationContext().getMainLooper());

            if (message instanceof InfoMessage) {
                mainHandler.post(() -> {
                    batt_level_tv.setText(String.format("Battery Level: %d", ((InfoMessage) message).getBattery_level()));
                    angle_x_tv.setText(String.format("Angle X: %d", ((InfoMessage) message).getAngleX()));
                    angle_y_tv.setText(String.format("Angle Y: %d", ((InfoMessage) message).getAngleY()));
                    angle_z_tv.setText(String.format("Angle Z: %d", ((InfoMessage) message).getAngleZ()));
                });
                Log.d("MESSAGE_TAG", "message: " + message.toString());
            }
        });

        take_off_btn.setOnClickListener(v -> {
            throttle.setProgress(50, true);
            tcpManagerService.submitMessage(new ThrottleMessage(50, 0, 0, 0));
        });
        kill_btn.setOnClickListener(v -> {
            throttle.setProgress(0, true);
            tcpManagerService.submitMessage(new ThrottleMessage(0, 0, 0, 0));
        });
        preflight_btn.setOnClickListener(v -> {
            throttle.setProgress(12, true);
            tcpManagerService.submitMessage(new ThrottleMessage(12, 0, 0, 0));

        });
        throttle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    tcpManagerService.submitMessage(new ThrottleMessage(progress, 0, 0, 0));
                Log.d("SEEKBAR_TAG", "onProgressChanged: " + fromUser);
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

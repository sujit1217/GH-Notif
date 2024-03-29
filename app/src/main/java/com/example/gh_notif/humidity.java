package com.example.gh_notif;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Timer;
import java.util.TimerTask;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class humidity extends AppCompatActivity {

private Switch humidityswitch;
private SeekBar humidityseekbar;
private TextInputEditText humiditycontent;

    private TextView targethumidityTextView;

    private TextView currenthumidityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);

        humidityswitch = findViewById(R.id.humidswitch);
        humidityseekbar = findViewById(R.id.humidityseekbar);
        currenthumidityTextView= findViewById(R.id.current);
        targethumidityTextView = findViewById(R.id.target);
        humiditycontent= findViewById(R.id.moisturecontent);


        // Set initial state of SeekBar and TextView based on Switch state
        updateSeekBarState(!humidityswitch.isChecked()); // Inverted the logic for initial state
        targethumidityTextView.setText("Target Temperature: " + humidityseekbar.getProgress());
        currenthumidityTextView.setText("Target Temperature: " + humidityseekbar.getProgress());



        // Set OnCheckedChangeListener for the Switch
        humidityswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateSeekBarState(!isChecked); // Inverted the logic when Switch is checked
                if (isChecked) {
                    // Display a message indicating that no changes can be made when the switch is on
                    Toast.makeText(humidity.this, "Switch is ON. No changes can be made.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateSeekBarState(boolean isEnabled) {
        humidityseekbar.setEnabled(isEnabled);
        humidityseekbar.setClickable(isEnabled); // Set clickable property based on isEnabled
    }
}
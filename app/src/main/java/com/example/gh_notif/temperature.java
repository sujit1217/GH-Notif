package com.example.gh_notif;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class temperature extends AppCompatActivity {

    private Switch temperatureSwitch;
    private SeekBar temperatureSeekBar;
    private TextView targetTemperatureTextView;

    private TextView currentTemperatureTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        temperatureSwitch = findViewById(R.id.temperatureswitch);
        temperatureSeekBar = findViewById(R.id.temperatureSeekBar);
        currentTemperatureTextView = findViewById(R.id.current);
        targetTemperatureTextView = findViewById(R.id.target);

        // Set initial state of SeekBar and TextView based on Switch state
        updateSeekBarState(!temperatureSwitch.isChecked()); // Inverted the logic for initial state
        targetTemperatureTextView.setText("Target Temperature: " + temperatureSeekBar.getProgress());

        // Set OnCheckedChangeListener for the Switch
        temperatureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateSeekBarState(!isChecked); // Inverted the logic when Switch is checked
                if (isChecked) {
                    // Display a message indicating that no changes can be made when the switch is on
                    Toast.makeText(temperature.this, "Switch is ON. No changes can be made.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set OnSeekBarChangeListener for the SeekBar
        temperatureSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the target temperature display based on SeekBar progress
                targetTemperatureTextView.setText("Target Temperature: " + progress);
                currentTemperatureTextView.setText("Current Temperature: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }
        });


    }

    private void updateSeekBarState(boolean isEnabled) {
        temperatureSeekBar.setEnabled(isEnabled);
        temperatureSeekBar.setClickable(isEnabled); // Set clickable property based on isEnabled
    }
}
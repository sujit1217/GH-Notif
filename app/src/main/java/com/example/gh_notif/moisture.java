package com.example.gh_notif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class moisture extends AppCompatActivity {

    private WaveProgressBar waveProgressBar;
    private TextView currentMoistureTextView, targetMoistureTextView;

    private TextInputEditText moisturecontenttxt;

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://gh-notif-d5754-default-rtdb.firebaseio.com/");
    public Switch moistureSwitch;
    int progress=0;
    boolean started =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moisture);
// Initialize views
        waveProgressBar = findViewById(R.id.waveProgressbar);
        currentMoistureTextView = findViewById(R.id.current);
        targetMoistureTextView = findViewById(R.id.target);
        moistureSwitch = findViewById(R.id.temperatureswitch);
        moisturecontenttxt=findViewById(R.id.moisturecontent);

        updateProgressState(!moistureSwitch.isChecked());

        // Set OnCheckedChangeListener for the Switch
        moistureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateProgressState(!isChecked);
                if (isChecked) {
                    // Display a message indicating that no changes can be made when the switch is on
                    Toast.makeText(moisture.this, "Switch is ON. No changes can be made.", Toast.LENGTH_SHORT).show();
                }

                String moisture=moisturecontenttxt.getText().toString();

                if (moisture.isEmpty()){
                    Toast.makeText(moisture.this, "please enter required moisture content", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("readings").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("readings").child("greenhouse").child("moist").setValue(moisture);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (started) {
                    progress++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            waveProgressBar.setProgress(progress);
                            currentMoistureTextView.setText("Current Moisture: " + progress + "%");
                            targetMoistureTextView.setText("Target Moisture: " + progress + "%");
                        }
                    });
                    if (progress == 100) {
                        progress = 0;

                    }
                }
            }
        };

            timer.schedule(timerTask,0,100);

            waveProgressBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    started=!started;
                }
            });
        }
    private void updateProgressState(boolean isEnabled) {
        waveProgressBar.setEnabled(isEnabled);

    }
}

package com.example.gh_notif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
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

public class humidity extends AppCompatActivity {

private Switch humidityswitch;
private SeekBar humidityseekbar;
 TextInputEditText humiditycontenttxt;

    private TextView targethumidityTextView;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://gh-notif-d5754-default-rtdb.firebaseio.com/");
    private TextView currenthumidityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity);

        humidityswitch = findViewById(R.id.humidswitch);
        humidityseekbar = findViewById(R.id.humidityseekbar);
        currenthumidityTextView= findViewById(R.id.current);
        targethumidityTextView = findViewById(R.id.target);
        humiditycontenttxt= findViewById(R.id.moisturecontent);


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

                String humidity=humiditycontenttxt.getText().toString();

                if (humidity.isEmpty()){
                    Toast.makeText(humidity.this, "please enter required humidity content", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("readings").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("readings").child("greenhouse").child("humid").setValue(humidity);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    private void updateSeekBarState(boolean isEnabled) {
        humidityseekbar.setEnabled(isEnabled);
        humidityseekbar.setClickable(isEnabled); // Set clickable property based on isEnabled
    }
}
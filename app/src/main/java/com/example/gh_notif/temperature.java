package com.example.gh_notif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class temperature extends AppCompatActivity {

     Switch temperatureSwitch;
    NumberPicker hours, minute;
     SeekBar temperatureSeekBar;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://gh-notif-d5754-default-rtdb.firebaseio.com/");
    TextView targetTemperatureTextView;
    TextInputEditText temperatureconetenttxt;
    TextView currentTemperatureTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
         hours=findViewById(R.id.hrs);
         minute=findViewById(R.id.min);
        temperatureSwitch = findViewById(R.id.temperatureswitch);
        temperatureSeekBar = findViewById(R.id.temperatureSeekBar);
        currentTemperatureTextView = findViewById(R.id.current);
        targetTemperatureTextView = findViewById(R.id.target);
        temperatureconetenttxt=findViewById(R.id.temperaturecontent);

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

                String temp=temperatureconetenttxt.getText().toString();


                if(temp.isEmpty()){
                    Toast.makeText(temperature.this, "please enter required humidity content", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("readings").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child("readings").child("greenhouse").child("temp").setValue(temp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
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
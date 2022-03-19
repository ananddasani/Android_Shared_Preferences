package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView movedTextView;
    EditText toBeMoved;
    Button saveButton, moveButton;
    Switch aSwitch;
    String dataToBeMoved = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movedTextView = findViewById(R.id.movedTextview);
        toBeMoved = findViewById(R.id.toBeMoved);
        saveButton = findViewById(R.id.saveButton);
        moveButton = findViewById(R.id.moveButton);
        aSwitch = findViewById(R.id.switch1);

        //Load the user's data if any (previous session)
        loadDataFromSP();

        //Move Text From Edit Text to TextView
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataToBeMoved = toBeMoved.getText().toString().trim();

                if (dataToBeMoved.equals(""))
                    Toast.makeText(MainActivity.this, "Please Enter Some Text", Toast.LENGTH_SHORT).show();
                else {
                    movedTextView.setText(dataToBeMoved);
                    toBeMoved.setText(""); //empty the edit text
                }
            }
        });

        //save the user preference on the save button clicked
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToSP();
            }
        });
    }

    /**
     * Method will save the user's preference to the shared preference
     * on the save button click
     */
    private void saveDataToSP() {
        //save all the preference of user by using shared preference
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreference", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Save user data in form of Key-Value Pair
        if (!dataToBeMoved.equals("")) {
            editor.putString("text", dataToBeMoved);
            editor.apply();

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }

        if (aSwitch.isChecked()) {
            editor.putBoolean("switchOnOff", aSwitch.isChecked());
            editor.apply();

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to load the user's data from the shared preference & showing it on to the UI
     */
    private void loadDataFromSP() {
        //get data from the same shared preference we have created & saved in above method
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreference", MODE_PRIVATE);

        //Extract Text & Show on UI
        if (sharedPreferences.contains("text"))
            movedTextView.setText(sharedPreferences.getString("text", ""));

        if (sharedPreferences.contains("switchOnOff"))
            aSwitch.setChecked(sharedPreferences.getBoolean("switchOnOff", false));
    }
}

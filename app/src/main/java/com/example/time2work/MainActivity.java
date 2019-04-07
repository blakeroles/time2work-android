package com.example.time2work;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    // Declaring variables from the UI
    private EditText homeAddress;
    private EditText workAddress;
    private EditText timeToArriveAtWork;
    private TextView timeToGetReady;
    private Button saveButton;
    private Button editButton;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Map UI elements to variables.
        calculateButton = findViewById(R.id.buttonCalculate);

        homeAddress = findViewById(R.id.editTextHomeAddress);
        workAddress = findViewById(R.id.editTextWorkAddress);
        timeToArriveAtWork = findViewById(R.id.editTextTimeToArriveWork);

        // Check to see if saved file exists, if it does:
        try {
            FileInputStream appFile = openFileInput("time2work.csv");

            // TODO: Fill EditText fields with data from file.
            calculateButton.setEnabled(true);

        } catch (FileNotFoundException e) {
            // If file does not exist
            e.printStackTrace();
        }

    }

    public void onClick(View view)
    {
        // Switch statement to determine what has been clicked
        switch ((view).getId())
        {
            case R.id.buttonSave:
                // Lock all the fields to not be editable
                disableEditText(homeAddress);
                disableEditText(workAddress);
                disableEditText(timeToArriveAtWork);

                System.out.println(homeAddress.isEnabled());

                break;

                // Create a new model object
                // Model newModel = new Model(workAddress.toString(), homeAddress.toString(), Integer.parseInt(timeToArriveAtWork.toString()));


                // TODO: Call saveFile function
                // TODO: Populate the text fields with the saved information
                // TODO: Allow the Edit button to be visible and save button to be invisible

            // TODO: Implement if Edit button is pressed
            case R.id.buttonEdit:
                // Unlock all the fields to be editable
                enableEditText(homeAddress);
                enableEditText(workAddress);
                enableEditText(timeToArriveAtWork);
                System.out.println(homeAddress.isEnabled());
                break;

            case R.id.buttonCalculate:
                break;
            // TODO: Implement if Calculate button is pressed

                // TODO: Call the callGoogleApi function
                // TODO: Call the calculateTimeToGetReady function
                // TODO: Update the textView with the calculated time to get ready

        }
    }

    public boolean saveFile()
    {
        // TODO: Implement function to save the new Model to the file system
        // TODO: Check if a saved file already exists
        // TODO: If saved file already exists, prompt the user if they want to overwrite
        // TODO:      Delete saved file
        // TODO: Create and save new file with new Model information

        return true;
    }

    public String callGoogleApi()
    {
        // TODO: Query the Google API to get the time between two addresses
        // TODO: Return the time between two addresses

        return "";
    }

    public String calculateTimeToGetReady()
    {
        // TODO: Take the time calculated between the two addresses and subtract it from the Time to get to work
        // TODO: Return the time to get ready

        return "";
    }

    private void disableEditText(EditText editText) {
        editText.setFocusableInTouchMode(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
    }

    private void enableEditText(EditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
    }

}

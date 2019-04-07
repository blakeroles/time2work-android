package com.example.time2work;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        // TODO: Check to see if saved file exists, if it does:
            // TODO: allow the Edit button to be visible
            // TODO: allow Save button to be visible
            // TODO: populate the fields with the file contents
            // TODO: allow the Calculate button to be visible
        // TODO: If it doesn't exist:
            // TODO: Allow the Save button to be visible,
            // TODO: allow the Edit button to be visible
            // TODO: dont allow the calculate button to be visible

    }

    public void onClick(View view)
    {
        // Switch statement to determine what has been clicked
        switch ((view).getId())
        {
            case R.id.button:
                // Create a new model object
                Model newModel = new Model(workAddress.toString(), homeAddress.toString(), Integer.parseInt(timeToArriveAtWork.toString()));

                // TODO: Lock all the fields to not be editable
                // TODO: Call saveFile function
                // TODO: Populate the text fields with the saved information
                // TODO: Allow the Edit button to be visible and save button to be invisible

            // TODO: Implement if Edit button is pressed

                // TODO: Unlock all the fields to be editable

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

}

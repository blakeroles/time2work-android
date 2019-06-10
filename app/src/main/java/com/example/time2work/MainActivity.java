package com.example.time2work;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;


public class MainActivity extends AppCompatActivity implements GeoTask.Geo
{

    // Declaring variables from the UI
    private EditText homeAddress;
    private EditText workAddress;
    private EditText timeToArriveAtWork;
    private TextView timeToGetReady;
    private Button calculateButton;

    private Model newModel;
    private String str_from, str_to;
    private TimePickerDialog timePickerDialog;
    public static final String FILE_NAME = "time2work.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Map UI elements to variables.
        calculateButton = findViewById(R.id.buttonCalculate);
        homeAddress = findViewById(R.id.editTextHomeAddress);
        workAddress = findViewById(R.id.editTextWorkAddress);
        timeToArriveAtWork = findViewById(R.id.editTextTimeToArriveWork);
        timeToGetReady = findViewById(R.id.textViewTimeToGetReady);

        // Check to see if saved file exists, if it does:
        try {
            FileInputStream appFileStream = openFileInput(FILE_NAME);

            // Fill EditText fields with data from file.
            calculateButton.setEnabled(true);
            String stringFromFileStream = getFileContent(appFileStream);
            if (stringFromFileStream.matches(""))
            {
                // TODO: Error Occurred!
            } else
            {
                if (stringFromFileStream.contains("!"))
                {
                    // Split the string and populate the fields appropriately
                    String[] stringFromFileArr = stringFromFileStream.split("!");
                    homeAddress.setText(stringFromFileArr[0]);
                    workAddress.setText(stringFromFileArr[1]);
                    timeToArriveAtWork.setText(stringFromFileArr[2]);
                } else
                {
                    // TODO: Print a Toast message with error occurred.
                }

            }

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

                // Check that newModel is populated
                // TODO: Also include a check that the time to arrive at work field is a number
                if (workAddress.getText().toString().matches("") && homeAddress.getText().toString().matches("") && timeToArriveAtWork.getText().toString().matches(""))
                {
                    // TODO: Add a toast/alert message to populate fields
                } else
                {
                    // Create a model object
                    newModel = new Model(workAddress.getText().toString(), homeAddress.getText().toString(), timeToArriveAtWork.getText().toString());
                    System.out.println("New Model object created with: Home Address = " + homeAddress.getText().toString() + ", Work Address = " + workAddress.getText().toString() + ", Time To Arrive At Work = " + timeToArriveAtWork.getText().toString());
                }

                // Call saveFile function
                Boolean result = saveFile(newModel);

                // TODO: Depending on result: set an alert saying if it was successful or not
                // TODO: Populate the text fields with the saved information

                break;

            // Implement if Edit button is pressed
            case R.id.buttonEdit:
                // Unlock all the fields to be editable
                enableEditText(homeAddress);
                enableEditText(workAddress);
                enableEditText(timeToArriveAtWork);
                break;

            case R.id.buttonCalculate:
                str_from = homeAddress.getText().toString();
                str_to = workAddress.getText().toString();
                String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + str_to + "&key=" + APIContract.DISTANCE_MATRIX_API_KEY;
                new GeoTask(MainActivity.this).execute(url);
                break;

            case R.id.editTextTimeToArriveWork:
                showTimePickerDialog();



        }
    }

    public boolean saveFile(Model m)
    {
        // Check to see if saved file exists, if it does:
        try {
            File appFileToDelete = new File(getFilesDir(), FILE_NAME);
            appFileToDelete.delete();
            writeToFile(m);

        } catch (Exception e) {
            // If file does not exist
            writeToFile(m);
            e.printStackTrace();
        }

        return true;
    }

    public void writeToFile(Model m)
    {
        // Set the delimeter to a ! sign so that commas in address dont get misplaced when string splitting on file read
        String fileContents = m.getHomeAddress() + "!" + m.getWorkAddress() + "!" + m.getTimeToArriveAtWork();
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            System.out.println("File contents: " + fileContents.getBytes());
            outputStream.write(fileContents.getBytes("UTF-8"));
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public String getFileContent( FileInputStream fis)
    {
        String fileString;
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(fis, writer, "UTF-8");
            fileString = writer.toString();
            System.out.println("File is: " + fileString);
            return fileString;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    // Purpose of this function is to take the result from the API and calculate the time to get ready and display on screen
    @Override
    public void setDouble(String result) {
        int timeInSecs = Integer.parseInt(result);
        Double timeInHours = timeInSecs / 3600.0;
        int hours = timeInSecs / 3600;
        Double remainder1 = timeInHours - hours;
        Double timeInMinutes = remainder1 * 60.0;
        int minutes = (int) (remainder1 * 60.0);
        Double remainder2 = timeInMinutes - minutes;
        int seconds = (int) (remainder2 * 60.0);

        String timeAtWork = timeToArriveAtWork.getText().toString();
        String[] timeAtWorkArr = timeAtWork.split(":");

        int secondToPrint = 60 - seconds;
        int minuteToPrint = Integer.parseInt(timeAtWorkArr[1]) - 1;
        int hoursToPrint = Integer.parseInt(timeAtWorkArr[0]);
        if (minuteToPrint < minutes)
        {
            hoursToPrint -= 1;
            minuteToPrint += 60;
        }

        minuteToPrint -= minutes;
        if (hoursToPrint < hours)
        {
            hoursToPrint = 24 - hours + hoursToPrint;
        } else
        {
            hoursToPrint -= hours;
        }




        timeToGetReady.setText(hours + ":" + minutes + ":" + seconds + "......." + hoursToPrint + ":" + minuteToPrint + ":" + secondToPrint);

    }


    public void showTimePickerDialog() {
        timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                int hour_length = String.valueOf(hourOfDay).length();
                int minute_length = String.valueOf(minute).length();
                String time_string = "";
                if (hour_length == 1)
                {
                    time_string += "0";
                }
                time_string += hourOfDay + ":";

                if (minute_length == 1)
                {
                    time_string += "0";
                }
                time_string += minute;
                timeToArriveAtWork.setText(time_string);
            }
        }, 0, 0, true);
        timePickerDialog.show();
    }


}



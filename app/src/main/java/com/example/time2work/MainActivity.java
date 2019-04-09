package com.example.time2work;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // Declaring variables from the UI
    private EditText homeAddress;
    private EditText workAddress;
    private EditText timeToArriveAtWork;
    private TextView timeToGetReady;
    private Button saveButton;
    private Button editButton;
    private Button calculateButton;

    private Model newModel;
    public static final String FILE_NAME = "time2work.csv";

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
            FileInputStream appFileStream = openFileInput(FILE_NAME);

            // Fill EditText fields with data from file.
            calculateButton.setEnabled(true);
            String stringFromFileStream = getFileContent(appFileStream);
            if (stringFromFileStream.matches(""))
            {
                // TODO: Error Occurred!
            } else
            {
                // Split the string and populate the fields appropriately
                String[] stringFromFileArr = stringFromFileStream.split(",");
                homeAddress.setText(stringFromFileArr[0]);
                workAddress.setText(stringFromFileArr[1]);
                timeToArriveAtWork.setText(stringFromFileArr[2]);
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
                if (workAddress.getText().toString().matches("") && homeAddress.getText().toString().matches("") && timeToArriveAtWork.getText().toString().matches(""))
                {
                    // TODO: Add a toast/alert message to populate fields
                } else
                {
                    // Create a model object
                    newModel = new Model(workAddress.getText().toString(), homeAddress.getText().toString(), Integer.parseInt(timeToArriveAtWork.getText().toString()));
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
                getDistanceInfo();
                break;

                // TODO: Call the callGoogleApi function
                // TODO: Call the calculateTimeToGetReady function
                // TODO: Update the textView with the calculated time to get ready

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
        String fileContents = m.getHomeAddress() + "," + m.getWorkAddress() + "," + m.getTimeToArriveAtWork();
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

    private double getDistanceInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        Double dist = 0.0;
        //try {
//
        //    //destinationAddress = destinationAddress.replaceAll(" ","%20");
        //    String url = "http://maps.googleapis.com/maps/api/directions/json?origin=" + -33.73 + "," + 150.99 + "&destination=" + -33.72 + "," + 150.93 + "&mode=driving&sensor=false";
        //    Log.i("string", url);
        //    HttpPost httppost = new HttpPost(url);
////
        //    HttpClient client = new DefaultHttpClient();
        //    HttpResponse response;
        //    stringBuilder = new StringBuilder();
////
////
        //    response = client.execute(httppost);
        //    //HttpEntity entity = response.getEntity();
        //    //InputStream stream = entity.getContent();
        //    //int b;
        //    //while ((b = stream.read()) != -1) {
        //    //    stringBuilder.append((char) b);
        //    //}
        //} catch (ClientProtocolException e) {
        //} catch (IOException e) {
        //}

        //JSONObject jsonObject = new JSONObject();
        //try {
//
        //    jsonObject = new JSONObject(stringBuilder.toString());
//
        //    JSONArray array = jsonObject.getJSONArray("routes");
//
        //    JSONObject routes = array.getJSONObject(0);
//
        //    JSONArray legs = routes.getJSONArray("legs");
//
        //    JSONObject steps = legs.getJSONObject(0);
//
        //    JSONObject distance = steps.getJSONObject("distance");
//
        //    Log.i("Distance", distance.toString());
        //    dist = Double.parseDouble(distance.getString("text").replaceAll("[^\\.0123456789]","") );
//
        //} catch (JSONException e) {
        //    // TODO Auto-generated catch block
        //    e.printStackTrace();
        //}

        return dist;
    }

}

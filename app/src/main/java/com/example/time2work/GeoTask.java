package com.example.time2work;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GeoTask extends AsyncTask<String, Void, String>
{
    ProgressDialog pd;
    Context mContext;
    Double duration;
    Geo geo1;

    // Constructor is used to get the context.
    public GeoTask(Context mContext)
    {
        this.mContext = mContext;
        geo1 = (Geo) mContext;
    }

    // This function is executed before "doInBackground(String...params)" is executed to display the progress dialog
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        pd = new ProgressDialog(mContext);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
    }

    // This function is executed after the execution of "doInBackground(String...params)" to dismiss the displayed progress dialog and call "setDouble(Double)" defined in "MainActivity.java"
    @Override
    protected void onPostExecute(String aDouble)
    {
        super.onPostExecute(aDouble);
        if (aDouble!=null)
        {
            geo1.setDouble(aDouble);
            pd.dismiss();
        }
        else
        {
            Toast.makeText(mContext, "Error! Please try again with proper values", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected String doInBackground(String... params)
    {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statuscode = con.getResponseCode();
            if (statuscode==HttpURLConnection.HTTP_OK)
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null)
                {
                    sb.append(line);
                    line = br.readLine();
                }
                String json = sb.toString();
                Log.d("JSON",json);

                JSONObject root = new JSONObject(json);

                return "return statement";
            }

        } catch (MalformedURLException e) {
            Log.d("error", e.toString());
        } catch (IOException e) {
            Log.d("error", e.toString());
        } catch (JSONException e) {
            Log.d("error", e.toString());
        }
        return null;
    }

    interface Geo
    {
        void setDouble(String min);
    }
}

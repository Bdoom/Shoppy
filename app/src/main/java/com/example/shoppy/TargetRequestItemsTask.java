package com.example.shoppy;

import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class TargetRequestItemsTask extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... data) {
        String apiURL = "https://redsky.target.com/v2/plp/search/?count=1&offset=0&keyword=" + data[0] + "&store_ids=" + data[1] + "&pricing_store_id=" + data[1] + "&key=eb2551e4accc14f38cc42d32fbc2b2ea";

        HttpURLConnection urlConnection = null;
        StringBuilder sb = new StringBuilder();

        try
        {
            URL url = new URL(apiURL);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String inputLine = "";


            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }

            System.out.println(sb);

        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
        }

        return "";
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }


}

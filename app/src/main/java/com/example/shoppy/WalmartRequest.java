package com.example.shoppy;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WalmartRequest extends AsyncTask<String, String, String> {


    @Override
    protected String doInBackground(String... data) {
        String zipCode = data[0];
        String query = data[1];
        String walmartSearchURL = "https://www.walmart.com/search/api/wpa?type=product&min=2&max=20&zipCode=" + zipCode + "&keyword=" + query + "&mloc=bottom&pageType=search";

        HttpURLConnection urlConnection = null;
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(walmartSearchURL);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String inputLine = "";


            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }

            System.out.println("Walmart: " + sb);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        System.out.println("Results: " + result);

    }

}

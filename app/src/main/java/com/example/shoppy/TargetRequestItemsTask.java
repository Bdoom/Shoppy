package com.example.shoppy;

import android.os.AsyncTask;

import java.lang.annotation.Target;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

public class TargetRequestItemsTask extends AsyncTask<String, String, String> {

    StringBuilder sb = null;
    Activity myActivity = null;

    public TargetRequestItemsTask(Activity myActivity)
    {
        this.myActivity = myActivity;
    }

    @Override
    protected String doInBackground(String... data) {
        String apiURL = "https://redsky.target.com/v2/plp/search/?count=1&offset=0&keyword=" + data[0].trim() + "&store_ids=" + data[1].trim() + "&pricing_store_id=" + data[1].trim() + "&key=eb2551e4accc14f38cc42d32fbc2b2ea";

        HttpURLConnection urlConnection = null;
        sb = new StringBuilder();

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
            System.out.println(apiURL);

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


        try
        {
            JSONObject jsonObject = new JSONObject(sb.toString());

            JSONObject search_response = jsonObject.getJSONObject("search_response");
            JSONObject items = search_response.getJSONObject("items");
            JSONArray item = items.getJSONArray("Item");
            for (int i = 0; i < item.length(); i++)
            {
                JSONObject jsonItem = item.getJSONObject(i);
                String title = jsonItem.getString("title");
                JSONObject jsonPrice = jsonItem.getJSONObject("price");
                double price = jsonPrice.getDouble("current_retail");
                TextView priceValueText = myActivity.findViewById(R.id.txtPriceActualValue);

                priceValueText.setText(Double.toString(price));
            }

        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }


    }


}

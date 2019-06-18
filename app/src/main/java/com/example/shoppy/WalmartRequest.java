package com.example.shoppy;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WalmartRequest extends AsyncTask<String, String, String> implements StoreRequestCallback {


    private SearchContainer searchContainer;

    public WalmartRequest(SearchContainer searchContainer)
    {
        this.searchContainer = searchContainer;
    }

    @Override
    protected String doInBackground(String... data) {
        String zipCode = data[0];
        String query = data[1];
        String walmartSearchURL = "https://www.walmart.com/search/api/wpa?type=product&min=2&max=900&zipCode=" + zipCode + "&keyword=" + query + "&mloc=bottom&pageType=search";

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

            in.close();
            br.close();

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
    public void onCallComplete() {
        searchContainer.ReduceNumItemsLeftByOne();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        String productName = "";

        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONObject resultObject = jsonObject.getJSONObject("result");

            JSONArray products = resultObject.getJSONArray("products");

            for (int i = 0; i < products.length(); i++) {

                JSONObject obj = products.getJSONObject(i);
                JSONObject priceObj = obj.getJSONObject("price");
                productName = obj.getString("productName");
                String currentPrice = priceObj.getString("currentPrice");
                if (currentPrice != null && !currentPrice.isEmpty() && currentPrice != "null") {
                    double price = Double.parseDouble(currentPrice);
                    searchContainer.WalmartRequestHashMap.put(productName, price);
                }
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        onCallComplete();

    }

}

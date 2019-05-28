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
import java.util.ArrayList;

public class WegmansRequest extends AsyncTask<String, String, String> {

    StringBuilder stringBuilderForLocationID = null;

    @Override
    protected String doInBackground(String... data) {
        String query = data[1];
        String storeSearchURL = "https://sp1004f27d.guided.ss-omtrdc.net/?q=*&do=location-search&sp_q_location_1=" + data[0] + "&sp_x_1=zip&sp_q_max_1=1000&sp_s=zip_proximity;sp_c=1000";

        HttpURLConnection urlConnection = null;
        stringBuilderForLocationID = new StringBuilder();

        try {
            URL url = new URL(storeSearchURL);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String inputLine = "";


            while ((inputLine = br.readLine()) != null) {
                stringBuilderForLocationID.append(inputLine);
            }

            System.out.println(stringBuilderForLocationID);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        WegItemSearch(query);


        return "";
    }

    private  void WegItemSearch(String query)
    {
        /*
        curl 'https://wegapi.azure-api.net/pricing/carttotal/false?api-version=1.0' -H 'Accept: application/json, text/plain, ' -H 'Referer: https://www.wegmans.com/products/product-search.html?searchKey=Pizza' -H 'Origin: https://www.wegmans.com' -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36' -H 'DNT: 1' -H 'Ocp-Apim-Subscription-Key: 9ab421ab7cc74f6c934b0406a917ed5c' -H 'Content-Type: application/json' --data-binary '{"LineItems":[{"Sku":"581943","Quantity":1},{"Sku":"37996","Quantity":1},{"Sku":"581945","Quantity":1},{"Sku":"13552","Quantity":1},{"Sku":"581947","Quantity":1},{"Sku":"626169","Quantity":1},{"Sku":"607295","Quantity":1},{"Sku":"581956","Quantity":1},{"Sku":"564374","Quantity":1},{"Sku":"677076","Quantity":1},{"Sku":"26875","Quantity":1},{"Sku":"26876","Quantity":1},{"Sku":"43529","Quantity":1},{"Sku":"426018","Quantity":1},{"Sku":"14546","Quantity":1},{"Sku":"43530","Quantity":1},{"Sku":"426022","Quantity":1},{"Sku":"39209","Quantity":1}],"StoreNumber":"56"}' --compressed
         */
        //https://wegapi.azure-api.net/pricing/carttotal/false?api-version=1.0
        String wegItemSearch = "https://sp1004f27d.guided.ss-omtrdc.net/?do=prod-search;storeNumber=" + GetStoreID() + ";sort=relevance;sp_c=18;sp_n=1;q=" + query;

        HttpURLConnection urlConnection = null;
        StringBuilder builder = new StringBuilder();

        try {
            URL url = new URL(wegItemSearch);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String inputLine = "";


            while ((inputLine = br.readLine()) != null) {
                builder.append(inputLine);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        try
        {
            JSONObject jsonObject = new JSONObject(builder.toString());
            JSONArray results = jsonObject.getJSONArray("results");
            StringBuilder lineItemsBuilder = new StringBuilder();
            lineItemsBuilder.append("{\"LineItems\":[");
            for (int i = 0; i < results.length(); i++)
            {
                try
                {
                    JSONObject result = results.getJSONObject(i);
                    int sku = result.getInt("sku");
                    if (i == results.length() - 1)
                    {
                        lineItemsBuilder.append("{\"Sku\":" + sku + ",\"Quantity\":1}"); // last element, no comma
                    }
                    else
                    {
                        lineItemsBuilder.append("{\"Sku\":" + sku + ",\"Quantity\":1},"); // last element, has comma.
                    }
                }
                catch (JSONException ex)
                {
                    ex.printStackTrace();;
                }
            }
            int storeID = GetStoreID();

            lineItemsBuilder.append("],\"StoreNumber\":\"" + storeID + "\"}");

            System.out.print(lineItemsBuilder.toString());


        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }



    }

    private int GetStoreID()
    {
        try {
            JSONObject jsonObject = new JSONObject(stringBuilderForLocationID.toString());

            JSONArray results = jsonObject.getJSONArray("results");

            JSONObject jsonItem = results.getJSONObject(0);
            int locationNumber = jsonItem.getInt("locationNumber");
            return locationNumber;

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);




    }
}
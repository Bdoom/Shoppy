package com.example.shoppy;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.*;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import java.net.HttpURLConnection;

public class TargetRequest extends AsyncTask<String, String, String> {

    String id = "";
    String query = "";
    Activity myActivity;

    public TargetRequest(Activity myActivity)
    {
        this.myActivity = myActivity;
    }


    @Override
    protected String doInBackground(String... data) {
        query = data[3];
        HttpURLConnection urlConnection = null;
        StringBuilder sb = new StringBuilder();

        if (data[0] == null || data[1] == null || data[2] == null)
        {
            return null;
        }

        String storeURL = "https://redsky.target.com/v2/stores/nearby/" + data[0].trim() + "?key=eb2551e4accc14f38cc42d32fbc2b2ea&locale=en-US&limit=" + data[1].trim() + "&range=" + data[2].trim();

        try
        {
            URL url = new URL(storeURL);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String inputLine = "";


            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }

            in.close();
            br.close();

            ParseXML(sb);

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


        ParseXML(sb);

        RequestItems();

        return "";
    }

    private void RequestItems()
    {
        MainActivity mainActivity = (MainActivity)myActivity;

        String apiURL = "https://redsky.target.com/v2/plp/search/?count=1&offset=0&keyword=" + query.trim() + "&store_ids=" + id.trim() + "&pricing_store_id=" + id.trim() + "&key=eb2551e4accc14f38cc42d32fbc2b2ea";

        HttpURLConnection urlConnection = null;
        StringBuilder stringBuilder = new StringBuilder();

        try
        {
            URL url = new URL(apiURL);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String inputLine = "";


            while ((inputLine = br.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

            in.close();
            br.close();

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

        try
        {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());

            JSONObject search_response = jsonObject.getJSONObject("search_response");
            JSONObject items = search_response.getJSONObject("items");
            if (items == null)
            {
                return;
            }

            JSONArray item = items.getJSONArray("Item");

            for (int i = 0; i < item.length(); i++)
            {
                JSONObject jsonItem = item.getJSONObject(i);
                String itemName = jsonItem.getString("title");
                JSONObject jsonPrice = jsonItem.getJSONObject("price");
                double price = jsonPrice.getDouble("current_retail");

                mainActivity.TargetRequestHashMap.put(itemName, price);

            }

        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }

        mainActivity.RequestFinished();

    }

    public String ParseXML(StringBuilder sb)
    {
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(
                    sb.toString().getBytes("UTF-8"));
            Document doc = builder.parse(input);

            if (doc != null) {
                 NodeList nList = doc.getElementsByTagName("ID");
                 if (nList != null)
                 {
                     Node node = nList.item(0);
                     if (node != null)
                     {
                         id = node.getTextContent();
                     }
                 }
            }
        }
        catch (ParserConfigurationException ex)
        {
            ex.printStackTrace();
        }
        catch (SAXException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        // END XML


        return id;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

    }


}

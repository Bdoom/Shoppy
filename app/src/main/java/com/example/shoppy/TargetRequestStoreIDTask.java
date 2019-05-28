package com.example.shoppy;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import java.net.HttpURLConnection;

public class TargetRequestStoreIDTask extends AsyncTask<String, String, String> {

    String id = "";
    String query = "";
    Activity myActivity;

    public TargetRequestStoreIDTask(Activity myActivity)
    {
        this.myActivity = myActivity;
    }


    @Override
    protected String doInBackground(String... data) {
        query = data[3];
        HttpURLConnection urlConnection = null;
        StringBuilder sb = new StringBuilder();

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

        return ParseXML(sb);
    }

    public String ParseXML(StringBuilder sb)
    {
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(
                    sb.toString().getBytes("UTF-8"));
            Document doc = builder.parse(input);

            id = doc.getElementsByTagName("ID").item(0).getTextContent();
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

        return id;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        TargetRequestItemsTask reqItems = new TargetRequestItemsTask(myActivity);
        reqItems.execute(query, id);

    }


}

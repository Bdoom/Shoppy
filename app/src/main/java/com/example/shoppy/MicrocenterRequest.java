package com.example.shoppy;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;


public class MicrocenterRequest extends AsyncTask<String, String, String> implements StoreRequestCallback {

    private SearchContainer searchContainer;
    private String url;

    public MicrocenterRequest(SearchContainer searchContainer) {
        this.searchContainer = searchContainer;
    }

    @Override
    protected String doInBackground(String... strings) {
        String query = strings[0];
        query = query.replace(" ", "+");

        url = "http://www.microcenter.com/search/search_results.aspx?Ntt=" + query;
        ParseHTML();


        return "";
    }

    private void ParseHTML() {

        // data-name is the product name
        // data-price is the price of the product.

        try {

            Document doc = Jsoup.connect(url).get();
            List<Element> elements = doc.getElementsByAttribute("data-name");
            String itemName = "";
            double itemPrice = 0.0;
            for (Element element : elements) {
                for (Attribute attribute : element.attributes()) {
                    if (attribute.getKey().equalsIgnoreCase("data-name"))
                    {
                        itemName = attribute.getValue();
                    }
                    if (attribute.getKey().equalsIgnoreCase("data-price"))
                    {
                        itemPrice = Double.parseDouble(attribute.getValue());
                    }
                }
                if (itemPrice > 0.0) {
                    searchContainer.MicrocenterRequestHashMap.put(itemName, itemPrice);
                }
                itemName = "";
                itemPrice = 0.0;

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }



    }

    @Override
    public void onCallComplete() {
        searchContainer.ReduceNumItemsLeftByOne();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        onCallComplete();
    }
}

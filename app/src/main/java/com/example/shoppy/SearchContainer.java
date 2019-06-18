package com.example.shoppy;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class SearchContainer {

    public HashMap<String, Double> TargetRequestHashMap = new HashMap<>();
    public HashMap<String, Double> WalmartRequestHashMap = new HashMap<>();
    public HashMap<String, Double> WegmansRequestHashMap = new HashMap<>();
    public HashMap<String, Double> MicrocenterRequestHashMap = new HashMap<>();

    public ArrayList<SearchItem> CombinedSearchResults = new ArrayList<>();

    private int NumItemsLeft = 0;
    private Activity activity;

    public SearchContainer(Activity activity)
    {
        this.activity = activity;
    }

    public void ReduceNumItemsLeftByOne()
    {
        NumItemsLeft--;
        if (NumItemsLeft <= 0)
        {
           RequestsCompleteCallback requestsCompleteCallback = (RequestsCompleteCallback)activity;
           if (requestsCompleteCallback != null)
           {
               CombineTheResults();
               requestsCompleteCallback.AllRequestsComplete();
           }
        }
    }

    /***
     * Private function which is called once all of the multiple requests are done. It will combine all of the hashmaps into a single list of SearchItem objects.
     */
    private void CombineTheResults()
    {
        Iterator it = TargetRequestHashMap.entrySet().iterator();
        while (it.hasNext()) {
            SearchItem searchItem = new SearchItem();
            HashMap.Entry pair = (HashMap.Entry)it.next();
            searchItem.itemName = pair.getKey().toString();
            searchItem.itemPrice = Double.parseDouble(pair.getValue().toString());
            searchItem.StoreName = EStoreName.Store_TARGET;
            CombinedSearchResults.add(searchItem);
            it.remove(); // avoids a ConcurrentModificationException
        }

        Iterator it2 = WalmartRequestHashMap.entrySet().iterator();
        while (it2.hasNext()) {
            SearchItem searchItem = new SearchItem();
            HashMap.Entry pair = (HashMap.Entry)it2.next();
            searchItem.itemName = pair.getKey().toString();
            searchItem.itemPrice = Double.parseDouble(pair.getValue().toString());
            searchItem.StoreName = EStoreName.Store_Walmart;
            CombinedSearchResults.add(searchItem);
            it2.remove();
        }

        Iterator it3 = WegmansRequestHashMap.entrySet().iterator();
        while (it3.hasNext()) {
            SearchItem searchItem = new SearchItem();
            HashMap.Entry pair = (HashMap.Entry)it3.next();
            searchItem.itemName = pair.getKey().toString();
            searchItem.itemPrice = Double.parseDouble(pair.getValue().toString());
            searchItem.StoreName = EStoreName.Store_Wegmans;
            CombinedSearchResults.add(searchItem);
            it3.remove();
        }

        Iterator it4 = MicrocenterRequestHashMap.entrySet().iterator();
        while (it4.hasNext())
        {
            SearchItem searchItem = new SearchItem();
            HashMap.Entry pair = (HashMap.Entry)it4.next();
            searchItem.itemName = pair.getKey().toString();
            searchItem.itemPrice = Double.parseDouble(pair.getValue().toString());
            searchItem.StoreName = EStoreName.Store_Microcenter;
            CombinedSearchResults.add(searchItem);
            it4.remove();
        }

        SortByPrice();
    }

    private void SortByPrice()
    {
        // Sorts the results by price.
        Collections.sort(CombinedSearchResults, new SearchItemComparator());
    }


    public void SetNumItemsLeft(int newItemsLeft)
    {
        System.out.println("Number of requests to make: " + newItemsLeft);
        NumItemsLeft = newItemsLeft;
    }

    public void ResetRequests()
    {
        TargetRequestHashMap.clear();
        WalmartRequestHashMap.clear();
        WegmansRequestHashMap.clear();
        MicrocenterRequestHashMap.clear();
    }

}

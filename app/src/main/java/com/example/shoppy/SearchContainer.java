package com.example.shoppy;

import android.app.Activity;

import java.util.HashMap;

public class SearchContainer {

    public HashMap<String, Double> TargetRequestHashMap = new HashMap<>();
    public HashMap<String, Double> WalmartRequestHashMap = new HashMap<>();
    public HashMap<String, Double> WegmansRequestHashMap = new HashMap<>();

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
               requestsCompleteCallback.AllRequestsComplete();
           }
        }
    }


    public void SetNumItemsLeft(int newItemsLeft)
    {
        System.out.println("Number of requests to make: " + newItemsLeft);
        NumItemsLeft = newItemsLeft;
    }

    public int GetNumItemsLeft()
    {
        return NumItemsLeft;
    }

    public void ResetRequests()
    {
        TargetRequestHashMap.clear();
        WalmartRequestHashMap.clear();
        WegmansRequestHashMap.clear();
    }

}

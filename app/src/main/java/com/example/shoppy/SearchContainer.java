package com.example.shoppy;

import java.util.HashMap;

public class SearchContainer {

    public HashMap<String, Double> TargetRequestHashMap = new HashMap<>();
    public HashMap<String, Double> WalmartRequestHashMap = new HashMap<>();
    public HashMap<String, Double> WegmansRequestHashMap = new HashMap<>();
    private int NumItemsLeft = 0;

    public void SetNumItemsLeft(int newItemsLeft)
    {
        NumItemsLeft = newItemsLeft;
    }

    public int GetNumItemsLeft()
    {
        return NumItemsLeft;
    }

    public void ClearAllLists()
    {
        TargetRequestHashMap.clear();
        WalmartRequestHashMap.clear();
        WegmansRequestHashMap.clear();
    }

}

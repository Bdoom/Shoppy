package com.example.shoppy;

import java.net.URL;

enum EStoreName
{
    Store_TARGET,
    Store_Walmart,
    Store_Wegmans,
    Store_Microcenter
}

public class SearchItem {

    public String itemName;
    public double itemPrice;
    public EStoreName StoreName;
    public URL imageURL;

}

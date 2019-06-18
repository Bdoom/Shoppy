package com.example.shoppy;

import java.util.Comparator;

public class SearchItemComparator implements Comparator<SearchItem> {

    @Override
    public int compare(SearchItem item1, SearchItem item2) {
        return Double.compare(item1.itemPrice, item2.itemPrice);
    }

}

package com.example.shoppy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;

public class SearchResultsGridActivity extends AppCompatActivity implements RequestsCompleteCallback {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private int NumberOfColumns = 3;
    public RecyclerView.Adapter mAdapter;
    public ArrayList<String> listData = new ArrayList<>();
    public SearchContainer searchContainer = new SearchContainer(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_grid);

        recyclerView = findViewById(R.id.recyclerSearchResultsGrid);

        gridLayoutManager = new GridLayoutManager(this, NumberOfColumns);
        recyclerView.setLayoutManager(gridLayoutManager);

        // get data from Bundle object
        listData.add("test");

        mAdapter = new SearchResultsGridAdapter(getBaseContext(), listData.toArray(new String[0]));
        recyclerView.setAdapter(mAdapter);

        AsyncMakeMultipleRequest asyncMakeMultipleRequest = new AsyncMakeMultipleRequest(this, searchContainer);
        asyncMakeMultipleRequest.execute();

    }

    @Override
    public void AllRequestsComplete() {
        System.out.println("All requests complete!");
        for (String s : searchContainer.WegmansRequestHashMap.keySet())
        {
            System.out.println("Item name; " + s);
        }

    }
}

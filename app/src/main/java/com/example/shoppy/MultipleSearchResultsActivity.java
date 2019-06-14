package com.example.shoppy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

public class MultipleSearchResultsActivity extends AppCompatActivity implements RequestsCompleteCallback {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private int NumberOfColumns = 3;
    public RecyclerView.Adapter mAdapter;
    public HashMap<String, HashMap<String, Double>> data = new HashMap<String, HashMap<String, Double>>();
    public SearchContainer searchContainer = new SearchContainer(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        recyclerView = findViewById(R.id.recyclerSearchResultsGrid);

        layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MultipleSearchResultsAdapter(getBaseContext(), searchContainer);
        recyclerView.setAdapter(mAdapter);

        AsyncMakeMultipleRequest asyncMakeMultipleRequest = new AsyncMakeMultipleRequest(this, searchContainer);

        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));

        asyncMakeMultipleRequest.execute();
        System.out.println("Requests started");
    }

    public void UpdateList()
    {
        mAdapter = new MultipleSearchResultsAdapter(getBaseContext(), searchContainer);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void AllRequestsComplete() {
        UpdateList();

    }
}

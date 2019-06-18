package com.example.shoppy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MultipleSearchResultsActivity extends AppCompatActivity implements RequestsCompleteCallback {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    public RecyclerView.Adapter mAdapter;
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

package com.example.shoppy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SearchResultsGridActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private int NumberOfColumns = 3;
    public RecyclerView.Adapter mAdapter;
    public ArrayList<String> listData = new ArrayList<>();

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

    }

}

package com.example.shoppy;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultsGridAdapter extends RecyclerView.Adapter<SearchResultsGridAdapter.SearchResultsViewHolder> {

    public static class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public View view;
        public ImageView imgItemImage;
        public TextView txtItemName;
        public TextView txtItemPrice;

        public SearchResultsViewHolder(View v) {
            super(v);
            view = v;
            imgItemImage = v.findViewById(R.id.imgItemImage);
            txtItemName = v.findViewById(R.id.txtItemName);
            txtItemPrice = v.findViewById(R.id.txtItemPrice);

        }
    }

    private String[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public SearchResultsGridAdapter(Context context, String[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    public SearchResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_list_result_item, parent, false);
        return new SearchResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultsGridAdapter.SearchResultsViewHolder searchResultsViewHolder, int position) {
        ImageView imgItemImage = searchResultsViewHolder.imgItemImage;
        TextView txtItemName = searchResultsViewHolder.txtItemName;
        TextView txtItemPrice = searchResultsViewHolder.txtItemPrice;

        // setup the data for this view.
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}

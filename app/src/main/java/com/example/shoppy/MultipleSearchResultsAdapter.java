package com.example.shoppy;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MultipleSearchResultsAdapter extends RecyclerView.Adapter<MultipleSearchResultsAdapter.SearchResultsViewHolder> {

    public static class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public View view;
        public ImageView imgItemImage;
        public TextView txtItemName;
        public TextView txtItemPrice;
        public TextView txtStoreName;

        public SearchResultsViewHolder(View v) {
            super(v);
            view = v;
            txtItemName = v.findViewById(R.id.txtItemName);
            txtItemPrice = v.findViewById(R.id.txtItemPrice);
            txtStoreName = v.findViewById(R.id.txtStoreName);

        }
    }

    private SearchContainer searchContainer;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public MultipleSearchResultsAdapter(Context context, SearchContainer searchContainer) {
        this.mInflater = LayoutInflater.from(context);
        this.searchContainer = searchContainer;
        this.context = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    public SearchResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_list_result_item, parent, false);
        return new SearchResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MultipleSearchResultsAdapter.SearchResultsViewHolder searchResultsViewHolder, int position) {
        ImageView imgItemImage = searchResultsViewHolder.imgItemImage;
        TextView txtItemName = searchResultsViewHolder.txtItemName;
        TextView txtItemPrice = searchResultsViewHolder.txtItemPrice;
        TextView txtStoreName = searchResultsViewHolder.txtStoreName;

        SearchItem searchItem = searchContainer.CombinedSearchResults.get(position);

        txtItemName.setText(searchItem.itemName);
        txtItemPrice.setText(String.format("Price: $%s", Double.toString(searchItem.itemPrice)));
        String storeName = "";

        switch (searchItem.StoreName)
        {
            case Store_TARGET:
                storeName = "Target";
                break;
            case Store_Walmart:
                storeName = "Walmart";
                break;
            case Store_Wegmans:
                storeName = "Wegmans";
                break;
        }

        txtStoreName.setText(String.format("Store: %s", storeName));

    }

    // total number of cells
    @Override
    public int getItemCount() {
        int size = searchContainer.CombinedSearchResults.size();
        return size;
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

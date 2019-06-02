package com.example.shoppy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.view.LayoutInflater;

import org.w3c.dom.Text;

public class ShoppyListAdapter extends RecyclerView.Adapter<ShoppyListAdapter.ShoppyListViewHolder> {

    private String[] mData;

    public static class ShoppyListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView textView;
        public Button btnEditItem;

        public View view;
        public ShoppyListViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.shoppingListItem);
            btnEditItem = v.findViewById(R.id.btnEditItem);
            view = v;
        }
    }

    public ShoppyListAdapter(String[] mData)
    {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ShoppyListAdapter.ShoppyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shoppy_list_view, parent, false);

        ShoppyListViewHolder vh = new ShoppyListViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppyListViewHolder shoppyListViewHolder, int position) {
        TextView textView = shoppyListViewHolder.textView;

        textView.setText(mData[position]);
        shoppyListViewHolder.btnEditItem.setOnClickListener(editButtonOnClickListener);

    }


    private View.OnClickListener editButtonOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnEditItem) {
                System.out.println("edit button clicked :)!");
                View parent = (View)v.getParent();
                if (parent != null) {
                    TextView txtView = parent.findViewById(R.id.shoppingListItem);
                    txtView.setText("New text to set.");
                }


            }
        }
    };

    @Override
    public int getItemCount() {
        return mData.length;
    }


}

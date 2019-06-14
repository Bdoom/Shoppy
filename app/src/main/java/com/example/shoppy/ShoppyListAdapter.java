package com.example.shoppy;

import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.view.LayoutInflater;
import android.content.Context;

public class ShoppyListAdapter extends RecyclerView.Adapter<ShoppyListAdapter.ShoppyListViewHolder> {

    private String[] mData;
    private Context context;
    private listFragment listFragment;

    public static class ShoppyListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView textView;
        public Button btnEditItem;
        public Button btnDeleteItem;

        public View view;
        public ShoppyListViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.shoppingListItem);
            btnEditItem = v.findViewById(R.id.btnEditItem);
            btnDeleteItem = v.findViewById(R.id.btnDelete);
            view = v;
        }
    }

    public ShoppyListAdapter(String[] mData, Context context, listFragment listFragment)
    {
        this.mData = mData;
        this.context = context;
        this.listFragment = listFragment;
    }

    @Override
    public ShoppyListAdapter.ShoppyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shoppy_list_view, parent, false);

        ShoppyListViewHolder vh = new ShoppyListViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ShoppyListViewHolder shoppyListViewHolder, int position) {
        TextView textView = shoppyListViewHolder.textView;

        textView.setText(mData[position]);
        shoppyListViewHolder.btnEditItem.setOnClickListener(onClickListener);
        shoppyListViewHolder.btnDeleteItem.setOnClickListener(onClickListener);

    }


    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            View parent = (View)v.getParent();

            if (v.getId() == R.id.btnEditItem) {

                if (parent != null) {
                    final TextView txtView = parent.findViewById(R.id.shoppingListItem);
                    AlertDialog.Builder builder = new AlertDialog.Builder(listFragment.getContext());
                    builder.setTitle(R.string.title_edit_shopping_list_item);

                    final EditText input = new EditText(listFragment.getContext());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Set new text.
                            String newText = input.getText().toString();
                            String oldText = txtView.getText().toString();

                            AsyncUpdateShoppingListItem asyncUpdateShoppingListItem = new AsyncUpdateShoppingListItem(listFragment);
                            asyncUpdateShoppingListItem.execute(oldText, newText);

                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            }

            if (v.getId() == R.id.btnDelete)
            {
                AsyncDeleteShoppingListItem asyncDeleteShoppingListItem = new AsyncDeleteShoppingListItem(context, listFragment);
                TextView txtView = parent.findViewById(R.id.shoppingListItem);

                asyncDeleteShoppingListItem.execute(txtView.getText().toString());

            }
        }
    };

    @Override
    public int getItemCount() {
        return mData.length;
    }


}

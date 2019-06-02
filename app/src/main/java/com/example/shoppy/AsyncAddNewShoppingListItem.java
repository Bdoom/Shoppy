package com.example.shoppy;

import android.os.AsyncTask;

import androidx.room.Room;
import java.lang.ref.WeakReference;

public class AsyncAddNewShoppingListItem extends AsyncTask<String, String, String> {

    private WeakReference<listFragment> listFragmentWeakReference;
    private String itemName;

    public AsyncAddNewShoppingListItem(listFragment listFragment, String itemName)
    {
        listFragmentWeakReference = new WeakReference<>(listFragment);
        this.itemName = itemName;
    }

    @Override
    protected String doInBackground(String... strings) {

        if (listFragmentWeakReference.get() == null)
        {
            return null;
        }

        ShoppyDatabase db = Room.databaseBuilder(listFragmentWeakReference.get().getContext(),
                ShoppyDatabase.class, "Shoppy").build();

        ShoppingListItem shoppingListItem = new ShoppingListItem();
        shoppingListItem.itemName = itemName;

        db.shoppingListItemDAO().insertAll(shoppingListItem);

        return itemName;
    }


    @Override
    protected void onPostExecute(String itemName) {
        if(listFragmentWeakReference.get() == null) {
            return;
        }

        listFragment listFragment = listFragmentWeakReference.get();
        listFragment.listData.add(itemName);
        listFragment.SetupAdapter();
        listFragment.mAdapter.notifyDataSetChanged();




    }

}

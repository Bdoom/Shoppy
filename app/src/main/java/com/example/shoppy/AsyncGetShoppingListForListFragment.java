package com.example.shoppy;

import android.os.AsyncTask;

import androidx.room.Room;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AsyncGetShoppingListForListFragment extends AsyncTask<Void, Void, ArrayList> {


    private WeakReference<listFragment> listFragment;
    private ArrayList<String> itemList = new ArrayList<>();


    public AsyncGetShoppingListForListFragment(listFragment listFragment)
    {
        this.listFragment = new WeakReference<>(listFragment);
    }

    @Override
    protected ArrayList doInBackground(Void... params) {

        if (listFragment.get() == null)
        {
            return null;
        }

        if (listFragment.get().getContext() == null)
        {
            System.out.println("listFragment context was null.");
            return null;
        }

        ShoppyDatabase db = Room.databaseBuilder(listFragment.get().getContext(),
                ShoppyDatabase.class, "Shoppy").build();

        List<ShoppingListItem> listItems = db.shoppingListItemDAO().getAllListItems();

        for (ShoppingListItem listItem : listItems)
        {
            itemList.add(listItem.itemName);
        }

        db.close();

        return itemList;
    }

    @Override
    protected void onPostExecute(ArrayList items) {
        if(listFragment.get() == null) {
            return;
        }

        listFragment.get().listData = items;
        listFragment.get().SetupAdapter();
    }


}

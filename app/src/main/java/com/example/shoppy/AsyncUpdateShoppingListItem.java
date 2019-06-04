package com.example.shoppy;

import android.os.AsyncTask;

import androidx.room.Room;

public class AsyncUpdateShoppingListItem extends AsyncTask<String, String, String> {


    private listFragment listFragment;

    public AsyncUpdateShoppingListItem(listFragment listFragment)
    {
        this.listFragment = listFragment;
    }

    @Override
    protected String doInBackground(String... strings) {

        String oldName = strings[0];
        String newName = strings[1];


        ShoppyDatabase db = Room.databaseBuilder(listFragment.getContext(),
                ShoppyDatabase.class, "Shoppy").build();

        db.shoppingListItemDAO().update(newName, oldName);

        db.close();

        return "";
    }

    protected void onPostExecute(String string) {
        AsyncGetShoppingListForListFragment getShoppingList = new AsyncGetShoppingListForListFragment(listFragment);
        getShoppingList.execute();
    }
}

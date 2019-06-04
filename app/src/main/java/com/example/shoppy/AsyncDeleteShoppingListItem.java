package com.example.shoppy;

import android.os.AsyncTask;

import androidx.room.Room;
import android.content.Context;

public class AsyncDeleteShoppingListItem extends AsyncTask<String, String, String> {

    private Context context;
    private listFragment listFragment;

    public AsyncDeleteShoppingListItem(Context context, listFragment listFragment)
    {
        this.context = context;
        this.listFragment = listFragment;
    }

    @Override
    protected String doInBackground(String... strings) {

        ShoppyDatabase db = Room.databaseBuilder(context,
                ShoppyDatabase.class, "Shoppy").build();

        db.shoppingListItemDAO().delete(strings[0]);

        db.close();

        return strings[0];
    }

    protected void onPostExecute(String string) {
        AsyncGetShoppingListForListFragment getShoppingList = new AsyncGetShoppingListForListFragment(listFragment);
        getShoppingList.execute();
    }

}

package com.example.shoppy;

import android.os.AsyncTask;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class AsyncMakeMultipleRequest extends AsyncTask<Void, Void, ArrayList> {

    private MainActivity mainActivity;

    public AsyncMakeMultipleRequest(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    @Override
    protected ArrayList doInBackground(Void... params) {

        if (mainActivity == null)
        {
            return null;
        }

        ArrayList<String> itemList = new ArrayList<>();

        ShoppyDatabase db = Room.databaseBuilder(mainActivity.getApplicationContext(),
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

        System.out.println("This may take several minutes. Please wait");

        for (int i = 0; i < items.size(); i++)
        {
            String query = (String)items.get(i);
            TargetRequest targetRequest = new TargetRequest(mainActivity);
            WegmansRequest wegmansRequest = new WegmansRequest(mainActivity);
            WalmartRequest walmartRequest = new WalmartRequest(mainActivity);

            if (mainActivity.isEmulator()) {
                System.out.println("Making requests.");
                wegmansRequest.execute("20876", query);
                walmartRequest.execute("20876", query);
                targetRequest.execute("20876", "1", "5", query);

            } else {
                String zipCode = mainActivity.GetZipCode();
                wegmansRequest.execute(zipCode, query);
                walmartRequest.execute(zipCode, query);
                targetRequest.execute(zipCode, "1", "5", query);
            }

        }

    }

}

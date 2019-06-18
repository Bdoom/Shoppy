package com.example.shoppy;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class AsyncMakeMultipleRequest extends AsyncTask<Void, Void, ArrayList> {

    private ArrayList<String> itemList = new ArrayList<>();
    public int NumberOfWebSites = 4;
    private SearchContainer searchContainer;
    private Activity activity;

    public AsyncMakeMultipleRequest(Activity activity, SearchContainer searchContainer) {this.searchContainer = searchContainer; this.activity = activity;}

    @Override
    protected ArrayList doInBackground(Void... params) {

        searchContainer.ResetRequests();

        ShoppyDatabase db = Room.databaseBuilder(activity.getApplicationContext(),
                ShoppyDatabase.class, "Shoppy").build();

        List<ShoppingListItem> listItems = db.shoppingListItemDAO().getAllListItems();

        for (ShoppingListItem listItem : listItems) {
            itemList.add(listItem.itemName);
        }

        searchContainer.SetNumItemsLeft(itemList.size() * NumberOfWebSites);

        db.close();

        if (itemList.isEmpty()) {
            Context context = activity.getApplicationContext();
            CharSequence text = "Please add an item to your shopping list.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);

            toast.setGravity(Gravity.CENTER, 0, 0);

            toast.show();
            return null;
        }

        System.out.println("This may take several minutes. Please wait");

        for (int i = 0; i < itemList.size(); i++) {
            String query = itemList.get(i);
            TargetRequest targetRequest = new TargetRequest(searchContainer);
            WegmansRequest wegmansRequest = new WegmansRequest(searchContainer);
            WalmartRequest walmartRequest = new WalmartRequest(searchContainer);
            MicrocenterRequest microcenterRequest = new MicrocenterRequest(searchContainer);

            if (Util.isEmulator()) {
                wegmansRequest.execute("20876", query);
                walmartRequest.execute("20876", query);
                targetRequest.execute("20876", "1", "5", query);
                microcenterRequest.execute(query);

            } else {
                String zipCode = Util.GetZipCode(activity);
                wegmansRequest.execute(zipCode, query);
                walmartRequest.execute(zipCode, query);
                targetRequest.execute(zipCode, "1", "5", query);
                microcenterRequest.execute(query);
            }

        }

        return itemList;
    }

    @Override
    protected void onPostExecute(ArrayList items) {

    }

}

package com.example.shoppy;

import android.os.AsyncTask;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.Context;
import android.view.View;

public class AsyncMakeMultipleRequest extends AsyncTask<Void, Void, ArrayList> {

    private MainActivity mainActivity;
    private ArrayList<String> itemList = new ArrayList<>();

    public AsyncMakeMultipleRequest(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected ArrayList doInBackground(Void... params) {

        if (mainActivity == null) {
            return null;
        }

        mainActivity.ClearAllLists();

        ShoppyDatabase db = Room.databaseBuilder(mainActivity.getApplicationContext(),
                ShoppyDatabase.class, "Shoppy").build();

        List<ShoppingListItem> listItems = db.shoppingListItemDAO().getAllListItems();

        for (ShoppingListItem listItem : listItems) {
            itemList.add(listItem.itemName);
        }

        mainActivity.NumItemsLeft = itemList.size();

        db.close();


        if (itemList.isEmpty()) {
            Context context = mainActivity.getApplicationContext();
            CharSequence text = "Please add an item to your shopping list.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);

            toast.setGravity(Gravity.CENTER, 0, 0);

            toast.show();
            return null;
        }

        System.out.println("This may take several minutes. Please wait");

        View v = mainActivity.GetMainFragment().getView();
        ProgressBar progressBar = v.findViewById(R.id.searchFromListProgressBar);
        progressBar.setMax(itemList.size());

        for (int i = 0; i < itemList.size(); i++) {
            String query = itemList.get(i);
            TargetRequest targetRequest = new TargetRequest(mainActivity);
            WegmansRequest wegmansRequest = new WegmansRequest(mainActivity);
            WalmartRequest walmartRequest = new WalmartRequest(mainActivity);

            System.out.println("Round: " + i + " of requests has been made.");

            if (mainActivity.isEmulator()) {
                wegmansRequest.execute("20876", query);
                walmartRequest.execute("20876", query);
                targetRequest.execute("20876", "1", "5", query);

            } else {
                String zipCode = mainActivity.GetZipCode();
                wegmansRequest.execute(zipCode, query);
                walmartRequest.execute(zipCode, query);
                targetRequest.execute(zipCode, "1", "5", query);
            }


            int currentProgress = i / progressBar.getMax();
            progressBar.setProgress(currentProgress);
            System.out.println("Progress made.");

        }

        return itemList;
    }

    @Override
    protected void onPostExecute(ArrayList items) {


        //mainActivity.StartSort();

    }

}

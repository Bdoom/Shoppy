package com.example.shoppy;

import androidx.room.RoomDatabase;
import androidx.room.Database;

@Database(entities = {ShoppingListItem.class}, version = 1)
public abstract class ShoppyDatabase extends RoomDatabase {

    public abstract ShoppingListItemDAO shoppingListItemDAO();

}

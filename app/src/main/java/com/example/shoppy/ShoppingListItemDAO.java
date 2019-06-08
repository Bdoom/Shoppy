package com.example.shoppy;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;

import java.util.List;

@Dao
public interface ShoppingListItemDAO {

    @Query("SELECT * FROM shopping_list")
    List<ShoppingListItem> getAllListItems();

    @Insert
    void insertAll(ShoppingListItem... items);

    @Query("DELETE FROM shopping_list WHERE item_name = :itemName")
    void delete(String itemName);

    @Query("UPDATE shopping_list SET item_name = :newName WHERE item_name = :oldName")
    void update(String newName, String oldName);


}

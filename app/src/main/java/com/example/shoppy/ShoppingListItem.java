package com.example.shoppy;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shopping_list")
public class ShoppingListItem {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "item_name")
    public String itemName;

}

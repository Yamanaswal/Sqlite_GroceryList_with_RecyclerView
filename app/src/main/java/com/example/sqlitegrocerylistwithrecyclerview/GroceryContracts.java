package com.example.sqlitegrocerylistwithrecyclerview;

import android.provider.BaseColumns;

public class GroceryContracts {

    private GroceryContracts() {
    }

    public static final class GroceryEntry implements BaseColumns {
        public static final String Table_Name = "groceryList";
        public static final String Column_Name = "name";
        public static final String Column_Amount = "amount";
        public static final String Column_TimeStamp = "timestamp";
    }
}

package com.example.sqlitegrocerylistwithrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Sql DB
    private SQLiteDatabase mydatabase;

    //RecyclerView Adapter
    private GroceryAdapter myAdapter;

    private EditText textName;
    private TextView viewAmount;
    private int amount = 0;
    Button plus_button, minus_button, add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create Database in SQL
        GroceryDBhelper groceryDBhelper = new GroceryDBhelper(this);
        mydatabase = groceryDBhelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new GroceryAdapter(this, getAllItems());
        recyclerView.setAdapter(myAdapter);

        //
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeId((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        textName = findViewById(R.id.editText_name);
        viewAmount = findViewById(R.id.textView_name);
        plus_button = findViewById(R.id.plus_button);
        minus_button = findViewById(R.id.minus_button);
        add_button = findViewById(R.id.add_button);

        plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
            }
        });

        minus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease();
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    public void increase() {
        amount++;
        viewAmount.setText(String.valueOf(amount));
    }

    public void decrease() {
        if (amount > 0) {
            amount--;
            viewAmount.setText(String.valueOf(amount));
        }
    }

    //Add items to Sql DB
    public void addItem() {
        if (textName.getText().toString().trim().length() == 0 || amount == 0) {
            return;
        }
        String itemText = textName.getText().toString();
        //Enter Data to DB-SQLite
        ContentValues contentValues = new ContentValues();
        contentValues.put(GroceryContracts.GroceryEntry.Column_Name, itemText);
        contentValues.put(GroceryContracts.GroceryEntry.Column_Amount, amount);
        mydatabase.insert(GroceryContracts.GroceryEntry.Table_Name, null, contentValues);
        myAdapter.swapCursor(getAllItems());
        //clear the text field
        textName.getText().clear();

    }

    //Get the Cursor with ALL ITEMS DATA
    private Cursor getAllItems() {
        return mydatabase.query(GroceryContracts.GroceryEntry.Table_Name, null,
                null,
                null,
                null,
                null,
                GroceryContracts.GroceryEntry.Column_TimeStamp + " DESC"
        );
    }

    //Remove Items on Swapping on Recycler View
    private void removeId(long id) {
        mydatabase.delete(GroceryContracts.GroceryEntry.Table_Name,
                GroceryContracts.GroceryEntry._ID + "=" + id, null);
        myAdapter.swapCursor(getAllItems());
    }
}

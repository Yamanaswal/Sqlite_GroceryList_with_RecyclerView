package com.example.sqlitegrocerylistwithrecyclerview;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {

    public class GroceryViewHolder extends RecyclerView.ViewHolder {

        TextView nameText, countText;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.name_text);
            countText = itemView.findViewById(R.id.amount_text);
        }
    }

    Context context;
    Cursor cursor;

    public GroceryAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;

    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.grocery_list, parent, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }
        String name = cursor.getString(cursor.getColumnIndex(GroceryContracts.GroceryEntry.Column_Name));
        int amount = cursor.getInt(cursor.getColumnIndex(GroceryContracts.GroceryEntry.Column_Amount));

        long id = cursor.getLong(cursor.getColumnIndex(GroceryContracts.GroceryEntry._ID));
        holder.nameText.setText(name);
        holder.countText.setText(String.valueOf(amount));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }

        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }

    }
}

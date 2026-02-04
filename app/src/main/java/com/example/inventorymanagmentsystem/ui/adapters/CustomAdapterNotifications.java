package com.example.inventorymanagmentsystem.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.models.Item;

import java.util.ArrayList;

public class CustomAdapterNotifications extends ArrayAdapter<Item> {
    private final Context context;
    private ArrayList<Item> listItem;

    public CustomAdapterNotifications(Context context, ArrayList<Item> listItem) {
        super(context, R.layout.custom_noticeslv, new ArrayList<>(listItem));
        this.context = context;
        this.listItem = listItem;
    }

    public void updateList(ArrayList<Item> list) {
        // Keep listItem in sync with the data shown by the adapter (getView uses listItem.get(position))
        this.listItem = list != null ? list : new ArrayList<>();
        clear();
        addAll(this.listItem);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater=LayoutInflater.from(context);
            view=inflater.inflate(R.layout.custom_noticeslv,parent,false);
        }
        TextView tv=view.findViewById(R.id.customTV);
        Item currentItem=listItem.get(position);
        String text = context.getString(R.string.notification_item_format,
                currentItem.getName(), currentItem.getType(), currentItem.getCurrentQuantity());
        tv.setText(text);
        return view;
    }
}

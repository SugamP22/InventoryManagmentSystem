package com.example.inventorymanagmentsystem.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.ItemType;

import java.util.ArrayList;

public class CustomAdapterItems extends ArrayAdapter<Item> {

    private Context context;
    private ArrayList<Item> itemList;

    private Integer[] images_resources;


    public CustomAdapterItems(Context context, ArrayList<Item> itemList, Integer[] images_resources) {
        super(context, R.layout.custom_listview, new ArrayList<>(itemList));
        this.context = context;
        this.itemList = new ArrayList<>(itemList);
        this.images_resources = images_resources;
    }

    public void updateList(ArrayList<Item> newList) {
        this.itemList = newList;
        clear();
        addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.custom_listview, parent, false);
        }
        Item currentItem = itemList.get(position);
        TextView imageText = view.findViewById(R.id.textView);
        ImageView imgView = view.findViewById(R.id.imgview);
        TextView imageType=view.findViewById(R.id.typetv);
        TextView num=view.findViewById(R.id.itemid);


        imageText.setText(currentItem.getName());
        imageType.setText(currentItem.getType().toString());
        num.setText(currentItem.getId()+".");

        // We use 0-based indices: 0=vegetal, 1=meat, 2=fish
        if (currentItem.getType().equals(ItemType.VEGETABLE)) {
            imgView.setImageResource(images_resources[0]);
        } else if (currentItem.getType().equals(ItemType.MEAT)) {
            imgView.setImageResource(images_resources[1]);
        } else {
            imgView.setImageResource(images_resources[2]);
        }
        return view;

    }
}

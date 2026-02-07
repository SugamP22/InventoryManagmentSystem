package com.example.inventorymanagmentsystem.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.inventorymanagmentsystem.R;

/**
 * Adapter for the main screen category filter spinner. I use spinner_main (All / Meat / Fish / Vegetable)
 * and I show the "All" option in gray so it's clear it's the default filter.
 */
public class CategorySpinnerAdapter extends ArrayAdapter<String> {

    public CategorySpinnerAdapter(Context context) {
        super(context, android.R.layout.simple_spinner_item,
                context.getResources().getStringArray(R.array.spinner_main));
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        tv.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
        return view;
    }
}

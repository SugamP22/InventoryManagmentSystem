package com.example.inventorymanagmentsystem.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.utils.IntentUtils;

public class AddingItemActivity extends AppCompatActivity {
    private Item item;
    private AppCompatButton btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_item);
        btnHome=findViewById(R.id.btnHomeAddV);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.change(AddingItemActivity.this, MainActivity.class);
            }
        });
    }
}
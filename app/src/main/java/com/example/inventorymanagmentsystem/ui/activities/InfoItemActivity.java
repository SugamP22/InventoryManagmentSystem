package com.example.inventorymanagmentsystem.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.utils.IntentUtils;

public class InfoItemActivity extends AppCompatActivity  implements  View.OnClickListener{
    AppCompatButton btnHome;
    AppCompatButton btnModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_information);
        btnHome=findViewById(R.id.home);
        btnModify=findViewById(R.id.modify);
        btnHome.setOnClickListener(this);
        btnModify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id == btnHome.getId()) {
            IntentUtils.change(InfoItemActivity.this, MainActivity.class);
        } else if (id == btnModify.getId()) {
            IntentUtils.change(InfoItemActivity.this, UpdateActivity.class);
        }
    }
}
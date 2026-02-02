package com.example.inventorymanagmentsystem.ui.activities;

import android.content.Intent;
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

public class NotificationsActivity extends AppCompatActivity {
    AppCompatButton btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notices);
        btnHome=findViewById(R.id.btnHomeNotV);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.change(NotificationsActivity.this, MainActivity.class);
            }
        });

    }
}
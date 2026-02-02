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

public class UpdateActivity extends AppCompatActivity {
    AppCompatButton btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updating_item);
        btnHome=findViewById(R.id.btnHomeUpdV);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.change(UpdateActivity.this, InfoItemActivity.class);
            }
        });
    }
}
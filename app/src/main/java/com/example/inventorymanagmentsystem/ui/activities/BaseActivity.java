package com.example.inventorymanagmentsystem.ui.activities;


import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagmentsystem.utils.LocaleHelper;


public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        // This connects every activity to the LocaleHelper
        super.attachBaseContext(LocaleHelper.wrapContext(newBase));
    }
}
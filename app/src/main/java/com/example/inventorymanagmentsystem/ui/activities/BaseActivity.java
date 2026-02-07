package com.example.inventorymanagmentsystem.ui.activities;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagmentsystem.utils.LocaleHelper;

/**
 * Base for activities that need the app language (en/es). I wrap the context with the saved
 * locale so all child activities get the correct strings without me passing it everywhere.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.wrapContext(newBase));
    }
}
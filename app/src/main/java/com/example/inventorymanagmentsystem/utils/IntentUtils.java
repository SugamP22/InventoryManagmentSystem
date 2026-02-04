package com.example.inventorymanagmentsystem.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.inventorymanagmentsystem.ui.activities.InfoItemActivity;
import com.example.inventorymanagmentsystem.ui.activities.UpdateActivity;


public class IntentUtils {
//    changing the activity without finishing the previous one
    public static void change(Context context, Class<?> targetActivity) {
        Intent intent=new Intent(context,targetActivity);
        context.startActivity(intent);

    }

    // Start activity AND finish current activity
    public static void changeAndFinish(Activity currentActivity, Class<?> targetActivity) {
        Intent intent = new Intent(currentActivity, targetActivity);
        currentActivity.startActivity(intent);
        currentActivity.finish(); // This closes the current activity
    }

    public static void changeWithExtras(Context infoItemActivity, Class<?> updateActivityClass, String key, int value) {
        Intent intent=new Intent(infoItemActivity,updateActivityClass);
        intent.putExtra(key,value);
        infoItemActivity.startActivity(intent);
    }
}


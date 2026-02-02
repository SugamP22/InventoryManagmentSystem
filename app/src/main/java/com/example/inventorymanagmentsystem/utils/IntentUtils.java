package com.example.inventorymanagmentsystem.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.inventorymanagmentsystem.ui.activities.MainActivity;
import com.example.inventorymanagmentsystem.ui.activities.NotificationsActivity;

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
}


package com.example.inventorymanagmentsystem.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * I use these helpers to start activities so I don't repeat the same intent code. change() keeps
 * the current activity in the stack; changeWithExtras() passes a key-value (e.g. ITEM_ID) and finishes the current activity.
 */
public class IntentUtils {

    /** I start the target activity and leave the current one in the back stack. */
    public static void change(Context context, Class<?> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }

    /** I start the target activity and finish the current one so the user can't come back to it. */
    public static void changeAndFinish(Activity currentActivity, Class<?> targetActivity) {
        Intent intent = new Intent(currentActivity, targetActivity);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    /** I start the target activity with one int extra (e.g. ITEM_ID) and finish the current activity. */
    public static void changeWithExtras(Activity infoItemActivity, Class<?> updateActivityClass, String key, int value) {
        Intent intent = new Intent(infoItemActivity, updateActivityClass);
        intent.putExtra(key, value);
        infoItemActivity.startActivity(intent);
        infoItemActivity.finish();
    }
}


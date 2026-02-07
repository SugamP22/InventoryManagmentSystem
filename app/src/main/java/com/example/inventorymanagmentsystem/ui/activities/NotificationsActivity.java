package com.example.inventorymanagmentsystem.ui.activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.data.repository.ItemRepository;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.ui.adapters.CustomAdapterNotifications;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Screen that shows items with low stock (quantity below 5). I poll the DB every 5 seconds on a
 * background thread and update the list on the UI thread so the user always sees current data.
 */
public class NotificationsActivity extends AppCompatActivity {
    private CustomAdapterNotifications adapter;
    private ItemRepository itemRepository;
    private ListView notificationLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notices);

        itemRepository = new ItemRepository(this);
        notificationLv = findViewById(R.id.notificationCustomLv);
        AppCompatButton homeBtn = findViewById(R.id.btnHomeNotV);

        adapter = new CustomAdapterNotifications(this, new ArrayList<>());

        if (notificationLv != null) {
            notificationLv.setAdapter(adapter);
            startStockMonitor();
        }

        if (homeBtn != null) {
            homeBtn.setOnClickListener(v -> finish());
        }
    }

    /** I run a loop on a background thread: fetch low-stock items, post update to UI, then sleep 5 seconds. */
    private void startStockMonitor() {
        Executors.newSingleThreadExecutor().execute(() -> {
            while (!isFinishing()) {
                try {
                    List<Item> lowStock = itemRepository.getLowStockItems(5);
                    ArrayList<Item> listItems = lowStock != null ? new ArrayList<>(lowStock) : new ArrayList<>();

                    runOnUiThread(() -> {
                        if (!isFinishing() && !isDestroyed() && adapter != null) {
                            adapter.updateList(listItems);
                        }
                    });

                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
    }
}
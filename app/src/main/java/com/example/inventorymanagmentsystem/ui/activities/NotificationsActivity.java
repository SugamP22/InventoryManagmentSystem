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

public class NotificationsActivity extends AppCompatActivity {
    private CustomAdapterNotifications adapter;
    private ItemRepository itemRepository;
    private ListView notificationLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notices);

        // 1. Initialize Repository FIRST
        itemRepository = new ItemRepository(this);

        // 2. Connect UI with the EXACT ID from your XML
        notificationLv = findViewById(R.id.notificationCustomLv);
        AppCompatButton homeBtn = findViewById(R.id.btnHomeNotV);

        // 3. Initialize Adapter with an EMPTY list so it's not null
        adapter = new CustomAdapterNotifications(this, new ArrayList<>());

        if (notificationLv != null) {
            notificationLv.setAdapter(adapter);
            // 4. Start the background thread
            startStockMonitor();
        }

        if (homeBtn != null) {
            homeBtn.setOnClickListener(v -> finish());
        }
    }

    private void startStockMonitor() {
        Executors.newSingleThreadExecutor().execute(() -> {
            while (!isFinishing()) {
                try {
                    // Fetch data
                    List<Item> lowStock = itemRepository.getLowStockItems(5);
                    ArrayList<Item> listItems = lowStock != null ? new ArrayList<>(lowStock) : new ArrayList<>();

                    // Post update to UI Thread (only if activity is still alive)
                    runOnUiThread(() -> {
                        if (!isFinishing() && !isDestroyed() && adapter != null) {
                            adapter.updateList(listItems);
                        }
                    });

                    // Wait 5 seconds
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                    break; // Exit if error occurs
                }
            }
        });
    }
}
package com.example.inventorymanagmentsystem.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.data.repository.ItemRepository;
import com.example.inventorymanagmentsystem.data.repository.TransactionRepository;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.ui.adapters.CategorySpinnerAdapter;
import com.example.inventorymanagmentsystem.ui.adapters.CustomAdapterItems;
import com.example.inventorymanagmentsystem.utils.IntentUtils;
import com.example.inventorymanagmentsystem.utils.ItemFilter;
import com.example.inventorymanagmentsystem.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ListView mCustomLv;
    private Spinner spinner;
    private ImageButton languageBtn;
    private TextView tvTitle;
    private AppCompatButton infoBtn;
    private AppCompatButton addItemBtn;

    private final Integer[] images = {R.drawable.imavegetal, R.drawable.imgmeat, R.drawable.imgfish};

    // We keep this list to handle filtering logic
    private ArrayList<Item> itemList;
    private ItemRepository itemRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Initialize UI and Repository
        connectXML();
        itemRepository = new ItemRepository(this);

        // load default data if empty
        itemRepository.seedDataBase();

        TransactionRepository transactionRepository = new TransactionRepository(this);
        transactionRepository.seedTransactionData(itemRepository.getAllItems());

        // Load initial data from Database
        loadDataFromDatabase();

        //  Set up Spinner and Adapter
        spinner.setAdapter(new CategorySpinnerAdapter(this));
        CustomAdapterItems adapterItems = new CustomAdapterItems(this, itemList, images);
        mCustomLv.setAdapter(adapterItems);

        // 5. Filter logic
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Filter the current itemList based on category
                ArrayList<Item> filteredList = ItemFilter.filter(itemList, position);
                CustomAdapterItems adapter = (CustomAdapterItems) mCustomLv.getAdapter();
                if (adapter != null) {
                    adapter.updateList(filteredList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Helper method to pull data from Room and update our local ArrayList
     */
    private void loadDataFromDatabase() {
        List<Item> dbItems = itemRepository.getAllItems();
        itemList = new ArrayList<>(dbItems);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data whenever we return to this screen (e.g., after adding an item)
        loadDataFromDatabase();
        CustomAdapterItems adapter = (CustomAdapterItems) mCustomLv.getAdapter();
        if (adapter != null) {
            // Reset spinner to "All" (position 0) or just refresh current view
            adapter.updateList(itemList);
            spinner.setSelection(0);
        }
    }

    private void connectXML() {
        spinner = findViewById(R.id.spinner_main);
        languageBtn = findViewById(R.id.btn_lng);
        tvTitle = findViewById(R.id.textView);
        infoBtn = findViewById(R.id.btn_notice);
        mCustomLv = findViewById(R.id.customlv);
        addItemBtn = findViewById(R.id.btnCreateMainV);

        String currentLang = LocaleHelper.getSavedLanguage(this);
        languageBtn.setImageResource(currentLang.equals("en") ? R.drawable.img_spain : R.drawable.img_uk);

        languageBtn.setOnClickListener(v -> toggleLanguage());
        addItemBtn.setOnClickListener(this);
        infoBtn.setOnClickListener(this);

        // Pass the ID to InfoItemActivity so it knows which item to show
        mCustomLv.setOnItemClickListener((parent, view, position, id) -> {
            Item selectedItem = (Item) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, InfoItemActivity.class);
            intent.putExtra("ITEM_ID", selectedItem.getId());
            startActivity(intent);
        });
    }

    private void toggleLanguage() {
        String currentLang = LocaleHelper.getSavedLanguage(this);
        String newLang = currentLang.equals("en") ? "es" : "en";
        LocaleHelper.saveLanguage(this, newLang);

        Intent intent = getIntent();
        finish();
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == addItemBtn.getId()) {
            IntentUtils.change(MainActivity.this, AddingItemActivity.class);
        } else if (id == infoBtn.getId()) {
            IntentUtils.change(MainActivity.this, NotificationsActivity.class);
        }
    }
}
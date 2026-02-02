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
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.ui.adapters.CategorySpinnerAdapter;
import com.example.inventorymanagmentsystem.ui.adapters.CustomAdapterItems;
import com.example.inventorymanagmentsystem.utils.IntentUtils;
import com.example.inventorymanagmentsystem.utils.ItemFilter;
import com.example.inventorymanagmentsystem.utils.LocaleHelper;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ListView mCustomLv;
    private Spinner spinner;
    private ImageButton languageBtn;
    private TextView tvTitle;
    private AppCompatButton infoBtn;

    private AppCompatButton addItemBtn;
    private Integer[] images = {R.drawable.imavegetal, R.drawable.imgmeat, R.drawable.imgfish};

    private ArrayList<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectXML();
        itemList = ItemRepository.getDefaultItems();

        spinner.setAdapter(new CategorySpinnerAdapter(this));
        CustomAdapterItems adapterItems = new CustomAdapterItems(this, itemList, images);
        mCustomLv.setAdapter(adapterItems);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
        mCustomLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentUtils.change(MainActivity.this, InfoItemActivity.class);
            }
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

package com.example.inventorymanagmentsystem.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.data.repository.ItemRepository;
import com.example.inventorymanagmentsystem.data.repository.TransactionRepository;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.TransactionType;
import com.example.inventorymanagmentsystem.utils.ImageUtils;
import com.example.inventorymanagmentsystem.utils.IntentUtils;

import java.time.LocalDate;

public class InfoItemActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btnHome, btnModify, btnAdd, btnRemove;

    ImageView img;

    TextView id, quantity, name, description, type, entryDate, exitDate;
    EditText EtAddRemove;

    int idCurrentItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_information);
        getCurrentItemID();
        connectXML();
        addListeners();
        getCurrentItem();
    }

    private void fillInfoItem(Item item, LocalDate entry, LocalDate exit) {
        img.setImageResource(ImageUtils.getImageResource(item.getType()));

        // checking null pointers
        String strId = item.getId() <= 0 ? "N/A" : String.valueOf(item.getId());
        String strName = item.getName().isEmpty() ? "N/A" : item.getName();
        String strType = item.getType().toString().isEmpty() ? "N/A" : item.getType().toString();
        String strDescription = item.getDescription().isEmpty() ? "N/A" : item.getDescription();
        String strQuantity = (item.getId() <= 0) ? "N/A" : String.valueOf(item.getCurrentQuantity());
        String strEntry = String.valueOf(entry).isEmpty() ? "N/A" : String.valueOf(entry);
        String strExit = String.valueOf(exit).isEmpty() ? "N/A" : String.valueOf(exit);

        // setting texts
        id.setText(strId);
        name.setText(strName);
        type.setText(strType);
        description.setText(strDescription);
        entryDate.setText(strEntry);
        exitDate.setText(strExit);
        quantity.setText(strQuantity);


    }

    private void getCurrentItem() {
        ItemRepository itemRepository = new ItemRepository(this);
        TransactionRepository transactionRepository = new TransactionRepository(this);
        LocalDate entry = transactionRepository.getDateByItemTypeAndID(TransactionType.ENTRY, idCurrentItem);
        LocalDate exit = transactionRepository.getDateByItemTypeAndID(TransactionType.EXIT, idCurrentItem);
        Item currentItem = itemRepository.getItem(idCurrentItem);
        fillInfoItem(currentItem, entry, exit);
    }

    // -- getting id from the intent and closing view if not found --s
    private void getCurrentItemID() {
        idCurrentItem = getIntent().getIntExtra("ITEM_ID", -1);
        if (idCurrentItem == -1) {
            Toast.makeText(this, "Error: Select item not found", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    //adding listner to buttons
    private void addListeners() {
        btnHome.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    //connecting to xml
    private void connectXML() {
        btnHome = findViewById(R.id.home);
        btnModify = findViewById(R.id.modify);
        EtAddRemove = findViewById(R.id.etAddRemove);
        id = findViewById(R.id.infoidtv);
        name = findViewById(R.id.infonombretv);
        description = findViewById(R.id.infoDescription);
        type = findViewById(R.id.infotypetv);
        entryDate = findViewById(R.id.infoDateEntry);
        exitDate = findViewById(R.id.infodateExit);
        btnAdd = findViewById(R.id.btnPlus);
        btnRemove = findViewById(R.id.btnMinus);
        img = findViewById(R.id.infoImg);
        quantity = findViewById(R.id.infoCantidadtv);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == btnHome.getId()) {
            finish();
        } else if (id == btnModify.getId()) {
            IntentUtils.change(InfoItemActivity.this, UpdateActivity.class);
        }
    }
}
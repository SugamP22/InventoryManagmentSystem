package com.example.inventorymanagmentsystem.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.data.repository.ItemRepository;
import com.example.inventorymanagmentsystem.data.repository.TransactionRepository;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.TransactionType;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Class that looks after updating a certain item in the DB
 */
public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btnBack, btnUpdate;
    ItemRepository itemRepository;
    TransactionRepository transactionRepository;
    TextInputEditText id, name, category, description, totalQuantity, entry, exit;
    int currentItemID;

    Item itemToUpdate;


    /**
     * on create we connect our xml with our activity ,set listeners, verify out data is not null or empty and then update if possible
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemRepository = new ItemRepository(this);
        transactionRepository = new TransactionRepository(this);
        setContentView(R.layout.updating_item);
        currentItemID = getIntent().getIntExtra("ITEM_ID", -1);
        if (currentItemID < 0) {
            Toast.makeText(this, getString(R.string.errorIntentLoad), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == btnBack.getId()) {
            finish();

        }
    }
}
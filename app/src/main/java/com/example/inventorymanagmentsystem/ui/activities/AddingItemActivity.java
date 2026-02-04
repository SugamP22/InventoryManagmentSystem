package com.example.inventorymanagmentsystem.ui.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.inventorymanagmentsystem.R;
import com.example.inventorymanagmentsystem.data.repository.ItemRepository;
import com.example.inventorymanagmentsystem.data.repository.TransactionRepository;
import com.example.inventorymanagmentsystem.models.Item;
import com.example.inventorymanagmentsystem.models.ItemType;
import com.example.inventorymanagmentsystem.models.Transaction;
import com.example.inventorymanagmentsystem.models.TransactionType;
import com.example.inventorymanagmentsystem.utils.CleaningUtils;
import com.example.inventorymanagmentsystem.utils.ConversionUtils;
import com.example.inventorymanagmentsystem.utils.IntentUtils;
import com.example.inventorymanagmentsystem.utils.ValidationUtils;

import java.time.format.DateTimeParseException;

public class AddingItemActivity extends AppCompatActivity {
    private AppCompatButton btnHome, btnCreate;
    private EditText editTextName, editTextType, editTextDescription, editTextQuantity, editTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_item);
        connectXML();

        editTextDate.setText(ConversionUtils.getCurrentDate());

        btnCreate.setOnClickListener(v -> {
            ItemRepository itemRepository = new ItemRepository(this);
            TransactionRepository transactionRepository = new TransactionRepository(this);
            // 1. Basic empty check
            if (ValidationUtils.isInputsValid(this,editTextName, editTextType, editTextDescription, editTextQuantity)) {

                try {
                    // 2. Try to parse technical data
                    int quantity = ConversionUtils.parseQuantity(editTextQuantity.getText().toString());
                    ItemType type = ConversionUtils.parseType(editTextType.getText().toString());

                    // 3. If we got here, data is valid! Create the object
                    Item newItem = new Item();
                    newItem.setName(editTextName.getText().toString().trim());
                    newItem.setType(type);
                    newItem.setDescription(editTextDescription.getText().toString().trim());
                    newItem.setCurrentQuantity(quantity);
                    int id_item = itemRepository.returnIDItem(newItem);
                    Transaction transaction = new Transaction();
                    String dateStr = editTextDate.getText().toString().trim();
                    transaction.setDate(ConversionUtils.stringToLocalDate(dateStr));
                    transaction.setItem_id(id_item);
                    transaction.setType(TransactionType.ENTRY);
                    transactionRepository.insertTransaction(transaction);
                    Toast.makeText(this, getString(R.string.msg_item_created_success), Toast.LENGTH_SHORT).show();
                    CleaningUtils.clearData(editTextName, editTextType, editTextDescription, editTextQuantity,editTextDate);
                } catch (NumberFormatException e) {
                    // Catch quantity errors
                    editTextQuantity.setError(getString(R.string.validErrorMSG));
                    Toast.makeText(this, getString(R.string.msg_invalid_quantity), Toast.LENGTH_SHORT).show();
                } catch (IllegalArgumentException e) {
                    // Catch Enum errors (e.g., user typed "Pizza" instead of "Meat")
                    editTextType.setError(getString(R.string.validItemType));
                    Toast.makeText(this, getString(R.string.msg_invalid_item_type), Toast.LENGTH_SHORT).show();
                } catch (DateTimeParseException e) {
                    //catch error related to date format
                    editTextDate.setError(getString(R.string.hint_date_format));
                    Toast.makeText(this, getString(R.string.msg_invalid_date), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHome.setOnClickListener(v -> IntentUtils.change(this, MainActivity.class));
    }

    private void connectXML() {
        editTextName = findViewById(R.id.etName);
        editTextDate = findViewById(R.id.etDate);
        editTextDescription = findViewById(R.id.etDescription);
        editTextQuantity = findViewById(R.id.etQuantity);
        editTextType = findViewById(R.id.etType);
        btnHome = findViewById(R.id.btnHomeAddV);
        btnCreate = findViewById(R.id.btnCreateAddV);
    }
}
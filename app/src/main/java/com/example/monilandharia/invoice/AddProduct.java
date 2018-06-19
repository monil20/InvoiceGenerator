package com.example.monilandharia.invoice;

import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {

    public EditText product_title,product_desc,product_qty,product_rate,product_note;
    TextView title_text;
    Button save;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/ProductSans-Regular.ttf");

        title_text = (TextView) findViewById(R.id.add_product_title);

        product_title = (EditText) findViewById(R.id.product_title);
        product_desc = (EditText) findViewById(R.id.product_desc);
        product_qty = (EditText) findViewById(R.id.product_qty);
        product_rate = (EditText) findViewById(R.id.product_rate);
        product_note = (EditText) findViewById(R.id.product_note);

        save = (Button) findViewById(R.id.product_save);

        title_text.setTypeface(typeface);
        product_title.setTypeface(typeface);
        product_desc.setTypeface(typeface);
        product_qty.setTypeface(typeface);
        product_rate.setTypeface(typeface);
        product_note.setTypeface(typeface);
        save.setTypeface(typeface);

        product_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(product_title);
            }
        });

        product_desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(product_desc);
            }
        });

        product_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(product_qty);
            }
        });

        product_rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(product_rate);
            }
        });

        databaseHelper = new DatabaseHelper(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_title_string = product_title.getText().toString();
                String product_desc_string = product_desc.getText().toString();
                String product_qty_string = product_qty.getText().toString();
                String product_rate_string = product_rate.getText().toString();
                String product_note_string = product_note.getText().toString();


                if(checkValidation()){
                    boolean insert_test = databaseHelper.TABLE_PRODUCTS_onInsert(product_title_string, product_desc_string, product_qty_string, product_rate_string, product_note_string);
                    if (insert_test == true) {
                        Toast.makeText(AddProduct.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(AddProduct.this, "Oops, facing downtime", Toast.LENGTH_SHORT).show();
                    }
                }
//                product_title.setText("");
//                product_desc.setText("");
//                product_qty.setText("");
//                product_rate.setText("");
//                product_note.setText("");
//                product_title.setFocusableInTouchMode(true);
//                product_title.requestFocus();
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(product_title)) ret = false;
        if (!Validation.hasText(product_desc)) ret = false;
        if (!Validation.hasText(product_qty)) ret = false;
        if (!Validation.hasText(product_rate)) ret = false;

        return ret;
    }

}

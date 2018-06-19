package com.example.monilandharia.invoice;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddClient extends AppCompatActivity {

    EditText client_number, client_name, client_email, client_addr1, client_addr2, client_addr3, client_city, client_state, client_website;
    TextView title_text;
    Button save;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ProductSans-Regular.ttf");

        title_text = (TextView) findViewById(R.id.add_client_title);

        client_number = (EditText) findViewById(R.id.client_number);
        client_name = (EditText) findViewById(R.id.client_name);
        client_email = (EditText) findViewById(R.id.client_email);
        client_addr1 = (EditText) findViewById(R.id.client_addr1);
        client_addr2 = (EditText) findViewById(R.id.client_addr2);
        client_addr3 = (EditText) findViewById(R.id.client_addr3);
        client_city = (EditText) findViewById(R.id.client_city);
        client_state = (EditText) findViewById(R.id.client_state);
        client_website = (EditText) findViewById(R.id.client_website);

        save = (Button) findViewById(R.id.client_save);

        title_text.setTypeface(typeface);
        client_number.setTypeface(typeface);
        client_name.setTypeface(typeface);
        client_email.setTypeface(typeface);
        client_addr1.setTypeface(typeface);
        client_addr2.setTypeface(typeface);
        client_addr3.setTypeface(typeface);
        client_city.setTypeface(typeface);
        client_state.setTypeface(typeface);
        client_website.setTypeface(typeface);
        save.setTypeface(typeface);

        databaseHelper = new DatabaseHelper(this);

        client_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(client_number);
            }
        });

        client_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(client_name);
            }
        });

        client_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.isEmailAddress(client_email, true);
            }
        });

        client_addr1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(client_addr1);
            }
        });

        client_addr2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(client_addr2);
            }
        });

        client_addr3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(client_addr3);
            }
        });

        client_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(client_city);
            }
        });

        client_state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(client_state);
            }
        });

        client_website.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.isWebsite(client_website, false);

            }
        });

        client_website.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(client_website.getText().toString()==""){
                    client_website.setError(null);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String client_name_string = client_name.getText().toString();
                String client_number_string = client_number.getText().toString();
                String client_email_string = client_email.getText().toString();
                String client_addr1_string = client_addr1.getText().toString();
                String client_addr2_string = client_addr2.getText().toString();
                String client_addr3_string = client_addr3.getText().toString();
                String client_city_string = client_city.getText().toString();
                String client_state_string = client_state.getText().toString();
                String client_website_string = client_website.getText().toString();


                if (checkValidation()) {
                    boolean insert_test = databaseHelper.TABLE_CLIENTS_onInsert(client_name_string, client_number_string, client_email_string, client_addr1_string, client_addr2_string, client_addr3_string, client_city_string, client_state_string, client_website_string);
                    if (insert_test == true) {
                        Toast.makeText(AddClient.this, "Client Added Successfully", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(AddClient.this, "Oops, facing downtime", Toast.LENGTH_SHORT).show();
                    }
//                    client_name.setText("");
//                    client_number.setText("");
//                    client_email.setText("");
//                    client_addr1.setText("");
//                    client_addr2.setText("");
//                    client_addr3.setText("");
//                    client_city.setText("");
//                    client_state.setText("");
//                    client_website.setText("");
//                    client_number.setFocusableInTouchMode(true);
//                    client_number.requestFocus();
                }
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

        if (!Validation.hasText(client_number)) ret = false;
        if (!Validation.hasText(client_name)) ret = false;
        if (!Validation.hasText(client_addr1)) ret = false;
        if (!Validation.hasText(client_addr2)) ret = false;
        if (!Validation.hasText(client_addr3)) ret = false;
        if (!Validation.hasText(client_city)) ret = false;
        if (!Validation.hasText(client_state)) ret = false;
        if (!Validation.isEmailAddress(client_email, true)) ret = false;
        if (!Validation.isEmailAddress(client_email, true)) ret = false;

        return ret;
    }

}

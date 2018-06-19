package com.example.monilandharia.invoice;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchClient extends AppCompatActivity {

    Toolbar mToolbar;
    EditText search;
    ListView listView;
    ArrayAdapter<String> adapter;
    Typeface typeface;
    DatabaseHelper databaseHelper;
    android.support.design.widget.FloatingActionButton add_new;
    //Clients List
    List<String> client_name = new ArrayList<>();

    String invoice_id;
    String invoice_name, invoice_client_name;
    String client_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

        Intent intent = getIntent();
        invoice_id = intent.getExtras().getString("invoice_id_from_AddInvoice");

        databaseHelper = new DatabaseHelper(this);

        final Cursor res = databaseHelper.TABLE_INVOICE_view();

        while (res.moveToNext()) {
            if (res.getString(0).contentEquals(invoice_id)) {
                invoice_name = res.getString(1);
            }
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar_search_client);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/ProductSans-Regular.ttf");

        init();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
            }
        });


        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                SearchClient.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddClient.class);
                startActivity(intent);
            }
        });

    }

    public void init() {
        search = (EditText) findViewById(R.id.search_client);
        search.setTypeface(typeface);
        listView = (ListView) findViewById(R.id.listView_search_client);
        add_new = (android.support.design.widget.FloatingActionButton) findViewById(R.id.button5);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        client_name.clear();
        databaseHelper = new DatabaseHelper(this);
        Cursor res = databaseHelper.TABLE_CLIENTS_view();
        while (res.moveToNext()) {
            client_name.add(res.getString(1));
        }

        adapter = new ArrayAdapter<String>(this, R.layout.search_list_item, R.id.search_client_name, client_name);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView client_name = (TextView) listView.getChildAt(listView.indexOfChild(view)).findViewById(R.id.search_client_name);
                String temp = client_name.getText().toString();

                Cursor client_table = databaseHelper.TABLE_CLIENTS_view();
                while (client_table.moveToNext()) {
                    if (client_table.getString(1).contentEquals(temp)) {
                        client_id = client_table.getString(0);
                    }
                }

                boolean result = databaseHelper.TABLE_INVOICE_updateData(invoice_id, invoice_name, temp, client_id);
                if (result) {
                    onBackPressed();
                    onBackPressed();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Oops, Facing Downtime", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

package com.example.monilandharia.invoice;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monil Andharia on 01-May-16.
 */
public class SearchProduct extends AppCompatActivity {

    Toolbar mToolbar;
    EditText search;
    ListView listView;
    ArrayAdapter<String> adapter;
    Typeface typeface;
    DatabaseHelper databaseHelper;
    android.support.design.widget.FloatingActionButton add_new;
    //Clients List
    List<String> product_name = new ArrayList<>();
    final Context context = this;

    String product_id_str, product_name_str, product_rate_str, product_avail_qty_str, product_sold_qty_str, product_note_str;
    String client_id;
    String invoice_id;

    int available, sold;

    CustomTextView pro_name, pro_avail_qty, pro_rate;
    EditText pro_sold_qty;
    Button ok;

    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

        mToolbar = (Toolbar) findViewById(R.id.toolbar_search_client);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/ProductSans-Regular.ttf");
        init();

        Intent intent = getIntent();
        client_id = intent.getExtras().getString("client_id_from_AddInvoice");
        invoice_id = intent.getExtras().getString("invoice_id_from_AddInvoice");
//        Toast.makeText(this, client_id, Toast.LENGTH_SHORT).show();

        databaseHelper = new DatabaseHelper(this);


        search.setHint("Search Product");

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
                SearchProduct.this.adapter.getFilter().filter(cs);
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
                Intent intent = new Intent(getApplicationContext(), AddProduct.class);
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

        product_name.clear();
        databaseHelper = new DatabaseHelper(this);
        Cursor res = databaseHelper.TABLE_PRODUCTS_view();
        while (res.moveToNext()) {
            product_name.add(res.getString(1));
        }

        adapter = new ArrayAdapter<String>(this, R.layout.search_list_item, R.id.search_client_name, product_name);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView prod_name = (TextView) listView.getChildAt(listView.indexOfChild(view)).findViewById(R.id.search_client_name);
                product_name_str = prod_name.getText().toString();

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_search_product);
                dialog.setCanceledOnTouchOutside(true);


                pro_name = (CustomTextView) dialog.findViewById(R.id.search_prod_name);
                pro_avail_qty = (CustomTextView) dialog.findViewById(R.id.search_avail_qty);
                pro_rate = (CustomTextView) dialog.findViewById(R.id.search_prod_rate);
                pro_sold_qty = (EditText) dialog.findViewById(R.id.search_sold_qty);
                ok = (Button) dialog.findViewById(R.id.search_ok);

                final Cursor res = databaseHelper.TABLE_PRODUCTS_view();

                while (res.moveToNext()) {
                    if (res.getString(1).contentEquals(product_name_str)) {
                        product_id_str = res.getString(0);
                        pro_name.setText(res.getString(1));
                        temp = res.getString(1);
//                        Toast.makeText(context, temp, Toast.LENGTH_SHORT).show();
                        pro_avail_qty.setText("/ " + res.getString(3));
                        product_avail_qty_str = res.getString(3);
                        pro_rate.setText("Rate: " + res.getString(4));
                        product_rate_str = res.getString(4);
                    }
                }

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkValidation()) {
                            available = Integer.parseInt(product_avail_qty_str);
                            sold = Integer.parseInt(pro_sold_qty.getText().toString());
                            if (sold <= available) {
                                boolean insert_test = databaseHelper.TABLE_INV_PRODUCT_LIST_onInsert(temp, pro_sold_qty.getText().toString(), product_rate_str, client_id, invoice_id);
                                if (insert_test == true) {
                                    int avail_qty_int = Integer.parseInt(product_avail_qty_str);
                                    int sold_qty_int = Integer.parseInt(pro_sold_qty.getText().toString());
                                    int qty_left = avail_qty_int - sold_qty_int;
                                    databaseHelper.TABLE_PRODUCTS_updateQty(product_id_str, String.valueOf(qty_left));
                                    Toast.makeText(getApplicationContext(), "Sales Record added successfully", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Oops, facing downtime", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                pro_sold_qty.setError("Sorry, we don't have that much of quota");
                            }
                        }
                    }
                });

                dialog.show();
            }
        });

    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(pro_sold_qty)) ret = false;

        return ret;
    }

}


// OLD CODE onCreate
//        Cursor cursor = databaseHelper.TABLE_CLIENTS_view();
//
//        try {
//            while (cursor.moveToNext()) {
//                if (cursor.getString(1).contentEquals(client_name)) {
//                    respective_client_id = cursor.getString(0);
//                }
//            }
//        } catch (Exception e) {
//
//        }
//
////        Toast.makeText(getApplicationContext(),respective_client_id,Toast.LENGTH_SHORT).show();
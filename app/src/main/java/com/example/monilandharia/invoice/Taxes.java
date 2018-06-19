package com.example.monilandharia.invoice;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.design.widget.*;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Taxes extends AppCompatActivity {

    android.support.design.widget.FloatingActionButton fab, ok;
    final Context context = this;
    DatabaseHelper databaseHelper;

    EditText tax_number, tax_rate, et_error;
    Spinner tax_type;

    ListView listView;
    List<String> tax_id_list = new ArrayList<>();
    List<String> tax_number_list = new ArrayList<>();
    List<String> tax_rate_list = new ArrayList<>();
    List<String> tax_type_list = new ArrayList<>();
    List<String> tax_flag_list = new ArrayList<>();

    Tax_ListCustomAdapter tax_listCustomAdapter;

    CoordinatorLayout coordinatorLayout;
    String received_intent, invoice_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        boolean flag;

        Intent intent = getIntent();
        received_intent = intent.getExtras().getString("tax_sent_intent");
        invoice_id = intent.getExtras().getString("invoice_id");

        if (received_intent.contentEquals("do_nothing")) {
            ok.setVisibility(View.INVISIBLE);
        } else {
            fab.setVisibility(View.INVISIBLE);
        }


        databaseHelper = new DatabaseHelper(this);

        Cursor res = databaseHelper.TABLE_TAXES_view();
        while (res.moveToNext()) {
            tax_id_list.add(res.getString(0));
            tax_type_list.add(res.getString(1));
            tax_number_list.add(res.getString(2));
            tax_flag_list.add(res.getString(3));
            tax_rate_list.add(res.getString(4));
        }

        tax_listCustomAdapter = new Tax_ListCustomAdapter(context, tax_type_list, tax_number_list, tax_flag_list, tax_rate_list, tax_id_list, received_intent, invoice_id);
        listView.setAdapter(tax_listCustomAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_tax);
                dialog.setCanceledOnTouchOutside(true);

                tax_number = (EditText) dialog.findViewById(R.id.tax_number);
                et_error = (EditText) dialog.findViewById(R.id.editText3);
                tax_type = (Spinner) dialog.findViewById(R.id.tax_type);
                tax_rate = (EditText) dialog.findViewById(R.id.tax_rate);
                Button tax_add = (Button) dialog.findViewById(R.id.tax_add);

                List<String> categories = new ArrayList<String>();
                categories.add("---Tax Type---");
                categories.add("Income");
                categories.add("Capital Gains");
                categories.add("Securities Transaction");
                categories.add("Perquisite");
                categories.add("Corporate");
                categories.add("Sales");
                categories.add("Service");
                categories.add("Value Added");
                categories.add("Custom duty");
                categories.add("Excise Duty");
                categories.add("Anti Dumping Duty");
                categories.add("Municipal ");
                categories.add("Professional");
                categories.add("Dividend distribution");
                categories.add("Entertainment ");
                categories.add("Stamp Duty, Registration Fees, Transfer");
                categories.add("Education Cess");
                categories.add("Gift");
                categories.add("Wealth");
                categories.add("Toll");
                categories.add("Swachh Bharat");
                categories.add("Krishi Kalyan");
                categories.add("Dividend");
                categories.add("Infrastructure");
                categories.add("Entry");

                final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, categories);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tax_type.setAdapter(dataAdapter);

                tax_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (checkValidation()) {
                            if (Float.parseFloat(tax_rate.getText().toString()) > 100.0) {
                                tax_rate.setError("Percentage is <100");
                            } else {
                                boolean insert_test = databaseHelper.TABLE_TAXES_onInsert(tax_type.getSelectedItem().toString(), tax_number.getText().toString(), "0", tax_rate.getText().toString());
                                if (insert_test == true) {
                                    Toast.makeText(Taxes.this, "Tax Added Successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    Intent intent = new Intent(getApplicationContext(), Taxes.class);
                                    intent.putExtra("tax_sent_intent", "do_nothing");
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Taxes.this, "Oops, facing downtime", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                    }
                });

                dialog.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox flag = (CheckBox) listView.getChildAt(listView.indexOfChild(view)).findViewById(R.id.taxFlag);
                TextView taxID = (TextView) listView.getChildAt(listView.indexOfChild(view)).findViewById(R.id.tax_id);
                if (flag.isChecked()) {
                    flag.setChecked(false);
                    databaseHelper.TABLE_INV_TAX_LIST_deleteData(invoice_id, taxID.getText().toString());
                } else {
                    flag.setChecked(true);
                    databaseHelper.TABLE_INV_TAX_LIST_onInsert(invoice_id, taxID.getText().toString());
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    public void init() {
        fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab_add_tax);
        ok = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab_ok);
        listView = (ListView) findViewById(R.id.listView_taxes);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(tax_number)) ret = false;
        if (!Validation.hasText(tax_rate)) ret = false;
        if (tax_type.getSelectedItem().toString().contentEquals("---Tax Type---")) {
            et_error.setError("Mandatory Field");
        }

        return ret;
    }

    @Override
    protected void onResume() {
        super.onResume();


//        tax_number_list.clear();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView client_id = (TextView) listView.getChildAt(listView.indexOfChild(view)).findViewById(R.id.search_client_name);
//                String temp = client_id.getText().toString();
//                Cursor res = databaseHelper.TABLE_TAXES_view();
//                while (res.moveToNext()) {
//                    if (res.getString(2).contentEquals(temp)) {
//                        tax_type = res.getString(1);
//                    }
//                }
//                Toast.makeText(getApplicationContext(), tax_type, Toast.LENGTH_SHORT).show();
//                Snackbar snackbar = Snackbar
//                        .make(coordinatorLayout, "Message is deleted", Snackbar.LENGTH_LONG)
//                        .setAction("UNDO", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
//                                snackbar1.show();
//                            }
//                        });
//
//                snackbar.show();
//            }
//        });

    }
}

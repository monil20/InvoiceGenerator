package com.example.monilandharia.invoice;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateProduct extends AppCompatActivity {

    TextView prod_id_textview, prod_title_textview, prod_qty_textview, prod_rate_textview, prod_note_textview, dialog_id;
    EditText dialog_title, dialog_desc, dialog_qty, dialog_rate, dialog_note;
    WebView prod_desc_textview;
    DatabaseHelper databaseHelper;
    String prod_id, text, title, desc, qty, rate, note;
    Button dialog_update;
    android.support.design.widget.FloatingActionButton update, delete;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        prod_id = intent.getExtras().getString("prod_id_intentExtra");
        final Cursor res = databaseHelper.TABLE_PRODUCTS_view();


        while (res.moveToNext()) {
            if (res.getString(0).contentEquals(prod_id)) {
                prod_id_textview.setText(prod_id);
                prod_title_textview.setText(res.getString(1));
                text = "<html><body><p align=\"justify\">";
                text += res.getString(2);
                text += "</p></body></html>";
                prod_desc_textview.loadData(text, "text/html", "utf-8");
                prod_qty_textview.setText(res.getString(3));
                prod_desc_textview.setBackgroundColor(Color.parseColor("#fafafa"));
                prod_rate_textview.setText("₹. " + res.getString(4) + "/-");
                prod_note_textview.setText(res.getString(5));

                title = res.getString(1);
                desc = res.getString(2);
                qty = res.getString(3);
                rate = res.getString(4);
                note = res.getString(5);

            }
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.update_product_dialog);
                dialog.setCanceledOnTouchOutside(true);


                dialog_id = (TextView) dialog.findViewById(R.id.dialog_prod_id);
                dialog_id.setText(prod_id);
                dialog_title = (EditText) dialog.findViewById(R.id.dialog_client_number);
                dialog_title.setText(title);
                dialog_desc = (EditText) dialog.findViewById(R.id.dialog_client_name);
                dialog_desc.setText(desc);
                dialog_qty = (EditText) dialog.findViewById(R.id.dialog_client_email);
                dialog_qty.setText(qty);
                dialog_rate = (EditText) dialog.findViewById(R.id.dialog_client_addr1);
                dialog_rate.setText(rate);
                dialog_note = (EditText) dialog.findViewById(R.id.dialog_client_addr2);
                dialog_note.setText(note);
                dialog_update = (Button) dialog.findViewById(R.id.dialog_prod_update_button);

                dialog_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean result = databaseHelper.TABLE_PRODUCTS_updateData(prod_id, dialog_title.getText().toString(), dialog_desc.getText().toString(), dialog_qty.getText().toString(), dialog_rate.getText().toString(), dialog_note.getText().toString());
                        if (result == true) {
                            Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                            Cursor res = databaseHelper.TABLE_PRODUCTS_view();
                            while (res.moveToNext()) {
                                if (res.getString(0).contentEquals(prod_id)) {
                                    prod_id_textview.setText(res.getString(0));
                                    prod_title_textview.setText(res.getString(1));
                                    text = "<html><body><p align=\"justify\">";
                                    text += res.getString(2);
                                    text += "</p></body></html>";
                                    prod_desc_textview.loadData(text, "text/html", "utf-8");
                                    prod_qty_textview.setText("₹. " + res.getString(3) + "/-");
                                    prod_rate_textview.setText(res.getString(4));
                                    prod_note_textview.setText(res.getString(5));
                                }
                            }
                            dialog.dismiss();
                        } else {
                            dialog_title.setText("");
                            dialog_desc.setText("");
                            dialog_qty.setText("");
                            dialog_rate.setText("");
                            dialog_note.setText("");
                            Toast.makeText(getApplicationContext(), "Product Updation Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res = databaseHelper.TABLE_PRODUCTS_deleteData(prod_id);
                if (res > 0) {
//                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
//                    startActivity(i);
                    onBackPressed();
                    Toast.makeText(getApplicationContext(), "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Data Deletion Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void init() {
        prod_id_textview = (TextView) findViewById(R.id.update_prod_id);
        prod_title_textview = (TextView) findViewById(R.id.update_prod_title);
        prod_desc_textview = (WebView) findViewById(R.id.update_prod_desc);
        prod_qty_textview = (TextView) findViewById(R.id.update_prod_qty);
        prod_rate_textview = (TextView) findViewById(R.id.update_prod_rate);
        prod_note_textview = (TextView) findViewById(R.id.update_prod_note);
        update = (android.support.design.widget.FloatingActionButton) findViewById(R.id.prod_update_update);
        delete = (android.support.design.widget.FloatingActionButton) findViewById(R.id.prod_update_delete);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}

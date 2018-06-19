package com.example.monilandharia.invoice;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.monilandharia.invoice.R.id.add_client_title;
import static com.example.monilandharia.invoice.R.id.start;
import static com.example.monilandharia.invoice.R.id.update_client_addr2;
import static com.example.monilandharia.invoice.R.id.update_client_email;

public class UpdateClient extends AppCompatActivity {

    TextView tv_client_id, tv_client_number, tv_client_name, tv_client_email, tv_client_website, tv_client_addr1, tv_client_addr2, tv_client_addr3, tv_client_city, tv_client_state;
    DatabaseHelper databaseHelper;
    TextView dialog_id;
    EditText dialog_number, dialog_name, dialog_email, dialog_addr1, dialog_addr2, dialog_addr3, dialog_website, dialog_city, dialog_state;
    String client_id, number, name, email, website, addr1, addr2, addr3, city, state;
    android.support.design.widget.FloatingActionButton update, delete;
    Button dialog_update;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_client);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        client_id = intent.getExtras().getString("client_id_intentExtra");
        final Cursor res = databaseHelper.TABLE_CLIENTS_view();

        while (res.moveToNext()) {
            if (res.getString(0).contentEquals(client_id)) {
                tv_client_id.setText(client_id);
                tv_client_name.setText(res.getString(1));
                tv_client_number.setText(res.getString(2));
                tv_client_email.setText(res.getString(3));
                tv_client_addr1.setText(res.getString(4) + ",");
                tv_client_addr2.setText(res.getString(5) + ",");
                tv_client_addr3.setText(res.getString(6) + ",");
                tv_client_city.setText(res.getString(7) + ", ");
                tv_client_state.setText(res.getString(8));
                tv_client_website.setText(res.getString(9));

                name = res.getString(1);
                number = res.getString(2);
                email = res.getString(3);
                addr1 = res.getString(4);
                addr2 = res.getString(5);
                addr3 = res.getString(6);
                city = res.getString(7);
                state = res.getString(8);
                website = res.getString(9);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.update_client_dialog);
                        dialog.setCanceledOnTouchOutside(true);

                        dialog_id = (TextView) dialog.findViewById(R.id.dialog_client_id);
                        dialog_id.setText(client_id);
                        dialog_number = (EditText) dialog.findViewById(R.id.dialog_client_number);
                        dialog_number.setText(number);
                        dialog_name = (EditText) dialog.findViewById(R.id.dialog_client_name);
                        dialog_name.setText(name);
                        dialog_email = (EditText) dialog.findViewById(R.id.dialog_client_email);
                        dialog_email.setText(email);
                        dialog_addr1 = (EditText) dialog.findViewById(R.id.dialog_client_addr1);
                        dialog_addr1.setText(addr1);
                        dialog_addr2 = (EditText) dialog.findViewById(R.id.dialog_client_addr2);
                        dialog_addr2.setText(addr2);
                        dialog_addr3 = (EditText) dialog.findViewById(R.id.dialog_client_addr3);
                        dialog_addr3.setText(addr3);
                        dialog_city = (EditText) dialog.findViewById(R.id.dialog_client_city);
                        dialog_city.setText(city);
                        dialog_state = (EditText) dialog.findViewById(R.id.dialog_client_state);
                        dialog_state.setText(state);
                        dialog_website = (EditText) dialog.findViewById(R.id.dialog_client_website);
                        dialog_website.setText(website);
                        dialog_update = (Button) dialog.findViewById(R.id.dialog_client_update_button);

                        dialog_update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean result = databaseHelper.TABLE_CLIENTS_updateData(client_id, dialog_number.getText().toString(), dialog_name.getText().toString(), dialog_email.getText().toString(), dialog_website.getText().toString(), dialog_addr1.getText().toString(), dialog_addr2.getText().toString(), dialog_addr3.getText().toString(), dialog_city.getText().toString(), dialog_state.getText().toString());
                                if (result == true) {
                                    Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                    Cursor res = databaseHelper.TABLE_CLIENTS_view();
                                    while (res.moveToNext()) {
                                        if (res.getString(0).contentEquals(client_id)) {
                                            tv_client_id.setText(client_id);
                                            tv_client_name.setText(res.getString(1));
                                            tv_client_number.setText(res.getString(2));
                                            tv_client_email.setText(res.getString(3));
                                            tv_client_addr1.setText(res.getString(4) + ",");
                                            tv_client_addr2.setText(res.getString(5) + ",");
                                            tv_client_addr3.setText(res.getString(6) + ",");
                                            tv_client_city.setText(res.getString(7) + ", ");
                                            tv_client_state.setText(res.getString(8));
                                            tv_client_website.setText(res.getString(9));
                                        }
                                    }
                                    dialog.dismiss();
                                } else {
                                    dialog_name.setText("");
                                    dialog_number.setText("");
                                    dialog_email.setText("");
                                    dialog_addr1.setText("");
                                    dialog_addr2.setText("");
                                    dialog_addr3.setText("");
                                    dialog_city.setText("");
                                    dialog_state.setText("");
                                    dialog_website.setText("");
                                    Toast.makeText(getApplicationContext(), "Client Updation Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        dialog.show();
                    }
                });

                tv_client_website.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent("android.intent.action.VIEW");
                        Uri data = Uri.parse("http://"+website);
                        i.setData(data);
                        startActivity(i);
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int res = databaseHelper.TABLE_CLIENTS_deleteData(client_id);
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
        }

    }

    public void init() {
        tv_client_id = (TextView) findViewById(R.id.update_client_id);
        tv_client_number = (TextView) findViewById(R.id.update_client_number);
        tv_client_name = (TextView) findViewById(R.id.update_client_name);
        tv_client_email = (TextView) findViewById(update_client_email);
        tv_client_website = (TextView) findViewById(R.id.update_client_website);
        tv_client_addr1 = (TextView) findViewById(R.id.update_client_addr1);
        tv_client_addr2 = (TextView) findViewById(update_client_addr2);
        tv_client_addr3 = (TextView) findViewById(R.id.update_client_addr3);
        tv_client_city = (TextView) findViewById(R.id.update_client_city);
        tv_client_state = (TextView) findViewById(R.id.update_client_state);

        update = (android.support.design.widget.FloatingActionButton) findViewById(R.id.client_update_update);
        delete = (android.support.design.widget.FloatingActionButton) findViewById(R.id.client_update_delete);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}

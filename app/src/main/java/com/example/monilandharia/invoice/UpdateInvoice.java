package com.example.monilandharia.invoice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UpdateInvoice extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    final Context context = this;

    String invoice_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_invoice);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        invoice_id = intent.getExtras().getString("invoice_name");
        final Cursor res = databaseHelper.TABLE_INVOICE_view();



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}

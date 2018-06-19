package com.example.monilandharia.invoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class InvoiceFragment extends ListFragment {

    Context context;
    DatabaseHelper databaseHelper;
    //Invoice List
    List<String> invoice_id = new ArrayList<>();
    List<String> invoice_name = new ArrayList<>();
    List<String> invoice_client_name = new ArrayList<>();

    public InvoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        databaseHelper = new DatabaseHelper(getActivity());

        View fam = getActivity().findViewById(R.id.menu3);
        fam.setVisibility(View.VISIBLE);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddInvoice.class);
                TextView invoice = (TextView) getListView().getChildAt(getListView().indexOfChild(view)).findViewById(R.id.prod_id_txtvu);
                String temp = invoice.getText().toString();
                intent.putExtra("invoice_name", temp);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();

        invoice_id.clear();
        invoice_name.clear();

        Cursor res = databaseHelper.TABLE_INVOICE_view();
        while (res.moveToNext()) {
            invoice_id.add(res.getString(0));
            invoice_name.add(res.getString(1));
            invoice_client_name.add(res.getString(2));
        }


        Invoice_ListCustomAdapter invoiceListCustomAdapter = new Invoice_ListCustomAdapter(getActivity(), invoice_id, invoice_name, invoice_client_name);
        setListAdapter(invoiceListCustomAdapter);
    }
}

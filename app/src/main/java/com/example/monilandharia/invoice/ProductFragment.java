package com.example.monilandharia.invoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends ListFragment {

    ListView listView;
    DatabaseHelper databaseHelper;
    //Products Lists
    List<String> product_id = new ArrayList<>();
    List<String> product_title = new ArrayList<>();

    Context context;

    public ProductFragment() {
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
       // listView = (ListView) getActivity().findViewById(R.id.list_products);

        View fam = getActivity().findViewById(R.id.menu3);
        fam.setVisibility(View.VISIBLE);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(),UpdateProduct.class);
                TextView prod_id = (TextView) getListView().getChildAt(getListView().indexOfChild(view)).findViewById(R.id.prod_id_txtvu);
                String temp = prod_id.getText().toString();
                intent.putExtra("prod_id_intentExtra", temp);
                startActivity(intent);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        product_id.clear();
        product_title.clear();

        Cursor res = databaseHelper.TABLE_PRODUCTS_view();
        while (res.moveToNext()) {
            product_id.add(res.getString(0));
            product_title.add(res.getString(1));
        }

        Product_ListCustomAdapter customAdapter = new Product_ListCustomAdapter(getActivity(),product_id,product_title);
        setListAdapter(customAdapter);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

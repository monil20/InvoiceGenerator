package com.example.monilandharia.invoice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ClientFragment extends ListFragment {

    DatabaseHelper databaseHelper;
    //Clients List
    List<String> client_id = new ArrayList<>();
    List<String> client_name = new ArrayList<>();

    Context context;

    public ClientFragment() {
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
                Intent intent = new Intent(getActivity().getApplicationContext(),UpdateClient.class);
                TextView client_id = (TextView) getListView().getChildAt(getListView().indexOfChild(view)).findViewById(R.id.prod_id_txtvu);
                String temp = client_id.getText().toString();
                intent.putExtra("client_id_intentExtra", temp);
                startActivity(intent);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client, container, false);
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

        client_id.clear();
        client_name.clear();

        Cursor res = databaseHelper.TABLE_CLIENTS_view();
        while (res.moveToNext()) {
            client_id.add(res.getString(0));
            client_name.add(res.getString(1));
        }

        Client_ListCustomAdapter customAdapter = new Client_ListCustomAdapter(getActivity(),client_id,client_name);
        setListAdapter(customAdapter);
    }
}

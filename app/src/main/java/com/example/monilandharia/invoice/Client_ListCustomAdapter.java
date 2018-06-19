package com.example.monilandharia.invoice;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Monil Andharia on 12-Apr-16.
 */
public class Client_ListCustomAdapter extends ArrayAdapter<String> {

    List<String> client_id;
    List<String> client_name;

    Context context;
    int counter = 1;

    public Client_ListCustomAdapter(Context context, List<String> client_id, List<String> client_name) {
        super(context, R.layout.single_list_item, R.id.prod_id_txtvu, client_id);
        this.context = context;
        this.client_id = client_id;
        this.client_name = client_name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.single_list_item, parent, false);

        TextView prod_id_txtvu = (TextView) row.findViewById(R.id.prod_id_txtvu);
        TextView prod_title_txtvu = (TextView) row.findViewById(R.id.prod_title_txtvu);
        TextView prod_id_txtvu_duplicate = (TextView) row.findViewById(R.id.prod_id_txtvu_duplicate);
        int temp = prod_id_txtvu_duplicate.getText().toString().length();

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/ProductSans-Regular.ttf");

        if (temp < 10) {
            prod_id_txtvu_duplicate.setPadding(11, 1, 0, 0);
        }
        prod_id_txtvu.setText(client_id.get(position));
        prod_id_txtvu.setTypeface(typeface);
        prod_title_txtvu.setText(client_name.get(position));
        prod_title_txtvu.setTypeface(typeface);
        prod_id_txtvu_duplicate.setText(String.valueOf(counter));
        counter++;
        return row;
    }
}

package com.example.monilandharia.invoice;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Monil Andharia on 09-May-16.
 */
public class Invoice_ListCustomAdapter extends ArrayAdapter<String> {
    List<String> invoice_id;
    List<String> invoice_name;
    List<String> invoice_client_name;

    Context context;
    int counter = 1;

    public Invoice_ListCustomAdapter(Context context, List<String> invoice_id, List<String> invoice_name, List<String> invoice_client_name) {
        super(context, R.layout.single_list_item, R.id.prod_id_txtvu, invoice_id);
        this.context = context;
        this.invoice_id = invoice_id;
        this.invoice_name = invoice_name;
        this.invoice_client_name = invoice_client_name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.single_invoice_list_item, parent, false);

        TextView prod_id_txtvu = (TextView) row.findViewById(R.id.prod_id_txtvu);
        TextView prod_title_txtvu = (TextView) row.findViewById(R.id.prod_title_txtvu);
        TextView prod_id_txtvu_duplicate = (TextView) row.findViewById(R.id.prod_id_txtvu_duplicate);
        TextView respective_client = (TextView) row.findViewById(R.id.temp);
        TextView warning_msg = (TextView) row.findViewById(R.id.warning_msg);

        int temp = prod_id_txtvu_duplicate.getText().toString().length();

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/ProductSans-Regular.ttf");

        if (temp < 10) {
            prod_id_txtvu_duplicate.setPadding(11, 1, 0, 0);
        }
        prod_id_txtvu.setText(invoice_id.get(position));
        prod_id_txtvu.setTypeface(typeface);
        prod_title_txtvu.setTypeface(typeface);
        if (invoice_client_name.get(position).contentEquals("temp_value")) {
            respective_client.setText("");
            prod_title_txtvu.setPadding(0, 20, 0, 0);
            prod_title_txtvu.setText(invoice_name.get(position));
            warning_msg.setVisibility(View.VISIBLE);
        } else {
            respective_client.setText("Respective Client: " + invoice_client_name.get(position));
            prod_title_txtvu.setText(invoice_name.get(position));
        }
        respective_client.setTypeface(typeface);
        prod_id_txtvu_duplicate.setText(String.valueOf(counter));
        counter++;

        if(counter>invoice_id.size()){
            counter=1;
        }

        return row;
    }
}

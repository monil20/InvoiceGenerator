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
 * Created by Monil Andharia on 11-May-16.
 */
public class InvTaxNameListAdapter extends ArrayAdapter<String> {

    List<String> tax_name_list;

    Context context;
    int counter = 1;

    public InvTaxNameListAdapter(Context context, List<String> tax_name) {
        super(context, R.layout.inv_tax_name_list_item, R.id.textView12, tax_name);
        this.context = context;
        this.tax_name_list = tax_name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.inv_tax_name_list_item, parent, false);

        TextView tax_name = (TextView) row.findViewById(R.id.textView12);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/ProductSans-Regular.ttf");

        tax_name.setText(tax_name_list.get(position));
        tax_name.setTypeface(typeface);

        return row;
    }

}

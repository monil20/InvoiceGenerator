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
public class Product_ListCustomAdapter extends ArrayAdapter<String> {

    List<String> product_id;
    List<String> product_title;
    int counter=1;

    Context context;

    public Product_ListCustomAdapter(Context context, List<String> product_id, List<String> product_title) {
        super(context, R.layout.single_list_item, R.id.prod_id_txtvu, product_id);
        this.context = context;
        this.product_id = product_id;
        this.product_title = product_title;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.single_list_item, parent, false);

        TextView prod_id_txtvu = (TextView) row.findViewById(R.id.prod_id_txtvu);
        prod_id_txtvu.setVisibility(View.INVISIBLE);
        TextView prod_title_txtvu = (TextView) row.findViewById(R.id.prod_title_txtvu);
        TextView prod_id_txtvu_duplicate = (TextView) row.findViewById(R.id.prod_id_txtvu_duplicate);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/ProductSans-Regular.ttf");

        if (String.valueOf(counter).length() == 1) {
            prod_id_txtvu_duplicate.setPadding(11, 1, 0, 0);
        }
        prod_id_txtvu.setText(product_id.get(position));
        prod_id_txtvu.setTypeface(typeface);
        prod_title_txtvu.setText(product_title.get(position));
        prod_title_txtvu.setTypeface(typeface);
        prod_id_txtvu_duplicate.setText(String.valueOf(counter));
        counter++;
        return row;
    }
}

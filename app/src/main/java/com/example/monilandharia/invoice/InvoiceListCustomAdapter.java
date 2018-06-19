package com.example.monilandharia.invoice;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monil Andharia on 08-May-16.
 */
public class InvoiceListCustomAdapter extends ArrayAdapter<String> {

    List<String> prod_name;
    List<String> prod_sold_qty;
    List<String> prod_rate;
    List<String> prod_total = new ArrayList<>();
    List<Float> prod_total_float = new ArrayList<>();
    String invoice_id;
    float subtotal = 0;
    float final_total = 0;

    List<Float> tax_rate = new ArrayList<>();
    float total = subtotal;

    Context context;

    public InvoiceListCustomAdapter(Context context, List<String> prod_name, List<String> prod_sold_qty, List<String> prod_rate, String invoice_id) {
        super(context, R.layout.single_invoice_prod_list, R.id.invoice_list_prod_name, prod_name);
        this.context = context;
        this.prod_name = prod_name;
        this.prod_sold_qty = prod_sold_qty;
        this.prod_rate = prod_rate;
        this.invoice_id = invoice_id;

        sub_total();
        total();
    }

    public void sub_total() {
        for (int x = 0; x < prod_name.size(); x++) {
            int qty = Integer.parseInt(prod_sold_qty.get(x));
            int rate = Integer.parseInt(prod_rate.get(x));
            Float mul = Float.valueOf(qty * rate);
            prod_total_float.add(mul);
            String mul_string = String.valueOf(mul);
            prod_total.add(mul_string);
        }
        for (int x = 0; x < prod_total.size(); x++) {
            subtotal = subtotal + prod_total_float.get(x);
        }
    }

    public void total() {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        Cursor res = databaseHelper.TABLE_INV_TAX_LIST_view();
        while (res.moveToNext()) {
            if (res.getString(1).contentEquals(invoice_id)) {
                Cursor res1 = databaseHelper.TABLE_TAXES_view();
                while (res1.moveToNext()) {
                    if (res1.getString(0).contentEquals(res.getString(2))) {
                        tax_rate.add(Float.parseFloat(res1.getString(4)));
                    }
                }
            }
        }

        if(tax_rate.size()>0){
            for (int i = 0; i < tax_rate.size(); i++) {
                total = (subtotal*tax_rate.get(i))/100;
                final_total=final_total+total;
            }
        }

    }

    public float getSubtotal() {
        return subtotal;
    }

    public float getTotal() {
        return final_total;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.single_invoice_prod_list, parent, false);

        CustomTextView prod_name_txtvu = (CustomTextView) row.findViewById(R.id.invoice_list_prod_name);
        CustomTextView prod_rate_txtvu = (CustomTextView) row.findViewById(R.id.invoice_list_prod_rate);
        CustomTextView prod_sold_qty_txtvu = (CustomTextView) row.findViewById(R.id.invoice_list_sold_qty);
        CustomTextView prod_total_txtvu = (CustomTextView) row.findViewById(R.id.invoice_list_prod_total);

        prod_name_txtvu.setText(prod_name.get(position));
        prod_rate_txtvu.setText(prod_rate.get(position));
        prod_sold_qty_txtvu.setText(prod_sold_qty.get(position));
        prod_total_txtvu.setText(prod_total.get(position));

        return row;
    }
}

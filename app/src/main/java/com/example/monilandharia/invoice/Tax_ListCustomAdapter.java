package com.example.monilandharia.invoice;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.design.widget.*;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monil Andharia on 11-May-16.
 */
public class Tax_ListCustomAdapter extends ArrayAdapter<String> {

    List<String> tax_type;
    List<String> tax_number;
    List<String> tax_flag;
    List<String> tax_rate;
    List<String> tax_id;
    String checkBox;
    String invoice_id;
    Cursor res, res1;

    List<String> checked_tax_name = new ArrayList<>();
    List<String> checked_tax_rate = new ArrayList<>();

    Context context;

    public Tax_ListCustomAdapter(Context context, List<String> tax_type, List<String> tax_number, List<String> tax_flag, List<String> tax_rate, List<String> tax_id, String checkBox, String invoice_id) {
        super(context, R.layout.search_list_item_tax, R.id.taxNumber, tax_number);
        this.context = context;
        this.tax_type = tax_type;
        this.tax_number = tax_number;
        this.tax_flag = tax_flag;
        this.tax_rate = tax_rate;
        this.tax_id = tax_id;
        this.checkBox = checkBox;
        this.invoice_id = invoice_id;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        res = databaseHelper.TABLE_INV_TAX_LIST_view();
        res1 = databaseHelper.TABLE_TAXES_view();

//        while (res.moveToNext()) {
//            if (res.getString(1).contentEquals(invoice_id)) {
//                while (res1.moveToNext()) {
//                    if (res.getString(2).contentEquals(res1.getString(0))) {
//                        checked_tax_name.add(res1.getString(2));
//                        Log.d("KEY1", res1.getString(2));
//                        checked_tax_rate.add(res1.getString(4));
//                        Log.d("KEY2", res1.getString(4));
//                    }
//                }
//            }
//        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.search_list_item_tax, parent, false);

        CustomTextView taxNumber = (CustomTextView) row.findViewById(R.id.taxNumber);
        CustomTextView taxRate = (CustomTextView) row.findViewById(R.id.taxRate);
        CustomTextView taxRate_when_do_nothing = (CustomTextView) row.findViewById(R.id.RATE_when_do_nothing);
        CustomTextView taxType_when_do_nothing = (CustomTextView) row.findViewById(R.id.TYPE_when_do_nothing);
        CustomTextView taxType = (CustomTextView) row.findViewById(R.id.taxType);
        CheckBox taxFlag = (CheckBox) row.findViewById(R.id.taxFlag);

        TextView taxID = (TextView) row.findViewById(R.id.tax_id);

        if (checkBox.contentEquals("do_nothing")) {
            taxFlag.setVisibility(View.INVISIBLE);
            taxRate_when_do_nothing.setText(tax_rate.get(position) + "%");
            taxRate.setVisibility(View.INVISIBLE);
            taxType_when_do_nothing.setText(tax_type.get(position));
            taxType.setVisibility(View.INVISIBLE);
        } else {
            taxRate.setText(tax_rate.get(position) + "%");
            taxRate_when_do_nothing.setVisibility(View.INVISIBLE);
            taxType.setText(tax_type.get(position));
            taxType_when_do_nothing.setVisibility(View.INVISIBLE);
        }

        taxID.setText(tax_id.get(position));
        taxID.setVisibility(View.INVISIBLE);
        taxNumber.setText(tax_number.get(position));


//        while (res.moveToNext()){
//            if(res.getString(1).contentEquals(invoice_id)){
//                Log.d("KEYWORD 1",res.getString(1));
//                Log.d("KEYWORD 2",invoice_id);
//                while (res1.moveToNext()){
//                    if(res1.getString(0).contentEquals(res.getString(2))){
//                        Log.d("KEYWORD 3",res1.getString(0));
//                        Log.d("KEYWORD 4",res.getString(2));
//                        if(res1.getString(2).contentEquals(tax_number.get(position)) && res1.getString(4).contentEquals(tax_rate.get(position))){
//                            Log.d("KEYWORD 5",res1.getString(2));
//                            Log.d("KEYWORD 6",tax_number.get(position));
//                            Log.d("KEYWORD 7",res1.getString(4));
//                            Log.d("KEYWORD 8",tax_rate.get(position));
//                            taxFlag.setChecked(true);
//                        }
//                    }
//                }
//            }else{
//                taxFlag.setChecked(true);
//            }
//        }


        return row;
    }

}

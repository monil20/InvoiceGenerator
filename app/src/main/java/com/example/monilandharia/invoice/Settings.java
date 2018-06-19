package com.example.monilandharia.invoice;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Settings extends Fragment {

    TextView comp_details, taxes, inv_prefix, currency, vendor_no;
    TextView likeUs, pro, about, feedback, share, rate, basic_settings_tag;
    Intent intent;
    Context context;
    ListView listView;
    String final_currency;

    public Settings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View fam = getActivity().findViewById(R.id.menu3);
        fam.setVisibility(View.INVISIBLE);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"fonts/ProductSans-Regular.ttf");

        comp_details = (TextView) getActivity().findViewById(R.id.company_details);
        comp_details.setTypeface(typeface);
        taxes = (TextView) getActivity().findViewById(R.id.taxes);
        taxes.setTypeface(typeface);
        inv_prefix = (TextView) getActivity().findViewById(R.id.invoice_prefix);
        inv_prefix.setTypeface(typeface);
        currency = (TextView) getActivity().findViewById(R.id.currency);
        currency.setTypeface(typeface);
        likeUs = (TextView) getActivity().findViewById(R.id.textView2);
        likeUs.setTypeface(typeface);
        pro = (TextView) getActivity().findViewById(R.id.textView7);
        pro.setTypeface(typeface);
        about = (TextView) getActivity().findViewById(R.id.textView3);
        about.setTypeface(typeface);
        feedback = (TextView) getActivity().findViewById(R.id.textView4);
        feedback.setTypeface(typeface);
        share = (TextView) getActivity().findViewById(R.id.textView5);
        share.setTypeface(typeface);
        rate = (TextView) getActivity().findViewById(R.id.textView6);
        rate.setTypeface(typeface);
        basic_settings_tag = (TextView) getActivity().findViewById(R.id.textView8);
        basic_settings_tag.setTypeface(typeface);

        vendor_no = (TextView) getActivity().findViewById(R.id.vendor_num);

        comp_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity().getApplicationContext(), CompanyDetails.class);
                startActivity(intent);
            }
        });

        currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context = getActivity();
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_currency);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setTitle(null);

                ArrayList<String> arrayList;
                ArrayAdapter<String> arrayAdapter;

                listView = (ListView) dialog.findViewById(R.id.listView_currency);

                arrayList = new ArrayList<String>();
                arrayList.add("None");
                arrayList.add("Lek");
                arrayList.add("؋");
                arrayList.add("$");
                arrayList.add("ƒ");
                arrayList.add("\u20BC");
                arrayList.add("p.");
                arrayList.add("BZ$");
                arrayList.add("$b");
                arrayList.add("KM");
                arrayList.add("P");
                arrayList.add("лв");
                arrayList.add("R$");
                arrayList.add("៛");
                arrayList.add("¥");
                arrayList.add("₡");
                arrayList.add("₱");
                arrayList.add("kn");
                arrayList.add("Kč");
                arrayList.add("kr");
                arrayList.add("RD$");
                arrayList.add("£");
                arrayList.add("€");
                arrayList.add("\u20BE");
                arrayList.add("¢");
                arrayList.add("Q");
                arrayList.add("L");
                arrayList.add("Ft");
                arrayList.add("₹");
                arrayList.add("Rp");
                arrayList.add("﷼");
                arrayList.add("₪");
                arrayList.add("J$");
                arrayList.add("₩");
                arrayList.add("Ls");
                arrayList.add("₭");
                arrayList.add("Lt");
                arrayList.add("ден");
                arrayList.add("RM");
                arrayList.add("₨");
                arrayList.add("₮");
                arrayList.add("MT");
                arrayList.add("B/.");
                arrayList.add("C$");
                arrayList.add("₦");
                arrayList.add("Gs");
                arrayList.add("S/.");
                arrayList.add("₱");
                arrayList.add("zł");
                arrayList.add("lei");
                arrayList.add("\u20BD");
                arrayList.add("S");
                arrayList.add("CHF");
                arrayList.add("NT$");
                arrayList.add("฿");
                arrayList.add("TT$");
                arrayList.add("₺");
                arrayList.add("₴");
                arrayList.add("$U");
                arrayList.add("Bs");
                arrayList.add("₫");
                arrayList.add("Z$");

                arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_single_choice, arrayList);
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CheckedTextView textView = (CheckedTextView) view;
                        final_currency = textView.getText().toString();
                        if (final_currency == "None") {
                            final_currency = "";
                        }
                        Log.d("final currency", final_currency);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        taxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),Taxes.class);
                intent.putExtra("tax_sent_intent","do_nothing");
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

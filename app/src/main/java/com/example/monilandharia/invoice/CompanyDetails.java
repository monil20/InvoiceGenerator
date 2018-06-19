package com.example.monilandharia.invoice;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class CompanyDetails extends AppCompatActivity {

    EditText name, country_code, number, email, website, tc_et, dec_et;
    Button addr, tc, dec, sign, save;
    Dialog dialog;
    ImageView comp_logo;

    final DatabaseHelper db = new DatabaseHelper(this);

    byte[] imageInByte;
    byte[] signInByte;

    int flag = 0;


    //    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;
    public static final int SIGNATURE_ACTIVITY = 3;

    Context context;

    DatabaseHelper databaseHelper;

    String address1, address2, address3, city, state, pincode, terms_and_conditions, declaration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        databaseHelper = new DatabaseHelper(this);

        context = this;

        addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.comp_address);
                dialog.setCanceledOnTouchOutside(true);

                Button addr_save = (Button) dialog.findViewById(R.id.addr_save);
                addr_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ProductSans-Regular.ttf");

                        EditText addr1 = (EditText) dialog.findViewById(R.id.comp_addr1);
                        EditText addr2 = (EditText) dialog.findViewById(R.id.comp_addr2);
                        EditText addr3 = (EditText) dialog.findViewById(R.id.comp_addr3);
                        EditText city_et = (EditText) dialog.findViewById(R.id.comp_addr_city);
                        EditText state_et = (EditText) dialog.findViewById(R.id.comp_addr_state);
                        EditText pincode_et = (EditText) dialog.findViewById(R.id.comp_addr_pincode);

                        addr1.setTypeface(typeface);
                        addr2.setTypeface(typeface);
                        addr3.setTypeface(typeface);
                        city_et.setTypeface(typeface);
                        state_et.setTypeface(typeface);
                        pincode_et.setTypeface(typeface);

                        address1 = addr1.getText().toString();
                        address2 = addr2.getText().toString();
                        address3 = addr3.getText().toString();
                        city = city_et.getText().toString();
                        state = state_et.getText().toString();
                        pincode = pincode_et.getText().toString();

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.comp_tc);
                dialog.setCanceledOnTouchOutside(true);

                Button tc_save = (Button) dialog.findViewById(R.id.tc_save);
                Button tc_reset = (Button) dialog.findViewById(R.id.tc_reset);
                tc_et = (EditText) dialog.findViewById(R.id.text);

                tc_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ProductSans-Regular.ttf");

                        tc_et.setTypeface(typeface);

                        if (tc_et.length() > 300) {
                            Toast.makeText(getApplicationContext(), "Cannot enter more than 300 characters.", Toast.LENGTH_SHORT).show();
                        } else {
                            terms_and_conditions = tc_et.getText().toString();

                            dialog.dismiss();
                        }
                        Log.d("Hello", tc_et.getText().toString());
                    }
                });

                tc_reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tc_et.setText("");
                    }
                });

                dialog.show();
            }
        });

        comp_logo = (ImageView) findViewById(R.id.comp_logo);
        comp_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.camera_gallery);
                dialog.setCanceledOnTouchOutside(true);

                ImageView camera = (ImageView) dialog.findViewById(R.id.camera);
                ImageView gallery = (ImageView) dialog.findViewById(R.id.gallery);

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent cameraIntent = new Intent(
                                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
                        cameraIntent.putExtra("crop", "true");
                        cameraIntent.putExtra("aspectX", 0);
                        cameraIntent.putExtra("aspectY", 0);
                        cameraIntent.putExtra("outputX", 200);
                        cameraIntent.putExtra("outputY", 150);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        dialog.dismiss();
                    }
                });

                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        // call android default gallery
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.putExtra("crop", "true");
                        intent.putExtra("aspectX", 0);
                        intent.putExtra("aspectY", 0);
                        intent.putExtra("outputX", 200);
                        intent.putExtra("outputY", 150);

                        try {

                            intent.putExtra("return-data", true);
                            startActivityForResult(Intent.createChooser(intent,
                                    "Complete action using"), PICK_FROM_GALLERY);

                        } catch (Exception e) {
                            // Do nothing for now
                        }

                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.comp_dec);
                dialog.setCanceledOnTouchOutside(true);

                Button dec_save = (Button) dialog.findViewById(R.id.dec_save);
                Button dec_reset = (Button) dialog.findViewById(R.id.dec_reset);
                dec_et = (EditText) dialog.findViewById(R.id.comp_dec);

                dec_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ProductSans-Regular.ttf");

                        dec_et.setTypeface(typeface);

                        if (dec_et.length() > 150) {
                            Toast.makeText(getApplicationContext(), "Cannot enter more than 150 characters.", Toast.LENGTH_SHORT).show();
                        } else {
                            declaration = dec_et.getText().toString();

                            dialog.dismiss();
                        }
                        Log.d("Hello", dec_et.getText().toString());
                    }
                });

                dec_reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dec_et.setText("");
                    }
                });

                dialog.show();
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog = new Dialog(context);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.signature);
//                dialog.setCanceledOnTouchOutside(true);

                Intent intent = new Intent(CompanyDetails.this, CaptureSignature.class);
                startActivityForResult(intent, SIGNATURE_ACTIVITY);
//                dialog.show();
                flag=1;
            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    signInByte = CaptureSignature.getMySign();
                }
                String temp = country_code.getText().toString()+number.getText().toString();
                boolean insert_test = databaseHelper.TABLE_COMPANY_DETAILS_onInsert(imageInByte,name.getText().toString(),temp,email.getText().toString(),website.getText().toString(),address1,address2,address3,city,state,pincode,terms_and_conditions,declaration,signInByte);
                if (insert_test == true) {
                    Toast.makeText(CompanyDetails.this, "Company Details Saved", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(CompanyDetails.this, "Oops, facing downtime", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void init() {
        name = (EditText) findViewById(R.id.comp_name);
        country_code = (EditText) findViewById(R.id.editText);
        number = (EditText) findViewById(R.id.editText2);
        email = (EditText) findViewById(R.id.comp_email);
        website = (EditText) findViewById(R.id.comp_website);

        addr = (Button) findViewById(R.id.button);
        tc = (Button) findViewById(R.id.button2);
        dec = (Button) findViewById(R.id.button3);
        sign = (Button) findViewById(R.id.button4);
        save = (Button) findViewById(R.id.company_details_save);

        Typeface t = Typeface.createFromAsset(getAssets(), "fonts/ProductSans-Regular.ttf");
        // Applying Typefaces
        name.setTypeface(t);
        country_code.setTypeface(t);
        number.setTypeface(t);
        email.setTypeface(t);
        website.setTypeface(t);
        addr.setTypeface(t);
        tc.setTypeface(t);
        dec.setTypeface(t);
        sign.setTypeface(t);
        save.setTypeface(t);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                comp_logo.setImageBitmap(photo);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageInByte = stream.toByteArray();
            }
        }

        if (requestCode == PICK_FROM_GALLERY) {
            if (data != null) {
                Bundle extras2 = data.getExtras();
                if (extras2 != null) {
                    Bitmap photo = extras2.getParcelable("data");
                    comp_logo.setImageBitmap(photo);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    imageInByte = stream.toByteArray();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}

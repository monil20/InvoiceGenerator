package com.example.monilandharia.invoice;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AddInvoice extends AppCompatActivity {

    ArrayList<String> tax_name_list = new ArrayList<>();
    ArrayList<String> tax_rate_list = new ArrayList<>();
    InvTaxNameListAdapter adapter;
    InvTaxRateListAdapter adapter1;
    DatabaseHelper databaseHelper;
    TextView search_client, search_product;
    RelativeLayout part2;

    ListView lv1, lv2;

    TextView temp_tax_name, temp_tax_rate, total_textView;

    CustomTextView subtotal_res, total;

    List<String> prod_name = new ArrayList<>();
    List<String> prod_sold_qty = new ArrayList<>();
    List<String> prod_rate = new ArrayList<>();

    ListView listView;

    CustomTextView tax;

    String client_name;
    String invoice_id, invoice_name;
    String client_id;

    float subtotal = 0;
    float total_value = 0;

    private static final String LOG_TAG = "GeneratePDF";

    private File pdfFile;
    private String filename = "Sample.pdf";
    private String filepath = "MyInvoices";

    private BaseFont bfBold;

    byte[] comp_logo, comp_sign;
    String comp_name, addr1, addr2, addr3, city, state, pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invoice);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();


        final Intent intent = getIntent();
        invoice_id = intent.getExtras().getString("invoice_name");

        databaseHelper = new DatabaseHelper(this);

        Cursor res1 = databaseHelper.TABLE_INVOICE_view();
        while (res1.moveToNext()) {
            if (res1.getString(0).contentEquals(invoice_id)) {
                invoice_name = res1.getString(1);
            }
        }
        filename = invoice_name + ".pdf";

        Cursor res = databaseHelper.TABLE_COMPANY_DETAILS_view();
        while (res.moveToNext()) {
            comp_logo = res.getBlob(0);
            comp_name = res.getString(1);
            addr1 = res.getString(5);
            addr2 = res.getString(6);
            addr3 = res.getString(7);
            city = res.getString(8);
            state = res.getString(9);
            pincode = res.getString(10);
            comp_sign = res.getBlob(13);

        }

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.v(LOG_TAG, "External Storage not available or you don't have permission to write");
        } else {
            //path for the PDF file in the external storage
            pdfFile = new File(getExternalFilesDir(filepath), filename);
        }

        search_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), SearchClient.class);
                intent1.putExtra("invoice_id_from_AddInvoice", invoice_id);
                startActivity(intent1);
            }
        });

        view_invoice();

        search_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = databaseHelper.TABLE_INVOICE_view();
                while (res.moveToNext()) {
                    if (res.getString(0).contentEquals(invoice_id)) {
                        client_id = res.getString(3);
                    }
                }
                Intent intent = new Intent(getApplicationContext(), SearchProduct.class);
                intent.putExtra("invoice_id_from_AddInvoice", invoice_id);
                intent.putExtra("client_id_from_AddInvoice", client_id);
                startActivity(intent);
            }
        });

        tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), Taxes.class);
                intent1.putExtra("tax_sent_intent", "show_checkboxes");
                intent1.putExtra("invoice_id", invoice_id);
                startActivity(intent1);
            }
        });

    }

    public static void setListViewHeightBasedOnChildren(final ListView listView) {
        listView.post(new Runnable() {
            @Override
            public void run() {
                ListAdapter listAdapter = listView.getAdapter();
                if (listAdapter == null) {
                    return;
                }
                int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
                int listWidth = listView.getMeasuredWidth();
                for (int i = 0; i < listAdapter.getCount(); i++) {
                    View listItem = listAdapter.getView(i, null, listView);
                    listItem.measure(
                            View.MeasureSpec.makeMeasureSpec(listWidth, View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));


                    totalHeight += listItem.getMeasuredHeight();
                    Log.d("listItemHeight" + listItem.getMeasuredHeight(), "___________");
                }
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = (int) ((totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1))));
                listView.setLayoutParams(params);
                listView.requestLayout();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void view_invoice() {
        Cursor res = databaseHelper.TABLE_INVOICE_view();
        while (res.moveToNext()) {
            if (res.getString(0).contentEquals(invoice_id)) {
                client_id = res.getString(3);
            }
        }
    }

    public void init() {
        search_client = (TextView) findViewById(R.id.enter_client);
        search_product = (TextView) findViewById(R.id.enter_product);
        listView = (ListView) findViewById(R.id.invoice_calculation);
        part2 = (RelativeLayout) findViewById(R.id.part2);
        subtotal_res = (CustomTextView) findViewById(R.id.subtotal_res);
        lv1 = (ListView) findViewById(R.id.tax_name);
        lv2 = (ListView) findViewById(R.id.tax_res);
        temp_tax_name = (TextView) findViewById(R.id.textView14);
        temp_tax_rate = (TextView) findViewById(R.id.textView15);
        tax = (CustomTextView) findViewById(R.id.tax);
        total = (CustomTextView) findViewById(R.id.total);
        total_textView = (CustomTextView) findViewById(R.id.textView16);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pdf: {
                this.generatePDF("CEO, " + comp_name);
                break;

            }
            case android.R.id.home: {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        prod_name.clear();
        prod_rate.clear();
        prod_sold_qty.clear();
        tax_name_list.clear();
        tax_rate_list.clear();

        final Cursor res = databaseHelper.TABLE_INVOICE_view();
        while (res.moveToNext()) {
            if (res.getString(0).contentEquals(invoice_id)) {
                if (res.getString(2).contentEquals("temp_value")) {
                    search_client.setText("Enter/ Search Client");
                    client_name = res.getString(2);
                } else {
                    search_client.setText(res.getString(2));
                    search_client.setTextColor(Color.parseColor("#009688"));
                    search_client.setClickable(false);
                    client_name = res.getString(2);
                }

            }
        }

        if (client_name.contentEquals("temp_value")) {
            search_product.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.INVISIBLE);
            part2.setVisibility(View.INVISIBLE);
            total.setVisibility(View.INVISIBLE);
            total_textView.setVisibility(View.INVISIBLE);
        } else {
            search_product.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            part2.setVisibility(View.VISIBLE);
            total.setVisibility(View.VISIBLE);
            total_textView.setVisibility(View.VISIBLE);
        }

        try {
            Cursor res1 = databaseHelper.TABLE_INV_PRODUCT_LIST_view();
            while (res1.moveToNext()) {
                if (res1.getString(4).contentEquals(client_id) && res1.getString(5).contentEquals(invoice_id)) {
                    prod_name.add(res1.getString(1));
//                    Toast.makeText(getApplicationContext(),res1.getString(1),Toast.LENGTH_SHORT).show();
                    prod_sold_qty.add(res1.getString(2));
                    prod_rate.add(res1.getString(3));
                } else {
                }
            }

        } catch (Exception e) {

        }

        Cursor res1 = databaseHelper.TABLE_INV_TAX_LIST_view();
        while (res1.moveToNext()) {
            if (res1.getString(1).contentEquals(invoice_id)) {

                Cursor res2 = databaseHelper.TABLE_TAXES_view();
                while (res2.moveToNext()) {
                    if (res2.getString(0).contentEquals(res1.getString(2))) {
                        tax_name_list.add(res2.getString(2));
                        tax_rate_list.add(res2.getString(4));
                    }
                }

            }
        }

        adapter = new InvTaxNameListAdapter(this, tax_name_list);
        adapter1 = new InvTaxRateListAdapter(this, tax_rate_list);
        lv1.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lv1);
        lv2.setAdapter(adapter1);
        setListViewHeightBasedOnChildren(lv2);

        if (prod_name.size() == 0) {
            part2.setVisibility(View.INVISIBLE);
            total.setVisibility(View.INVISIBLE);
            total_textView.setVisibility(View.INVISIBLE);
        }

        if (tax_name_list.size() != 0) {
            temp_tax_name.setVisibility(View.INVISIBLE);
            temp_tax_rate.setVisibility(View.INVISIBLE);
            total.setVisibility(View.VISIBLE);
            total_textView.setVisibility(View.VISIBLE);
        }


        InvoiceListCustomAdapter invoiceListCustomAdapter = new InvoiceListCustomAdapter(this, prod_name, prod_sold_qty, prod_rate, invoice_id);
        listView.setAdapter(invoiceListCustomAdapter);
        setListViewHeightBasedOnChildren(listView);

        subtotal = invoiceListCustomAdapter.getSubtotal();
        subtotal_res.setText(String.valueOf(subtotal));

        total_value = invoiceListCustomAdapter.getTotal() + subtotal;
        total.setText(String.valueOf(total_value));

    }

    private void generatePDF(String personName) {

        //create a new document
        Document document = new Document();

        try {

            PdfWriter docWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();


            PdfContentByte cb = docWriter.getDirectContent();
            //initialize fonts for text printing
            initializeFonts();

            //the company logo is stored in the assets which is read only
            //get the logo and print on the document

            Image companyLogo = Image.getInstance(comp_logo);
            companyLogo.setAbsolutePosition(25, 700);
            companyLogo.scalePercent(75);
            document.add(companyLogo);

            //creating a sample invoice with some customer data
            createHeadings(cb, 400, 780, comp_name);
            createHeadings(cb, 400, 765, addr1);
            createHeadings(cb, 400, 750, addr2);
            createHeadings(cb, 400, 735, addr3);
            createHeadings(cb, 400, 720, city + ", " + state + " - " + pincode);

            float[] columnWidths = {1.5f, 5f, 3.5f, 3.5f, 4f};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setTotalWidth(500f);

            PdfPCell cell = new PdfPCell(new Phrase("Sr. No."));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Product Name"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Quantity Sold"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Product Rate"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Subtotal"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            DecimalFormat df = new DecimalFormat("0.00");
            for (int i = 0; i < prod_sold_qty.size(); i++) {
                cell = new PdfPCell(new Phrase(String.valueOf(i + 1)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(prod_name.get(i)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(prod_sold_qty.get(i)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(prod_rate.get(i)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(String.valueOf(df.format(Float.parseFloat(prod_sold_qty.get(i)) * Float.parseFloat(prod_rate.get(i))))));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(subtotal)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            table.writeSelectedRows(0, -1, document.leftMargin(), 650, docWriter.getDirectContent());

            document.add(Chunk.NEWLINE);

            float[] columnWidths1 = {1.5f, 5f, 3.5f, 3.5f, 4f};
            PdfPTable table1 = new PdfPTable(columnWidths1);
            table1.setTotalWidth(500f);

            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            table1.addCell(cell);
            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            table1.addCell(cell);
            cell = new PdfPCell(new Phrase("Tax Number"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.addCell(cell);
            cell = new PdfPCell(new Phrase("Tax Rate"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.addCell(cell);
            cell = new PdfPCell(new Phrase("Total"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.addCell(cell);

            for (int i = 0; i < tax_name_list.size(); i++) {
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
                table1.addCell(cell);
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
                table1.addCell(cell);
                cell = new PdfPCell(new Phrase(tax_name_list.get(i)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table1.addCell(cell);
                cell = new PdfPCell(new Phrase(tax_rate_list.get(i)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table1.addCell(cell);
                cell = new PdfPCell(new Phrase(String.valueOf(df.format(subtotal * Float.parseFloat(tax_rate_list.get(i)) / 100))));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table1.addCell(cell);
            }

            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            table1.addCell(cell);
            cell = new PdfPCell(new Phrase(""));
            cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            table1.addCell(cell);
            cell = new PdfPCell(new Phrase("-"));
            cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.addCell(cell);
            cell = new PdfPCell(new Phrase("-"));
            cell.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(total_value)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.addCell(cell);

            float height = table.getTotalHeight();
            Log.d("HEIGHT", String.valueOf(height));

            int temp = table.getRows().size();
            float temp1 = table.getRowHeight(0);

            table1.writeSelectedRows(0, -1, document.leftMargin(), 650 - table1.getTotalHeight() - (temp * temp1), docWriter.getDirectContent());

            //print the signature image along with the persons name
            Image signature = Image.getInstance(comp_sign);
            signature.setAbsolutePosition(400f, 150f);
            signature.scalePercent(15f);
            document.add(signature);

            createHeadings(cb, 450, 135, personName);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //PDF file is now ready to be sent to the bluetooth printer using PrintShare
        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setPackage("com.dynamixsoftware.printershare");
        i.setDataAndType(Uri.fromFile(pdfFile), "application/pdf");
        startActivity(i);

    }

    private void createHeadings(PdfContentByte cb, float x, float y, String text) {

        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();

    }

    private void initializeFonts() {


        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

}


// Old OnCreate Code


//        databaseHelper = new DatabaseHelper(this);
//        Intent intent = getIntent();
//        try {
//            client_name = intent.getExtras().getString("search_client_result");
////            if (client_name != "") {
////
////            }
//            search_client.setText(client_name);
//        } catch (Exception e) {
//
//        }
//
//        Intent intent1 = getIntent();
//        invoice_id = intent1.getExtras().getString("invoice_name");
//        final Cursor res = databaseHelper.TABLE_INVOICE_view();
//
//        prod_name.clear();
//        prod_rate.clear();
//        prod_sold_qty.clear();
//
////        Cursor cursor = databaseHelper.TABLE_CLIENTS_view();
////        while (cursor.moveToNext()) {
////            if (cursor.getString(1).contentEquals(client_name)) {
////                client_id = cursor.getString(0);
////            }
////        }
//
//        Cursor res = databaseHelper.TABLE_INV_PRODUCT_LIST_view();
//        while (res.moveToNext()) {
//            if (res.getString(4).contentEquals("1")) {
//                prod_name.add(res.getString(1));
//                prod_sold_qty.add(res.getString(2));
//                prod_rate.add(res.getString(3));
//            }
//        }
//
//        InvoiceListCustomAdapter invoiceListCustomAdapter = new InvoiceListCustomAdapter(this, prod_name, prod_sold_qty, prod_rate);
//        listView.setAdapter(invoiceListCustomAdapter);
//        setListViewHeightBasedOnItems(listView);
//
//        search_client.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SearchClient.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        search_product.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (client_name != "") {
//                    Intent intent = new Intent(getApplicationContext(), SearchProduct.class);
//                    intent.putExtra("client_name", client_name);
//                    startActivity(intent);
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Select a Client initially!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });


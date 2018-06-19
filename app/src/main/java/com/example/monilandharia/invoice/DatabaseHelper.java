package com.example.monilandharia.invoice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.design.internal.NavigationMenu;
import android.util.Log;

import java.io.File;

/**
 * Created by Monil Andharia on 11-Apr-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "invoice.db";

    public static final String TABLE_PRODUCTS = "Inv_Product";
    public static final String PRODUCT_ID = "PRODUCT_ID";
    public static final String PRODUCT_TITLE = "PRODUCT_TITLE";
    public static final String PRODUCT_DESCRIPTION = "PRODUCT_DESCRIPTION";
    public static final String PRODUCT_QTY = "PRODUCT_QTY";
    public static final String PRODUCT_RATE = "PRODUCT_RATE";
    public static final String PRODUCT_NOTE = "PRODUCT_NOTE";

    public static final String TABLE_CLIENTS = "Inv_Client";
    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String CLIENT_NAME = "CLIENT_NAME";
    public static final String CLIENT_NUMBER = "CLIENT_NO";
    public static final String CLIENT_EMAIL = "CLIENT_EMAIL";
    public static final String CLIENT_ADDRESS1 = "CLIENT_ADDRESS1";
    public static final String CLIENT_ADDRESS2 = "CLIENT_ADDRESS2";
    public static final String CLIENT_ADDRESS3 = "CLIENT_ADDRESS3";
    public static final String CLIENT_CITY = "CLIENT_CITY";
    public static final String CLIENT_STATE = "CLIENT_STATE";
    public static final String CLIENT_WEBSITE = "CLIENT_WEBSITE";

    public static final String TABLE_INVOICE = "Inv_Invoice";
    public static final String INVOICE_ID = "INVOICE_ID";
    public static final String INVOICE_NAME = "INVOICE_NAME";
    public static final String INVOICE_CLIENT_NAME = "INVOICE_CLIENT_NAME";
    public static final String INVOICE_CLIENT_ID = "INVOICE_CLIENT_ID";

    public static final String TABLE_COMPANY_DETAILS = "Company_Details";
    public static final String COMPANY_LOGO = "COMPANY_LOGO";
    public static final String COMPANY_NAME = "COMPANY_NAME";
    public static final String COMPANY_TELEPHONE = "COMPANY_TELEPHONE";
    public static final String COMPANY_EMAIL = "COMPANY_EMAIL";
    public static final String COMPANY_WEBSITE = "COMPANY_WEBSITE";
    public static final String COMPANY_ADDRESS1 = "COMPANY_ADDRESS1";
    public static final String COMPANY_ADDRESS2 = "COMPANY_ADDRESS2";
    public static final String COMPANY_ADDRESS3 = "COMPANY_ADDRESS3";
    public static final String COMPANY_CITY = "COMPANY_CITY";
    public static final String COMPANY_STATE = "COMPANY_STATE";
    public static final String COMPANY_PINCODE = "COMPANY_PINCODE";
    public static final String COMPANY_TERMS_AND_CONDITIONS = "COMPANY_TERMS_AND_CONDITIONS";
    public static final String COMPANY_DECLARATION = "COMPANY_DECLARATION";
    public static final String COMPANY_SIGNATURE = "COMPANY_SIGNATURE";

    public static final String TABLE_INV_PRODUCT_LIST = "Inv_Product_List";
    public static final String SOLD_PRODUCT_LIST_ID = "SOLD_PRODUCT_LIST_ID";
    public static final String SOLD_CLIENT_ID = "SOLD_CLIENT_ID";
    public static final String SOLD_INVOICE_ID = "SOLD_INVOICE_ID";
    public static final String SOLD_PRODUCT_TITLE = "SOLD_PRODUCT_TITLE";
    public static final String SOLD_PRODUCT_QTY = "SOLD_PRODUCT_QTY";
    public static final String SOLD_PRODUCT_RATE = "SOLD_PRODUCT_RATE";

    public static final String TABLE_INV_TAX_LIST = "Inv_Tax_List";
    public static final String INV_TAX_LIST_ID = "INV_TAX_LIST";
    public static final String TAX_INVOICE_ID = "TAX_INVOICE_ID";
    public static final String TAX_TAX_ID = "TAX_TAX_ID";

    public static final String TABLE_TAXES = "Table_Taxes";
    public static final String TAX_ID = "TAX_ID";
    public static final String TAX_TYPE = "TAX_TYPE";
    public static final String TAX_NUMBER = "TAX_NUMBER";
    public static final String TAX_FLAG = "TAX_FLAG";
    public static final String TAX_RATE = "TAX_RATE";

    public static final String TABLE_CHECKFLAG = "Check_Flag";
    public static final String FIRST_TIME_INSTALLED_FLAG = "FIRST_TIME_INSTALLED_FLAG";
    public static final String TEMP = "TEMP";

    public DatabaseHelper(Context context) {
//        super(context, Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME, null, 1);
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + " ( PRODUCT_ID INTEGER PRIMARY KEY AUTOINCREMENT, PRODUCT_TITLE TEXT, PRODUCT_DESCRIPTION TEXT, PRODUCT_QTY TEXT, PRODUCT_RATE TEXT, PRODUCT_NOTE TEXT ) ");
        db.execSQL("CREATE TABLE " + TABLE_CLIENTS + " ( CLIENT_ID INTEGER PRIMARY KEY AUTOINCREMENT, CLIENT_NAME TEXT, CLIENT_NO TEXT, CLIENT_EMAIL TEXT, CLIENT_ADDRESS1 TEXT, CLIENT_ADDRESS2 TEXT, CLIENT_ADDRESS3 TEXT, CLIENT_CITY TEXT, CLIENT_STATE TEXT, CLIENT_WEBSITE TEXT ) ");
        db.execSQL("CREATE TABLE " + TABLE_CHECKFLAG + " ( FIRST_TIME_INSTALLED_FLAG INTEGER PRIMARY KEY AUTOINCREMENT, TEMP TEXT ) ");
        db.execSQL("CREATE TABLE " + TABLE_COMPANY_DETAILS + " ( COMPANY_LOGO BLOB, COMPANY_NAME TEXT, COMPANY_TELEPHONE TEXT, COMPANY_EMAIL TEXT, COMPANY_WEBSITE TEXT, COMPANY_ADDRESS1 TEXT, COMPANY_ADDRESS2 TEXT, COMPANY_ADDRESS3 TEXT, COMPANY_CITY TEXT, COMPANY_STATE TEXT, COMPANY_PINCODE TEXT, COMPANY_TERMS_AND_CONDITIONS TEXT, COMPANY_DECLARATION TEXT, COMPANY_SIGNATURE BLOB ) ");
        db.execSQL("CREATE TABLE " + TABLE_TAXES + " ( TAX_ID INTEGER PRIMARY KEY AUTOINCREMENT, TAX_TYPE TEXT, TAX_NUMBER TEXT, TAX_FLAG TEXT, TAX_RATE TEXT ) ");
        db.execSQL("CREATE TABLE " + TABLE_INV_PRODUCT_LIST + " ( SOLD_PRODUCT_LIST_ID INTEGER PRIMARY KEY AUTOINCREMENT, SOLD_PRODUCT_TITLE TEXT, SOLD_PRODUCT_QTY TEXT, SOLD_PRODUCT_RATE TEXT, SOLD_CLIENT_ID TEXT, SOLD_INVOICE_ID TEXT  ) ");
        db.execSQL("CREATE TABLE " + TABLE_INVOICE + " ( INVOICE_ID INTEGER PRIMARY KEY AUTOINCREMENT, INVOICE_NAME TEXT, INVOICE_CLIENT_NAME TEXT, INVOICE_CLIENT_ID TEXT ) ");
        db.execSQL("CREATE TABLE " + TABLE_INV_TAX_LIST + " ( INV_TAX_LIST INTEGER PRIMARY KEY AUTOINCREMENT, TAX_INVOICE_ID TEXT, TAX_TAX_ID TEXT ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKFLAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAXES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INV_PRODUCT_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVOICE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INV_TAX_LIST);
        onCreate(db);
    }

    //TABLE_COMPANY_DETAILS
    public boolean TABLE_COMPANY_DETAILS_onInsert(byte[] logo, String name, String telephone, String email, String website, String address1, String address2, String address3, String city, String state, String pincode, String tc, String dec, byte[] sign) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMPANY_LOGO, logo);
        contentValues.put(COMPANY_NAME, name);
        contentValues.put(COMPANY_TELEPHONE, telephone);
        contentValues.put(COMPANY_EMAIL, email);
        contentValues.put(COMPANY_WEBSITE, website);
        contentValues.put(COMPANY_ADDRESS1, address1);
        contentValues.put(COMPANY_ADDRESS2, address2);
        contentValues.put(COMPANY_ADDRESS3, address3);
        contentValues.put(COMPANY_CITY, city);
        contentValues.put(COMPANY_STATE, state);
        contentValues.put(COMPANY_PINCODE, pincode);
        contentValues.put(COMPANY_TERMS_AND_CONDITIONS, tc);
        contentValues.put(COMPANY_DECLARATION, dec);
        contentValues.put(COMPANY_SIGNATURE, sign);

        long res = database.insert(TABLE_COMPANY_DETAILS, null, contentValues);
        if (res == -1) {
            return false;
        } else {
            Log.d("Check", "TRUE CALLED");
            return true;
        }
    }

    public Cursor TABLE_COMPANY_DETAILS_view() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_COMPANY_DETAILS, null);
        return res;
    }

    //TABLE_INV_TAX_LIST
    public boolean TABLE_INV_TAX_LIST_onInsert(String invoice_id, String tax_id) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAX_INVOICE_ID, invoice_id);
        contentValues.put(TAX_TAX_ID, tax_id);
        long res = database.insert(TABLE_INV_TAX_LIST, null, contentValues);
        if (res == -1) {
            return false;
        } else {
            Log.d("Check", "TRUE CALLED");
            Log.d("1",contentValues.toString());
            return true;
        }
    }

    public Cursor TABLE_INV_TAX_LIST_view() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_INV_TAX_LIST, null);
        return res;
    }

    public int TABLE_INV_TAX_LIST_deleteData(String invoice_id, String tax_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_INV_TAX_LIST, "TAX_INVOICE_ID = ? AND TAX_TAX_ID = ? ", new String[]{invoice_id,tax_id});
        Log.d("DELETE: ",String.valueOf(res));
        return res;
    }

    //TABLE_INVOICE
    public boolean TABLE_INVOICE_onInsert(String name, String client_id, String respective_client_id) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INVOICE_NAME, name);
        contentValues.put(INVOICE_CLIENT_NAME, client_id);
        contentValues.put(INVOICE_CLIENT_ID, respective_client_id);

        long res = database.insert(TABLE_INVOICE, null, contentValues);
        if (res == -1) {
            return false;
        } else {
            Log.d("Check", "TRUE CALLED");
            return true;
        }
    }

    public Cursor TABLE_INVOICE_view() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_INVOICE, null);
        return res;
    }

    public boolean TABLE_INVOICE_updateData(String id, String name, String client_name, String client_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INVOICE_ID, id);
        contentValues.put(INVOICE_NAME, name);
        contentValues.put(INVOICE_CLIENT_NAME, client_name);
        contentValues.put(INVOICE_CLIENT_ID, client_id);

        db.update(TABLE_INVOICE, contentValues, "INVOICE_ID = ?", new String[]{id});
        return true;
    }

    public int TABLE_INVOICE_deleteData(String client_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_INVOICE, "INVOICE_CLIENT_NAME = ?", new String[]{client_name});
        return res;
    }

    //TABLE_INV_PRODUCT_LIST
    public boolean TABLE_INV_PRODUCT_LIST_onInsert(String title, String qty, String rate, String client_id, String invoice_id) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SOLD_PRODUCT_TITLE, title);
        contentValues.put(SOLD_PRODUCT_QTY, qty);
        contentValues.put(SOLD_PRODUCT_RATE, rate);
        contentValues.put(SOLD_CLIENT_ID, client_id);
        contentValues.put(SOLD_INVOICE_ID, invoice_id);
        long res = database.insert(TABLE_INV_PRODUCT_LIST, null, contentValues);
        if (res == -1) {
            return false;
        } else {
            Log.d("Check", "TRUE CALLED");
            Log.d("1",contentValues.toString());
            return true;
        }
    }

    public Cursor TABLE_INV_PRODUCT_LIST_view() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_INV_PRODUCT_LIST, null);
        return res;
    }

    //TABLE_TAXES
    public boolean TABLE_TAXES_onInsert(String type, String number, String flag, String rate) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAX_TYPE, type);
        contentValues.put(TAX_NUMBER, number);
        contentValues.put(TAX_FLAG,flag);
        contentValues.put(TAX_RATE, rate);
        long res = database.insert(TABLE_TAXES, null, contentValues);
        if (res == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor TABLE_TAXES_view() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_TAXES, null);
        return res;
    }

    //TABLE_CHECKFLAG
    public boolean TABLE_CHECKFLAG_onInsert(String temp) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEMP, temp);
        long res = database.insert(TABLE_CHECKFLAG, null, contentValues);
        if (res == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor TABLE_CHECKFLAG_view() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_CHECKFLAG, null);
        return res;
    }

    //TABLE_PRODUCTS
    public boolean TABLE_PRODUCTS_onInsert(String product_title, String product_description, String product_qty, String product_rate, String product_note) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_TITLE, product_title);
        contentValues.put(PRODUCT_DESCRIPTION, product_description);
        contentValues.put(PRODUCT_QTY, product_qty);
        contentValues.put(PRODUCT_RATE, product_rate);
        contentValues.put(PRODUCT_NOTE, product_note);

        long res = database.insert(TABLE_PRODUCTS, null, contentValues);
        if (res == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor TABLE_PRODUCTS_view() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
        return res;
    }

    public boolean TABLE_PRODUCTS_updateData(String id, String title, String desc, String qty, String rate, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_ID, id);
        contentValues.put(PRODUCT_TITLE, title);
        contentValues.put(PRODUCT_DESCRIPTION, desc);
        contentValues.put(PRODUCT_QTY, qty);
        contentValues.put(PRODUCT_RATE, rate);
        contentValues.put(PRODUCT_NOTE, note);
        db.update(TABLE_PRODUCTS, contentValues, "PRODUCT_ID = ?", new String[]{id});
        return true;
    }

    public boolean TABLE_PRODUCTS_updateQty(String id, String qty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_ID, id);
        contentValues.put(PRODUCT_QTY, qty);
        db.update(TABLE_PRODUCTS, contentValues, "PRODUCT_ID = ?", new String[]{id});
        return true;
    }

    public int TABLE_PRODUCTS_deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_PRODUCTS, "PRODUCT_ID = ?", new String[]{id});
        return res;
    }

    //TABLE_CLIENTS
    public boolean TABLE_CLIENTS_onInsert(String client_name, String client_no, String client_email, String client_address1, String client_address2, String client_address3, String client_city, String client_state, String client_website) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLIENT_NAME, client_name);
        contentValues.put(CLIENT_NUMBER, client_no);
        contentValues.put(CLIENT_EMAIL, client_email);
        contentValues.put(CLIENT_ADDRESS1, client_address1);
        contentValues.put(CLIENT_ADDRESS2, client_address2);
        contentValues.put(CLIENT_ADDRESS3, client_address3);
        contentValues.put(CLIENT_CITY, client_city);
        contentValues.put(CLIENT_STATE, client_state);
        contentValues.put(CLIENT_WEBSITE, client_website);

        long res = database.insert(TABLE_CLIENTS, null, contentValues);
        if (res == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor TABLE_CLIENTS_view() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_CLIENTS, null);
        return res;
    }

    public boolean TABLE_CLIENTS_updateData(String id, String no, String name, String email, String website, String addr1, String addr2, String addr3, String city, String state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLIENT_ID, id);
        contentValues.put(CLIENT_NUMBER, no);
        contentValues.put(CLIENT_NAME, name);
        contentValues.put(CLIENT_EMAIL, email);
        contentValues.put(CLIENT_WEBSITE, website);
        contentValues.put(CLIENT_ADDRESS1, addr1);
        contentValues.put(CLIENT_ADDRESS2, addr2);
        contentValues.put(CLIENT_ADDRESS3, addr3);
        contentValues.put(CLIENT_CITY, city);
        contentValues.put(CLIENT_STATE, state);
        db.update(TABLE_CLIENTS, contentValues, "CLIENT_ID = ?", new String[]{id});
        return true;
    }

    public int TABLE_CLIENTS_deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_CLIENTS, "CLIENT_ID = ?", new String[]{id});
        return res;
    }

}

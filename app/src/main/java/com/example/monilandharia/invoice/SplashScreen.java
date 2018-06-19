package com.example.monilandharia.invoice;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends Activity {

    DatabaseHelper databaseHelper;
    List<String> check_flag = new ArrayList<>();
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.TABLE_CHECKFLAG_onInsert("M");
        Cursor res = databaseHelper.TABLE_CHECKFLAG_view();
        while (res.moveToNext()) {
            check_flag.add(res.getString(0));
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(check_flag.size()==1){
                    Intent mainIntent = new Intent(SplashScreen.this,Tutorial.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
                else {
                    Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}

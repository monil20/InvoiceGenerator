package com.example.monilandharia.invoice;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    private FloatingActionMenu fab1;
    private FloatingActionButton fab12;
    private FloatingActionButton fab22;
    private FloatingActionButton fab32;

    boolean doubleBackToExitPressedOnce = false;

    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();

    final Context context = this;
    Intent intent;
    DatabaseHelper databaseHelper;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final FloatingActionMenu menu3 = (FloatingActionMenu) findViewById(R.id.menu3);
        fab1 = menu3;

        final FloatingActionButton programFab1 = new FloatingActionButton(this);
        programFab1.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab1.setLabelText("Programmatically added button");
        programFab1.setImageResource(R.drawable.ic_edit);

        ContextThemeWrapper context = new ContextThemeWrapper(this, R.style.MenuButtonsStyle);
        FloatingActionButton programFab2 = new FloatingActionButton(context);
        programFab2.setLabelText("Programmatically added button");
        programFab2.setImageResource(R.drawable.ic_edit);

        menus.add(menu3);


        menu3.hideMenuButton(false);


        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }

        fab12 = (FloatingActionButton) findViewById(R.id.sub_fab1);
        fab22 = (FloatingActionButton) findViewById(R.id.sub_fab2);
        fab32 = (FloatingActionButton) findViewById(R.id.sub_fab3);

        fab12.setOnClickListener(clickListener);
        fab22.setOnClickListener(clickListener);
        fab32.setOnClickListener(clickListener);

        createCustomAnimation();

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        fab1.setClosedOnTouchOutside(true);

        // display the first navigation drawer view on app launch
        displayView(0);
    }

    private void createCustomAnimation() {
        final FloatingActionMenu menu3 = (FloatingActionMenu) findViewById(R.id.menu3);

        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menu3.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menu3.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menu3.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menu3.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menu3.getMenuIconView().setImageResource(menu3.isOpened()
                        ? R.drawable.ic_close : R.drawable.fab_add);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        menu3.setIconToggleAnimatorSet(set);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ProductSans-Regular.ttf");
//            Intent intent;

            switch (v.getId()) {
                case R.id.sub_fab1:
                    //   text = fab12.getLabelText();
                    fab1.close(true);
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_add_invoice);
                    dialog.setCanceledOnTouchOutside(true);

                    editText = (EditText) dialog.findViewById(R.id.invoice_name);
                    Button button = (Button) dialog.findViewById(R.id.invoice_ok);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (checkValidation()) {
                                databaseHelper = new DatabaseHelper(context);
                                boolean insert_test = databaseHelper.TABLE_INVOICE_onInsert(editText.getText().toString(), "temp_value", "temp_id");
                                if (insert_test == true) {
                                    Toast.makeText(MainActivity.this, "Invoice Added Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Oops, facing downtime", Toast.LENGTH_SHORT).show();
                                }
                                intent = new Intent(getApplicationContext(), MainActivity.class);

                                startActivity(intent);
                                dialog.dismiss();
                                finish();
                            }

                        }
                    });
                    dialog.show();
                    break;
                case R.id.sub_fab2:
                    //  text = fab22.getLabelText();
                    intent = new Intent(getApplicationContext(), AddProduct.class);
                    fab1.close(true);
                    startActivity(intent);
                    break;
                case R.id.sub_fab3:
                    //  text = fab32.getLabelText();
                    intent = new Intent(getApplicationContext(), AddClient.class);
                    fab1.close(true);
                    startActivity(intent);
                    break;
            }

            //Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        Intent intent;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new InvoiceFragment();
                title = getString(R.string.title_invoice);
                break;
            case 1:
                fragment = new ProductFragment();
                title = getString(R.string.title_product);
                break;
            case 2:
                fragment = new ClientFragment();
                title = getString(R.string.title_client);
                break;
            case 3:
                fragment = new Settings();
                title = getString(R.string.title_settings);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            moveTaskToBack(true);
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.setCanceledOnTouchOutside(false);

        CustomTextView warning1 = (CustomTextView) dialog.findViewById(R.id.exit_warning_msg1);
        CustomTextView warning2 = (CustomTextView) dialog.findViewById(R.id.exit_warning_msg2);
        CustomTextView message = (CustomTextView) dialog.findViewById(R.id.exit_confirm_msg);
        Button ok = (Button) dialog.findViewById(R.id.exit_ok);
        Button cancel = (Button) dialog.findViewById(R.id.exit_cancel);

        databaseHelper = new DatabaseHelper(this);
        Cursor res = databaseHelper.TABLE_INVOICE_view();
        int flag=0;

        while(res.moveToNext()){
            if (res.getString(2).contentEquals("temp_value")){
                flag++;
            }
        }

        if(flag>0){

        }
        else{
            warning1.setVisibility(View.INVISIBLE);
            warning2.setVisibility(View.INVISIBLE);
            message.setPadding(0,45,0,0);
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.TABLE_INVOICE_deleteData("temp_value");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(editText)) ret = false;

        return ret;
    }

}
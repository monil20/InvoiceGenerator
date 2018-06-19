package com.example.monilandharia.invoice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;

public class Tutorial extends AppCompatActivity {
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ImageView nxt;
    Animation animation;
    private static final Integer[] IMAGES = {R.drawable.navigation_drawer, R.drawable.fab_with_actions, R.drawable.app_settings};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nxt = (ImageView) findViewById(R.id.splash_next_button);
        nxt.setVisibility(View.INVISIBLE);

        init();

        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void init() {
        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(Tutorial.this, ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = IMAGES.length;

        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                mPager.setCurrentItem(currentPage++, true);
//            }
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                if (position == NUM_PAGES - 1) {
                    animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_entry);
                    nxt.startAnimation(animation);
                    nxt.setVisibility(View.VISIBLE);
                } else {
                    animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_exit);
                    nxt.startAnimation(animation);
                    nxt.setVisibility(View.INVISIBLE);
                }
//                Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

}
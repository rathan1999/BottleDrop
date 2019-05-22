package com.example.sairathan.botttledrop;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private SliderAdapter sliderAdapter;
    private ImageView imageView;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSlideViewPager = (ViewPager)findViewById(R.id.slideViewPager);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);
        imageView = (ImageView)findViewById(R.id.glassesId);
        changeGlasses(currentPage);
        mSlideViewPager.addOnPageChangeListener(viewListner);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 3) {
                    currentPage = 0;
                }
                mSlideViewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    public int[] slide_icons={
            R.drawable.glass1,
            R.drawable.glass2,
            R.drawable.glass3
    };

    public void changeGlasses(int position)
    {
        imageView.setBackgroundResource(slide_icons[position]);
    }

    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            currentPage=i;
            changeGlasses(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}

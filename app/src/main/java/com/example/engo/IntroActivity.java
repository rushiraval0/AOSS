package com.example.engo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class IntroActivity extends AppCompatActivity {


    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;

    TabLayout tabIndicator;
    Button next;
    int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        next=findViewById(R.id.button);

        tabIndicator = findViewById(R.id.tab_indicator);

        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Helping Hands","sharing is caring",R.drawable.hands));
        mList.add(new ScreenItem("Charity","Donate with us",R.drawable.paisa));
        mList.add(new ScreenItem("Spread Love","Live and Let Live",R.drawable.imgone));


        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);


        tabIndicator.setupWithViewPager(screenPager);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                position = screenPager.getCurrentItem();



                if(position<mList.size()) {

                    position++;
                    screenPager.setCurrentItem(position);
                }




                if(position == mList.size())
                {
                    Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                    startActivity(intent);

                }


            }
        });


    }
}

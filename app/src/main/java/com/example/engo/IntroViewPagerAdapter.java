package com.example.engo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context mContext;
    List<ScreenItem> mLastScreen;

    public IntroViewPagerAdapter(Context mContext, List<ScreenItem> mLastScreen) {
        this.mContext = mContext;
        this.mLastScreen = mLastScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_screen,null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.intro_img);
        TextView title = layoutScreen.findViewById(R.id.intro_title);
        TextView description = layoutScreen.findViewById(R.id.intro_desc);

        title.setText(mLastScreen.get(position).getTitle());
        description.setText(mLastScreen.get(position).getDescription());
        imgSlide.setImageResource(mLastScreen.get(position).getScreenImg());

        container.addView(layoutScreen);

        return layoutScreen;






    }

    @Override
    public int getCount() {
        return mLastScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);
    }
}

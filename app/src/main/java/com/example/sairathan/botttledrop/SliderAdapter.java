package com.example.sairathan.botttledrop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context=context;
    }

    public int[] slide_icons={
            R.drawable.slide1,
            R.drawable.slide2,
            R.drawable.slide3
    };

    public String[] slide_headings={
            "Discover the Best Prices",
            "Delivery @ Your Door Step",
            "Let the Drinks Come to You"
    };

    public String[] slide_captions={
            "Thousands of products form stores near you",
            "Delivery in under 60 minutes",
            "More stores means more options"
    };

    public String[] slide_descs={
            "Bottle drop registers will all the local stores with wide variety of products with the best deals available for you",
            "Be it your favourite or your friends! More options at your fingertips. Not sure? Let your friends decide!",
            "Be it your favourite or your friends! More options at your fingertips. Not sure? Let your friends decide!"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==((RelativeLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView= (ImageView) view.findViewById(R.id.slideImage);
        TextView slideHeading=(TextView) view.findViewById(R.id.textView1);
        TextView slideCaption=(TextView) view.findViewById(R.id.textView2);
        TextView slideDescs=(TextView) view.findViewById(R.id.textView3);

        slideImageView.setBackgroundResource(slide_icons[position]);
        slideHeading.setText(slide_headings[position]);
        slideCaption.setText(slide_captions[position]);
        slideDescs.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,Object object){
        container.removeView((RelativeLayout)object);
    }
}

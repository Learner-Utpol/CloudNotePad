package com.raslab.cloudnotepad.pojo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.raslab.cloudnotepad.R;

public class SlideViewPager extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    public SlideViewPager (Context context){
        this.context=context;
    }
    public int[] slide_image={
            R.drawable.asset_1,
            R.drawable.asset_2,
            R.drawable.asset_3
    };


    public String[] slide_headings ={

            "Amin",
            "tushar",
            "Uddin"
    };
    public String[] slide_description ={

            "he role of the MainActivity in the above code is to just reference the ViewPager and set the CustomPagerAdapter that extends the PagerAdapter.",
            "The enum above lists all the pages of the ViewPagers. There are three pages with their respective layouts.",
            "The remaining two pages have similar layouts and are given in the source code of this project CustomPagerAdapter.java"
    };

    @Override
    public int getCount() {

        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject( View view, Object o) {

        return view ==(RelativeLayout)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.slide_layout,container,false);

        ImageView imageSlider = view.findViewById(R.id.image_slider);
        TextView sliderContent = view.findViewById(R.id.slider_content);
        TextView sliderDescription = view.findViewById(R.id.sliderDescription);

        imageSlider.setImageResource(slide_image[position]);
        sliderContent.setText(slide_headings[position]);
        sliderDescription.setText(slide_description[position]);


        container.addView(view);
        return  view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}

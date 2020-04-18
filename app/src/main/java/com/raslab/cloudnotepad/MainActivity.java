package com.raslab.cloudnotepad;

import android.content.Intent;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.raslab.cloudnotepad.pojo.SlideViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewpager;
    private LinearLayout mdots;
    private SlideViewPager slideViewPager;
    private TextView[] mdotsview;
    private Button guestLogin,userLogin;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final int RC_SIGN_IN =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //* View Pager
        mViewpager = findViewById(R.id.viewPagerSlider);

        mdots=findViewById(R.id.mDots);


        slideViewPager =new SlideViewPager(this);

        mViewpager.setAdapter(slideViewPager);
        addDotsIndicator(0);
        mViewpager.addOnPageChangeListener(onPageChangeListener);


        Timer timer= new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);


        //end View Pager

        //Button
        guestLogin = findViewById(R.id.guestLogin);
        userLogin = findViewById(R.id.userLogin);

        guestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, NavDrawerActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "User Signed in", Toast.LENGTH_SHORT).show();
            finish();
        }
        else  {
            userLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }



    }

    // Viewpager Mathod
    public  void  addDotsIndicator(int position){

        mdotsview = new TextView[3];
        mdots.removeAllViews();
        for (int i = 0 ;i < mdotsview.length;i++){
            mdotsview[i] = new TextView(this);
            mdotsview[i].setText(Html.fromHtml("&#8226"));
            mdotsview[i].setTextSize(40);
            mdotsview[i].setTextColor(getResources().getColor(R.color.colorTransparent));

            mdots.addView(mdotsview[i]);
        }
        if (mdotsview.length>0){
            mdotsview[position].setTextColor(getResources().getColor(R.color.colorTransparentClick));
            // mdotsview[position].setText(40);
        }


    }
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };



    //end firebaseUi
    public  class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mViewpager.getCurrentItem()==0){
                        mViewpager.setCurrentItem(1);
                    }else if(mViewpager.getCurrentItem()==1){
                        mViewpager.setCurrentItem(2);
                    }else{
                        mViewpager.setCurrentItem(0);
                    }
                }
            });
        }
    }
    //End ViewPager Method
}

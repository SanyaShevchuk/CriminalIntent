package com.bignerdranch.android.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

/**
 * Created by User on 21.04.2018.
 */

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";
    private ViewPager mViewPager;
    List<Crime> mCrimes;
    private Button mFirstButton;
    private Button mLastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        mFirstButton = (Button) findViewById(R.id.first_button);
        mLastButton = (Button) findViewById(R.id.last_button);
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mViewPager = (ViewPager) findViewById(R.id.crime_pager);
        mViewPager.setOffscreenPageLimit(1);
        mCrimes = CrimeLab.get(this).getCrimes();


        FragmentManager fragmentManager = getSupportFragmentManager();

        /**
         * In old version you can use ViewPager.setOnPageChangeListener, but now it
         * is depricated, and addOnPageChangeListener is alternative change
         */
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==mCrimes.size()-1) mLastButton.setEnabled(false);
                if(position==0) mFirstButton.setEnabled(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public int getCount() {
                return mCrimes.size();
            }

            @Override
            public Fragment getItem(int position) {
                if(mViewPager.getCurrentItem()==0) updateButton(false,true);
                else if(mViewPager.getCurrentItem()==mCrimes.size()-1) updateButton(true,false);
                else updateButton(true, true);
                Crime crime = mCrimes.get(position);

                return CrimeFragment.newIntent(crime.getId());
            }
        });

        for(int i=0; i<mCrimes.size();i++){
            if(mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewPager.getCurrentItem()!=0) {
                    mViewPager.setCurrentItem(0);
                    updateButton(false, true);
                }
            }
        });

        mLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(mViewPager.getCurrentItem()!=mCrimes.size()-1) {
                    mViewPager.setCurrentItem(mCrimes.size()-1);
                    updateButton(true, false);
                }
            }
        });
    }



    public void updateButton(boolean firstButton, boolean secondButton){
        mFirstButton.setEnabled(firstButton);
        mLastButton.setEnabled(secondButton);
    }

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

}

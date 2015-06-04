package edu.uw.prathh.musee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import edu.uw.prathh.musee.camera.CameraActivity;

/**
 * Created by owong on 6/3/15.
 */
public class ScreenSlidePageActivity extends FragmentActivity {

    public static final int NUM_PAGES = 5;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ScreenSlidePageFragment f = new ScreenSlidePageFragment();
            Bundle b = new Bundle();
            b.putInt("position", position);
            f.setArguments(b);
            return f;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void startCamera(View v) {
        Intent startCamera = new Intent(this, CameraActivity.class);
        startActivity(startCamera);
    }
}

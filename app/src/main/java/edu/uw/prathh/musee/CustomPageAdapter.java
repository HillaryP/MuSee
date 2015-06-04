package edu.uw.prathh.musee;

/**
 * Created by owong on 5/28/15.
 */
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
public class CustomPageAdapter extends PagerAdapter {
    public Object instantiateItem(View collection, int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int resId = 0;
        switch (position) {
            case 0:
                Log.d("Case 0", "Case 0 worked");
                resId = R.layout.tutorial_1;
                break;
            case 1:
                Log.d("Case 1", "Case 1 worked");
                resId = R.layout.tutorial_2;
                break;
            case 2:
                Log.d("Case 2", "Case 2 worked");
                resId = R.layout.tutorial_3;
                break;
            case 3:
                Log.d("Case 3", "Case 3 worked");
                resId = R.layout.tutorial_4;
                break;
            case 4:
                Log.d("Case 4", "Case 4 worked");
                resId = R.layout.tutorial_5;
                break;
        }
        View view = inflater.inflate(resId, null);
        ((ViewPager) collection).addView(view, 0);
        return view;
    }
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }
    @Override
    public Parcelable saveState() {
        return null;
    }
    @Override
    public int getCount() {
        return 5;
    }
}
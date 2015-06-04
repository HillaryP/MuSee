package edu.uw.prathh.musee;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;

/**
 * Created by owong on 5/21/15.
 */

public class TutorialSwipe extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_main);

        CustomPageAdapter adapter = new CustomPageAdapter();
        ViewPager myPager = (ViewPager)
                findViewById(R.id.pager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }
}

package edu.uw.prathh.musee.info;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.TabHost;
import android.app.TabActivity;



import edu.uw.prathh.musee.R;


public class AccessibilityActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Accessibility");

        // Working on adding tabs *NOT DONE*
//        TabHost tabHost = getTabHost();
//        tabHost.setCurrentTabByTag("First");
//
//        TabSpec firstTab = tabHost.newTabSpec("First");
//        firstTab.setIndicator("firstTab",getResources().getDrawable(R.drawable.ic_action_first)); //drawable 1
//        firstTab.setContent(R.id.first_content);    //View
//        tabHost.addTab(firstTab);
//
//        TabSpec secondTab = tabHost.newTabSpec("Second");
//        secondTab.setIndicator("secondTab",getResources().getDrawable(R.drawable.ic_action_second)); //drawable 2
//        secondTab.setContent(R.id.second_content);    //View
//        tabHost.addTab(secondTab);
//
//        TabSpec thirdTab = tabHost.newTabSpec("Third");
//        thirdTab.setIndicator("thirdTab",getResources().getDrawable(R.drawable.ic_action_third)); //drawable 3
//        thirdTab.setContent(R.id.third_content);    //View
//        tabHost.addTab(thirdTab);
//
//        tabHost.setCurrentTab(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accessibility, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

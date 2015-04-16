package edu.uw.prathh.musee.events;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.uw.prathh.musee.ExpandableListAdapter;
import edu.uw.prathh.musee.R;

public class EventActivity extends ActionBarActivity {
    private List<String> eventList;
    private Map<String, List<String>> collection;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Events");
        createEventList();
        createCollection();
        expandableListView = (ExpandableListView) findViewById(R.id.event_list);
        final ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(this, eventList, collection);
        expandableListView.setAdapter(expandableListAdapter);
    }

    private void createEventList() {
        eventList = new ArrayList<>();
        eventList.add("Fun super awesome event");
        eventList.add("Fun super awesome event 2");
        eventList.add("Fun super awesome event 3");
        eventList.add("Fun super awesome event 4");
        eventList.add("Fun super awesome event 5");
    }

    private void createCollection() {
        List<String> description = new ArrayList<>();
        description.add("Sunday, April 12, 2011\nBurke Second Floor\n6:00 - 9:00pm\nOMG description!");
        collection = new LinkedHashMap<>();
        for (String event : eventList) {
            collection.put(event, description);
        }
    }

    private void setGroupIndicatorToRight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        expandableListView.setIndicatorBounds(width - getDipsFromPixs(35), width - getDipsFromPixs(5));
    }

    private int getDipsFromPixs(float pix) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pix * scale + 0.5f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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

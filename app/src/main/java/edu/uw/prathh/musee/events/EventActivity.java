package edu.uw.prathh.musee.events;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
        //new InfoRequestTask().execute("http://www.burkemuseum.org/events"); TODO - FIX THIS
    }

    private void createCollection() {
        List<String> description = new ArrayList<>();
        description.add("2011-04-12\nBurke Second Floor\n6:00 - 9:00pm\nOMG description!");
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

    private class InfoRequestTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... uri) {
            List<String> list = new ArrayList<>();
            if (uri[0] != null) {
                try {
                    Document doc = Jsoup.connect(uri[0]).get();
                    Elements events = doc.getElementsByClass(".bk_event_listing");
                    for (Element event : events) {
                        list.add("Fun super awesome event");
                    }
                    return list;
                } catch (Exception e) {
                    Log.e("EventActivity", "Accessing the document did not work: " + e);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            eventList = result;
            Log.i("EventActivity", result.toString());
        }
    }
}

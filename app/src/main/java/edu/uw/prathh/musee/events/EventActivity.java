package edu.uw.prathh.musee.events;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.uw.prathh.musee.ExpandableListAdapter;
import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.R;

public class EventActivity extends Activity {
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        TextView title = (TextView) findViewById(R.id.header).findViewById(R.id.title);
        title.setText("Events");

        new InfoRequestTask().execute("http://www.burkemuseum.org/events/");

        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setList(List<String> result) {
        List<String> names = new ArrayList<>();
        Map<String, List<String>> collection = new LinkedHashMap<>();
        for (String event : result) {
            String[] nameAndRest = event.split(":::");
            names.add(nameAndRest[0] + ":::" + nameAndRest[1]);
            List<String> description = new ArrayList<>();
            description.add(nameAndRest[2]);
            collection.put(nameAndRest[0], description);
        }

        expandableListView = (ExpandableListView) findViewById(R.id.event_list);
        final ExpandableListAdapter expandableListAdapter =
                new ExpandableListAdapter(EventActivity.this, names, collection);
        expandableListView.setAdapter(expandableListAdapter);
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
    public void onResume() {
        super.onResume();
        new InfoRequestTask().execute("http://www.burkemuseum.org/events/");
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
            if (uri[0] != null) {
                try {
                    Connection.Response response = Jsoup.connect(uri[0])
                            .userAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.46 Safari/536.5")
                            .timeout(10*1000)
                            .ignoreHttpErrors(true)
                            .execute();
                    int statusCode = response.statusCode();
                    if (statusCode == 200) {
                        Document doc = Jsoup.connect(uri[0]).timeout(10 * 1000).get();
                        Elements events = doc.select(".bk_event_listing");
                        List<String> eventNames = new ArrayList<>();
                        for (Element e : events) {
                            String[] timeArray = e.getElementsByTag("h4").get(0).text().split("\\|");
                            String timeField = timeArray.length > 1 ? timeArray[1].trim() : "";
                            String imageURL = e.getElementsByTag("img").get(0).attr("src");
                            Log.i("EventActivity", "Image URL: " + imageURL);
                            String info = e.getElementsByTag("h2").text() + ":::"
                                    + imageURL + ":::"
                                    + e.getElementsByClass("bk_event_day").attr("name").toString() + "\n"
                                    + e.getElementsByClass("bk_event_location").text() + "\n"
                                    + timeField + "\n"
                                    + e.getElementsByTag("p").text();
                            eventNames.add(info);
                        }
                        return eventNames;
                    } else {
                        Log.e("EventActivity", "Status code: " + response.statusMessage());
                    }
                } catch (Exception e) {
                    Log.e("EventActivity", "Accessing the document did not work: " + e);
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(EventActivity.this, "Fetching event data now!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            Log.i("EventActivity", "In Task Response: " + result);
            if (result != null) {
                setList(result);
            }
        }
    }
}

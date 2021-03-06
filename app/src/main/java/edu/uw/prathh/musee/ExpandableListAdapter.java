package edu.uw.prathh.musee;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity context;
    private Map<String, List<String>> eventCollections;
    private List<String> events;
    private ImageView img;

    public ExpandableListAdapter(Activity context, List<String> events, Map<String, List<String>> eventCollections) {
        this.context = context;
        this.events = events;
        this.eventCollections = eventCollections;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return eventCollections.get(events.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView ( final int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent ) {

        final String text = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.single_event, null);
        }
        String[] items = text.split("\n");
        Calendar eventDate = convertToDate(items[0]);
        String dateString = eventDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
                + ", " + eventDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                + " " + eventDate.get(Calendar.DAY_OF_MONTH);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText(dateString + "\n" + items[1] + "\n" + items[2]);
        TextView item = (TextView) convertView.findViewById(R.id.description);
        item.setText(items[3]);
        Button button = (Button) convertView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent(v, groupPosition);
            }
        });
        return convertView;
    }

    private void addEvent(View v, int groupPosition) {
        String[] items = ((String) getChild(groupPosition, 0)).split("\n");
        String noNonNumbers = items[2].replaceAll("\\D+", " ");
        Calendar startDate = convertToDate(items[0]);
        Log.i("Events", "Time: " + noNonNumbers);
        String[] time = noNonNumbers.split(" ");
        int firstHour = 0;
        int firstMinute = 0;
        int secondHour = 0;
        int secondMinute = 0;
        if (time.length == 2) {
            firstHour = Integer.parseInt(time[0]);
            secondHour = Integer.parseInt(time[1]);
        } else {
            firstHour = Integer.parseInt(time[0]);
            firstMinute = Integer.parseInt(time[1]);
            secondHour = Integer.parseInt(time[2]);
            secondMinute = Integer.parseInt(time[3]);
        }

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH),
                firstHour,
                firstMinute);
        Calendar endTime = Calendar.getInstance();
        endTime.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH),
                secondHour,
                secondMinute);

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, (String) getGroup(groupPosition))
                .putExtra(CalendarContract.Events.DESCRIPTION,
                        ((TextView) ((View) v.getParent()).findViewById(R.id.description)).getText())
                .putExtra(CalendarContract.Events.EVENT_LOCATION, items[1])
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_FREE);
        context.startActivity(intent);
    }

    private Calendar convertToDate(String s) {
        try {
            DateFormat formatter = new SimpleDateFormat("/yyyy/MM/dd");
            Date date = formatter.parse(s);
            Calendar startDate = new GregorianCalendar();
            startDate.setTime(date);
            return startDate;
        } catch (ParseException e) {
            return null;
        }
    }

    public int getChildrenCount(int groupPosition) {
        return eventCollections.get(events.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return events.get(groupPosition);
    }

    public int getGroupCount() {
        return events.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String[] info = ((String) getGroup(groupPosition)).split(":::");
        String eventName = "";

        //String url = "";
        if (info.length > 1) {
            eventName = info[0];
            //url = info[1];
        }
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_item, null);

        }
        TextView item = (TextView) convertView.findViewById(R.id.event);
        item.setText(eventName);

        //this.img = (ImageView) convertView.findViewById(R.id.event_img);
        //new BitmapRequestTask().execute(url);
        LinearLayout eventLayout = (LinearLayout) convertView.findViewById(R.id.oneevent);
        String eventType = item.getText().toString();
        if(eventType.toLowerCase().contains("pub")){
            eventLayout.setBackgroundResource(R.drawable.burke9);
        } else if (eventType.toLowerCase().contains("free")) {
            eventLayout.setBackgroundResource(R.drawable.burke10);
        } else if(eventType.toLowerCase().contains("earth")) {
            eventLayout.setBackgroundResource(R.drawable.burke8);
        }
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class BitmapRequestTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... uri) {
            Bitmap bm = null;
            try {
                URL aURL = new URL(uri[0]);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e("ExpandableListAdapter", "Error getting bitmap", e);
            }
            return bm;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            Log.i("EventActivity", "In Task Response: " + result);
            if (result != null) {
                img.setImageBitmap(result);
            }
        }
    }
}
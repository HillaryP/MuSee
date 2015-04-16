package edu.uw.prathh.musee;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    public static final String[] months = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    private Activity context;
    private Map<String, List<String>> eventCollections;
    private List<String> events;

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
        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText(items[0] + "\n" + items[1] + "\n" + items[2]);
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

        String[] date = items[0].split(" ");
        String[] time = items[2].split(" ");

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(Integer.parseInt(date[3]), Arrays.asList(months).indexOf(date[1]),
                Integer.parseInt(date[2].substring(0, date[2].length() - 1)),
                Integer.parseInt(time[0].split(":")[0]),
                Integer.parseInt(time[0].split(":")[1]));
        Calendar endTime = Calendar.getInstance();
        endTime.set(Integer.parseInt(date[3]), Arrays.asList(months).indexOf(date[1]),
                Integer.parseInt(date[2].substring(0, date[2].length() - 1)),
                Integer.parseInt(time[2].split(":")[0]),
                Integer.parseInt(time[2].split(":")[1].substring(0, time[2].split(":")[1].length() - 2)));

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
        String eventName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_item, null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.event);
        item.setText(eventName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
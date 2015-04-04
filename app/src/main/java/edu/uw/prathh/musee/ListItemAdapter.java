package edu.uw.prathh.musee;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.uw.prathh.musee.camera.CameraActivity;
import edu.uw.prathh.musee.donate.DonateActivity;
import edu.uw.prathh.musee.events.EventActivity;
import edu.uw.prathh.musee.exhibits.ExhibitActivity;
import edu.uw.prathh.musee.feedback.FeedbackActivity;
import edu.uw.prathh.musee.info.AccessibilityActivity;
import edu.uw.prathh.musee.info.FavoritesActivity;
import edu.uw.prathh.musee.info.MapActivity;

public class ListItemAdapter extends ArrayAdapter {
    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public ListItemAdapter(Context ctx, int resourceId, List objects) {

        super( ctx, resourceId, objects );
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context=ctx;
    }

    @Override
    public View getView ( final int position, View convertView, ViewGroup parent ) {
        convertView = (LinearLayout) inflater.inflate( resource, null );

        MenuListItem item = (MenuListItem) getItem( position );
        String label = item.getLabel();
        TextView labelView = (TextView) convertView.findViewById(R.id.label);
        labelView.setText(label);
        final Intent goTo = getGoTo(label);
        labelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(goTo);
            }
        });
        return convertView;
    }

    public Intent getGoTo(String label) {
        switch (label) {
            case "SCAN ARTIFACTS":
                return new Intent(context, CameraActivity.class);
            case "FAVORITES":
                return new Intent(context, FavoritesActivity.class);
            case "EXHIBITS":
                return new Intent(context, ExhibitActivity.class);
            case "EVENTS":
                return new Intent(context, EventActivity.class);
            case "ACCESSIBILITY":
                return new Intent(context, AccessibilityActivity.class);
            case "MAP":
                return new Intent(context, MapActivity.class);
            case "MUSEUM FEEDBACK":
                return new Intent(context, FeedbackActivity.class);
            case "DONATE TO THE MUSEUM":
                return new Intent(context, DonateActivity.class);
        };
        return new Intent(context, MenuActivity.class);
    }
}
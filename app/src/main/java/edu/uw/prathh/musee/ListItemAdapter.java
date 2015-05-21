package edu.uw.prathh.musee;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Typeface;
import android.content.Context;

import edu.uw.prathh.musee.camera.CameraActivity;
import edu.uw.prathh.musee.donate.DonateActivity;
import edu.uw.prathh.musee.events.EventActivity;
import edu.uw.prathh.musee.exhibits.ExhibitActivity;
import edu.uw.prathh.musee.feedback.FeedbackActivity;
import edu.uw.prathh.musee.info.AccessibilityActivity;
import edu.uw.prathh.musee.info.FavoritesActivity;
import edu.uw.prathh.musee.info.MapActivity;
import edu.uw.prathh.musee.media.ArtifactInfoFragment;

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

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);

        MenuListItem item = (MenuListItem) getItem( position );
        String label = item.getLabel();
        TextView labelView = (TextView) convertView.findViewById(R.id.label);
        labelView.setText(label);
        labelView.setTextColor(Color.parseColor("#FF5337"));
//        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir.ttc");
//        labelView.setTypeface(custom_font);
        labelView.setTextSize(24);

        final Intent goTo = getGoTo(label, imageView);

        labelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(goTo);
            }
        });
        return convertView;
    }

    public Intent getGoTo(String label, ImageView imageView) {


        switch (label) {
            case "SCAN ARTIFACTS":
                imageView.setImageResource(R.drawable.smallmenu3);
                return new Intent(context, CameraActivity.class);
            case "FAVORITES":
                imageView.setImageResource(R.drawable.smallmenu4);
                return new Intent(context, FavoritesActivity.class);
            case "EXHIBITS":
                imageView.setImageResource(R.drawable.smallmenu2);
                return new Intent(context, ExhibitActivity.class);
            case "EVENTS":
                imageView.setImageResource(R.drawable.smallmenu5);
                return new Intent(context, EventActivity.class);
            case "ACCESSIBILITY":
                imageView.setImageResource(R.drawable.smallmenu8);
                return new Intent(context, AccessibilityActivity.class);
            case "MAP":
                imageView.setImageResource(R.drawable.smallmenu7);
                return new Intent(context, MapActivity.class);
            case "MUSEUM FEEDBACK":
                imageView.setImageResource(R.drawable.smallmenu6);
                return new Intent(context, FeedbackActivity.class);
            case "DONATE":
                imageView.setImageResource(R.drawable.smallmenu9);
                return new Intent(context, DonateActivity.class);
            default:
                imageView.setImageResource(R.drawable.filledstar);
                return new Intent(context, MenuActivity.class);
        }
    }
}
package edu.uw.prathh.musee.media;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import edu.uw.prathh.musee.R;


/**
 * Information fragment for artifact-specific information
 */
public class GalleryFragment extends Fragment {
    private List<ParseObject> imageList;

    public GalleryFragment() {
    }

    public static GalleryFragment newInstance() {
        GalleryFragment f = new GalleryFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            Log.i("ArtifactInfoFragment", "PoiData: " + getArguments().getString("poi"));
            String id = getArguments().getString("id");
            ParseQuery<ParseObject> queryPhoto = ParseQuery.getQuery("ArtImages");
            ParseObject obj = ParseObject.createWithoutData("Artifacts", id);
            queryPhoto.whereEqualTo("artifact_name", obj);
            queryPhoto.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> information, ParseException e) {
                    if (e == null) {
                        imageList = information;
                        Log.i("CameraActivity", information.size() + " photos for this artifact");
                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.gallery_frag, container, false);
        TextView first = (TextView) rootView.findViewById(R.id.first);
        first.setText(imageList.get(0).get("image").toString());
        TextView second = (TextView) rootView.findViewById(R.id.second);
        second.setText(imageList.get(1).get("image").toString());
        TextView third = (TextView) rootView.findViewById(R.id.third);
        third.setText(imageList.get(2).get("image").toString());
        return rootView;
    }
}
package edu.uw.prathh.musee.media;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import edu.uw.prathh.musee.R;


/**
 * Information fragment for artifact-specific information
 */
public class GalleryFragment extends Fragment {
    private String id;
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
            Log.i("GalleryFragment", "ArtifactId: " + getArguments().getString("artifactId"));
            this.id = getArguments().getString("artifactId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.gallery_frag, container, false);

        ParseQuery<ParseObject> queryPhoto = ParseQuery.getQuery("ArtImages");
        ParseObject obj = ParseObject.createWithoutData("Artifacts", this.id);
        queryPhoto.whereEqualTo("artifact_name", obj);
        queryPhoto.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> information, ParseException e) {
                if (e == null) {
                    imageList = information;

                    final LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.layout);
                    for (ParseObject image : imageList) {
                        ParseFile parseFile = (ParseFile) image.get("image");
                        parseFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, ParseException e) {
                                ImageView first = new ImageView(rootView.getContext());
                                first.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                first.setImageBitmap(bmp);
                                layout.addView(first);
                            }
                        });
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        return rootView;
    }
}
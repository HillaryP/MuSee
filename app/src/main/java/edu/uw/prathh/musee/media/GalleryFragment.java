package edu.uw.prathh.musee.media;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
                        final String parseText = image.getString("name");
                        final String parseDescription = image.getString("description");
                        parseFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, ParseException e) {
                                ImageView first = new ImageView(rootView.getContext());
                                first.setLayoutParams(new ViewGroup.LayoutParams(400, 400));
                                first.setScaleType(ImageView.ScaleType.FIT_XY);
                                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                                LinearLayout layoutOne = new LinearLayout(rootView.getContext());
                                layoutOne.setOrientation(LinearLayout.VERTICAL);
                                TextView comment = new TextView(rootView.getContext());
                                TextView description= new TextView(rootView.getContext());
                                comment.setText(parseText);
                                description.setText(parseDescription);
                                comment.setTextSize(20);
                                description.setTextSize(13);
                                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(390, LinearLayout.LayoutParams.WRAP_CONTENT);
                                description.setLayoutParams(layoutParams2);

                                first.setPadding(0, 0, 0, 0);
                                comment.setPadding(20,30,10,10);
                                description.setPadding(20,10,10,30);

                                first.setImageBitmap(bmp);
                                comment.setGravity(Gravity.TOP);
                                comment.setTextColor(Color.parseColor("#3E4753"));
                                description.setTextColor(Color.parseColor("#3E4753"));
                                comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                                description.setBackgroundColor(Color.parseColor("#D8D8D8"));
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(10,150,10,0);


                                layoutOne.setLayoutParams(layoutParams);
                                layoutOne.setPadding(0,0,0,0);
//                                layoutOne.setPadding(left,top,right,bottom);
                                // adding image & comment to layoutOne, then adding to layout
                                layoutOne.addView(first);
                                layoutOne.addView(comment);
                                layoutOne.addView(description);
                                layout.addView(layoutOne);

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
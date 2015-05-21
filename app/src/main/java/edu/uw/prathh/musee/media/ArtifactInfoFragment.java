package edu.uw.prathh.musee.media;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import edu.uw.prathh.musee.MuSeeApp;
import edu.uw.prathh.musee.R;
import edu.uw.prathh.musee.camera.CameraActivity;

/**
 * Information fragment for artifact-specific information
 */
public class ArtifactInfoFragment extends Fragment {
    String poiData;
    String artifactId;
    String url;
    String audioUrl;
    static MediaPlayer mediaPlayer; //TODO - This should not be static

    public ArtifactInfoFragment() {
    }

    public static ArtifactInfoFragment newInstance() {
        ArtifactInfoFragment f = new ArtifactInfoFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            Log.i("ArtifactInfoFragment", "PoiData: " + getArguments().getString("poi"));
            this.poiData = getArguments().getString("poi");
            mediaPlayer = new MediaPlayer();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.artifact_info_frag, container, false);

        TextView title = (TextView) rootView.findViewById(R.id.title);
        title.setText(this.poiData);

        final ImageButton favorite = (ImageButton) rootView.findViewById(R.id.favorite);
        if (((MuSeeApp) getActivity().getApplication()).getFavorites().contains(this.poiData)) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                favorite.setBackgroundResource(R.drawable.filledstar);
            } else {
                favorite.setBackground(getResources().getDrawable(R.drawable.filledstar));
            }
        }
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CameraActivity) getActivity()).addFavorite(favorite, poiData);
            }
        });

        setUp(rootView);

        // Set up the video box
        LinearLayout videoBox = (LinearLayout) rootView.findViewById(R.id.gridview).findViewById(R.id.video);
        videoBox.setBackgroundColor(Color.parseColor("#F5F5F5"));
        ((ImageView) videoBox.findViewById(R.id.imageView)).setImageResource(R.drawable.playbutton);
        videoBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        // Set up the music box
        LinearLayout musicBox = (LinearLayout) rootView.findViewById(R.id.gridview).findViewById(R.id.music);
        musicBox.setBackgroundColor(Color.parseColor("#D8D8D8"));
        ((ImageView) musicBox.findViewById(R.id.imageView)).setImageResource(R.drawable.music);
        musicBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = audioUrl;
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(url);
                    DialogFragment newFragment = new MediaPauseDialogFragment();
                    newFragment.show(getFragmentManager(), "media");
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    Log.e("CameraActivity", e.getLocalizedMessage());
                    Toast.makeText(rootView.getContext(), "This audio file is not supported", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Set up the photo box
        LinearLayout photoBox = (LinearLayout) rootView.findViewById(R.id.gridview).findViewById(R.id.photos);
        photoBox.setBackgroundColor(Color.parseColor("#EBEBEB"));
        ((ImageView) photoBox.findViewById(R.id.imageView)).setImageResource(R.drawable.photos);
        ((TextView) photoBox.findViewById(R.id.name)).setText("Gallery");
        ((TextView) photoBox.findViewById(R.id.sub_text)).setText("0 photos");
        photoBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CameraActivity", "ID: " + artifactId);
                ((CameraActivity) getActivity()).openGallery(artifactId);
            }
        });

        // Set up the share box
        LinearLayout shareBox = (LinearLayout) rootView.findViewById(R.id.gridview).findViewById(R.id.share);
        shareBox.setBackgroundColor(Color.parseColor("#E0E0E0"));
        ((ImageView) shareBox.findViewById(R.id.imageView)).setImageResource(R.drawable.share);
        ((TextView) shareBox.findViewById(R.id.name)).setText("Share:");
        ((TextView) shareBox.findViewById(R.id.sub_text)).setText("");
        shareBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - make something happen
            }
        });
        return rootView;
    }

        /*===============================Helper methods to populate media==========================*/

    private void setUp(final View rootView) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Artifacts");
        query.whereEqualTo("name", this.poiData);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> information, ParseException e) {
                if (e == null) {
                    TextView description = (TextView) rootView.findViewById(R.id.description);
                    LinearLayout videoBox = (LinearLayout) rootView.findViewById(R.id.gridview).findViewById(R.id.video);
                    LinearLayout musicBox = (LinearLayout) rootView.findViewById(R.id.gridview).findViewById(R.id.music);
                    if (information.size() > 0) {
                        artifactId = information.get(0).getObjectId();
                        setUpPhoto(artifactId, rootView);
                        description.setText(information.get(0).getString("description"));

                        int length = information.get(0).getInt("video_length");
                        if (length != 0) {
                            ((TextView) videoBox.findViewById(R.id.sub_text)).setText(
                                    length + " minutes");
                        } else {
                            ((TextView) videoBox.findViewById(R.id.sub_text)).setText("");
                        }
                        url = information.get(0).getString("video_url");
                        String videoName = information.get(0).getString("video_name");
                        if (videoName == null || videoName.length() == 0) {
                            ((TextView) videoBox.findViewById(R.id.name)).setText("Video");
                        } else {
                            ((TextView) videoBox.findViewById(R.id.name)).setText(videoName);
                        }

                        audioUrl = information.get(0).getString("music_url");
                        String audioName = information.get(0).getString("music_name");
                        if (audioName == null || audioName.length() == 0) {
                            ((TextView) musicBox.findViewById(R.id.name)).setText("Audio");
                        } else {
                            ((TextView) musicBox.findViewById(R.id.name)).setText(audioName);
                        }
                        String audioArtist = information.get(0).getString("music_artist");
                        if (audioArtist == null || audioArtist.length() == 0) {
                            ((TextView) musicBox.findViewById(R.id.sub_text)).setText("Unknown Artist");
                        } else {
                            ((TextView) musicBox.findViewById(R.id.sub_text)).setText(audioArtist);
                        }
                    } else {
                        description.setText("Artifact Media");
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    private void setUpPhoto(String artifactId, final View rootView) {
        ParseQuery<ParseObject> queryPhoto = ParseQuery.getQuery("ArtImages");
        ParseObject obj = ParseObject.createWithoutData("Artifacts", artifactId);
        queryPhoto.whereEqualTo("artifact_name", obj);
        queryPhoto.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> information, ParseException e) {
                if (e == null) {
                    LinearLayout photoBox = (LinearLayout) rootView.findViewById(R.id.gridview).findViewById(R.id.photos);
                    ((TextView) photoBox.findViewById(R.id.sub_text)).setText(information.size() + " photos");
                    Log.i("CameraActivity", information.size() + " photos for this artifact");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public static class MediaPauseDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Currently playing audio")
                    .setPositiveButton("Stop", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mediaPlayer.stop();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
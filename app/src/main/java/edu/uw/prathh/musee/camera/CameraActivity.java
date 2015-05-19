package edu.uw.prathh.musee.camera;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.ArchitectView.ArchitectUrlListener;

import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class CameraActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public final String license =
            "XcZk4IBkd5sD6CYfdtXjmum1pQPDB9JZ4w47uEDEPM4Lm+D2Ljn96qndDAATnKRsAqQr9De0/6v0Nm385cAjCGMvk71cg0of4FO9OamGtjwnCiYG0Q2sPT0cYq/y2rXmgETeHcXmsHb63k05QRvS3Xt62QsaknmjwANLC/oQshNTYWx0ZWRfX+9PmWk5kTsqStwQ3lKgmaVMo1phjuzjaAItHfmHdDaNVr6IdEqrqLsRVxgpuUlkPX0bohm0fh89mpB+5tYZ+ycykAf4S9k7wL7Io5r7lX5o8glXj5vn64KT3W+I9f2NpvjRvjQ5MYBqJkiN00oZMBCfA0ExVUDbOqiCwjeWkoYGKH2UftYF/CJgKzFoOUpBdOuR3WLaYvATUuyowxtSKs/BoDDh9p7TxHvNgNILEayhzGtgC6Ux9uKZqocStgQSn82mhHTR6fgSpSbSfoAj3gbweCn9Vn70lzXsuo7zeZOTXxgALTAoKiXimG4iVJiXJwm0N7O5pMPwI/FMfEnot+wIBdcXJ3Tt7eV3d4mpar2exfxLG5AxZlVDWt7hiio4cY5kVS+ZcU/psClbgQgHEJPbC7TYmXvFVos3J9RzZ8xhlRYxutQDZtXziqfiILTsoxRhHJUavuj8sY1uKxsqu7XKMne8dfD/2g==";
    private ArchitectView architectView;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private ArchitectView.ArchitectUrlListener urlListener;

    /*=====================================Lifecycle Event Handler methods==================================*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Log.i("CameraActivity", "Camera Activity Created");

        buildGoogleApiClient();
        createLocationRequest();

        this.architectView = (ArchitectView) findViewById(R.id.architect_view);
        final ArchitectView.ArchitectConfig config = new ArchitectView.ArchitectConfig(license);
        try {
            this.architectView.onCreate(config);
        } catch (Exception e) {
            Log.e("CameraActivity", "ArchitectView could not be created: " + e.getMessage());
        }

        ImageButton menuButton = (ImageButton) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        this.urlListener = getUrlListener();
        if ( this.urlListener !=null ) {
            this.architectView.registerUrlListener( this.getUrlListener() );
        }

        Toast.makeText(this, "Move your phone around to scan artifacts!", Toast.LENGTH_LONG);
    }

    @Override
    public void onPostCreate(Bundle bundleSavedInstance) {
        super.onPostCreate(bundleSavedInstance);
        Log.i("CameraActivity", "onPostCreate Called");
        if (this.architectView != null) {
            this.architectView.onPostCreate();
            try {
                this.architectView.load("index.html");
            } catch (Exception e) {
                Log.e("CameraActivity", "Cannot load arexperience: " + e.getLocalizedMessage());
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this.architectView != null) {
            this.architectView.onPause();
        }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.architectView != null) {
            this.architectView.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.architectView != null) {
            this.architectView.onDestroy();
        }
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if ( this.architectView != null ) {
            this.architectView.onLowMemory();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
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

    /*============================Location Connection/update methods===========================*/

    protected synchronized void buildGoogleApiClient() {
        Log.i("CameraActivity", "buildGoogleApiClient Called");
        this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        this.mGoogleApiClient.connect();
    }

    protected void createLocationRequest() {
        Log.i("CameraActivity", "createLocationRequest called");
        this.mLocationRequest = new LocationRequest()
                .setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Camera Activity", "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("CameraActivity", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        handleNewLocation(mCurrentLocation);
    }

    private void handleNewLocation(Location location) {
        Log.d("CameraActivity", location.toString());
        architectView.setLocation(location.getLatitude(), location.getLongitude(), 0.0, 0.0f);
    }

    /* ================================Wikitude specific methods=================================*/

    public ArchitectUrlListener getUrlListener() {
        return new ArchitectUrlListener() {
            @Override
            public boolean urlWasInvoked(String uriString) {
                Uri invokedUri = Uri.parse(uriString);
                Log.i("Wikitude", "URI invoked: " + uriString);

                // pressed "More" button on POI-detail panel
                if ("markerselected".equalsIgnoreCase(invokedUri.getHost())) {
                    final ArtifactInfoFragment artifactInfo = new ArtifactInfoFragment();

                    Bundle b = new Bundle();
                    b.putString("poi", invokedUri.getQueryParameter("title").toString());
                    artifactInfo.setArguments(b);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.architect_view, artifactInfo)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                }
                return true;
            }
        };
    }

    /**
     * Information fragment for artifact-specific information
     */
    public static class ArtifactInfoFragment extends Fragment {
        String poiData;

        public ArtifactInfoFragment() { }

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
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.artifact_info_frag, container, false);
//            ImageButton button = (ImageButton) rootView.findViewById (R.id.menu_button);
//            if(button != null) {
//                button.setVisibility(View.GONE);
//            }

            TextView title = (TextView) rootView.findViewById(R.id.title);
            title.setText(this.poiData);

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Artifacts");
            query.whereEqualTo("name", this.poiData);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> information, ParseException e) {
                    if (e == null) {
                        TextView description = (TextView) rootView.findViewById(R.id.description);
                        description.setText(information.get(0).getString("description"));
                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });

            LinearLayout videoBox = (LinearLayout) rootView.findViewById(R.id.gridview).findViewById(R.id.video);
            videoBox.setBackgroundColor(Color.parseColor("#F5F5F5"));
            ((ImageView) videoBox.findViewById(R.id.imageView)).setImageResource(R.drawable.playbutton);
            ((TextView) videoBox.findViewById(R.id.name)).setText("Origin Video");
            ((TextView) videoBox.findViewById(R.id.sub_text)).setText("3 minutes");

            LinearLayout musicBox = (LinearLayout) rootView.findViewById(R.id.gridview).findViewById(R.id.music);
            musicBox.setBackgroundColor(Color.parseColor("#D8D8D8"));
            ((ImageView) musicBox.findViewById(R.id.imageView)).setImageResource(R.drawable.music);
            ((TextView) musicBox.findViewById(R.id.name)).setText("\"Chant\"");
            ((TextView) musicBox.findViewById(R.id.sub_text)).setText("Artist");

            LinearLayout photoBox = (LinearLayout) rootView.findViewById(R.id.gridview).findViewById(R.id.photos);
            photoBox.setBackgroundColor(Color.parseColor("#EBEBEB"));
            ((ImageView) photoBox.findViewById(R.id.imageView)).setImageResource(R.drawable.photos);
            ((TextView) photoBox.findViewById(R.id.name)).setText("Gallery");
            ((TextView) photoBox.findViewById(R.id.sub_text)).setText("5 photos");

            LinearLayout shareBox = (LinearLayout) rootView.findViewById(R.id.gridview).findViewById(R.id.share);
            shareBox.setBackgroundColor(Color.parseColor("#E0E0E0"));
            ((ImageView) shareBox.findViewById(R.id.imageView)).setImageResource(R.drawable.share);
            ((TextView) shareBox.findViewById(R.id.name)).setText("Share:");
            ((TextView) shareBox.findViewById(R.id.sub_text)).setText("");
            return rootView;
        }
    }
}

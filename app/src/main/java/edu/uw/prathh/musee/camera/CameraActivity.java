package edu.uw.prathh.musee.camera;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.wikitude.architect.ArchitectView;

import edu.uw.prathh.musee.MenuActivity;
import edu.uw.prathh.musee.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class CameraActivity extends Activity implements
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
    }

    protected synchronized void buildGoogleApiClient() {
        Log.i("CameraActivity", "buildGoogleApiClient Called");
        this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        this.mGoogleApiClient.connect();
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
    }
}

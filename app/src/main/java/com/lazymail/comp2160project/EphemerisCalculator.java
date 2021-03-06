package com.lazymail.comp2160project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import swisseph.SweConst;
import swisseph.SweDate;

public class EphemerisCalculator extends AppCompatActivity {

    private double latitude = 49.1;
    private double longitude = -122.9;

    private int planet;
    private SweDate sd;

    private TextView txtElevation;
    private TextView txtAzimuth;
    private TextView txtRtAscension;
    private TextView txtDeclination;
    private TextView txtLatitude;
    private TextView txtLongitude;
    private TextView txtPlanetOutput;

    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID=44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ephemeris_calculator);

        new CopyAssetfiles(".*\\.se1", getApplicationContext()).copy();

        EphPlanetListData[] ephPlanetList = new EphPlanetListData[] {
                new EphPlanetListData("Sol", SweConst.SE_SUN),
                new EphPlanetListData("Mercury", SweConst.SE_MERCURY),
                new EphPlanetListData("Venus", SweConst.SE_VENUS),
                new EphPlanetListData("Mars", SweConst.SE_MARS),
                new EphPlanetListData("Jupiter", SweConst.SE_JUPITER),
                new EphPlanetListData("Saturn", SweConst.SE_SATURN),
                new EphPlanetListData("Uranus", SweConst.SE_URANUS),
                new EphPlanetListData("Neptune", SweConst.SE_NEPTUNE),
                new EphPlanetListData("Pluto", SweConst.SE_PLUTO)
        };

        RecyclerView ephRecyclerView = (RecyclerView) findViewById(R.id.ephRecyclerView);
        EphPlanetListAdapter adapter = new EphPlanetListAdapter((ephPlanetList));
        ephRecyclerView.setHasFixedSize(true);
        ephRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ephRecyclerView.setAdapter(adapter);

        txtElevation = (TextView) findViewById(R.id.textElevation);
        txtAzimuth = (TextView) findViewById(R.id.textAzimuth);
        txtRtAscension = (TextView) findViewById(R.id.textRighAscen);
        txtDeclination = (TextView) findViewById(R.id.textDeclination);
        txtLatitude = (TextView) findViewById(R.id.editTextLatitude);
        txtLongitude = (TextView) findViewById(R.id.editTextLongitude);

        txtPlanetOutput = (TextView) findViewById(R.id.planetOutputTextView);

        txtLatitude.setText("" + latitude);
        txtLongitude.setText("" + longitude);

        // commented out the next statement due to the UI becoming unresponsive.
        //mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // commented out the next two statements because we don't have a default planet set.
        //getLastLocation();
        //calculateAzEl(null);
    }

    public void calculateAzEl(View view) {
        String azString;
        String elString;

        PersistentVariables persistentVar = new PersistentVariables();
        double[] azel = new double[4];

        sd = EphemerisCalculatorUtility.timeNow();

        latitude = Double.parseDouble(txtLatitude.getText().toString());
        longitude = Double.parseDouble(txtLongitude.getText().toString());

        // set body
        planet = persistentVar.getPlanet();
        txtPlanetOutput.setText("" + planet);

        EphemerisCalculatorUtility.calcAzEl(getApplicationContext(), planet, sd, latitude, longitude, azel);

        txtRtAscension.setText(String.format("Right Ascension: %.2f", azel[2]));
        txtDeclination.setText(String.format("Declination: %.2f", azel[3]));

        azString = String.format("Azimuth: %.2f", azel[0]);
        elString = String.format("Elevation: %.2f", azel[1]);
        txtAzimuth.setText(azString);
        txtElevation.setText(elString);

        txtPlanetOutput.setText(EphemerisCalculatorUtility.GetFuzzyInstructions(azel[0], azel[1]));
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last location from FusedLocationClient object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            txtLatitude.setText(location.getLatitude() + "");
                            txtLongitude.setText(location.getLongitude() + "");
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest object with appropriate methods
        // LocationRequest() is deprecated. No idea what the replacement is or what it will do to this code.
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            txtLatitude.setText("Latitude: " + mLastLocation.getLatitude() + "");
            txtLongitude.setText("Longitude: " + mLastLocation.getLongitude() + "");
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location on Android 10.0 and higher, use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    public void updateLocation(View view) {
        //requestNewLocationData(); // do not use
        //getLastLocation();  // commented out because of unresponsive UI issue.
    }
}

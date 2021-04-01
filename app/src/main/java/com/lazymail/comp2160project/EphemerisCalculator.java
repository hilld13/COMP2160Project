package com.lazymail.comp2160project;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    private TextView txtGeneralOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ephemeris_calculator);

        new CopyAssetfiles(".*\\.se1", getApplicationContext()).copy();

        txtElevation = (TextView) findViewById(R.id.textElevation);
        txtAzimuth = (TextView) findViewById(R.id.textAzimuth);
        txtRtAscension = (TextView) findViewById(R.id.textRighAscen);
        txtDeclination = (TextView) findViewById(R.id.textDeclination);
        txtLatitude = (TextView) findViewById(R.id.editTextLatitude);
        txtLongitude = (TextView) findViewById(R.id.editTextLongitude);

        txtGeneralOutput = (TextView) findViewById(R.id.generalOutputTextView);

        txtLatitude.setText("" + latitude);
        txtLongitude.setText("" + longitude);

        calculateAzEl(null);

    }

    public void calculateAzEl(View view) {
        String azString;
        String elString;

        double[] azel = new double[4];

        sd = EphemerisCalculatorUtility.timeNow();

        latitude = Double.parseDouble(txtLatitude.getText().toString());
        longitude = Double.parseDouble(txtLongitude.getText().toString());

        // set body (hard code for now to Mars)
        planet = SweConst.SE_MARS;

        EphemerisCalculatorUtility.calcAzEl(getApplicationContext(), planet, sd, latitude, longitude, azel);

        txtRtAscension.setText(String.format("Right Ascension: %.2f", azel[2]));
        txtDeclination.setText(String.format("Declination: %.2f", azel[3]));

        azString = String.format("Azimuth: %.2f", azel[0]);
        elString = String.format("Elevation: %.2f", azel[1]);
        txtAzimuth.setText(azString);
        txtElevation.setText(elString);
    }
}
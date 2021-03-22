package com.lazymail.comp2160project;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;

public class EphemerisCalculator extends AppCompatActivity {

    private double latitude = 49.1;
    private double longitude = -122.9;
    private double azimuth;
    private double elevation;
    private int requestCode = 1000;

    private int t_second;
    private int t_minute;
    private int t_hour;
    private int day;
    private int month;
    private int year;
    private double hour;

    private int planet;
    private SweDate sd;

    double[] xp = new double[6];
    double[] xaz = new double[3];
    double [] xin = new double[3];
    double[] geopos = new double[3];

    private TextView txtElevation;
    private TextView txtAzimuth;
    private TextView txtRtAscension;
    private TextView txtDeclination;
    private TextView txtLatitude;
    private TextView txtLongitude;
    private TextView txtGeneralOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window. FEATURE_NO_TITLE);
        getSupportActionBar().hide();
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

    }

    public void calculateAzEl(View view) {
        StringBuffer serr = new StringBuffer();
        String s = "";
        Calendar cal = Calendar.getInstance(java.util.TimeZone.getTimeZone("Etc/UTC"), Locale.CANADA);

        double[] azel = new double[4];

        int flags = SweConst.SEFLG_EQUATORIAL | SweConst.SEFLG_SWIEPH | SweConst.SEFLG_SPEED | SweConst.SEFLG_TRUEPOS;
        boolean retrograde = false;

        String azString;
        String elString;

        SwissEph sw = new SwissEph(getApplicationContext().getFilesDir() + File.separator + "/ephe");

        sd = EphemerisCalculatorUtility.timeNow();

        latitude = Double.parseDouble(txtLatitude.getText().toString());
        longitude = Double.parseDouble(txtLongitude.getText().toString());

        sw.swe_set_topo(longitude, latitude, 0);

        // set body (hard code for now to Mars)
        planet = SweConst.SE_MARS;

        EphemerisCalculatorUtility.calcAzEl(getApplicationContext(), planet, sd, latitude, longitude, azel);


        txtRtAscension.setText(String.format("Right Ascension: %.2f", azel[2]));
        txtDeclination.setText(String.format("Declination: %.2f", azel[3]));

        azString = String.format("Azimuth: %.2f", azel[0]);
        elString = String.format("Elevation: %.2f", azel[1]);
        txtAzimuth.setText(azString);
        txtElevation.setText(elString);

        sw.swe_close();
    }
}
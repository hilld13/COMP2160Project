package com.lazymail.comp2160project;

import android.os.Bundle;
import android.view.View;
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
    //private SDate utdate;

    //private SwissEph sw = new SwissEph();   // SwissEph(java.lang.String path); path is the location of the ephemeris data files
    //private SwissEph sw = new SwissEph(getApplicationContext().getFilesDir() + File.separator + "ephe");     // crashes

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

        txtLatitude.setText("" + latitude);
        txtLongitude.setText("" + longitude);

        //txtMessages.setText(getCacheDir().toString());  // /data/user/0/ca.lazymail.ephemeristest2/cache
        //txtMessages.setText(getApplication().getAssets().toString());  // android.content.res.AssetManager@82e57c9
        //txtMessages.setText(getBaseContext().getAssets().toString()); // android.content.res.AssetManager@82e57c9
        //txtMessages.setText(getDataDir().toString()); // /data/user/ca.lazymail.ephemeristest2
        //txtMessages.setText(getDataDir().toString()); // /data/user/ca.lazymail.ephemeristest2
        //txtMessages.setText(getApplicationContext().getFilesDir() + File.separator + "ephe");   // /data/user/ca.lazymail.ephemeristest2/files/ephe

    }

    public void calcAzEl(View view) {
        StringBuffer serr = new StringBuffer();
        String s = "";
        Calendar cal = Calendar.getInstance(java.util.TimeZone.getTimeZone("Etc/UTC"), Locale.CANADA);

        int flags =
                SweConst.SEFLG_TOPOCTR |    // topocentric !!!!
                        SweConst.SEFLG_SWIEPH |         // slow and least accurate calculation method
                        SweConst.SEFLG_SIDEREAL |       // sidereal zodiac
                        SweConst.SEFLG_NONUT |          // will be set automatically for sidereal calculations, if not set here
                        SweConst.SEFLG_SPEED;           // to determine retrograde vs. direct motion
        boolean retrograde = false;

        SwissEph sw = new SwissEph(getApplicationContext().getFilesDir() + File.separator + "/ephe");

        // Set date/time
        t_second = cal.get(Calendar.SECOND);
        t_minute = cal.get(Calendar.MINUTE);
        t_hour = cal.get(Calendar.HOUR_OF_DAY);
        day = cal.get(Calendar.DATE);
        month = cal.get(Calendar.MONTH) + 1;
        year = cal.get(Calendar.YEAR);
        hour = t_hour + (t_minute / 60.0) + (t_second / 3600.0);

        sd = new SweDate(year, month, day, hour);
        sw.swe_set_topo(longitude, latitude, 0);
        // set body (hard code for now to Mars)
        planet = SweConst.SE_MARS;

        // calc RA/D
        int ret = sw.swe_calc_ut(sd.getJulDay(), planet, flags, xp, serr);

        txtRtAscension.setText("RA: " + xp[0]);
        txtDeclination.setText(" D: " + xp[1]);
        //txtMessages.setText("serr: " + serr);
        //txtMessages.setText("" + cal.getTimeZone());

        // convert to Az/El
        xin[0] = xp[0];
        xin[1] = xp[1];

        geopos[0] = latitude;
        geopos[1] = longitude;
        geopos[2] = 0;

        sw.swe_azalt(sd.getJulDay(), SweConst.SE_EQU2HOR, geopos, 0, 20, xin, xaz);

        txtElevation.setText("Elevation: " + xaz[1]);
        txtAzimuth.setText("Azimuth: " + xaz[0]);
    }
}
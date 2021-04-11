package com.lazymail.comp2160project;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;

public class EphemerisCalculatorUtility extends AppCompatActivity {
    public static SweDate timeNow() {
        int t_second;
        int t_minute;
        int t_hour;
        int day;
        int month;
        int year;
        double hour;
        SweDate sd;

        Calendar cal = Calendar.getInstance(java.util.TimeZone.getTimeZone("Etc/UTC"), Locale.CANADA);

        t_second = cal.get(Calendar.SECOND);
        t_minute = cal.get(Calendar.MINUTE);
        t_hour = cal.get(Calendar.HOUR_OF_DAY);
        day = cal.get(Calendar.DATE);
        month = cal.get(Calendar.MONTH) + 1;
        year = cal.get(Calendar.YEAR);
        hour = t_hour + (t_minute / 60.0) + (t_second / 3600.0);

        sd = new SweDate(year, month, day, hour);
        return sd;
    }

    public static void calcAzEl(Context context, int planet, SweDate sd, double latitude, double longitude, double[] azel) {
        /*
         *  we have to pass the context in from the caller. Just add "getApplicationContext()" to the argument for context.
         *  planet is one of the SweConst planets. eg, SweConst.SE_MARS
         *  SweDate sd is the date and time SweDate Object. I create a SweDate object called sd in the calling function, then set sd=EphemerisCalculatorUtility.timeNow() .
         *  latitude and longitude is that of the observer
         *  azel is a 4 element double array. Azimuth, Elevation, Right Ascension, Declination
         *  azel is where the results are stored.
         *
         * An example of a call to this function:
         * EphemerisCalculatorUtility.calcAzEl(getApplicationContext(), SweConst.SE_MARS, EphemerisCalculatorUtility.timeNow(), 49.1, -122.9, azelResultArray);
         */

        double azimuth;
        double elevation;
        double[] xp = new double[6];
        double[] xaz = new double[3];
        double [] xin = new double[3];
        double[] geopos = new double[3];

        StringBuffer serr = new StringBuffer();

        int flags = SweConst.SEFLG_EQUATORIAL | SweConst.SEFLG_SWIEPH | SweConst.SEFLG_SPEED;
        boolean retrograde = false;

        new CopyAssetfiles(".*\\.se1", context.getApplicationContext()).copy();

        SwissEph sw = new SwissEph(context.getApplicationContext().getFilesDir() + File.separator + "/ephe");

        sw.swe_set_topo(longitude, latitude, 0);

        // calc RA/D
        int ret = sw.swe_calc_ut(sd.getJulDay(), planet, flags, xp, serr);

        // convert to Az/El
        xin[0] = xp[0];
        xin[1] = xp[1];

        geopos[0] = longitude;
        geopos[1] = latitude;
        geopos[2] = 0;

        sw.swe_azalt(sd.getJulDay(), SweConst.SE_EQU2HOR, geopos, 0, 20, xin, xaz);

        azimuth = xaz[0] + 180.0;      // azimuth is incorrectly(?) calculated as south being 0 instead of 180.
        if (azimuth >= 360.0)  azimuth -= 360.0; // and if we roll over 360, then fix that too.

        elevation = xaz[1];

        sw.swe_close();

        azel[0] = azimuth;
        azel[1] = elevation;
        azel[2] = xp[0];
        azel[3] = xp[1];

    }

    public static String GetFuzzyInstructions(double azimuth, double elevation) {
        String fuzzyInstructions = "";

        if (elevation < 0)
            return "Not visible - Below the Horizon";

        if (azimuth <  22.5)
            fuzzyInstructions = "Look North";
        else if (azimuth < 67.5)
            fuzzyInstructions = "Look North East";
        else if (azimuth < 112.5)
            fuzzyInstructions = "Look East";
        else if (azimuth < 157.5)
            fuzzyInstructions = "Look South East";
        else if (azimuth < 202.5)
            fuzzyInstructions = "Look South";
        else if (azimuth < 247.5)
            fuzzyInstructions = "Look South West";
        else if (azimuth < 292.5)
            fuzzyInstructions = "Look West";
        else if (azimuth < 337.5)
            fuzzyInstructions = "Look North West";
        else if (azimuth <= 360.0)
            fuzzyInstructions = "Look North";

        if (elevation < 10)
            fuzzyInstructions += " and near the horizon";
        else if (elevation < 25)
            fuzzyInstructions += " and low in the sky";
        else if (elevation < 60)
            fuzzyInstructions += " and roughly half way up the sky.";
        else if (elevation < 90)
            fuzzyInstructions += " and almost straight up.";

        return fuzzyInstructions;
    }
}

package com.lazymail.comp2160project;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button camera;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window. FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void activityPlanetData(View view) {
        Intent actPD = new Intent(this, PlanetData.class);
        startActivity(actPD);
    }

    public void activityEphemerisCalculator(View view) {
        Intent actEC = new Intent(this, EphemerisCalculator.class);
        startActivity(actEC);
    }

    public void activityCompass(View view) {
        Intent actC = new Intent(this, Compass.class);
        startActivity(actC);
    }
}
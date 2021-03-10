package com.lazymail.comp2160project;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button camera;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openCamera();
    }

    // To access to camera
    protected void openCamera(){
        camera = (Button) findViewById(R.id.camera_btt);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void activityPlanetData(View view) {
        Intent actPD = new Intent(this, PlanetData.class);
        startActivity(actPD);
    }

    public void activityEphemerisCalculator(View view) {
        Intent actEC = new Intent(this, EphemerisCalculator.class);
        startActivity(actEC);
    }
}
package com.lazymail.comp2160project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.*;
import org.json.simple.parser.JSONParser;

public class PlanetData extends AppCompatActivity {
    //Declaring variables.
    ArrayList<String> arrayList = new ArrayList<String>();
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    JSONParser parser = new JSONParser();
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_data);
        readJson();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listView = (ListView) findViewById(R.id.listView_planets);
        listView.setAdapter(arrayAdapter);
    }

    public String LoadData(String inFile) {
        String tContents = "";

        try {
            InputStream stream = getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }
        return tContents;
    }
    //FETCH JSON FILE.
    public void readJson(){
        try {
            jsonArray = new JSONArray(LoadData("planetdata.json"));
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                    arrayList.add(jsonObject.getString("Body"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


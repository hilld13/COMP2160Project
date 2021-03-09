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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.*;
import org.json.simple.parser.JSONParser;

public class PlanetData extends AppCompatActivity {
    //Declaring variables.
    JSONArray jsonArray = new JSONArray(); //contains data of planets.
    ArrayList<String> arrayList = new ArrayList<String>();
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_data);
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listView = (ListView) findViewById(R.id.listView_planets);
        listView.setAdapter(arrayAdapter);
    }


    //READ JSON ARRAY
    public void readJson(){
        try {
            FileReader reader = new FileReader("/src/main/assets/planetdata.json");
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                    arrayList.add(jsonObject.getString("Body"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


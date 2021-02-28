package com.lazymail.comp2160project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.nio.file.Files;

import java.io.FileNotFoundException;

import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.*;


public class PlanetData extends AppCompatActivity {
    //Initializing variables
    ListView listView;
    ArrayList<String> planetList = new ArrayList<>();
    String[] testList = {"1", "2", "3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // I can't make the list appear properly for some reason ! Need help !!
        setContentView(R.layout.activity_planet_data);
        listView = findViewById(R.id.list_planet_view);

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, testList);
        listView.setAdapter(itemsAdapter);
    }


    //To add data from json file into array. (considering create a class as object to handle Planet data.)
    protected void addData() throws IOException {
        //Assign variables
        Gson gson = new Gson();
        String file = "src//main//assets//planetdata.json";
        String json = "";
        try {
            //read file
            json =  new String(Files.readAllBytes(Paths.get(file)));
            planetList = gson.fromJson(json, ArrayList.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


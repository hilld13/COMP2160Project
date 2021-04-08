package com.lazymail.comp2160project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlanetData extends AppCompatActivity {
    //Declaring variables.
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Planet> arrayPlanet = new ArrayList<>();
    List<list_item> mlist = new ArrayList<>();
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    Adapter adapter;
    JSONParser parser = new JSONParser();
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_data);

        //get data from json file.
       readJson();
        // set the statue bar background to transparent

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // setup recyclerview with the adapter

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        mlist.add(new list_item(R.drawable.sun, "Sun"));
        mlist.add(new list_item(R.drawable.mercury, "Mercury"));
        mlist.add(new list_item(R.drawable.venus, "Venus"));
        mlist.add(new list_item(R.drawable.mars, "Mars"));
        mlist.add(new list_item(R.drawable.jupiter, "Jupiter"));
        mlist.add(new list_item(R.drawable.saturn, "Saturn"));
        mlist.add(new list_item(R.drawable.neptune, "Neptune"));
        mlist.add(new list_item(R.drawable.uranus, "Uranus"));
        mlist.add(new list_item(R.drawable.miranda, "Miranda "));
        mlist.add(new list_item(R.drawable.unknown, "Ariel "));
        mlist.add(new list_item(R.drawable.unknown, "Umbriel "));
        mlist.add(new list_item(R.drawable.unknown, "Titania "));
        mlist.add(new list_item(R.drawable.unknown, "Oberon "));
        mlist.add(new list_item(R.drawable.unknown, "Neptune "));
        mlist.add(new list_item(R.drawable.unknown, "Naiad "));
        mlist.add(new list_item(R.drawable.unknown, "Thalassa "));
        mlist.add(new list_item(R.drawable.unknown, "Despina "));
        mlist.add(new list_item(R.drawable.unknown, "Galatea "));
        mlist.add(new list_item(R.drawable.unknown, "Larissa "));
        mlist.add(new list_item(R.drawable.unknown, "Hippocamp "));
        mlist.add(new list_item(R.drawable.unknown, "Proteus "));
        mlist.add(new list_item(R.drawable.unknown, "Triton "));
        mlist.add(new list_item(R.drawable.unknown, "Nereid "));
        mlist.add(new list_item(R.drawable.unknown, "Halimede "));
        mlist.add(new list_item(R.drawable.unknown, "Sao "));
        mlist.add(new list_item(R.drawable.unknown, "Laomedeia "));
        mlist.add(new list_item(R.drawable.unknown, "Neso "));
        mlist.add(new list_item(R.drawable.unknown, "Psamathe "));
        mlist.add(new list_item(R.drawable.unknown, "Pluto "));
        mlist.add(new list_item(R.drawable.unknown, "Charon "));
        mlist.add(new list_item(R.drawable.unknown, "Syx "));
        mlist.add(new list_item(R.drawable.unknown, "Nix "));
        mlist.add(new list_item(R.drawable.unknown, "Kerberos "));
        mlist.add(new list_item(R.drawable.unknown, "Hydra "));


        adapter = new Adapter(this, mlist, arrayPlanet);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Search bar
        EditText editText = (EditText) findViewById(R.id.search_bar);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        editText.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                editText.setAlpha(1.0f);
                return false;
            }
        });
    }
    private void filter(String text) {
        ArrayList<list_item> filteredList = new ArrayList<>();
        for (list_item item : mlist) {
            if (item.getItemName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList,arrayPlanet);
    }


    // LOAD ASSET (JSON FILE) & GET STRING FORMAT FROM IT
    public String LoadData (String inFile){
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

    //CONVERT JSON ARRAY INTO ARRAY OF PLANET & ARRAY OF STRING FOR DISPLAYING.
    public void readJson(){
        try {
            jsonArray = new JSONArray(LoadData("planetdata.json"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayPlanet.add(new Planet(jsonObject));
                arrayList.add(jsonObject.getString("Body"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


package com.lazymail.comp2160project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class PlanetData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_data);

        // set the statue bar background to transparent

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // setup recyclerview with the adapter

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        List<list_item> mlist = new ArrayList<>();
        mlist.add(new list_item(R.drawable.sun, "Sun"));
        mlist.add(new list_item(R.drawable.mercury, "Mercury"));
        mlist.add(new list_item(R.drawable.venus, "Venus"));
        mlist.add(new list_item(R.drawable.mars, "Mars"));
        mlist.add(new list_item(R.drawable.jupiter, "Jupiter"));
        mlist.add(new list_item(R.drawable.saturn, "Saturn"));
        mlist.add(new list_item(R.drawable.neptune, "Neptune"));
        mlist.add(new list_item(R.drawable.uranus, "Uranus"));
        Adapter adapter = new Adapter(this, mlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}


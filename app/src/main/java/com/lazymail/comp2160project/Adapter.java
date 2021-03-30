package com.lazymail.comp2160project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.*;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {
    //Declaring variables.
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Planet> arrayPlanet = new ArrayList<>();
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    JSONParser parser = new JSONParser();
    JSONArray jsonArray;
    Context mContext;
    List<list_item> mData;
    private boolean expanded;
    private String name;
    public Adapter(Context mContext, List<list_item> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.list_item, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.background_img.setImageResource(mData.get(position).getBackground());
        holder.name.setText(mData.get(position).getItemName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView background_img;
        TextView name;

        public myViewHolder(View itemView) {
            super(itemView);
            background_img = itemView.findViewById(R.id.card_background);
            name = itemView.findViewById(R.id.item_name);
        }
    }
}

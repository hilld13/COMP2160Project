package com.lazymail.comp2160project;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.*;
import org.json.simple.parser.JSONParser;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    Context mContext;
    List<list_item> mData;
    List<Planet> planetList;
    private boolean expanded;
    private String name;


    public Adapter(Context mContext, List<list_item> mData, List<Planet> planetList) {
        this.mContext = mContext;
        this.mData = mData;
        this.planetList = planetList;

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
        boolean isExpanded = planetList.get(position).isExpanded();
        holder.expandableLayout.setVisibility((isExpanded ? View.VISIBLE : View.GONE));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout expandableLayout;
        ImageView background_img;
        TextView name;
        Button button;
        public myViewHolder(View itemView) {
            super(itemView);
            background_img = itemView.findViewById(R.id.card_background);
            name = itemView.findViewById(R.id.item_name);
            expandableLayout = itemView.findViewById((R.id.more_info));
            button = (Button) itemView.findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    TextView mass = itemView.findViewById(R.id.mass_planet);
                    mass.setText(planetList.get(getAdapterPosition()).getMass());
                    TextView volume = itemView.findViewById(R.id.volume_planet);
                    volume.setText(planetList.get(getAdapterPosition()).getVolume());
                    TextView gravity = itemView.findViewById(R.id.gravity_planet);
                    gravity.setText(planetList.get(getAdapterPosition()).getGravity());

                    Planet planet = planetList.get(getAdapterPosition());
                    planet.setExpanded(!planet.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}

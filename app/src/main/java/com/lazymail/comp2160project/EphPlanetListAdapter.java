package com.lazymail.comp2160project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class EphPlanetListAdapter extends RecyclerView.Adapter<EphPlanetListAdapter.ViewHolder> {

    private EphPlanetListData[] planetList;

    public EphPlanetListAdapter(EphPlanetListData[] planetList) {
        this.planetList = planetList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.eph_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final EphPlanetListData myListData = planetList[position];
        PersistentVariables persistentVar = new PersistentVariables();
        holder.textView.setText(planetList[position].getPlanetName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),"click on item: "+myListData.getPlanetName(),Toast.LENGTH_LONG).show();
                persistentVar.setPlanet(myListData.getPlanetSweNum());

            }
        });
    }

    @Override
    public int getItemCount() {
        return planetList.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.eph_item);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}

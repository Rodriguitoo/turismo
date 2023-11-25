package com.proyecto.turismoapp.ui.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.turismoapp.R;
import com.proyecto.turismoapp.model.ListLocation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListLocationAdapter extends RecyclerView.Adapter<ListLocationAdapter.ViewHolder> {

    private List<ListLocation> listLocations;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_location_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListLocation location = listLocations.get(position);
        holder.location_name.setText(location.getNameLocation());
        if (!location.getImage().isEmpty()){
            Picasso.with(holder.itemView.getContext()).load(location.getImage()).into(holder.location_image);
        }else {
            Picasso.with(holder.itemView.getContext()).load(R.drawable.no_image).into(holder.location_image);
        }
        holder.location_direction.setText(location.getDirectionLocation());
        holder.location_country.setText(location.getPaisLocation());
        holder.location_description.setText(location.getDescriptions());

        holder.itemView.setOnClickListener(view -> {
            // Crea un Bundle para almacenar los datos de ubicación
            Bundle bundle = new Bundle();
            // Agrega la ubicación al Bundle (reemplaza con tu propia lógica para obtener la ubicación)
            ListLocation locatio = location;
            bundle.putParcelable("ubicacion", (Parcelable) locatio);

            // Navega al fragmento de mapa y pasa el Bundle como argumento
            Navigation.findNavController(view).navigate(R.id.nav_maps, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return listLocations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView location_name;
        private ImageView location_image;
        private TextView location_direction;
        private TextView location_country;
        private TextView location_description;

        public ViewHolder(View itemView) {
            super(itemView);
            location_image = itemView.findViewById(R.id.location_image);
            location_name = itemView.findViewById(R.id.location_name);
            location_direction = itemView.findViewById(R.id.direction_location);
            location_country = itemView.findViewById(R.id.country_location);
            location_description = itemView.findViewById(R.id.description_location);
        }
    }
    public void setData(List<ListLocation> data) {
        this.listLocations = data;
        notifyDataSetChanged();
    }
}

package com.proyecto.turismoapp.ui.fragments.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.proyecto.turismoapp.R;
import com.proyecto.turismoapp.databinding.FragmentMapsBinding;
import com.proyecto.turismoapp.model.ListLocation;
import com.squareup.picasso.Picasso;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private FragmentMapsBinding fragmentMapsBinding;
    private GoogleMap mMap;
    double latitud;
    double longitud;
    String imageLocation;
    String description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtén los argumentos pasados desde el fragmento anterior
        Bundle args = getArguments();
        if (args != null) {
            // Recupera la ubicación del Bundle
            ListLocation location = args.getParcelable("ubicacion");

            // Se puede hacer lo que se necesite con la ubicación en este fragmento
            if (location != null) {
                latitud = Double.parseDouble(location.getLatitudeLocation());
                longitud = Double.parseDouble(location.getLongitudeLocation());
                imageLocation = location.getImage();
                description = location.getDescriptions();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMapsBinding = FragmentMapsBinding.inflate(inflater, container, false);
        // Obtén una referencia al SupportMapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        // Inicializa el mapa de manera asincrónica
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return fragmentMapsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!imageLocation.isEmpty()){
            Picasso.with(requireActivity()).load(imageLocation).into(fragmentMapsBinding.imageView);
        }
        fragmentMapsBinding.description.setText(description);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Crear una instancia de LatLng
        LatLng ubicacion = new LatLng(latitud, longitud);

        // Agregar un marcador en la ubicación
        mMap.addMarker(new MarkerOptions().position(ubicacion).title("Marcador en ubicación"));

        // Mover la cámara a la ubicación y aplicar zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
        // Verificar y solicitar permisos de ubicación en tiempo de ejecución
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Si no se tienen los permisos, solicitarlos al usuario
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Si se tienen los permisos, habilitar la ubicación en el mapa, etc.
            mMap.setMyLocationEnabled(true);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso de ubicación concedido, habilitar la ubicación en el mapa, etc.
                if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                       return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                // Permiso de ubicación denegado, manejarlo según tus necesidades
            }
        }
    }
}
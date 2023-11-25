package com.proyecto.turismoapp.ui.fragments.home;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.turismoapp.ui.adapter.ListLocationAdapter;
import com.proyecto.turismoapp.databinding.FragmentHomeBinding;
import com.proyecto.turismoapp.model.ListLocation;
import com.proyecto.turismoapp.provider.ClientProvider;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding fragmentHomeBinding;
    private ListLocationAdapter listLocationAdapter;
    private ClientProvider clientProvider;
    List<ListLocation> locationList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(getLayoutInflater(),container,false);
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clientProvider = new ClientProvider();
        listLocationAdapter = new ListLocationAdapter();
        mProgressDialog = new ProgressDialog(requireContext());
        getLocations();
    }

    private void getLocations() {
        mProgressDialog.setMessage("Cargando...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        clientProvider.getLocations().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Limpiar la lista antes de agregar nuevos elementos
                locationList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    ListLocation location = ds.getValue(ListLocation.class);
                    if (location != null) {
                        locationList.add(location); // Agregar ubicaciones a la lista
                    }
                }

                // Pasar la lista de ubicaciones al adaptador
                listLocationAdapter.setData(locationList);

                // Configurar el RecyclerView con el adaptador después de obtener los datos
                fragmentHomeBinding.recyclerView.setAdapter(listLocationAdapter);

                if (!locationList.isEmpty()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mProgressDialog.dismiss();
            }
        });

    }
}
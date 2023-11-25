package com.proyecto.turismoapp.ui.fragments.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.turismoapp.databinding.FragmentProfileBinding;
import com.proyecto.turismoapp.model.Users;
import com.proyecto.turismoapp.provider.AuthProvider;
import com.proyecto.turismoapp.provider.ClientProvider;
import com.proyecto.turismoapp.ui.activities.login.MainActivity;

public class ProfileFragment extends Fragment {
    private ClientProvider clientProvider;
    private AuthProvider authProvider;
    private FragmentProfileBinding profileBinding;
    private ProgressDialog mProgressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileBinding = FragmentProfileBinding.inflate(getLayoutInflater(),container,false);
        return profileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authProvider = new AuthProvider();
        clientProvider = new ClientProvider();
        mProgressDialog = new ProgressDialog(requireContext());
        getUserData();
        profileBinding.btnUpdate.setOnClickListener(view1 -> {
            updateData();
        });
        profileBinding.btnDelete.setOnClickListener(view1 -> {
            deleteAccount();
        });
    }

    private void deleteAccount() {
        mProgressDialog.setMessage("Eliminando...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        clientProvider.removeDataUser(authProvider.getId()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    removeUserData();
                }else {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    private void removeUserData() {
        FirebaseUser user = authProvider.getCurrentUser();
        if (user!=null){
            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(requireContext(), "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(requireContext(), MainActivity.class));
                        requireActivity().finish();
                        mProgressDialog.dismiss();
                    } else {
                        Toast.makeText(requireContext(), "Error al eliminar el usuario " +task.getException(), Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }
                }
            });
        }else {
            Toast.makeText(requireContext(), "No se pudo encontrar un usuario autenticado", Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();
        }
    }

    private void updateData() {
        mProgressDialog.setMessage("Actualizando...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        clientProvider.update(authProvider.getId(),profileBinding.firstname.getText().toString(),
                profileBinding.lastname.getText().toString(),profileBinding.password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(requireContext(), "Los datos han sido actualizados correctamente", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }else {
                    Toast.makeText(requireContext(), "Error al actualizar los datos " + task.getException(), Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    private void getUserData() {
        clientProvider.getUser(authProvider.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Users users = snapshot.getValue(Users.class);
                    assert users != null;
                    profileBinding.firstname.setText(users.getFirstname());
                    profileBinding.lastname.setText(users.getLastname());
                    profileBinding.tvEmail.setText(users.getEmail());
                    profileBinding.password.setText(users.getPassword());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
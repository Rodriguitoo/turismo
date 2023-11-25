package com.proyecto.turismoapp.provider;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.proyecto.turismoapp.model.ListLocation;
import com.proyecto.turismoapp.model.Users;

import java.util.HashMap;
import java.util.Map;

public class ClientProvider {
    DatabaseReference database;
    DatabaseReference databaseLocation;

    public ClientProvider() {
        database = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseLocation = FirebaseDatabase.getInstance().getReference().child("Locations");
    }

    public Task<Void> create(Users users){
        return database.child(users.getIdUser()).setValue(users);
    }

    public Task<Void> createLocation(ListLocation listLocation){
        return databaseLocation.push().setValue(listLocation);
    }
    public DatabaseReference getUser(String idUser){
        return database.child(idUser);
    }
    public DatabaseReference getLocations(){
        return databaseLocation;
    }

      public Task<Void> update(String idUser,String firstName,String lastName,String password){
        Map<String,Object> map = new HashMap<>();
        map.put("firstname",firstName);
        map.put("lastname",lastName);
        map.put("password",password);
        map.put("password2",password);
        return database.child(idUser).updateChildren(map);
    }

    public Task<Void> removeDataUser(String idUser){
      return database.child(idUser).removeValue();
    }

}




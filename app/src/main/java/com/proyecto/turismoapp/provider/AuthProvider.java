package com.proyecto.turismoapp.provider;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthProvider {
    FirebaseAuth mAuth;

    public AuthProvider() {
        mAuth = FirebaseAuth.getInstance();
    }
    public Task<AuthResult> register(String email, String password) {
        return mAuth.createUserWithEmailAndPassword(email, password);
    }
    public Task<AuthResult> login(String email, String password){
        return mAuth.signInWithEmailAndPassword(email , password);
    }
    public void logout(){
        mAuth.signOut();
    }
    public String getId() {return mAuth.getCurrentUser().getUid();}
    public FirebaseUser getCurrentUser() {return mAuth.getCurrentUser();}
}

// primero se deben tener las importaciones de fire base y google task
// Luego se declara la clase y se instancia un objeto de la clase, que es para interactuari con firebase
// Los parametros son para un usuario unico

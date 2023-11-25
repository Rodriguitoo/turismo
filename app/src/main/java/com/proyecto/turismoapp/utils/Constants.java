package com.proyecto.turismoapp.utils;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Constants {

    //Validacion regex para los campos
    public  boolean validateLetter(String datos) {
        return datos.matches("[a-zA-Z-ñÑ ]*");
    }

    //Validacion Patterns para el campo email
    public boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}

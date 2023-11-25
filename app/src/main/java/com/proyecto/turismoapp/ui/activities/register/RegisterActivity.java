package com.proyecto.turismoapp.ui.activities.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.proyecto.turismoapp.R;
import com.proyecto.turismoapp.ui.activities.login.MainActivity;
import com.proyecto.turismoapp.databinding.ActivityRegisterBinding;
import com.proyecto.turismoapp.model.ListLocation;
import com.proyecto.turismoapp.model.Users;
import com.proyecto.turismoapp.provider.AuthProvider;
import com.proyecto.turismoapp.provider.ClientProvider;
import com.proyecto.turismoapp.utils.Constants;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private ProgressDialog mProgressDialog;
    private AuthProvider mAuthProvider;
    private Constants mConstants;
    private ClientProvider mClientProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mProgressDialog = new ProgressDialog(this);
        mAuthProvider = new AuthProvider();
        mConstants = new Constants();
        mClientProvider = new ClientProvider();
        binding.btnRegister.setOnClickListener(view -> {
            validateOnclickButton();
        });
        validateRealTime();
        //Activarlo solo cuando quiero agregar un nuevo lugar
       /* ListLocation listLocation = new ListLocation("https://lh3.googleusercontent.com/p/AF1QipNbtj8sTicO8q4j1adu_FnPFXWilE4_GjZazMLD=s1360-w1360-h1020","Cruz del Tercer Milenio","Tte Merino 32, Coquimbo","Chile","-29.9518215","-71.347114","La Cruz del Tercer Milenio es un monumento conmemorativo religioso ubicado en el cerro El Vigía de Coquimbo, Chile. Fue construido con el motivo del jubileo del año 2000 de la Iglesia católica. Su construcción fue iniciada en 1999 y terminada en 2000.");
        RegisterLocations(listLocation);*/
    }

    private void validateOnclickButton() {
        if (binding.firstname.getText().toString().isEmpty()) {
            binding.layoutfirstname.setHelperText(getString(R.string.error_empty));
            binding.btnRegister.setEnabled(false);
        }else if (!mConstants.validateLetter(binding.firstname.getText().toString())){
            binding.layoutfirstname.setHelperText(getString(R.string.only_letter));
        }else  if (binding.lastname.getText().toString().isEmpty()) {
            binding.layoutlastname.setHelperText(getString(R.string.error_empty));
            binding.btnRegister.setEnabled(false);
        } else if (!mConstants.validateLetter(binding.lastname.getText().toString())){
            binding.layoutlastname.setHelperText(getString(R.string.only_letter));
        }else if (binding.emaillogin.getText().toString().isEmpty()){
            binding.layoutemail.setHelperText(getString(R.string.error_empty));
            binding.btnRegister.setEnabled(false);
        }else if (!mConstants.validateEmail(binding.emaillogin.getText().toString())){
            binding.layoutemail.setHelperText(getString(R.string.invalid_email));
            binding.btnRegister.setEnabled(false);
        }else if (binding.password.getText().toString().isEmpty()){
            binding.layoutpassword.setHelperText(getString(R.string.error_empty));
            binding.btnRegister.setEnabled(false);
        }else if (binding.password.getText().toString().length()<6){
            binding.layoutpassword.setHelperText(getString(R.string.short_passord));
            binding.btnRegister.setEnabled(false);
        }else  if (binding.confirmpassword.getText().toString().isEmpty()){
            binding.layoutconfirmpassword.setHelperText(getString(R.string.error_empty));
            binding.btnRegister.setEnabled(false);
        }else if (binding.confirmpassword.getText().toString().length()<6){
            binding.layoutconfirmpassword.setHelperText(getString(R.string.short_passord));
        }else if (!binding.confirmpassword.getText().toString().equals(binding.password.getText().toString())){
            binding.layoutconfirmpassword.setHelperText(getString(R.string.no_match_password));
        }else {
            binding.btnRegister.setEnabled(true);
            binding.layoutfirstname.setHelperText("");
            binding.layoutlastname.setHelperText("");
            binding.layoutemail.setHelperText("");
            binding.layoutpassword.setHelperText("");
            binding.layoutconfirmpassword.setHelperText("");
            clickRegister(binding.firstname.getText().toString(),binding.lastname.getText().toString(),
                    binding.emaillogin.getText().toString(),binding.password.getText().toString(), binding.confirmpassword.getText().toString());
        }

    }

    private void validateRealTime() {
        binding.firstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.firstname.getText().toString().isEmpty()) {
                    binding.layoutfirstname.setHelperText(getString(R.string.error_empty));
                    binding.btnRegister.setEnabled(false);
                }else if (!mConstants.validateLetter(binding.firstname.getText().toString())){
                    binding.layoutfirstname.setHelperText(getString(R.string.only_letter));
                }
                else {
                    binding.btnRegister.setEnabled(true);
                    binding.layoutfirstname.setHelperText("");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.lastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.lastname.getText().toString().isEmpty()) {
                    binding.layoutlastname.setHelperText(getString(R.string.error_empty));
                    binding.btnRegister.setEnabled(false);
                } else if (!mConstants.validateLetter(binding.lastname.getText().toString())){
                    binding.layoutlastname.setHelperText(getString(R.string.only_letter));
                }
                else {
                    binding.btnRegister.setEnabled(true);
                    binding.layoutlastname.setHelperText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.emaillogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.emaillogin.getText().toString().isEmpty()){
                    binding.layoutemail.setHelperText(getString(R.string.error_empty));
                    binding.btnRegister.setEnabled(false);
                }else if (!mConstants.validateEmail(binding.emaillogin.getText().toString())){
                    binding.layoutemail.setHelperText(getString(R.string.invalid_email));
                    binding.btnRegister.setEnabled(false);
                }
                else {
                    binding.layoutemail.setHelperText("");
                    binding.btnRegister.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.password.getText().toString().isEmpty()){
                    binding.layoutpassword.setHelperText(getString(R.string.error_empty));
                    binding.btnRegister.setEnabled(false);
                }else if (binding.password.getText().toString().length()<6){
                    binding.layoutpassword.setHelperText(getString(R.string.short_passord));
                    binding.btnRegister.setEnabled(false);
                }
                else {
                    binding.layoutpassword.setHelperText("");
                    binding.btnRegister.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.confirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.confirmpassword.getText().toString().isEmpty()){
                    binding.layoutconfirmpassword.setHelperText(getString(R.string.error_empty));
                    binding.btnRegister.setEnabled(false);
                }else if (binding.confirmpassword.getText().toString().length()<6){
                    binding.layoutconfirmpassword.setHelperText(getString(R.string.short_passord));
                }else if (!binding.confirmpassword.getText().toString().equals(binding.password.getText().toString())){
                    binding.layoutconfirmpassword.setHelperText(getString(R.string.no_match_password));
                }else {
                    binding.layoutconfirmpassword.setHelperText("");
                    binding.btnRegister.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void clickRegister(String firstname, String lastname, String email, String password, String confirmpassword) {
        mProgressDialog.setMessage("Cargando...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        registers(firstname,lastname,email,password,confirmpassword);
    }

    private void registers(String firstname, String lastname, String email, String password, String confirmpassword) {
        mAuthProvider.register(email, password).addOnCompleteListener(task -> {
            mProgressDialog.dismiss();
            if (task.isSuccessful()) {
                String id =(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())).getUid();
                Users users = new Users(id,firstname,lastname,email,password,confirmpassword,"");
                RegisterClient(users);
            }else {
                Toast.makeText(this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void RegisterClient(Users users) {
        mClientProvider.create(users).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                mAuthProvider.logout();
            }else {
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void RegisterLocations(ListLocation listLocation) {
        mClientProvider.createLocation(listLocation).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
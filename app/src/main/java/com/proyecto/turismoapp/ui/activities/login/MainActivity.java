package com.proyecto.turismoapp.ui.activities.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.proyecto.turismoapp.R;
import com.proyecto.turismoapp.ui.activities.Home.HomeActivity;
import com.proyecto.turismoapp.ui.activities.register.RegisterActivity;
import com.proyecto.turismoapp.databinding.ActivityMainBinding;
import com.proyecto.turismoapp.provider.AuthProvider;
import com.proyecto.turismoapp.utils.Constants;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Constants mConstant;
    private ProgressDialog mDialog;
    private AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mConstant = new Constants();
        mDialog = new ProgressDialog(this);
        mAuthProvider = new AuthProvider();
        validateRealTime();
        binding.btnLogin.setOnClickListener(view->{
            validateLogin();
        });
        goToRegister();
    }

    private void validateLogin() {
        if (binding.emaillogin.getText().toString().isEmpty()){
            binding.layoutemaillogin.setHelperText(getString(R.string.error_empty));
            binding.btnLogin.setEnabled(false);
        }else if (!mConstant.validateEmail(binding.emaillogin.getText().toString())){
            binding.layoutemaillogin.setHelperText(getString(R.string.invalid_email));
            binding.btnLogin.setEnabled(false);
        }else if (binding.passwordlogin.getText().toString().isEmpty()){
            binding.layoutpasswordlogin.setHelperText(getString(R.string.error_empty));
            binding.btnLogin.setEnabled(false);
        }else if (binding.passwordlogin.getText().toString().length()<6){
            binding.layoutpasswordlogin.setHelperText(getString(R.string.short_passord));
            binding.btnLogin.setEnabled(false);
        }else {
            binding.layoutpasswordlogin.setHelperText("");
            binding.layoutemaillogin.setHelperText("");
            binding.btnLogin.setEnabled(true);
            LoginUser(binding.emaillogin.getText().toString(),binding.passwordlogin.getText().toString());
        }
    }

    private void LoginUser(String email, String password) {
        //noinspection deprecation
        mDialog.setMessage("Cargando...");
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mAuthProvider.login(email, password).addOnCompleteListener(task->{
            if (task.isSuccessful()){
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                mDialog.dismiss();
            }else {
                mDialog.dismiss();
                Toast.makeText(this, "Correo electronico o contraseña no se existe", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validateRealTime() {
        binding.emaillogin.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.emaillogin.getText().toString().isEmpty()){
                    binding.layoutemaillogin.setHelperText(getString(R.string.error_empty));
                    binding.btnLogin.setEnabled(false);
                }else if (!mConstant.validateEmail(binding.emaillogin.getText().toString())){
                    binding.layoutemaillogin.setHelperText(getString(R.string.invalid_email));
                    binding.btnLogin.setEnabled(false);
                }else {
                    binding.layoutemaillogin.setHelperText("");
                    binding.btnLogin.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.passwordlogin.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.passwordlogin.getText().toString().isEmpty()){
                    binding.layoutpasswordlogin.setHelperText(getString(R.string.error_empty));
                    binding.btnLogin.setEnabled(false);
                }else if (binding.passwordlogin.getText().toString().length()<6){
                    binding.layoutpasswordlogin.setHelperText(getString(R.string.short_passord));
                    binding.btnLogin.setEnabled(false);
                }
                else {
                    binding.layoutpasswordlogin.setHelperText("");
                    binding.btnLogin.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void goToRegister(){
        binding.gotoregister.setOnClickListener(view->{
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}


package com.proyecto.turismoapp.ui.activities.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.proyecto.turismoapp.R;
import com.proyecto.turismoapp.databinding.ActivityHomeBinding;
import com.proyecto.turismoapp.provider.AuthProvider;
import com.proyecto.turismoapp.ui.activities.login.MainActivity;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private AuthProvider authProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        drawerLayout = findViewById(R.id.drawer_layout);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        authProvider = new AuthProvider();

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setDrawerLayout(drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController((NavigationView) findViewById(R.id.nav_view), navController);
        // Configura el NavigationItemSelectedListener para manejar clics en elementos del menú
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {

            } else if (itemId == R.id.nav_profile) {
                 navController.navigate(R.id.nav_profile);
            }else {
              authProvider.logout();
              startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }

            // Cierra el cajón de navegación después de hacer clic en un elemento
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_lateral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.nav_acercade){
            navController.navigate(R.id.acercadeFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
    }
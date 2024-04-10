package com.salmantino.suite;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.salmantino.suite.databinding.ActivityMainBinding;
import com.salmantino.suite.javi.JaviActividad;
import com.salmantino.suite.jesus.JesusActividad;
import com.salmantino.suite.juanCarlos.JcActividad;

public class MainActivity extends AppCompatActivity implements ComunicaMenu {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar la vista de la actividad utilizando View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtener una referencia al BottomNavigationView
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Configurar los destinos de nivel superior para la barra de aplicaciones
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_jesus, R.id.navigation_juanCarlos, R.id.navigation_javier)
                .build();

        // Obtener el controlador de navegación
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // Configurar la barra de acción para trabajar con el controlador de navegación
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Configurar el BottomNavigationView para trabajar con el controlador de navegación
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public void jcFragmentMenu(int queBoton) {
        Intent in = new Intent(this, JcActividad.class);
        in.putExtra("BOTONPULSADO", queBoton);

        startActivity(in);
    }

    @Override
    public void javiFragmentMenu(int queBoton) {
        Intent in = new Intent(this, JaviActividad.class);
        in.putExtra("BOTONPULSADO", queBoton);

        startActivity(in);
    }

    @Override
    public void jesusFragmentMenu(int queBoton) {
        Intent in = new Intent(this, JesusActividad.class);
        in.putExtra("BOTONPULSADO", queBoton);

        startActivity(in);
    }
}



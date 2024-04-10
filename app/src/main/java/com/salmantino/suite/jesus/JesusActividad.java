package com.salmantino.suite.jesus;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.salmantino.suite.ComunicaMenu;
import com.salmantino.suite.R;

public class JesusActividad extends AppCompatActivity implements ComunicaMenu {

    private Fragment[] misFragmentos;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_jesus);

        misFragmentos = new Fragment[3];

        // Los fragmentos correspondientes a vibración, GPS y huella dactilar
        misFragmentos[0] = new LinternaFragment(); // Ajuste de índice a 0
        misFragmentos[1] = new PresionFragment(); // Ajuste de índice a 1
        misFragmentos[2] = new TemperaturaFragment();  // Ajuste de índice a 2

        // Extraer la información del botón pulsado desde el Intent
        Bundle extras = getIntent().getExtras();
        int botonPulsado = extras != null ? extras.getInt("BOTONPULSADO", 0) : 0;

        // Mostrar el fragmento correspondiente al botón pulsado
        jesusFragmentMenu(botonPulsado);

        // Log de depuración
        Log.d("JesusActividad", "onCreate llamado");
    }

    @Override
    public void jcFragmentMenu(int queBoton) {

    }

    @Override
    public void javiFragmentMenu(int queBoton) {

    }

    @Override
    public void jesusFragmentMenu(int queBoton) {
        FragmentManager miManejador = getSupportFragmentManager();
        FragmentTransaction miTransaccion = miManejador.beginTransaction();

        // No es necesario un fragmento especial para el menú iluminado si estás utilizando un nuevo diseño

        // Reemplazar el fragmento de herramientas con el correspondiente al botón pulsado
        miTransaccion.replace(R.id.fragment_container_jesus, misFragmentos[queBoton]);

        miTransaccion.commit();
        Log.d("JesusActividad", "JesusFragmentMenu llamado con: " + queBoton);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}




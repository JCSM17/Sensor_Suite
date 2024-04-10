// Importaciones necesarias para la actividad
package com.salmantino.suite.juanCarlos;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

// Importaciones de AndroidX para compatibilidad con fragmentos y AppCompatActivity
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

// Importaciones de clases personalizadas
import com.salmantino.suite.ComunicaMenu;
import com.salmantino.suite.R;

// Declaración de la clase JcActividad, que extiende AppCompatActivity e implementa ComunicaMenu
public class JcActividad extends AppCompatActivity implements ComunicaMenu {

    // Arreglo para almacenar instancias de fragmentos correspondientes a vibración, GPS y huella dactilar
    private Fragment[] misFragmentos;

    // Método llamado al crear la actividad
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Llamada al método onCreate de la superclase
        super.onCreate(savedInstanceState);

        // Establecer el diseño de la actividad desde el archivo XML
        setContentView(R.layout.fragment_juancarlos);

        // Inicializar el arreglo de fragmentos
        misFragmentos = new Fragment[3];

        // Crear instancias de los fragmentos correspondientes a vibración, GPS y huella dactilar
        misFragmentos[0] = new VibradorFragment();  // Ajuste de índice a 0
        misFragmentos[1] = new GPSFragment();  // Ajuste de índice a 1
        misFragmentos[2] = new HuellaFragment();  // Ajuste de índice a 2

        // Extraer la información del botón pulsado desde el Intent
        Bundle extras = getIntent().getExtras();
        int botonPulsado = extras != null ? extras.getInt("BOTONPULSADO", 0) : 0;

        // Mostrar el fragmento correspondiente al botón pulsado
        jcFragmentMenu(botonPulsado);

        // Log de depuración
        Log.d("JcActividad", "onCreate llamado");
    }

    // Método implementado desde la interfaz ComunicaMenu
    @Override
    public void jcFragmentMenu(int queBoton) {
        // Obtener el manejador de fragmentos
        FragmentManager miManejador = getSupportFragmentManager();

        // Iniciar una transacción de fragmentos
        FragmentTransaction miTransaccion = miManejador.beginTransaction();

        // No es necesario un fragmento especial para el menú iluminado si estás utilizando un nuevo diseño

        // Reemplazar el fragmento de herramientas con el correspondiente al botón pulsado
        miTransaccion.replace(R.id.fragment_container_jc, misFragmentos[queBoton]);

        // Confirmar la transacción
        miTransaccion.commit();

        // Log de depuración
        Log.d("JcActividad", "JcFragmentMenu llamado con: " + queBoton);
    }

    @Override
    public void javiFragmentMenu(int queBoton) {

    }

    @Override
    public void jesusFragmentMenu(int queBoton) {

    }

    // Método llamado cuando se presiona el botón de retroceso
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Mover la tarea a segundo plano al presionar el botón de retroceso
        moveTaskToBack(true);
    }
}

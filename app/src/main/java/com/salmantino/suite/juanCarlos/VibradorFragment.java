// Importaciones necesarias para el fragmento
package com.salmantino.suite.juanCarlos;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.salmantino.suite.R;
import com.salmantino.suite.UtilidadAnimacionBoton;

import java.util.Random;

// Declaración de la clase VibradorFragment, que extiende Fragment
public class VibradorFragment extends Fragment {

    private ImageButton vibrarButton;
    private Vibrator vibrator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_vibracion, container, false);

        // Obtener referencia al botón de vibración y establecer un escuchador de clic
        vibrarButton = view.findViewById(R.id.btnVibrar);
        vibrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Método llamado cuando se hace clic en el botón de vibración
                onVibrarClick();
            }
        });

        // Obtener referencia al botón de retroceso y establecer un escuchador de clic con expresión lambda
        ImageButton atrasButton = view.findViewById(R.id.btnAtrasVibrar);
        atrasButton.setOnClickListener(v -> {
            // Iluminar temporalmente el botón "Atrás"
            UtilidadAnimacionBoton.iluminarBoton(atrasButton, R.drawable.on_salir);  // Cambiar a la imagen iluminada
            // Luego, ir a la pantalla de inicio
            irAPantallaDeInicio();
        });

        // Devolver la vista inflada
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Configurar el servicio del vibrador
        vibrator = (Vibrator) requireContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void onVibrarClick() {
        // Registrar un mensaje de depuración en el registro
        Log.d("VibradorFragment", "Botón de vibración clicado");

        // Verificar si el servicio de vibración está disponible
        if (vibrator != null) {
            // Generar un valor aleatorio entre 0 y 10 segundos
            int tiempoAleatorioEnSegundos = generarTiempoAleatorio(10); // Duración máxima de 10 segundos

            // Mostrar un Toast con la duración de la vibración en segundos
            mostrarDuracionVibracion(tiempoAleatorioEnSegundos);

            // Convertir la duración de segundos a milisegundos
            int tiempoAleatorioEnMilisegundos = tiempoAleatorioEnSegundos * 1000;

            // Verificar la versión de Android para utilizar el método adecuado
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(tiempoAleatorioEnMilisegundos, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(tiempoAleatorioEnMilisegundos);
            }
        } else {
            // Registrar un mensaje de error si el servicio de vibración no está disponible
            Log.e("VibradorFragment", "El servicio de vibración no está disponible");
        }
    }

    private int generarTiempoAleatorio(int maximo) {
        // Generar un valor aleatorio entre 0 y maximo
        Random random = new Random();
        return random.nextInt(maximo) + 1; // Se suma 1 para evitar una duración de 0 segundos
    }

    private void mostrarDuracionVibracion(int duracionEnSegundos) {
        // Mostrar un Toast con la duración de la vibración en segundos
        Toast.makeText(requireContext(), "Duración de vibración: " + duracionEnSegundos + " segundos", Toast.LENGTH_SHORT).show();
    }

    private void irAPantallaDeInicio() {
        // Lógica para manejar el clic en el botón de retroceso
        requireActivity().finish();
    }
}

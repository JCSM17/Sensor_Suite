package com.salmantino.suite.javi;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.salmantino.suite.R;
import com.salmantino.suite.UtilidadAnimacionBoton;

public class AcelerometroFragment extends Fragment {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private int whip = 0;
    private ImageButton atrasButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acelerometro, container, false);

        // Inicializar sensor y sensor manager
        sensorManager = (SensorManager) requireActivity().getSystemService(requireActivity().SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        atrasButton = view.findViewById(R.id.btnAtrasAcelerometro);

        // Verificar si el dispositivo tiene acelerómetro
        if (sensor == null) {
            Toast.makeText(requireContext(), "Este dispositivo no tiene acelerómetro", Toast.LENGTH_SHORT).show();
            requireActivity().finish();
        }

        // Configurar el listener del botón de atrás
        atrasButton.setOnClickListener(v -> {
            // Iluminar temporalmente el botón "Atrás"
            UtilidadAnimacionBoton.iluminarBoton(atrasButton, R.drawable.on_salir);  // Cambiar a la imagen iluminada
            // Luego, ir a la pantalla de inicio
            irAPantallaDeInicio();
        });

        // Configurar el listener del sensor
        sensorEventListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                System.out.println("valor giro" + x);

                if (x < -5 && whip == 0) {
                    whip++;
                    requireActivity().getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                } else if (x > 5 && whip == 1) {
                    whip++;
                    requireActivity().getWindow().getDecorView().setBackgroundColor(Color.RED);
                }

                if (whip == 2) {
                    sound();
                    whip = 0;
                }
            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                // No es necesario implementar algo aquí
            }
        };

        // Iniciar la escucha del sensor
        start();

        return view;
    }

    // Método para reproducir el sonido
    private void sound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(requireContext(), R.raw.latigo_sheldon_cooper_big_bang_theory);
        mediaPlayer.start();
    }

    // Método para iniciar la escucha del sensor
    private void start() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Método para detener la escucha del sensor
    private void stop() {
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    public void onPause() {
        stop();
        super.onPause();
    }

    @Override
    public void onResume() {
        start();
        super.onResume();
    }

    private void irAPantallaDeInicio() {
        requireActivity().finish();
    }
}

package com.salmantino.suite.jesus;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.salmantino.suite.UtilidadAnimacionBoton;

import com.salmantino.suite.R;

public class TemperaturaFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private TextView temperatureTextView;
    private ImageButton btnAtrasTemperatura;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        return inflater.inflate(R.layout.fragment_temperatura, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener referencias a las vistas
        temperatureTextView = view.findViewById(R.id.temperatureTextView);
        btnAtrasTemperatura = view.findViewById(R.id.btnAtrasTemperatura);

        // Configurar el listener del botón de atrás
        btnAtrasTemperatura.setOnClickListener(v -> {
            // Iluminar temporalmente el botón "Atrás"
            UtilidadAnimacionBoton.iluminarBoton(btnAtrasTemperatura, R.drawable.on_salir);
            // Luego, ir a la pantalla de inicio
            irAPantallaDeInicio();
        });

        // Obtén el servicio del sensor
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);

        // Verifica si el dispositivo tiene un sensor de temperatura
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        } else {
            // Manejar el caso en el que el dispositivo no tiene un sensor de temperatura
            temperatureTextView.setText("Este dispositivo no tiene un sensor de temperatura.");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Registra el listener del sensor cuando la actividad está en primer plano
        if (temperatureSensor != null) {
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Desregistra el listener del sensor cuando la actividad está en segundo plano
        if (temperatureSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Actualiza la interfaz de usuario con el valor del sensor de temperatura
        float temperatureValue = event.values[0];
        temperatureTextView.setText("Temperatura: " + temperatureValue + " °C");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Método requerido pero no utilizado en este ejemplo
    }

    private void irAPantallaDeInicio() {
        requireActivity().finish();
    }
}
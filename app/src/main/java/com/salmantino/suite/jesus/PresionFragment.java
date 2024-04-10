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

import androidx.fragment.app.Fragment;

import com.salmantino.suite.R;
import com.salmantino.suite.UtilidadAnimacionBoton;

import java.util.Objects;

public class PresionFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor pressureSensor;
    private TextView presionTextView;
    private ImageButton btnAtrasPresion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_presion, container, false);

        presionTextView = view.findViewById(R.id.presionTextView);
        btnAtrasPresion = view.findViewById(R.id.btnAtrasPresion);

        // Configurar el listener del botón de atrás
        btnAtrasPresion.setOnClickListener(v -> {
            // Iluminar temporalmente el botón "Atrás"
            UtilidadAnimacionBoton.iluminarBoton(btnAtrasPresion, R.drawable.on_salir);  // Cambiar a la imagen iluminada
            // Luego, ir a la pantalla de inicio
            irAPantallaDeInicio();
        });

        sensorManager = (SensorManager) Objects.requireNonNull(requireContext().getSystemService(Context.SENSOR_SERVICE));

        if ((pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)) == null) {
            presionTextView.setText("Este dispositivo no tiene un sensor de presión.");
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pressureSensor != null) {
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (pressureSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float pressureValue = event.values[0];
        presionTextView.setText(String.format("Presión: %.2f hPa", pressureValue));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No necesitas implementar esto para el sensor de presión
    }

    private void irAPantallaDeInicio() {
        requireActivity().finish();
    }
}

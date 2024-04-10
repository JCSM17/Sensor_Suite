package com.salmantino.suite.javi;

import android.content.Context;
import android.content.pm.ActivityInfo;
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

public class GiroscopioFragment extends Fragment {

    private TextView textX, textY, textZ;
    private SensorManager sensorManager;
    private Sensor sensor;
    private ImageButton atrasButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giroscopio, container, false);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        textX = view.findViewById(R.id.tvX);
        textY = view.findViewById(R.id.tvY);
        textZ = view.findViewById(R.id.tvZ);
        atrasButton = view.findViewById(R.id.btnAtrasGiroscopio);

        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        // Configurar el listener del botón de atrás
        atrasButton.setOnClickListener(v -> {
            // Iluminar temporalmente el botón "Atrás"
            UtilidadAnimacionBoton.iluminarBoton(atrasButton, R.drawable.on_salir);  // Cambiar a la imagen iluminada
            // Luego, ir a la pantalla de inicio
            irAPantallaDeInicio();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(gyroListener);
    }

    private final SensorEventListener gyroListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Con el (int) es para que muestre los números enteros en vez de decimales
            textX.setText("X: " + x);
            textY.setText("Y: " + y);
            textZ.setText("Z: " + z);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            // No es necesario implementar algo aquí
        }
    };

    private void irAPantallaDeInicio() {
        requireActivity().finish();
    }
}

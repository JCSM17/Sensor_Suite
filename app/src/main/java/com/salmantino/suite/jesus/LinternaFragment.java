package com.salmantino.suite.jesus;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.salmantino.suite.R;
import com.salmantino.suite.UtilidadAnimacionBoton;

public class LinternaFragment extends Fragment {

    private CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashOn = false;
    private ImageButton btnAtrasLinterna;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_linterna, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cameraManager = (CameraManager) requireActivity().getSystemService(Context.CAMERA_SERVICE);

        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        btnAtrasLinterna = view.findViewById(R.id.btnAtrasLinterna);

        // Configurar el listener del botón de atrás
        btnAtrasLinterna.setOnClickListener(v -> {
            // Iluminar temporalmente el botón "Atrás"
            UtilidadAnimacionBoton.iluminarBoton(btnAtrasLinterna, R.drawable.on_salir);  // Cambiar a la imagen iluminada
            // Luego, ir a la pantalla de inicio
            irAPantallaDeInicio();
        });

        ImageButton toggleFlashButton = view.findViewById(R.id.toggleFlashButton);
        toggleFlashButton.setOnClickListener(v -> toggleFlash());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void toggleFlash() {
        try {
            if (isFlashOn) {
                turnOffFlash();
            } else {
                turnOnFlash();
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void turnOnFlash() throws CameraAccessException {
        if (isCameraFlashAvailable()) {
            cameraManager.setTorchMode(cameraId, true);
            isFlashOn = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void turnOffFlash() throws CameraAccessException {
        if (isCameraFlashAvailable()) {
            cameraManager.setTorchMode(cameraId, false);
            isFlashOn = false;
        }
    }

    private boolean isCameraFlashAvailable() {
        return requireContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    private void irAPantallaDeInicio() {
        requireActivity().finish();
    }
}

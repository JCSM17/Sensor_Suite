package com.salmantino.suite.juanCarlos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.salmantino.suite.R;
import com.salmantino.suite.UtilidadAnimacionBoton;

import java.util.concurrent.Executor;

import javax.crypto.Cipher;

public class HuellaFragment extends Fragment {

    private static final int REQUEST_PERMISSION_USE_BIOMETRIC = 1;
    private ImageButton atrasButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_huella, container, false);

        // Verificar y solicitar permisos para el uso del sensor de huellas
        checkBiometricPermission();

        // Asignar el listener al botón "Obtener Huella"
        ImageButton obtenerHuellaButton = view.findViewById(R.id.btnObtenerHuella);
        obtenerHuellaButton.setOnClickListener(v -> onClickObtenerHuella());

        // Referencia al botón "Atrás" y su listener
        atrasButton = view.findViewById(R.id.btnAtrasHuella);
        atrasButton.setOnClickListener(v -> {
            // Iluminar temporalmente el botón "Atrás"
            UtilidadAnimacionBoton.iluminarBoton(atrasButton, R.drawable.on_salir);  // Cambiar a la imagen iluminada
            // Luego, ir a la pantalla de inicio
            irAPantallaDeInicio();
        });

        // Devolver la vista inflada
        return view;
    }

    // Método para verificar y solicitar permisos para el uso del sensor de huellas
    private void checkBiometricPermission() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.USE_BIOMETRIC) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.USE_BIOMETRIC},
                    REQUEST_PERMISSION_USE_BIOMETRIC
            );
        }
    }

    // Método que verifica si el dispositivo es compatible con el sensor de huellas y muestra el diálogo de autenticación
    @RequiresApi(api = Build.VERSION_CODES.P)
    private boolean checkBiometricSupport() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    // Método que muestra el diálogo de autenticación con huella dactilar
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void showBiometricPrompt() {
        Executor executor = requireActivity().getMainExecutor();
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                showToast("Error de autenticación: " + errString);
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                showToast("Autenticación exitosa");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                showToast("Autenticación fallida");
            }
        };

        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(requireContext())
                .setTitle("Autenticación con Huella Dactilar")
                .setSubtitle("Coloca tu dedo en el sensor")
                .setNegativeButton("Cancelar", executor, (dialog, which) -> {
                    // Manejar la acción del botón Cancelar si es necesario
                })
                .build();

        CancellationSignal cancellationSignal = new CancellationSignal();

        // Utiliza BiometricPrompt.CryptoObject
        biometricPrompt.authenticate(new BiometricPrompt.CryptoObject((Cipher) null), cancellationSignal, executor, callback);
    }

    // Método para mostrar mensajes Toast
    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Método llamado al hacer clic en el botón "Obtener Huella"
    private void onClickObtenerHuella() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (checkBiometricSupport()) {
                showBiometricPrompt();
            } else {
                // Mensaje de que el dispositivo no es compatible con el sensor de huellas
                Toast.makeText(requireContext(), "El dispositivo no es compatible con el sensor de huellas dactilares", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método llamado cuando se han procesado las solicitudes de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_USE_BIOMETRIC) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("Permiso de huellas dactilares concedido");
            } else {
                showToast("Permiso de huellas dactilares denegado");
            }
        }
    }

    // Método para ir a la pantalla de inicio
    private void irAPantallaDeInicio() {
        requireActivity().finish();
    }
}

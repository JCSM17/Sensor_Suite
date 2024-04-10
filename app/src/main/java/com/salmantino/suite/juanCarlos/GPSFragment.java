package com.salmantino.suite.juanCarlos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.salmantino.suite.R;
import com.salmantino.suite.UtilidadAnimacionBoton;

public class GPSFragment extends Fragment {

    private FusedLocationProviderClient clienteUbicacion;
    private ImageButton obtenerUbicacionButton;
    private ImageButton atrasButton;
    private TextView ubicacionTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_gps, container, false);

        // Inicializar el cliente de ubicación
        clienteUbicacion = LocationServices.getFusedLocationProviderClient(requireContext());

        // Referencias a los elementos de la interfaz de usuario
        obtenerUbicacionButton = view.findViewById(R.id.btnObtenerUbicacion);
        ubicacionTextView = view.findViewById(R.id.ubicacionTextView);
        atrasButton = view.findViewById(R.id.btnAtrasGPS);

        // Verificar y solicitar permisos
        verificarPermisoUbicacion();

        // Configurar el listener del botón para obtener la ubicación
        obtenerUbicacionButton.setOnClickListener(v -> {
            // Verificar y solicitar habilitación del servicio de ubicación
            verificarConfiguracionUbicacion();

            // Obtener la última ubicación cuando se presiona el botón
            obtenerUltimaUbicacion();
        });

        // Configurar el listener del botón "Atrás"
        atrasButton.setOnClickListener(v -> {
            // Iluminar temporalmente el botón "Atrás"
            UtilidadAnimacionBoton.iluminarBoton(atrasButton, R.drawable.on_salir);  // Cambiar a la imagen iluminada
            // Luego, ir a la pantalla de inicio
            irAPantallaDeInicio();
        });

        // Devolver la vista inflada
        return view;
    }

    // Método para verificar y solicitar permisos de ubicación
    private void verificarPermisoUbicacion() {
        String permisoFineLocation = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ActivityCompat.checkSelfPermission(requireContext(), permisoFineLocation)
                != PackageManager.PERMISSION_GRANTED) {
            // Si no se tienen permisos, solicítalos al usuario
            ActivityCompat.requestPermissions(
                    requireActivity(),
                    new String[]{permisoFineLocation},
                    MI_SOLICITUD_PERMISO_UBICACION
            );
        }
    }

    // Método para verificar si el servicio de ubicación está habilitado
    private boolean servicioUbicacionHabilitado() {
        LocationManager locationManager =
                (LocationManager) requireContext().getSystemService(requireContext().LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // Método para verificar y solicitar habilitación del servicio de ubicación
    private void verificarConfiguracionUbicacion() {
        if (!servicioUbicacionHabilitado()) {
            // Si el servicio de ubicación no está habilitado, mostrar el diálogo para habilitarlo
            mostrarDialogoHabilitarUbicacion();
        }
    }

    // Método para mostrar el diálogo de habilitar ubicación
    private void mostrarDialogoHabilitarUbicacion() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Habilitar Ubicación")
                .setMessage("Para utilizar esta aplicación, debes habilitar el servicio de ubicación.")
                .setPositiveButton("Ir a Configuración", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Método para mostrar mensajes Toast
    private void mostrarToast(String mensaje) {
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    // Método llamado cuando se han procesado las solicitudes de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MI_SOLICITUD_PERMISO_UBICACION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso otorgado, habilitar el botón para obtener la ubicación
                obtenerUbicacionButton.setEnabled(true);
            } else {
                mostrarToast("Permiso de ubicación denegado");
            }
        }
    }

    // Método para obtener la última ubicación conocida del dispositivo
    private void obtenerUltimaUbicacion() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {
            clienteUbicacion.getLastLocation()
                    .addOnSuccessListener(requireActivity(), ubicacion -> {
                        if (ubicacion != null) {
                            // Actualizar la interfaz de usuario con la ubicación actual
                            actualizarInterfazConUbicacion(ubicacion);
                        } else {
                            mostrarToast("Ubicación no disponible");
                        }
                    })
                    .addOnFailureListener(requireActivity(), excepcion ->
                            mostrarToast("Error al obtener la ubicación: " + excepcion.getMessage()));
        }
    }

    // Método para actualizar la interfaz de usuario con la ubicación actual
    private void actualizarInterfazConUbicacion(Location ubicacion) {
        double latitud = ubicacion.getLatitude();
        double longitud = ubicacion.getLongitude();
        ubicacionTextView.setText("Latitud: " + latitud + ", Longitud: " + longitud);
    }

    // Método llamado al hacer clic en el botón "Atrás"
    private void onAtrasClick() {
        // Lógica para manejar el clic en el botón de retroceso
        irAPantallaDeInicio();
    }

    // Método para ir a la pantalla de inicio
    private void irAPantallaDeInicio() {
        requireActivity().finish();
    }

    private static final int MI_SOLICITUD_PERMISO_UBICACION = 1;
}
package com.salmantino.suite.juanCarlos;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.salmantino.suite.ComunicaMenu;
import com.salmantino.suite.R;

// Declaración de la clase JcFragmentMenu, que extiende Fragment
public class JcFragmentMenu extends Fragment {

    // Arreglos que contienen identificadores de recursos y referencias a imágenes para botones
    private final int[] BOTONESMENU = {R.id.vibracion, R.id.gps, R.id.sensorHuella};   // IDs de los botones en el menú
    private final int[] BOTONESILUMINADOS = {R.drawable.on_vibracion, R.drawable.on_gps, R.drawable.on_huella};   // IDs de imágenes de botones iluminados
    private final int[] BOTONESORIGINALES = {R.drawable.foto_vibracion, R.drawable.foto_gps, R.drawable.foto_huella};   // IDs de imágenes de botones originales

    // Variable para almacenar el índice del botón pulsado
    private int boton;

    // Reproductor de audio para el botón playTrailer
    private MediaPlayer mediaPlayer;

    // Flag para verificar si el audio está reproduciéndose
    private boolean isReproduciendo = false;

    // Flag para verificar si el fragmento estaba en pausa
    private boolean isPaused = false;

    // Declarar una variable miembro para almacenar la referencia al botón playTrailer
    private ImageButton playTrailerButton;

    // Clave para guardar y recuperar el estado de reproducción en el Bundle
    private static final String KEY_REPRODUCIENDO = "reproduciendo";

    // Constructor vacío requerido por Fragment
    public JcFragmentMenu() {
        // Constructor vacío requerido
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Guardar el estado de reproducción en el Bundle
        outState.putBoolean(KEY_REPRODUCIENDO, isReproduciendo);
    }

    // Método llamado para crear y devolver la jerarquía de vistas asociada con el fragmento
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View miMenu = inflater.inflate(R.layout.fragment_juancarlos, container, false);
        // Inicializar la variable de botón
        boton = -1;

        // Obtener el índice del botón pulsado desde los argumentos
        if (getArguments() != null) {
            boton = getArguments().getInt("BOTONPULSADO");
        }

        // Restaurar el estado de reproducción desde el Bundle si está disponible
        if (savedInstanceState != null) {
            isReproduciendo = savedInstanceState.getBoolean(KEY_REPRODUCIENDO, false);
        }

        // Inicializar el reproductor de audio solo si es nulo
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.trailer);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // La reproducción ha terminado, actualizar la imagen del botón
                    isReproduciendo = false;
                    actualizarImagenBoton(playTrailerButton);
                }
            });
        }

        // Iterar sobre los botones del menú
        for (int i = 0; i < BOTONESMENU.length; i++) {
            final ImageButton botonMenu = miMenu.findViewById(BOTONESMENU[i]);

            // Colocar la imagen correspondiente si es el botón pulsado
            if (boton == i) {
                botonMenu.setImageResource(BOTONESILUMINADOS[i]);
            } else {
                // Establecer la imagen original si no es el botón pulsado
                botonMenu.setImageResource(BOTONESORIGINALES[i]);
            }

            final int queBoton = i;

            // Establecer un escuchador de clic para cada botón del menú
            botonMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Dirigir a la actividad correspondiente
                    if (getActivity() instanceof ComunicaMenu) {
                        ((ComunicaMenu) getActivity()).jcFragmentMenu(queBoton);
                    }

                    // Cambiar la imagen permanentemente después del clic
                    botonMenu.setImageResource(BOTONESILUMINADOS[queBoton]);

                    // Restaurar la imagen original después de 1000 milisegundos (1 segundo)
                    cambiarImagenConRetardo(botonMenu, BOTONESORIGINALES[queBoton], 1000);
                }
            });
        }

        // Configurar el botón para abrir el enlace del trailer en YouTube
        ImageButton linkTrailerButton = miMenu.findViewById(R.id.linkTrailer);
        linkTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLinkTrailer();
            }
        });

        // Configurar el botón para reproducir el audio
        playTrailerButton = miMenu.findViewById(R.id.playTrailer);
        playTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleReproduccion(playTrailerButton);
            }
        });

        // Actualizar la imagen del botón según el estado de reproducción
        actualizarImagenBoton(playTrailerButton);

        // Devolver la vista inflada
        return miMenu;
    }

    // Método para cambiar la imagen de un botón con retardo
    private void cambiarImagenConRetardo(final ImageButton boton, final int imagen, long retardo) {
        new Handler().postDelayed(() -> boton.setImageResource(imagen), retardo);
    }

    // Método para abrir el enlace del trailer en YouTube
    private void abrirLinkTrailer() {
        // Crea un Intent para abrir el enlace en un navegador web
        String url = "https://www.youtube.com/watch?v=QdBZY2fkU-0";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        // Inicia la actividad del navegador web
        startActivity(intent);
    }

    // Método para reproducir el audio
    private void toggleReproduccion(ImageButton boton) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isReproduciendo = false;
        } else {
            mediaPlayer.start();
            isReproduciendo = true;

            AudioManager audioManager = (AudioManager) requireContext().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
            }
        }

        // Actualizar la imagen del botón según el estado de reproducción actual
        actualizarImagenBoton(boton);
    }

    // Método para actualizar la imagen del botón según el estado de reproducción actual
    private void actualizarImagenBoton(ImageButton boton) {
        if (isReproduciendo) {
            boton.setImageResource(R.drawable.foto_pausa); // Cambiar a la imagen de pausa
        } else {
            boton.setImageResource(R.drawable.foto_play); // Restaurar la imagen original
        }
    }

    // Método llamado cuando se destruye el fragmento
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Libera los recursos del reproductor de audio
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    // Método llamado cuando se pausa el fragmento
    @Override
    public void onPause() {
        super.onPause();
        // Pausa la reproducción si el fragmento está en pausa y la reproducción está en curso
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPaused = true;
        }
    }

    // Método llamado cuando se reanuda el fragmento
    @Override
    public void onResume() {
        super.onResume();
        // Reanuda la reproducción si el fragmento estaba en pausa y la reproducción estaba en curso
        if (isPaused && isReproduciendo) {
            mediaPlayer.start();
        }
        isPaused = false;
    }
}

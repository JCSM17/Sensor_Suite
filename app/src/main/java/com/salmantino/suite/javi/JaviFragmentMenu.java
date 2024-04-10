package com.salmantino.suite.javi;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.salmantino.suite.ComunicaMenu;
import com.salmantino.suite.R;

public class JaviFragmentMenu extends Fragment {

    private final int[] BOTONESMENU = {R.id.acelerometro, R.id.giroscopio, R.id.proximidad};
    private final int[] BOTONESILUMINADOS = {R.drawable.on_acelerometro, R.drawable.on_giroscopio, R.drawable.on_proximidad};
    private final int[] BOTONESORIGINALES = {R.drawable.foto_acelerometro, R.drawable.foto_giroscopio, R.drawable.foto_proximidad};

    private int boton;

    public JaviFragmentMenu() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View miMenu = inflater.inflate(R.layout.fragment_javi, container, false);

        boton = getArguments() != null ? getArguments().getInt("BOTONPULSADO", -1) : -1;

        for (int i = 0; i < BOTONESMENU.length; i++) {
            final ImageButton botonMenu = miMenu.findViewById(BOTONESMENU[i]);
            final int queBoton = i;

            // Colocar la imagen correspondiente si es el botón pulsado
            if (boton == i) {
                cambiarImagenConRetardo(botonMenu, BOTONESILUMINADOS[queBoton], 1000);
            }

            botonMenu.setOnClickListener(v -> {
                // Dirigir a la actividad correspondiente
                if (getActivity() instanceof ComunicaMenu) {
                    ((ComunicaMenu) getActivity()).javiFragmentMenu(queBoton);
                }

                // Cambiar la imagen permanentemente después del clic
                botonMenu.setImageResource(BOTONESILUMINADOS[queBoton]);

                // Restaurar la imagen original después de 1000 milisegundos (1 segundo)
                cambiarImagenConRetardo(botonMenu, BOTONESORIGINALES[queBoton], 1000);
            });
        }

        return miMenu;
    }

    private void cambiarImagenConRetardo(final ImageButton boton, final int imagen, long retardo) {
        new Handler().postDelayed(() -> boton.setImageResource(imagen), retardo);
    }
}

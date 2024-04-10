package com.salmantino.suite.jesus;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.salmantino.suite.ComunicaMenu;
import com.salmantino.suite.R;

public class JesusFragmentMenu extends Fragment {

    private final int[] BOTONESMENU = {R.id.linterna, R.id.presion, R.id.termometro};
    private final int[] BOTONESILUMINADOS = {R.drawable.on_linterna, R.drawable.on_presion, R.drawable.on_termometro};
    private final int[] BOTONESORIGINALES = {R.drawable.foto_linterna, R.drawable.foto_presion, R.drawable.foto_termometro};

    private int boton;

    public JesusFragmentMenu() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View miMenu = inflater.inflate(R.layout.fragment_jesus, container, false);

        boton = -1;

        if (getArguments() != null) {
            boton = getArguments().getInt("BOTONPULSADO");
        }

        for (int i = 0; i < BOTONESMENU.length; i++) {
            final ImageButton botonMenu = miMenu.findViewById(BOTONESMENU[i]);
            final int queBoton = i;

            // Colocar la imagen correspondiente si es el botón pulsado
            if (boton == i) {
                botonMenu.setImageResource(BOTONESILUMINADOS[queBoton]);
            }

            botonMenu.setOnClickListener(v -> {
                // Dirigir a la actividad correspondiente
                if (getActivity() instanceof ComunicaMenu) {
                    ((ComunicaMenu) getActivity()).jesusFragmentMenu(queBoton);
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

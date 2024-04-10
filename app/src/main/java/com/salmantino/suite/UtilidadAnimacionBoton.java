package com.salmantino.suite;

import android.os.Handler;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;

public class UtilidadAnimacionBoton {

    public static void iluminarBoton(ImageButton boton, int recursoIluminado) {
        // Crear una animación de escala para cambiar la imagen
        ScaleAnimation animacionEscala = new ScaleAnimation(1f, 0f, 1f, 0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animacionEscala.setDuration(10);  // Duración de la animación en milisegundos
        animacionEscala.setFillAfter(true);  // Mantener la escala después de la animación

        // Configurar un manejador para cambiar la imagen después de la mitad de la duración de la animación
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            // Cambiar la imagen mientras está oculta
            boton.setImageResource(recursoIluminado);

            // Aplicar la animación de escala inversa para mostrar la imagen iluminada
            boton.startAnimation(animacionEscala);
        }, animacionEscala.getDuration() / 2);
    }
}

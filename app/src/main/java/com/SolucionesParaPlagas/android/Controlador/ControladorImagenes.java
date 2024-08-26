package com.SolucionesParaPlagas.android.Controlador;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class ControladorImagenes {

    private Context context;

    // Constructor que recibe el contexto de la aplicación
    public ControladorImagenes(Context context) {
        this.context = context;
    }

    /**
     * Método que recibe una URL y un ImageView, carga la imagen desde la URL
     * y la muestra en el ImageView.
     *
     * @param imageUrl La URL de la imagen.
     * @param imageView El ImageView donde se mostrará la imagen.
     */
    public void cargarImagenDesdeUrl(String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .into(imageView);
    }

}

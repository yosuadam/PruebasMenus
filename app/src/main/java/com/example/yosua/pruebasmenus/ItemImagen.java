package com.example.yosua.pruebasmenus;

/**
 * Created by Yosua on 20/11/2017.
 */

public class ItemImagen {

    private String genero;
    private String tituloImagen;
    private int idIcono;
    private int idImagen;


    public ItemImagen (String tituloImagen, int idIcono, int idImagen) {
        this.tituloImagen = tituloImagen;
        this.idIcono = idIcono;
        this.idImagen = idImagen;
    }

    public int getIdIcono() {
        return idIcono;
    }

    public int getIdImagen () { return idImagen; }

    public String getTituloImagen () { return tituloImagen; }
}

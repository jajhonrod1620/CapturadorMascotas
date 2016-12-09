package com.jjhon.jrodriguez.capturador;

import android.app.Application;

/**
 * Created by jaimea on 12/5/16.
 */
public class MiAplicacion extends Application{
    //private String miURL= "http://192.168.0.7/agendamascotas/";
    private String miURL= "http://192.168.2.132:4568/agendamascotas/";
    //private String miURL= "http://172.17.2.51/agendamascotas/";
    private String carpetaImage = "imagenes/";

    public String getMiURL() {
        return miURL;
    }
    public void setMiURL(String miURL) {
        this.miURL = miURL;
    }

    public String getCarpetaImage() {
        return carpetaImage;
    }
}

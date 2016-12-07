package com.jjhon.jrodriguez.capturador;

/**
 * Created by jrodriguez on 5/12/16.
 */
public class Mascotas {
    private String idMascota;
    private String nombreMascota;
    private String raza;
    private String fechaNac;
    private String urlImagen;

    public Mascotas(String idMascota, String nombreMascota, String raza, String fechaNac, String urlImagen) {
        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.raza = raza;
        this.fechaNac = fechaNac;
        this.urlImagen = urlImagen;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(String idMascota) {
        this.idMascota = idMascota;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @Override
    public String toString() {
        return "Mascotas{" +
                "idMascota='" + idMascota + '\'' +
                ", nombreMascota='" + nombreMascota + '\'' +
                ", raza='" + raza + '\'' +
                ", fechaNac='" + fechaNac + '\'' +
                ", urlImagen='" + urlImagen + '\'' +
                '}';
    }
}

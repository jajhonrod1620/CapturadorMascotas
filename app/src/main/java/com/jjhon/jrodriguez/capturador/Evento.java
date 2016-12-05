package com.jjhon.jrodriguez.capturador;

/**
 * Created by jrodriguez on 5/12/16.
 */
public class Evento {

    public Evento(String idEvento, String idMascota, String titulo, String descripcion, String fecha, String peso, String urlImagen) {
        this.idEvento = idEvento;
        this.idMascota = idMascota;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.peso = peso;
        this.urlImagen = urlImagen;
    }

    private String idEvento, idMascota, titulo, descripcion, fecha, peso, urlImagen;

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(String idMascota) {
        this.idMascota = idMascota;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "idEvento='" + idEvento + '\'' +
                ", idMascota='" + idMascota + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", peso='" + peso + '\'' +
                ", urlImagen='" + urlImagen + '\'' +
                '}';
    }
}

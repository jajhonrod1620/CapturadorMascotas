package com.jjhon.jrodriguez.capturador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by jrodriguez on 5/12/16.
 */
public class MascotasAdapter extends RecyclerView.Adapter<MascotasAdapter.Myvistaholder> {

    private Context contexto;
    private ArrayList<Mascotas> miPet;
    //private String miUrl = "http://192.168.0.7/agendamascotas/";
    private String miUrl = "http://192.168.2.132:4568/agendamascotas/";

    public MascotasAdapter(Context contexto, ArrayList<Mascotas> miPet) {
        this.contexto = contexto;
        this.miPet = miPet;
    }

    @Override
    public Myvistaholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Myvistaholder(LayoutInflater.from
                (parent.getContext()).inflate(R.layout.lista_mascotas,null));
    }

    @Override
    public void onBindViewHolder(Myvistaholder holder, int position) {
        holder.petNombre.setText(miPet.get(position).getNombreMascota());
        Glide.with(contexto).
            load(miUrl+miPet.get(position).getUrlImagen()).into(holder.petImagen);

        holder.petImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contexto.startActivity(new Intent(contexto,CapturaEvento.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return miPet.size();
    }

    public class Myvistaholder extends RecyclerView.ViewHolder {

        ImageView petImagen;
        TextView petNombre;

        public Myvistaholder(View itemView) {
            super(itemView);

            petImagen = (ImageView)itemView.findViewById(R.id.imagen_mascota);
            petNombre = (TextView)itemView.findViewById(R.id.nombre_mascota);

        }
    }
}

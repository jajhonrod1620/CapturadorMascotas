package com.jjhon.jrodriguez.capturador;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by jaimea on 12/5/16.
 */
public class EventosAdpater extends RecyclerView.Adapter<EventosAdpater.Myvistaholder> {
    private Context contexto;
    private ArrayList<Evento> miEvento;
    //private String miUrl = "http://192.168.0.7/agendamascotas/";
    //private String miUrl = "http://192.168.2.132:4568/agendamascotas/";
    private String miUrl = "http://172.17.2.51/agendamascotas/";

    public EventosAdpater(Context contexto, ArrayList<Evento> miEvento) {
        this.contexto = contexto;
        this.miEvento = miEvento;
    }

    @Override
    public Myvistaholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Myvistaholder(LayoutInflater.from
                (parent.getContext()).inflate(R.layout.lista_eventos,null));
    }

    @Override
    public void onBindViewHolder(Myvistaholder holder, int position) {
        holder.eventoTitulo.setText(miEvento.get(position).getTitulo());
        holder.eventoDesc.setText(miEvento.get(position).getDescripcion());
        holder.eventoFecha.setText(miEvento.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return miEvento.size();
    }

    public class Myvistaholder extends RecyclerView.ViewHolder {

        TextView eventoTitulo, eventoDesc, eventoFecha;

        public Myvistaholder(View itemView) {
            super(itemView);
            eventoTitulo = (TextView)itemView.findViewById(R.id.titulo_evento);
            eventoDesc = (TextView)itemView.findViewById(R.id.descripcion_evento);
            eventoFecha = (TextView)itemView.findViewById(R.id.fecha_evento);

        }
    }
}

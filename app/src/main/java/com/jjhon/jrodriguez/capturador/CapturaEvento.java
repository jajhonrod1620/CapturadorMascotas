package com.jjhon.jrodriguez.capturador;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CapturaEvento extends AppCompatActivity {
    EditText titulo, descripcion, fecha, peso;
    Button guardar;
    RequestQueue requestQueue;
    private static final String URL = "http://192.168.2.132:4568/agendamascotas/insertar_evento.php";
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_evento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titulo = (EditText)findViewById(R.id.txtTitulo);
        descripcion = (EditText)findViewById(R.id.txtDescripcion);
        fecha = (EditText)findViewById(R.id.txtFecha);
        fecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    showDatePickerDialog(view);
                }
            }
        });
        peso = (EditText)findViewById(R.id.txtPeso);
        guardar = (Button)findViewById(R.id.guarda_evento);
        
        requestQueue = Volley.newRequestQueue(this);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarEvento();
            }
        });
    }

    private void guardarEvento() {
        stringRequest = new StringRequest(
                Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(
                                getApplicationContext(),"Guardado Correctamente",
                                Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(
                        getApplicationContext(),""+error,
                        Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap<String,String> envio = new HashMap<>();
                envio.put("mascota","7");
                envio.put("titulo",titulo.getText().toString());
                envio.put("fecha_even",fecha.getText().toString());
                envio.put("peso",peso.getText().toString());
                envio.put("descripcion",descripcion.getText().toString());
                return envio;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerEvento();
        newFragment.show(getSupportFragmentManager(), "Calendario");
    }

}

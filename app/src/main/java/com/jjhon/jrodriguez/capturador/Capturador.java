package com.jjhon.jrodriguez.capturador;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Capturador extends AppCompatActivity {
    EditText txtNombre, txtTipo, txtRaza, txtFecha, txtDescripcion;
    ImageView colocarImagen;
    Button guardar;
    RequestQueue requestQueue;
    private static final String URL = "http://192.168.2.132:4568/agendamascotas/insertar.php";
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtTipo = (EditText) findViewById(R.id.txtTipo);
        txtRaza = (EditText) findViewById(R.id.txtRaza);
        txtFecha = (EditText) findViewById(R.id.txtFechaNac);
        txtFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    showDatePickerDialog(view);
                }
            }
        });
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        colocarImagen = (ImageView)findViewById(R.id.colocarimagen);
        guardar = (Button) findViewById(R.id.btnguardar);

        colocarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imagenIntent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                imagenIntent.setType("image/*");
                startActivityForResult(imagenIntent,100);
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path = data.getData();
            colocarImagen.setImageURI(path);
        }
    }

    private void guardar() {
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
            protected Map<String,String> getParams() throws AuthFailureError{
                HashMap<String,String> envio = new HashMap<>();
                envio.put("nombre",txtNombre.getText().toString());
                envio.put("tipo",txtTipo.getText().toString());
                envio.put("raza",txtRaza.getText().toString());
                envio.put("fecha_nac",txtFecha.getText().toString());
                envio.put("descripcion",txtDescripcion.getText().toString());
                return envio;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "Calendario");
    }
}

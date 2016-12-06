package com.jjhon.jrodriguez.capturador;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CapturaEvento extends AppCompatActivity {
    EditText titulo, descripcion, fecha, peso;
    Button guardar;
    RequestQueue requestQueue;
    //private static final String URL = "http://192.168.2.132:4568/agendamascotas/insertar_evento.php";
    //private static final String URL = "http://192.168.0.7/agendamascotas/insertar_evento.php";
    private static final String URL = "http://172.17.2.51/agendamascotas/insertar_evento.php";

    private int PICK_IMAGE_REQUEST = 1;
    StringRequest stringRequest;
    private Bitmap bitmap;
    ImageView subirImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_evento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titulo = (EditText)findViewById(R.id.txtTitulo);
        descripcion = (EditText)findViewById(R.id.txtDescripcion);
        fecha = (EditText)findViewById(R.id.txtFecha);
        peso = (EditText)findViewById(R.id.txtPeso);
        guardar = (Button)findViewById(R.id.guarda_evento);
        subirImagen = (ImageView)findViewById(R.id.subirimagen);

        fecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    showDatePickerDialog(view);
                }
            }
        });
        subirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imagenIntent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                imagenIntent.setType("image/*");
                startActivityForResult(imagenIntent,PICK_IMAGE_REQUEST);
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarEvento();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                subirImagen.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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
                String uploadImage = getStringImage(bitmap);
                HashMap<String,String> envio = new HashMap<>();
                envio.put("mascota","7");
                envio.put("titulo",titulo.getText().toString());
                envio.put("fecha_even",fecha.getText().toString());
                envio.put("peso",peso.getText().toString());
                envio.put("descripcion",descripcion.getText().toString());
                envio.put("url_imagen",uploadImage);
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

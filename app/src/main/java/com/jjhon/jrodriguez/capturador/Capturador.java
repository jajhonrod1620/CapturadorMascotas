package com.jjhon.jrodriguez.capturador;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Capturador extends AppCompatActivity {
    EditText txtNombre, txtTipo, txtRaza, txtFecha, txtDescripcion;
    ImageView colocarImagen;
    Button guardar;
    RequestQueue requestQueue;

    private static final String programa = "insertar.php";
    private String URL;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        URL = ((MiAplicacion) this.getApplication()).getMiURL()+ programa;

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtTipo = (EditText) findViewById(R.id.txtTipo);
        txtRaza = (EditText) findViewById(R.id.txtRaza);
        txtFecha = (EditText) findViewById(R.id.txtFechaNac);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        colocarImagen = (ImageView)findViewById(R.id.colocarimagen);
        guardar = (Button) findViewById(R.id.btnguardar);

        txtFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    showDatePickerDialog(view);
                }
            }
        });

        colocarImagen.setOnClickListener(new View.OnClickListener() {
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
                guardar();
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
                colocarImagen.setImageBitmap(bitmap);
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
                String uploadImage = getStringImage(bitmap);
                HashMap<String,String> envio = new HashMap<>();
                envio.put("nombre",txtNombre.getText().toString());
                envio.put("tipo",txtTipo.getText().toString());
                envio.put("raza",txtRaza.getText().toString());
                envio.put("fecha_nac",txtFecha.getText().toString());
                envio.put("descripcion",txtDescripcion.getText().toString());
                envio.put("urlimagen",uploadImage);

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

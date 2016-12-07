package com.jjhon.jrodriguez.capturador;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaMascotas extends AppCompatActivity {

    //private static final String url = "http://192.168.0.7/agendamascotas/consulta_mascota.php";
    //private static final String url = "http://192.168.2.132:4568/agendamascotas/consulta_mascota.php";
    //private static final String url = "http://172.17.2.51/agendamascotas/consulta_mascota.php";
    private String url;
    private static final String programa = "consulta_mascota.php";

    private RecyclerView recyclerView;
    private MascotasAdapter adaptador;
    private ArrayList<Mascotas> miMascota = new ArrayList<>();
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mascotas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        mostrarInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_activity_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mascota:
                startActivity(new Intent(getApplicationContext(),Capturador.class));
                return true;
            case R.id.action_cerrar:
                finish();
                startActivity(new Intent(getApplicationContext(),Logueo.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void mostrarInfo() {
        url = ((MiAplicacion) this.getApplication()).getMiURL();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando datos...");
        recyclerView=(RecyclerView)findViewById(R.id.listado_mascotas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adaptador = new MascotasAdapter(this,miMascota);
        recyclerView.setAdapter(adaptador);

        JsonArrayRequest request = new JsonArrayRequest(url+programa, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                for(int i=0; i<response.length();i++){
                    try {
                        JSONObject objeto = response.getJSONObject(i);
                        String urlImagen = objeto.getString("urlimagen");
                        String nombreMascota = objeto.getString("nombre");
                        String idMascota = objeto.getString("idmascota");
                        miMascota.add(new Mascotas(idMascota,nombreMascota, "", "", urlImagen));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),""+e, Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
                adaptador.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }
}

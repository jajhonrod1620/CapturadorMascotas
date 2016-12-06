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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaEventos extends AppCompatActivity {

    private static final String url = "http://172.17.2.51/agendamascotas/consulta_evento.php";
    private RecyclerView recyclerView;
    private EventosAdpater adaptador;
    private ArrayList<Evento> miEvento = new ArrayList<>();
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String llave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        llave=getIntent().getExtras().getString("llave");
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void mostrarInfo() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando datos...");
        recyclerView=(RecyclerView)findViewById(R.id.listado_eventos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adaptador = new EventosAdpater(this,miEvento);
        recyclerView.setAdapter(adaptador);

        //JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
        StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray object = new JSONArray(response);
                    for (int i = 0; i < object.length(); i++) {
                        JSONObject objeto = (JSONObject) object.get(i);
                        String urlImagen = objeto.getString("url_imagen");
                        String descEvento = objeto.getString("descripcion");
                        String titEvento = objeto.getString("titulo");
                        miEvento.add(new Evento("", "", titEvento, descEvento, "", "", urlImagen));
                    }
                    //recyclerView.setAdapter(adapatador);
                    System.out.println("datos " + object + " Cantidad: " + object.length());
                } catch (JSONException e) {
                    System.out.println("error " + e);
                }
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("error "+error);
                }
            }){
                protected Map<String,String> getParams(){
                    HashMap<String,String> hashMap= new HashMap<String, String>();
                    hashMap.put("mascota",llave);
                    return hashMap;
                }
            };

            /*
            @Override

            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                for(int i=0; i<response.length();i++){
                    try {
                        JSONObject objeto = response.getJSONObject(i);
                        String urlImagen = objeto.getString("url_imagen");
                        String descEvento = objeto.getString("descripcion");
                        String titEvento = objeto.getString("titulo");
                        miEvento.add(new Evento("", "", titEvento,  descEvento, "",  "", urlImagen));

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
        });*/

        requestQueue.add(request);
    }
}

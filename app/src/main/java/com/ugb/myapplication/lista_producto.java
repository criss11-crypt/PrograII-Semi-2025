package com.ugb.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class lista_producto extends Activity {
    Bundle parametros = new Bundle();
    ListView ltsProducto;
    Cursor cProducto;
    DB db;
    final ArrayList<producto> alProducto = new ArrayList<producto>();
    final ArrayList<producto> alProductoCopia = new ArrayList<producto>();
    JSONArray jsonArray;
    JSONObject jsonObject;
    producto misProducto;
    FloatingActionButton fab;
    int posicion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto);

        parametros.putString("accion", "nuevo");
        db = new DB(this);

        fab = findViewById(R.id.fabAgregarProducto);
        fab.setOnClickListener(view -> abriVentana());
        obtenerDatosProducto();
        buscarProducto();
    }

    private void buscarProducto() {
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);
        try {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicion = info.position;
            menu.setHeaderTitle(jsonArray.getJSONObject(posicion).getString("nombre"));
        } catch (Exception e) {
            mostrarMsg("Error: " + e.getMessage());
        }
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try{
            if( item.getItemId()==R.id.mnxNuevo){
                abriVentana();
            }else if( item.getItemId()==R.id.mnxModificar){
                parametros.putString("accion", "modificar");
                parametros.putString("producto", jsonArray.getJSONObject(posicion).toString());
                abriVentana();
            } else if (item.getItemId()==R.id.mnxEliminar) {
                eliminarProducto();
            }
            return true;
        }catch (Exception e){
            mostrarMsg("Error: " + e.getMessage());
            return super.onContextItemSelected(item);
        }
    }
    private void eliminarProducto(){
        try{
            String nombre = jsonArray.getJSONObject(posicion).getString("nombre");
            AlertDialog.Builder confirmacion = new AlertDialog.Builder(this);
            confirmacion.setTitle("Esta seguro de eliminar a: ");
            confirmacion.setMessage(nombre);
            confirmacion.setPositiveButton("Si", (dialog, which) -> {
                try {
                    String respuesta = db.administrar_producto("eliminar", new String[]{jsonArray.getJSONObject(posicion).getString("idAmigo")});
                    if(respuesta.equals("ok")) {
                        obtenerDatosProducto();
                        mostrarMsg("Registro eliminado con exito");
                    }else{
                        mostrarMsg("Error: " + respuesta);
                    }
                }catch (Exception e){
                    mostrarMsg("Error: " + e.getMessage());
                }
            });
            confirmacion.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            confirmacion.create().show();
        }catch (Exception e){
            mostrarMsg("Error: " + e.getMessage());
        }
    }
    private void abriVentana(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(parametros);
        startActivity(intent);
    }
    private void obtenerDatosProducto(){
        try{
            cProducto = db.lista_producto();
            if(cProducto.moveToFirst()){
                jsonArray = new JSONArray();
                do{
                    jsonObject = new JSONObject();
                    jsonObject.put("idProducto", cProducto.getString(0));
                    jsonObject.put("codigo", cProducto.getString(1));
                    jsonObject.put("descripcion", cProducto.getString(2));
                    jsonObject.put("nombre", cProducto.getString(3));
                    jsonObject.put("marca", cProducto.getString(4));
                    jsonObject.put("presentacion", cProducto.getString(5));
                    jsonObject.put("precio", cProducto.getString(6));
                    jsonObject.put("foto", cProducto.getString(7));
                    jsonArray.put(jsonObject);
                }while(cProducto.moveToNext());
                mostrarDatosProducto();
            }else{
                mostrarMsg("No hay amigos registrados.");
                abriVentana();
            }
        }catch (Exception e){
            mostrarMsg("Error: " + e.getMessage());
        }
    }
    private void mostrarDatosProducto(){
       
        TextView tempVal = findViewById(R.id.txtBuscarProducto);
        tempVal.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                alProducto.clear();
                String buscar = tempVal.getText().toString().trim().toLowerCase();
                if( buscar.length()<=0){
                    alProducto.addAll(alProductoCopia);
                }else{
                    for (producto item: alProductoCopia){
                        if(item.getNombre().toLowerCase().contains(buscar) ||
                                item.getCodigo().toLowerCase().contains(buscar) ||
                                item.getDescripcion().toLowerCase().contains(buscar)){
                            alProducto.addAll((Collection<? extends producto>) item);
                        }
                    }
                    ltsProducto.setAdapter((ListAdapter) new AdaptadorProducto(getApplicationContext(), alProducto));
                }
            }
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void mostrarMsg(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
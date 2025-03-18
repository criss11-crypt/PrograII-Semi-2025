package com.ugb.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdaptadorProducto extends BaseAdapter {

    Context context;

    ArrayList<producto> alProducto;

    producto misProducto;

    LayoutInflater inflater;



    public AdaptadorProducto(Context context, ArrayList<producto> alProducto) {

        this.context = context;

        this.alProducto = alProducto;

    }


    public int getCount() {
        return alProducto.size();
    }


    public Object getItem(int position) {
        return alProducto.get(position);
    }


    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.fotos, parent, false);
        try {
            misProducto = alProducto.get(position);

            TextView tempVal = itemView.findViewById(R.id.lblCodigoAdaptador);
            tempVal.setText(misProducto.getCodigo());

            tempVal = itemView.findViewById(R.id.lblDescripcionAdaptador);
            tempVal.setText(misProducto.getDescripcion());

            tempVal = itemView.findViewById(R.id.lblNombreAdaptador);
            tempVal.setText(misProducto.getNombre());

            ImageView img = itemView.findViewById(R.id.imgFotoAdaptador);
            Bitmap bitmap = BitmapFactory.decodeFile(misProducto.getFoto());
            img.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return itemView;
    }
}
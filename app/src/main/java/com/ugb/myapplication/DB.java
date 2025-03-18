package com.ugb.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tienda";
    private static final int DATABASE_VERSION = 1;
    private static final String SQLdb = "CREATE TABLE producto (idProducto INTEGER PRIMARY KEY AUTOINCREMENT, codigo TEXT, descripcion TEXT, nombre TEXT, marca TEXT, presentacion TEXT, precio DECIMAL, urlFoto TEXT)";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLdb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS producto");
        onCreate(db);
    }

    public String administrar_producto(String accion, String[] datos) {
        SQLiteDatabase db = getWritableDatabase();
        String mensaje = "ok";

        try {
            switch (accion) {
                case "nuevo":
                    ContentValues values = new ContentValues();
                    values.put("codigo", datos[1]);
                    values.put("descripcion", datos[2]);
                    values.put("nombre", datos[3]);
                    values.put("marca", datos[4]);
                    values.put("presentacion", datos[5]);
                    values.put("precio", Double.parseDouble(datos[6]));
                    values.put("urlFoto", datos[7]);
                    db.insert("producto", null, values);
                    break;

                case "modificar":
                    ContentValues updateValues = new ContentValues();
                    updateValues.put("codigo", datos[1]);
                    updateValues.put("descripcion", datos[2]);
                    updateValues.put("nombre", datos[3]);
                    updateValues.put("marca", datos[4]);
                    updateValues.put("presentacion", datos[5]);
                    updateValues.put("precio", Double.parseDouble(datos[6]));
                    updateValues.put("urlFoto", datos[7]);
                    db.update("producto", updateValues, "idProducto = ?", new String[]{datos[0]});
                    break;

                case "eliminar":
                    db.delete("producto", "idProducto = ?", new String[]{datos[0]});
                    break;
            }
        } catch (Exception e) {
            mensaje = e.getMessage();
            Log.e("DBError", "Error en administrar_producto: " + e.getMessage());
        } finally {
            db.close();
        }
        return mensaje;
    }

    public Cursor lista_producto() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM producto", null);
    }
}
package com.alexis.miprimeraplicacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    // Constantes para nombres de tablas y columnas
    private static final String DATABASE_NAME = "productos.db";
    private static final int DATABASE_VERSION = 32;

    // Tabla productos
    private static final String TABLE_PRODUCTOS = "productos";
    private static final String SQL_CREATE_PRODUCTOS =
            "CREATE TABLE " + TABLE_PRODUCTOS + " (" +
                    "idProducto TEXT PRIMARY KEY, " +
                    "codigo TEXT, " +
                    "descripcion TEXT, " +
                    "marca TEXT, " +
                    "presentacion TEXT, " +
                    "precio TEXT, " +
                    "costo TEXT, " +
                    "ganancia TEXT, " +
                    "stock TEXT, " +
                    "urlFoto TEXT, " +
                    "urlFoto1 TEXT, " +
                    "urlFoto2 TEXT)";

    // Tabla actualizado
    private static final String TABLE_ACTUALIZADO = "actualizado";
    private static final String SQL_CREATE_ACTUALIZADO =
            "CREATE TABLE " + TABLE_ACTUALIZADO + " (" +
                    "id TEXT PRIMARY KEY, " +
                    "actualizado TEXT)";
    private static final String SQL_INIT_ACTUALIZADO =
            "INSERT INTO " + TABLE_ACTUALIZADO + " (id, actualizado) VALUES ('0','0')";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear ambas tablas al inicio
        db.execSQL(SQL_CREATE_PRODUCTOS);
        db.execSQL(SQL_CREATE_ACTUALIZADO);
        db.execSQL(SQL_INIT_ACTUALIZADO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 31) {
            // Agregar nuevas columnas
            db.execSQL("ALTER TABLE " + TABLE_PRODUCTOS + " ADD COLUMN urlFoto1 TEXT");
            db.execSQL("ALTER TABLE " + TABLE_PRODUCTOS + " ADD COLUMN urlFoto2 TEXT");

            // Crear tabla actualizado si no existe
            db.execSQL(SQL_CREATE_ACTUALIZADO);
            db.execSQL(SQL_INIT_ACTUALIZADO);
        }
    }

    // Operaciones CRUD para productos
    public String administrarProductos(String accion, String[] datos) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            String sql;

            switch (accion) {
                case "nuevo":
                    sql = "INSERT INTO " + TABLE_PRODUCTOS + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                    db.execSQL(sql, datos);
                    break;

                case "modificar":
                    sql = "UPDATE " + TABLE_PRODUCTOS + " SET " +
                            "codigo=?, descripcion=?, marca=?, presentacion=?, " +
                            "precio=?, costo=?, ganancia=?, stock=?, " +
                            "urlFoto=?, urlFoto1=?, urlFoto2=? WHERE idProducto=?";
                    // Reordenar datos para que idProducto sea el último
                    String[] updateParams = {
                            datos[1], datos[2], datos[3], datos[4], datos[5],
                            datos[6], datos[7], datos[8], datos[9], datos[10],
                            datos[11], datos[0]
                    };
                    db.execSQL(sql, updateParams);
                    break;

                case "eliminar":
                    sql = "DELETE FROM " + TABLE_PRODUCTOS + " WHERE idProducto=?";
                    db.execSQL(sql, new String[]{datos[0]});
                    break;

                case "eliminarTodo":
                    sql = "DELETE FROM " + TABLE_PRODUCTOS;
                    db.execSQL(sql);
                    break;
            }

            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Operaciones para tabla actualizado
    public String administrarActualizados(String accion, String datos, String id) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            String sql;

            switch (accion) {
                case "modificar":
                    sql = "UPDATE " + TABLE_ACTUALIZADO + " SET actualizado=? WHERE id='0'";
                    db.execSQL(sql, new String[]{datos});
                    break;

                case "nuevo":
                    sql = "INSERT INTO " + TABLE_ACTUALIZADO + " VALUES (?,?)";
                    db.execSQL(sql, new String[]{id, datos});
                    break;

                case "eliminar":
                    sql = "DELETE FROM " + TABLE_ACTUALIZADO + " WHERE id!='0'";
                    db.execSQL(sql);
                    break;
            }

            return "ok";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Consultas
    public Cursor obtenerProductosActualizados() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ACTUALIZADO, null);
    }

    public Cursor obtenerProductos() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS, null);
    }

}
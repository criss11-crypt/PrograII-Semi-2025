package com.alexis.miprimeraplicacion;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;
@RequiresApi(api = Build.VERSION_CODES.O)
public class utilidades {
    static String url_consulta = "http://192.168.85.61:5984/cristofer/_design/Luis/_view/Luis";
    static String url_mto = "http://192.168.85.61:5984/cristofer";
    static String user = "criss11";//Agregar usuario
    static String passwd = "Crishvmc1";//Agregar contraseña
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user + ":" + passwd).getBytes());
    public String generarUnicoId(){
        return java.util.UUID.randomUUID().toString();
    }
}

package com.alexis.miprimeraplicacion;
import java.util.Base64;
public class utilidades {
    static String url_consulta = "http://192.168.31.205/cristofer/_design/cristofer/_view/Luis";
    static String url_mto = "http://192.168.31.205:5984/Luis";
    static String user = "criss11";//Agregar usuario
    static String passwd = "Crishvmc1";//Agregar contraseña
    static String credencialesCodificadas = Base64.getEncoder().encodeToString((user + ":" + passwd).getBytes());
    public String generarUnicoId(){
        return java.util.UUID.randomUUID().toString();
    }
}

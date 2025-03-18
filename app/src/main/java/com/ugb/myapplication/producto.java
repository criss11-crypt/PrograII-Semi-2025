package com.ugb.myapplication;

import java.math.BigDecimal;

/**
 * Clase que representa un producto en la aplicación.
 */
public class producto {
    private String idProducto;
    private String codigo;
    private String descripcion;
    private String nombre;
    private String marca;
    private String presentacion;
    private BigDecimal precio; // Usar BigDecimal para mayor precisión
    private String foto;

    /**
     * Constructor vacío.
     */
    public producto() {
    }

    /**
     * Constructor con todos los campos.
     *
     * @param idProducto    ID del producto.
     * @param codigo        Código del producto.
     * @param descripcion   Descripción del producto.
     * @param nombre        Nombre del producto.
     * @param marca         Marca del producto.
     * @param presentacion  Presentación del producto.
     * @param precio        Precio del producto.
     * @param foto          URL o ruta de la foto del producto.
     */
    public producto(String idProducto, String codigo, String descripcion, String nombre, String marca, String presentacion, BigDecimal precio, String foto) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.marca = marca;
        this.presentacion = presentacion;
        this.precio = precio;
        this.foto = foto;
    }

    public producto(String idProducto, String codigo, String descripcion, String nombre, String marca, String presentacion, String foto) {
    }

    // Getters y Setters

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        if (precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "producto{" +
                "idProducto='" + idProducto + '\'' +
                ", codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", marca='" + marca + '\'' +
                ", presentacion='" + presentacion + '\'' +
                ", precio=" + precio +
                ", foto='" + foto + '\'' +
                '}';
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.beans;

import java.sql.Date;

/**
 *
 * @author bryan
 */
public class Transaccion {
    private Date fecha;
    private String tipo;
    private int cod_proveedor;
    private String producto;
    private String nit;
    private double precio;
    private int cantidad;
    private double total;

    public Transaccion() {
    }

    public Transaccion(Date fecha, String tipo, int cod_proveedor, String producto, String nit, double precio, int cantidad, double total) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.cod_proveedor = cod_proveedor;
        this.producto = producto;
        this.nit = nit;
        this.precio = precio;
        this.cantidad = cantidad;
        this.total = total;
    }
    
    
    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
   
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCod_proveedor() {
        return cod_proveedor;
    }

    public void setCod_proveedor(int cod_proveedor) {
        this.cod_proveedor = cod_proveedor;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal() {
        this.total = total;
    }
    
    
}

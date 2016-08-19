/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.beans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import negocio.helpers.Conexion;
import negocio.helpers.Dialogs;

/**
 *
 * @author bryan
 */
public class Compra {
    Connection conexion = null;
    
    private Date fecha;
    private int cod_proveedor;
    private int cod_producto;
    private int cantidad;
    private double precio;
    private double total;

    public Compra() {
    }

    public Compra(Date fecha, int cod_proveedor, int cod_producto, int cantidad, double precio, double total) {
        this.fecha = fecha;
        this.cod_proveedor = cod_proveedor;
        this.cod_producto = cod_producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }

    

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCod_proveedor() {
        return cod_proveedor;
    }

    public void setCod_proveedor(int cod_proveedor) {
        this.cod_proveedor = cod_proveedor;
    }

    public int getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(int cod_producto) {
        this.cod_producto = cod_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public boolean insertarCompra(Compra compra){
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO compra (fecha ,cod_proveedor, cod_producto"
                    + ", precio, cantidad, total) VALUES(?,?,?,?,?,?)");
            consulta.setDate(1, compra.getFecha());
            consulta.setInt(2, compra.getCod_proveedor());
            consulta.setInt(3, compra.getCod_producto());
            consulta.setDouble(4, compra.getPrecio());
            consulta.setInt(5, compra.getCantidad());
            consulta.setDouble(6, compra.getTotal());
            
            consulta.executeUpdate();
            
            int __cantidad = 0;
            
            PreparedStatement select= conexion.prepareStatement("SELECT existencia FROM producto "
                    + "WHERE cod_producto= ?");
            select.setInt(1, compra.getCod_producto());
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                __cantidad = resultado.getInt("existencia");
            }
            
            PreparedStatement update;
            update = conexion.prepareStatement("UPDATE producto SET existencia = ? WHERE cod_producto = ?");
            update.setInt(1, __cantidad + compra.getCantidad());
            update.setInt(2, compra.getCod_producto());
            update.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at insert a compra", ex);
            error.showAndWait();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at insert a compra", ex);
            error.showAndWait();
            return false;
        }
        return true;
    }
}

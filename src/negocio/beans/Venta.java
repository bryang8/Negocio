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
public class Venta {
    private Date fecha;
    private String nit_cliente;
    private int cod_producto;
    private int cantidad;
    private double total;
    Connection conexion = null;

    public Venta() {
    }

    public Venta(Date fecha, String nit_cliente, int cod_producto, int cantidad, double total) {
        this.fecha = fecha;
        this.nit_cliente = nit_cliente;
        this.cod_producto = cod_producto;
        this.cantidad = cantidad;
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNit_cliente() {
        return nit_cliente;
    }

    public void setNit_cliente(String nit_cliente) {
        this.nit_cliente = nit_cliente;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
     public boolean insertarVenta(Venta venta){
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO factura (fecha ,nit_cliente, cod_producto"
                    + ", cantidad, total) VALUES(?,?,?,?,?)");
            consulta.setDate(1, venta.getFecha());
            consulta.setString(2, venta.getNit_cliente());
            consulta.setInt(3, venta.getCod_producto());
            consulta.setInt(4, venta.getCantidad());
            consulta.setDouble(5, venta.getTotal());
            
            consulta.executeUpdate();
            
            int __cantidad = 0;
            
            PreparedStatement select= conexion.prepareStatement("SELECT existencia FROM producto "
                    + "WHERE cod_producto= ?");
            select.setInt(1, venta.getCod_producto());
            ResultSet resultado = select.executeQuery();
            while(resultado.next()){
                __cantidad = resultado.getInt("existencia");
            }
            
            PreparedStatement update;
            update = conexion.prepareStatement("UPDATE producto SET existencia = ? WHERE cod_producto = ?");
            update.setInt(1, __cantidad - venta.getCantidad());
            update.setInt(2, venta.getCod_producto());
            update.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at insert a sell", ex);
            error.showAndWait();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at insert a sell", ex);
            error.showAndWait();
            return false;
        }
        return true;
    }
}

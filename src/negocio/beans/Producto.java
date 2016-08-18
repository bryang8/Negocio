/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class Producto {
    Connection conexion = null;
    
    private int cod_producto;
    private String descripcion;
    private int id_categoria;
    private String categoria;
    private double precio_unidad;
    private double precio_docena;
    private double precio_mayor;
    private int existencia;
    private String tipo_empaque;

    public Producto(int cod_producto, String descripcion, int id_categoria, double precio_unidad, 
            double precio_docena, double precio_mayor, int existencia, String tipo_empaque, String categoria) {
        this.cod_producto = cod_producto;
        this.descripcion = descripcion;
        this.id_categoria = id_categoria;
        this.precio_unidad = precio_unidad;
        this.precio_docena = precio_docena;
        this.precio_mayor = precio_mayor;
        this.existencia = existencia;
        this.tipo_empaque = tipo_empaque;
        this.categoria = categoria;
    }
    
    public Producto() {
        
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public int getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(int cod_producto) {
        this.cod_producto = cod_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public double getPrecio_unidad() {
        return precio_unidad;
    }

    public void setPrecio_unidad(double precio_unidad) {
        this.precio_unidad = precio_unidad;
    }

    public double getPrecio_docena() {
        return precio_docena;
    }

    public void setPrecio_docena(double precio_docena) {
        this.precio_docena = precio_docena;
    }

    public double getPrecio_mayor() {
        return precio_mayor;
    }

    public void setPrecio_mayor(double precio_mayor) {
        this.precio_mayor = precio_mayor;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public String getTipo_empaque() {
        return tipo_empaque;
    }

    public void setTipo_empaque(String tipo_empaque) {
        this.tipo_empaque = tipo_empaque;
    }
    
    public boolean insertarProducto(Producto producto){
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO producto (descripcion, precio_unidad"
                    + ", precio_docena, precio_mayor, id_categoria, existencia, tipo_empaque"
                    + ") VALUES(?,?,?,?,?,?,?)");
            consulta.setString(1, producto.getDescripcion());
            consulta.setDouble(2, producto.getPrecio_unidad());
            consulta.setDouble(3, producto.getPrecio_docena());
            consulta.setDouble(4, producto.getPrecio_mayor());
            consulta.setInt(5, producto.getId_categoria());
            consulta.setInt(6, producto.getExistencia());
            consulta.setString(7, producto.getTipo_empaque());
            
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at insert a product", ex);
            error.showAndWait();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at insert a product", ex);
            error.showAndWait();
            return false;
        }
        return true;
    }

    public boolean editarProdcto(Producto producto){
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE producto SET descripcion = ?, precio_unidad = ?,"
                    + " precio_docena = ?, precio_mayor = ?, id_categoria = ?, existencia = ?, "
                    + "tipo_empaque = ?  WHERE cod_producto = ?");
            consulta.setString(1, producto.getDescripcion());
            consulta.setDouble(2, producto.getPrecio_unidad());
            consulta.setDouble(3, producto.getPrecio_docena());
            consulta.setDouble(4, producto.getPrecio_mayor());
            consulta.setInt(5, producto.getId_categoria());
            consulta.setInt(6, producto.getExistencia());
            consulta.setString(7, producto.getTipo_empaque());
            consulta.setInt(8, producto.getCod_producto());
            
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at update a product", ex);
            error.showAndWait();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at update a product", ex);
            error.showAndWait();
            return false;
        }
        return true;
    }
    
    
    public boolean eliminarProducto(Producto p) {
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta = conexion.prepareStatement("DELETE FROM producto WHERE cod_producto = ?");
            consulta.setInt(1, p.getCod_producto());
            consulta.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at delete a product", ex);
            error.showAndWait();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at delete a product", ex);
            error.showAndWait();
            return false;
        }
        return true;
    }
    
    
}

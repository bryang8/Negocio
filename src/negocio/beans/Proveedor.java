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
public class Proveedor {
    private int codigo;
    private String nit;
    private String razon_social;
    private String direccion;
    private int telefono;
    private String pagina_web;
    private String email;
    private String contacto_principal;
    
    
    Connection conexion = null;

    public Proveedor() {
    }

    public Proveedor(int codigo, String nit, String razon_social, String direccion, int telefono, String pagina_web, String email, String contacto_principal) {
        this.codigo = codigo;
        this.nit = nit;
        this.razon_social = razon_social;
        this.direccion = direccion;
        this.telefono = telefono;
        this.pagina_web = pagina_web;
        this.email = email;
        this.contacto_principal = contacto_principal;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getPagina_web() {
        return pagina_web;
    }

    public void setPagina_web(String pagina_web) {
        this.pagina_web = pagina_web;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContacto_principal() {
        return contacto_principal;
    }

    public void setContacto_principal(String contacto_principal) {
        this.contacto_principal = contacto_principal;
    }
    
    public boolean insertarProveedor(Proveedor proveedor){
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO proveedor (codigo ,nit, razon_social"
                    + ", direccion, telefono, pagina_web, email, contacto_principal"
                    + ") VALUES(?,?,?,?,?,?,?,?)");
            consulta.setInt(1, proveedor.getCodigo());
            consulta.setString(2, proveedor.getNit());
            consulta.setString(3, proveedor.getRazon_social());
            consulta.setString(4, proveedor.getDireccion());
            consulta.setInt(5, proveedor.getTelefono());
            consulta.setString(6, proveedor.getPagina_web());
            consulta.setString(7, proveedor.getEmail());
            consulta.setString(8, proveedor.getContacto_principal());
            
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at insert a proveedor", ex);
            error.showAndWait();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at insert a proveedor", ex);
            error.showAndWait();
            return false;
        }
        return true;
    }

    public boolean editarProveedor(Proveedor proveedor){
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE proveedor SET nit = ?, razon_social = ?,"
                    + " direccion = ?, telefono = ?, pagina_web = ?, email = ?, contacto_principal = ?"
                    + " WHERE codigo = ?");
            consulta.setString(1, proveedor.getNit());
            consulta.setString(2, proveedor.getRazon_social());
            consulta.setString(3, proveedor.getDireccion());
            consulta.setInt(4, proveedor.getTelefono());
            consulta.setString(5, proveedor.getPagina_web());
            consulta.setString(6, proveedor.getEmail());
            consulta.setString(7, proveedor.getContacto_principal());
            consulta.setInt(8, proveedor.getCodigo());
            
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at update a proveedor", ex);
            error.showAndWait();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at update a proveedor", ex);
            error.showAndWait();
            return false;
        }
        return true;
    }
    
    
    public boolean eliminarProveedor(Proveedor proveedor) {
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta = conexion.prepareStatement("DELETE FROM proveedor WHERE codigo = ?");
            consulta.setInt(1, proveedor.getCodigo());
            consulta.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at delete a proveedor", ex);
            error.showAndWait();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at delete a proveedor", ex);
            error.showAndWait();
            return false;
        }
        return true;
    }
}

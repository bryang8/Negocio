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
public class Cliente {
    private String nit;
    private String dpi;
    private String nombre;
    private String direccion;
    private int telefono;
    private String email;
    Connection conexion = null;

    public Cliente() {
    }

    public Cliente(String nit, String dpi, String nombre, String direccion, int telefono, String email) {
        this.nit = nit;
        this.dpi = dpi;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean insertarCliente(Cliente cliente){
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO cliente (nit, dpi"
                    + ", nombre, telefono, direccion, email"
                    + ") VALUES(?,?,?,?,?,?)");
            consulta.setString(1, cliente.getNit());
            consulta.setString(2, cliente.getDpi());
            consulta.setString(3, cliente.getNombre());
            consulta.setInt(4, cliente.getTelefono());
            consulta.setString(5, cliente.getDireccion());
            consulta.setString(6, cliente.getEmail());
            
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at insert a client", ex);
            error.showAndWait();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at insert a client", ex);
            error.showAndWait();
            return false;
        }
        return true;
    }

    public boolean editarCliente(Cliente cliente, String nitViejo){
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("UPDATE cliente SET nit = ?, dpi = ?,"
                    + " nombre = ?, telefono = ?, direccion = ?, email = ?"
                    + " WHERE nit = "+ nitViejo);
            consulta.setString(1, cliente.getNit());
            consulta.setString(2, cliente.getDpi());
            consulta.setString(3, cliente.getNombre());
            consulta.setInt(4, cliente.getTelefono());
            consulta.setString(5, cliente.getDireccion());
            consulta.setString(6, cliente.getEmail());
            
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at update a client", ex);
            error.showAndWait();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at update a client", ex);
            error.showAndWait();
            return false;
        }
        return true;
    }
    
    
    public boolean eliminarCliente(Cliente cliente) {
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta = conexion.prepareStatement("DELETE FROM cliente WHERE nit = ?");
            consulta.setString(1, cliente.getNit());
            consulta.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at delete a client", ex);
            error.showAndWait();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error at delete a client", ex);
            error.showAndWait();
            return false;
        }
        return true;
    }
}

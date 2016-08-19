/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import negocio.Main;
import negocio.beans.Cliente;
import negocio.beans.Compra;
import negocio.beans.Producto;
import negocio.beans.Proveedor;
import negocio.helpers.Conexion;
import negocio.helpers.Dialogs;

/**
 *
 * @author bryan
 */
public class AddCompraController implements Initializable{
    private ObservableList<String> proveedores = FXCollections.observableArrayList();
    private ObservableList<String> productos = FXCollections.observableArrayList();

    
    Connection conexion = null;
    
    @FXML private ComboBox cmbProveedor;
    @FXML private ComboBox cmbProducto;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtPrecio;
    @FXML private Label lblTotal;
    
    private Stage dialogStage;
    private Main mainApp;
    private boolean presionadoOk;
    private int cantidad;
    private double precio;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setComboBoxes();
        txtCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
           cantidad = Integer.parseInt(newValue);
           lblTotal.setText(String.valueOf(cantidad * precio));
        });
        txtPrecio.textProperty().addListener((observable, oldValue, newValue) -> {
           precio = Double.parseDouble(newValue);
           lblTotal.setText(String.valueOf(cantidad * precio));
        });
    }
    
    public boolean fuePresionadoOk(){
        return this.presionadoOk;
    }
    
    private void setComboBoxes(){
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta = conexion.prepareStatement
                ("SELECT contacto_principal FROM proveedor");
            ResultSet resultado_ = consulta.executeQuery();
            while(resultado_.next()){
               proveedores.add(resultado_.getString("contacto_principal"));
            }
            cmbProveedor.setItems(proveedores);
            
            PreparedStatement consulta_ = conexion.prepareStatement("SELECT descripcion FROM producto;"
            );
            ResultSet resultado = consulta_.executeQuery();
            while(resultado.next()){
               productos.add(resultado.getString("descripcion"));
            }
            cmbProducto.setItems(productos);
        } catch (SQLException ex) {
            Logger.getLogger(EditProductoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditProductoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NullPointerException ex){
            
        }
    }
    
    private int getCodProveedor(String nombre) throws SQLException{
       PreparedStatement consulta = conexion.prepareStatement("SELECT codigo FROM proveedor "
               + "WHERE contacto_principal = ?" );
       consulta.setString(1, nombre);
       ResultSet resultado = consulta.executeQuery();
       while(resultado.next()){
           return resultado.getInt("codigo");
       }
       return 0;
    }
    
    private int getCodProducto(String nombre) throws SQLException{
       PreparedStatement consulta = conexion.prepareStatement("SELECT cod_producto FROM producto "
               + "WHERE descripcion = ?" );
       consulta.setString(1, nombre);
       ResultSet resultado = consulta.executeQuery();
       while(resultado.next()){
           return resultado.getInt("cod_producto");
       }
       return 0;
    }
    
    @FXML
    private void cambiosAceptados() throws SQLException{
        java.util.Date dates=new java.util.Date();
        long fechas = dates.getTime();
        java.sql.Date d=new java.sql.Date(fechas);
        
        Compra compra = new Compra();
        compra.setFecha(d);
        compra.setCod_producto(getCodProducto(cmbProducto.getValue().toString()));
        compra.setCod_proveedor(getCodProveedor(cmbProveedor.getValue().toString()));
        compra.setTotal(cantidad * precio);
        compra.setCantidad(cantidad);
        compra.setPrecio(precio);
        
        presionadoOk = compra.insertarCompra(compra);    
        dialogStage.close() ;
    }
    
    @FXML
    private void cambiosCancelados(){
        dialogStage.close();
    }
}


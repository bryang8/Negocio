/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
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
import negocio.beans.Producto;
import negocio.beans.Venta;
import negocio.helpers.Conexion;
import negocio.helpers.Dialogs;

/**
 *
 * @author bryan
 */
public class AddVentaController implements Initializable{
    private ObservableList<String> clientes = FXCollections.observableArrayList();
    private ObservableList<Producto> productos = FXCollections.observableArrayList();
    private ObservableList<String> productosDescripciones = FXCollections.observableArrayList();

    
    Connection conexion = null;
    
    @FXML private ComboBox cmbCliente;
    @FXML private ComboBox cmbProducto;
    @FXML private TextField txtCantidad;
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
           if(newValue.isEmpty()){
               cantidad = 0;
           }else{
               cantidad = Integer.parseInt(newValue);
           }
           setTotal();
        });
        cmbProducto.valueProperty().addListener((observable, oldValue, newValue) -> {
           setTotal();
        });
    }
    
    public void setTotal(){
        revisarExistencia(cmbProducto.getValue().toString());
        if(cmbProducto.getValue() != null){
            if (cantidad<12){
                precio = getPrecioUnidad(cmbProducto.getValue().toString());
            }
            else if (cantidad >11 && cantidad < 36) {
                precio = getPrecioDocena(cmbProducto.getValue().toString());
            }
            else{
                precio = getPrecioMayor(cmbProducto.getValue().toString());
            }
            lblTotal.setText("Q " + String.valueOf(cantidad * precio));
        }
    }
    
    public boolean fuePresionadoOk(){
        return this.presionadoOk;
    }
    
    private void setComboBoxes(){
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta = conexion.prepareStatement
                ("SELECT nombre FROM cliente");
            ResultSet resultado_ = consulta.executeQuery();
            while(resultado_.next()){
               clientes.add(resultado_.getString("nombre"));
            }
            cmbCliente.setItems(clientes);
            PreparedStatement consulta_ = conexion.prepareStatement("SELECT cod_producto ,"
                 + "descripcion,id_categoria, precio_unidad,"
                 + "precio_docena , precio_mayor, existencia,"
                 + "tipo_empaque FROM producto;"
            );
            ResultSet resultado = consulta_.executeQuery();
            while(resultado.next()){
               productos.add(new Producto(
               resultado.getInt("cod_producto"), resultado.getString("descripcion"), 
                       resultado.getInt("id_categoria"),resultado.getDouble("precio_unidad"),
                       resultado.getDouble("precio_docena"), resultado.getDouble("precio_mayor"),
                       resultado.getInt("existencia"),resultado.getString("tipo_empaque"),
                       "categoria"));
               productosDescripciones.add(resultado.getString("descripcion"));
            }
            cmbProducto.setItems(productosDescripciones);
        } catch (SQLException ex) {
            Logger.getLogger(EditProductoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditProductoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NullPointerException ex){
            
        }
    }
    
    private String getNitCliente(String nombre) throws SQLException{
       Cliente cliente = null;
       PreparedStatement consulta = conexion.prepareStatement("SELECT nit FROM cliente WHERE nombre = ?" );
       consulta.setString(1, nombre);
       ResultSet resultado = consulta.executeQuery();
       while(resultado.next()){
           return resultado.getString("nit");
       }
       return "";
    }
    
    private int getCodProducto(String nombre) throws SQLException{
       for (Producto p : productos){
            if(p.getDescripcion().equals(nombre)){
            return p.getCod_producto();
            }
       }
       return 0;
    }
    
    private double getPrecioUnidad(String nombre){
       for (Producto p : productos){
            if(p.getDescripcion().equals(nombre)){
            return p.getPrecio_unidad();
            }
       }
       return 0;
    }
    
    private double getPrecioDocena(String nombre){
       for (Producto p : productos){
            if(p.getDescripcion().equals(nombre)){
            return p.getPrecio_docena();
            }
       }
       return 0;
    }
    
    private double getPrecioMayor(String nombre){
       for (Producto p : productos){
            if(p.getDescripcion().equals(nombre)){
            return p.getPrecio_mayor();
            }
       }
       return 0;
    }
    
    private boolean revisarExistencia(String nombre){
        for (Producto p : productos){
            if(p.getDescripcion().equals(nombre)){
                if (p.getExistencia() >= cantidad){
                    return true;
                }
            }
       }
       Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "No hay suficiente producto en existencia");
       error.showAndWait();
       txtCantidad.setText("0");
       return false;
    }
    
    @FXML
    private void cambiosAceptados() throws SQLException{
        java.util.Date dates=new java.util.Date();
        long fechas = dates.getTime();
        java.sql.Date d=new java.sql.Date(fechas);
        
        Venta venta = new Venta();
        venta.setFecha(d);
        venta.setCod_producto(getCodProducto(cmbProducto.getValue().toString()));
        venta.setNit_cliente(getNitCliente(cmbCliente.getValue().toString()));
        venta.setTotal(cantidad * precio);
        venta.setCantidad(cantidad);
        
        presionadoOk = venta.insertarVenta(venta);    
        dialogStage.close() ;
    }
    
    @FXML
    private void cambiosCancelados(){
        dialogStage.close();
    }
}

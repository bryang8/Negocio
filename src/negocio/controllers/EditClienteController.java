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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import negocio.Main;
import negocio.beans.Categoría;
import negocio.beans.Cliente;
import negocio.beans.Producto;
import negocio.helpers.Conexion;
import negocio.helpers.Dialogs;

/**
 *
 * @author bryan
 */
public class EditClienteController implements Initializable {
    Connection conexion = null;
    
    @FXML TextField txtNit;
    @FXML TextField txtDpi;
    @FXML TextField txtNombre;
    @FXML TextField txtTelefono;
    @FXML TextField txtDireccion;
    @FXML TextField txtEmail;
    
    
    private Stage dialogStage;
    private Cliente cliente;
    private boolean presionadoOk;
    private Main.CRUDOperation operacion;
    private Main mainApp;
    private String nitviejo = "";
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setOperacion(Main.CRUDOperation operacion) {
        this.operacion = operacion;
        
    }
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        txtNit.setText(cliente.getNit());
        txtDpi.setText(cliente.getDpi());
        txtNombre.setText(cliente.getNombre());
        txtTelefono.setText(String.valueOf(cliente.getTelefono()));
        txtDireccion.setText(cliente.getDireccion());
        txtEmail.setText(cliente.getEmail());
        if (operacion.equals(Main.CRUDOperation.Update)){
            nitviejo = cliente.getNit();
        }
    }
    
    public boolean fuePresionadoOk(){
        return this.presionadoOk;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
   @FXML
    private void cambiosAceptados() throws SQLException{
        if(esProductoValido()){
            cliente.setNit(txtNit.getText());
            cliente.setDpi(txtDpi.getText());
            cliente.setNombre(txtNombre.getText());
            cliente.setDireccion(txtDireccion.getText());
            cliente.setTelefono(Integer.parseInt(txtTelefono.getText()));
            cliente.setEmail(txtEmail.getText());
            
            if (operacion.equals(Main.CRUDOperation.Create)){
                presionadoOk = cliente.insertarCliente(cliente);
            }
            if (operacion.equals(Main.CRUDOperation.Update)){
                presionadoOk = cliente.editarCliente(cliente, nitviejo);
            }
            dialogStage.close() ;
        }
    }
    
    @FXML
    private void cambiosCancelados(){
        dialogStage.close();
    }
    
    private boolean esProductoValido(){
        if(txtNit.getText() == null || txtNit.getText().length() == 0){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid nit");
            error.showAndWait();
            txtNit.requestFocus();
            return false;
        }
        if(txtNombre.getText() == null || txtNombre.getText().length() == 0){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid name");
            error.showAndWait();
            txtDpi.requestFocus();
            return false;
        }
        if(!isNumeric(txtDpi.getText())){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid dpi");
            error.showAndWait();
            txtDpi.requestFocus();
            return false;
        }
        if(!isNumeric(txtTelefono.getText())){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid phone number");
            error.showAndWait();
            txtTelefono.requestFocus();
            return false;
        }
        if(txtDireccion.getText() == null || txtDireccion.getText().length() == 0){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid address");
            error.showAndWait();
            txtDireccion.requestFocus();
            return false;
        }
        if(txtEmail.getText() == null || txtEmail.getText().length() == 0){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid email");
            error.showAndWait();
            txtDpi.requestFocus();
            return false;
        }
        return true;
    } 
    
    public static boolean isNumeric(String str) {
        return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("")==false);
    }
}

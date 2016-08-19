/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import negocio.Main;
import negocio.beans.Proveedor;
import negocio.helpers.Dialogs;

/**
 *
 * @author bryan
 */
public class EditProveedorController implements Initializable{
    @FXML TextField txtNit;
    @FXML TextField txtRazon;
    @FXML TextField txtDireccion;
    @FXML TextField txtTelefono;
    @FXML TextField txtPagina;
    @FXML TextField txtEmail;
    @FXML TextField txtContacto;
    
    private Stage dialogStage;
    private Proveedor proveedor;
    private boolean presionadoOk;
    private Main.CRUDOperation operacion;
    private Main mainApp;
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setOperacion(Main.CRUDOperation operacion) {
        this.operacion = operacion;
    }
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        txtNit.setText(proveedor.getNit());
        txtRazon.setText(proveedor.getRazon_social());
        txtDireccion.setText(proveedor.getDireccion());
        txtTelefono.setText(String.valueOf(proveedor.getTelefono()));
        txtPagina.setText(proveedor.getPagina_web());
        txtEmail.setText(proveedor.getEmail());
        txtContacto.setText(proveedor.getContacto_principal());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
    
    public boolean fuePresionadoOk(){
        return this.presionadoOk;
    }
    
    @FXML
    private void cambiosAceptados() throws SQLException{
        if(esProveedorValido()){
            proveedor.setNit(txtNit.getText());
            proveedor.setRazon_social(txtRazon.getText());
            proveedor.setDireccion(txtDireccion.getText());
            proveedor.setTelefono(Integer.parseInt(txtTelefono.getText()));
            proveedor.setPagina_web(txtPagina.getText());
            proveedor.setEmail(txtEmail.getText());
            proveedor.setContacto_principal(txtContacto.getText());
            if (operacion.equals(Main.CRUDOperation.Create)){
                presionadoOk = proveedor.insertarProveedor(proveedor);
            }
            if (operacion.equals(Main.CRUDOperation.Update)){
                presionadoOk = proveedor.editarProveedor(proveedor);
            }
            dialogStage.close() ;
        }
    }
    
    @FXML
    private void cambiosCancelados(){
        dialogStage.close();
    }
    
    private boolean esProveedorValido(){
        if(txtNit.getText() == null || txtNit.getText().length() == 0){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid Nit");
            error.showAndWait();
            txtNit.requestFocus();
            return false;
        }
        if(txtRazon.getText() == null || txtRazon.getText().length() == 0){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid reason");
            error.showAndWait();
            txtRazon.requestFocus();
            return false;
        }
        if(txtDireccion.getText() == null || txtDireccion.getText().length() == 0){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid address");
            error.showAndWait();
            txtDireccion.requestFocus();
            return false;
        }
        if(!isNumeric(txtTelefono.getText())){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid phone number");
            error.showAndWait();
            txtTelefono.requestFocus();
            return false;
        }
        if(txtPagina.getText() == null || txtPagina.getText().length() == 0){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid page");
            error.showAndWait();
            txtPagina.requestFocus();
            return false;
        }
        if(txtEmail.getText() == null || txtEmail.getText().length() == 0){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid address");
            error.showAndWait();
            txtEmail.requestFocus();
            return false;
        }
        if(txtContacto.getText() == null || txtContacto.getText().length() == 0){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid contact");
            error.showAndWait();
            txtContacto.requestFocus();
            return false;
        }
        return true;
    } 
    
    public static boolean isNumeric(String str) {
        return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("")==false);
    }
}

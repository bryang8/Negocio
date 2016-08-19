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
import negocio.beans.Producto;
import negocio.helpers.Conexion;
import negocio.helpers.Dialogs;

/**
 *
 * @author bryan
 */
public class EditProductoController implements Initializable {
    private ObservableList<String> categorias = FXCollections.observableArrayList();
    Connection conexion = null;
    
    @FXML TextField txtDescripcion;
    @FXML ComboBox cmbCategoria;
    @FXML TextField txtPrecioU;
    @FXML TextField txtPrecioD;
    @FXML TextField txtPrecioM;
    @FXML TextField txtTipoEmpaque;
    @FXML TextField txtExistencia;
    
    
    private Stage dialogStage;
    private Producto producto;
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
    
    public void setProducto(Producto producto) {
        this.producto = producto;
        txtDescripcion.setText(producto.getDescripcion());
        cmbCategoria.setValue(producto.getCategoria());
        txtPrecioU.setText(String.valueOf(producto.getPrecio_unidad()));
        txtPrecioD.setText(String.valueOf(producto.getPrecio_docena()));
        txtPrecioM.setText(String.valueOf(producto.getPrecio_mayor()));
        txtTipoEmpaque.setText(producto.getTipo_empaque());
        txtExistencia.setText(String.valueOf(producto.getExistencia()));
    }
    
    public boolean fuePresionadoOk(){
        return this.presionadoOk;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCategorias();
    }
    
    private void setCategorias(){
        try {
            conexion = Conexion.obtener();
            PreparedStatement consulta = conexion.prepareStatement
                ("SELECT nombre FROM categoria");
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            categorias.add(resultado.getString("nombre"));
         }
         cmbCategoria.setItems(categorias);
        } catch (SQLException ex) {
            Logger.getLogger(EditProductoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditProductoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NullPointerException ex){
            
        }
    }
    
    private int setCategoria(String nombre) throws SQLException{
       Categoría categoria = null;
       PreparedStatement consulta = conexion.prepareStatement("SELECT id_categoria FROM categoria WHERE nombre = ?" );
       consulta.setString(1, nombre);
       ResultSet resultado = consulta.executeQuery();
       while(resultado.next()){
           return resultado.getInt("id_categoria");
       }
       return 0;
    }
    
   @FXML
    private void cambiosAceptados() throws SQLException{
        if(esProductoValido()){
            producto.setDescripcion(txtDescripcion.getText());
            producto.setCategoria(cmbCategoria.getValue().toString());
            producto.setId_categoria(setCategoria(producto.getCategoria()));
            producto.setExistencia(Integer.parseInt(txtExistencia.getText()));
            producto.setPrecio_unidad(Double.parseDouble(txtPrecioU.getText()));
            producto.setPrecio_docena(Double.parseDouble(txtPrecioD.getText()));
            producto.setPrecio_mayor(Double.parseDouble(txtPrecioM.getText()));
            producto.setTipo_empaque(txtTipoEmpaque.getText());
            
            if (operacion.equals(Main.CRUDOperation.Create)){
                presionadoOk = producto.insertarProducto(producto);
            }
            if (operacion.equals(Main.CRUDOperation.Update)){
                presionadoOk = producto.editarProdcto(producto);
            }
            dialogStage.close() ;
        }
    }
    
    @FXML
    private void cambiosCancelados(){
        dialogStage.close();
    }
    
    private boolean esProductoValido(){
        if(txtDescripcion.getText() == null || txtDescripcion.getText().length() == 0){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid Description");
            error.showAndWait();
            txtDescripcion.requestFocus();
            return false;
        }
        if(txtTipoEmpaque.getText() == null || txtTipoEmpaque.getText().length() == 0){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid Type of package");
            error.showAndWait();
            txtTipoEmpaque.requestFocus();
            return false;
        }
        if(cmbCategoria.getSelectionModel().getSelectedIndex() < 0){
             Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Please select a category");
            error.showAndWait();
            cmbCategoria.requestFocus();
            return false;
        }
        if(!isNumeric(txtPrecioU.getText())){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid unity price");
            error.showAndWait();
            txtPrecioU.requestFocus();
            return false;
        }
        if(!isNumeric(txtPrecioD.getText())){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid dozen price");
            error.showAndWait();
            txtPrecioD.requestFocus();
            return false;
        }
        if(!isNumeric(txtPrecioM.getText())){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid major price");
            error.showAndWait();
            txtPrecioM.requestFocus();
            return false;
        }
        if(!isNumeric(txtExistencia.getText())){
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "Invalid dozen price");
            error.showAndWait();
            txtPrecioD.requestFocus();
            return false;
        }
        return true;
    } 
    
    public static boolean isNumeric(String str) {
        return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("")==false);
    }
}

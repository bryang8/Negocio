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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.Main;
import negocio.beans.Categoría;
import negocio.beans.Producto;
import negocio.beans.Proveedor;
import negocio.helpers.Conexion;
import negocio.helpers.Dialogs;

/**
 *
 * @author bryan
 */
public class ProveedoresController implements Initializable{
    private Main mainApp;
    @FXML private TableView<Proveedor> tb_proveedores;
    @FXML private TableColumn<Proveedor, String> tbcNit;
    @FXML private TableColumn<Proveedor, String> tbcRazon;
    @FXML private TableColumn<Proveedor, String> tbcDireccion;
    @FXML private TableColumn<Proveedor, Integer> tbcTelefono;
    @FXML private TableColumn<Proveedor, String> tbcPagina;
    @FXML private TableColumn<Proveedor, String> tbcEmail;
    @FXML private TableColumn<Proveedor, Integer> tbcContactoP;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            refresh();
        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    public void setProveedores() throws SQLException, ClassNotFoundException{
        tbcNit.setCellValueFactory(new PropertyValueFactory<>("nit"));
        tbcRazon.setCellValueFactory(new PropertyValueFactory<>("razon_social"));
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion")); 
        tbcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono")); 
        tbcPagina.setCellValueFactory(new PropertyValueFactory<>("pagina_web")); 
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("email")); 
        tbcContactoP.setCellValueFactory(new PropertyValueFactory<>("contacto_principal"));
        tb_proveedores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tb_proveedores.setItems(listarProveedores(Conexion.obtener()));
    }
    
    public ObservableList<Proveedor> listarProveedores(Connection conexion) throws SQLException{
      ObservableList<Proveedor> proveedores = FXCollections.observableArrayList();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT codigo ,"
                 + "nit,razon_social, direccion,"
                 + "telefono , pagina_web, email,"
                 + "contacto_principal FROM proveedor;"
         );
         ResultSet rs = consulta.executeQuery();
         while(rs.next()){
            proveedores.add(new Proveedor(rs.getInt("codigo"), rs.getString("nit"),
            rs.getString("razon_social"), rs.getString("direccion"), rs.getInt("telefono"),
            rs.getString("pagina_web"),rs.getString("email"), rs.getString("contacto_principal")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return proveedores;
   }
    
    @FXML
    public void refresh() throws SQLException, ClassNotFoundException{
        setProveedores();
    }
    
    @FXML
    private void insertarProveedor() throws SQLException, ClassNotFoundException{
        Proveedor proveedorTemporal = new Proveedor();
        boolean okPresionado = mainApp.showEditProveedor(proveedorTemporal, Main.CRUDOperation.Create,"Agregar Proveedor");
        refresh();
    }
    
    @FXML
    private void modificarProveedor() throws SQLException, ClassNotFoundException {
        Proveedor producto = tb_proveedores.getSelectionModel().getSelectedItem();
        if (producto != null) {
            boolean okClicked = mainApp.showEditProveedor(producto, Main.CRUDOperation.Update, "Editar Proveedor");
            if (okClicked) {
                refresh();
            }

        } else {
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "No provider selected");
            error.showAndWait();
        }
    }
    
    @FXML
    private void eliminarProveedor(){
        int selectedIndex = tb_proveedores.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Proveedor p  = tb_proveedores.getSelectionModel().getSelectedItem();
            Alert pregunta = Dialogs.getDialog(Alert.AlertType.CONFIRMATION, "Negocio", null, "Do you want remove this provider?");
            Optional<ButtonType> result = pregunta.showAndWait();
            if (result.get() == ButtonType.OK){
                if(p.eliminarProveedor(p)){
                    tb_proveedores.getItems().remove(selectedIndex);
                }
            }
        } else {
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "No provider selected");
            error.showAndWait();
        }
    }
}

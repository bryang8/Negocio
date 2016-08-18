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
import negocio.beans.Cliente;
import negocio.beans.Producto;
import negocio.helpers.Conexion;
import negocio.helpers.Dialogs;

/**
 *
 * @author bryan
 */
public class ClienteController implements Initializable{

    private Main mainApp;
    @FXML private TableView<Cliente> tb_clientes;
    @FXML private TableColumn<Cliente, String> tbcNit;
    @FXML private TableColumn<Cliente, String> tbcDpi;
    @FXML private TableColumn<Cliente, String> tbcNombre;
    @FXML private TableColumn<Cliente, String> tbcDireccion;
    @FXML private TableColumn<Cliente, Integer> tbcTelefono;
    @FXML private TableColumn<Cliente, String> tbcEmail;
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       try {
            setClientes();
        } catch (SQLException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void refresh() throws SQLException, ClassNotFoundException{
        setClientes();
    }
    
    public void setClientes() throws SQLException, ClassNotFoundException{
        tbcNit.setCellValueFactory(new PropertyValueFactory<>("nit"));
        tbcDpi.setCellValueFactory(new PropertyValueFactory<>("dpi"));
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre")); 
        tbcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion")); 
        tbcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono")); 
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("email")); 
        tb_clientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tb_clientes.setItems(listarClientes(Conexion.obtener()));
    }
    
    public ObservableList<Cliente> listarClientes(Connection conexion) throws SQLException{
      ObservableList<Cliente> clientes = FXCollections.observableArrayList();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT nit ,"
                 + "dpi, nombre, telefono, direccion , email FROM cliente;"
         );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            clientes.add(new Cliente(resultado.getString("nit"),
            resultado.getString("dpi"), resultado.getString("nombre"),resultado.getString("direccion"),
            resultado.getInt("telefono"), resultado.getString("email")));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return clientes;
   }
    
    @FXML
    private void agregarCliente() throws SQLException, ClassNotFoundException{
        Cliente clienteTemporal = new Cliente();
        boolean okPresionado = mainApp.showEditCliente(clienteTemporal, Main.CRUDOperation.Create,"Agregar Cliente");
        refresh();
    }
    
    @FXML
    private void modificarCliente() throws SQLException, ClassNotFoundException {
        Cliente cliente = tb_clientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            boolean okClicked = mainApp.showEditCliente(cliente, Main.CRUDOperation.Update, "Editar Cliente");
            if (okClicked) {
                refresh();
            }

        } else {
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "No client selected");
            error.showAndWait();
        }
    }
    
    @FXML
    private void eliminarCliente(){
        int selectedIndex = tb_clientes.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Cliente cliente  = tb_clientes.getSelectionModel().getSelectedItem();
            Alert pregunta = Dialogs.getDialog(Alert.AlertType.CONFIRMATION, "Negocio", null, "Do you want remove this client?");
            Optional<ButtonType> result = pregunta.showAndWait();
            if (result.get() == ButtonType.OK){
                if(cliente.eliminarCliente(cliente)){
                    tb_clientes.getItems().remove(selectedIndex);
                }
            }
        } else {
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "No client selected");
            error.showAndWait();
        }
    }
}

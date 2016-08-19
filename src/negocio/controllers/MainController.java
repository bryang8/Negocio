/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import negocio.Main;

/**
 * FXML Controller class
 *
 * @author bryan
 */
public class MainController implements Initializable {
    
    private Main mainApp;
    private Pane productosLayout;
    private Pane clientesLayout;
    private Pane proveedoresLayout;
    private Pane historialLayout;
    
    @FXML Tab tab_productos;
    @FXML Tab tab_clientes;
    @FXML Tab tab_proveedores;
    @FXML Tab tab_historial;
    
      /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
    }
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        loadProductos();
        loadClientes();
        loadProveedores();
        loadHistorial();
    }
    
    public void loadProductos(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/Productos.fxml"));
            productosLayout = (Pane) loader.load();
            ProductosController controller = loader.getController();
            controller.setMainApp(mainApp);
            tab_productos.setContent(productosLayout);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadClientes(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/Clientes.fxml"));
            clientesLayout = (Pane) loader.load();
            ClienteController controller = loader.getController();
            controller.setMainApp(mainApp);
            tab_clientes.setContent(clientesLayout);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadProveedores(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/Proveedores.fxml"));
            proveedoresLayout = (Pane) loader.load();
            ProveedoresController controller = loader.getController();
            controller.setMainApp(mainApp);
            tab_proveedores.setContent(proveedoresLayout);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadHistorial(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/Historial.fxml"));
            historialLayout = (Pane) loader.load();
            HistorialController controller = loader.getController();
            controller.setMainApp(mainApp);
            tab_historial.setContent(historialLayout);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void showAddVenta(){
        mainApp.showAddVenta();
    }
    
    
    @FXML
    private void showAddCompra(){
        mainApp.showAddCompra();
    }
    
}

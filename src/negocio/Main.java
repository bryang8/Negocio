/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import negocio.beans.Cliente;
import negocio.beans.Producto;
import negocio.beans.Proveedor;
import negocio.controllers.AddCompraController;
import negocio.controllers.AddVentaController;
import negocio.controllers.EditClienteController;
import negocio.controllers.EditProductoController;
import negocio.controllers.EditProveedorController;
import negocio.controllers.MainController;
import negocio.helpers.Dialogs;

/**
 *
 * @author bryan
 */
public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Resource resource = new Resource();
    public enum CRUDOperation {None, Create, Read, Update, Delete};  
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Negocio");        
        this.primaryStage.setResizable(false);
        this.primaryStage.getIcons().add(resource.getIcon());
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/Main.fxml"));
            rootLayout = (BorderPane) loader.load();
            MainController controller = loader.getController();
            controller.setMainApp(this);
            Scene scene = new Scene(rootLayout);
            
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public boolean showEditProducto(Producto producto, CRUDOperation operacion, String title){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/EditProducto.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.getIcons().add(resource.getIcon());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene escena = new Scene(pane);
            dialogStage.setScene(escena);
            dialogStage.setResizable(false);
            EditProductoController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOperacion(operacion);
            controller.setMainApp(this);
            controller.setProducto(producto);
            
            dialogStage.showAndWait();
            
            return controller.fuePresionadoOk();
            
        }
        catch(Exception e){
            e.printStackTrace();
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error al cargar el archivo FXML", e);
            error.showAndWait();
            return false;
        }
    }
    
    public boolean showEditCliente(Cliente cliente, CRUDOperation operacion, String title){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/EditCliente.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.getIcons().add(resource.getIcon());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene escena = new Scene(pane);
            dialogStage.setScene(escena);
            dialogStage.setResizable(false);
            EditClienteController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOperacion(operacion);
            controller.setMainApp(this);
            controller.setCliente(cliente);
            
            dialogStage.showAndWait();
            
            return controller.fuePresionadoOk();
            
        }
        catch(Exception e){
            e.printStackTrace();
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error al cargar el archivo FXML", e);
            error.showAndWait();
            return false;
        }
    }
    
     public boolean showEditProveedor(Proveedor proveedor, CRUDOperation operacion, String title){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/EditProveedor.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.getIcons().add(resource.getIcon());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene escena = new Scene(pane);
            dialogStage.setScene(escena);
            dialogStage.setResizable(false);
            EditProveedorController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOperacion(operacion);
            controller.setMainApp(this);
            controller.setProveedor(proveedor);
            
            dialogStage.showAndWait();
            
            return controller.fuePresionadoOk();
            
        }
        catch(Exception e){
            e.printStackTrace();
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error al cargar el archivo FXML", e);
            error.showAndWait();
            return false;
        }
    
    }
     
    public boolean showAddVenta(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/AddVenta.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Registrar venta");
            dialogStage.getIcons().add(resource.getIcon());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene escena = new Scene(pane);
            dialogStage.setScene(escena);
            dialogStage.setResizable(false);
            AddVentaController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            
            dialogStage.showAndWait();
            
            return controller.fuePresionadoOk();
            
        }
        catch(Exception e){
            e.printStackTrace();
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error al cargar el archivo FXML", e);
            error.showAndWait();
            return false;
        }
    
    }
    
     public boolean showAddCompra(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/AddCompra.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Registrar compra");
            dialogStage.getIcons().add(resource.getIcon());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene escena = new Scene(pane);
            dialogStage.setScene(escena);
            dialogStage.setResizable(false);
            AddCompraController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            
            dialogStage.showAndWait();
            
            return controller.fuePresionadoOk();
            
        }
        catch(Exception e){
            e.printStackTrace();
            Alert error = Dialogs.getErrorDialog(Alert.AlertType.ERROR, "Negocio", null, "Error al cargar el archivo FXML", e);
            error.showAndWait();
            return false;
        }
    
    }
}

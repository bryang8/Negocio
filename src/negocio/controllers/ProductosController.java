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
import java.util.ArrayList;
import java.util.List;
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
import negocio.helpers.Conexion;
import negocio.helpers.Dialogs;

/**
 * FXML Controller class
 *
 * @author bryan
 */
public class ProductosController implements Initializable {
    
    private Main mainApp;
    @FXML private TableView<Producto> tb_productos;
    @FXML private TableColumn<Producto, String> tbcDescripcion;
    @FXML private TableColumn<Producto, String> tbcCategoria;
    @FXML private TableColumn<Producto, Double> tbcPrecioU;
    @FXML private TableColumn<Producto, Double> tbcPrecioD;
    @FXML private TableColumn<Producto, Double> tbcPrecioM;
    @FXML private TableColumn<Producto, String> tbcTipo;
    @FXML private TableColumn<Producto, Integer> tbcExistencia;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setProductos();
        } catch (SQLException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    public void refresh() throws SQLException, ClassNotFoundException{
        setProductos();
    }
    
    public void setProductos() throws SQLException, ClassNotFoundException{
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tbcCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tbcPrecioU.setCellValueFactory(new PropertyValueFactory<>("precio_unidad")); 
        tbcPrecioD.setCellValueFactory(new PropertyValueFactory<>("precio_docena")); 
        tbcPrecioM.setCellValueFactory(new PropertyValueFactory<>("precio_mayor")); 
        tbcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo_empaque")); 
        tbcExistencia.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        tb_productos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tb_productos.setItems(listarProductos(Conexion.obtener()));
    }
    
    public ObservableList<Producto> listarProductos(Connection conexion) throws SQLException{
      ObservableList<Producto> productos = FXCollections.observableArrayList();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT cod_producto ,"
                 + "descripcion,id_categoria, precio_unidad,"
                 + "precio_docena , precio_mayor, existencia,"
                 + "tipo_empaque FROM producto;"
         );
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            productos.add(new Producto(
            resultado.getInt("cod_producto"), resultado.getString("descripcion"), 
                    resultado.getInt("id_categoria"),resultado.getDouble("precio_unidad"),
                    resultado.getDouble("precio_docena"), resultado.getDouble("precio_mayor"),
                    resultado.getInt("existencia"),resultado.getString("tipo_empaque"),
                    getCategoria(resultado.getInt("id_categoria"), conexion)));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return productos;
   }

    private String getCategoria(int num, Connection conexion) throws SQLException {
       Categoría categoria = null;
       PreparedStatement consulta = conexion.prepareStatement("SELECT nombre FROM categoria WHERE id_categoria = ?" );
       consulta.setInt(1, num);
       ResultSet resultado = consulta.executeQuery();
       while(resultado.next()){
           return resultado.getString("nombre");
       }
        return "";
    }
    
    @FXML
    private void insertarProducto() throws SQLException, ClassNotFoundException{
        Producto productoTemporal = new Producto();
        boolean okPresionado = mainApp.showEditProducto(productoTemporal, Main.CRUDOperation.Create,"Agregar Producto");
        refresh();
    }
    
    @FXML
    private void modificarProducto() throws SQLException, ClassNotFoundException {
        Producto producto = tb_productos.getSelectionModel().getSelectedItem();
        if (producto != null) {
            boolean okClicked = mainApp.showEditProducto(producto, Main.CRUDOperation.Update, "Editar Producto");
            if (okClicked) {
                refresh();
            }

        } else {
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "No product selected");
            error.showAndWait();
        }
    }
    
    @FXML
    private void eliminarProducto(){
        int selectedIndex = tb_productos.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Producto p  = tb_productos.getSelectionModel().getSelectedItem();
            Alert pregunta = Dialogs.getDialog(Alert.AlertType.CONFIRMATION, "Negocio", null, "Do you want remove this product?");
            Optional<ButtonType> result = pregunta.showAndWait();
            if (result.get() == ButtonType.OK){
                if(p.eliminarProducto(p)){
                    tb_productos.getItems().remove(selectedIndex);
                }
            }
        } else {
            Alert error = Dialogs.getDialog(Alert.AlertType.ERROR, "Negocio", null, "No product selected");
            error.showAndWait();
        }
    }
}

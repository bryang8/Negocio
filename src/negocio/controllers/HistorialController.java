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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.Main;
import negocio.beans.Proveedor;
import negocio.beans.Transaccion;
import negocio.helpers.Conexion;

/**
 *
 * @author bryan
 */
public class HistorialController implements Initializable{
    private Main mainApp;
    
    @FXML private TableView<Transaccion> tb_historial;
    @FXML private TableColumn<Transaccion, String> tbcTipo;
    @FXML private TableColumn<Transaccion, String> tbcFecha;
    @FXML private TableColumn<Transaccion, Integer> tbcCodigo;
    @FXML private TableColumn<Transaccion, String> tbcProducto;
    @FXML private TableColumn<Transaccion, Double> tbcPrecio;
    @FXML private TableColumn<Transaccion, Integer> tbcCantidad;
    @FXML private TableColumn<Transaccion, Double> tbcTotal;
    @FXML private TableColumn<Transaccion, String> tbcNit;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            refresh();
        } catch (SQLException ex) {
            Logger.getLogger(HistorialController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HistorialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setHistorial() throws SQLException, ClassNotFoundException{
        tbcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("cod_proveedor")); 
        tbcProducto.setCellValueFactory(new PropertyValueFactory<>("producto")); 
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio")); 
        tbcCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad")); 
        tbcTotal.setCellValueFactory(new PropertyValueFactory<>("total")); 
        tbcNit.setCellValueFactory(new PropertyValueFactory<>("nit"));
        tb_historial.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tb_historial.setItems(listarHistorial(Conexion.obtener()));
    }
    
    public ObservableList<Transaccion> listarHistorial(Connection conexion) throws SQLException{
      ObservableList<Transaccion> transacciones = FXCollections.observableArrayList();
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT cod_proveedor , cod_producto, "
                 + "cantidad , precio, total, fecha FROM compra;"
         );
         ResultSet rs = consulta.executeQuery();
         while(rs.next()){
            transacciones.add(new Transaccion(rs.getDate("fecha"), "Compra",
            rs.getInt("cod_proveedor"), getDescripcionProducto(rs.getInt("cod_producto"), conexion),
            getNitProveedor(rs.getInt("cod_proveedor"), conexion),
            rs.getDouble("precio"),rs.getInt("cantidad"), rs.getDouble("total")));
         }
         
         PreparedStatement consulta2 = conexion.prepareStatement("SELECT nit_cliente , cod_producto, "
                 + "cantidad , total, fecha FROM factura;"
         );
         ResultSet rs2 = consulta2.executeQuery();
         while(rs2.next()){
            double total = rs2.getDouble("total");
            int cantidad = rs2.getInt("cantidad");
            int cod_producto = rs2.getInt("cod_producto");
            transacciones.add(new Transaccion(rs2.getDate("fecha"), "Venta",
            0, getDescripcionProducto(cod_producto, conexion),
            rs2.getString("nit_cliente"), getPrecio(total , cantidad, cod_producto, conexion),
            cantidad, total));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return transacciones;
   }
    
    private String getDescripcionProducto(int codigo, Connection conexion) throws SQLException{
       PreparedStatement consulta = conexion.prepareStatement("SELECT descripcion FROM producto "
               + "WHERE cod_producto = ?" );
       consulta.setInt(1, codigo);
       ResultSet resultado = consulta.executeQuery();
       while(resultado.next()){
           return resultado.getString("descripcion");
       }
       return "";
    }
    
    private String getNitProveedor(int codigo, Connection conexion) throws SQLException{
       PreparedStatement consulta = conexion.prepareStatement("SELECT nit FROM proveedor "
               + "WHERE codigo = ?" );
       consulta.setInt(1, codigo);
       ResultSet resultado = consulta.executeQuery();
       while(resultado.next()){
           return resultado.getString("nit");
       }
       return "";
    }
    
    private Double getPrecio(double total, int cantidad, int codigo, Connection conexion) throws SQLException{
        double precioU = 1;
        double precioD = 1;
        double precioM = 1;
        PreparedStatement consulta = conexion.prepareStatement("SELECT precio_unidad,"
                + "precio_docena, precio_mayor FROM producto WHERE cod_producto = ?" );
        consulta.setInt(1, codigo);
        ResultSet resultado = consulta.executeQuery();
        while(resultado.next()){
            precioU = resultado.getDouble("precio_unidad");
            precioD = resultado.getDouble("precio_docena");
            precioM = resultado.getDouble("precio_mayor");
        }
        
        if((cantidad * precioU) == total){
            return precioU;
        }
        if((cantidad * precioD) == total){
            return precioD;
        }
        if((cantidad * precioM) == total){
            return precioM;
        }
        
        return 0.0;
    }
    
    @FXML
    public void refresh() throws SQLException, ClassNotFoundException{
        setHistorial();
    }
}

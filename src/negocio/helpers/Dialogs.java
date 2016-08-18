/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio.helpers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Callback;
import negocio.Resource;

/**
 *
 * @author bryan
 */
public class Dialogs {
    
    public static Alert getDialog(Alert.AlertType type, String title, String header, String content){
        Alert alert = new Alert(type);
        Resource resource = new Resource();
        Image icon = resource.getIcon();
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }
    
    public static Alert getErrorDialog(Alert.AlertType type, String title, String header, String content, Exception ex){
        Alert alert = Dialogs.getDialog(type, title, header, content);
        Resource resource = new Resource();
        Image icon = resource.getIcon();
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        String textoDeError = ex.getMessage();
        
        Label label = new Label("Error:");
        
        TextArea textArea = new TextArea(textoDeError);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        
        GridPane expContenido = new GridPane();
        expContenido.setMaxWidth(Double.MAX_VALUE);
        expContenido.add(label, 0, 0);
        expContenido.add(textArea, 0, 1);
        
        alert.getDialogPane().setExpandableContent(expContenido);
        
        return alert;
    }
    
}

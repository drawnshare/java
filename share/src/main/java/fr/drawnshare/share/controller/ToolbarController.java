package fr.drawnshare.share.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ToolbarController implements Initializable {
    
    @FXML
    private void handleAddTaskAction(ActionEvent event) {
        System.out.println("Adding a new task.");
    }

    @FXML
    private void handleRefreshAction(ActionEvent event){
        System.out.println("Refreshing");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}

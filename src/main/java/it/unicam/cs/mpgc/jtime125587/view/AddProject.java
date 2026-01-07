package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.ProjectController;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class AddProject {
    @FXML
    private DialogPane addProject;
    @FXML
    private TextField name;
    @FXML
    private ButtonType okButton;

    public void initialize() {
        Button confirm = (Button) addProject.lookupButton(okButton);
        confirm.setOnAction(event -> ProjectController.getInstance().add(new Project(name.getText())));
    }
}

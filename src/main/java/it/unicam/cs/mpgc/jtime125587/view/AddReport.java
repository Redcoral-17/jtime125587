package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.ProjectController;
import it.unicam.cs.mpgc.jtime125587.controller.ReportController;
import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Report;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

import static javafx.collections.FXCollections.observableList;

public class AddReport {
    @FXML
    private DialogPane addReport;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> project;
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private ButtonType okButton;

    public void initialize() {
        project.setItems(observableList(ProjectController.getInstance().getAllProjNames()));
        start.setValue(LocalDate.now());
        end.setValue(LocalDate.now().plusDays(7));
        Button button = (Button) addReport.lookupButton(okButton);
        button.setOnAction(event -> {
            Project p = ProjectController.getInstance().getByName(project.getPromptText());
            ReportController.getInstance().add(new Report(name.getText(), p, start.getValue(), end.getValue()));
        });
    }
}

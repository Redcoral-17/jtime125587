package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.ProjectController;
import it.unicam.cs.mpgc.jtime125587.controller.ReportController;
import it.unicam.cs.mpgc.jtime125587.model.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

import static it.unicam.cs.mpgc.jtime125587.view.Main.showError;
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
        button.addEventFilter(ActionEvent.ACTION, event -> {
            if(check()) {  event.consume(); return; }
            ReportController.getInstance().add(new Report(name.getText(), project.getValue(), start.getValue(), end.getValue()));
        });
    }

    private boolean check() {
        if(name.getText().isBlank()) { showError("Name cannot be empty"); return true; }
        if(start.getValue() != null && end.getValue() != null) {
            if(start.getValue().isAfter(end.getValue())) { showError("Start date cannot be after end date"); return true; }
        }
        return false;
    }
}

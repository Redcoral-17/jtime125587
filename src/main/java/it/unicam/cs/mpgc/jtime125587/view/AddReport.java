package it.unicam.cs.mpgc.jtime125587.view;

import it.unicam.cs.mpgc.jtime125587.controller.Controller;
import it.unicam.cs.mpgc.jtime125587.controller.HibernateController;
import it.unicam.cs.mpgc.jtime125587.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

import static it.unicam.cs.mpgc.jtime125587.view.Main.showError;
import static javafx.collections.FXCollections.observableList;

public class AddReport {
    private final Controller<Project> projectController = new HibernateController<>(Project.class);
    private final Controller<Report> reportController = new HibernateController<>(Report.class);
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
        project.setItems(observableList(projectController.getAll().stream().map(Project::getName).toList()));
        start.setValue(LocalDate.now());
        end.setValue(LocalDate.now().plusDays(7));
        Button button = (Button) addReport.lookupButton(okButton);
        button.addEventFilter(ActionEvent.ACTION, event -> {
            if(check()) {  event.consume(); return; }
            reportController.add(new Report(name.getText(), start.getValue(), end.getValue(), project.getValue()));
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

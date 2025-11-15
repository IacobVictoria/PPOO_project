package org.example.project_ppoo_java;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;


public class HelloApplication extends Application {
    private final MainManager manager = MainManager.getInstance();
    private final ListView<DirectoryLocation> locationsList = new ListView<>();

    private final Button btnAddDir = new Button("Add");
    private final Button btnRemoveDir = new Button("Remove");
    private final Button btnTotalReport = new Button("TOTAL REPORT");
    private final Button btnImageReport = new Button("IMAGE REPORT");
    private final Button btnAudioReport = new Button("AUDIO REPORT");


    @Override
    public void start(Stage stage) throws IOException {
        manager.loadDirectories();
        manager.scanAll();

        locationsList.getItems().setAll(manager.getDirectories());

        setupUI();

        // When closing the app also saving in txt file
        stage.setOnCloseRequest(e -> manager.saveDirectories());

        Scene scene = new Scene(createRoot(), 500, 350);
        stage.setTitle("Multimedia Files Manager");
        stage.setScene(scene);
        stage.show();
    }

    private void setupUI() {
        locationsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                System.out.println("FILES in " + newVal.getPath() + "\n\n");
                for (MediaFile file : manager.getMediaFilesForDirectory(newVal)) {
                    System.out.println(file);
                }
            }
        });


        btnAddDir.setOnAction(e -> handleAddDirectory());
        btnAddDir.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        btnRemoveDir.setOnAction(e -> handleRemoveDirectory());
        btnRemoveDir.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        btnTotalReport.setOnAction(e -> {
            manager.scanAll();
//            System.out.println("----------      TOTAL REPORT    -------------");
//            System.out.println(manager.getSummaryReport());
            manager.saveSummaryReportToFile("total_report.txt");
        });
        btnTotalReport.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");


        btnImageReport.setOnAction(e -> {
            manager.scanAll();
//            System.out.println("----------------    IMAGE REPORT        --------------------------");
//            System.out.println(manager.getTypeReport(MediaType.IMAGE));
            manager.saveTypeReportToFile(MediaType.IMAGE, "image_report.txt");
        });
        btnImageReport.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");


        btnAudioReport.setOnAction(e -> {
            manager.scanAll();
//            System.out.println("------------------   AUDIO REPORT   ---------------");
//            System.out.println(manager.getTypeReport(MediaType.AUDIO));
            manager.saveTypeReportToFile(MediaType.AUDIO, "audio_report.txt");
        });

    }

    private VBox createRoot() {
        Label title = new Label("Multimedia Files Manager");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label listLabel = new Label("Monitored locations:");

        HBox buttons = new HBox(10, btnAddDir, btnRemoveDir, btnTotalReport, btnImageReport, btnAudioReport);

        VBox root = new VBox(10, title, listLabel, locationsList, buttons);
        root.setPadding(new Insets(10));

        return root;
    }

    private void handleAddDirectory() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add directory");
        dialog.setHeaderText(null);
        dialog.setContentText("Directory path:");

        String path = dialog.showAndWait().orElse("").trim();
        if (path.isEmpty()) {
            System.out.println("No directory added.");
            return;
        }

        try {
            manager.addDirectory(path);
            manager.scanAll();
            locationsList.getItems().setAll(manager.getDirectories());
            System.out.println("Added directory: " + path);
        } catch (InvalidDirectoryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleRemoveDirectory() {
        DirectoryLocation selected = locationsList.getSelectionModel().getSelectedItem();
        if (selected == null) {
            System.out.println("No directory selected.");
            return;
        }

        String path = selected.getPath();
        boolean removed = manager.removeDirectory(path);
        if (removed) {
            manager.scanAll();
            locationsList.getItems().setAll(manager.getDirectories());
            System.out.println("Removed directory: " + path);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
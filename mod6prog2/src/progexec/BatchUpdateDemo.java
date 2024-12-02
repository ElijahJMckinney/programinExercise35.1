package progexec;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BatchUpdateDemo extends Application {
    private DatabaseConnection dbConnection;

    @Override
    public void start(Stage primaryStage) {
        Label lblStatus = new Label("Status: Not connected");
        Button btnConnect = new Button("Connect to Database");
        Button btnInsert = new Button("Insert 1,000 Records");
        btnInsert.setDisable(true); // Disabled until connected

        // Handle database connection
        btnConnect.setOnAction(e -> {
            DatabaseConnectionDialog dialog = new DatabaseConnectionDialog();
            dbConnection = dialog.showAndGetConnection();
            if (dbConnection != null) {
                lblStatus.setText("Status: Connected to " + dbConnection.getUrl());
                btnInsert.setDisable(false);
            } else {
                lblStatus.setText("Status: Connection failed");
            }
        });

        // Handle record insertion
        btnInsert.setOnAction(e -> {
            try {
                lblStatus.setText("Status: Inserting records...");
                DatabaseInserter inserter = new DatabaseInserter(dbConnection);
                long nonBatchTime = inserter.insertWithoutBatch();
                long batchTime = inserter.insertWithBatch();
                lblStatus.setText("Status: Non-Batch: " + nonBatchTime + "ms, Batch: " + batchTime + "ms");
            } catch (Exception ex) {
                lblStatus.setText("Status: Error occurred - " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(10, lblStatus, btnConnect, btnInsert);
        root.setPadding(new Insets(15));
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.setTitle("Batch Update Performance");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package progexec;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionDialog {
    private Connection connection;

    public DatabaseConnection showAndGetConnection() {
        Stage dialog = new Stage();
        dialog.setTitle("Database Connection");

        TextField tfUrl = new TextField("jdbc:mysql://localhost:3306/javatestdb");
        TextField tfUser = new TextField("root");
        PasswordField pfPassword = new PasswordField();

        Button btnConnect = new Button("Connect");
        btnConnect.setOnAction(e -> {
            try {
                connection = DriverManager.getConnection(tfUrl.getText(), tfUser.getText(), pfPassword.getText());
                dialog.close();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Failed to connect: " + ex.getMessage()).showAndWait();
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Database URL:"), tfUrl);
        grid.addRow(1, new Label("Username:"), tfUser);
        grid.addRow(2, new Label("Password:"), pfPassword);
        grid.addRow(3, btnConnect);

        dialog.setScene(new Scene(grid, 400, 200));
        dialog.showAndWait();

        return connection != null ? new DatabaseConnection(tfUrl.getText(), tfUser.getText(), connection) : null;
    }
}

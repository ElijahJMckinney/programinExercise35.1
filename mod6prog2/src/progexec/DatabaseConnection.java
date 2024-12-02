package progexec;

import java.sql.Connection;

public class DatabaseConnection {
    private String url;
    private String username;
    private Connection connection;

    public DatabaseConnection(String url, String username, Connection connection) {
        this.url = url;
        this.username = username;
        this.connection = connection;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public Connection getConnection() {
        return connection;
    }
}

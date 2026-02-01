import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String URL = "jdbc:sqlite:planner.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void setupDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks ("
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " title TEXT NOT NULL,"
                    + " category TEXT DEFAULT 'Umum'," 
                    + " is_completed INTEGER DEFAULT 0,"
                    + " deadline TEXT,"
                    + " priority TEXT"
                    + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("[INFO] Database siap digunakan.");
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal setup database: " + e.getMessage());
        }
    }
}
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private List<Task> cachedTaskList = new ArrayList<>();

    public TaskService() {
        refreshData(); 
    }

    // FITUR CRUD DATABASE

    public void tambahTask(String judul, String kategori, LocalDate deadline, Priority priority) {
        if (deadline.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Deadline tidak boleh di masa lalu!");
        }
        String sql = "INSERT INTO tasks(title, category, deadline, priority, is_completed) VALUES(?, ?, ?, ?, 0)";
        try (Connection conn = DatabaseConfig.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, judul);
            pstmt.setString(2, kategori);
            pstmt.setString(3, deadline.toString());
            pstmt.setString(4, priority.toString());

            pstmt.executeUpdate();
            
            refreshData();
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal tambah data: " + e.getMessage());
        }
    }

    public List<Task> getDaftarTugas() {

        return cachedTaskList;
    }

    public boolean hapusTask(int index) {
        if (index < 0 || index >= cachedTaskList.size()) return false;

        int idDb = cachedTaskList.get(index).getId();
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseConfig.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idDb);
            pstmt.executeUpdate();
            
            refreshData();
            return true;
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal hapus data: " + e.getMessage());
            return false;
        }
    }

    public boolean tandaiSelesai(int index) {
        if (index < 0 || index >= cachedTaskList.size()) return false;

        int idDb = cachedTaskList.get(index).getId();
        String sql = "UPDATE tasks SET is_completed = 1 WHERE id = ?";

        try (Connection conn = DatabaseConfig.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idDb);
            pstmt.executeUpdate();
            
            refreshData();
            return true;
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal update status: " + e.getMessage());
            return false;
        }
    }

    public boolean editTask(int index, String judul, String tglStr, int pChoice) {
        if (index < 0 || index >= cachedTaskList.size()) return false;
    
        Task t = cachedTaskList.get(index);
        int idDb = t.getId();
        
        String judulBaru = judul.isEmpty() ? t.getTitle() : judul;
        String tglBaru = tglStr.isEmpty() ? t.getDeadline().toString() : tglStr;
        String prioritasBaru = t.getPriority().toString();
        
        if (pChoice == 1) prioritasBaru = "TINGGI";
        else if (pChoice == 2) prioritasBaru = "SEDANG";
        else if (pChoice == 3) prioritasBaru = "RENDAH";
    
        String sql = "UPDATE tasks SET title = ?, deadline = ?, priority = ? WHERE id = ?";
    
        try (Connection conn = DatabaseConfig.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, judulBaru);
            pstmt.setString(2, tglBaru);
            pstmt.setString(3, prioritasBaru);
            pstmt.setInt(4, idDb);
            pstmt.executeUpdate();
            
            refreshData();
            return true;
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal edit data: " + e.getMessage());
            return false;
        }
    }
    
    public List<Task> cariTugas(String keyword) {
        List<Task> hasil = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE title LIKE ?";

        try (Connection conn = DatabaseConfig.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + keyword + "%"); // % artinya "mengandung"
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                hasil.add(mapResultSetToTask(rs));
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Pencarian gagal: " + e.getMessage());
        }
        return hasil;
    }
        public List<Task> filterByPriority(Priority p) {
        List<Task> hasil = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE priority = ? ORDER BY deadline ASC";

        try (Connection conn = DatabaseConfig.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, p.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                hasil.add(mapResultSetToTask(rs));
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal filter prioritas: " + e.getMessage());
        }
        return hasil;
    }

    public List<Task> filterByStatus(boolean isCompleted) {
        List<Task> hasil = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE is_completed = ? ORDER BY deadline ASC";

        try (Connection conn = DatabaseConfig.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, isCompleted ? 1 : 0);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                hasil.add(mapResultSetToTask(rs));
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal filter status: " + e.getMessage());
        }
        return hasil;
    }
    private void refreshData() {
        cachedTaskList.clear();
        String sql = "SELECT * FROM tasks ORDER BY deadline ASC"; // Sorting langsung dari SQL

        try (Connection conn = DatabaseConfig.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                cachedTaskList.add(mapResultSetToTask(rs));
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Gagal memuat data: " + e.getMessage());
        }
    }

    private Task mapResultSetToTask(ResultSet rs) throws SQLException {
        return new Task(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("category"),
            LocalDate.parse(rs.getString("deadline")),
            Priority.valueOf(rs.getString("priority")),
            rs.getInt("is_completed") == 1
        );
    }
    
    public int getTotalTugas() { return cachedTaskList.size(); }
    public int getTugasSelesai() {
        int count = 0;
        for (Task t : cachedTaskList) if (t.isCompleted()) count++;
        return count;
    }
    public void hapusTugasSelesai() {
    String sql = "DELETE FROM tasks WHERE is_completed = 1";
    try (Connection conn = DatabaseConfig.connect();
         Statement stmt = conn.createStatement()) {
        stmt.executeUpdate(sql);
        refreshData();
        int jumlahHapus = stmt.executeUpdate(sql);
        if (jumlahHapus > 0) {
            System.out.println("\u001B[32m[OK] Berhasil menghapus " + jumlahHapus + " tugas yang sudah selesai!\u001B[0m");
        } else {
            System.out.println("\u001B[31m[INFO] Tidak ada tugas selesai yang perlu dihapus.\u001B[0m");
        }
    } catch (SQLException e) { System.out.println(e.getMessage()); }
    }
}
import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String category;
    private boolean isCompleted;
    private LocalDate deadline;
    private Priority priority; 

    // Constructor untuk Task baru (sebelum masuk DB - Opsional jika tidak dipakai di Service tapi baik untuk kelengkapan)
    public Task(String title, LocalDate deadline, Priority priority, String category) {
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.isCompleted = false;
    }

    // Constructor lengkap untuk mengambil dari Database
    public Task(int id, String title, String category, LocalDate deadline, Priority priority, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.deadline = deadline;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    public int getId() { return id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public void markAsCompleted() { this.isCompleted = true; }
}
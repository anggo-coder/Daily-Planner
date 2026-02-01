import java.time.LocalDate;

public class Task {
    private int id;
    private String title;
    private String category;
    private boolean isCompleted;
    private LocalDate deadline;
    private Priority priority; 
    

    public Task(String title, LocalDate deadline, Priority priority, String category) {
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.isCompleted = false;
    }

    public Task(int id, String title, LocalDate deadline, Priority priority, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    public Task(int id, String title, String category, LocalDate deadline, Priority priority, boolean isCompleted) {
    this.id = id;
    this.title = title;
    this.category = category;
    this.deadline = deadline;
    this.priority = priority;
    this.isCompleted = isCompleted;
    }

    public int getId() { return id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    // Getter dan Setter
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public void markAsCompleted() { this.isCompleted = true; }
}
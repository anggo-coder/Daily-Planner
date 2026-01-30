import java.time.LocalDate;

public class Task {
    private String title;
    private boolean isCompleted;
    private LocalDate deadline;
    private Priority priority; 

    public Task(String title, LocalDate deadline, Priority priority) {
        this.title = title;
        this.deadline = deadline;
        this.priority = priority;
        this.isCompleted = false;
    }

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
import java.time.LocalDate;

public class Task {
    private String title;
    private boolean isCompleted;
    private LocalDate deadline;


    public Task(String title, LocalDate deadline){
        this.title = title;
        this.deadline = deadline;
        this.isCompleted = false;
    }
    //getter
    public String getTitle() { return title; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
    public String getStatusSymbol() { return (isCompleted) ? "[v]" : "[ ]"; }
    //setter
    public void markAsCompleted() { this.isCompleted = true; }
    public LocalDate getDeadline() {return deadline; }
    public void setTitle(String title) { this.title = title; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
}
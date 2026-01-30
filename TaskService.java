import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskService {
    private ArrayList<Task> taskList = new ArrayList<>();
    private final String FILE_NAME = "data.txt";

    public TaskService() {
        muatData();
    }

    public void tambahTask(String judul, LocalDate deadline, Priority priority) {
        if (deadline.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Deadline tidak boleh di masa lalu!");
        }
        taskList.add(new Task(judul, deadline, priority));
        simpanData();
    }

    public List<Task> getDaftarTugas() {
        Collections.sort(taskList, Comparator.comparing(Task::getDeadline));
        return taskList;
    }

    public boolean hapusTask(int index) {
        if (index >= 0 && index < taskList.size()) {
            taskList.remove(index);
            simpanData();
            return true;
        }
        return false;
    }

    public boolean tandaiSelesai(int index) {
        if (index >= 0 && index < taskList.size()) {
            taskList.get(index).setCompleted(true);
            simpanData();
            return true;
        }
        return false;
    }

    public List<Task> cariTugas(String keyword) {
        List<Task> hasil = new ArrayList<>();
        for (Task t : taskList) {
            if (t.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                hasil.add(t);
            }
        }
        return hasil;
    }

    public boolean editTask(int index, String judul, String tglStr, int pChoice) {
        if (index >= 0 && index < taskList.size()) {
            Task t = taskList.get(index);
            if (!judul.isEmpty()) t.setTitle(judul);
            if (!tglStr.isEmpty()) t.setDeadline(LocalDate.parse(tglStr));
            
            if (pChoice == 1) t.setPriority(Priority.TINGGI);
            else if (pChoice == 2) t.setPriority(Priority.SEDANG);
            else if (pChoice == 3) t.setPriority(Priority.RENDAH);
            
            simpanData();
            return true;
        }
        return false;
    }

    private void simpanData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task t : taskList) {
                String status = t.isCompleted() ? "1" : "0";
                writer.write(status + ";" + t.getTitle() + ";" + t.getDeadline() + ";" + t.getPriority());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Gagal simpan data.");
        }
    }

    private void muatData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(";");
                if (p.length == 4) { 
                    Task t = new Task(p[1], LocalDate.parse(p[2]), Priority.valueOf(p[3]));
                    if (p[0].equals("1")) t.markAsCompleted();
                    taskList.add(t);
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal muat data: " + e.getMessage());
        }
    }

    public int getTotalTugas() { return taskList.size(); }
    public int getTugasSelesai() {
        int count = 0;
        for (Task t : taskList) if (t.isCompleted()) count++;
        return count;
    }
}
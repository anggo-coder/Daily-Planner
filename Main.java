import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Main {
    static ArrayList<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
        muatData();
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        System.out.println("Selamat Datang di Daily Planner!");
        tampilkanDashboard();
        while (isRunning) {
            tampilkanMenu();
            System.out.print("Pilih menu : ");
            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1": tambahTugas(scanner); break;
                case "2": lihatTugas(); break;
                case "3": tandaiSelesai(scanner); break;
                case "4": hapusTugas(scanner); break;
                case "5": cariTugas(scanner); break;
                case "6": editTugas(scanner); break;
                case "7":
                    System.out.println("Terima kasih! Sampai jumpa.");
                    isRunning = false;
                    break;
                default:
                    System.out.println("⚠️ Pilihan tidak valid!");
            }
        }
    }

    private static void tampilkanMenu() {
        System.out.println("\n=== MENU UTAMA ===");
        System.out.println("1. Tambah Tugas");
        System.out.println("2. Lihat Daftar Tugas (Sorted)");
        System.out.println("3. Tandai Selesai");
        System.out.println("4. Hapus Tugas");
        System.out.println("5. Cari Tugas");
        System.out.println("6. Edit Tugas");
        System.out.println("7. Keluar");
    }

    private static void tambahTugas(Scanner scanner) {
        System.out.print("Masukkan nama tugas: ");
        String judul = scanner.nextLine();
        if (judul.trim().isEmpty()) {
            System.out.println("Gagal: Nama tidak boleh kosong!");
            return;
        }

        System.out.print("Masukkan deadline (YYYY-MM-DD): ");
        try {
            LocalDate deadline = LocalDate.parse(scanner.nextLine());
            taskList.add(new Task(judul, deadline));
            simpanData();
            System.out.println("✅ Berhasil disimpan!");
        } catch (DateTimeParseException e) {
            System.out.println("⚠️ Gagal: Format tanggal salah!");
        }
    }

    private static void lihatTugas() {
        if (taskList.isEmpty()) {
            System.out.println("Daftar kosong.");
            return;
        }
        Collections.sort(taskList, Comparator.comparing(Task::getDeadline));
        System.out.println("\n--- DAFTAR TUGAS ---");
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.get(i);
            String info = t.getDeadline().isBefore(LocalDate.now()) && !t.isCompleted() ? " [⚠️ TERLAMBAT]" : "";
            System.out.println((i + 1) + ". " + t.getStatusSymbol() + " " + t.getTitle() + " (" + t.getDeadline() + ")" + info);
        }
    }

    private static void tandaiSelesai(Scanner scanner) {
        lihatTugas();
        System.out.print("Nomor tugas selesai: ");
        try {
            int nomor = Integer.parseInt(scanner.nextLine());
            if (nomor > 0 && nomor <= taskList.size()) {
                taskList.get(nomor - 1).setCompleted(true);
                simpanData();
                System.out.println("✅ Status diperbarui!");
            }
        } catch (Exception e) { System.out.println("⚠️ Input tidak valid!"); }
    }

    private static void hapusTugas(Scanner scanner) {
        lihatTugas();
        System.out.print("Nomor tugas yang dihapus: ");
        try {
            int nomor = Integer.parseInt(scanner.nextLine());
            taskList.remove(nomor - 1);
            simpanData();
            System.out.println("✅ Berhasil dihapus!");
        } catch (Exception e) { System.out.println("⚠️ Gagal menghapus!"); }
    }

    private static void cariTugas(Scanner scanner) {
        System.out.print("Kata kunci: ");
        String key = scanner.nextLine().toLowerCase();
        boolean ditemukan = false;
        for (Task t : taskList) {
            if (t.getTitle().toLowerCase().contains(key)) {
                System.out.println(t.getStatusSymbol() + " " + t.getTitle() + " (" + t.getDeadline() + ")");
                ditemukan = true;
            }
        }
        if (!ditemukan) System.out.println("Tidak ditemukan.");
    }

    private static void editTugas(Scanner scanner) {
        lihatTugas();
        System.out.print("Nomor yang akan diedit: ");
        try {
            int nomor = Integer.parseInt(scanner.nextLine());
            Task t = taskList.get(nomor - 1);
            
            System.out.print("Judul baru (kosongkan jika tetap): ");
            String jBaru = scanner.nextLine();
            if (!jBaru.isEmpty()) t.setTitle(jBaru);

            System.out.print("Deadline baru YYYY-MM-DD (kosongkan jika tetap): ");
            String dBaru = scanner.nextLine();
            if (!dBaru.isEmpty()) t.setDeadline(LocalDate.parse(dBaru));

            simpanData();
            System.out.println("✅ Berhasil diupdate!");
        } catch (Exception e) { System.out.println("⚠️ Gagal edit!"); }
    }

    private static void simpanData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
            for (Task t : taskList) {
                writer.write((t.isCompleted() ? "1" : "0") + ";" + t.getTitle() + ";" + t.getDeadline());
                writer.newLine();
            }
        } catch (IOException e) { System.out.println("⚠️ Error simpan data."); }
    }

    private static void muatData() {
        File file = new File("data.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(";");
                if (p.length == 3) {
                    Task t = new Task(p[1], LocalDate.parse(p[2]));
                    if (p[0].equals("1")) t.markAsCompleted();
                    taskList.add(t);
                }
            }
        } catch (Exception e) { System.out.println("⚠️ Gagal muat data."); }
    }
    private static void tampilkanDashboard() {
    System.out.println("\n----------------------------------------");
    System.out.println("         DASHBOARD HARIAN ANDA");
    System.out.println("----------------------------------------");

    if (taskList.isEmpty()) {
        System.out.println("   Belum ada tugas. Mulai produktif yuk!");
        System.out.println("----------------------------------------");
        return;
    }

    int total = taskList.size();
    int selesai = 0;
    int terlambat = 0;
    int hariIni = 0;
    LocalDate today = LocalDate.now();

    for (Task t : taskList) {
        if (t.isCompleted()) {
            selesai++;
        } else {
            // Cek terlambat (Deadline sebelum hari ini & belum selesai)
            if (t.getDeadline().isBefore(today)) {
                terlambat++;
            }
            // Cek deadline hari ini
            if (t.getDeadline().isEqual(today)) {
                hariIni++;
            }
        }
    }

    System.out.println(" Total Tugas   : " + total);
    System.out.println(" Selesai       : " + selesai);
    System.out.println(" Belum Selesai : " + (total - selesai));
    
    // Logic notifikasi
    if (terlambat > 0) {
        System.out.println(" WARNING       : Ada " + terlambat + " tugas TERLAMBAT!");
    }
    if (hariIni > 0) {
        System.out.println(" REMINDER      : Ada " + hariIni + " tugas deadline HARI INI!");
    }
    
    // Motivasi kecil
    if (selesai == total && total > 0) {
        System.out.println("\n Luar biasa! Semua tugas selesai.");
    }
    System.out.println("----------------------------------------");
    }
}


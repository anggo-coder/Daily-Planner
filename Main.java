import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

public class Main {
    // Pastikan service dan scanner static agar bisa diakses semua method
    static TaskService service = new TaskService();
    static Scanner scanner = new Scanner(System.in);

    // Kode Warna ANSI
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        DatabaseConfig.setupDatabase();
        boolean isRunning = true;
        
        while (isRunning) {
            clearScreen();
            
            // --- BAGIAN DASHBOARD ---
            Map<String, Integer> stats = service.getStatistik();
            System.out.println("\n" + CYAN + "=".repeat(45) + RESET);
            System.out.println("          DASHBOARD PRODUKTIVITAS");
            System.out.println(CYAN + "=".repeat(45) + RESET);
            System.out.printf(" Total Tugas    : %d\n", stats.getOrDefault("total", 0));
            System.out.printf(" Tugas Pending  : %s%d%s\n", YELLOW, stats.getOrDefault("pending", 0), RESET);
            System.out.printf(" Urgent (HIGH)  : %s%d%s\n", RED, stats.getOrDefault("urgent", 0), RESET);
            System.out.println(CYAN + "=".repeat(45) + RESET);
            // ------------------------

            // --- MENU UTAMA (KEMBALI KE LIST KE BAWAH) ---
            System.out.println("1. Tambah Tugas");
            System.out.println("2. Lihat Tugas");
            System.out.println("3. Tandai Selesai");
            System.out.println("4. Hapus Tugas");
            System.out.println("5. Cari Tugas");
            System.out.println("6. Edit Tugas");
            System.out.println("7. Bersihkan Tugas Selesai");
            System.out.println("8. Backup Data ke CSV");
            System.out.println("9. Keluar");
            System.out.print("\nPilih menu: ");
            
            String pilihan = scanner.nextLine();
            switch (pilihan) {
                case "1": uiTambahTugas(); break;
                case "2": uiLihatTugas(); break;
                case "3": uiTandaiSelesai(); break;
                case "4": uiHapusTugas(); break;
                case "5": uiCariTugas(); break;
                case "6": uiEditTugas(); break;
                case "7": 
                    service.hapusTugasSelesai();
                    System.out.println(GREEN + "[OK] Tugas selesai telah dibersihkan." + RESET);
                    kembaliKeMenu();
                    break; 
                case "8": uiExportCSV(); break;
                case "9": 
                    System.out.println("Sampai jumpa! Tetap produktif ya."); 
                    isRunning = false; 
                    break;
                default: 
                    System.out.println(RED + "[ERROR] Pilihan tidak valid!" + RESET);
                    kembaliKeMenu();
            }
        }
    }

    // --- METODE HELPER ---

    private static void kembaliKeMenu() {
        System.out.println("\n" + YELLOW + "Tekan ENTER untuk kembali..." + RESET);
        scanner.nextLine();
    }

    private static void uiTambahTugas() {
        System.out.println("\n--- TAMBAH TUGAS BARU ---");
        System.out.print("Judul: ");
        String judul = scanner.nextLine();
        System.out.print("Kategori: ");
        String kategori = scanner.nextLine();
        
        LocalDate deadline = mintaInputTanggal("Deadline");
        // Memanggil menu prioritas yang berwarna
        Priority priority = mintaInputPrioritas();

        try {
            service.tambahTask(judul, kategori, deadline, priority);
            System.out.println(GREEN + "[OK] Berhasil disimpan!" + RESET);
        } catch (Exception e) {
            System.out.println(RED + "[ERROR] Gagal: " + e.getMessage() + RESET);
        }
        kembaliKeMenu();
    }

    private static void uiLihatTugas() {
        System.out.println("\n--- FILTER TAMPILAN ---");
        System.out.println("1. Semua");
        System.out.println("2. Prioritas " + RED + "TINGGI" + RESET);
        System.out.println("3. Status Belum Selesai");
        System.out.print("Pilih: ");
        
        String sub = scanner.nextLine();
        List<Task> list;
        
        if (sub.equals("2")) list = service.filterByPriority(Priority.TINGGI);
        else if (sub.equals("3")) list = service.filterByStatus(false);
        else list = service.getDaftarTugas();

        tampilkanList(list);
        kembaliKeMenu();
    }

    private static void uiCariTugas() {
        System.out.print("Cari judul/kategori: ");
        String key = scanner.nextLine();
        List<Task> hasil = service.cariTugas(key);
        
        if (hasil.isEmpty()) System.out.println(RED + "Tidak ditemukan." + RESET);
        else tampilkanList(hasil);
        
        kembaliKeMenu();
    }

    private static void uiTandaiSelesai() {
        List<Task> list = service.getDaftarTugas();
        tampilkanList(list);
        if (list.isEmpty()) { kembaliKeMenu(); return; }
        
        System.out.print("Nomor tugas selesai: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (service.tandaiSelesai(idx)) System.out.println(GREEN + "[OK] Status diperbarui!" + RESET);
            else System.out.println(RED + "[ERROR] Gagal." + RESET);
        } catch (Exception e) { System.out.println(RED + "[ERROR] Input salah." + RESET); }
        kembaliKeMenu();
    }

    private static void uiHapusTugas() {
        List<Task> list = service.getDaftarTugas();
        tampilkanList(list);
        if (list.isEmpty()) { kembaliKeMenu(); return; }
        
        System.out.print("Nomor tugas yang dihapus: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (service.hapusTask(idx)) System.out.println(GREEN + "[OK] Berhasil dihapus!" + RESET);
            else System.out.println(RED + "[ERROR] Gagal." + RESET);
        } catch (Exception e) { System.out.println(RED + "[ERROR] Input salah." + RESET); }
        kembaliKeMenu();
    }

    private static void uiEditTugas() {
        List<Task> list = service.getDaftarTugas();
        tampilkanList(list);
        if (list.isEmpty()) { kembaliKeMenu(); return; }
        
        System.out.print("Nomor yang akan diedit: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.println("(Kosongkan input jika tidak ingin mengubah data)");
            
            System.out.print("Judul baru: ");
            String judul = scanner.nextLine();
            
            System.out.print("Deadline baru (YYYY-MM-DD): ");
            String tglStr = scanner.nextLine();
            
            // Tampilan Prioritas Edit dibuat vertikal juga agar rapi
            System.out.println("Prioritas baru:");
            System.out.println("1. " + RED + "TINGGI" + RESET);
            System.out.println("2. " + YELLOW + "SEDANG" + RESET);
            System.out.println("3. " + GREEN + "RENDAH" + RESET);
            System.out.print("Pilih (Kosongkan utk tetap): ");
            
            String pStr = scanner.nextLine();
            int pChoice = pStr.isEmpty() ? 0 : Integer.parseInt(pStr);

            if (service.editTask(idx, judul, tglStr, pChoice)) 
                System.out.println(GREEN + "[OK] Berhasil diedit!" + RESET);
            else 
                System.out.println(RED + "[ERROR] Gagal edit." + RESET);
                
        } catch (Exception e) { System.out.println(RED + "[ERROR] Input salah." + RESET); }
        kembaliKeMenu();
    }

    private static void uiExportCSV() {
        System.out.print("Nama file (default: backup.csv): ");
        String name = scanner.nextLine();
        if (name.isEmpty()) name = "backup.csv";
        if (!name.endsWith(".csv")) name += ".csv";
        
        if (service.exportToCSV(name)) System.out.println(GREEN + "[SUKSES] Cek file: " + name + RESET);
        else System.out.println(RED + "[GAGAL] Export gagal." + RESET);
        kembaliKeMenu();
    }

    // --- FORMAT TAMPILAN TABEL ---
    private static void tampilkanList(List<Task> list) {
            System.out.println("\n+-----+----------------------+----------------+------------+------------+-------------+");
            System.out.printf("| %-3s | %-20s | %-14s | %-10s | %-10s | %-11s |%n", "NO", "JUDUL", "KATEGORI", "DEADLINE", "PRIORITAS", "STATUS");
            System.out.println("+-----+----------------------+----------------+------------+------------+-------------+");

            if (list.isEmpty()) {
                System.out.printf("| %-79s |%n", " (Data Kosong) ");
            } else {
                int num = 1;
                for (Task t : list) {
                    String colorPrio;
                    if (t.getPriority() == Priority.TINGGI) {
                        colorPrio = RED;
                    } else if (t.getPriority() == Priority.SEDANG) {
                        colorPrio = YELLOW;
                    } else {
                        colorPrio = GREEN;
                    }

                    String colorStatus = t.isCompleted() ? GREEN : YELLOW;
                    String statusTeks = t.isCompleted() ? "SELESAI" : "BELUM";
                    String judul = t.getTitle().length() > 18 ? t.getTitle().substring(0, 15) + "..." : t.getTitle();

                    System.out.printf("| %-3d | %-20s | %-14s | %-10s | %s%-10s%s | %s%-11s%s |%n", 
                        num++, 
                        judul, 
                        t.getCategory(), 
                        t.getDeadline(), 
                        colorPrio, t.getPriority(), RESET, 
                        colorStatus, statusTeks, RESET
                    );
                }
            }
            System.out.println("+-----+----------------------+----------------+------------+------------+-------------+");
        }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) 
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else System.out.print("\033[H\033[2J\n");
        } catch (Exception e) {}
    }

    private static LocalDate mintaInputTanggal(String pesan) {
        while (true) {
            System.out.print(pesan + " (YYYY-MM-DD): ");
            String input = scanner.nextLine();
            try {
                LocalDate d = LocalDate.parse(input);
                if (!d.isBefore(LocalDate.now())) return d;
                System.out.println(RED + "[ERROR] Tanggal minimal hari ini." + RESET);
            } catch (DateTimeParseException e) { 
                System.out.println(RED + "[ERROR] Format salah. Contoh: 2025-05-20" + RESET); 
            }
        }
    }

    private static Priority mintaInputPrioritas() {
        while (true) {
            System.out.println("Pilih Prioritas:");
            System.out.println("1. " + RED + "TINGGI" + RESET);
            System.out.println("2. " + YELLOW + "SEDANG" + RESET);
            System.out.println("3. " + GREEN + "RENDAH" + RESET);
            System.out.print("Pilihan (1-3): ");
            
            String p = scanner.nextLine();
            if (p.equals("1")) return Priority.TINGGI;
            if (p.equals("2")) return Priority.SEDANG;
            if (p.equals("3")) return Priority.RENDAH;
            
            System.out.println(RED + "[ERROR] Pilihan tidak valid, coba lagi." + RESET);
        }
    }
}
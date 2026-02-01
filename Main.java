import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Main {
    static TaskService service = new TaskService();
    static Scanner scanner = new Scanner(System.in);

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        DatabaseConfig.setupDatabase();
        boolean isRunning = true;
        System.out.println(CYAN + "Selamat Datang di Daily Planner Pro!" + RESET);
        
        tampilkanDashboard(); 

        while (isRunning) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Tambah Tugas");
            System.out.println("2. Lihat Tugas");
            System.out.println("3. Tandai Selesai");
            System.out.println("4. Hapus Tugas");
            System.out.println("5. Cari Tugas");
            System.out.println("6. Edit Tugas");
            System.out.println("7. Bersihkan Tugas Selesai");
            System.out.println("8. Keluar");
            System.out.print("Pilih: ");
            
            String pilihan = scanner.nextLine();
            switch (pilihan) {
                case "1": uiTambahTugas(); break;
                case "2": uiLihatTugas(); break;
                case "3": uiTandaiSelesai(); break;
                case "4": uiHapusTugas(); break;
                case "5": uiCariTugas(); break;
                case "6": uiEditTugas(); break;
                case "7": service.hapusTugasSelesai(); break; 
                case "8": 
                    System.out.println("Sampai jumpa!"); 
                    isRunning = false; 
                    break;
                default: System.out.println(RED + "[ERROR] Pilihan salah!" + RESET);
            }
        }
    }

private static void uiTambahTugas() {
    try {
        System.out.print("Judul: ");
        String judul = scanner.nextLine();
        System.out.print("Kategori: ");
        String kategori = scanner.nextLine();
        System.out.print("Deadline (YYYY-MM-DD): ");
        String inputTanggal = scanner.nextLine();
        LocalDate date = LocalDate.parse(inputTanggal);
        
        System.out.print("Prioritas (1.TINGGI, 2.SEDANG, 3.RENDAH): ");
        int pChoice = Integer.parseInt(scanner.nextLine());
        Priority p = (pChoice == 1) ? Priority.TINGGI : (pChoice == 2) ? Priority.SEDANG : Priority.RENDAH;

        // Pastikan urutan parameter sesuai dengan TaskService (Judul, Kategori, Tanggal, Prioritas)
        service.tambahTask(judul, kategori, date, p); 
        System.out.println(GREEN + "[OK] Berhasil disimpan!" + RESET);
        
        } catch (DateTimeParseException e) {
            System.out.println(RED + "[ERROR] Format tanggal salah!" + RESET);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "[ERROR] " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(RED + "[ERROR] Terjadi kesalahan input." + RESET);
        }
    }

    private static void uiLihatTugas() {
        System.out.println("\n--- MENU LIHAT TUGAS ---");
        System.out.println("1. Semua Tugas");
        System.out.println("2. Filter Prioritas TINGGI");
        System.out.println("3. Filter Belum Selesai");
        System.out.print("Pilih: ");
        
        String subPilihan = scanner.nextLine();
        List<Task> list = null;

        switch (subPilihan) {
            case "1":
                list = service.getDaftarTugas();
                break;
            case "2":
                list = service.filterByPriority(Priority.TINGGI);
                break;
            case "3":
                list = service.filterByStatus(false);
                break;
            default:
                System.out.println(RED + "[ERROR] Pilihan tidak ada, menampilkan semua." + RESET);
                list = service.getDaftarTugas();
        }
        
        tampilkanList(list);
    }


    private static void tampilkanList(List<Task> list) {
        if (list.isEmpty()) {
            System.out.println("Tidak ada data yang sesuai.");
            return;
        }
        
        System.out.println("\nList Tugas");
        for (int i = 0; i < list.size(); i++) {
            Task t = list.get(i);
            String status = t.isCompleted() ? (GREEN + "[V]" + RESET) : "[ ]";
            System.out.println((i + 1) + ". " + status + " [" + t.getPriority() + "] " + t.getTitle() + " (" + t.getDeadline() + ")");
        }
    }

    private static void uiHapusTugas() {
        List<Task> list = service.getDaftarTugas();
        tampilkanList(list);
        System.out.print("Nomor hapus: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (service.hapusTask(idx)) {
                System.out.println(GREEN + "[OK] Terhapus!" + RESET);
            } else {
                System.out.println(RED + "[ERROR] Nomor tidak valid!" + RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "[ERROR] Input harus angka!" + RESET);
        }
    }

    private static void uiTandaiSelesai() {
        List<Task> list = service.getDaftarTugas();
        tampilkanList(list);
        System.out.print("Nomor selesai: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (service.tandaiSelesai(idx)) {
                System.out.println(GREEN + "[OK] Status update!" + RESET);
            } else {
                System.out.println(RED + "[ERROR] Gagal update." + RESET);
            }
        } catch (Exception e) { System.out.println(RED + "[ERROR] Error input." + RESET); }
    }

    private static void uiCariTugas() {
        System.out.print("Masukkan kata kunci: ");
        String keyword = scanner.nextLine();
        List<Task> hasil = service.cariTugas(keyword);

        if (hasil.isEmpty()) {
            System.out.println(RED + "[ERROR] Tidak ditemukan tugas dengan kata kunci '" + keyword + "'" + RESET);
        } else {
            System.out.println("\n[SEARCH] HASIL PENCARIAN:");
            for (Task t : hasil) {
                String status = t.isCompleted() ? (GREEN + "[V]" + RESET) : "[ ]";
                System.out.println("- " + status + " [" + t.getPriority() + "] " + t.getTitle() + " (" + t.getDeadline() + ")");
            }
        }
    }

    private static void uiEditTugas() {
        List<Task> list = service.getDaftarTugas();
        tampilkanList(list);
        System.out.print("Nomor edit: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.print("Judul baru (Enter jika tetap): ");
            String judul = scanner.nextLine();
            System.out.print("Deadline baru (Enter jika tetap): ");
            String tgl = scanner.nextLine();
            
            System.out.print("Prioritas baru (1.TINGGI, 2.SEDANG, 3.RENDAH, 0.Tetap): ");
            String pInput = scanner.nextLine();
            int pChoice = pInput.isEmpty() ? 0 : Integer.parseInt(pInput);
            
            if (service.editTask(idx, judul, tgl, pChoice)) {
                System.out.println(GREEN + "[OK] Data diperbarui!" + RESET);
            } else {
                System.out.println(RED + "[ERROR] Gagal edit." + RESET);
            }
        } catch (Exception e) { System.out.println(RED + "[ERROR] Error." + RESET); }
    }

    private static void tampilkanDashboard() {
        System.out.println("[INFO] Total Tugas: " + service.getTotalTugas());
        System.out.println("[INFO] Selesai: " + service.getTugasSelesai());
    }
}
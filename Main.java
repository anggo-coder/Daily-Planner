import java.util.ArrayList;
import java.util.Scanner;
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

        while (isRunning) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Tambah Tugas");
            System.out.println("2. Lihat Daftar Tugas");
            System.out.println("3. Tandai Selesai");
            System.out.println("4. Hapus Tugas");
            System.out.println("5. Cari Tugas");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu : ");

            int pilihan = 0;
            try {
                pilihan = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                pilihan = 0; 
            }

            switch (String.valueOf(pilihan)) { 
                case "1":
                    System.out.println("Masukan nama tugas: ");
                    String judul = scanner.nextLine();

                    if (judul.trim().isEmpty()) {
                   System.out.println("Gagal: Nama tugas tidak boleh kosong!");
                   break;
                    } 
                    System.out.print("Masukkan deadline (format: YYYY-MM-DD, contoh: 2024-02-14): ");
                    String tglInput = scanner.nextLine();
                    
                    try {
                        LocalDate deadline = LocalDate.parse(tglInput);
                       
                        Task tugasBaru = new Task(judul, deadline);
                        taskList.add(tugasBaru);
                        System.out.println("Berhasil disimpan!");
                    } catch (DateTimeParseException e) {
                        System.out.println("Gagal: Format tanggal salah! Gunakan YYYY-MM-DD.");
                    }
                    break;
                case "2":
                    if (taskList.isEmpty()){
                        System.out.println("Belum ada tugas, silahkan tambahkan tugas pada menu 1!");
                    } else {
                        System.out.println("------------------------------------");
                        System.out.println("Total tugas: " + taskList.size());
                   for (int i = 0; i < taskList.size(); i++) {
                        Task t = taskList.get(i);
                        LocalDate hariIni = LocalDate.now();
                        String infoWaktu = "";

                        // Logika pengecekan waktu
                        if (t.getDeadline().isBefore(hariIni) && !t.isCompleted()) {
                            infoWaktu = " [⚠️ TERLAMBAT!]";
                        } else if (t.getDeadline().isEqual(hariIni)) {
                            infoWaktu = " [📅 HARI INI!]";
                        }

                        System.out.println((i + 1) + ". " + t.getStatusSymbol() + " " + t.getTitle() + " (Deadline: " + t.getDeadline() + ")" + infoWaktu);
}
                        System.out.println("------------------------------------");
                    }
                    break;
                case "3":
                    if (taskList.isEmpty()){
                        System.out.println("Daftar kosong, tidak ada yang bisa ditandai.");
                        break;
                    }
                    System.out.println("Nomor tugas yang selesai : ");
                   try {
                    int noSelesai = Integer.parseInt(scanner.nextLine());

                    if(noSelesai > 0 && noSelesai <= taskList.size()){
                        Task t = taskList.get(noSelesai-1);
                        t.setCompleted(true);
                        System.out.println("Bagus! Tugas " + t.getTitle() + " ditandai selesai.");
                    } else {
                        System.out.println("Nomor tidak valid!");
                    }
                 } catch (NumberFormatException e) {
                          System.out.println("⚠️ Input harus angka, bukan huruf!");
                          }
                    break;
                case "4":
                    if (taskList.isEmpty()) {
                        System.out.println("Daftar kosong, tidak bisa dihapus.");
                        break;
                    }
                    System.out.println("Nomor tugas yang akan dihapus : ");
                   try {
                    int noHapus = Integer.parseInt(scanner.nextLine());

                    if (noHapus > 0 && noHapus <= taskList.size()){
                        Task removedTask = taskList.remove(noHapus-1);
                        System.out.println("Tugas '" + removedTask.getTitle() + "' berhasil dihapus.");
                    } else {
                         System.out.println(" Nomor tidak valid!");
                        }
                   } catch (NumberFormatException e) {
                        System.out.println(" Input harus angka, bukan huruf!");
                        }
                    break;
                case "5" :
                        System.out.println("Masukkan kata kunci pencarian : ");
                        String keyword = scanner.nextLine();
                        boolean ditemukan = false;
                        for(int i = 0; i < taskList.size(); i++){
                           
                            Task t = taskList.get(i);

                            if (t.getTitle().toLowerCase().contains(keyword.toLowerCase())){
                                String status = t.isCompleted() ? "[v]" : "[ ]";
                                System.out.println(i + "." + status + " "+ t.getTitle());
                                ditemukan = true;
                            }
                    }
                        if (!ditemukan){
                                System.out.println("Tidak ada tugas yang cocok dengan kata kunci '" + keyword + "'.");
                            }
                    break;

                case "6":
                    System.out.println("Sedang menyimpan data...");
                    simpanData(); // <--- PANGGIL METHOD DI SINI
                    System.out.println("Terima kasih! Sampai jumpa.");
                    isRunning = false;
                    break;
                    default:
                    System.out.println("Pilihan tidak valid! Masukkan angka 1-5.");
            }
        }
        
    }
    private static void simpanData(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))){
            for (Task t : taskList){
                String status = t.isCompleted() ? "1" : "0" ;
                writer.write(status + ";" + t.getTitle() + ";" + t.getDeadline());
                writer.newLine();
            }
            System.out.println("Data berhasil disimpan ke data.txt");
            } catch (IOException e) {
             System.out.println("Gagal menyimpan data: " + e.getMessage());
            }
        }

    

    private static void muatData() {
        File file = new File("data.txt");

        if (!file.exists()){
            return;
        }
        try (BufferedReader reader= new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine())!= null) {
                String[] parts = line.split(";");

                if (parts.length == 3){
                    String status = parts[0];
                    String title = parts[1];
                    LocalDate deadline = LocalDate.parse(parts[2]);

                    Task t = new Task(title, deadline);
                    if (status.equals("1")){
                        t.markAsCompleted();
                    }
                    taskList.add(t);
                }
            }
            System.out.println("Data lama berhasil dimuat kembali!");
        } catch (IOException e){
            System.out.println("Gagal memuat data: " + e.getMessage());
        }
      }
    }

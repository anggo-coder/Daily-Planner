import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Task> taskList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        System.out.println("Selamat Datang di Daily Planner!");

        while (isRunning) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Tambah Tugas");
            System.out.println("2. Lihat Daftar Tugas");
            System.out.println("3. Tandai Selesai");
            System.out.println("4. Hapus Tugas");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu (1-5): ");

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
                    Task tugasBaru = new Task(judul);
                    taskList.add(tugasBaru);
                    System.out.println("Berhasil disimpan!");
                    break;
                case "2":
                    if (taskList.isEmpty()){
                        System.out.println("Belum ada tugas, silahkan tambahkan tugas pada menu 1!");
                    } else {
                        System.out.println("------------------------------------");
                        System.out.println("Total tugas: " + taskList.size());
                        for( int i = 0; i < taskList.size(); i++){
                            Task t = taskList.get(i);
                            System.out.println((i+1)+ "."+ t.getStatusSymbol()+ " " + t.getTitle());
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
                case "5":
                    System.out.println("Terima kasih! Sampai jumpa.");
                    isRunning = false; // Ini akan mematikan loop
                    break;
                default:
                    System.out.println("Pilihan tidak valid! Masukkan angka 1-5.");
            }
        }
    }
}
               

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

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    System.out.println("-> Fitur Tambah Tugas (Belum dibuat)");
                    break;
                case "2":
                    System.out.println("-> Fitur Lihat Tugas (Belum dibuat)");
                    break;
                case "3":
                    System.out.println("-> Fitur Tandai Selesai (Belum dibuat)");
                    break;
                case "4":
                    System.out.println("-> Fitur Hapus Tugas (Belum dibuat)");
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
# Daily Planner Pro - Java CLI

Aplikasi manajemen tugas harian berbasis terminal (CLI) dengan arsitektur modular, dukungan database SQLite, dan sistem kategorisasi tugas.

## Fitur Utama
- **Separation of Concerns:** Logika bisnis (Service) terpisah sepenuhnya dari antarmuka pengguna (Main).
- **Persistent Storage:** Menggunakan SQLite Database untuk penyimpanan data yang aman dan permanen.
- **Categorization System:** Mendukung pengelompokan tugas (misal: Kerja, Pribadi, Kuliah).
- **Advanced Filtering:** Fitur penyaringan tugas berdasarkan Prioritas dan Status.
- **Bulk Action:** Fitur pembersihan otomatis untuk menghapus semua tugas yang sudah selesai sekaligus.
- **Input Validation:** Validasi ketat untuk format tanggal dan pencegahan input deadline masa lalu.

## Struktur File
- **Main.java:** Menangani interaksi user (CLI), validasi input, dan routing menu.
- **TaskService.java:** Mengelola logika CRUD (Create, Read, Update, Delete) dan eksekusi query SQL.
- **Task.java:** Model objek tugas yang mencakup ID, Judul, Kategori, Deadline, Prioritas, dan Status.
- **Priority.java:** Enum untuk standarisasi level prioritas (TINGGI, SEDANG, RENDAH).
- **DatabaseConfig.java:** Mengelola koneksi JDBC ke file database `planner.db`.

## Cara Menjalankan
1. Pastikan Java Development Kit (JDK) sudah terinstal.
2. Tambahkan library `sqlite-jdbc.jar` ke dalam *classpath* atau *Referenced Libraries*.
3. Compile kode sumber: `javac -d bin src/*.java`.
4. Jalankan aplikasi: `java -cp "bin;lib/sqlite-jdbc.jar" Main`.
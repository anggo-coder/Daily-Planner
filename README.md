# Daily Planner Pro - Java CLI

Aplikasi manajemen tugas harian berbasis terminal (CLI) dengan arsitektur modular dan dukungan database.

## Fitur Utama
- Separation of Concerns: Logika bisnis dan UI dipisahkan secara modular.
- Priority System: Penggunaan Enum untuk manajemen prioritas (TINGGI, SEDANG, RENDAH).
- Input Validation: Mencegah input deadline di masa lalu.
- SQL Ready: Menggunakan SQLite untuk penyimpanan data yang lebih persisten.

## Struktur File
- Main.java: Menangani Input/Output user.
- TaskService.java: Mengelola logika dan penyimpanan data.
- Task.java: Representasi objek tugas.
- Priority.java: Tipe data khusus untuk prioritas.
- DatabaseConfig.java: Pengaturan koneksi database SQLite.

## Cara Menjalankan
1. Clone repositori ini.
2. Tambahkan `sqlite-jdbc.jar` ke Referenced Libraries di VS Code.
3. Jalankan `Main.java`.
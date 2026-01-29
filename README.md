# 📝 Daily Planner CLI (Java)

Aplikasi manajemen tugas (To-Do List) berbasis Command Line Interface (CLI) yang dibangun menggunakan bahasa pemrograman Java. Proyek ini dikembangkan secara bertahap untuk mendalami konsep Pemrograman Berorientasi Objek (OOP), Manajemen File, Manipulasi Tanggal, dan Arsitektur Kode yang Bersih.

---

## 📅 Catatan Perubahan (Changelog)

### v1.2: Pembaruan "Arsitektur & Statistik" (Versi Saat Ini) 🛠️
*Fokus: Refaktorisasi Kode, Arsitektur Modular, dan Dasbor Statistik.*

- **[Baru] Refaktorisasi Modular:** Pemisahan kode monolitik menjadi fungsi-fungsi spesifik (metode) untuk meningkatkan keterbacaan dan kemudahan pemeliharaan kode.
- **[Baru] Pengurutan Otomatis (Auto-Sorting):** Daftar tugas otomatis diurutkan berdasarkan tenggat waktu terdekat menggunakan algoritma pengurutan Java.
- **[Baru] Fitur Ubah Tugas (Edit):** Memungkinkan pengguna untuk memperbarui judul atau tenggat waktu pada tugas yang sudah tersimpan.
- **[Baru] Dasbor Statistik:** Menampilkan ringkasan data (Total Tugas, Tugas Selesai, Belum Selesai, dan Peringatan Terlambat) saat aplikasi dijalankan.
- **[Pembaruan] Sistem Simpan Otomatis:** Implementasi *Silent Save* yang memastikan data tersimpan secara otomatis setiap kali terjadi perubahan tanpa mengganggu navigasi pengguna.



### v1.1: Pembaruan "Fitur Cerdas" 🚀
*Fokus: Integrasi penanggalan, fitur pencarian, dan perbaikan antarmuka.*

- **[Baru] Sistem Tenggat Waktu (Deadline):** Integrasi `java.time.LocalDate` untuk menetapkan batas waktu pada setiap tugas.
- **[Baru] Fitur Pencarian:** Pencarian tugas berdasarkan kata kunci judul (tidak peka huruf besar/kecil).
- **[Pembaruan] Struktur Data:** Format penyimpanan pada `data.txt` ditingkatkan menjadi tiga kolom (`status;judul;tenggat_waktu`).
- **[Perbaikan] Validasi Input:** Penanganan kesalahan untuk format tanggal yang tidak sesuai dan pencegahan input data kosong.

### v1.0: Pembaruan "Persistensi Dasar" 💾
*Fokus: Fitur dasar CRUD dan penyimpanan data permanen.*

- **Fitur CRUD Dasar:** Implementasi fungsi Tambah, Lihat, Tandai Selesai, dan Hapus tugas.
- **Persistensi Data:** Penggunaan `BufferedWriter` dan `BufferedReader` untuk menyimpan data ke file teks agar tidak hilang saat aplikasi ditutup.
- **Penanganan Pengecualian (Exception Handling):** Validasi dasar untuk menangani kesalahan input karakter non-angka.

---

## 📂 Evolusi Struktur Data

Perubahan format penyimpanan data pada file `data.txt` seiring berkembangnya versi aplikasi.

**Format v1.1 & v1.2 (Terbaru):**
```text
0;Belajar Java;2026-02-14
1;Mengerjakan Proyek;2026-02-15
(Format: Status [0=Belum, 1=Selesai]; Judul; Tenggat Waktu [YYYY-MM-DD])

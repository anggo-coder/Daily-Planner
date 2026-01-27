# 📝 Daily Planner CLI (Java)

Aplikasi manajemen tugas (To-Do List) berbasis Command Line Interface (CLI) yang dibangun dengan Java. Proyek ini dikembangkan secara bertahap untuk mempelajari konsep OOP, Manajemen File, dan Manipulasi Tanggal.

---

## 📅 Riwayat Update (Changelog)

Berikut adalah perjalanan pengembangan aplikasi ini dari versi awal hingga sekarang.

### v1.1: The "Smart" Update (Versi Saat Ini) 🚀
*Fokus: Penambahan fitur tanggal, pencarian, dan perbaikan UX.*

- **[Baru] Sistem Deadline:** Integrasi `java.time.LocalDate`. Setiap tugas kini memiliki tanggal tenggat waktu (deadline).
- **[Baru] Fitur Pencarian (Search):** Mencari tugas spesifik menggunakan kata kunci (case-insensitive).
- **[Update] Struktur Data:** Format penyimpanan di `data.txt` diperbarui menjadi 3 kolom (`status;judul;deadline`).
- **[Fix] Validasi Input:** Mencegah aplikasi crash jika user memasukkan format tanggal yang salah atau data kosong.
- **[Fix] Penomoran List:** Daftar tugas kini dimulai dari angka 1 (sebelumnya index 0) agar lebih mudah dibaca.

### v1.0: The "Persistence" Update (Versi Awal) 💾
*Fokus: Fitur dasar CRUD dan penyimpanan data permanen.*

- **Fitur CRUD Dasar:**
  - [x] Tambah Tugas
  - [x] Lihat Daftar Tugas
  - [x] Tandai Selesai
  - [x] Hapus Tugas
- **Data Persistence:** Implementasi `BufferedWriter` dan `BufferedReader` agar data tersimpan di file `.txt` dan tidak hilang saat aplikasi ditutup.
- **Exception Handling:** Menangani error input angka (NumberFormatException).

---

## 📂 Evolusi Struktur Data

Perubahan format penyimpanan data di file `data.txt` seiring update versi:

**Format v1.0 (Lama):**
```text
0;Belajar Java Dasar
(Hanya Status dan Judul)
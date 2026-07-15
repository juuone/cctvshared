# HomeCam Blueprint

## Goal

Membuat aplikasi Android CCTV berbasis WebRTC.

Video HARUS P2P.

Tidak boleh melewati server.

Cloudflare Worker hanya digunakan sebagai signaling.

## Project Rules

- Java only
- Android Studio
- Material 3
- Clean Architecture
- No Firebase
- No Supabase
- No Node.js server
- No proprietary SDK
- MIT License

## Device Modes

- Camera
- Viewer

Satu APK untuk kedua mode.

## Workflow

Viewer
↓
Send START
↓
Worker
↓
Camera Device
↓
Open Camera
↓
Create WebRTC
↓
Stream
↓
Viewer

STOP melakukan kebalikannya.

## AI Rules

Jangan membuat placeholder.

Jangan membuat TODO.

Semua kode harus production-ready.

Jika file terlalu panjang, lanjutkan file berikutnya tanpa mengulang.

Selalu menjaga kompatibilitas dengan Android API 26+.

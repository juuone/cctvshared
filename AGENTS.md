AGENTS.md

HomeCam AI Development Rules

This repository is developed by AI coding agents.

Every AI agent working on this repository MUST follow these rules.

---

Primary Goal

Build a production-ready Android CCTV application using WebRTC.

Video MUST always be peer-to-peer (P2P).

Cloudflare Workers are used ONLY for signaling.

The server must NEVER relay video or audio.

---

Project Principles

- Production-ready code only.
- No placeholder implementations.
- No TODO comments.
- No fake methods.
- No stub classes.
- No incomplete features.
- Every generated file must compile successfully.
- Keep the project buildable after every change.

---

Technology Stack

- Java (100%)
- Android Studio
- Gradle
- Material Design 3
- Camera2 API
- Google WebRTC
- Cloudflare Workers
- STUN (Google)
- AndroidX

Never convert the project to Kotlin unless explicitly requested.

---

Single Source of Truth

The AI must always follow these documents in order:

1. BLUEPRINT.md
2. ARCHITECTURE.md
3. REQUIREMENTS.md
4. DESIGN.md
5. TASKS.md

If there is a conflict, BLUEPRINT.md always wins.

---

Project Architecture

One APK.

Two modes:

- Camera
- Viewer

The application must never be split into multiple APKs.

---

Networking Rules

Allowed:

- HTTPS
- WebRTC
- Cloudflare Worker
- STUN

Forbidden:

- Firebase
- Supabase
- Proprietary signaling services
- Third-party CCTV SDKs
- Video relay servers

Video and audio must never pass through the signaling server.

---

UI Rules

Use Material Design 3.

Support:

- Dark mode
- Light mode
- Responsive layouts
- Modern animations

Do not copy layouts from other projects.

---

Code Quality Rules

Write clean Java.

Avoid duplicated code.

Avoid long methods.

Extract reusable components.

Use meaningful class names.

Prefer composition over duplication.

Do not leave unused imports.

No dead code.

No commented-out code.

---

Resource Rules

Never hardcode:

- colors
- strings
- dimensions

Use:

- strings.xml
- colors.xml
- dimens.xml

---

Security Rules

Never hardcode:

- API keys
- tokens
- secrets

Always validate:

- Device ID
- Session Token
- Commands

Use secure random values where needed.

---

Android Rules

Use:

- Foreground Service
- WakeLock
- Camera2 API
- Runtime Permissions
- Notification Channel
- Lifecycle-aware components

Handle:

- Screen Off
- Internet Lost
- Device Rotation
- Background Restrictions
- Battery Optimization

---

Worker Rules

Generate a complete Cloudflare Worker.

The Worker only handles:

- Offer
- Answer
- ICE Candidate
- Commands
- Device Status

Never relay media streams.

---

Performance Rules

Prefer hardware acceleration.

Release camera immediately after stopping.

Release microphone immediately after stopping.

Avoid unnecessary object allocations.

Prevent memory leaks.

---

Logging Rules

Create a centralized Logger.

Verbose logging only in debug builds.

Disable sensitive logs in release builds.

Never log:

- Device tokens
- Session IDs
- ICE credentials

---

Documentation Rules

Every major class must include JavaDoc.

README.md must stay updated.

Update documentation whenever architecture changes.

---

Testing Rules

Every new feature should include a manual testing checklist.

Features must work on Android 8 through the latest Android version.

---

Commit Rules

Every completed task should:

- compile successfully
- not break existing features
- maintain backward compatibility

---

AI Behavior Rules

Before generating code:

1. Read BLUEPRINT.md.
2. Read TASKS.md.
3. Check existing architecture.
4. Reuse existing classes whenever possible.
5. Avoid creating duplicate implementations.

When modifying files:

- Preserve project style.
- Preserve architecture.
- Keep naming consistent.
- Keep the project buildable.

If the generated response reaches the context limit:

Continue automatically from the last unfinished file.

Never regenerate files that are already complete.

Never replace working code with placeholders.

Never simplify production code for brevity.

Always prefer correctness, maintainability, and production quality over shorter output.

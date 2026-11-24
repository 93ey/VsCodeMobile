# VS Code Mobile (prototype)

An Android starter project for a touch-friendly code editor inspired by the layout and workflow of VS Code. This is an independent project, not an official Microsoft product and not a port of the full VS Code desktop application.

## Included in this first build
- Dark, mobile-first editor interface
- Editable source area with line numbers and cursor position
- Android document picker to open files
- Save As using Android's Storage Access Framework
- New file, indent, and terminal preview panel
- No broad storage permission required

## Build APK
Requirements: Android Studio (recommended), JDK 17+, Android SDK Platform 35, Android Gradle Plugin 8.7.3.

1. Open this folder in Android Studio.
2. Let Gradle sync and install any requested Android SDK components.
3. Select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
4. The debug APK will be at `app/build/outputs/apk/debug/app-debug.apk`.

Command line (with Gradle installed): `gradle assembleDebug`.

## Current limitations
This is the first runnable foundation, not full VS Code parity. The terminal is a UI preview and does not execute shell commands; language intelligence, debugging, extensions, Git integration, and syntax highlighting are not implemented yet. The editor content is currently a basic text editor. Add a proper editor engine and a sandboxed local runtime in later milestones.
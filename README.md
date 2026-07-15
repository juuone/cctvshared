# HomeCam

HomeCam is a Java Android CCTV app that produces one APK with Camera and Viewer modes. Cloudflare Workers handle HTTPS signaling only; WebRTC media remains peer-to-peer using Google's STUN server.

## Build
Open the project in Android Studio, sync Gradle, and run `./gradlew assembleDebug` or `gradle assembleDebug`.

## Run
Grant camera, microphone, notification, and battery optimization permissions. Choose Camera Device on the CCTV phone and Viewer Device on the viewing phone. Enter the camera Device ID and send START CAMERA.

## Worker
Deploy `worker/` with Wrangler, then set the Worker HTTPS URL in app Settings. The Worker uses Cloudflare Cache API for short-lived signaling and does not require a KV namespace.

## Permissions
The app uses camera, microphone, foreground service, wake lock, notifications, network state, boot completed, and battery optimization permissions to stream while locked and reconnect after device events.

## Troubleshooting
If streaming fails, verify both devices have internet, the Worker URL is HTTPS, the Device ID starts with HOME, permissions are granted, and the network permits UDP WebRTC traffic.

## Architecture
`activity` owns UI, `service` owns background streaming, `webrtc` owns PeerConnection and media tracks, `network` owns Worker HTTPS signaling, `database` owns settings, and `utils` owns validation and logging.


## Manual testing checklist

- Install the debug APK on two Android 8+ devices.
- Grant camera, microphone, notification, and battery optimization permissions.
- Deploy the Worker and set the same HTTPS Worker URL on both devices.
- On the camera phone, choose Camera Device and start foreground camera mode.
- On the viewer phone, choose Viewer Device, enter the camera Device ID, and send START CAMERA.
- Verify STOP CAMERA releases camera capture and the foreground notification stop action shuts down streaming.
- Toggle front/back camera, mute, HD, reconnect, and network interruption scenarios.

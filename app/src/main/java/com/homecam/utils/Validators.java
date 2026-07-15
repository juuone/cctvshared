package com.homecam.utils;

import org.webrtc.IceCandidate;
import java.security.SecureRandom;
import java.util.Locale;

/** Validates IDs, commands, session tokens, and ICE candidates before network use. */
public final class Validators {
    private static final SecureRandom RANDOM = new SecureRandom();
    private Validators() { }
    public static boolean isValidDeviceId(String value) { return value != null && value.matches("HOME[0-9A-Z]{5,24}"); }
    public static String newDeviceId() { int n = 10000 + RANDOM.nextInt(90000); return String.format(Locale.US, "HOME%d", n); }
    public static String newSessionToken() { byte[] bytes = new byte[32]; RANDOM.nextBytes(bytes); StringBuilder b = new StringBuilder(); for (byte v : bytes) b.append(String.format(Locale.US, "%02x", v)); return b.toString(); }
    public static boolean isValidToken(String value) { return value != null && value.matches("[a-fA-F0-9]{64}"); }
    public static boolean isValidIce(IceCandidate c) { return c != null && c.sdp != null && c.sdp.length() < 4096 && c.sdpMid != null && c.sdpMLineIndex >= 0; }
}

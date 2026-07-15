package com.homecam.receiver;

import android.content.*;import com.homecam.database.SettingsStore;import com.homecam.service.StreamingService;

/** Restarts foreground streaming after boot when the user enabled automatic reconnect. */
public final class BootReceiver extends BroadcastReceiver { public void onReceive(Context c,Intent i){ if(Intent.ACTION_BOOT_COMPLETED.equals(i.getAction())&&new SettingsStore(c).isAutoReconnect()) c.startForegroundService(new Intent(c,StreamingService.class).setAction(StreamingService.ACTION_START)); } }

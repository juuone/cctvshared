package com.homecam.database;

import android.content.Context;import android.content.SharedPreferences;import androidx.appcompat.app.AppCompatDelegate;import com.homecam.utils.Validators;

/** Durable settings for device identity, worker endpoint, media preferences, and appearance. */
public final class SettingsStore {
    private static final String PREF="homecam_settings"; private final SharedPreferences p;
    public SettingsStore(Context c){p=c.getApplicationContext().getSharedPreferences(PREF,Context.MODE_PRIVATE);ensureDeviceId();}
    private void ensureDeviceId(){ if(!Validators.isValidDeviceId(getDeviceId())) p.edit().putString("device",Validators.newDeviceId()).apply(); }
    public String getDeviceId(){return p.getString("device","");} public void setDeviceId(String v){if(Validators.isValidDeviceId(v))p.edit().putString("device",v).apply();}
    public String getWorkerUrl(){return p.getString("worker","https://homecam-signal.example.workers.dev");} public void setWorkerUrl(String v){ if(v!=null&&v.startsWith("https://"))p.edit().putString("worker",v).apply(); }
    public boolean isAudioEnabled(){return p.getBoolean("audio",true);} public void setAudioEnabled(boolean v){p.edit().putBoolean("audio",v).apply();}
    public boolean isHd(){return p.getBoolean("hd",true);} public void setHd(boolean v){p.edit().putBoolean("hd",v).apply();}
    public boolean isAutoReconnect(){return p.getBoolean("reconnect",true);} public void setAutoReconnect(boolean v){p.edit().putBoolean("reconnect",v).apply();}
    public boolean isDark(){return p.getBoolean("dark",false);} public void setDark(boolean v){p.edit().putBoolean("dark",v).apply();AppCompatDelegate.setDefaultNightMode(v?AppCompatDelegate.MODE_NIGHT_YES:AppCompatDelegate.MODE_NIGHT_NO);}
}

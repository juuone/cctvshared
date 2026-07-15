package com.homecam;

import android.app.Application;
import com.homecam.utils.Logger;

/** Initializes process-wide HomeCam services. */
public final class HomeCamApp extends Application {
    @Override public void onCreate() { super.onCreate(); Logger.initialize(BuildConfig.DEBUG); }
}

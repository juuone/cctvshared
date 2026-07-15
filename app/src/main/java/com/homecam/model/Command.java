package com.homecam.model;

/** Supported signaling commands exchanged through the Cloudflare Worker. */
public enum Command { START, STOP, FLASH_ON, FLASH_OFF, FRONT_CAMERA, BACK_CAMERA, ENABLE_AUDIO, DISABLE_AUDIO, HD_ON, HD_OFF, PING }

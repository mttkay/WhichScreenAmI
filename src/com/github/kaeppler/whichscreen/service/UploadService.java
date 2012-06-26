package com.github.kaeppler.whichscreen.service;

import com.github.kaeppler.whichscreen.DeviceInfo;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UploadService extends IntentService {
    private static final String VERSION_LAST_UPLOADED = "uploadedVersionNumber";
    private static final String TAG = UploadService.class.getSimpleName();

    public UploadService() {
        super(UploadService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {

            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            final int versionLastUploaded = prefs.getInt(VERSION_LAST_UPLOADED, -1);
            final int myVersion;
            try {
                myVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            } catch (NameNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Don't upload if we already uploaded for this device.
            if (versionLastUploaded == myVersion)
                return;

            DeviceInfo deviceInfo = new DeviceInfo(this);
            HttpURLConnection urlConnection = null;
            URL url = null;

            try {
                url = new URL(
                        String.format("https://docs.google.com/spreadsheet/formResponse?formkey=dEExRVg5aDQ4NHhUTmZicFdvT2c2b2c6MQ&ifq?entry.0.single=%s&entry.1.single=%s&entry.2.single=%s&entry.3.single=%s&entry.4.single=%s&entry.5.single=%s&entry.6.single=%s&entry.7.single=%s&entry.8.single=%s&entry.9.single=%s&entry.10.single=%s&entry.11.single=%s&entry.12.single=%s",
                                URLEncoder.encode(deviceInfo.getHashOfAndroidId()),
                                myVersion,
                                deviceInfo.getMemoryClass(),
                                URLEncoder.encode(deviceInfo.getScaledDensity()),
                                URLEncoder.encode(deviceInfo.getScreenDensity()),
                                URLEncoder.encode(deviceInfo.getSizeClass()),
                                URLEncoder.encode(deviceInfo.getDensityClass()),
                                URLEncoder.encode(deviceInfo.getUiMode()),
                                URLEncoder.encode(deviceInfo.getScreenX()),
                                URLEncoder.encode(deviceInfo.getScreenY()),
                                URLEncoder.encode(deviceInfo.getHashOfAndroidId()),
                                URLEncoder.encode(deviceInfo.getAndroidVersion()),
                                URLEncoder.encode(deviceInfo.getDeviceModel())
                        ));
                urlConnection = (HttpURLConnection) url.openConnection();
                new BufferedInputStream(urlConnection.getInputStream()); // ignore the result
                Log.i(TAG, "Made request to " + url);
                // Record that we uploaded data for this version of the app
                prefs.edit().putInt(VERSION_LAST_UPLOADED, myVersion).commit();
            } catch (Exception e) {
                // Log but otherwise ignore exceptions since we can always just try again some other time
                Log.e(TAG, String.format("Unable to upload data to %s, will try again some other time", url), e);
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }

        } finally {
            stopSelf();
        }
    }
}

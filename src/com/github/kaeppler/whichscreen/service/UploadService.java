package com.github.kaeppler.whichscreen.service;

import com.github.kaeppler.whichscreen.DeviceInfo;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

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
            final String packageName = getPackageName();
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
                url = new URL(String.format("http://google.com/%s?versionCode=%s&memoryClass=%s&", packageName, myVersion, deviceInfo.getMemoryClass()));
                urlConnection = (HttpURLConnection) url.openConnection();
                //final InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                // ignore the result
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

package com.github.kaeppler.whichscreen;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

public class DeviceInfo {
    private static final int DENSITY_XHIGH = 320;
    private static final int DENSITY_HIGH = 240;
    private static final int DENSITY_TV = 213;
    private static final int DENSITY_MEDIUM = 160;
    private static final int DENSITY_LOW = 120;

    private static final int UI_MODE_TYPE_NORMAL = 1;
    private static final int UI_MODE_TYPE_DESK = 2;
    private static final int UI_MODE_TYPE_CAR = 3;
    private static final int UI_MODE_TYPE_TELEVISION = 4;

    private Context context;
    private DisplayMetrics displayMetrics;
    private Configuration configuration;

    public DeviceInfo(Context context) {
        this.context = context;
        this.displayMetrics = context.getResources().getDisplayMetrics();
        this.configuration = context.getResources().getConfiguration();
    }

    public String getScaledDensity() {
        return String.valueOf(displayMetrics.densityDpi);
    }

    public String getScreenDensity() {
        return (int) displayMetrics.xdpi + "x" + (int) displayMetrics.ydpi;
    }

    public String getSizeClass() {
        String sizeString = null;
        int size = configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (size) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                sizeString = "SMALL";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                sizeString = "NORMAL";
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                sizeString = "LARGE";
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                sizeString = "XLARGE";
                break;
            default:
                sizeString = "?";
        }
        return sizeString;
    }

    public String getDensityClass() {
        String density = null;
        switch (displayMetrics.densityDpi) {
            case DENSITY_XHIGH:
                density = "XHDPI";
                break;
            case DENSITY_HIGH:
                density = "HDPI";
                break;
            case DENSITY_TV:
                density = "TVDPI";
                break;
            case DENSITY_MEDIUM:
                density = "MDPI";
                break;
            case DENSITY_LOW:
                density = "LDPI";
                break;
            default:
                density = "?";
        }
        return density;
    }

    public String getUiMode() {
        String uiModeString = null;
        int uiMode = configuration.uiMode & Configuration.UI_MODE_TYPE_MASK;
        switch (uiMode) {
            case UI_MODE_TYPE_NORMAL:
                uiModeString = "NORMAL";
                break;
            case UI_MODE_TYPE_DESK:
                uiModeString = "DESK";
                break;
            case UI_MODE_TYPE_CAR:
                uiModeString = "CAR";
                break;
            case UI_MODE_TYPE_TELEVISION:
                uiModeString = "TV";
                break;
            default:
                uiModeString = "?";
        }
        return uiModeString;
    }

    public String getScreenX() {
        return String.valueOf(displayMetrics.widthPixels);
    }

    public String getScreenY() {
        return String.valueOf(displayMetrics.heightPixels);
    }

    public String getScaledScreenDim(String which) {
        Field field = null;
        try {
            field = configuration.getClass().getField(which);
            int scaledDim = (Integer) field.get(configuration);
            return String.valueOf(scaledDim);
        } catch (Exception e) {
            e.printStackTrace();
            return "?";
        }
    }

    public int getMemoryClass() {
        return ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
    }
}

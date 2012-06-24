package com.github.kaeppler.whichscreen;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class ScreenDetectorActivity extends Activity {

    private static final int DENSITY_XHIGH = 320;
    private static final int DENSITY_HIGH = 240;
    private static final int DENSITY_TV = 213;
    private static final int DENSITY_MEDIUM = 160;
    private static final int DENSITY_LOW = 120;

    private static final int UI_MODE_TYPE_NORMAL = 1;
    private static final int UI_MODE_TYPE_DESK = 2;
    private static final int UI_MODE_TYPE_CAR = 3;
    private static final int UI_MODE_TYPE_TELEVISION = 4;

    private Configuration configuration;
    private DisplayMetrics displayMetrics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        configuration = getResources().getConfiguration();
        displayMetrics = getResources().getDisplayMetrics();

        String density = getDensityClass();
        String size = getSizeClass();

        TextView text1 = (TextView) findViewById(R.id.text1);
        text1.setText(size + " @ " + density);

        TextView text2 = (TextView) findViewById(R.id.text2);
        StringBuilder sb = new StringBuilder();
        sb.append("Scaled Density: " + getScaledDensity());
        sb.append("\nReal Density: " + getScreenDensity());
        sb.append("\nUI mode: " + getUiMode());
        text2.setText(sb.toString());

        TextView textX = (TextView) findViewById(R.id.text_x);
        textX.setText(getScreenX() + "px\n" + getScaledScreenDim("screenWidthDp") + "dp");
        TextView textY = (TextView) findViewById(R.id.text_y);
        textY.setText(getScreenY() + "px\n" + getScaledScreenDim("screenHeightDp") + "dp");
    }

    private String getSizeClass() {
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

    private String getDensityClass() {
        String density = null;
        switch (displayMetrics.densityDpi) {
        case DENSITY_XHIGH:
            density = "XHDPI";
            break;
        case DENSITY_HIGH:
            density = "HDPI";
            break;
        case DENSITY_TV:
            density = "TV";
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

    private String getUiMode() {
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

    private String getScreenX() {
        return String.valueOf(displayMetrics.widthPixels);
    }

    private String getScreenY() {
        return String.valueOf(displayMetrics.heightPixels);
    }

    private String getScaledScreenDim(String which) {
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

    private String getScaledDensity() {
        return String.valueOf(displayMetrics.densityDpi);
    }

    private String getScreenDensity() {
        return (int) displayMetrics.xdpi + "x" + (int) displayMetrics.ydpi;
    }

}
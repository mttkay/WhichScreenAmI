package com.github.kaeppler.whichscreen;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

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
        textX.setText(getScreenX() + "px");
        TextView textY = (TextView) findViewById(R.id.text_y);
        textY.setText(getScreenY() + "px");
    }

    private String getSizeClass() {
        String sizeString = null;
        Configuration c = getResources().getConfiguration();
        int size = c.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
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
        DisplayMetrics dm = getResources().getDisplayMetrics();
        String density = null;
        switch (dm.densityDpi) {
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
        Configuration c = getResources().getConfiguration();
        int uiMode = c.uiMode & Configuration.UI_MODE_TYPE_MASK;
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
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return String.valueOf(dm.widthPixels);
    }

    private String getScreenY() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return String.valueOf(dm.heightPixels);
    }

    private String getScaledDensity() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return String.valueOf(dm.densityDpi);
    }

    private String getScreenDensity() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) dm.xdpi + "x" + (int) dm.ydpi;
    }

}
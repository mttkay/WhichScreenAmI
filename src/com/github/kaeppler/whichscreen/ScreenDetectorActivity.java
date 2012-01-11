package com.github.kaeppler.whichscreen;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class ScreenDetectorActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        String density = null;
        switch (dm.densityDpi) {
        case DisplayMetrics.DENSITY_XHIGH:
            density = "XHDPI";
            break;
        case DisplayMetrics.DENSITY_HIGH:
            density = "HDPI";
            break;
        case DisplayMetrics.DENSITY_MEDIUM:
            density = "MDPI";
            break;
        case DisplayMetrics.DENSITY_LOW:
            density = "LDPI";
            break;
        default:
            density = "default or unknown";
        }

        Configuration c = getResources().getConfiguration();
        int sizeMask = c.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        String size = null;
        switch (sizeMask) {
        case Configuration.SCREENLAYOUT_SIZE_SMALL:
            size = "SMALL";
            break;
        case Configuration.SCREENLAYOUT_SIZE_NORMAL:
            size = "NORMAL";
            break;
        case Configuration.SCREENLAYOUT_SIZE_LARGE:
            size = "LARGE";
            break;
        case Configuration.SCREENLAYOUT_SIZE_XLARGE:
            size = "XLARGE";
            break;
        default:
            size = "UNDEFINED";
        }

        TextView text = (TextView) findViewById(R.id.text);
        text.setText(density + " / " + size);
    }
}
package com.github.kaeppler.whichscreen;

import com.github.kaeppler.whichscreen.service.UploadService;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ScreenDetectorActivity extends Activity {


    private DeviceInfo deviceInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        deviceInfo = new DeviceInfo(this);

        String density = deviceInfo.getDensityClass();
        String size = deviceInfo.getSizeClass();

        TextView text1 = (TextView) findViewById(R.id.text1);
        text1.setText(size + " @ " + density);

        TextView text2 = (TextView) findViewById(R.id.text2);
        StringBuilder sb = new StringBuilder();
        sb.append("Scaled Density: " + deviceInfo.getScaledDensity());
        sb.append("\nReal Density: " + deviceInfo.getScreenDensity());
        sb.append("\nUI mode: " + deviceInfo.getUiMode());
        text2.setText(sb.toString());

        TextView textX = (TextView) findViewById(R.id.text_x);
        textX.setText(deviceInfo.getScreenX() + "px\n" + deviceInfo.getScaledScreenDim("screenWidthDp") + "dp");
        TextView textY = (TextView) findViewById(R.id.text_y);
        textY.setText(deviceInfo.getScreenY() + "px\n" + deviceInfo.getScaledScreenDim("screenHeightDp") + "dp");

        // Upload this device's info if necessary
        startService(new Intent(this, UploadService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "View Report").setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/spreadsheet/ccc?key=0Anf-WW-Nv9fhdEExRVg5aDQ4NHhUTmZicFdvT2c2b2c")));
        return true;
    }
}

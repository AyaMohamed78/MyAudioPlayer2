package com.example.myaudioplayer;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MusicBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case Intent.ACTION_BATTERY_LOW:
                    // Handle low battery event
                    Toast.makeText(context, "Battery is low!", Toast.LENGTH_SHORT).show();
                    break;
                case Intent.ACTION_POWER_CONNECTED:
                    // Handle device plugged in
                    Toast.makeText(context, "Device is charging", Toast.LENGTH_SHORT).show();
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    // Handle device unplugged
                    Toast.makeText(context, "Device is no longer charging", Toast.LENGTH_SHORT).show();
                    break;
                case Intent.ACTION_BOOT_COMPLETED:
                    // Handle device boot completed
                    Toast.makeText(context, "Device boot completed", Toast.LENGTH_SHORT).show();
                    break;
                // Add more cases for other system-level actions if needed
            }
        }
    }
}

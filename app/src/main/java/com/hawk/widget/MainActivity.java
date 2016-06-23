
package com.hawk.widget;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.hawk.widget.utils.SMLog;
import com.hawk.widget.utils.UStats;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        //Check if permission enabled
        if (!UStats.isUsageStatsON(this)){
            // Settings\Security\Apps with usage access
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*
    // Hawk: For unknown reason, APP will crash, so I remarked them.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            SMLog.i("","onKeyDown = KEYCODE_HOME");
            //The Code Want to Perform.
        }
        else if (keyCode == KeyEvent.KEYCODE_CALL || keyCode == KeyEvent.KEYCODE_ENDCALL || keyCode == KeyEvent.KEYCODE_SETTINGS)
        {
            SMLog.i("","onKeyDown = "+ keyCode);
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        else{
            SMLog.i("","onKeyDown = "+ keyCode);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        SMLog.i("","onBackPressed = ");
        super.onBackPressed();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        }
    }
    */
}


package com.hawk.widget;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hawk.widget.utils.HomeWatcher;
import com.hawk.widget.utils.OnHomePressedListener;
import com.hawk.widget.utils.SMLog;
import com.hawk.widget.utils.UStats;

public class MainActivity extends AppCompatActivity {
    Button mMyBtn;
    TextView mMyTv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyBtn  = (Button) findViewById(R.id.Button1);
        mMyTv2  = (TextView) findViewById(R.id.textView2);
        mMyBtn.setOnClickListener(mMyToggleBtnListener);

        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        //Check if permission enabled
        if (!UStats.isUsageStatsON(this)){
            mMyTv2.setText("please ENABLE this APP.");
        }
        else{
            mMyTv2.setText("don't change this setting.");
        }
    }

    private View.OnClickListener mMyToggleBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "Please ENABLE it!", Toast.LENGTH_SHORT).show();
            // Settings\Security\Apps with usage access
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        HomeWatcher mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                SMLog.i("","onHomePressed+++++++++++++++++"); // Hawk: Test OK on API23
                // do something here...
            }
            @Override
            public void onHomeLongPressed() {
                SMLog.i("","onHomeLongPressed+++++++++++++++++"); // Hawk: Test failed on API23
            }
        });
        mHomeWatcher.startWatch();

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

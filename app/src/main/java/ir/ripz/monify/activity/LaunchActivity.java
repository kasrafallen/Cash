package ir.ripz.monify.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Calendar;

import ir.ripz.monify.R;
import ir.ripz.monify.instance.DataManager;
import ir.ripz.monify.notify.AlarmReceiver;
import ir.ripz.monify.util.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LaunchActivity extends Activity {
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createView());
        check();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void check() {
        Util util = new Util(this);
        Log.d("DATA", "check() returned: " + new DataManager(this).test());
        if (!util.isDimen()) {
            setObserver(util);
        } else {
            if (util.isStarted()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                        finish();
                    }
                }, 1000);
            } else {
                startActivity(new Intent(LaunchActivity.this, StartActivity.class));
                finish();
            }
        }
    }

    private void setObserver(final Util util) {
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                util.setDimen(new float[]{layout.getWidth(), layout.getHeight()});
                setAlarm();
                check();
            }
        });
    }

    private void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 45);

        Intent downloader = new Intent(this, AlarmReceiver.class);
        PendingIntent recurringDownload = PendingIntent.getBroadcast(this, 0, downloader, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarms = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarms.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, recurringDownload);
    }

    private View createView() {
        layout = new RelativeLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        layout.setBackgroundResource(R.color.theme);
        layout.addView(logo());
        return layout;
    }

    private View logo() {
        Button button = new Button(this);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(getSize(), getSize());
        p.addRule(RelativeLayout.CENTER_IN_PARENT);
        button.setLayoutParams(p);
        return button;
    }

    public int getSize() {
        float density = getResources().getDisplayMetrics().density;
        return (int) (150 * density + 0.5f);
    }
}

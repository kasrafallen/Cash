package ir.ripz.monify.notify;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class BootService extends IntentService {
    public BootService(String name) {
        super(name);
    }

    public BootService() {
        super("BootService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        setAlarm();
        BootReceiver.completeWakefulIntent(intent);
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
}

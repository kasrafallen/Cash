package ir.ripz.monify.notify;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.text.NumberFormat;

import ir.ripz.monify.R;
import ir.ripz.monify.activity.LaunchActivity;
import ir.ripz.monify.instance.DataManager;

public class AlarmService extends IntentService {
    public AlarmService(String name) {
        super(name);
    }

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        showNotification();
        AlarmReceiver.completeWakefulIntent(intent);
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("فاکتور").setContentText(getMessage())
                .setAutoCancel(true).setOngoing(false).setContentIntent(getIntent());

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(125, notification);
        }
    }

    private PendingIntent getIntent() {
        return PendingIntent.getActivity(this, 125, new Intent(this, LaunchActivity.class), 0);
    }

    private CharSequence getMessage() {
        long sum = new DataManager(this).getTodayAll();
        return "مجموع هزینه های امروز:" + "\n" + NumberFormat.getNumberInstance().format(sum) + " تومان";
    }
}

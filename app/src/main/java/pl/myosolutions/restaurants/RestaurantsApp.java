package pl.myosolutions.restaurants;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;

import pl.myosolutions.restaurants.alarms.RemoveReservationsBroadcastReceiver;

public class RestaurantsApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
          scheduleAlarm();
    }

    private void scheduleAlarm() {
        long intervalTime = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

        Intent intent = new Intent(this, RemoveReservationsBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis() + intervalTime , intervalTime, pendingIntent);
        }

    }

}

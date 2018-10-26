package pl.myosolutions.restaurants.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pl.myosolutions.restaurants.database.AppRepository;

public class RemoveReservationsBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AppRepository repository = AppRepository.getInstance(context);
        repository.resetReservations();
    }
}

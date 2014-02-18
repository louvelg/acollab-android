package com.akelio.android.acollab.receiver;

import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.akelio.android.acollab.core.news.service.NewsService;

public class NewsReceiver extends BroadcastReceiver {
	private static final String	TAG					= NewsReceiver.class.getSimpleName();
	private static final long	DEFAULT_INTERVAL	= AlarmManager.INTERVAL_FIFTEEN_MINUTES;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("BootReceiver", "onReceived");
		context.startService(new Intent(context, NewsService.class));

		PendingIntent operation = PendingIntent.getService(context, -1, new Intent(context, NewsService.class), PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		long interval = DEFAULT_INTERVAL;
		if (interval == 0) {
			alarmManager.cancel(operation);
			Log.d(TAG, "cancelling repeat operation");
		} else {
			Calendar c = Calendar.getInstance();
			alarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), c.getTimeInMillis() + 60000, operation);
			Log.d(TAG, "setting repeat operation for: " + interval);
		}
	}
}

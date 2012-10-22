/**
 * 
 */
package com.witwatersrand.androidapplication.scheduler;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * A daily scheduler
 * @author Kailesh Ramjee - University of Witwatersrand - School of Electrical & Information Engineering
 *
 */
public class DailyScheduler {


	public DailyScheduler(Context context) {
		Calendar updateTime = Calendar.getInstance();
		updateTime.setTimeZone(TimeZone.getTimeZone("GMT"));
		updateTime.set(Calendar.HOUR_OF_DAY, 03);
		updateTime.set(Calendar.MINUTE, 30);

		Intent downloader = new Intent(context, DailyResetTimerTask.class);
		PendingIntent recurringDownload = PendingIntent.getBroadcast(context,
				0, downloader, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				updateTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
				recurringDownload);
	}
}

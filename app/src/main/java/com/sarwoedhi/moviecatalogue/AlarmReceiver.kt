package com.sarwoedhi.moviecatalogue

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.sarwoedhi.moviecatalogue.models.Movie
import com.sarwoedhi.moviecatalogue.models.MovieResponse
import com.sarwoedhi.moviecatalogue.service.ApiClient
import com.sarwoedhi.moviecatalogue.service.ApiInterface
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var pendingIntent: PendingIntent
    private lateinit var pendingIntentMovie: PendingIntent

    override fun onReceive(context: Context, intent: Intent?) {
        val code = intent?.getStringExtra(EXTRA_CODE)
        showAlarmNotification(context, code ?: "")
    }


    private fun showAlarmNotification(context: Context, code: String) {
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var builder: NotificationCompat.Builder
        if (code == "movie") {
            val date: Date = Calendar.getInstance().time
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
            val releaseDate: String = df.format(date)
            val api: ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
            val call: Call<MovieResponse> =
                api.getReleaseToday(BuildConfig.API_KEY, releaseDate, releaseDate)
            val movies: ArrayList<Movie> = ArrayList()
            call.doAsync {
                call.enqueue(object : Callback<MovieResponse> {
                    override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                    }

                    override fun onResponse(
                        call: Call<MovieResponse>?,
                        response: Response<MovieResponse>?
                    ) {
                        val body = response?.body()
                        if (body != null) {
                            if (!body.results.isNullOrEmpty()) {
                                for (i in body.results.iterator()) {
                                    movies.add(
                                        Movie(
                                            i.id,
                                            i.title,
                                            i.overview,
                                            i.release_date,
                                            i.posterPath,
                                            i.backdropPath,
                                            i.runtime
                                        )
                                    )
                                    builder = NotificationCompat.Builder(context, "1")
                                        .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                                        .setContentTitle("New Movie : ${i.title}")
                                        .setContentText(i.overview).setColor(
                                            ContextCompat.getColor(
                                                context,
                                                android.R.color.transparent
                                            )
                                        ).setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                                        .setSound(alarmSound)
                                        .setStyle(NotificationCompat.BigTextStyle().bigText(i.overview))
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        val channel = NotificationChannel(
                                            "1",
                                            "Notification Day",
                                            NotificationManager.IMPORTANCE_DEFAULT
                                        )
                                        channel.enableVibration(true)
                                        channel.vibrationPattern =
                                            longArrayOf(1000, 1000, 1000, 1000, 1000)
                                        builder.setChannelId("1")
                                        notificationManagerCompat.createNotificationChannel(channel)
                                    }
                                    val notification: Notification = builder.build()
                                    notificationManagerCompat.notify(1, notification)
                                }
                            }
                        }
                    }
                })
            }
        } else {
            builder = NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentTitle("Miss you")
                .setContentText("Hello! Movie catalogue missing you!")
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "1",
                    "Notification Day",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
                builder.setChannelId("1")
                notificationManagerCompat.createNotificationChannel(channel)
            }
            val notification: Notification = builder.build()
            notificationManagerCompat.notify(1, notification)
        }
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_CODE, "repeating")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        alarmManager.cancel(pendingIntent)
        Toast.makeText(
            context,
            context.getString(R.string.toast_reminder_disabled),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun setRepeatingAlarm(context: Context) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_CODE, "repeating")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(
            context,
            context.getString(R.string.toast_reminder_enabled),
            Toast.LENGTH_SHORT
        ).show()

    }

    fun cancelMovieAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)

        intent.putExtra(EXTRA_CODE, "movie")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        pendingIntentMovie = PendingIntent.getBroadcast(context, 1, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntentMovie
        )

        alarmManager.cancel(pendingIntentMovie)
        Toast.makeText(
            context,
            context.getString(R.string.release_reminder_disabled),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun setMovieAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        intent.putExtra(EXTRA_CODE, "movie")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        pendingIntentMovie = PendingIntent.getBroadcast(context, 1, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntentMovie
        )

        Toast.makeText(
            context,
            context.getString(R.string.release_reminder_enabled),
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val EXTRA_CODE = "EXTRA_CODE"
    }
}
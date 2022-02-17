package com.pd.currencyconverter.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        Log.e("Timer Service", "Service tried to stop");
//            Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show();

        val time = p1?.getDoubleExtra("time", 0.0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("Timer Service", "broadcast onDestroy: Foreground ")
            context?.startForegroundService(
                Intent(
                    context,
                    TimerService::class.java
                ).putExtra(TimerService.TIME_EXTRA, time)
            )
        } else {
            Log.e("Timer Service", "broadcast onDestroy: Normal ")
            context?.startService(
                Intent(
                    context,
                    TimerService::class.java
                ).putExtra(TimerService.TIME_EXTRA, time)
            )
        }
    }
}
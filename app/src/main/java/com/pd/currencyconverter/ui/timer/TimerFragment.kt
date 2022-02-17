package com.pd.currencyconverter.ui.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.pd.currencyconverter.NavigationActivity
import com.pd.currencyconverter.databinding.FragmentTimerBinding
import com.pd.currencyconverter.services.TimerService
import com.pd.currencyconverter.utils.ConstantUtils

class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding

    private var timerStarted = false
    private lateinit var serviceIntent: Intent


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.btnStartTimer.setOnClickListener {
            if (timerStarted)
                stopTimer()
            else
                startTimer()
        }

        binding.btnResetTimer.setOnClickListener {
            resetTimer()
        }

        serviceIntent = Intent(context, TimerService::class.java)
        requireActivity().registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))

        return root
    }

    private fun resetTimer() {
        stopTimer()
        NavigationActivity.time = 0.0
        binding.tvCountdownTimer.text =
            TimerService.getTimeStringFromDouble(NavigationActivity.time)
    }

    private fun startTimer() {
        PreferenceManager.getDefaultSharedPreferences(requireActivity()).edit()
            .putBoolean(ConstantUtils.KEY_TIMER_CHECK, true).apply()
        serviceIntent.putExtra(TimerService.TIME_EXTRA, NavigationActivity.time)
        requireActivity().startService(serviceIntent)
        binding.btnStartTimer.text = "Stop"
//        binding.startStopButton.icon = getDrawable(R.drawable.ic_baseline_pause_24)
        timerStarted = true
    }

    private fun stopTimer() {
        PreferenceManager.getDefaultSharedPreferences(requireActivity()).edit()
            .putBoolean(ConstantUtils.KEY_TIMER_CHECK, false).apply()
        binding.btnStartTimer.text = "Start"
//        binding.startStopButton.icon = getDrawable(R.drawable.ic_baseline_play_arrow_24)
        timerStarted = false
        requireActivity().stopService(serviceIntent)
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            NavigationActivity.time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            binding.tvCountdownTimer.text =
                TimerService.getTimeStringFromDouble(NavigationActivity.time)
            binding.btnStartTimer.text = "Stop"
            timerStarted = true
        }
    }

//    override fun onDestroy() {
//        val check = PreferenceManager.getDefaultSharedPreferences(requireContext())
//            .getBoolean(ConstantUtils.KEY_TIMER_CHECK, false)
//        if (check) {
//            val broadcastIntent = Intent()
//            broadcastIntent.action = "restartservice"
//            broadcastIntent.setClass(requireContext(), TimerService.MyBroadcastReceiver::class.java)
//            broadcastIntent.putExtra("time", NavigationActivity.time)
//            requireContext().sendBroadcast(broadcastIntent)
//            Log.e("Timer Service", "Frag onDestroy: Broadcast" )
//        }else{
//            Log.e("Timer Service", "Frag onDestroy: Check OFF" )
//        }
//        super.onDestroy()
//    }
}
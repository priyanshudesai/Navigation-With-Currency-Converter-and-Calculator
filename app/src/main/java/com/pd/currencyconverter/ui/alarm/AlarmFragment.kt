package com.pd.currencyconverter.ui.alarm

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pd.currencyconverter.R
import com.pd.currencyconverter.adapter.AlarmListAdapter
import com.pd.currencyconverter.databinding.FragmentAlarmBinding
import com.pd.currencyconverter.dataclass.AlarmEntity
import com.pd.currencyconverter.utils.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class AlarmFragment : Fragment() {

    private lateinit var binding: FragmentAlarmBinding
    var listAlarm: List<AlarmEntity>? = null
    var listAdapter: AlarmListAdapter? = null
    lateinit var alarmViewModel: AlarmViewModel
    private lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlarmBinding.inflate(layoutInflater)
        var root: View = binding.root

        calendar = Calendar.getInstance()

        val sdf = SimpleDateFormat("dd/MMM/yyyy")
        val currentDate = sdf.format(Date())

        val stf = SimpleDateFormat("hh:mm aa")
        val currentTime = stf.format(Date())

        binding.tvDatePreview.text = currentDate
        binding.tvTimePreview.text = currentTime

        alarmViewModel = ViewModelProvider(requireActivity()).get(AlarmViewModel::class.java)
        alarmViewModel.getAllAlarmObservers().observe(viewLifecycleOwner, Observer {

            listAlarm = it
            listAdapter = activity?.let { it1 ->
                AlarmListAdapter(
                    it1,
                    listAlarm
                )
            }
            binding.rvAlarmFrag.adapter = listAdapter
            binding.rvAlarmFrag.adapter?.notifyDataSetChanged()

            if (listAlarm?.isNotEmpty() == true) {
                binding.rvAlarmFrag.visibility = View.VISIBLE
            } else {
                binding.rvAlarmFrag.visibility = View.GONE
            }

        })

        binding.btnSaveAlarm.setOnClickListener {
            if (binding.etDescription.text.isNotEmpty()) {
                Toast.makeText(context, "Alarm Saved", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "Alarm Save")
                val cTime = System.currentTimeMillis().toInt()

                saveAlarmModel(cTime)
                setAlarm(cTime, binding.etDescription.text.toString())
                binding.etDescription.text.clear()
            } else {
                binding.etDescription.error = "Please enter Description!!"
            }
        }

        binding.btnSetDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                R.style.MyTimePickerDialogStyle,
                { picker, year, month, date ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, date)
                    binding.tvDatePreview.text = sdf.format(calendar.timeInMillis)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
            ).show()
        }

        binding.btnSetTime.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                R.style.MyTimePickerDialogStyle,
                { picker, hour, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    binding.tvTimePreview.text = stf.format(calendar.timeInMillis)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            ).show()

        }

        return root
    }

    private fun saveAlarmModel(id: Int) {
        val alarmEntity = AlarmEntity(
            id = id,
            date = binding.tvDatePreview.text.toString(),
            time = binding.tvTimePreview.text.toString(),
            description = binding.etDescription.text.toString()
        )
        Log.e("TAG", "saveAlarmModel: ")
        alarmViewModel.insertAlarmInfo(alarmEntity)
    }

    private fun setAlarm(id: Int, description: String) {
        calendar.set(Calendar.SECOND, 0)
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("description", description)
        intent.putExtra("id", id)
        intent.action = "com.pd.currencyconverter.alarm"
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}
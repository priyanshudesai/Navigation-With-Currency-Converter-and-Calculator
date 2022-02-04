package com.pd.currencyconverter.ui.alarm

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.pd.currencyconverter.R
import com.pd.currencyconverter.adapter.EmployeeListAdapter
import com.pd.currencyconverter.databinding.FragmentAlarmBinding
import com.pd.currencyconverter.dataclass.AlarmEntity
import com.pd.currencyconverter.ui.cardlist.CardListViewModel
import com.pd.currencyconverter.utils.AlarmReceiver
import com.pd.currencyconverter.utils.ConstantUtils
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class AlarmFragment : Fragment() {

    companion object {
        private const val ALARM_REQUEST_CODE = 1000
    }

    private lateinit var binding: FragmentAlarmBinding
    lateinit var alarmViewModel: AlarmViewModel
    private lateinit var calendar : Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlarmBinding.inflate(layoutInflater)
        var root :View = binding.root

        calendar = Calendar.getInstance()

        val sdf = SimpleDateFormat("dd/MMM/yyyy")
        val currentDate = sdf.format(Date())

        val stf = SimpleDateFormat("hh:mm aa")
        val currentTime = stf.format(Date())

        binding.tvDatePreview.text = currentDate
        binding.tvTimePreview.text = currentTime

        alarmViewModel = ViewModelProvider(requireActivity()).get(AlarmViewModel::class.java)
//        alarmViewModel.getAllAlarmObservers().observe(viewLifecycleOwner, Observer {
//
////            listEmployee = it
////            listAdapter = activity?.let { it1 ->
////                EmployeeListAdapter(
////                    it1,
////                    listEmployee
////                )
////            }
////            binding.rvCardFrag.adapter = listAdapter
////            binding.rvCardFrag.adapter?.notifyDataSetChanged()
////
////            if (listEmployee?.isNotEmpty() == true) {
////                binding.tvTotalCardFrag.text = listEmployee?.size.toString() + getString(R.string.cards_total)
////
////                binding.pbCard.visibility = View.GONE
////                binding.tvTotalCardFrag.visibility = View.VISIBLE
////                binding.rvCardFrag.visibility = View.VISIBLE
////
////            }
//
//        })

        binding.btnSaveAlarm.setOnClickListener {
            if (binding.etDescription.text.isNotEmpty()) {
                Log.e("TAG", "Alarm Save")
                val cTime = System.currentTimeMillis().toInt()

                saveAlarmModel(cTime)
                setAlarm(cTime,binding.etDescription.text.toString())
            }else{
                binding.etDescription.error = "Please enter Description!!"
            }
        }

        binding.btnSetDate.setOnClickListener {
            DatePickerDialog(requireContext(), { picker, year, month ,date ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, date)
                binding.tvDatePreview.text = sdf.format(calendar.timeInMillis)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),).show()
        }

        binding.btnSetTime.setOnClickListener {
            TimePickerDialog(requireContext(), { picker, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                binding.tvTimePreview.text = stf.format(calendar.timeInMillis)
//                val model = saveAlarmModel(hour, minute, false)
//                renderView(model)
//                cancelAlarm()
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show()

        }

//        val model = fetchDataFromSharedPreferences()
//        renderView(model)

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

    fun setAlarm(id: Int, description: String){
        calendar.set(Calendar.SECOND, 0)
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("description", description)
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



//    private fun fetchDataFromRoomDatabase() {
//
//
////        val timeDBValue = sharedPreferences?.getString(ALARM_KEY, "9:30") ?: "9:30"
////        val onOffDBValue = sharedPreferences?.getBoolean(ONOFF_KEY, false)
//
//
////        val alarmData = timeDBValue.split(":")
////
////        val alarmModel = AlarmDisplayModel(
////            hour = alarmData[0].toInt(),
////            minute = alarmData[1].toInt(),
////            onOff = onOffDBValue!!
////        )
//
//
////        val pendingIntent = PendingIntent.getBroadcast(requireContext(), ALARM_REQUEST_CODE, Intent(requireContext(), AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE)
////
////        if ((pendingIntent == null) and alarmModel.onOff) {
////            alarmModel.onOff = false
////        } else if ((pendingIntent != null) and alarmModel.onOff.not()){
////            pendingIntent.cancel()
////        }
//
//        return alarmModel
//
//    }

//    private fun cancelAlarm() {
//        Log.e("TAG", "cancelAlarm: ")
//        val pendingIntent = PendingIntent.getBroadcast(requireContext(), ALARM_REQUEST_CODE, Intent(requireContext(), AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE)
//        pendingIntent?.cancel()
//    }
}
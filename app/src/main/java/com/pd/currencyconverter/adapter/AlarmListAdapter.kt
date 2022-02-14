package com.pd.currencyconverter.adapter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.pd.currencyconverter.R
import com.pd.currencyconverter.dataclass.AlarmEntity
import com.pd.currencyconverter.services.AlarmReceiver
import com.pd.currencyconverter.ui.alarm.AlarmViewModel

class AlarmListAdapter(
    private var context: Context,
    private var listAlarm: List<AlarmEntity>?

) :
    RecyclerView.Adapter<AlarmListAdapter.ViewHolder>() {

    var alarmViewModel: AlarmViewModel =
        ViewModelProvider(context as FragmentActivity).get(AlarmViewModel::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_alarm_list, null)

        val viewHolder = ViewHolder(itemView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alarmData: AlarmEntity? = listAlarm?.get(position)

        holder.time.text = alarmData?.time
        holder.date.text = alarmData?.date
        holder.description.text = alarmData?.description

        holder.btnDelete.setOnClickListener {
            Toast.makeText(context, "Alarm Deleted", Toast.LENGTH_SHORT).show()
            alarmViewModel.deleteAlarmInfo(alarmData!!.id)

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.action = "com.pd.currencyconverter.alarm"
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                alarmData.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager.cancel(pendingIntent)
        }

    }

    override fun getItemCount(): Int {
        return listAlarm!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var time: TextView = itemView.findViewById(R.id.tv_time_alarm)
        var date: TextView = itemView.findViewById(R.id.tv_date_alarm)
        var description: TextView = itemView.findViewById(R.id.tv_description_alarm)
        var btnDelete: ImageButton = itemView.findViewById(R.id.ib_delete_alarm)
    }
}
package com.pd.currencyconverter.ui.alarm

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pd.currencyconverter.database.RoomAppDb
import com.pd.currencyconverter.dataclass.AlarmEntity
import com.pd.currencyconverter.utils.AlarmReceiver
import java.util.*

class AlarmViewModel(application: Application) : AndroidViewModel(application) {
    var allAlarms: MutableLiveData<List<AlarmEntity>> = MutableLiveData()

    init {
        getAllAlarm()
    }

    fun getAllAlarmObservers(): MutableLiveData<List<AlarmEntity>> {
        return allAlarms
    }

    private fun getAllAlarm() {
        val alarmDao = RoomAppDb.getAppDatabase((getApplication()))?.databaseDao()
        val list = alarmDao?.getAllAlarms()
        allAlarms.postValue(list!!)
    }

    fun deleteAlarmInfo(id: Int) {
        val alarmDao = RoomAppDb.getAppDatabase((getApplication()))?.databaseDao()
        alarmDao?.deleteAlarm(id)
        getAllAlarm()
    }

    fun insertAlarmInfo(entity: AlarmEntity) {
        val databaseDao = RoomAppDb.getAppDatabase((getApplication()))?.databaseDao()
        databaseDao?.insertAlarm(entity)
        getAllAlarm()
    }


//
//    fun deleteEmployeeInfo(entity: EmployeeEntity) {
//        val employeeDao = RoomAppDb.getAppDatabase((getApplication()))?.employeeDao()
//        employeeDao?.deleteEmployee(entity)
//        getAllEmployee()
//    }
//
//    fun updateEmployeeInfo(entity: EmployeeEntity) {
//        val employeeDao = RoomAppDb.getAppDatabase((getApplication()))?.employeeDao()
//        employeeDao?.updateEmployee(entity)
//        getAllEmployee()
//    }
}
package com.pd.currencyconverter.ui.cardlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pd.currencyconverter.database.RoomAppDb
import com.pd.currencyconverter.dataclass.EmployeeEntity

class CardListViewModel(application: Application) : AndroidViewModel(application) {
    var allEmployee: MutableLiveData<MutableList<EmployeeEntity>> = MutableLiveData()

    init {
        getAllEmployee()
    }


    fun getAllEmployeeObservers(): MutableLiveData<MutableList<EmployeeEntity>> {
        return allEmployee
    }

    private fun getAllEmployee() {
        val employeeDao = RoomAppDb.getAppDatabase((getApplication()))?.databaseDao()
        val list = employeeDao?.getAllEmployees()
        allEmployee.postValue(list!!)

    }

    fun insertEmployeesInfo(entity: MutableList<EmployeeEntity>) {
        val employeeDao = RoomAppDb.getAppDatabase((getApplication()))?.databaseDao()
        employeeDao?.insertEmployees(entity)
        getAllEmployee()
    }

//    fun insertEmployeeInfo(entity: EmployeeEntity) {
//        val employeeDao = RoomAppDb.getAppDatabase((getApplication()))?.employeeDao()
//        employeeDao?.insertEmployee(entity)
//        getAllEmployee()
//    }
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

package com.pd.currencyconverter.ui.cardlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pd.currencyconverter.dataclass.EmployeeEntity
import com.pd.currencyconverter.database.RoomAppDb

class CardListViewModel(application: Application) : AndroidViewModel(application) {
    var allEmployee: MutableLiveData<List<EmployeeEntity>> = MutableLiveData()

    init {
        getAllEmployee()
    }


    fun getAllEmployeeObservers(): MutableLiveData<List<EmployeeEntity>> {
        return allEmployee
    }

    private fun getAllEmployee() {
        val employeeDao = RoomAppDb.getAppDatabase((getApplication()))?.employeeDao()
        val list = employeeDao?.getAllEmployees()
        allEmployee.postValue(list!!)

    }

    fun insertEmployeeInfo(entity: EmployeeEntity) {
        val employeeDao = RoomAppDb.getAppDatabase((getApplication()))?.employeeDao()
        employeeDao?.insertEmployee(entity)
        getAllEmployee()
    }

    fun insertEmployeesInfo(entity: List<EmployeeEntity>) {
        val employeeDao = RoomAppDb.getAppDatabase((getApplication()))?.employeeDao()
        employeeDao?.insertEmployees(entity)
        getAllEmployee()
    }

    fun deleteEmployeeInfo(entity: EmployeeEntity) {
        val employeeDao = RoomAppDb.getAppDatabase((getApplication()))?.employeeDao()
        employeeDao?.deleteEmployee(entity)
        getAllEmployee()
    }

    fun updateEmployeeInfo(entity: EmployeeEntity) {
        val employeeDao = RoomAppDb.getAppDatabase((getApplication()))?.employeeDao()
        employeeDao?.updateEmployee(entity)
        getAllEmployee()
    }
}

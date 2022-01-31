package com.pd.currencyconverter.api

import com.pd.currencyconverter.dataclass.EmployeeListDataClass
import com.pd.currencyconverter.utils.ConstantUtils
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET(ConstantUtils.API_LIST_EMPLOYEES)
    fun listEmployees(): Call<EmployeeListDataClass?>?

}
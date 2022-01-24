package com.pd.currencyconverter.api

import com.pd.currencyconverter.dataclass.EmployeeListDataClass
import retrofit2.Call
import retrofit2.http.GET

public interface Api {

    @GET("list")
    fun listEmployees(): Call<EmployeeListDataClass?>?

}
package com.example.attendancereport.rertofit

import com.example.attendancereport.model.EmployeeDetails
import com.example.attendancereport.model.EmployeeModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("?e76c37b493ea168cea60b8902072387caf297979")
    fun getEmployees(): Call<List<EmployeeModel>>



    @FormUrlEncoded
    @POST("?e76c37b493ea168cea60b8902072387caf297979")
    fun getEmployeeDetails(
        @Field("emp_id") employeeid: String,
        @Field("from_dt") fromdate:String,
        @Field("to_dt") todate:String): Call<List<EmployeeDetails>>


}


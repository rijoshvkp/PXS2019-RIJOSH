package com.example.attendancereport.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class EmployeeModel {

    @SerializedName("emp_id")
    @Expose
    val empId: String? = null
    @SerializedName("name")
    @Expose
    val name: String? = null

    override fun toString(): String {
        return "$name"
    }


}
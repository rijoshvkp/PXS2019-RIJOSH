package com.example.attendancereport.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class EmployeeDetails {

    @SerializedName("emp_id")
    @Expose
    var empId: String? = null
    @SerializedName("entry_at")
    @Expose
    var entryAt: String? = null
    @SerializedName("exit_at")
    @Expose
    var exitAt: String? = null

}
package com.example.attendancereport.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancereport.rertofit.ApiClient
import com.example.attendancereport.model.EmployeeDetails
import com.example.attendancereport.R
import com.example.attendancereport.adapter.RecyclerviewAdapter
import kotlinx.android.synthetic.main.activity_employee_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class EmployeeDetailsActivity : AppCompatActivity() {

    lateinit var fromdate: String
    lateinit var todate: String
    lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)
        fromdate = intent.getStringExtra("From")
        todate = intent.getStringExtra("To")
        id = intent.getStringExtra("EmployeeId")
        getEmployees()
    }


    /////////////////////////////get employee details/////////////////////////////////////

    private fun getEmployees() {
        loading.visibility= View.VISIBLE
        val call: Call<List<EmployeeDetails>> =
            ApiClient.getClient.getEmployeeDetails(id, fromdate, todate)
        call.enqueue(object : Callback<List<EmployeeDetails>> {
            override fun onResponse(
                call: Call<List<EmployeeDetails>>?, response: Response<List<EmployeeDetails>>?
            ) {

                val rv = findViewById<RecyclerView>(R.id.rv_data)
                rv.adapter = RecyclerviewAdapter(
                    ArrayList(response?.body()!!),
                    this@EmployeeDetailsActivity
                )
                calculateHours(ArrayList(response?.body()!!))
                loading.visibility= View.GONE

            }

            override fun onFailure(call: Call<List<EmployeeDetails>>?, t: Throwable?) {
            }

        })
    }


////////////////////////////////////hour calculation///////////////////////////////////////////////

    private fun calculateHours(arrayList: ArrayList<EmployeeDetails>) {
        var total= 0.0
        for (i in arrayList.indices) {
            val dateFormat = "yyyy-MM-dd kk:mm:ss"
            val startDate = SimpleDateFormat(dateFormat).parse(arrayList[i].entryAt.toString())
            val endDate = SimpleDateFormat(dateFormat).parse(arrayList[i].exitAt.toString())
            var secs = (endDate.time - startDate.time) / 1000
            val hours = secs / 3600
            secs %= 3600
            var mins = (secs / 60).toString()
            if(mins.toInt()<10){
                mins="0"+ mins

            }
            total = total +("$hours.$mins".toDouble())
            tv_totalhour.text=(Math.round(total*100F)/100F).toString()
        }
        present.setText( arrayList.distinctBy { it.entryAt.toString().split(" ".toRegex())[0]  }.size.toString())

    }

}

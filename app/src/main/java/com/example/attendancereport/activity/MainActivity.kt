package com.example.attendancereport.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import com.whiteelephant.monthpicker.MonthPickerDialog
import android.widget.Toast
import com.example.attendancereport.rertofit.ApiClient
import com.example.attendancereport.model.EmployeeModel
import com.example.attendancereport.R


class MainActivity : AppCompatActivity() {
    lateinit var mySpinner: SearchableSpinner
    var fromdate: String=""
     var todate: String=""
    lateinit var today: Calendar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        listener()
        getEmployees()

    }



////////////////////////////////////init function/////////////////////////////////////////////////

    private fun init() {

        mySpinner = findViewById(R.id.search) as SearchableSpinner
        today = Calendar.getInstance()
    }

////////////////////////////////////get total month/////////////////////////////////////////////////

    private fun getTotalMonth( year:Int, month:Int) {
        val mycal = GregorianCalendar(year, month, 0)
        val daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val years = mycal.get(Calendar.YEAR)
        todate=""+years+"-"+"0"+month+"-"+daysInMonth
        fromdate=""+years+"-"+"0"+month+"-"+"01"


    }

    private fun listener() {

        month.setOnClickListener{

            getMonthandYear()


        }

        btn_details.setOnClickListener{

            var data=mySpinner.selectedItem

            if(data !=null){

                var getvalue: EmployeeModel =data as EmployeeModel

                if(getvalue.empId==null){

                    Toast.makeText(this,"no employee id",Toast.LENGTH_LONG).show()

                }else if(fromdate.equals("")){
                    Toast.makeText(this,"select date",Toast.LENGTH_LONG).show()

                }else{
                    intent = Intent(this, EmployeeDetailsActivity::class.java)
                    intent.putExtra("EmployeeId", getvalue.empId)
                    intent.putExtra("From", fromdate)
                    intent.putExtra("To", todate)
                    startActivity(intent)
                }


                }else{
                Toast.makeText(this,"select employee ",Toast.LENGTH_LONG).show()

            }


        }


    }


    ////////////////////////////month and year picker//////////////////////////////////////////

    private fun getMonthandYear() {
        val builder = MonthPickerDialog.Builder(this@MainActivity, MonthPickerDialog.OnDateSetListener { selectedMonth, selectedYear ->

            getTotalMonth(selectedYear,selectedMonth+1)
            var date=selectedYear.toString()+" - "+(selectedMonth+1)

            month.setText(date)


            }, today.get(Calendar.YEAR), today.get(Calendar.MONTH)
        )
        builder.setActivatedMonth(Calendar.OCTOBER)
            .setMinYear(1990)
            .setActivatedYear(2019)
            .setMaxYear(2030)
            .setMinMonth(Calendar.OCTOBER)
            .setTitle("Select Month and Year")
            .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
            .build()
            .show()    }

///////////////////////////////////////get employee details////////////////////////////////////////

    private fun getEmployees() {
        val call: Call<List<EmployeeModel>> = ApiClient.getClient.getEmployees()
        call.enqueue(object : Callback<List<EmployeeModel>> {
            override fun onResponse(
                call: Call<List<EmployeeModel>>?, response: Response<List<EmployeeModel>>?
            ) {

                search.adapter = ArrayAdapter<EmployeeModel>(
                    this@MainActivity,
                    android.R.layout.simple_spinner_item,
                    response!!.body()!!.toList()
                )
            }
            override fun onFailure(call: Call<List<EmployeeModel>>?, t: Throwable?) {
            }

        })
    }









}

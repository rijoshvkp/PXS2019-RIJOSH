package com.example.attendancereport.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancereport.R
import com.example.attendancereport.model.EmployeeDetails
import kotlinx.android.synthetic.main.rowitem.view.*
import java.text.SimpleDateFormat

class RecyclerviewAdapter(val arraylist: ArrayList<EmployeeDetails>, val context: Context) :
    RecyclerView.Adapter<RecyclerviewAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        val view: View = LayoutInflater.from(context).inflate(R.layout.rowitem, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {

        return arraylist.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {


        arraylist.get(position).entryAt.toString().split(" ".toRegex())[1];

        holder?.punchin?.text = arraylist.get(position).entryAt.toString().split(" ".toRegex())[1]
        holder?.punchout?.text = arraylist.get(position).exitAt.toString().split(" ".toRegex())[1]
        holder?.date?.text = arraylist.get(position).entryAt.toString().split(" ".toRegex())[0]
        holder?.timespend?.text = calcHours(arraylist.get(position).entryAt.toString(),arraylist.get(position).exitAt.toString())

    }

    class MyHolder(view: View) : RecyclerView.ViewHolder(view) {

        val date: TextView = view.tv_currentdate
        val punchin: TextView = view.tv_punchin
        val punchout: TextView = view.tv_punchout
        val timespend: TextView = view.tv_timespend

    }


    private fun calcHours(entryAt: String, exitAt: String):String {
        val dateFormat="yyyy-MM-dd kk:mm:ss"
        val startDate= SimpleDateFormat(dateFormat).parse(entryAt)
        val endDate= SimpleDateFormat(dateFormat).parse(exitAt)
        var secs=(endDate.time-startDate.time)/1000
        val hours=secs/3600
        secs %= 3600
        val mins=secs/60
        return "$hours.$mins"
    }

}
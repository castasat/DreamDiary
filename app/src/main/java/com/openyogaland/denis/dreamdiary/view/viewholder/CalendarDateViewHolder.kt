package com.openyogaland.denis.dreamdiary.view.viewholder

import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.openyogaland.denis.dreamdiary.R

class CalendarDateViewHolder(private val calendarDateItemView : View)
  : ViewHolder(calendarDateItemView)
{
  lateinit var calendarDateTextView : AppCompatTextView
  lateinit var itemClickListener : OnItemClickListener
  
  init
  {
    findViewsByIds()
    setListeners()
  }
  
  private fun findViewsByIds()
  {
    calendarDateTextView = calendarDateItemView.findViewById(R.id.calendarDateTextView)
  }
  
  private fun setListeners()
  {
    calendarDateTextView.setOnClickListener {
      // TODO
    }
  }
}
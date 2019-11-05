package com.openyogaland.denis.dreamdiary.view.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.listener.OnCalendarDateItemClickListener
import com.openyogaland.denis.dreamdiary.listener.OnCalendarDateItemLongClickListener

class
CalendarDateViewHolder(calendarDateView : View,
                       onItemClickListener : OnCalendarDateItemClickListener?,
                       onItemLongClickListener : OnCalendarDateItemLongClickListener?)
  : ViewHolder(calendarDateView)
{
  private lateinit var calendarDate : String
  
  val calendarDateTextView : AppCompatTextView =
    calendarDateView.findViewById(R.id.calendarDateTextView)
  
  init
  {
    calendarDateView
    .setOnClickListener {_ : View ->
      onItemClickListener?.onCalendarDateItemClick(calendarDate)
    }
    
    calendarDateView
    .setOnLongClickListener {_ : View ->
      onItemLongClickListener?.onCalendarDateItemLongClick(calendarDate)
      true
    }
  }
  
  fun
  bindCalendarDate(calendarDate : String)
  {
    this.calendarDate = calendarDate
  }
}
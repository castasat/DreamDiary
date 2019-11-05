package com.openyogaland.denis.dreamdiary.adapter

import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.listener.OnCalendarDateItemClickListener
import com.openyogaland.denis.dreamdiary.listener.OnCalendarDateItemLongClickListener
import com.openyogaland.denis.dreamdiary.view.viewholder.CalendarDateViewHolder

class CalendarDateAdapter(var calendarDates : ArrayList<String>,
                          private var onCalendarDateItemClickListener
                          : OnCalendarDateItemClickListener?,
                          private var onCalendarDateItemLongClickListener
                          : OnCalendarDateItemLongClickListener?)
  : Adapter<CalendarDateViewHolder>()
{
  override fun onCreateViewHolder(parent : ViewGroup,
                                  viewType : Int) : CalendarDateViewHolder
  {
    val calendarDateItemView : View
    with(from(parent.context)) {
      when(viewType)
      {
        // TODO
        else -> calendarDateItemView = inflate(R.layout.item_calendar_date_layout, parent, false)
      }
    }
    return CalendarDateViewHolder(calendarDateItemView,
                                  onCalendarDateItemClickListener,
                                  onCalendarDateItemLongClickListener)
  }
  
  override fun getItemCount() : Int
  {
    return calendarDates.size
  }
  
  override fun onBindViewHolder(calendarDateViewHolder : CalendarDateViewHolder,
                                position : Int)
  {
    val calendarDate =
      calendarDates[calendarDateViewHolder.adapterPosition]
    
    val calendarDateTextView =
      calendarDateViewHolder.calendarDateTextView
    
    calendarDateViewHolder.bindCalendarDate(calendarDate)
    calendarDateTextView.text = calendarDate
  }
  
  fun addCalendarDates(calendarDates : List<String>)
  {
    this.calendarDates
    .addAll(calendarDates)
  }
}
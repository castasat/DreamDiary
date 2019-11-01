package com.openyogaland.denis.dreamdiary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.view.viewholder.CalendarDateViewHolder

class CalendarDateAdapter(private val context : Context,
                          /* TODO */ private val data : List<CalendarDateItem>)
  : Adapter<CalendarDateViewHolder>()
{
  private val layoutInflater = LayoutInflater.from(context)
  
  // inflates item_calendar_date_layout from XML when needed
  override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CalendarDateViewHolder
  {
    // TODO select viewType
    val view = layoutInflater.inflate(R.layout.item_calendar_date_layout, parent, false)
    return CalendarDateViewHolder(view)
  }
  
  // bind data to text view of each cell
  override fun onBindViewHolder(holder : CalendarDateViewHolder, position : Int)
  {
    holder.calendarDateTextView.text = data[position]
  }
  
  override fun getItemCount() : Int
  {
    return data.size
  }
}
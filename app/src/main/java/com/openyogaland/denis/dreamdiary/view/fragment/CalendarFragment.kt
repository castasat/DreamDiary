package com.openyogaland.denis.dreamdiary.view.fragment

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.adapter.CalendarDateAdapter
import com.openyogaland.denis.dreamdiary.listener.OnCalendarDateItemClickListener
import com.openyogaland.denis.dreamdiary.listener.OnCalendarDateItemLongClickListener
import com.openyogaland.denis.dreamdiary.viewmodel.CalendarViewModel

@Suppress("NAME_SHADOWING")
class
CalendarFragment : Fragment()
{
  // view fields
  private var monthDaysRecyclerView : RecyclerView? = null
  
  // architecture fields
  private lateinit var calendarViewModel : CalendarViewModel
  
  override fun
  onCreateView(inflater : LayoutInflater,
               container : ViewGroup?,
               savedInstanceState : Bundle?) : View?
  {
    val view : View = inflater.inflate(R.layout.calendar_fragment, container, false)
    
    monthDaysRecyclerView = view.findViewById<RecyclerView>(R.id.monthDaysRecyclerView)
    
    monthDaysRecyclerView
    ?.apply {
      setHasFixedSize(true)
      layoutManager = GridLayoutManager(context, DAYS_IN_A_WEEK)
      adapter = CalendarDateAdapter(ArrayList<String>(MAX_DAYS_IN_A_MONTH),
                                    object : OnCalendarDateItemClickListener
                                    {
                                      override fun
                                      onCalendarDateItemClick(calendarDate : String)
                                      {
                                        // TODO
                                      }
                                    },
                                    object : OnCalendarDateItemLongClickListener
                                    {
                                      override fun
                                      onCalendarDateItemLongClick(calendarDate : String)
                                      {
                                        // TODO
                                      }
                                    })
      recycledViewPool.setMaxRecycledViews(0, MAX_DAYS_IN_A_MONTH)
    }
    
    calendarViewModel =
      ViewModelProvider(this)
      .get(CalendarViewModel::class.java)
    
    calendarViewModel
    .calendarDatesLiveData
    .observe(viewLifecycleOwner,
             Observer<Array<String>>
             {calendarDates : Array<String> ->
               (monthDaysRecyclerView?.adapter as CalendarDateAdapter)
               .let {calendarDateAdapter : CalendarDateAdapter ->
                 calendarDateAdapter
                 .apply {
                   this.calendarDates.clear()
                   addCalendarDates(calendarDates.toList())
                   notifyDataSetChanged()
                 }
               }
             })
    
    calendarViewModel.fillCurrentMonthWithDates()
    return view
  }
  
  override fun onDestroyView()
  {
    super.onDestroyView()
    // prevent memory leak
    monthDaysRecyclerView?.adapter = null
    monthDaysRecyclerView = null
  }
  
  override fun
  onActivityCreated(savedInstanceState : Bundle?)
  {
    super.onActivityCreated(savedInstanceState)
    activity
    ?.let {activity : FragmentActivity ->
      activity.window
      ?.let {window : Window ->
        // from API 19
        if(VERSION.SDK_INT >= VERSION_CODES.KITKAT)
        {
          window.clearFlags(FLAG_TRANSLUCENT_STATUS)
          // from API 21
          if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP)
          {
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(activity, R.color.translucent)
          }
        }
      }
    }
  }
  
  companion object
  {
    private const val WEEK_ROWS = 5
    const val DAYS_IN_A_WEEK = 7
    const val MAX_DAYS_IN_A_MONTH = DAYS_IN_A_WEEK * WEEK_ROWS
  }
}
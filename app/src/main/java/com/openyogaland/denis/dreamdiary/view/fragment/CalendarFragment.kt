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
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.openyogaland.denis.dreamdiary.R

@Suppress("NAME_SHADOWING")
class
CalendarFragment : Fragment()
{
  companion object{
    const val DAYS_IN_A_WEEK = 7
    const val ROWS = 5
  }
  
  override fun
  onCreateView(inflater : LayoutInflater,
               container : ViewGroup?,
               savedInstanceState : Bundle?) : View?
  {
    val view : View = inflater.inflate(R.layout.calendar_fragment,
                                       container, false)
    
    val monthDaysRecyclerView = view.findViewById<RecyclerView>(R.id.monthDaysRecyclerView)
    monthDaysRecyclerView.layoutManager = GridLayoutManager(requireContext(), DAYS_IN_A_WEEK)
    monthDaysRecyclerView.adapter =
      object : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder
        {
          val view = AppCompatTextView(parent.context).apply {
            text = "10"
          }
          return  object : ViewHolder(view){}
        }
        override fun getItemCount() : Int = DAYS_IN_A_WEEK * ROWS
        override fun onBindViewHolder(holder : ViewHolder, position : Int) {}
      }
    monthDaysRecyclerView.recycledViewPool.setMaxRecycledViews(0, DAYS_IN_A_WEEK * ROWS)
    
    return view
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
}
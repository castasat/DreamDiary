package com.openyogaland.denis.dreamdiary.view.custom

import android.annotation.TargetApi
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.graphics.Color.BLACK
import android.graphics.Color.RED
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Calendar.DATE
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.DAY_OF_WEEK
import java.util.Calendar.MONTH
import java.util.Calendar.YEAR
import java.util.Date
import java.util.Locale
import android.widget.TextView
import android.graphics.Typeface
import android.view.Gravity.CENTER
import androidx.core.content.ContextCompat
import com.openyogaland.denis.dreamdiary.R

class CalendarView : ConstraintLayout
{
  // view fields
  private lateinit var previousMonthImageView : AppCompatImageView
  private lateinit var monthLabelTextView : AppCompatTextView
  private lateinit var nextMonthImageView : AppCompatImageView
  private lateinit var weekDaysLinearLayout : LinearLayoutCompat
  private lateinit var monthDaysGridView : GridView
  
  constructor(context : Context)
  : super(context)
  
  constructor(context : Context, attrs : AttributeSet)
  : super(context, attrs)
  
  constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int)
  : super(context, attrs, defStyleAttr)
  
  @TargetApi(21)
  constructor(context : Context, attrs : AttributeSet, defStyleAttr : Int, defStyleRes : Int)
  : super(context, attrs, defStyleAttr, defStyleRes)
  
  init
  {
    initControl(context)
    findViewsByIds()
  }
  
  private fun initControl(context : Context)
  {
    (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)
    .inflate(R.layout.calendar_view_layout, this)
  }
  
  private fun findViewsByIds()
  {
    previousMonthImageView = findViewById(R.id.previousMonthImageView)
    monthLabelTextView = findViewById(R.id.monthLabelTextView)
    nextMonthImageView = findViewById(R.id.nextMonthImageView)
    weekDaysLinearLayout = findViewById(R.id.weekDaysLinearLayout)
    monthDaysGridView = findViewById(R.id.monthDaysGridView)
  }
  
  fun updateCalendar(events : HashSet<Date>)
  {
    val cells = ArrayList<Date>()
    val calendar = Calendar.getInstance()
    
    returnCalendarToWeekBeginning(calendar, monthBeginningCel(calendar))
    fillCells(cells, calendar)
    updateGrid(monthDaysGridView, cells, events)
    updateMonth(calendar)
  }
  
  private fun monthBeginningCel(calendar : Calendar) : Int
  {
    calendar.set(DAY_OF_MONTH, 1)
    return calendar.get(DAY_OF_WEEK - 2)
  }
  
  private fun returnCalendarToWeekBeginning(calendar : Calendar,
                                            monthBeginningCell : Int)
  {
    calendar.add(DAY_OF_MONTH, -monthBeginningCell)
  }
  
  private fun fillCells(cells : ArrayList<Date>,
                        calendar : Calendar)
  {
    val cellsSize = cells.size
    while(cellsSize < DAYS_COUNT)
    {
      cells.add(calendar.time)
      calendar.add(DAY_OF_MONTH, 1)
    }
  }
  
  private fun updateGrid(monthDaysGridView : GridView,
                         cells : ArrayList<Date>,
                         events : HashSet<Date>)
  {
    monthDaysGridView.adapter = CalendarAdapter(context, cells, events)
  }
  
  private fun updateMonth(calendar : Calendar)
  {
    val simpleDateFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    val currentMonth = simpleDateFormat.format(calendar.time)
    monthLabelTextView.text = currentMonth.toUpperCase(Locale.getDefault())
  }
  
  class CalendarAdapter(context : Context,
                        cells : ArrayList<Date>,
                        private val events : HashSet<Date>)
    : ArrayAdapter<Date>(context, R.layout.calendar_view_layout, cells)
  {
    private val inflater = LayoutInflater.from(context)
    
    override fun getView(position : Int, convertView : View?, parent : ViewGroup) : View
    {
      val view =
        convertView
        ?: inflater.inflate(R.layout.calendar_view_layout, parent, false)
      
      // day in question
      val calendar = Calendar.getInstance()
      getItem(position)?.let {date ->
        calendar.time = date
      }
      val day = calendar.get(DATE)
      val month = calendar.get(MONTH)
      val year = calendar.get(YEAR)
      
      // today
      val today = Date()
      val calendarToday = Calendar.getInstance()
      calendarToday.time = today
      
      with(view)
      {
        // clear styling
        (this as TextView).setTypeface(null, Typeface.NORMAL)
        setTextColor(BLACK)
        
        if(month != calendarToday.get(MONTH) ||
           year != calendarToday.get(YEAR))
        {
          // if this day is outside current month, grey it out
          setTextColor(ContextCompat.getColor(context, R.color.gray))
        }
        else if(day == calendarToday.get(DATE))
        {
          // if it is today, set it to blue/bold
          setTextColor(RED)
          gravity = CENTER
          //view.setBackgroundResource(R.drawable.round_textview)
        }
        
        // set text
        text = calendar.get(DATE).toString()
      }
      return view
    }
  }
}

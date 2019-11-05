package com.openyogaland.denis.dreamdiary.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.openyogaland.denis.dreamdiary.application.DreamDiary.DreamDiary.log
import com.openyogaland.denis.dreamdiary.view.fragment.CalendarFragment.Companion.MAX_DAYS_IN_A_MONTH
import java.lang.IllegalStateException
import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.DAY_OF_WEEK
import java.util.Calendar.MONTH

class
CalendarViewModel(application : Application)
  : BaseViewModel(application)
{
  // live data fields
  val calendarDatesLiveData = MutableLiveData<List<String>>()
  
  // reactive fields
  
  init
  {
    initializeApplicationContext(application)
  }
  
  fun
  fillCurrentMonthWithDates()
  {
    val currentTimeCalendar = Calendar.getInstance()
    fillMonthWithDates(currentTimeCalendar)
  }
  
  fun
  fillMonthWithDates(selectedMonthCalendar : Calendar)
  {
    val calendarDates = ArrayList<String>(MAX_DAYS_IN_A_MONTH)
    val firstDayIndex : Int
    
    val maxDaysInSelectedMonth = determineSelectedMonthLengthInDays(selectedMonthCalendar)
    log("CalendarViewModel.fillMonthWithDates: maxDaysInSelectedMonth = $maxDaysInSelectedMonth")
    
    setTimeCalendarToFirstDayOfMonth(selectedMonthCalendar)
    
    val dayOfWeekForFirstDayOfMonth = determineWeekDay(selectedMonthCalendar)
    log("CalendarViewModel.fillMonthWithDates: dayOfWeekForFirstDayOfMonth = $dayOfWeekForFirstDayOfMonth")
    
    firstDayIndex =
      when(dayOfWeekForFirstDayOfMonth)
      {
        SUNDAY    -> SUNDAY_INDEX
        MONDAY    -> MONDAY_INDEX
        TUESDAY   -> TUESDAY_INDEX
        WEDNESDAY -> WEDNESDAY_INDEX
        THURSDAY  -> THURSDAY_INDEX
        FRIDAY    -> FRIDAY_INDEX
        SATURDAY  -> SATURDAY_INDEX
        else      -> throw IllegalStateException("firstDayIndex should be in the range of week day indexes")
      }
    
    val maxDaysInPreviousMonth = determinePreviousMonthLengthInDays(selectedMonthCalendar)
    log("CalendarViewModel.fillMonthWithDates: maxDaysInPreviousMonth = $maxDaysInPreviousMonth")
    
    // fill previous month
    var previousMonthDay = maxDaysInPreviousMonth - (firstDayIndex - 1)
    for(calendarDate in 0 until firstDayIndex)
    {
      calendarDates.add(calendarDate, calendarDate.toString())
      log("CalendarViewModel.fillMonthWithDates: previousMonthDay = $previousMonthDay")
      previousMonthDay++
    }
    
    // fill current month
    for(calendarDate in 1..maxDaysInSelectedMonth)
    {
      calendarDates.add((firstDayIndex - 1) + calendarDate, calendarDate.toString())
      log("CalendarViewModel.fillMonthWithDates: currentMonthDay = $calendarDate")
    }
    
    // fill next month
    var nextMonthDay = 1
    for(calendarDate in 1..(MAX_DAYS_IN_A_MONTH - (maxDaysInSelectedMonth + firstDayIndex)))
    {
      calendarDates.add(calendarDate, nextMonthDay.toString())
      log("CalendarViewModel.fillMonthWithDates: nextMonthDay = $nextMonthDay")
      nextMonthDay++
    }
    calendarDatesLiveData.postValue(calendarDates)
  }
  
  private fun
  determinePreviousMonthLengthInDays(selectedMonthCalendar : Calendar) : Int
  {
    val previousMonthCalendar = selectedMonthCalendar.clone() as Calendar
    setTimeCalendarToPreviousMonth(previousMonthCalendar)
    return determineSelectedMonthLengthInDays(previousMonthCalendar)
  }
  
  private fun setTimeCalendarToPreviousMonth(previousMonthCalendar : Calendar)
  {
    previousMonthCalendar.add(MONTH, PREVIOUS_MONTH)
  }
  
  private fun
  determineWeekDay(selectedMonthCalendar : Calendar) : Int
  {
    return selectedMonthCalendar.get(DAY_OF_WEEK) // Sunday is 1
  }
  
  private fun
  determineSelectedMonthLengthInDays(selectedMonthCalendar : Calendar) : Int
  {
    return selectedMonthCalendar.getActualMaximum(DAY_OF_MONTH)
  }
  
  private fun
  setTimeCalendarToFirstDayOfMonth(selectedMonthCalendar : Calendar)
  {
    selectedMonthCalendar.set(DAY_OF_MONTH, FIRST_DAY_OF_MONTH)
  }
  
  companion object
  {
    const val FIRST_DAY_OF_MONTH = 1
    const val PREVIOUS_MONTH = -1
    
    const val SUNDAY = 1
    const val MONDAY = 2
    const val TUESDAY = 3
    const val WEDNESDAY = 4
    const val THURSDAY = 5
    const val FRIDAY = 6
    const val SATURDAY = 7
    
    const val SUNDAY_INDEX = 6
    const val MONDAY_INDEX = 0
    const val TUESDAY_INDEX = 1
    const val WEDNESDAY_INDEX = 2
    const val THURSDAY_INDEX = 3
    const val FRIDAY_INDEX = 4
    const val SATURDAY_INDEX = 5
  }
}

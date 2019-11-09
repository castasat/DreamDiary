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
  val calendarDatesLiveData = MutableLiveData<Array<String>>()
  
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
  
  private fun
  fillMonthWithDates(selectedMonthCalendar : Calendar)
  {
    val maxDaysInSelectedMonth = determineSelectedMonthLengthInDays(selectedMonthCalendar)
    log("CalendarViewModel.fillMonthWithDates: maxDaysInSelectedMonth = $maxDaysInSelectedMonth")
    
    setTimeCalendarToFirstDayOfMonth(selectedMonthCalendar)
    
    val dayOfWeekForFirstDayOfMonth = determineWeekDay(selectedMonthCalendar)
    log("CalendarViewModel.fillMonthWithDates: dayOfWeekForFirstDayOfMonth = $dayOfWeekForFirstDayOfMonth")
    
    val firstDayIndex =
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
    
    val calendarDates = Array<String>(MAX_DAYS_IN_A_MONTH) {index : Int ->
      when(index)
      {
        in 0 until firstDayIndex -> // fill previous month
        {
          val previousMonthDay = maxDaysInPreviousMonth - (firstDayIndex - 1) + index
          log("CalendarViewModel.fillMonthWithDates: previousMonthDay = $previousMonthDay")
          previousMonthDay.toString()
        }
        in 1..(maxDaysInSelectedMonth + (firstDayIndex - 1)) -> // fill current month
        {
          val currentMonthDay = index - (firstDayIndex - 1)
          log("CalendarViewModel.fillMonthWithDates: currentMonthDay = $currentMonthDay")
          currentMonthDay.toString()
        }
        in ((maxDaysInSelectedMonth + firstDayIndex)..MAX_DAYS_IN_A_MONTH) -> // fill next month
        {
          val nextMonthDay = (index + 1) - (maxDaysInSelectedMonth + firstDayIndex)
          log("CalendarViewModel.fillMonthWithDates: nextMonthDay = $nextMonthDay")
          nextMonthDay.toString()
        }
        else -> { "" }
      }
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

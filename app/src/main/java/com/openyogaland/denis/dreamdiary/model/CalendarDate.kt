package com.openyogaland.denis.dreamdiary.model

import com.openyogaland.denis.dreamdiary.enumeration.CalendarDateType

data class
CalendarDate(
  var date : String = "",
  var dayOfMonth : String = "",
  var moonPhaseDay : String = "",
  var day : Day? = null,
  var dream : Dream? = null,
  var type : CalendarDateType? = null)
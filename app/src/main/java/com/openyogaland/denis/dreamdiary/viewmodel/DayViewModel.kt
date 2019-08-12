package com.openyogaland.denis.dreamdiary.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.openyogaland.denis.dreamdiary.application.DreamDiary.DreamDiary.log
import com.openyogaland.denis.dreamdiary.database.dao.DayDao
import com.openyogaland.denis.dreamdiary.database.dao.PracticeDao
import com.openyogaland.denis.dreamdiary.model.Day
import com.openyogaland.denis.dreamdiary.model.Practice
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers

class
DayViewModel(application : Application)
  : BaseViewModel(application)
{
  // room database fields
  private lateinit var practiceDao : PracticeDao
  private lateinit var dayDao : DayDao
  
  // live data fields
  var allPracticesLiveData = MutableLiveData<List<Practice>>()
  var currentDayLiveData = MutableLiveData<Day>()
  
  // reactive fields)
  private val addPracticePublishProcessor = PublishProcessor.create<Practice>()
  private val loadAllPracticesPublishProcessor = PublishProcessor.create<Boolean>()
  private val saveDayPublishProcessor = PublishProcessor.create<Day>()
  private val loadDayPublishProcessor = PublishProcessor.create<String>()
  
  init
  {
    initializeApplicationContext(application)
    initializeRoomDatabase()
    observeAddPractice()
    observeLoadAllPractices()
    observeLoadDay()
    observeSaveDay()
  }
  
  private fun
  observeLoadDay()
  {
    utilizeDisposable(loadDayPublishProcessor
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .filter {date : String ->
                        date.isNotEmpty() &&
                        date.isNotBlank()
                      }
                      .switchMap {date : String ->
                        dayDao.getDay(date).toFlowable()
                      }
                      .subscribe({day : Day ->
                                   currentDayLiveData
                                   .postValue(day)
                                 },
                                 {throwable : Throwable ->
                                   log("DayViewModel" +
                                       ".observeLoadDay(): " +
                                       "throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DayViewModel" +
                                       ".observeLoadDay(): " +
                                       "completed")
                                 }))
  }
  
  private fun
  observeSaveDay()
  {
    utilizeDisposable(saveDayPublishProcessor
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .switchMap {day : Day ->
                        log("DayViewModel.observeSaveDay():" +
                            "day = $day")
      
                        val dayId = dayDao.insert(day)
                        dayDao.getDay(dayId).toFlowable()
                      }
                      .subscribe({day : Day ->
                                   currentDayLiveData
                                   .postValue(day)
                                 },
                                 {throwable : Throwable ->
                                   log("DayViewModel" +
                                       ".observeSaveDay(): " +
                                       "throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DayViewModel" +
                                       ".observeSaveDay(): " +
                                       "completed")
                                 }))
  }
  
  private fun
  observeLoadAllPractices()
  {
    utilizeDisposable(loadAllPracticesPublishProcessor
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .switchMap {_ : Boolean ->
                        practiceDao.getAll().toFlowable()
                      }
                      .subscribe({practices : List<Practice> ->
                                   allPracticesLiveData.postValue(practices)
                                 },
                                 {throwable : Throwable ->
                                   log("DayViewModel" +
                                       ".observeLoadAllPractices(): " +
                                       "throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DayViewModel" +
                                       ".observeLoadAllPractices(): " +
                                       "completed")
                                 }))
  }
  
  private fun
  observeAddPractice()
  {
    utilizeDisposable(addPracticePublishProcessor
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .switchMap {practice : Practice ->
                        practiceDao.insert(practice)
                        practiceDao.getAll().toFlowable()
                      }
                      .subscribe({practices : List<Practice> ->
                                   allPracticesLiveData
                                   .postValue(practices)
                                 },
                                 {throwable : Throwable ->
                                   log("DayViewModel" +
                                       ".observeAddPractice(): " +
                                       "throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DayViewModel" +
                                       ".observeAddPractice(): " +
                                       "completed")
                                 }
                      ))
  }
  
  override fun
  initializeRoomDatabase()
  {
    super
    .initializeRoomDatabase()
    practiceDao = dreamDiaryRoomDatabase.practiceDao()
    dayDao = dreamDiaryRoomDatabase.dayDao()
  }
  
  fun
  addPractice(practice : Practice)
  {
    log("DayViewModel.addPractice(): " +
        "practice = $practice")
    addPracticePublishProcessor.onNext(practice)
  }
  
  fun
  loadAllPractices()
  {
    loadAllPracticesPublishProcessor.onNext(true)
  }
  
  fun
  loadDay(date : String)
  {
    loadDayPublishProcessor.onNext(date)
  }
  
  fun
  saveDay(day : Day)
  {
    log("DayViewModel.saveDay()")
    saveDayPublishProcessor.onNext(day)
  }
}

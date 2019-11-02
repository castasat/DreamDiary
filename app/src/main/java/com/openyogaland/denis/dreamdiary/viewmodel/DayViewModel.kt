package com.openyogaland.denis.dreamdiary.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.openyogaland.denis.dreamdiary.application.DreamDiary.DreamDiary.log
import com.openyogaland.denis.dreamdiary.database.dao.DayDao
import com.openyogaland.denis.dreamdiary.database.dao.PracticeDao
import com.openyogaland.denis.dreamdiary.model.Day
import com.openyogaland.denis.dreamdiary.model.Practice
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers

class
DayViewModel(application : Application)
  : BaseViewModel(application)
{
  // room database fields
  private lateinit var practiceDao : PracticeDao
  private lateinit var dayDao : DayDao
  
  // live data fields
  val allPracticesLiveData = MutableLiveData<List<Practice>>()
  val currentDayLiveData = MutableLiveData<Day>()
  
  // reactive fields
  private val addPracticePublishProcessor = PublishProcessor.create<Practice>()
  private val editPracticePublishProcessor = PublishProcessor.create<Practice>()
  private val loadAllPracticesPublishProcessor = PublishProcessor.create<Boolean>()
  private val saveDayPublishProcessor = PublishProcessor.create<Day>()
  private val loadDayPublishProcessor = PublishProcessor.create<String>()
  
  init
  {
    initializeApplicationContext(application)
    initializeRoomDatabase()
    observeAddPractice()
    observeEditPractice()
    observeLoadAllPractices()
    observeLoadDay()
    observeSaveDay()
  }
  
  private fun
  observeEditPractice()
  {
    utilizeDisposable(editPracticePublishProcessor
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .switchMap {practice : Practice ->
                        practiceDao.update(practice)
                        practiceDao.getAll().toFlowable()
                      }
                      .subscribe({practices : List<Practice> ->
                                   allPracticesLiveData
                                   .postValue(practices)
                                 },
                                 {throwable : Throwable ->
                                   log("DayViewModel" +
                                       ".observeEditPractice(): " +
                                       "throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DayViewModel" +
                                       ".observeEditPractice(): " +
                                       "completed")
                                 }))
  }
  
  private fun
  observeLoadDay()
  {
    utilizeDisposable(loadDayPublishProcessor
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .doOnNext {date : String ->
                        log("DayViewModel.observeLoadDay(): date = $date")
                      }
                      .filter {date : String ->
                        date.isNotEmpty() &&
                        date.isNotBlank()
                      }
                      .switchMap {date : String ->
                        dayDao.getDayMaybe(date).toFlowable()
                      }
                      .subscribe({day : Day ->
  
                                   log("DayViewModel.observeLoadDay(): ")
                                   log("day.date = ${day.date}")
                                   log("day.moonPhaseDay = ${day.moonPhaseDay}")
                                   log("day.cycleDay = ${day.cycleDay}")
                                   log("day.practiceType = ${day.practiceType}")
                                   log("day.practiceDurationMinutes = ${day.practiceDurationMinutes}")
                                   log("day.nutrition = ${day.nutrition}")
                                   log("day.events = ${day.events}")
                                   log("day.stressLevel = ${day.stressLevel}")
  
                                   log("DayViewModel.observeLoadDay(): day = $day")
  
                                   currentDayLiveData.postValue(day)
                                 },
                                 {throwable : Throwable ->
                                   log("DayViewModel.observeLoadDay(): " +
                                       "throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DayViewModel.observeLoadDay(): completed")
                                 }))
  }
  
  private fun
  observeSaveDay()
  {
    utilizeDisposable(saveDayPublishProcessor
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .doOnNext {day : Day ->
                        log("DayViewModel.observeSaveDay(): ")
                        log("day.date = ${day.date}")
                        log("day.moonPhaseDay = ${day.moonPhaseDay}")
                        log("day.cycleDay = ${day.cycleDay}")
                        log("day.practiceType = ${day.practiceType}")
                        log("day.practiceDurationMinutes = ${day.practiceDurationMinutes}")
                        log("day.nutrition = ${day.nutrition}")
                        log("day.events = ${day.events}")
                        log("day.stressLevel = ${day.stressLevel}")
      
                        log("DayViewModel.observeSaveDay(): day = $day")
                      }
                      .switchMap {day : Day ->
      
                        if(dayDao.getDay(day.date) == null)
                        {
                          log("DayViewModel.observeSaveDay(): insert day")
                          dayDao.insertDay(day)
                        }
                        else
                        {
                          log("DayViewModel.observeSaveDay(): update day")
                          dayDao.updateDay(day)
                        }
      
                        dayDao.getDayMaybe(day.date).toFlowable()
                      }
                      .subscribe({day : Day ->
                                   log("DayViewModel.observeSaveDay(): day = $day")
                                   currentDayLiveData.postValue(day)
                                 },
                                 {throwable : Throwable ->
                                   log("DayViewModel.observeSaveDay(): throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DayViewModel.observeSaveDay(): completed")
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
                                 }))
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
    saveDayPublishProcessor.onNext(day)
  }
  
  fun
  editPractice(practice : Practice)
  {
    log("DayViewModel.editPractice(): " +
        "practice = $practice")
    editPracticePublishProcessor.onNext(practice)
  }
}

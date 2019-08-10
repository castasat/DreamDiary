package com.openyogaland.denis.dreamdiary.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.openyogaland.denis.dreamdiary.application.DreamDiary.DreamDiary.log
import com.openyogaland.denis.dreamdiary.database.dao.DayDao
import com.openyogaland.denis.dreamdiary.database.dao.PracticeDao
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
  
  // reactive fields)
  private val addPracticePublishProcessor = PublishProcessor.create<Practice>()
  private val downloadAllPracticesPublishProcessor = PublishProcessor.create<Boolean>()
  
  init
  {
    initializeApplicationContext(application)
    initializeRoomDatabase()
    observeAddPractice()
    observeDownloadAllPractices()
  }
  
  private fun
  observeDownloadAllPractices()
  {
    utilizeDisposable(downloadAllPracticesPublishProcessor
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .switchMap {_ : Boolean ->
                        practiceDao.getAll()
                      }
                      .subscribe({practices : List<Practice> ->
                                   allPracticesLiveData.postValue(practices)
                                 },
                                 {throwable : Throwable ->
                                   log("DayViewModel" +
                                       ".observeDownloadAllPractices(): " +
                                       "throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DayViewModel" +
                                       ".observeDownloadAllPractices(): " +
                                       "completed")
                                 }))
  }
  
  private fun
  observeAddPractice()
  {
    log("DayViewModel.observeAddPractice")
    
    utilizeDisposable(addPracticePublishProcessor
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .switchMap {practice : Practice ->
                        log("DayViewModel.observeAddPractice():" +
                            "practice = $practice")
                        practiceDao.insert(practice)
                        practiceDao.getAll()
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
  downloadAllPractices()
  {
    downloadAllPracticesPublishProcessor.onNext(true)
  }
}

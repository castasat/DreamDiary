package com.openyogaland.denis.dreamdiary.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.openyogaland.denis.dreamdiary.application.DreamDiary.DreamDiary.log
import com.openyogaland.denis.dreamdiary.database.dao.DreamDao
import com.openyogaland.denis.dreamdiary.model.Dream
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers

class DreamViewModel(application : Application)
  : BaseViewModel(application)
{
  // room database fields
  private lateinit var dreamDao : DreamDao
  
  // live data fields
  val currentDreamLiveData = MutableLiveData<Dream>()
  
  // reactive fields
  private val saveDreamPublishProcessor = PublishProcessor.create<Dream>()
  private val loadDreamPublishProcessor = PublishProcessor.create<String>()
  
  init
  {
    initializeApplicationContext(application)
    initializeRoomDatabase()
    observeLoadDream()
    observeSaveDream()
  }
  
  private fun
  observeSaveDream()
  {
    utilizeDisposable(saveDreamPublishProcessor
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .switchMap {dream : Dream ->
                        log("DreamViewModel" +
                            ".observeSaveDream(): " +
                            "dream = $dream")
      
                        val dreamId = dreamDao.insert(dream)
                        dreamDao.getDream(dreamId).toFlowable()
                      }
                      .subscribe({dream : Dream ->
                                   currentDreamLiveData
                                   .postValue(dream)
                                 },
                                 {throwable : Throwable ->
                                   log("DreamViewModel" +
                                       ".observeSaveDream(): " +
                                       "throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DreamViewModel" +
                                       ".observeSaveDream(): " +
                                       "completed")
                                 }))
  }
  
  private fun
  observeLoadDream()
  {
    utilizeDisposable(loadDreamPublishProcessor
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .filter {date : String ->
                        date.isNotEmpty() &&
                        date.isNotBlank()
                      }
                      .switchMap {date : String ->
                        dreamDao.getDream(date).toFlowable()
                      }
                      .subscribe({dream : Dream ->
                                   currentDreamLiveData
                                   .postValue(dream)
                                 },
                                 {throwable : Throwable ->
                                   log("DreamViewModel" +
                                       ".observeLoadDream(): " +
                                       "throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DreamViewModel" +
                                       ".observeLoadDream(): " +
                                       "completed")
                                 }))
  }
  
  override fun
  initializeRoomDatabase()
  {
    super
    .initializeRoomDatabase()
    dreamDao = dreamDiaryRoomDatabase.dreamDao()
  }
  
  fun
  loadDream(date : String)
  {
    loadDreamPublishProcessor.onNext(date)
  }
  
  fun
  saveDream(dream : Dream)
  {
    saveDreamPublishProcessor.onNext(dream)
  }
}

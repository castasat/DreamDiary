package com.openyogaland.denis.dreamdiary.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.openyogaland.denis.dreamdiary.application.DreamDiary.DreamDiary.log
import com.openyogaland.denis.dreamdiary.database.dao.DreamDao
import com.openyogaland.denis.dreamdiary.model.Dream
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers

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
                      .doOnNext {dream : Dream ->
                        log("DreamViewModel.observeSaveDream(): " +
                            "dream = $dream")
                      }
                      .switchMap {dream : Dream ->
                        
                        if(dreamDao.getDream(dream.date) == null)
                        {
                          log("DreamViewModel.observeSaveDream(): insert dream")
                          dreamDao.insertDream(dream)
                        }
                        else
                        {
                          log("DreamViewModel.observeSaveDream(): update dream")
                          dreamDao.updateDream(dream)
                        }
                        
                        dreamDao.getDreamMaybe(dream.date).toFlowable()
                      }
                      .subscribe({dream : Dream ->
                                   currentDreamLiveData
                                   .postValue(dream)
                                 },
                                 {throwable : Throwable ->
                                   log("DreamViewModel.observeSaveDream(): " +
                                       "throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DreamViewModel.observeSaveDream(): completed")
                                 }))
  }
  
  private fun
  observeLoadDream()
  {
    utilizeDisposable(loadDreamPublishProcessor
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .doOnNext {date:String ->
                        log("DayViewModel.observeLoadDream(): date = $date")
                      }
                      .filter {date : String ->
                        date.isNotEmpty() &&
                        date.isNotBlank()
                      }
                      .switchMap {date : String ->
                        dreamDao.getDreamMaybe(date).toFlowable()
                      }
                      .subscribe({dream : Dream ->
                                   log("DayViewModel.observeLoadDream(): dream = $dream")
                                   currentDreamLiveData.postValue(dream)
                                 },
                                 {throwable : Throwable ->
                                   log("DreamViewModel.observeLoadDream(): " +
                                       "throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DreamViewModel.observeLoadDream(): completed")
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

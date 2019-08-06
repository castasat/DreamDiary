package com.openyogaland.denis.dreamdiary.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openyogaland.denis.dreamdiary.application.DreamDiary.DreamDiary.log
import com.openyogaland.denis.dreamdiary.database.dao.DayDao
import com.openyogaland.denis.dreamdiary.database.dao.PracticeDao
import com.openyogaland.denis.dreamdiary.model.Practice
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class
DayViewModel(application : Application)
  : BaseViewModel(application)
{
  // room database fields
  private lateinit var practiceDao : PracticeDao
  private lateinit var dayDao : DayDao
  
  // live data fields
  private var allPracticesLiveData = MutableLiveData<List<Practice>>()
  
  // reactive fields
  private val compositeDisposable = CompositeDisposable()
  
  init
  {
    initializeApplicationContext(application)
    initializeRoomDatabase()
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
    utilizeDisposable(Flowable
                      .fromCallable {
                        practiceDao.insert(practice)
                        practiceDao.getAll()
                      }
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .subscribe({practices : List<Practice> ->
                                   allPracticesLiveData
                                   .postValue(practices)
                                 },
                                 {throwable : Throwable ->
                                   log("DayViewModel" +
                                       ".addPractice(): " +
                                       "throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DayViewModel.addPractice(): " +
                                       "completed")
                                 }
                      ))
  }
  
  fun
  downloadAllPractices() : LiveData<List<Practice>>
  {
    utilizeDisposable(Flowable
                      .fromCallable {
                        practiceDao.getAll()
                      }
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .subscribe({practices : List<Practice> ->
                                   allPracticesLiveData.postValue(practices)
                                 },
                                 {throwable : Throwable ->
                                   log("DayViewModel.downloadAllPractices(): " +
                                       "throwable = $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DayViewModel.downloadAllPractices(): " +
                                       "completed")
                                 }))
    return allPracticesLiveData
  }
  
  private fun
  utilizeDisposable(disposableToUtilize : Disposable)
  {
    compositeDisposable.add(disposableToUtilize)
  }
}

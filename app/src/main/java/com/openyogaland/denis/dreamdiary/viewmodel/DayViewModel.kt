package com.openyogaland.denis.dreamdiary.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openyogaland.denis.dreamdiary.application.DreamDiary.DreamDiary.log
import com.openyogaland.denis.dreamdiary.database.dao.DayDao
import com.openyogaland.denis.dreamdiary.database.dao.PracticeDao
import com.openyogaland.denis.dreamdiary.model.Practice
import io.reactivex.Completable
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
  
  // liveData database fields
  var practicesLiveData = MutableLiveData<List<Practice>>()
  
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
    super.initializeRoomDatabase()
    
    practiceDao = dreamDiaryRoomDatabase.practiceDao()
    dayDao = dreamDiaryRoomDatabase.dayDao()
  }
  
  fun
  addPracticeType(practiceType : String)
  {
    utilizeDisposable(Completable
                      .fromAction {
                        practiceDao.insert(Practice(0, practiceType))
                      }
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .subscribe({
                                   log("DayViewModel.addPracticeType(): completed")
                                 },
                                 {throwable : Throwable ->
                                   log("DayViewModel.addPracticeType(): $throwable")
                                   throwable.printStackTrace()
                                 }))
  }
  
  fun
  loadPracticeTypes() : LiveData<List<Practice>>
  {
    utilizeDisposable(Flowable
                      .fromCallable {
                        practiceDao.getAll()
                      }
                      .subscribeOn(Schedulers.io())
                      .observeOn(Schedulers.io())
                      .subscribe({practices : List<Practice> ->
                                   practicesLiveData
                                   .postValue(practices)
                                 },
                                 {throwable : Throwable ->
                                   log("DayViewModel.loadPracticeTypes(): $throwable")
                                   throwable.printStackTrace()
                                 },
                                 {
                                   log("DayViewModel.loadPracticeTypes(): completed")
                                 }))
    return practicesLiveData
  }
  
  private fun
  utilizeDisposable(disposableToUtilize : Disposable)
  {
    compositeDisposable.add(disposableToUtilize)
  }
}

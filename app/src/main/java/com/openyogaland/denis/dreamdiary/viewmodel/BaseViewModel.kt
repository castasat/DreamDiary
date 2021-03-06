package com.openyogaland.denis.dreamdiary.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.openyogaland.denis.dreamdiary.database.DreamDiaryRoomDatabase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.lang.ref.WeakReference

open class
BaseViewModel(application : Application)
  : AndroidViewModel(application)
{
  // context fields
  private lateinit var contextWeakReference : WeakReference<Context>
  private val applicationContext : Context?
    get() = contextWeakReference.get()
  
  // room database fields
  protected lateinit var dreamDiaryRoomDatabase : DreamDiaryRoomDatabase
  
  // reactive fields
  private val compositeDisposable = CompositeDisposable()
  
  protected fun
  initializeApplicationContext(application : Application) : Context
  {
    contextWeakReference = WeakReference(application.applicationContext)
    return application.applicationContext
  }
  
  open fun
  initializeRoomDatabase()
  {
    applicationContext
    ?.let {applicationContext : Context ->
      DreamDiaryRoomDatabase.getDatabase(applicationContext)
      ?.let {dreamDiaryRoomDatabase : DreamDiaryRoomDatabase ->
        this.dreamDiaryRoomDatabase = dreamDiaryRoomDatabase
      }
    }
  }
  
  open fun
  utilizeDisposable(disposableToUtilize : Disposable)
  {
    compositeDisposable.add(disposableToUtilize)
  }
}

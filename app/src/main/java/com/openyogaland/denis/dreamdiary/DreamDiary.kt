package com.openyogaland.denis.dreamdiary

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.akaita.java.rxjava2debug.RxJava2Debug
import com.akaita.java.rxjava2debug.RxJava2Debug.getEnhancedStackTrace
import com.splunk.mint.Mint
import com.splunk.mint.Mint.appEnvironmentDevelopment
import com.splunk.mint.Mint.disableNetworkMonitoring
import com.splunk.mint.Mint.enableLogging
import com.splunk.mint.Mint.initAndStartSession
import com.splunk.mint.Mint.setApplicationEnvironment
import com.splunk.mint.Mint.setLogging
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

public class
DreamDiary : MultiDexApplication()
{
  private var mintDisposable : Disposable? = null
  
  override fun
  onCreate()
  {
    super
    .onCreate()
    
    // for use of vector drawables on pre-lollipop
    AppCompatDelegate
    .setCompatVectorFromResourcesEnabled(true)
    
    // RxJava2 Debug
    RxJava2Debug
    .enableRxJava2AssemblyTracking(arrayOf("com.openyogaland.denis.dreamdiary"))
    
    // crash reporting
    setApplicationEnvironment(appEnvironmentDevelopment)
    enableLogging(true)
    setLogging(800, "DreamDiary:D *:S")
    disableNetworkMonitoring()
    
    mintDisposable =
      Completable
      .fromAction {
        initAndStartSession(this, "cb4c0bda")
      }
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io())
      .subscribeBy(onError =
                   {
                     throwable: Throwable ->
                     getEnhancedStackTrace(throwable)
                     .printStackTrace()
                   },
                   onComplete = {})
  }
  
  override fun
  onTerminate()
  {
    super
    .onTerminate()
    
    mintDisposable?.dispose()
    
    Mint
    .closeSession(this)
  }
}

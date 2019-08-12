package com.openyogaland.denis.dreamdiary.application

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.openyogaland.denis.dreamdiary.BuildConfig
import com.splunk.mint.Mint
import com.splunk.mint.Mint.appEnvironmentDevelopment
import com.splunk.mint.Mint.disableNetworkMonitoring
import com.splunk.mint.Mint.enableLogging
import com.splunk.mint.Mint.initAndStartSession
import com.splunk.mint.Mint.setApplicationEnvironment
import com.splunk.mint.Mint.setLogging
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.NonNls

class
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
    
    // crash reporting
    setApplicationEnvironment(appEnvironmentDevelopment)
    enableLogging(true)
    setLogging(800, "DreamDiary:D *:S")
    disableNetworkMonitoring()
    
    mintDisposable =
      Completable
      .fromAction {
        initAndStartSession(this, "060d5e82")
      }
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.io())
      .subscribe({
        log("DreamDiary.onCreate(): completed")
                 },
                 {throwable : Throwable ->
                   throwable
                   .printStackTrace()
                 })
  }
  
  override fun
  onTerminate()
  {
    super
    .onTerminate()
    
    mintDisposable
    ?.dispose()
    
    Mint
    .closeSession(this)
  }
  
  companion object DreamDiary
  {
    @JvmStatic
    @NonNls
    val APP_ID = "DreamDiary"
    
    @JvmStatic
    fun log(@NonNls text : String?)
    {
      if(BuildConfig.DEBUG)
      {
        when
        {
          text == null   ->
          {
            @NonNls val nullPointer =
              "NULL POINTER: text in log"
            
            Log
            .d(APP_ID,
               nullPointer)
          }
          text.isEmpty() ->
          {
            @NonNls val isEmpty =
              "IS EMPTY: text in log"
            
            Log
            .d(APP_ID,
               isEmpty)
          }
          else           -> Log.d(APP_ID, text)
        }
      }
    }
  }
}

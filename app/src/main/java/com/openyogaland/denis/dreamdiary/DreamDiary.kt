package com.openyogaland.denis.dreamdiary

import androidx.multidex.MultiDexApplication
import com.splunk.mint.Mint.appEnvironmentDevelopment
import com.splunk.mint.Mint.disableNetworkMonitoring
import com.splunk.mint.Mint.enableLogging
import com.splunk.mint.Mint.initAndStartSession
import com.splunk.mint.Mint.setApplicationEnvironment
import com.splunk.mint.Mint.setLogging

public class
DreamDiary : MultiDexApplication()
{
  override fun
  onCreate()
  {
    super
    .onCreate()
  
    // crash reporting
    setApplicationEnvironment(appEnvironmentDevelopment)
    enableLogging(true)
    setLogging(800, "DreamDiary:D *:S")
    disableNetworkMonitoring();
    initAndStartSession(this, "cb4c0bda")
  }
}

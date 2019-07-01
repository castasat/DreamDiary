package com.openyogaland.denis.dreamdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

public class
MainActivity : AppCompatActivity()
{
  override fun onCreate(savedInstanceState : Bundle?)
  {
    super
    .onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    setupNavigation()
  }
  
  private fun
  setupNavigation()
  {
    val navigationController =
      Navigation
      .findNavController(this, R.id.navigationHost)
  
    bottomNavigation
    .setupWithNavController(navigationController)
  }
}

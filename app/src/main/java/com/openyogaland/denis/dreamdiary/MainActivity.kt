package com.openyogaland.denis.dreamdiary

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.openyogaland.denis.dreamdiary.R.id
import kotlinx.android.synthetic.main.activity_main.bottomNavigationView

public class
MainActivity : AppCompatActivity()
{
  private lateinit var navController : NavController
  
  override fun
  onCreate(savedInstanceState : Bundle?)
  {
    super
    .onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    navController = findNavController(this, id.navigationHostFragment)
    
    NavigationUI.setupWithNavController(bottomNavigationView, navController)
  }
  
  override fun onOptionsItemSelected(item : MenuItem) : Boolean
  {
    when (item.itemId)
    {
      android.R.id.home ->
      {
        navController.popBackStack()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }
}

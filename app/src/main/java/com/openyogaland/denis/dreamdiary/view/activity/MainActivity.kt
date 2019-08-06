package com.openyogaland.denis.dreamdiary.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.navigateUp
import com.openyogaland.denis.dreamdiary.R.id
import com.openyogaland.denis.dreamdiary.R.layout
import com.openyogaland.denis.dreamdiary.R.string
import com.openyogaland.denis.dreamdiary.listener.TitleNavigationListener
import com.openyogaland.denis.dreamdiary.viewmodel.ActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

public class
MainActivity : AppCompatActivity()
{
  private lateinit var navController : NavController
  private lateinit var activityViewModel : ActivityViewModel
  
  override fun
  onCreate(savedInstanceState : Bundle?)
  {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    
    activityViewModel =
    ViewModelProviders
    .of(this)
    .get(ActivityViewModel::class.java)
    
    navController =
      findNavController(this,
                        id.navigationHostFragment)
    
    navController
    .addOnDestinationChangedListener {_,
                                      destination,
                                      _ ->
      when(destination.id)
      {
        id.dayFragment   ->
        {
          titleTextView
          .text = getString(string.day_title)
        }
        id.dreamFragment ->
        {
          titleTextView
          .text = getString(string.dream_title)
        }
      }
    }
    
    val appBarConfiguration =
      AppBarConfiguration(navController.graph)
    
    titleTextView
    .setOnClickListener{_ : View ->
      navigateUp(navController,
                 appBarConfiguration)
    }
  
    navController
    .addOnDestinationChangedListener(TitleNavigationListener(titleTextView,
                                                             toolbar))
    NavigationUI
    .setupWithNavController(bottomNavigationView,
                            navController)
  }
  
  override fun
  onOptionsItemSelected(item : MenuItem) : Boolean
  {
    when(item.itemId)
    {
      android.R.id.home ->
      {
        navController
        .popBackStack()
        return true
      }
    }
    return super
    .onOptionsItemSelected(item)
  }
}

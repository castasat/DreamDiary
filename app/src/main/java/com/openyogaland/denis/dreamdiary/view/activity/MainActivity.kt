package com.openyogaland.denis.dreamdiary.view.activity

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.navigateUp
import com.openyogaland.denis.dreamdiary.R
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
    setContentView(R.layout.activity_main)
    
    activityViewModel =
      ViewModelProvider(this)
      .get(ActivityViewModel::class.java)
    
    navController =
      findNavController(this,
                        id.navigationHostFragment)
    
    navController
    .addOnDestinationChangedListener {_, destination, _ ->
      when(destination.id)
      {
        id.calendarFragment ->
        {
          appBarLayout.visibility = GONE
          
          if(VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN)
          {
            coordinatorLayout.background = ContextCompat.getDrawable(this, R.color.colorPrimaryDark)
          }
        }
        id.dayFragment   ->
        {
          appBarLayout.visibility = VISIBLE
          titleTextView.text = getString(string.day_title)
        }
        id.dreamFragment ->
        {
          appBarLayout.visibility = VISIBLE
          titleTextView.text = getString(string.dream_title)
        }
      }
    }
    
    val appBarConfiguration =
      AppBarConfiguration(navController.graph)
    
    titleTextView
    .setOnClickListener {_ : View ->
      navigateUp(navController,
                 appBarConfiguration)
    }
    
    navController.addOnDestinationChangedListener(TitleNavigationListener(titleTextView, toolbar))
    
    NavigationUI.setupWithNavController(bottomNavigationView, navController)
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

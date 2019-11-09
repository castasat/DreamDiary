package com.openyogaland.denis.dreamdiary.view.activity

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.MenuItem
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getDrawable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.R.id
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
    
    activityViewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)
    navController = findNavController(this, R.id.navigationHostFragment)
    
    navController
    .addOnDestinationChangedListener {_, destination, _ ->
      when(destination.id)
      {
        id.calendarFragment ->
        {
          appBarLayout.layoutParams.height =
            (280 * resources.displayMetrics.density).toInt()
          
          // TODO set image background of coordinator layout
          
          // from API 16
          if(VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN)
          {
            appBarLayout.background = getDrawable(this, R.color.translucent)
            appBarLayout.statusBarForeground = getDrawable(this, R.color.translucent)
            coordinatorLayout.background = getDrawable(this, R.drawable.sky_background)
          }
        }
        id.dayFragment ->
        {
          appBarLayout.layoutParams.height =
            (64 * resources.displayMetrics.density).toInt()
          // from API 16
          if(VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN)
          {
            appBarLayout.background = getDrawable(this, R.color.colorPrimary)
            appBarLayout.statusBarForeground = getDrawable(this, R.color.colorPrimary)
            coordinatorLayout.background = getDrawable(this, R.color.colorPrimary)
          }
          titleTextView.text = getString(string.day_title)
        }
        id.dreamFragment ->
        {
          appBarLayout.layoutParams.height =
            (64 * resources.displayMetrics.density).toInt()
          // from API 16
          if(VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN)
          {
            appBarLayout.background = getDrawable(this, R.color.colorPrimary)
            appBarLayout.statusBarForeground = getDrawable(this, R.color.colorPrimary)
            coordinatorLayout.background = getDrawable(this, R.color.colorPrimary)
          }
          titleTextView.text = getString(string.dream_title)
        }
      }
    }
    appBarLayout.visibility = VISIBLE
    
    val appBarConfiguration = AppBarConfiguration(navController.graph)
    titleTextView.setOnClickListener { navigateUp(navController, appBarConfiguration)}
    navController.addOnDestinationChangedListener(TitleNavigationListener(titleTextView, toolbar))
    setupWithNavController(bottomNavigationView, navController)
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

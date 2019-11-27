package com.openyogaland.denis.dreamdiary.view.activity

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.MenuItem
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getDrawable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.listener.TitleNavigationListener
import com.openyogaland.denis.dreamdiary.viewmodel.ActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar_layout.*

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
    
    // set translucent status bar
    if(VERSION.SDK_INT >= VERSION_CODES.KITKAT)
    {
      val window : Window = window
      window.setFlags(FLAG_LAYOUT_NO_LIMITS, FLAG_LAYOUT_NO_LIMITS)
    }
    
    activityViewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)
    navController = findNavController(this, R.id.navigationHostFragment)
    
    navController
    .addOnDestinationChangedListener {_, destination, _ ->
      when(destination.id)
      {
        R.id.calendarFragment ->
        {
          appBarLayout.layoutParams.height =
            (280 * resources.displayMetrics.density).toInt()
          
          // TODO set image background of coordinator layout
          
          // from API 16
          if(VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN)
          {
            appBarLayout.background = getDrawable(this, R.color.translucent)
            appBarLayout.statusBarForeground = getDrawable(this, R.color.translucent)
            mainConstraintLayout.background = getDrawable(this, R.drawable.sky_background)
          }
        }
        R.id.dayFragment ->
        {
          appBarLayout.layoutParams.height =
            (64 * resources.displayMetrics.density).toInt()
          // from API 16
          if(VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN)
          {
            appBarLayout.background = getDrawable(this, R.color.colorPrimary)
            appBarLayout.statusBarForeground = getDrawable(this, R.color.colorPrimary)
            mainConstraintLayout.background = getDrawable(this, R.color.colorPrimary)
          }
          titleTextView.text = getString(R.string.day_title)
        }
        R.id.dreamFragment ->
        {
          appBarLayout.layoutParams.height =
            (64 * resources.displayMetrics.density).toInt()
          // from API 16
          if(VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN)
          {
            appBarLayout.background = getDrawable(this, R.color.colorPrimary)
            appBarLayout.statusBarForeground = getDrawable(this, R.color.colorPrimary)
            mainConstraintLayout.background = getDrawable(this, R.color.colorPrimary)
          }
          titleTextView.text = getString(R.string.dream_title)
        }
      }
    }
    appBarLayout.visibility = VISIBLE
    
    toolbar?.let {toolbar ->
      titleTextView?.let {titleTextView ->
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        titleTextView.setOnClickListener {navigateUp(navController, appBarConfiguration)}
        navController.addOnDestinationChangedListener(TitleNavigationListener(titleTextView, toolbar))
        setupWithNavController(bottomNavigationView, navController)
      }
    }
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

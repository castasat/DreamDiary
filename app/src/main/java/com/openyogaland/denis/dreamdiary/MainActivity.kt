package com.openyogaland.denis.dreamdiary

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.palette.graphics.Palette
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.openyogaland.denis.dreamdiary.R.id
import kotlinx.android.synthetic.main.activity_main.*

public class
MainActivity : AppCompatActivity()
{
  private lateinit var navController : NavController
  private lateinit var collapsingToolbarLayout : CollapsingToolbarLayout
  private lateinit var toolbar : Toolbar
  
  override fun
  onCreate(savedInstanceState : Bundle?)
  {
    super
    .onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    
    navController = findNavController(this, id.navigationHostFragment)
    NavigationUI.setupWithNavController(bottomNavigationView, navController)
    
    collapsingToolbarLayout =
      findViewById(id.collapsingToolbarLayout)
  
    toolbar =
      findViewById(id.toolbar)
    
    val appBarConfiguration =
      AppBarConfiguration(navController.graph)
  
    collapsingToolbarLayout
    .setupWithNavController(toolbar, 
                            navController, 
                            appBarConfiguration)
    
    collapsingToolbarLayoutDynamicColor()
  }
  
  private fun
  collapsingToolbarLayoutDynamicColor()
  {
   val bitmap = BitmapFactory
    .decodeResource(resources, R.drawable.night_collapsing_toolbar_background)
    
    Palette
    .from(bitmap)
    .generate {palette ->
      
      palette
      ?.let {
        collapsingToolbarLayout
        .setContentScrimColor(palette
                              .getMutedColor(resources
                                             .getColor(R.color
                                                       .colorPrimary)))
        collapsingToolbarLayout
        .setStatusBarScrimColor(palette
                                .getMutedColor(resources
                                               .getColor(R.color
                                                         .colorPrimaryDark)))
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

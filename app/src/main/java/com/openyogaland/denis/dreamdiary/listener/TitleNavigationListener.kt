package com.openyogaland.denis.dreamdiary.listener

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavDestination
import java.lang.ref.WeakReference
import java.util.regex.Pattern

class TitleNavigationListener
  (titleTextView : AppCompatTextView,
   toolbar : Toolbar)
  : OnDestinationChangedListener
{
  private val mTitleTextViewWeakReference : WeakReference<TextView> =
    WeakReference(titleTextView)
  
  private val mToolbarWeakReference : WeakReference<Toolbar> =
    WeakReference(toolbar)
  
  override fun
  onDestinationChanged(controller : NavController,
                       destination : NavDestination,
                       arguments : Bundle?)
  {
    if(mToolbarWeakReference.get() == null)
    {
      controller.removeOnDestinationChangedListener(this)
      return
    }
    
    if(mTitleTextViewWeakReference.get() == null)
    {
      controller.removeOnDestinationChangedListener(this)
      return
    }
    
    if(destination is FloatingWindow)
    {
      return
    }
    
    destination.label
    ?.let {label : CharSequence ->
      if(label.isNotEmpty())
      {
        val title = StringBuffer()
        val fillInPattern =
          Pattern.compile("\\{(.+?)\\}")
        val matcher =
          fillInPattern.matcher(label)
        while(matcher.find())
        {
          val argName =
            matcher.group(1)
          
          if(arguments != null &&
             arguments.containsKey(argName))
          {
            matcher
            .appendReplacement(title,
                               "")
            title
            .append(arguments.get(argName).toString())
          }
          else
          {
            throw
            IllegalArgumentException("Could not find $argName in " +
                                     "$arguments to fill label $label")
          }
        }
        matcher.appendTail(title)
        setTitle(title)
      }
    }
  }
  
  private fun setTitle(title : CharSequence)
  {
    mTitleTextViewWeakReference
    .get()
    ?.text = title
  }
}

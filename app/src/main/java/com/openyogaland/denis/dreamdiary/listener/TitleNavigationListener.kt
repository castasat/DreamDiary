package com.openyogaland.denis.dreamdiary.listener

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavDestination
import androidx.navigation.ui.R
import java.lang.ref.WeakReference
import java.util.regex.Pattern

class TitleNavigationListener
  (titleTextView : AppCompatTextView,
   toolbar : Toolbar)
  : OnDestinationChangedListener
{
  private val mContext : Context =
    toolbar.context
  
  private val mTitleTextViewWeakReference : WeakReference<TextView> =
    WeakReference(titleTextView)
  
  private val mToolbarWeakReference : WeakReference<Toolbar> =
    WeakReference(toolbar)
  
  private var mArrowDrawable : DrawerArrowDrawable? = null
  private var mAnimator : ValueAnimator? = null
  
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
  
    setActionBarUpIndicator()
  }
  
  private fun setTitle(title : CharSequence)
  {
    mTitleTextViewWeakReference
    .get()
    ?.text = title
  }
  
  private fun
  setNavigationIcon(icon : Drawable?,
                    @StringRes contentDescription : Int)
  {
    mToolbarWeakReference.get()
    ?.let {toolbar : Toolbar ->
      toolbar.navigationIcon = icon
      toolbar.setNavigationContentDescription(contentDescription)
    }
  }
  
  private fun
  setActionBarUpIndicator()
  {
    var animate = true
    
    mArrowDrawable =
      mArrowDrawable
      ?: DrawerArrowDrawable(mContext)
    
    if(mArrowDrawable == null)
    {
      // We're setting the initial state, so skip the animation
      animate = false
    }
    setNavigationIcon(mArrowDrawable, R.string.nav_app_bar_navigate_up_description)
    val endValue = 1f
    if(animate)
    {
      mArrowDrawable
      ?.let {mArrowDrawable : DrawerArrowDrawable ->
        val startValue =
          mArrowDrawable
          .progress
        if(mAnimator != null)
        {
          mAnimator
          ?.cancel()
        }
        mAnimator =
          ObjectAnimator
          .ofFloat(mArrowDrawable,
                   "progress",
                   startValue,
                   endValue)
        mAnimator
        ?.start()
      }
    }
    else
    {
      mArrowDrawable
      ?.progress = endValue
    }
  }
}

package com.openyogaland.denis.dreamdiary.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.openyogaland.denis.dreamdiary.R
import com.openyogaland.denis.dreamdiary.model.Dream

public class
DayFragment : Fragment()
{
  override fun onCreateView(inflater : LayoutInflater,
                            container : ViewGroup?,
                            savedInstanceState : Bundle?) : View?
  {
    val view : View =
      inflater
      .inflate(R.layout.day_fragment,
               container,
               false)
    
    val navController =
      Navigation
      .findNavController(container as View)
    
    val stressLevelSeekBar =
      view
      .findViewById<AppCompatSeekBar>(R.id.stressLevelSeekBar)
    
    val onSeekBarChangeListener =
      object : SeekBar.OnSeekBarChangeListener
      {
        override fun
        onStartTrackingTouch(seekBar : SeekBar?)
        {
          if(context != null)
          {
            /*context?
            .toast("Пользователь коснулся")*/
          }
        }
        
        override fun
        onStopTrackingTouch(seekBar : SeekBar?)
        {
          /*context?
          .toast("Пользователь перестал касаться")*/
        }
        
        override fun
        onProgressChanged(seekBar : SeekBar?,
                          progress : Int,
                          fromUser : Boolean)
        {
          // TODO 
        }
      }
    
    stressLevelSeekBar
    .setOnSeekBarChangeListener(onSeekBarChangeListener)
    
    stressLevelSeekBar
    .progressDrawable =
      ContextCompat
      .getDrawable(activity as Context,
                   R.drawable.stress_seekbar)
    
    val dream = Dream("")
    
    val dreamBundle =
      NightFragment
      .bundleArgs(dream)
    
    navController
    .navigate(R.id.nightFragment,
              dreamBundle)
    
    return view
  }
}

fun Context.toast(message : CharSequence) =
  Toast.makeText(this, message, LENGTH_SHORT).show()

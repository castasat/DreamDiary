package com.openyogaland.denis.dreamdiary.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
      inflater.inflate(R.layout.day_fragment,
                       container,
                       false)
  
    val navController  =
      Navigation.findNavController(container as View)
    
    val dream = Dream()
    
    val dreamBundle =
      NightFragment.bundleArgs(dream)
    
    navController.navigate(R.id.nightFragment,
                           dreamBundle)
    
    return view
  }
}
